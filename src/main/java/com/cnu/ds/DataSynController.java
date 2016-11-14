package com.cnu.ds;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.ds.parse.UserBehaviourParseCallback;
import com.cnu.ds.parse.UserResourceParseCallback;
import com.cnu.ds.parse.UserWordParseCallback;
import com.cnu.ds.service.impl.AndroidDataSynService;
import com.cnu.ds.service.impl.ParseXmlService;
import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.constant.StatusConstant;
import com.user.entity.UserBehaviour;
import com.user.entity.UserResource;
import com.user.entity.UserWord;

import net.sf.json.JSONObject;

/**
* @author 周亮 
* @version 创建时间：2016年10月25日 上午11:24:22
* 类说明：数据同步控制类
*/

@Controller
@RequestMapping(value="mobile/ds/")
public class DataSynController {
	
	private final Logger loger = LogManager.getLogger(DataSynController.class);
	
	private ParseXmlService parseXmlService;
	
	private AndroidDataSynService androidDataSynService;
	/**
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="receive")
	public ModelAndView receive(Model model,HttpServletRequest request){
		
		System.out.println(request.getRemoteAddr()+"同步数据");
		 List<UserWord> table_userWord=null;
		 List<UserBehaviour> table_userbehaviour=null;
		 List<UserResource> table_userresource=null;
		 boolean  parseflage = false;
	  try {
			SAXReader reader = new SAXReader();
	        Document document = reader.read(request.getInputStream());
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	        //解析xml
	        //解析user_word表数据
	        table_userWord = parseXmlService.parse(new UserWordParseCallback(sdf), document);
	        //解析user_behaviour表数据
	        table_userbehaviour = parseXmlService.parse(new UserBehaviourParseCallback(sdf),document);
	         //解析user_resource表数据
	        table_userresource = parseXmlService.parse(new UserResourceParseCallback(sdf),document);
	        
	        parseflage = true;
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("上传数据出错："+e.getMessage());
		}

		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		MyStatus status = new MyStatus();
		//解析完数据，开始存入数据库
		 if(parseflage){
			  boolean synresult =androidDataSynService.tableDataSyn(table_userWord, table_userbehaviour,table_userresource);
			  if( !synresult )
					status.setStatus(StatusConstant.SYN_STUDY_RECORD_FAILURE);
		 }else{
			 status.setStatus(StatusConstant.SYN_STUDY_RECORD_PARSE_FAILURE);
		 }
		JSONObject json = JSONObject.fromObject(status);
		mv.addObject("json", json.toString());
		return mv;
	}
	public ParseXmlService getParseXmlService() {
		return parseXmlService;
	}
	@Resource
	public void setParseXmlService(ParseXmlService parseXmlService) {
		this.parseXmlService = parseXmlService;
	}
	public AndroidDataSynService getAndroidDataSynService() {
		return androidDataSynService;
	}
	@Resource
	public void setAndroidDataSynService(AndroidDataSynService androidDataSynService) {
		this.androidDataSynService = androidDataSynService;
	}
	
}
