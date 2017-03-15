package com.cnu.offline.service.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnu.iqas.utils.FileTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.offline.MobileStyleEnum;
import com.cnu.offline.bean.ExpandWord;
import com.cnu.offline.bean.Word;
import com.cnu.offline.bean.WordGrade;
import com.cnu.offline.bean.WordGradeId;
import com.cnu.offline.bean.Wresource;
import com.cnu.offline.dao.ExpandWordDao;
import com.cnu.offline.dao.WordDao;
import com.cnu.offline.exception.ThemeWordNotExistException;
import com.cnu.offline.service.OffLineAdapter;
import com.cnu.offline.service.OffLineBagResource;
import com.cnu.offline.service.WresourceService;
import com.cnu.offline.utils.OffLineBagUntils;
import com.cnu.offline.xml.AttributeValue;
import com.cnu.offline.xml.Unit;
import com.cnu.offline.xml.WordNode;
import com.cnu.offline.xml.WordNode.SlaveNode;

/**
* @author 周亮 
* @version 创建时间：2016年11月9日 下午9:06:40
* 类说明
*/
@Component("iosOffLineAdapter")
public class IosOffLineAdapter implements OffLineAdapter<Unit, WordNode> {

	private static final Logger logger= LogManager.getLogger(IosOffLineAdapter.class);
	
	//ios单词数据访问接口
	private WordDao wordDao;
	//ios扩展单词访问类型
	private ExpandWordDao expandWordDao;
	//操作单词资源
	private WresourceService wresourceService;
	
	

	@Override
	public OffLineBagResource<Unit, WordNode> createOffLineBagResource(String themenumber, int realGrade,
			int recommendGrade) throws ThemeWordNotExistException {
		boolean ismaster = (realGrade == recommendGrade) ? true :false;
		//1.根据主题和推荐年级获得所有主单词
	    List<Word> listWords = getWordsFromMysql(realGrade,themenumber);
		//根据主单词获取所有要处理的单词
	    List<Unit> listUnit = getUnits(listWords,ismaster);
		
		if( listUnit==null ||listUnit.size()<=0){
			logger.info(themenumber+" 主题,"+realGrade+"年级,"+"没有单词!");
			throw new ThemeWordNotExistException(themenumber+" 主题"+" "+realGrade+"年级没有单词");
		}
		//3.开启多线程将对应单词的资源拷贝到压缩包文件中以及生成对应xml文件中的节点信息
		
		//3.2要处理的主单词
			//3.2主离线包要处理的所有单词,有可能会比wordMap中的多，因为每个单词的“联想”、“同义词”、“反义词”、“拓展”、“常用”属性中可能包含其它单词
		
		//离线资源
		OffLineBagResource<Unit,WordNode> iosOfflineResource =new OffLineBagResource<Unit,WordNode>(listUnit, themenumber, realGrade, recommendGrade,MobileStyleEnum.IOS);
			
		return iosOfflineResource;
	}

	@Override
	public void createOffLinePack(OffLineBagResource<Unit, WordNode> offLineBagResource, boolean masterOffLinePackageFlage) {
		// TODO Auto-generated method stub
		if(masterOffLinePackageFlage){
			
			masterPackProducer(offLineBagResource);
		}else{
			slavePackProducer(offLineBagResource);
		}
	}

