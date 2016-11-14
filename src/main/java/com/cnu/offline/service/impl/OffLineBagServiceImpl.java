package com.cnu.offline.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.offline.DeleteFileTask;
import com.cnu.offline.MobileStyleEnum;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.dao.OffLineBagDao;
import com.cnu.offline.exception.ThemeWordNotExistException;
import com.cnu.offline.service.ConvertTask;
import com.cnu.offline.service.OffLineAdapter;
import com.cnu.offline.service.OffLineBagResource;
import com.cnu.offline.service.OffLineBagService;
import com.cnu.offline.utils.OffLineBagUntils;
import com.cnu.offline.utils.ZipUtils;

/**
* @author 周亮 
* @version 创建时间：2016年11月6日 下午6:01:05
* 类说明
* 离线包服务类
*/
@Service("offLineBagService")
public class OffLineBagServiceImpl implements OffLineBagService {

	private static final Logger logger= LogManager.getLogger(OffLineBagServiceImpl.class);
	//线程池
    private ThreadPoolTaskExecutor taskExecutor;
	private OffLineBagDao offLineBagDao;
	//资源加载器
	private ResourceLoader resourceLoader;
	
	public OffLineBag find(String themenumber,int recommendGrade,int realGrade,MobileStyleEnum mobile){
		String offlineBagId = OffLineBagUntils.createOffLineBagId(recommendGrade, realGrade, themenumber, mobile);
		return offLineBagDao.find(offlineBagId);
	}

	public void add(OffLineBag offLineBag){
		offLineBagDao.save(offLineBag);
	}
	
	@Override
	public void update(OffLineBag offLineBag) {
		offLineBagDao.update(offLineBag);
	}

	@Override
	public boolean existMasterBag(String themenumber, int realGrade) {
		return offLineBagDao.existMasterBag(themenumber, realGrade);
	}

	@Override
	public List<OffLineBag> listMaster(String themenumber) {
		if( themenumber ==null || themenumber.equals(""))
			return null;
		return offLineBagDao.getAllData("o.themenumber =?", new Object[]{themenumber});
	}

	@Override
	public OffLineBag find(String id) {
		// TODO Auto-generated method stub
		return offLineBagDao.find(id);
	}

	@Override
	public org.springframework.core.io.Resource getFileResource(String savePath) {
		// TODO Auto-generated method stub
		return resourceLoader.getResource(savePath);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		// TODO Auto-generated method stub
		this.resourceLoader =resourceLoader;
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
	@Override
	public OffLineBag createSlaveOfflineBag( int recommendGrade, int realGrade, String themenumber,OffLineAdapter adapter) throws ThemeWordNotExistException{
		OffLineBag bag =createOfflineBag(recommendGrade, realGrade, themenumber,adapter);
		return bag;
	}
	
	@Override
	public OffLineBag createMasterOfflineBag(int realGrade, String themenumber,OffLineAdapter adapter) throws ThemeWordNotExistException {
		int recommendGrade = realGrade;   
		OffLineBag bag =createOfflineBag(recommendGrade, realGrade, themenumber,adapter);
		return bag;
	}
	private  OffLineBag createOfflineBag(final  int recommendGrade,final  int realGrade,final  String themenumber,OffLineAdapter adapter) throws ThemeWordNotExistException {
			//1.获取要生成的所有资源
			OffLineBagResource iosOfflineResource=adapter.createOffLineBagResource(themenumber, realGrade, recommendGrade);
			//3.3开启4个线程
			taskExecutor.execute(new ConvertTask(iosOfflineResource,adapter));
			taskExecutor.execute(new ConvertTask(iosOfflineResource,adapter));
			taskExecutor.execute(new ConvertTask(iosOfflineResource,adapter));
		
			String reldir = iosOfflineResource.getReldir();
			String parentdirpath =iosOfflineResource.getParentdirpath();
			String offLineBagname =iosOfflineResource.getOffLineBagname();
			File rootDir = iosOfflineResource.getOfflinebagDir();
			//4.文件转移完，开始生成xml文件
			Document document =iosOfflineResource.createDocument(rootDir, "words.xml",adapter);
			//5.压缩文件
				//压缩文件名
				String zipname = offLineBagname+".zip";
				//压缩后的文件
				File zipFile = new File(parentdirpath,zipname);
				//保存压缩文件记录
				OffLineBag bag  = null;
				try {
					logger.info("开始压缩文件:"+rootDir.getAbsolutePath());
					
					ZipUtils.zip(rootDir.getAbsolutePath(), zipFile.getAbsolutePath());
					bag= new OffLineBag(zipname, adapter.getMobileStyleEnum(), themenumber, recommendGrade, realGrade, "zip",iosOfflineResource.getWordSum());
					bag.setSize(zipFile.length());
					bag.setSavePath(PropertyUtils.getFileSaveDir(PropertyUtils.THEME_OFFLINE_BAG)+"/"+reldir+"/"+zipname);
					bag.setVersion(1);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("压缩失败"+e.getMessage());
				}
				try{
					logger.info("开启线程删除文件："+rootDir.getAbsolutePath());
					//开启线程删除，压缩前的文件
					taskExecutor.execute(new DeleteFileTask(rootDir));
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("删除压缩前文件失败"+e.getMessage());
				}
				return bag;
	}
	

	@Transactional
	@Override
	public void downOffLineBag(String bagId, HttpServletRequest request, HttpServletResponse response) {
		//取出用户
		try {
			User user =(User) request.getSession().getAttribute("user");
			String ip=request.getRemoteAddr();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
			logger.info(sdf.format(new Date())+":"+user.getUserName()+"用户来自:"+ip);
			OffLineBag bag =offLineBagDao.find(bagId);

			if( bag!=null){
				
				logger.info("下载:"+bag.getName());
				
				bag.setDownsize(bag.getDownsize()+1);
				offLineBagDao.update(bag);
				//下载
				String savePath =bag.getSavePath();
				String fileSavePath=PropertyUtils.appendFileSystemDir(savePath);
				//2.1获取文件资源
				Resource fileResource =resourceLoader.getResource("file:"+fileSavePath);
				BaseForm.loadFile(response, fileResource);
				
				logger.info(sdf.format(new Date())+":"+user.getUserName()+"用户来自:"+ip+":下载:【"+bag.getName()+"】完毕");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	@Autowired
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public OffLineBagDao getOffLineBagDao() {
		return offLineBagDao;
	}
	@Autowired
	public void setOffLineBagDao(OffLineBagDao offLineBagDao) {
		this.offLineBagDao = offLineBagDao;
	}

}
