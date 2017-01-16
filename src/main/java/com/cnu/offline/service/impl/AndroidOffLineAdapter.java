package com.cnu.offline.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnu.iqas.bean.iword.WordRes;
import com.cnu.iqas.dao.iword.WordThemeDao;
import com.cnu.iqas.service.iword.WordResService;
import com.cnu.offline.MobileStyleEnum;
import com.cnu.offline.WordElement;
import com.cnu.offline.WordElement.Property;
import com.cnu.offline.WordElement.Property.Pro;
import com.cnu.offline.exception.OntologyException;
import com.cnu.offline.exception.ThemeWordNotExistException;
import com.cnu.offline.service.OffLineAdapter;
import com.cnu.offline.service.OffLineBagResource;
import com.cnu.offline.service.QueryWordFromDataBase;
import com.cnu.offline.utils.OffLineBagUntils;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年11月10日 上午10:14:51
* 类说明
*/
@Component("androidOffLineAdapter")
public class AndroidOffLineAdapter implements OffLineAdapter<PropertyEntity, WordElement> {

	private static final Logger logger= LogManager.getLogger(AndroidOffLineAdapter.class);

	//单词主题数据访问类
	private WordThemeDao wordThemeDao;
	//操作本题库
	private OntologyManage ontologyManage ;
	/**
	 * 单词资源查询服务类
	 */
	private WordResService wordResService ;
	@Override
	public void fillDocument(Document document, List<WordElement> listWordNodes, String themenumber, int realGrade,
			int recommendGrade) {
		 Element root = document.addElement( "words" );

		  if(listWordNodes!=null)
			for( WordElement we : listWordNodes){
				Element wordele = root.addElement( "word" ).addAttribute( "name", we.getName()).addAttribute("themenumber", we.getThemenumber()).addAttribute("grade", we.getGrade()+"");
				if(we.getPropertys()!=null)
				for(Property pe : we.getPropertys()){
					if( pe.getName()!=null && !pe.getName().trim().equals("")){
						Element proele =wordele.addElement("property")
							.addAttribute("name", pe.getName());
						if( pe.getDifficulty()!=null && !pe.getDifficulty().trim().equals(""))
							proele.addAttribute("difficulty", pe.getDifficulty());
						
						if(pe.getValue()!=null && !pe.getValue().trim().equals(""))
							proele.addAttribute("value", pe.getValue());
						else{
							List<Pro> list =pe.getPros();
							if( list!=null)
							 for(Pro pr: list){
								 proele.addElement("pro")
								 	    .addAttribute("grade", pr.getGrade())
								 	    .addAttribute("value",pr.getValue())
								 		.addAttribute("path", pr.getPath());
							 }								
						}													
					}
					
				}
			}
	}

	@Override
	public OffLineBagResource<PropertyEntity, WordElement> createOffLineBagResource(String themenumber, int realGrade,
			int recommendGrade) throws ThemeWordNotExistException{

		boolean ismaster = (realGrade == recommendGrade) ? true :false;
		//1.根据主题和推荐年级获得所有单词
		Hashtable<String, PropertyEntity> wordsMap=null;
		
		wordsMap = getMapPropertyEntity(realGrade,themenumber);
		
		if( wordsMap==null ||wordsMap.size()<=0){
			logger.info(themenumber+" 主题,"+realGrade+"年级,"+"没有单词!");
			throw new ThemeWordNotExistException(themenumber+" 主题"+" "+realGrade+"年级没有单词");
		}
		//3.开启多线程将对应单词的资源拷贝到压缩包文件中以及生成对应xml文件中的节点信息
		
		//3.2要处理的主单词
		List<PropertyEntity> listpes = new ArrayList<>();
		
		if(ismaster){
			//3.2主离线包要处理的所有单词,有可能会比wordMap中的多，因为每个单词的“联想propertyAssociate”、“同义词propertyAntonym”、“反义词propertySynonyms”、“拓展propertyExtend”、“常用propertyCommonUse”属性中可能包含其它单词
			Set<String> keyset=wordsMap.keySet();
			for( String key : keyset){
				PropertyEntity pe = wordsMap.get(key);
				listpes.add(pe);
				//添加pe的从单词，即“联想”、“同义词”、“反义词”、“拓展”、“常用”属性中的单词
				HashSet<PropertyEntity> subpes = pe.getSub();
				listpes.addAll(subpes);
			}
		}else{
			//从离线包要处理的单词只有主单词
			Set<String> keyset=wordsMap.keySet();
			for( String key : keyset){
				PropertyEntity pe = wordsMap.get(key);
				listpes.add(pe);
			}
		}
		//离线资源
		OffLineBagResource<PropertyEntity,WordElement> iosOfflineResource =new OffLineBagResource<PropertyEntity,WordElement>(listpes, themenumber, realGrade, recommendGrade,MobileStyleEnum.ANDROID);
		
		return iosOfflineResource;
	}

