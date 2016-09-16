package com.cnu.offline.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.offline.OffLineBagTest;
import com.cnu.iqas.service.iword.WordThemeService;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.offline.ThemeWordNotExistException;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.bean.OffLineWordXml;
import com.cnu.offline.service.OffLineBagService;
import com.cnu.offline.service.OfflineService;

/**
* @author 周亮 
* @version 创建时间：2016年7月12日 下午3:58:03
* 类说明
*/
@Controller
@RequestMapping(value="mobile/offline/")
public class OffLineController implements ResourceLoaderAware{
	private static final Logger logger= LogManager.getLogger(OffLineController.class);
	//资源加载器
	private ResourceLoader resourceLoader;
	
	//主题服务类
	private WordThemeService wordThemeService; 
	private OfflineService offlineService;
	private OffLineBagService offLineBagService;
	
	
	
	
	@RequestMapping(value="down")
	public void  downOffline(String number,HttpServletResponse response){
		
		WordTheme theme =wordThemeService.findByNumber(number);
		if(theme!=null){
			OffLineWordXml owx= offlineService.findNewByThemeId(theme.getId());
			if( owx!=null){
				//1.获取文件系统的根路径:D:/Soft/nsystem/
				String fileSystemRoot =PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR);
				//2.生成文件的绝对路径:D:/Soft/system/news/files/报名.doc
				String fileSavePath = fileSystemRoot+owx.getSavePath();
				org.springframework.core.io.Resource fileResource =offlineService.getFileResource("file:"+fileSavePath);
				BaseForm.loadFile(response, fileResource,true);
				owx.setCount(owx.getCount()+1);
				offlineService.update(owx);
			}
		}
	}
	
	/**
	 * 
	 * 根据主题、实际年级、推荐年级下载离线包
	 * @param realGrade  实际年级
	 * @param recommendGrade  推荐年级
	 * @param themenumber "2-17"
	 * @param request
	 * http://localhost:8080/iqasweb/mobile/offline/offlinebag.html?realGrade=4&recommendGrade=5&themenumber=2-17&token=1472719089982@liang
	 */
	@RequestMapping(value="offlinebag")
	public void downOfflineBag(int realGrade,int recommendGrade,String themenumber,HttpServletRequest request,HttpServletResponse response){
		
		//取出用户
		//User user =(User) request.getSession().getAttribute("user");
		OffLineBag bag =offLineBagService.find(themenumber, recommendGrade, realGrade);
		if( bag ==null ){
			//离线包不存在，则先生成离线包
			try {
				//判断下载的离线包是主离线包还是从离线包
				boolean existmaster =offLineBagService.existMasterBag(themenumber,realGrade);
				//主离线包存在
				if( existmaster ){
					//下载从离线包
					bag=offlineService.createSlaveOfflineBag(recommendGrade, realGrade, themenumber);
				}else{
					//下载主离线包
					bag=offlineService.createOfflineBag(recommendGrade, realGrade, themenumber);
				}
				offLineBagService.add(bag);
			} catch (ThemeWordNotExistException e) {
				e.printStackTrace();
				logger.error("本体库内容还在完善中。"+e.getMessage());
			}
		}
		if( bag!=null){
			bag.setDownsize(bag.getDownsize()+1);
			offLineBagService.update(bag);
			//下载
			String savePath =bag.getSavePath();
			String fileSavePath=PropertyUtils.appendFileSystemDir(savePath);
			//2.1获取文件资源
			Resource fileResource =resourceLoader.getResource("file:"+fileSavePath);
			BaseForm.loadFile(response, fileResource);
		}
	}
	
	public WordThemeService getWordThemeService() {
		return wordThemeService;
	}
	@Autowired
	public void setWordThemeService(WordThemeService wordThemeService) {
		this.wordThemeService = wordThemeService;
	}
	public OfflineService getOfflineService() {
		return offlineService;
	}
	@Autowired
	public void setOfflineService(OfflineService offlineService) {
		this.offlineService = offlineService;
	}

	public OffLineBagService getOffLineBagService() {
		return offLineBagService;
	}
	@Autowired
	public void setOffLineBagService(OffLineBagService offLineBagService) {
		this.offLineBagService = offLineBagService;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		// TODO Auto-generated method stub
		this.resourceLoader = resourceLoader;
	}
	
}
