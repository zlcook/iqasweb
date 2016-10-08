package com.cnu.iqas.offline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cnu.iqas.bean.iword.WordRes;
import com.cnu.iqas.dao.iword.WordThemeDao;
import com.cnu.iqas.service.iword.WordResService;
import com.cnu.iqas.utils.FileTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.offline.DeleteFileTask;
import com.cnu.offline.MobileStyleEnum;
import com.cnu.offline.OffLineBagResource;
import com.cnu.offline.WordElement;
import com.cnu.offline.WordElement.Property;
import com.cnu.offline.WordElement.Property.Pro;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.service.OfflineService;
import com.cnu.offline.service.QueryWordFromOntology;
import com.cnu.offline.service.WordElementProducer;
import com.cnu.offline.service.impl.QueryWord4AndroidAdapter;
import com.cnu.offline.utils.ZipUtils;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年9月10日 下午9:28:31
* 类说明
*/
public class OffLineBagTest {

    private ThreadPoolTaskExecutor taskExecutor;
	private  OfflineService offlineService;
	private  OntologyManage ontologyManage;
	private  WordThemeDao wordThemeDao;
	/**
	 * 单词资源查询服务类
	 */
	private WordResService wordResService ;
	private  int realGrade=4;
	private  int recommendGrade=3;
	private String themenumber="2-17";
	/**
	 * 离线包文件夹
	 */
	private File offlinebagDir;
	
	
	private static final Logger logger= LogManager.getLogger(OffLineBagTest.class);
	@Before
	public void init(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		offlineService=ac.getBean(OfflineService.class);
		wordResService = ac.getBean(WordResService.class);
		wordThemeDao= ac.getBean(WordThemeDao.class);
		ontologyManage= ac.getBean(OntologyManage.class);
		taskExecutor = ac.getBean(ThreadPoolTaskExecutor.class);
	    //3.1新建压缩包根目录
		String rootPath=PropertyUtils.getFileSaveAbsolutePath("theme.offlinebag");
		    //相对路径
		String reldir =FileTool.createRelDir();
		String rootdirpath = rootPath+"/"+reldir;
		String rootName = themenumber+"-"+recommendGrade+"-"+realGrade;
		 offlinebagDir = new File(rootdirpath+"/"+rootName);
		if( !offlinebagDir.exists()){
			offlinebagDir.mkdirs();
		}
	}
	
	
	@Test
	public void createOfflineBag(){
		 //1.根据主题编号获得主题："17.旅游与交通-（58）交通运输方式"
		String themeContent =wordThemeDao.findByNumber(themenumber);
		//2.根据主题和推荐年级获得所有单词
		QueryWordFromOntology queryWord = new QueryWord4AndroidAdapter(ontologyManage);
		Hashtable<String,PropertyEntity> wordsMap=queryWord.queryWordByThemeAndGrade(realGrade, themeContent);
	
		if(wordsMap==null || wordsMap.size()<=0){
			logger.info(themenumber+" 主题,"+realGrade+"年级,"+"没有单词!");
			throw new RuntimeException(themenumber+" 主题"+" "+realGrade+"年级没有单词");
		}
		//3.开启多线程将对应单词的资源拷贝到压缩包文件中以及生成对应xml文件中的节点信息
		    //3.1新建压缩包根目录
		String rootPath=PropertyUtils.getFileSaveAbsolutePath("theme.offlinebag");
		    //相对路径
		String reldir =FileTool.createRelDir();
		String rootdirpath = rootPath+"/"+reldir;
		String rootName = themenumber+"-"+recommendGrade+"-"+realGrade;
		File rootDir = new File(rootdirpath+"/"+rootName);
		if( !rootDir.exists()){
			rootDir.mkdirs();
		}
			//3.2要处理的所有单词,有可能会比wordMap中的多，因为每个单词的“联想propertyAssociate”、“同义词propertyAntonym”、“反义词propertySynonyms”、“拓展propertyExtend”、“常用propertyCommonUse”属性中可能包含其它单词
		HashSet<PropertyEntity> listpes = new HashSet<>();
		Set<String> keyset=wordsMap.keySet();
		for( String key : keyset){
			PropertyEntity pe = wordsMap.get(key);
			listpes.add(pe);
			//添加pe的从单词，即“联想”、“同义词”、“反义词”、“拓展”、“常用”属性中的单词
			HashSet<PropertyEntity> subpes = pe.getSub();
			listpes.addAll(subpes);
		}
		
		OffLineBagResource offLineBagResource= new OffLineBagResource(listpes,rootDir,themenumber,realGrade, recommendGrade);
			//3.3开启3个线程
		taskExecutor.execute(new WordElementProducer(offLineBagResource,wordResService));
		taskExecutor.execute(new WordElementProducer(offLineBagResource,wordResService));
		taskExecutor.execute(new WordElementProducer(offLineBagResource,wordResService));
		taskExecutor.execute(new WordElementProducer(offLineBagResource,wordResService));/*      */
		//new Thread(new WordElementProducer(offLineBagResource,wordResService)).start();
		//4.文件转移完，开始生成xml文件
		//4.1新建Xml
		Document document =offLineBagResource.createDocument(rootDir, "words.xml");
		
		//5.压缩文件
		//压缩文件名
		String zipname = rootName+".zip";
		File zipFile = new File(rootdirpath,zipname);
		List<File> zipfiles = new ArrayList<>();
		zipfiles.add(rootDir);
		try {
			ZipUtils.zipFiles(zipfiles, zipFile);
			
			//保存压缩文件记录
			OffLineBag bag = new OffLineBag(zipname, MobileStyleEnum.ANDROID, themenumber, recommendGrade, realGrade, "zip",offLineBagResource.getWordSum());
			bag.setDownsize(zipFile.length());
			bag.setSavePath(PropertyUtils.getFileSaveDir("theme.offlinebag")+"/"+reldir+"/"+zipname);
			bag.setVersion(1);
			//开启线程删除，压缩前的文件
			new Thread(new DeleteFileTask(rootDir)).start();
			//taskExecutor.execute(new DeleteFileTask(rootDir));
			
			System.out.println(bag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("压缩失败"+e.getMessage());
		}
	}
	
	@Test
	public void testInstatnceProperty(){
		
		PropertyEntity pe = new PropertyEntity();
		pe.setInstanceLabel("trip");
		pe.setPropertyAntonym("无");
		pe.setPropertyAssociate("train (火车)/ship (轮船)/plane (飞机)");
		pe.setPropertyBook("7");
		pe.setPropertyChinese("出行，旅行");
		pe.setPropertyClass("tour|旅游");
		pe.setPropertyCommonUse("go on a trip(出去旅行)");
		pe.setPropertyDifficulty("5");
		pe.setPropertyExpand("A:I haven't seen you for a long time.(我很长时间没见到你了。)B:I've made a round-the-world trip.(我去环球旅行啦。)");
		pe.setPropertyExtend("business trip(出差)/free trip(免费旅行)");
		pe.setPropertyFunction("无");
		pe.setPropertyID("4/7/5/3");
		pe.setPropertyNcyclopedia("无");
		pe.setPropertyPartsOfSpeech("n.");
		pe.setPropertyScene("1.A:Did you go on a school trip yesterday?B:Yes.We went to the Great Wall.");
		pe.setPropertySynonyms("journey(旅行)");
		pe.setPropertyText("1.Did you go on a school trip yesterday?");
		pe.setPropertyTopic("17.旅游与交通-（57）旅行");
		pe.setPropertyUse("无");
		pe.setPropertyVersion("外研社（一年级起点）教育部审定2013");
		pe.setPropertyWordProperty("trips");
		WordElement we =WordElement.instance(pe,themenumber,realGrade);
		
		//1.生成情景段落
		WordElement.Property qjdlproperty = new WordElement.Property("propertyScene");
		String word = pe.getInstanceLabel();
		WordRes wr =wordResService.find(word);
		//1.1实际年级的情景段落
		try {
			WordElement.Property.Pro qjdlrealGpro= getPro(wr,false,realGrade);
			if( qjdlrealGpro!=null)
				qjdlproperty.getPros().add(qjdlrealGpro);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("生成压缩包时："+word+" "+realGrade+"年级， 生成情景段落时出错:"+e1.getMessage());
		}
		
		//1.2推荐年级的情景段落
		try {
			if( realGrade!=recommendGrade){
				WordElement.Property.Pro qjdlrecoGpro = getPro(wr,false,recommendGrade);
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
			WordElement.Property.Pro kwyjrealGpro = getPro(wr,true,realGrade);
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
				WordElement.Property.Pro kwyjrecoGpro = getPro(wr,true,recommendGrade);
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
			String value =copyfile2offliebag(offlinebagDir,OffLineBagResource.PRONUNCIATIONDIR,wr.getVoicePath());
			pronunciationproperty.setValue(value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成压缩包时："+word+" 发音文件拷贝出错");
		}
		//4.构造图片
		WordElement.Property pictureproperty = new WordElement.Property("picture");
		try {
			String value=copyfile2offliebag(offlinebagDir,OffLineBagResource.PICTUREDIR,wr.getPicPath());
			pictureproperty.setValue(value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成压缩包时："+word+" 图片文件拷贝出错");
		}
		//5.构造视频
		WordElement.Property videoproperty = new WordElement.Property("video");
		try {
			String value=null;
			if( realGrade>recommendGrade){
			   value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath1());
			   videoproperty.setDifficulty("1");
			}else if(realGrade<recommendGrade){
				  value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath3());
				  videoproperty.setDifficulty("3");
			}else{
				value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath2());
				 videoproperty.setDifficulty("2");
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
		System.out.println(we.getName());
		for(Property pro: we.getPropertys()){
			System.out.println("name:"+pro.getName()+" value:"+pro.getValue()+"  difficulty:"+pro.getDifficulty());
			for(Pro p: pro.getPros()){
				System.out.println("grade:"+p.getGrade()+"  path:"+p.getPath()+"  value:"+p.getValue());
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
	private WordElement.Property.Pro getPro(WordRes wr,boolean flage,int grade)throws Exception{
		//1.1获取情景段落实际年级内容
		Map<String,String> realGradeQjdl=getPropertyValue(wr,flage,grade);
		String qjdlValue = realGradeQjdl.get("value");
		String  qjdlVoicePath= realGradeQjdl.get("path");
		//1.2查找情景段实际年级落音频位置
		if( qjdlVoicePath!=null && qjdlValue!=null ){
			
			//WordElement.Property.Pro pro =null;
			
			String[] qjdls = qjdlValue.split(OffLineBagResource.PESPLITSTR);
			//1.3移动情景段落的音频到压缩文件夹中
			String[] qjdlvoices = qjdlVoicePath.split(OffLineBagResource.PESPLITSTR);
			
			StringBuffer qjdlSb = new StringBuffer();
			StringBuffer qjdlvoiceSb  = new StringBuffer();
			for(int i = 0;i < qjdls.length;i++){
				String qjdlvoice =qjdlvoices[i];
				String qjdl= qjdls[i];
				//qjdlvoice = ifilesystem/wordres/voices/2016/09/03/1472913772722.mp3
				try {
					String path =copyfile2offliebag(offlinebagDir,OffLineBagResource.AUDIODIR,qjdlvoice);
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
	 * 
	 *拷贝资源文件到压缩文件路径下
	 * @param offlinebagDir 压缩文件根目录 
	 * @param filedir   相对于压缩文件的中存储文件的目录如： auido
	 * @param relaPath  文件相对路径,比如ifilesystem/wordres/voices/2016/09/03/1472913772722.mp3
	 * @return 返回文件在压缩中的相对位置，相对于压缩包根目录
	 *  比如：auido/1472913772722.mp3
	 * @throws Exception
	 */
	public String copyfile2offliebag(File offlinebagDir,String filedir,String relaPath) throws Exception{
		
		if( offlinebagDir==null )
			throw new RuntimeException("压缩文件夹为null");
		if( filedir ==null || relaPath ==null)
			throw new RuntimeException("filedir 或者relaPath为null");
		File voiceDir = new File(offlinebagDir,filedir);
		if( !voiceDir.exists() ){
			voiceDir.mkdir();
		}
		//
		String fileName =relaPath.substring(relaPath.lastIndexOf("/")+1);
		//建立接收文件
		File  voicefile = new File(voiceDir,fileName);
		//读取文件  D:/Soft/autoiqasweb/ifilesystem/wordres/voices/2016/09/03/1472913772722.mp3
		String saveFilepath = PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR)+relaPath;
		File infile = new File(saveFilepath);
		try {
			FileTool.copy(infile, voicefile);
			return filedir+"/"+fileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
		
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
				value=flage?wr.getQjdl1():wr.getKwyj1();
				path = flage?wr.getQjdlVoicePath1():wr.getKwyjVoicePath1();
				break;
			case 2:
				value=flage?wr.getQjdl2():wr.getKwyj2();
				path = flage?wr.getQjdlVoicePath2():wr.getKwyjVoicePath2();
				break;
			case 3:
				value=flage?wr.getQjdl3():wr.getKwyj3();
				path = flage?wr.getQjdlVoicePath3():wr.getKwyjVoicePath3();
				break;
			case 4:
				value=flage?wr.getQjdl4():wr.getKwyj4();
				path = flage?wr.getQjdlVoicePath4():wr.getKwyjVoicePath4();
				break;
			case 5:
				value=flage?wr.getQjdl5():wr.getKwyj5();
				path = flage?wr.getQjdlVoicePath5():wr.getKwyjVoicePath5();
				break;
			case 6:
				value=flage?wr.getQjdl6():wr.getKwyj6();
				path = flage?wr.getQjdlVoicePath6():wr.getKwyjVoicePath6();
				break;
		}
		map.put("value", value);
		map.put("path", path);
		return map;
	}
}