	@Override
	public void createOffLinePack(OffLineBagResource<PropertyEntity, WordElement> offLineBagResource,
			boolean ismasterPackage) {
		if(ismasterPackage){
			masterPackProducer(offLineBagResource);
		}else{
			slavePackProducer(offLineBagResource);
		}
	}
	/**
	 * 根据主题和年级从本体中获取单词
	 * @param grade
	 * @param themenumber
	 * @return
	 */
	private Hashtable<String,PropertyEntity> getMapPropertyEntity(int grade, String themenumber)throws OntologyException{
		 //1.根据主题编号获得主题："17.旅游与交通-（58）交通运输方式"
		Hashtable<String, PropertyEntity> wordsMap=null;
		
		String themeContent =wordThemeDao.findByNumber(themenumber);
		if(themeContent==null || themeContent.trim().equals("")){
			throw new RuntimeException("主题编号不存在:"+themenumber);
		}
		try {
			//2.根据主题和推荐年级获得所有单词
			QueryWordFromDataBase queryWord = new QueryWord4AndroidAdapter(ontologyManage);
			wordsMap = queryWord.queryWordByThemeAndGrade(grade, themeContent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("本体库异常:"+e.getMessage());
			throw new OntologyException("本体库异常,"+e.getMessage(),e);
		}
		return wordsMap;
	}

	private void slavePackProducer(OffLineBagResource<PropertyEntity, WordElement> offLineBagResource) {
		int realGrade = offLineBagResource.getRealGrade();
		File offlinebagDir =offLineBagResource.getOfflinebagDir();
		int recommendGrade= offLineBagResource.getRecommendGrade(); 
		String themenumber  = offLineBagResource.getThemenumber();
		while(!offLineBagResource.convertisover()){

		  PropertyEntity pe=offLineBagResource.getUnit();
		  if( pe!=null){
			String word = pe.getInstanceLabel();
			WordElement we =new WordElement(word,themenumber,realGrade);
			//生成"propertyScene"、"propertyText"、2个属性
			WordRes wr =wordResService.find(word);
			if( wr==null)
			{
				logger.info(word+"没有任何资源");

				offLineBagResource.producerWordElement(we);
				continue;
			}
			//1.生成情景段落
			WordElement.Property qjdlproperty = new WordElement.Property("propertyScene");
			//1.2推荐年级的情景段落
			try {
					WordElement.Property.Pro qjdlrecoGpro = getPro(wr,false,recommendGrade,offlinebagDir);
					if( qjdlrecoGpro!=null)
						 qjdlproperty.getPros().add(qjdlrecoGpro);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" "+recommendGrade+"年级，生成情景段落时出错:"+e.getMessage());
			}
			
			//2.生成课文原句
			WordElement.Property kwyjproperty = new WordElement.Property("propertyText");
			
			//2.2推荐年级的课文原句
			try {
					WordElement.Property.Pro kwyjrecoGpro = getPro(wr,true,recommendGrade,offlinebagDir);
					if( kwyjrecoGpro!=null)
						 kwyjproperty.getPros().add(kwyjrecoGpro);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" "+recommendGrade+"年级，生成课文原句时出错:"+e.getMessage());
			}
			
			//5.构造视频
			WordElement.Property videoproperty = new WordElement.Property("video");
			try {
				String value=null;
				if( realGrade>recommendGrade ){
					if(wr.getVideoPath1()!=null && !wr.getVideoPath1().equals("")){
					   value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath1());
					   videoproperty.setDifficulty("1");
					}
				}else if(realGrade<recommendGrade ){
					if(wr.getVideoPath3()!=null && !wr.getVideoPath3().equals("")){
					  value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath3());
					  videoproperty.setDifficulty("3");
					}
				}else{
						if(wr.getVideoPath2()!=null && !wr.getVideoPath2().equals("")){
							value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath2());
							videoproperty.setDifficulty("2");
						}
				}
				videoproperty.setValue(value);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" 视频文件拷贝出错");
			}
			we.getPropertys().add(qjdlproperty);
			we.getPropertys().add(kwyjproperty);
			we.getPropertys().add(videoproperty);
			offLineBagResource.producerWordElement(we);
		  }
		}
	}
	private void masterPackProducer(OffLineBagResource<PropertyEntity, WordElement> offLineBagResource) {
		int realGrade = offLineBagResource.getRealGrade();
		File offlinebagDir =offLineBagResource.getOfflinebagDir();
		int recommendGrade= offLineBagResource.getRecommendGrade(); 
		String themenumber  = offLineBagResource.getThemenumber();
		//logger.info(Thread.currentThread().getName()+"：开始");
		while(!offLineBagResource.convertisover()){

		  PropertyEntity pe=offLineBagResource.getUnit();

		  if( pe!=null){
			String word = pe.getInstanceLabel();
			WordRes wr =wordResService.find(word);
			
			WordElement we =WordElement.instance(pe,themenumber,realGrade);
			if( wr==null){
				offLineBagResource.producerWordElement(we);
				logger.error("生成压缩包时："+word+" 单词没有任何资源。");
				continue;				
			}
			//生成"propertyScene"、"propertyText"、2个属性
			//1.生成情景段落
			WordElement.Property qjdlproperty = new WordElement.Property("propertyScene");
			
			//1.1实际年级的情景段落
			try {
				WordElement.Property.Pro qjdlrealGpro= getPro(wr,false,realGrade,offlinebagDir);
				if( qjdlrealGpro!=null)
					qjdlproperty.getPros().add(qjdlrealGpro);
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("生成压缩包时："+word+" "+realGrade+"年级， 生成情景段落时出错:"+e1.getMessage());
			}
			
			//1.2推荐年级的情景段落
			try {
				if( realGrade!=recommendGrade){
					WordElement.Property.Pro qjdlrecoGpro = getPro(wr,false,recommendGrade,offlinebagDir);
					if( qjdlrecoGpro!=null)
						 qjdlproperty.getPros().add(qjdlrecoGpro);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" "+recommendGrade+"年级，生成情景段落时出错:"+e.getMessage());
			}
			
			//2.生成课文原句
			WordElement.Property kwyjproperty = new WordElement.Property("propertyText");
			//2.1实际年级的课文原句
			try {
				WordElement.Property.Pro kwyjrealGpro = getPro(wr,true,realGrade,offlinebagDir);
				if( kwyjrealGpro!=null)
					kwyjproperty.getPros().add(kwyjrealGpro);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("生成压缩包时："+word+" "+realGrade+"年级，生成课文原句时出错:"+e1.getMessage());
			}
			
			//2.2推荐年级的课文原句
			try {
				if( realGrade!=recommendGrade){
					WordElement.Property.Pro kwyjrecoGpro = getPro(wr,true,recommendGrade,offlinebagDir);
					if( kwyjrecoGpro!=null)
						 kwyjproperty.getPros().add(kwyjrecoGpro);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" "+recommendGrade+"年级，生成课文原句时出错:"+e.getMessage());
			}
			
			//3.构造发音
			WordElement.Property pronunciationproperty = new WordElement.Property("pronunciation");
			try {
				String value =OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PRONUNCIATIONDIR,wr.getVoicePath());
				pronunciationproperty.setValue(value);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" 发音文件拷贝出错");
			}
			//4.构造图片
			WordElement.Property pictureproperty = new WordElement.Property("picture");
			try {
				String value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PICTUREDIR,wr.getPicPath());
				pictureproperty.setValue(value);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" 图片文件拷贝出错");
			}
			//5.构造视频
			WordElement.Property videoproperty = new WordElement.Property("video");
			try {
				String value=null;
				if( realGrade>recommendGrade ){
					if(wr.getVideoPath1()!=null && !wr.getVideoPath1().equals("")){
					   value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath1());
					   videoproperty.setDifficulty("1");
					}
				}else if(realGrade<recommendGrade ){
					if(wr.getVideoPath3()!=null && !wr.getVideoPath3().equals("")){
					  value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath3());
					  videoproperty.setDifficulty("3");
					}
				}else{
						if(wr.getVideoPath2()!=null && !wr.getVideoPath2().equals("")){
							value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath2());
							videoproperty.setDifficulty("2");
						}
					
				}
				videoproperty.setValue(value);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" 视频文件拷贝出错");
			}
			we.getPropertys().add(qjdlproperty);
			we.getPropertys().add(kwyjproperty);
			we.getPropertys().add(pronunciationproperty);
			we.getPropertys().add(pictureproperty);
			we.getPropertys().add(videoproperty);
			offLineBagResource.producerWordElement(we);
		  }
		}
		
	}
	/**
	 * 生成“课文原句”、“情景段落”的Pro
	 * @param wr
	 * @param flage flage true:表示获取“课文原句”的值，false:表示获取“情景段落”的值
	 * @param grade 对应“课文原句”、“情景段落”的年级
	 * @return
	 * Pro
	 * 
	 */
	private WordElement.Property.Pro getPro(WordRes wr,boolean flage,int grade,File offlinebagDir)throws Exception{
		//1.1获取年级内容
		Map<String,String> realGradeQjdl=getPropertyValue(wr,flage,grade);
		String qjdlValue = realGradeQjdl.get("value");
		String  qjdlVoicePath= realGradeQjdl.get("path");
		//1.2查找年级音频位置
		if( qjdlVoicePath!=null && qjdlValue!=null ){
			
			String[] qjdls = qjdlValue.split(OffLineBagResource.PESPLITSTR);
			//1.3移动音频到压缩文件夹中
			String[] qjdlvoices = qjdlVoicePath.split(OffLineBagResource.PESPLITSTR);
			
			StringBuffer qjdlSb = new StringBuffer();
			StringBuffer qjdlvoiceSb  = new StringBuffer();
			for(int i = 0;i < qjdls.length;i++){
				String qjdlvoice =qjdlvoices[i];
				String qjdl= qjdls[i];
				//qjdlvoice = ifilesystem/wordres/voices/2016/09/03/1472913772722.mp3
				try {
					String path =OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.AUDIODIR,qjdlvoice);
					qjdlSb.append(qjdl).append(OffLineBagResource.WESPLITSTR);
					qjdlvoiceSb.append(path).append(OffLineBagResource.WESPLITSTR);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}	
			}
			if(qjdlSb.length()>0){
				//去掉最后的weSplitStr分隔符
				String value =qjdlSb.substring(0, qjdlSb.lastIndexOf(OffLineBagResource.WESPLITSTR));
				String path =qjdlvoiceSb.substring(0, qjdlvoiceSb.lastIndexOf(OffLineBagResource.WESPLITSTR));
				WordElement.Property.Pro pro =new WordElement.Property.Pro(grade+"");
				pro.setValue(value);
				pro.setPath(path);
				return pro;
			}
		}
		return null;
	}
	/**
	 * 根据grade年级获取WordRes中“课文原句”、和“情景段落”的值，以及他们对应的音频的路径
	 * @param wr
	 * @param flage true:表示获取“课文原句”的值，false:表示获取“情景段落”的值
	 * @param grade 获取对应年级的“课文原句”或“情景段落”的值
	 * @return 
	 * 通过"value"获取对应的值，通过"path"获取对应的音频路径
	 */
	private Map<String,String> getPropertyValue(WordRes wr,boolean flage,int grade){
		String value = null;
		String path = null;
		Map<String,String> map = new HashMap<>();
		switch(grade){
			case 1:
				value=!flage?wr.getQjdl1():wr.getKwyj1();
				path = !flage?wr.getQjdlVoicePath1():wr.getKwyjVoicePath1();
				break;
			case 2:
				value=!flage?wr.getQjdl2():wr.getKwyj2();
				path = !flage?wr.getQjdlVoicePath2():wr.getKwyjVoicePath2();
				break;
			case 3:
				value=!flage?wr.getQjdl3():wr.getKwyj3();
				path = !flage?wr.getQjdlVoicePath3():wr.getKwyjVoicePath3();
				break;
			case 4:
				value=!flage?wr.getQjdl4():wr.getKwyj4();
				path = !flage?wr.getQjdlVoicePath4():wr.getKwyjVoicePath4();
				break;
			case 5:
				value=!flage?wr.getQjdl5():wr.getKwyj5();
				path = !flage?wr.getQjdlVoicePath5():wr.getKwyjVoicePath5();
				break;
			case 6:
				value=!flage?wr.getQjdl6():wr.getKwyj6();
				path = !flage?wr.getQjdlVoicePath6():wr.getKwyjVoicePath6();
				break;
		}
		map.put("value", value);
		map.put("path", path);
		return map;
	}
	public WordThemeDao getWordThemeDao() {
		return wordThemeDao;
	}
	@Autowired
	public void setWordThemeDao(WordThemeDao wordThemeDao) {
		this.wordThemeDao = wordThemeDao;
	}
	public OntologyManage getOntologyManage() {
		return ontologyManage;
	}
	@Autowired
	public void setOntologyManage(OntologyManage ontologyManage) {
		this.ontologyManage = ontologyManage;
	}
	public WordResService getWordResService() {
		return wordResService;
	}
	@Autowired
	public void setWordResService(WordResService wordResService) {
		this.wordResService = wordResService;
	}

	@Override
	public MobileStyleEnum getMobileStyleEnum() {
		// TODO Auto-generated method stub
		return MobileStyleEnum.ANDROID;
	}
	
	
}
