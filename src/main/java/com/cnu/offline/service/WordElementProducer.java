package com.cnu.offline.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cnu.iqas.bean.iword.WordRes;
import com.cnu.iqas.service.iword.WordResService;
import com.cnu.iqas.service.iword.impl.WordResServiceImpl;
import com.cnu.iqas.utils.FileTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.offline.OffLineBagResource;
import com.cnu.offline.WordElement;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年9月7日 下午9:12:27
* 类说明:
* 通过遍历listpes来生成WordElement对象。其中offLineBagResouce是共享资源，生成的WordElement会存在offLineBagResouce里，
* WordElementConsumer对象会从offLineBagResouce里消耗WordElement。
*/
public class WordElementProducer implements Runnable {

	private static final Logger logger= LogManager.getLogger(WordElementProducer.class);

	//和WordElementConsumer消费者共享的资源
	private OffLineBagResource  offLineBagResource;
	/**
	 * 单词资源查询服务类
	 */
	private WordResService wordResService ;
	/**
	 * 离线包文件夹
	 */
	private File offlinebagDir;
	/**
	 * 实际年级
	 */
	private int realGrade;
	/**
	 * 推荐年级
	 */
	private int recommendGrade;
	
	private String themenumber;
	/**
	 * 生成的wordElement是为主离线包准备的是还为从离线包准备的
	 * true:为主离线包准备的
	 * false:为从离线包准备的,那么wordElement中只有"propertyScene"、"propertyText"、"video"三个属性有值
	 */
	private boolean master;
	/**
	 * 默认为主离线包准备的
	 * @param offLineBagResource
	 * @param wordResService
	 */
	public WordElementProducer(OffLineBagResource  offLineBagResource,WordResService wordResService) {
		super();
		this.offLineBagResource = offLineBagResource;
		this.offlinebagDir = offLineBagResource.getOfflinebagDir();
		this.realGrade = offLineBagResource.getRealGrade();
		this.recommendGrade = offLineBagResource.getRecommendGrade();
		this.wordResService = wordResService;
		this.themenumber = offLineBagResource.getThemenumber();
		this.master = true;
	}
	/**
	 * 
	 * @param offLineBagResource
	 * @param wordResService
	 * @param ismaster  false：为从离线包准备的
	 *  生成的wordElement是为主离线包准备的是还为从离线包准备的
	 * true:为主离线包准备的
	 * false:为从离线包准备的,那么wordElement中只有"propertyScene"、"propertyText"、"video"三个属性有值
	 */
	public WordElementProducer(OffLineBagResource  offLineBagResource,WordResService wordResService,boolean ismaster) {
		super();
		this.offLineBagResource = offLineBagResource;
		this.offlinebagDir = offLineBagResource.getOfflinebagDir();
		this.realGrade = offLineBagResource.getRealGrade();
		this.recommendGrade = offLineBagResource.getRecommendGrade();
		this.wordResService = wordResService;
		this.master= ismaster;
	}

	@Override
	public void run() {
		
		if(master){
			masterPackProducer();
		}else{
			slavePackProducer();
		}
		
	}
	private void slavePackProducer() {

		System.out.println(Thread.currentThread().getName()+"：开始");
		while(!offLineBagResource.isover()){

		  PropertyEntity pe=offLineBagResource.getPropertyEntity();

		  System.out.println(Thread.currentThread().getName()+"：获取单词："+pe);
		  if( pe!=null){
			String word = pe.getInstanceLabel();
			WordElement we =new WordElement(word,themenumber,realGrade);
			//生成"propertyScene"、"propertyText"、2个属性
			WordRes wr =wordResService.find(word);
			//1.生成情景段落
			WordElement.Property qjdlproperty = new WordElement.Property("propertyScene");
			//1.2推荐年级的情景段落
			try {
					WordElement.Property.Pro qjdlrecoGpro = getPro(wr,false,recommendGrade);
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
					WordElement.Property.Pro kwyjrecoGpro = getPro(wr,true,recommendGrade);
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
					   value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath1());
					   videoproperty.setDifficulty("1");
					}
				}else if(realGrade<recommendGrade ){
					if(wr.getVideoPath3()!=null && !wr.getVideoPath3().equals("")){
					  value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath3());
					  videoproperty.setDifficulty("3");
					}
				}else{
						if(wr.getVideoPath2()!=null && !wr.getVideoPath2().equals("")){
							value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath2());
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
	private void masterPackProducer() {

		//logger.info(Thread.currentThread().getName()+"：开始");
		while(!offLineBagResource.isover()){

		  PropertyEntity pe=offLineBagResource.getPropertyEntity();

		 // logger.info(Thread.currentThread().getName()+"：获取单词："+pe);
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
				if( realGrade>recommendGrade ){
					if(wr.getVideoPath1()!=null && !wr.getVideoPath1().equals("")){
					   value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath1());
					   videoproperty.setDifficulty("1");
					}
				}else if(realGrade<recommendGrade ){
					if(wr.getVideoPath3()!=null && !wr.getVideoPath3().equals("")){
					  value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath3());
					  videoproperty.setDifficulty("3");
					}
				}else{
						if(wr.getVideoPath2()!=null && !wr.getVideoPath2().equals("")){
							value=copyfile2offliebag(offlinebagDir,OffLineBagResource.VIDEODIR,wr.getVideoPath2());
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
	private WordElement.Property.Pro getPro(WordRes wr,boolean flage,int grade)throws Exception{
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
}
