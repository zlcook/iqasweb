package com.cnu.offline.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cnu.iqas.utils.FileTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.offline.MobileStyleEnum;
import com.cnu.offline.utils.OffLineBagUntils;

/**
* @author 周亮 
* @version 创建时间：2016年11月6日 下午6:40:30
* 类说明
*/
public class OffLineBagResource<W,E> {

	private static final Logger logger= LogManager.getLogger(OffLineBagResource.class);
	
	/**
	 * 主线程和子线程锁，当子线程没有将Word全部转换成wordNode时，主线程需要等待该锁。
	 */
	private final ReentrantLock masterSubThreadLock = new ReentrantLock();
	/**
	 * 获得指定lock对象对应的condition
	 */
	private final Condition mainSubThreadCondition = masterSubThreadLock.newCondition();
	
	/**
	 * 获取unit互斥锁
	 */
	private final ReentrantLock consumer_unitlock=new ReentrantLock();
	
	/**
	 * 主线程是否可以运行标志。
	 * 如果主线程可以运行则为true,否则该子线程运行false。主线程运行的条件是子线程运行完毕
	 */
	private boolean mainThreadRunflage=false;
	
	/**
	 * 存放生成所有wordNode对象
	 */
	private List<E> listWordNodes= new ArrayList<E>();
	/**
	 * 存放要消费的所有unit
	 */
	private List<W> units = new ArrayList<W>();
	/**
	 * 已经生成的wordNode数
	 */
	private int product_wordNode_size=0;
	/**
	 * 已经被消费的Unit数
	 */
	private int consumer_uint_size=0;
	/**
	 * 判断所有的unit对象是否被读取完，读取完后就需要在判断是否生成的wordNode数和unit数一致，一致则说明所有的unit都有对应的wordNode。
	 */
	private boolean unit2wordNodeisover=false;
	
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
	
	private final String reldir ;
	private final String parentdirpath ;
	private final String offLineBagname ;
	/**
	 * 离线资源包是主包还是从包
	 */
	private final boolean ismasterPack ;
	//WordNode中一个属性中多个值之间的分隔符
	public static final String WESPLITSTR="@";
	//ios端一个属性中多个值之间的分隔符
	public static final String IOS_WESPLITSTR="&";
	//Word中一个属性中多个值之间的分隔符
	public static final String PESPLITSTR="&";
	//压缩文件中各个资源的存放的文件夹名称
	public static final String PICTUREDIR="picture";
	public static final String AUDIODIR="audio";
	public static final String VIDEODIR="video";
	public static final String PRONUNCIATIONDIR="pronunciation";
	/**
	 * 离线包文件夹
	 */
	private File offlinebagDir;
	public OffLineBagResource(List<W> units ,String themenumber, int realGrade, int recommendGrade,MobileStyleEnum moblie) {
		super();
		if(units!=null)
			this.units = units;
		 //根据当前时间生成相对路径
		 reldir =FileTool.createRelDir();
		 parentdirpath =parentdirpath(reldir);
		 offLineBagname =OffLineBagUntils.createOffLineBagName(recommendGrade, realGrade, themenumber,moblie);
		 this.offlinebagDir = offLineBagFileDir(parentdirpath, offLineBagname);
	
		this.themenumber = themenumber;
		this.realGrade = realGrade;
		this.recommendGrade = recommendGrade;
		if( realGrade !=recommendGrade )
			ismasterPack=false;
		else
			ismasterPack=true;
	}
	
	
	
	/**
	 * 线程安全的添加WordElement方法
	 * @param we
	 */
	public void producerWordElement(E we){
		masterSubThreadLock.lock();
		try{
			listWordNodes.add(we);
			this.product_wordNode_size++;
			/**
			 * unit被读取完，并且WordNode生成的个数和unit消耗的个数相同说明，unit都被转化成WordNode了.
			 * 此时通知主线程开始根据WordNode生成xml文件
			 */
			if( unit2wordNodeisover && product_wordNode_size==consumer_uint_size){
				mainThreadRunflage=true;
				//唤醒主线程执行
				logger.info("唤醒主线程.........");
				mainSubThreadCondition.signalAll();
			}
		}finally{
			masterSubThreadLock.unlock();
		}
	}
	