	@Override
	public void fillDocument(Document document, List<WordNode> listWes,String themenumber,int realGrade,int recommendGrade) {
		// TODO Auto-generated method stub
		//1级words
		 Element root = document.addElement( "words" );
		 root.addAttribute("topic", themenumber)
	 	 	 .addAttribute("realGrade", realGrade+"")
	 	 	 .addAttribute("recommendGrade", recommendGrade+"")
		 	 .addAttribute("count", listWes.size()+"");// topic="2-12-37" grade="4"
		 
		  if(listWes!=null)
			for( WordNode we : listWes){
				//2级word
				Element wordele = root.addElement( "word");
				for( AttributeValue attr: we.getAttrs())
					wordele.addAttribute(attr.getName(), attr.getValue());
				for( SlaveNode slave : we.getSlaves()){
					//3级property
					Element proele =wordele.addElement("property");
					for( AttributeValue attr: slave.getAttrs()){
						proele.addAttribute(attr.getName(), attr.getValue());
					}
					for( SlaveNode _2slave : slave.getSlaves()){
						//4级pro
						Element pr =proele.addElement("pro");
						for( AttributeValue attr: _2slave.getAttrs()){
							pr.addAttribute(attr.getName(), attr.getValue());
						}
					}
				}
			}
	}
	
	
	/**
	 * 创建ios的从离线包
	 * 包含内容：
	 * 下载推荐年级的单词视频及视频的题目
	 * 
	 */
	private void slavePackProducer(OffLineBagResource<Unit, WordNode> offLineBagResource) {
		int realGrade = offLineBagResource.getRealGrade();
		File offLineDir =offLineBagResource.getOfflinebagDir();
		int recommendGrade= offLineBagResource.getRecommendGrade(); 
		//判断是否完成
		while(!offLineBagResource.convertisover()){
			//1.获取要写入到xml中word
		  Unit unit=offLineBagResource.getUnit();
		  
		  if( unit!=null){
			//2.将word转换成wordNode描述的xml节点
			String releation = unit.getReleation();
			WordNode wn=null;
			if( releation.equals("master")){//处理主单词
				 wn=handleMasterUnit(unit,false,realGrade,recommendGrade,offLineDir);
			}
			if( wn ==null )
				logger.info("为 【"+unit.getWord()+"】生成对应的WordNode失败");

			offLineBagResource.producerWordElement(wn);
		  }
		}
	}
	/**
	 * 创建主离线包
	 * 目前ios端主离线包内容：
	 * 1. 2-12主题下4年级所有单词的文本信息，和资源文件（图片、视频、发音）
	 * 2. 3,4,5年级的课文原句和情景段落文本信息和资源文件
	 * 3. 3.4.5年级的所有扩展单词的文本信息和资源
	 */
	private void masterPackProducer(OffLineBagResource<Unit, WordNode> offLineBagResource) {
		int realGrade = offLineBagResource.getRealGrade();
		int recommendGrade= offLineBagResource.getRecommendGrade(); 
		File offLineDir =offLineBagResource.getOfflinebagDir();
		//判断是否完成
		while(!offLineBagResource.convertisover()){
			//1.获取要写入到xml中word
		  Unit unit=offLineBagResource.getUnit();
		  
		  if( unit!=null){
			//2.将word转换成wordNode描述的xml节点
			String releation = unit.getReleation();
			WordNode wn=null;
			if( releation.equals("master")){//处理主单词
				 wn=handleMasterUnit(unit,true,realGrade,recommendGrade,offLineDir);
			}if(releation.equals("slave") ){//处理从单词
				 wn=handleSlaveUnit(unit,offLineDir);
			}
			if( wn ==null )
				logger.info("为 【"+unit.getWord()+"】生成对应的WordNode失败");

			offLineBagResource.producerWordElement(wn);
		  }
		}
	}
	/**
	 * 处理主单词，依据主单词生成对应的WordNode对象
	 * @param unit
	 * @return
	 */
	private WordNode handleMasterUnit(Unit unit,boolean masterOffLinePackageFlage,int realGrade,int recommendGrade,File offlinebagDir) {
		WordNode wn = new WordNode();
		String word = unit.getWord();
		//添加属性
		wn.addAttribute(new AttributeValue("name",word))
		  .addAttribute(new AttributeValue("releation", unit.getReleation()));
		
		if( masterOffLinePackageFlage ){
			//3.1过滤掉需要特殊处理的属性
			List<String> exclu = new ArrayList<>();
			exclu.add("englishmeaning");
			exclu.add("textsentence");
			exclu.add("expandsentence");
			exclu.add("phase");
			exclu.add("photoUrl");
			exclu.add("soundUrl");
			//3.2往wordNode添加基础属性子节点
			WordNode.fillProperty2Wn(wn,unit,exclu);
		}
		
		
		//4.往wordNode添加复杂属性（带文件）
		//获取存储单词对应的资源对象
		Wresource resource =wresourceService.find(word);
		//判断资源是否存在
		if( resource==null){
			logger.info("生成压缩包时："+word+" 单词没有任何资源。");	
			return wn;		
		}
		
		if(masterOffLinePackageFlage){
			//获取单词其它年级的课文原句和情景段落，不包含当前学生所在年级。当前学生所在年级的课文、情景存放在i_word表中。
			List<WordGrade> listwg =wresourceService.findWordGrade(word);
			//生成带资源的属性englishmeaning textsentence expandsentence phase 发音 图片 视频
			
			//生成"phase"、"textsentence"、2个属性
			WordGrade ownwg = new WordGrade();
			ownwg.setId(new WordGradeId(word, realGrade));
			ownwg.setPhase(unit.getPhase());
			ownwg.setPhasephotoUrl(resource.getPhasephotoUrl());
			ownwg.setPhasesoundUrl(resource.getPhasesoundUrl());
			ownwg.setTextsentence(unit.getTextsentence());
			ownwg.setTextsentencephotoUrl(resource.getTextsentencephotoUrl());
			ownwg.setTextsentencesoundUrl(resource.getTextsentencesoundUrl());
			if( listwg==null)
				listwg = new ArrayList<>();
			//将本年级包含的课文原句和情景加入进来
			listwg.add(ownwg);
			//1.生成情景段落
			SlaveNode  qjdlproperty = new SlaveNode(new AttributeValue("name", "phase"));
			//1.生成课文原句
			SlaveNode  kwyjproperty = new SlaveNode(new AttributeValue("name", "textsentence"));
			//遍历生成
			for( WordGrade wg : listwg ){
				//1.1情景段落
				try {
					SlaveNode pro = createSlaveNode(wg,false,offlinebagDir);
					qjdlproperty.addSlaveNode(pro);
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("生成压缩包时："+word+" "+realGrade+"年级， 生成情景段落时出错:"+e1.getMessage());
				}
				//课文原句
				try {  
					SlaveNode pro = createSlaveNode(wg,true,offlinebagDir);
					kwyjproperty.addSlaveNode(pro);
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("生成压缩包时："+word+" "+realGrade+"年级， 生成情景段落时出错:"+e1.getMessage());
				}
			}

			//2.1.扩展
			SlaveNode  expandsentenceproperty = new SlaveNode(new AttributeValue("name", "expandsentence"));
			try {
				String soundPath=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PRONUNCIATIONDIR,resource.getExpandsentencesoundUrl());
				String picPath=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PICTUREDIR,resource.getExpandsentencephotoUrl());
				expandsentenceproperty.addAttribute(new AttributeValue("value", unit.getExpandsentence()))
									  .addAttribute(new AttributeValue("soundPath", soundPath))
									  .addAttribute(new AttributeValue("picPath", picPath));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" 图片文件拷贝出错");
			}
			
			//2.1.英文
			SlaveNode  englishmeaningproperty = new SlaveNode(new AttributeValue("name", "englishmeaning"));
			try {
				String soundPath=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PRONUNCIATIONDIR,resource.getEnglishimeaningsoundUrl());
				englishmeaningproperty.addAttribute(new AttributeValue("soundPath", soundPath))
									  .addAttribute(new AttributeValue("value", unit.getEnglishmeaning()));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" 图片文件拷贝出错");
			}
			//3.构造发音
			SlaveNode  pronunciationproperty = new SlaveNode(new AttributeValue("name", "pronunciation"));
			try {
				String value =OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PRONUNCIATIONDIR,resource.getSoundUrl());
				pronunciationproperty.addAttribute(new AttributeValue("value", value));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" 发音文件拷贝出错");
			}
			//4.构造图片
			SlaveNode  pictureproperty = new SlaveNode(new AttributeValue("name", "picture"));
			try {
				String value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PICTUREDIR,resource.getPhotoUrl());
				pictureproperty.addAttribute(new AttributeValue("value", value));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("生成压缩包时："+word+" 图片文件拷贝出错");
			}

			wn.addSlaveNode(qjdlproperty);
			wn.addSlaveNode(kwyjproperty);
			wn.addSlaveNode(pronunciationproperty);
			wn.addSlaveNode(pictureproperty);
			wn.addSlaveNode(expandsentenceproperty);
		    wn.addSlaveNode(englishmeaningproperty);
			
		}
		
		//6.构造实际年级对应难度的视频
		SlaveNode  videoproperty = new SlaveNode(new AttributeValue("name", "video"));
		try {
			String value=null;
			int difficult = 2;
			String videlUrl = resource.getVideoUrl2();
			if( !masterOffLinePackageFlage ){
				if( realGrade>recommendGrade ){
					videlUrl=resource.getVideoUrl1();
					difficult=1;
				}else if(realGrade<recommendGrade ){
					videlUrl=resource.getVideoUrl3();
					difficult=3;
				}
			}
			value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,videlUrl);
			
			videoproperty.addAttribute(new AttributeValue("value", value))
						 .addAttribute(new AttributeValue("difficulty", difficult+""));

			//视频答案问题
			String q1="";
			String a1="";
			String q2="";
			String a2="";
			 switch(difficult){
				case 1:
					 q1=resource.getV1q1();
					 a1=resource.getV1a1();
					 q2=resource.getV1q2();
					 a2=resource.getV1a2();
					break;
				case 2:
					 q1=resource.getV2q1();
					 a1=resource.getV2a1();
					 q2=resource.getV2q2();
					 a2=resource.getV2a2();
					break;
				case 3:
					 q1=resource.getV3q1();
					 a1=resource.getV3a1();
					 q2=resource.getV3q2();
					 a2=resource.getV3a2();
					break;
			}
			 SlaveNode pro1=new SlaveNode();
					 pro1.addAttribute(new AttributeValue("question", q1)).addAttribute(new AttributeValue("answer", a1));
			 SlaveNode pro2=new SlaveNode();
			 		pro2.addAttribute(new AttributeValue("question", q2)).addAttribute(new AttributeValue("answer", a2));
			 videoproperty.addSlaveNode(pro1).addSlaveNode(pro2);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成压缩包时："+word+" 视频文件拷贝出错");
		}
		wn.addSlaveNode(videoproperty);
		return wn;
	}

	/**
	 * 创建xml从节点描述类,如果wg的“课文原句或“情景段落”没有对应资源，则返回属性都为""
	 * @param wg  资源信息
	 * @param flage  true:表示获取“课文原句”的值，false:表示获取“情景段落”的值
	 * @param offlinebagDir  资源路径信息
	 * @return  
	 * @throws Exception
	 */
	private SlaveNode createSlaveNode(WordGrade wg,boolean flage,File offlinebagDir)throws Exception{
		//1.1获取年级内容
		Map<String,String> realGrade=getPropertyValue(wg,flage);
		String value = realGrade.get("value");
		String soundPath= realGrade.get("soundPath");
		String picPath= realGrade.get("picPath");
		
		//资源拷贝到压缩目录下,同时将压缩目录下资源对应的位置存放到wg中。
		if( value!=null && soundPath!=null && picPath!=null ){
			
			//课文原句、情景段落，每个属性记录都可能存在多个资源，多个资源是以&分隔，所以要分隔读取
			String[] values = value.split(OffLineBagResource.PESPLITSTR);
			//1.3移动音频到压缩文件夹中
			String[] sounds = soundPath.split(OffLineBagResource.PESPLITSTR);
			//图片资源
			String[] pics = picPath.split(OffLineBagResource.PESPLITSTR);
			
			StringBuffer valueSb = new StringBuffer();
			StringBuffer soundsSb  = new StringBuffer();
			StringBuffer picsSb  = new StringBuffer();
			for(int i = 0;i < values.length;i++){
				String sou=sounds[i];
				String va= values[i];
				String pic = pics[i];
				//sou = ifilesystem/wordres/voices/2016/09/03/1472913772722.mp3
				try {
					String path =OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.AUDIODIR,sou);
					valueSb.append(va).append(OffLineBagResource.IOS_WESPLITSTR);
					soundsSb.append(path).append(OffLineBagResource.IOS_WESPLITSTR);

					String picpath =OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PICTUREDIR,pic);
					picsSb.append(picpath).append(OffLineBagResource.IOS_WESPLITSTR);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}	
			}
				
				//去掉最后的weSplitStr分隔符
			    value =valueSb.substring(0, valueSb.lastIndexOf(OffLineBagResource.IOS_WESPLITSTR));
				String sopath =soundsSb.substring(0, soundsSb.lastIndexOf(OffLineBagResource.IOS_WESPLITSTR));
				String picMod=picsSb.substring(0, picsSb.lastIndexOf(OffLineBagResource.IOS_WESPLITSTR));
				SlaveNode slaveNode = new SlaveNode();
				slaveNode.addAttribute(new AttributeValue("grade", wg.getId().getGrade()+""))
		         		 .addAttribute(new AttributeValue("value", value))
				         .addAttribute(new AttributeValue("picPath", picMod))
				         .addAttribute(new AttributeValue("soundPath", sopath));
				return slaveNode;
		}
		SlaveNode nullSlave = new SlaveNode();
		nullSlave.addAttribute(new AttributeValue("grade", wg.getId().getGrade()+""))
		 .addAttribute(new AttributeValue("value", ""))
       .addAttribute(new AttributeValue("picPath", ""))
       .addAttribute(new AttributeValue("soundPath", ""));
		
		return nullSlave;
	}
	/**
	 * 根据grade年级获取WordGrade中“课文原句”、和“情景段落”的值，以及他们对应的音频、图片的路径
	 * @param wr
	 * @param flage true:表示获取“课文原句”的值，false:表示获取“情景段落”的值
	 * @return 
	 * 通过"value"获取对应的值，通过"soundPath"获取对应的音频路径，通过"picPath"获取对应的图片路径
	 */
	private Map<String,String> getPropertyValue(WordGrade wg,boolean flage){
		String value = null;
		String soundPath = null;
		String picPath = null;
		Map<String,String> map = new HashMap<>();
		if( flage ){//课文原句
			value=wg.getTextsentence();
			picPath=wg.getTextsentencephotoUrl();
			soundPath=wg.getTextsentencesoundUrl();
		}else{//情景段落
			value=wg.getPhase();
			picPath=wg.getPhasephotoUrl();
			soundPath=wg.getPhasesoundUrl();
		}
		
		map.put("value", value);
		map.put("soundPath", soundPath);
		map.put("picPath", picPath);
		return map;
	}
	/**
	 * 处理从单词，依据从单词生成对应的WordNode对象
	 * @param unit
	 * @return
	 */
	private WordNode handleSlaveUnit(Unit unit,File offlinebagDir) {
		WordNode wn = new WordNode();
		String word = unit.getWord();
		
		wn.addAttribute(new AttributeValue("name",word))
		  .addAttribute(new AttributeValue("releation", unit.getReleation()));
		
		SlaveNode node = new SlaveNode(new AttributeValue("name","textsentence"))
						.addAttribute(new AttributeValue("value",unit.getTextsentence()));
		wn.addSlaveNode(node);
		//发音、图片
		//3.构造发音
		SlaveNode  pronupro = new SlaveNode(new AttributeValue("name", "pronunciation"));
		try {
			String value =OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PRONUNCIATIONDIR,unit.getSoundUrl());
			pronupro.addAttribute(new AttributeValue("value", value));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成压缩包时："+word+" 发音文件拷贝出错");
		}
		//4.构造图片
		SlaveNode  picpro = new SlaveNode(new AttributeValue("name", "picture"));
		try {
			String value=OffLineBagUntils.copyfile2offliebag(offlinebagDir,OffLineBagResource.PICTUREDIR,unit.getPhotoUrl());
			picpro.addAttribute(new AttributeValue("value", value));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成压缩包时："+word+" 图片文件拷贝出错");
		}
		wn.addSlaveNode(pronupro).addSlaveNode(picpro);
		return wn;
	}
	/**
	 * 获取所有要处理的unit
	 * @param listWords 主单词集合
	 * @param ismasterPackage true:表示处理从单词；false不处理从单词
	 * @return
	 * 根据主单词生成unit,如果ismasterPackage=true,再根据主单词中的从单词生成unit，将所有生成的unit放到集合中返回。
	 */
	private List<Unit> getUnits(List<Word> listWords,boolean ismasterPackage)  {

		List<Unit> listUnits = new ArrayList();
		try {
			for( Word word :listWords){
				//将主单词转化到Unit中
					Unit un = new Unit("master");
					BeanUtils.copyProperties(un, word);
					listUnits.add(un);
					if( ismasterPackage ){
					//找出所有从单词，在属性：imageword  expandword中需找扩展单词
						String imWdAttr =word.getImageword();
						putUnitFromAttr(imWdAttr,listUnits);
						String expanAtrr = word.getExpandword();
						putUnitFromAttr(expanAtrr,listUnits);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getUnits: 将word和expandword转换成unit时出错");
		}
		return listUnits;
	}
	/**
	 * 从属性中添加从单词
	 * @param imWds
	 * @param listUnits
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void putUnitFromAttr(String imWds,List<Unit> listUnits) throws IllegalAccessException, InvocationTargetException {
		if( imWds!=null &&!imWds.trim().equals("无")&& !imWds.trim().equals("")){
			String[] exps = imWds.split("&");
			for( String expw : exps ){
				ExpandWord expWord = expandWordDao.find(expw);
				if(expWord!=null){
					Unit un = new Unit("slave");
					BeanUtils.copyProperties(un, expWord);
					listUnits.add(un);
				}
			}
		}
	}
	
	/**
	 * 在mysql数据库中根据主题和年级获取单词，主单词
	 * @param grade  年级
	 * @param themenumber  大主题编号，如：2-12
	 * @return
	 * 返回所有单词
	 */
	private List<Word> getWordsFromMysql(int grade,String themenumber){
		//查询条件
		//年级确定单词所在册数，一个年级有包含2册：c1=grade*2; c2=grade*2-1。所以在数据库中查询c1,c2所包含的单词
		Integer c1 = grade*2;
		Integer c2 = grade*2 -1;
		String wherejpql ="o.ceshu in (?,?) and o.topic like ?";
		Object[] queryParams = new Object[]{c1,c2,themenumber+"%"};
		List<Word> allWords =wordDao.getAllData(wherejpql, queryParams);
		
		return allWords;
	}

	public WordDao getWordDao() {
		return wordDao;
	}

	@Autowired
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}

	public ExpandWordDao getExpandWordDao() {
		return expandWordDao;
	}
	@Autowired
	public void setExpandWordDao(ExpandWordDao expandWordDao) {
		this.expandWordDao = expandWordDao;
	}

	public WresourceService getWresourceService() {
		return wresourceService;
	}
	@Autowired
	public void setWresourceService(WresourceService wresourceService) {
		this.wresourceService = wresourceService;
	}

	@Override
	public MobileStyleEnum getMobileStyleEnum() {
		// TODO Auto-generated method stub

		return MobileStyleEnum.IOS;
	}

}
