package com.cnu.offline.service.impl;

import java.beans.ConstructorProperties;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.dao.iword.WordThemeDao;
import com.cnu.iqas.enumtype.WordAttributeEnum;
import com.cnu.iqas.service.iword.ResourceCustomService;
import com.cnu.iqas.service.iword.WordResService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.utils.FileTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.offline.DeleteFileTask;
import com.cnu.offline.MobileStyleEnum;
import com.cnu.offline.OffLineBagResource;
import com.cnu.offline.ThemeWordNotExistException;
import com.cnu.offline.WordElement;
import com.cnu.offline.WordElement.Property;
import com.cnu.offline.WordElement.Property.Pro;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.bean.OffLineWordXml;
import com.cnu.offline.dao.OffLineDao;
import com.cnu.offline.service.OfflineService;
import com.cnu.offline.service.QueryWordFromOntology;
import com.cnu.offline.service.WordElementProducer;
import com.cnu.offline.utils.ZipUtils;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年7月10日 下午5:08:26
* 类说明
*/
@Service("offlineService")
public class OfflineServiceImpl implements OfflineService {

	private static final Logger logger= LogManager.getLogger(OfflineServiceImpl.class);
	//线程池
    private ThreadPoolTaskExecutor taskExecutor;
	private static String[] propertyLabel = new String[] { "单词ID", "单词",
			"主题-功能意念", "主题-话题", "Hownet中的父类", "词性", "词性属性", "中文含义", "单词教材版本",
			"单词册数", "难度", "课文原句", "情境段落", "联想", "同义词", "反义词", "拓展", "百科", "用法",
			"延伸例句", "常用" };
	private static String[] propertySPARQLValue = { "?propertyID",
			"?instanceLabel", "?propertyFunction", "?propertyTopic",
			"?propertyClass", "?propertyPartsOfSpeech",
			"?propertyWordProperty", "?propertyChinese", "?propertyVersion",
			"?propertyBook", "?propertyDifficulty", "?propertyText",
			"?propertyScene", "?propertyAssociate", "?propertyAntonym",
			"?propertySynonyms", "?propertyExtend", "?propertyNcyclopedia",
			"?propertyUse", "?propertyExpand", "?propertyCommonUse" };
	//操作本题库
	private OntologyManage ontologyManage ;
	//资源加载器
	private ResourceLoader resourceLoader;
	//操作离线文件
	private OffLineDao offLineDao;
	//操作单词资源
	private WordResourceService wordResourceService;
	private WordResService wordResService;
	//单词主题数据访问类
	private WordThemeDao wordThemeDao;
	
	
	public WordThemeDao getWordThemeDao() {
		return wordThemeDao;
	}
	@Resource
	public void setWordThemeDao(WordThemeDao wordThemeDao) {
		this.wordThemeDao = wordThemeDao;
	}
	
	
	@Override
	public OffLineWordXml createByThme(WordTheme wordTheme) {
		//得到所有单词
		List<PropertyEntity> listpe = generate4ontology(wordTheme.getContent());
		
		//生成document
		  Document document = DocumentHelper.createDocument();
	      Element words = document.addElement( "words" ).addAttribute("count", listpe.size()+"");
	      //遍历添加单词
          for(PropertyEntity pe: listpe){
        	pullPropertyEntity(words,pe);
          }
          try {
  			OutputFormat format = OutputFormat.createPrettyPrint();
  		    //项目实际根目录
 		    String fileSystemDir=PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR);
 		    String releativePath =PropertyUtils.getFileSaveDir("theme.offline");
  			File dir = new File(fileSystemDir+releativePath);
  			if(!dir.exists())
  				dir.mkdirs();
  			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
  			String xmlName = sdf.format(new Date())+".xml";
  			File xmlfile = new File(dir,xmlName);
			XMLWriter writer = new XMLWriter(new FileWriter(xmlfile),format);
			writer.write( document );
			writer.close();
			String savePath =releativePath+"/"+xmlName;
			OffLineWordXml wordXml =new OffLineWordXml(wordTheme.getId(), xmlName, "1", xmlfile.length(), savePath, listpe.size());
			return wordXml;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private void pullPropertyEntity(Element root, PropertyEntity pe) {
		// TODO Auto-generated method stub
		 Element word = root.addElement("word");
		 word.addAttribute("name", pe.getInstanceLabel());
		 //id
		 word.addAttribute("id", pe.getPropertyID());
		 //主题-功能意念
		 word.addAttribute("functiontheme", pe.getPropertyFunction());
		 //主题-话题
		 word.addAttribute("topictheme", pe.getPropertyTopic());
		 
		 Element properties= word.addElement("properties");
		//单词
		 addProperty(pe, properties,WordAttributeEnum.INSTANCELABEL);
		//Hownet中的父类属性
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYCLASS);
		 //词性
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYPARTSOFSPEECH);
		 //词性属性
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYWORDPROPERTY);
		 //中文含义
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYCHINESE);
		 //单词教材版本
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYVERSION);
		 //单词册数
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYBOOK);
		 //难度
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYDIFFICULTY);
		 //课文原句
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYTEXT);
		 //情境段落
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYSCENE);
		 //联想
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYASSOCIATE);
		 //同义词
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYANTONYM);
		 //反义词
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYSYNONYMS);
		 //拓展
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYEXTEND);
		 //百科
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYNCYCLOPEDIA);
		 //用法
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYUSE);
		 //延伸例句
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYEXPAND);
		 //常用
		 addProperty(pe, properties,WordAttributeEnum.PROPERTYCOMMONUSE);
		

	}
	/**
	 * 添加属性
	 * @param pe
	 * @param properties
	 * @param attrEnum
	 */
	private void addProperty(PropertyEntity pe, Element properties,WordAttributeEnum attrEnum) {
		Element wordproperty=properties.addElement("property");
		 
		 wordproperty.addAttribute("name",attrEnum.toString());
		 //属性值
		 Element wordvalues= wordproperty.addElement("values");
		 if( attrEnum.equals(WordAttributeEnum.INSTANCELABEL)){
			 wordvalues.addElement("value").addText(pe.getInstanceLabel());
			//属性资源
			 addResouce4Pro(wordproperty,pe);
		 }
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYANTONYM))
			 wordvalues.addElement("value").addText(pe.getPropertyAntonym());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYASSOCIATE))
			 wordvalues.addElement("value").addText(pe.getPropertyAssociate());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYBOOK))
			 wordvalues.addElement("value").addText(pe.getPropertyBook());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYCHINESE))
			 wordvalues.addElement("value").addText(pe.getPropertyChinese());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYCLASS))
			 wordvalues.addElement("value").addText(pe.getPropertyClass());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYCOMMONUSE))
			 wordvalues.addElement("value").addText(pe.getPropertyCommonUse());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYDIFFICULTY))
			 wordvalues.addElement("value").addText(pe.getPropertyDifficulty());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYEXPAND))
			 wordvalues.addElement("value").addText(pe.getPropertyExpand());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYEXTEND))
			 wordvalues.addElement("value").addText(pe.getPropertyExtend());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYNCYCLOPEDIA))
			 wordvalues.addElement("value").addText(pe.getPropertyNcyclopedia());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYPARTSOFSPEECH))
			 wordvalues.addElement("value").addText(pe.getPropertyPartsOfSpeech());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYSCENE))
			 wordvalues.addElement("value").addText(pe.getPropertyScene());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYSYNONYMS))
			 wordvalues.addElement("value").addText(pe.getPropertySynonyms());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYTEXT))
			 wordvalues.addElement("value").addText(pe.getPropertyText());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYUSE))
			 wordvalues.addElement("value").addText(pe.getPropertyUse());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYVERSION))
			 wordvalues.addElement("value").addText(pe.getPropertyVersion());
		 if( attrEnum.equals(WordAttributeEnum.PROPERTYWORDPROPERTY))
			 wordvalues.addElement("value").addText(pe.getPropertyWordProperty());
		 
	}
	/**
	 * 为属性添加资源
	 * @param property
	 * @param pe
	 */
	private void  addResouce4Pro(Element property,PropertyEntity pe){
		 Element resources= property.addElement("resources");
		 //查询单词所有的图片
		 List<WordResource> wrs= wordResourceService.findResourceByName(pe.getInstanceLabel(), 1);
		//图片
		 for( WordResource wr :wrs)
			 resources.addElement("resource").addAttribute("type", "imge").addText(wr.getSavepath());
		 //查询单词所有的绘本
		 List<WordResource> bookwrs= wordResourceService.findResourceByName(pe.getInstanceLabel(), 2);
		//绘本
		 for( WordResource wr :bookwrs)
			 resources.addElement("resource").addAttribute("type", "book").addText(wr.getSavepath());
		 //查询单词所有的音频
		 List<WordResource> voicewrs= wordResourceService.findResourceByName(pe.getInstanceLabel(), 3);
		//音频
		 for( WordResource wr :voicewrs)
			 resources.addElement("resource").addAttribute("type", "voice").addText(wr.getSavepath());
		 //查询单词所有的视频
		/* List<WordResource> videowrs= wordResourceService.findResourceByName(pe.getInstanceLabel(), 4);
		//视频
		 for( WordResource wr :videowrs)
			 resources.addElement("resource").addAttribute("type", "video").addText(wr.getSavepath());*/
		 
	 }
	/**
	 * 从本体库中得到主题下的所有单词
	 * @param theme  主题："17.旅游与交通-（58）交通运输方式"
	 * @return
	 */
	private  List<PropertyEntity> generate4ontology(String theme){

		List<PropertyEntity> listpe = new ArrayList<>();
		List<ResultSet> resultsAllBrother = ontologyManage.QueryBrotherIndividual("4",theme);
		for (int i = 0; i < resultsAllBrother.size(); i++) {
			if (resultsAllBrother.get(i).hasNext()) {
				while (resultsAllBrother.get(i).hasNext()) {
					QuerySolution solutionEachBrother = resultsAllBrother.get(i).next();
					PropertyEntity pe= PropertyEntity.generatePropertyEntity(solutionEachBrother);
					listpe.add(pe);
				}
			} else {
				System.out.println("知识本体库中没有此实例");
			}
		}
		return listpe;
	}
	
	public OntologyManage getOntologyManage() {
		return ontologyManage;
	}
	@Resource
	public void setOntologyManage(OntologyManage ontologyManage) {
		this.ontologyManage = ontologyManage;
	}
	
	@Override
	public void save(OffLineWordXml olx) {
		offLineDao.save(olx);
	}
	public OffLineDao getOffLineDao() {
		return offLineDao;
	}
	@Resource
	public void setOffLineDao(OffLineDao offLineDao) {
		this.offLineDao = offLineDao;
	}
	public WordResourceService getWordResourceService() {
		return wordResourceService;
	}
	@Resource
	public void setWordResourceService(WordResourceService wordResourceService) {
		this.wordResourceService = wordResourceService;
	}
	@Override
	public List<OffLineWordXml> findByThemeId(String themeId) {
		// TODO Auto-generated method stub.find(" o.themeId=? ",themeId);
		Object[] queryParams = {themeId};
		return offLineDao.getAllData(" o.themeId=? ", queryParams);
	}
	
	public OffLineWordXml findNewByThemeId(String themeId) {
		Object[] queryParams = {themeId};
		LinkedHashMap< String, String> orderby = new LinkedHashMap<>();
		orderby.put("createTime", "desc");
		List<OffLineWordXml> list =offLineDao.getAllData(" o.themeId=? ", queryParams, orderby);
		if( list!=null &&list.size()>0)
			return list.get(0);
		else
			return null;
	}
	@Override
	public org.springframework.core.io.Resource getFileResource(String savePath) {
		// TODO Auto-generated method stub
		return resourceLoader.getResource(savePath);
	}
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		// TODO Auto-generated method stub
		this.resourceLoader = resourceLoader;
	}
	@Override
	public void update(OffLineWordXml owx) {
		// TODO Auto-generated method stub
		offLineDao.update(owx);
	}
	
	/**
	 * 创建从离线包，从离线包和其主离线包的参数只有recommendGrade值是不同的。
	 * @param recommendGrade 推荐年级
	 * @param realGrade 实际年级
	 * @param themenumber 主题
	 * @return
	 * 返回离线包，离线包的内容是对主离线包的补充，而且补充的内容只有推荐年级的“情景段落”、“课文原句”、“视频”
	 * 主离线包的生成参数和从离线包的生成参数只有“recommendGrade”属性值不同。
	 * 比如：
	 * 第一次下载的离线包参数为:themenumber=2-17,realGrade=4,recommendGrade=3;这次会调用createOfflineBag方法生成主离线包。
	 * 第二次下载的离线包参数为:themenumber=2-17,realGrade=4,recommendGrade=3;这次会调用该方法生成从离线包。
	 * 从离线包中对于每一个主单词只会生成对应推荐年级的“情景段落”、“课文原句”以及对应难度的“视频”
	 * @throws ThemeWordNotExistException 
	 */
	public OffLineBag createSlaveOfflineBag(int recommendGrade, int realGrade, String themenumber) throws ThemeWordNotExistException{
		OffLineBag bag =createOfflineBag(recommendGrade, realGrade, themenumber, false);
		return bag;
	}
	
	@Override
	public OffLineBag createMasterOfflineBag( int realGrade, String themenumber) throws ThemeWordNotExistException {
		int recommendGrade = realGrade;   
		OffLineBag bag =createOfflineBag(recommendGrade, realGrade, themenumber, true);
		return bag;
	}
	
	
	private  OffLineBag createOfflineBag( int recommendGrade, int realGrade, String themenumber,boolean ismaster) throws ThemeWordNotExistException {
			//1.根据主题和推荐年级获得所有单词
			Hashtable<String,PropertyEntity> wordsMap=getMapPropertyEntity(realGrade,themenumber);
			if( wordsMap==null ||wordsMap.size()<=0){
				logger.info(themenumber+" 主题,"+realGrade+"年级,"+"没有单词!");
				throw new ThemeWordNotExistException(themenumber+" 主题"+" "+realGrade+"年级没有单词");
			}
			//3.开启多线程将对应单词的资源拷贝到压缩包文件中以及生成对应xml文件中的节点信息
			 //根据当前时间生成相对路径
			String reldir =FileTool.createRelDir();
			String parentdirpath =parentdirpath(reldir);
			String offLineBagname =offLineBagName(recommendGrade, realGrade, themenumber);
			File rootDir = offLineBagFileDir(parentdirpath, offLineBagname);
			//3.2要处理的主单词
			HashSet<PropertyEntity> listpes = new HashSet<>();
			
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
			OffLineBagResource offLineBagResource= new OffLineBagResource(listpes,rootDir,themenumber,realGrade, recommendGrade);
				//3.3开启4个线程
			taskExecutor.execute(new WordElementProducer(offLineBagResource,wordResService,ismaster));
			taskExecutor.execute(new WordElementProducer(offLineBagResource,wordResService,ismaster));
			taskExecutor.execute(new WordElementProducer(offLineBagResource,wordResService,ismaster));
			taskExecutor.execute(new WordElementProducer(offLineBagResource,wordResService,ismaster));
			
			//4.文件转移完，开始生成xml文件
			Document document =offLineBagResource.createDocument(rootDir, "words.xml");
			//5.压缩文件
				//压缩文件名
				String zipname = offLineBagname+".zip";
				//压缩后的文件
				File zipFile = new File(parentdirpath,zipname);
				//被压缩的文件
				List<File> zipfiles = new ArrayList<>();
				zipfiles.add(rootDir);
				//保存压缩文件记录
				OffLineBag bag  = null;
				try {
					System.out.println("开始压缩===========");
					ZipUtils.zipFiles(zipfiles, zipFile);
					bag= new OffLineBag(zipname, MobileStyleEnum.ANDROID, themenumber, recommendGrade, realGrade, "zip",offLineBagResource.getWordSum());
					bag.setSize(zipFile.length());
					bag.setSavePath(PropertyUtils.getFileSaveDir(PropertyUtils.THEME_OFFLINE_BAG)+"/"+reldir+"/"+zipname);
					bag.setVersion(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("压缩失败"+e.getMessage());
				}
				try{
	
					System.out.println("开启线程删除，压缩前的文件===========");
					//开启线程删除，压缩前的文件
					taskExecutor.execute(new DeleteFileTask(rootDir));
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("删除压缩前文件失败"+e.getMessage());
				}
				
				return bag;
	
	}
	/**
	 * 离线包的的父目录路径
	 * @return
	 */
	private String parentdirpath(String relativePath){
	    //3.1新建压缩包根目录
			String rootPath=PropertyUtils.getFileSaveAbsolutePath(PropertyUtils.THEME_OFFLINE_BAG);
			   //相对路径
			String rootdirpath = rootPath+"/"+relativePath;
			return rootdirpath;
	}
	
	/**
	 * 离线包的名称
	 * @param recommendGrade
	 * @param realGrade
	 * @param themenumber
	 * @return
	 * themenumber+"-"+realGrade+"-"+recommendGrade
	 */
	private String offLineBagName(int recommendGrade, int realGrade, String themenumber){
		String rootName = themenumber+"-"+realGrade+"-"+recommendGrade;
		return rootName;
	}
	/**
	 * 主题离线包文件
	 * @param parentdirpath 离线包所在父文件夹路径
	 * @param offLineBagname 离线包文件夹名称
	 * @return
	 */
	private File offLineBagFileDir(String parentdirpath,String offLineBagname){
		File rootDir = new File(parentdirpath+"/"+offLineBagname);
		if( !rootDir.exists()){
			rootDir.mkdirs();
		}
		return rootDir;
	}
	/**
	 * 根据主题和年级从本体中获取单词
	 * @param grade
	 * @param themenumber
	 * @return
	 */
	private Hashtable<String,PropertyEntity> getMapPropertyEntity(int grade, String themenumber){
		 //1.根据主题编号获得主题："17.旅游与交通-（58）交通运输方式"
		String themeContent =wordThemeDao.findByNumber(themenumber);
		//2.根据主题和推荐年级获得所有单词
		QueryWordFromOntology queryWord = new QueryWord4AndroidAdapter(ontologyManage);
		Hashtable<String,PropertyEntity> wordsMap=queryWord.queryWordByThemeAndGrade(grade, themeContent);
		return wordsMap;
	}
	
	public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }
    @Autowired
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
    
	public WordResService getWordResService() {
		return wordResService;
	}
	@Autowired
	public void setWordResService(WordResService wordResService) {
		this.wordResService = wordResService;
	}
	@Override
	public void downOffLineBag(String recommendGrade, String themenumber, int videoRecommendDegree) {
		
		
	}
	 
	
}