	/**
	 * 线程安全，获得Unit
	 * @return
	 */
	public W getUnit(){
		consumer_unitlock.lock();
		try{
			Iterator<W> it=units.iterator();
			if(it.hasNext())
			{
				W pe=it.next();
				units.remove(pe);
				consumer_uint_size++;
				
				return pe;
			}else{
				unit2wordNodeisover = true;
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			consumer_unitlock.unlock();
		}
		return null;
	}
	/**
	 * 阻塞方法，直到子线程将Word转化成WordElement后，主线程开始根据WordElement生成xml文件
	 * @param rootDir  xml文件根目录
	 * @param xmlfileName xml文件名称
	 * @return xml文件对象，
	 */
	public Document createDocument(File rootDir,String xmlfileName,ICreateDocument<E> icreateDoc){

		masterSubThreadLock.lock();
		try{
			//使用while防止伪唤醒
			while( !mainThreadRunflage ){
				logger.info("等待创建xml中....");
				//主线程等待
				mainSubThreadCondition.await();
			}
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("主线程等待时出错."+e.getMessage());
		} finally{
			masterSubThreadLock.unlock();
		}
		logger.info("创建xml中....");
		try{
			//主线程执行
			//4.文件转移完，开始生成xml文件
			//4.1新建Xml
			Document document = DocumentHelper.createDocument();
			//填充数据
			icreateDoc.fillDocument(document,this.listWordNodes,themenumber,realGrade,recommendGrade);
			//4.往压缩文件夹中添加words.xml
			File xmlFile = new File(rootDir,xmlfileName);
			 try {
				 writeData2Xml(xmlFile,document);
				 logger.info("生成xml文件完毕----------------");
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
			/*OutputFormat format = OutputFormat.createPrettyPrint();
			Writer xmlwriter = new OutputStreamWriter(new FileOutputStream(xmlFile), "UTF-8");  //设置保存文件时使用的编码 = new FileWriterWithEncoding(xmlFile, "UTF-8");
			format.setEncoding("UTF-8");//文件内容的编码
			XMLWriter writer = new XMLWriter(xmlwriter,format);
			writer.write(document);
			writer.close();
			xmlwriter.close();*/
			
			/*查看源码发现下面代码效果和上面代码效果一样*/
			OutputStream out =  new FileOutputStream(xmlFile);
			OutputFormat format2 = OutputFormat.createPrettyPrint();
			format2.setEncoding("UTF-8");//文件内容的编码
			XMLWriter writer2 = new XMLWriter(out,format2);
			writer2.write(document);
			writer2.close();
			out.close();
			
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
	/**
	 * 是否将所有unit都转化成wordNode
	 * @return
	 */
	public boolean convertisover() {
		return unit2wordNodeisover;
	}
	/**
	 * 返回离线包中包含的单词总数
	 * @return
	 */
	public int getWordSum(){
		if(listWordNodes!=null )
			return listWordNodes.size();
		else
			return 0;
	}
	/**
	 * 离线包的的父目录绝对路径
	 * @return 如D://xxx/2016/02/03
	 */
	private String parentdirpath(String relativePath){
	    //3.1新建压缩包根目录
			String rootPath=PropertyUtils.getFileSaveAbsolutePath(PropertyUtils.THEME_OFFLINE_BAG);
			   //相对路径
			String rootdirpath = rootPath+"/"+relativePath;
			return rootdirpath;
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

	public String getParentdirpath() {
		return parentdirpath;
	}

	public String getOffLineBagname() {
		return offLineBagname;
	}

	public String getReldir() {
		return reldir;
	}



	public boolean isIsmasterPack() {
		return ismasterPack;
	}
	
}
