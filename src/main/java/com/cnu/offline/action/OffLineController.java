package com.cnu.offline.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.service.iword.WordThemeService;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.offline.WordElement;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.bean.OffLineWordXml;
import com.cnu.offline.exception.ThemeWordNotExistException;
import com.cnu.offline.service.OffLineAdapter;
import com.cnu.offline.service.OffLineBagService;
import com.cnu.offline.service.OfflineService;
import com.cnu.offline.xml.Unit;
import com.cnu.offline.xml.WordNode;
import com.noumenon.entity.PropertyEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

	private OffLineAdapter<Unit,WordNode> iosadapter;
	private OffLineAdapter<PropertyEntity,WordElement> androidadapter;
	
	
	
	@Deprecated
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
	 * 根据主题、实际年级、推荐年级创建离线包
	 * @param realGrade  实际年级
	 * @param recommendGrade  推荐年级
	 * @param themenumber "2-17"
	 * @param mobile   可以为ios和android端创建离线包，选项： andorid或者ios
	 * http://localhost:8080/iqasweb/mobile/offline/createofflienbag.html?mobile=ios&realGrade=4&recommendGrade=5&themenumber=2-17&token=1472719089982@liang
	 */
	@RequestMapping(value="createofflienbag")
	public ModelAndView createofflienbag(@RequestParam(required=true)int realGrade,@RequestParam(required=true)int recommendGrade,@RequestParam(required=true)String themenumber,@RequestParam(required=true)String mobile){
		ModelAndView mv =null;
		
		if(mobile!=null && mobile.equals("ios")){
			mv =createofflienbag(realGrade, recommendGrade, themenumber, iosadapter);
		}else if(mobile!=null && mobile.equals("android")){
			mv =createofflienbag(realGrade, recommendGrade, themenumber, androidadapter);
		}else{
			 mv = new ModelAndView(PageViewConstant.MESSAGE);
			 mv.addObject("message", "请填写mobile参数，ios或者 android");
		}
		
		return mv;
		
	}
	/**
	 * 
	 * 根据主题、实际年级、推荐年级创建离线包
	 * @param realGrade  实际年级
	 * @param recommendGrade  推荐年级
	 * @param themenumber "2-12"
	 * @param adapter  适配器
	 * http://localhost:8080/iqasweb/mobile/offline/produceofflienbag.html?realGrade=4&recommendGrade=5&themenumber=2-17&token=1472719089982@liang
	 */
	public ModelAndView createofflienbag(int realGrade,int recommendGrade,String themenumber,OffLineAdapter adapter){

		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		String message = "创建成功";
		
		//推荐年级减去实际年级的绝对值要不大于1
		int range= realGrade-recommendGrade;
		if( range>1 || range<-1){
			 message = "推荐年级只能比实际年级大一个年级或低一个年级";
			 mv.addObject("message", message);
			 return mv;
		}
		try {
			
			if( recommendGrade!=realGrade){//从离线包
				//创建从离线包
				OffLineBag bag=offLineBagService.createSlaveOfflineBag(recommendGrade, realGrade, themenumber,adapter);
				if( bag!=null)
					offLineBagService.saveOrUpdate(bag);
			}else{//主离线包
				OffLineBag masterbag =offLineBagService.createMasterOfflineBag(realGrade, themenumber,adapter);
				if(masterbag!=null)
				 offLineBagService.saveOrUpdate(masterbag);
			}
		} catch (ThemeWordNotExistException e) {
			e.printStackTrace();
			message = "本体库内容还在完善中。"+e.getMessage();
			logger.error(message);
			
		}catch(Exception e1){
			e1.printStackTrace();
			message ="生成离线包失败："+ e1.getMessage();
			logger.error(message);
		}
		
		mv.addObject("message", message);
		return mv;
	}
	
	/**
	 * for android
	 * 获取离线包记录
	 * @param realGrade
	 * @param recommendGrade
	 * @param themenumber
	 * @param includMaster  是否包含主包
	 * @return
	 * {
	 *  status:"1",
	 *  message:"ok",
	 *  data:[
	 *  {OffLineBag的json数据 }, 如果返回两个，则第二个为主离线包的信息，第一个为从离线包信息
	 *  {OffLineBag的json数据 }
	 *  ]
	 * }
	 * http://localhost:8080/iqasweb/mobile/offline/getOffLineBag.html?realGrade=4&recommendGrade=5&themenumber=2-17&token=1472719089982@liang
	 
	 */
	@RequestMapping(value="getOffLineBag")
	public ModelAndView getOffLineBagDownUrl4android(@RequestParam(required=true)int realGrade,@RequestParam(required=true)int recommendGrade,@RequestParam(required=true)String themenumber,@RequestParam(required=true)boolean includeMaster){
		
			ModelAndView mv = getOffLineBagDownUrl(realGrade, recommendGrade, themenumber, includeMaster, androidadapter);
			return mv;
	}
	/**
	 * for ios
	 * 获取离线包记录
	 * @param realGrade
	 * @param recommendGrade
	 * @param themenumber
	 * @param includMaster  是否包含主包
	 * @return
	 * {
	 *  status:"1",
	 *  message:"ok",
	 *  data:[
	 *  {OffLineBag的json数据 }, 如果返回两个，则第二个为主离线包的信息，第一个为从离线包信息
	 *  {OffLineBag的json数据 }
	 *  ]
	 * }
	 * http://localhost:8080/iqasweb/mobile/offline/getOffLineBag.html?realGrade=4&recommendGrade=5&themenumber=2-17&token=1472719089982@liang
	 
	 */
	@RequestMapping(value="getOffLineBag4ios")
	public ModelAndView getOffLineBagDownUrl4ios(@RequestParam(required=true)int realGrade,@RequestParam(required=true)int recommendGrade,@RequestParam(required=true)String themenumber,@RequestParam(required=true)boolean includeMaster){
			ModelAndView mv = getOffLineBagDownUrl(realGrade, recommendGrade, themenumber, includeMaster, iosadapter);
			return mv;
	}
	/**
	 * 获取离线包记录
	 * @param realGrade
	 * @param recommendGrade
	 * @param themenumber
	 * @param includMaster
	 * @param adapter
	 * @return
	 * {
	 *  status:"1",
	 *  message:"ok",
	 *  data:[
	 *  {OffLineBag的json数据 }, 如果返回两个，则第二个为主离线包的信息，第一个为从离线包信息
	 *  {OffLineBag的json数据 }
	 *  ]
	 * }
	 * http://localhost:8080/iqasweb/mobile/offline/getOffLineBag.html?realGrade=4&recommendGrade=5&themenumber=2-17&token=1472719089982@liang
	 
	 */
	public ModelAndView getOffLineBagDownUrl(int realGrade,int recommendGrade,String themenumber,boolean includeMaster,OffLineAdapter adapter){
		//要下载的离线包都存在标志
	 	boolean pack_ok = true;
	 	JSONArray jsonOffline= new JSONArray();
		MyStatus status = new MyStatus();
	    //1.判断要下载的离线包是否存在
		OffLineBag bag =offLineBagService.find(themenumber, recommendGrade, realGrade,adapter.getMobileStyleEnum());
		if(bag!=null){
			 
			 JSONObject subJson = new JSONObject();
			 copy2Json(subJson,bag);
			 jsonOffline.add(subJson);
			//是否需要下载主离线包
			OffLineBag master =null;
			if(includeMaster){
				master = offLineBagService.find(themenumber, realGrade, realGrade,adapter.getMobileStyleEnum());
				if( master!=null){
					 JSONObject masterJson = new JSONObject();
					 copy2Json(masterJson,master);
					 jsonOffline.add(masterJson);
				}else{
					status.setStatus(StatusConstant.OFFLINEBAG_NO_EXIST);
					logger.info("主题编号："+themenumber+",实际年级为："+ realGrade+" 的主离线包不存在!");
					pack_ok = false;
				}
			}
		}else{
			status.setStatus(StatusConstant.OFFLINEBAG_NO_EXIST);
			logger.info("主题编号："+themenumber+",实际年级为："+ realGrade+",推荐年级为："+recommendGrade+" 的从离线包不存在!");
			pack_ok = false;
		}
		
		JSONObject json = new JSONObject();
		JsonTool.putStatusJson(status, json);
		if( pack_ok){
			//返回离线包信息
			json.put("data", jsonOffline);
		}
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		mv.addObject("json", json.toString());
		return mv;
}
	/**
  	 * @param id  
	 * @param response
	 *  http://localhost:8080/iqasweb/mobile/offline/downofflinebag.html?id=12364&token=1472719089982@liang
	 */
	@RequestMapping(value="downofflinebag")
	public void downOfflineBag(String id,HttpServletRequest request,HttpServletResponse response){
		if( id!=null && !id.equals(""))
			offLineBagService.downOffLineBag(id, request, response);
		
	}
	

	
	private void copy2Json(JSONObject json,OffLineBag bag){
		json.put("id", bag.getId());
		String name =bag.getName();
		name =name.substring(0, name.lastIndexOf("."));
		json.put("name", name);
		json.put("downUrl", "mobile/offline/downofflinebag.html");
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

	public OffLineAdapter<Unit, WordNode> getIosadapter() {
		return iosadapter;
	}
	@Autowired
	public void setIosadapter(OffLineAdapter<Unit, WordNode> iosadapter) {
		this.iosadapter = iosadapter;
	}

	public OffLineAdapter<PropertyEntity, WordElement> getAndroidadapter() {
		return androidadapter;
	}
	@Autowired
	public void setAndroidadapter(OffLineAdapter<PropertyEntity, WordElement> androidadapter) {
		this.androidadapter = androidadapter;
	}
	
}
