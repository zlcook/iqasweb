package com.cnu.offline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cnu.offline.WordElement.Property;
import com.cnu.offline.WordElement.Property.Pro;
import com.cnu.offline.service.impl.OfflineServiceImpl;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年9月9日 下午1:53:55
* 类说明 压缩包资源
*/
public class OffLineBagResource {


	private static final Logger logger= LogManager.getLogger(OffLineBagResource.class);
	
	/**
	 * 主线程和子线程锁，当子线程没有将PropertyEntity全部转换成wordElement时，主线程需要等待该锁。
	 */
	private final ReentrantLock mainSubLock = new ReentrantLock();
	//获得指定lock对象对应的condition
	private final Condition mainSubCond = mainSubLock.newCondition();
	
	//共享HashSet<PropertyEntity>的锁
	private final ReentrantLock pelock=new ReentrantLock();
	
	//如果主线程可以运行则为true,否则该子线程运行false。主线程运行的条件是子线程将所有的
	private boolean mainflage=false;
	
	/**
	 * wordElement资源
	 */
	private List<WordElement> listWordElements= new ArrayList<>();
	/**
	 * 本体库中word
	 */
	private HashSet<PropertyEntity> setpes = new HashSet<>();
	/**
	 * 实际生成的WordElement数
	 */
	private int wesize=0;
	//需要消费的PropertyEntity数
	private int pesize=0;
	/**
	 * 用于判读setpes资源是否被读取完
	 */
	private boolean peisover=false;
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
	/**
	 * 主题号
	 */
	private String themenumber;
	
	public OffLineBagResource(HashSet<PropertyEntity> setpes ,File offlinebagDir,String themenumber, int realGrade, int recommendGrade) {
		super();
		if(setpes!=null)
			this.setpes = setpes;
		this.offlinebagDir = offlinebagDir;
		this.themenumber = themenumber;
		this.realGrade = realGrade;
		this.recommendGrade = recommendGrade;
	}
	//WordElement中一个属性中多个值之间的分隔符
	public static final String WESPLITSTR="&amp;";
	//PropertyEntity中一个属性中多个值之间的分隔符
	public static final String PESPLITSTR="&";
	//压缩文件中各个资源的存放的文件夹名称
	public static final String PICTUREDIR="picture";
	public static final String AUDIODIR="audio";
	public static final String VIDEODIR="video";
	public static final String PRONUNCIATIONDIR="pronunciation";
	
	/**
	 * 线程安全的添加WordElement方法
	 * @param we
	 */
	public void producerWordElement(WordElement we){
		mainSubLock.lock();
		try{
			listWordElements.add(we);
			this.wesize++;
			System.out.println("生成第"+this.wesize+"个："+we.getName());

			/**
			 * pe被读取完，并且WordElement生成的个数和PropertyEntity消耗的个数相同说明，PropertyEntity都被转化成WordElemtnt了.
			 * 此时通知主线程开始根据WordElement生成xml文件
			 */
			if( peisover && wesize==pesize){
				mainflage=true;
				//唤醒主线程执行
				mainSubCond.signalAll();
			}
			
		}finally{
			mainSubLock.unlock();
		}
	}
	
	/**
	 * 线程安全，获得PropertyEntity
	 * @return
	 */
	public PropertyEntity getPropertyEntity(){
		pelock.lock();
		try{
			Iterator<PropertyEntity> it=setpes.iterator();
			if(it.hasNext())
			{
				PropertyEntity pe=it.next();
				setpes.remove(pe);
				pesize++;
				System.out.println("消费第"+this.pesize+"个："+pe.getInstanceLabel());
				//if( !it.hasNext() )
				//	isover=true;
				return pe;
			}else{
				peisover = true;
				return null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pelock.unlock();
		}
		return null;
	}
	/**
	 * 阻塞方法，直到子线程将PropertyEntity转化成WordElement后，主线程开始根据WordElement生成xml文件
	 * @param rootDir  xml文件根目录
	 * @param xmlfileName xml文件名称
	 * @return xml文件对象，
	 */
	public Document createDocument(File rootDir,String xmlfileName){
		mainSubLock.lock();
		try{
			//使用while防止伪唤醒
			while( !mainflage ){

				System.out.println("主线程等待----------------");
				//主线程等待
				mainSubCond.await();
			}
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("主线程等待时出错."+e.getMessage());
		} finally{
			mainSubLock.unlock();
		}

		try{
			System.out.println("主线程被唤醒开始生成xml文件----------------");
			//主线程执行
			//4.文件转移完，开始生成xml文件
			//4.1新建Xml
			Document document = DocumentHelper.createDocument();
			//填充数据
			fillDocument(document,this.listWordElements);
			//4.往压缩文件夹中添加words.xml
			File xmlFile = new File(rootDir,xmlfileName);
			 try {
				 writeData2Xml(xmlFile,document);
					System.out.println("生成xml文件完毕----------------");
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("将数据写入xml文件时出错"+e.getMessage());
			}
			return document;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 将document数据写入file文件中
	 * @param xmlFile
	 * @param document
	 * @throws IOException 
	 */
	private void writeData2Xml(File xmlFile,Document document) throws IOException{
		 
			OutputFormat format = OutputFormat.createPrettyPrint();
			Writer xmlwriter = new FileWriter(xmlFile);
			XMLWriter writer = new XMLWriter(xmlwriter,format);
			writer.write(document);
			writer.close();
			xmlwriter.close();
		
	}
	/**
	 * 将listWes内容填充到document中
	 * @param document xml文档
	 * @param listWes  填充到xml文档中的内容
	 */
	private void fillDocument(Document document,List<WordElement> listWes){
		 Element root = document.addElement( "words" );

		  int count =0;
		  if(listWes!=null)
			for( WordElement we : listWes){
				System.out.println("填充单词"+(++count)+"："+we.getName());
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
	
	public String getThemenumber() {
		return themenumber;
	}
	
	public File getOfflinebagDir() {
		return offlinebagDir;
	}
	public int getRealGrade() {
		return realGrade;
	}
	public int getRecommendGrade() {
		return recommendGrade;
	}
	public boolean isover() {
		return peisover;
	}
	/**
	 * 返回离线包中包含的单词总数
	 * @return
	 */
	public int getWordSum(){
		if(listWordElements!=null )
			return listWordElements.size();
		else
			return 0;
	}
}
