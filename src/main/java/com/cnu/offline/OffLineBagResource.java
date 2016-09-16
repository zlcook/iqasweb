package com.cnu.offline;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年9月9日 下午1:53:55
* 类说明 压缩包资源
*/
public class OffLineBagResource {

	//定义锁对象
	private final ReentrantLock lock = new ReentrantLock();
	//获得指定lock对象对应的condition
	private final Condition cond = lock.newCondition();
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
	//如果有资源则为true,没有资源为false
	private boolean flage=false;
	/**
	 * 用于判读setpes资源是否被读取完
	 */
	private boolean isover=false;
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
		lock.lock();
		try{
			listWordElements.add(we);
			flage = true;
			this.wesize++;

			System.out.println("生成第"+this.wesize+"个："+we.getName());
			cond.signalAll();//唤醒消费者线程
		}finally{
			lock.unlock();
		}
	}
	/**
	 * 线程安全，获得WordElement
	 * @return
	 */
	public WordElement consumerWordElement(){
		lock.lock();
		try{
			if( flage )
			{
				if(listWordElements.size()<=0 ){
					flage = false;
					cond.await();
				}else{
					return listWordElements.remove(0);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		return null;
		
	}
	
	/**
	 * 线程安全，获得PropertyEntity
	 * @return
	 */
	public PropertyEntity getPropertyEntity(){
		lock.lock();
		try{
			Iterator<PropertyEntity> it=setpes.iterator();
			if(it.hasNext())
			{
				PropertyEntity pe=it.next();
				setpes.remove(pe);
				pesize++;

				System.out.println("消费第"+this.pesize+"个："+pe.getInstanceLabel());
				if( !it.hasNext() )
					isover=true;
				return pe;
			}else{
				isover = true;
				return null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		return null;
	}
	
	public  boolean doover(){
		while(true){
			lock.lock();
			try{
				//System.out.println("isover:"+isover+" pesize:"+pesize+" wesize:"+wesize);
				if( isover && pesize==wesize){
					return true;
				}
			}finally{
				lock.unlock();
			}
		}
	}
	
	
	public String getThemenumber() {
		return themenumber;
	}
	public List<WordElement> getListWordElements() {
		return listWordElements;
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
		return isover;
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
