package com.cnu.iqas.controller.mobile.ios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.DateJsonValueProcessor;
import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.formbean.ios.SUserRegisterForm;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.WebUtils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
* @author 周亮 
* @version 创建时间：2016年3月1日 上午10:08:52
* 类说明
*/
@Controller
@RequestMapping(value="/mobile/ios/user/")
public class SLoginController {

	/**
	 * 用户基本服务接口
	 */
	private IUserBaseService suserBaseService;

	
	/**
	 * 登录
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="login")
	public ModelAndView login(String userName,String password,HttpServletRequest request){
		
		List<JSONObject> listJson= new ArrayList<JSONObject>();//最后返回的用户集合，其实就一个
		JSONObject userJson = null;
		MyStatus status = new MyStatus();
		
		try {
			Suser user =  null;
			if( !WebUtils.isNull(userName) && !WebUtils.isNull(password)){
				//根据用户名和密码查找用户
				user = (Suser) suserBaseService.findUser(userName, password);
				if(user !=null){
				
				//用户对象配置类
				JsonConfig userConfig = new JsonConfig();
				userConfig.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				 //生成json
				 userJson = JSONObject.fromObject(user,userConfig);
				}
			}
			
			if( userJson==null){
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
				status.setMessage("用户名或者密码有误");
			}else{
				//登录成功
				listJson.add(userJson);
				//添加登录记录
				suserBaseService.addLoginRecord(user.getUserId(), userName, request.getRemoteHost());
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常");
		}
		
		//返回结果
		return JsonTool.generateModelAndView(listJson, status);
	}
	/**
	 * 注册
	 * 用户名，密码，性别，年级
	 * @param formbean
	 * @return
	 */
	@RequestMapping(value="register")
	public ModelAndView register(SUserRegisterForm formbean){
		
		MyStatus status = new MyStatus();
		//校验注册信息
		if( formbean.validateRegisterInfo()){
			//根据用户名检查用户是否存在
			if(suserBaseService.findByName(formbean.getUserName())==null){
				Suser user = new Suser();
				//将表单类中的信息拷贝到对应的类中
				WebUtils.copyBean(user, formbean);
				suserBaseService.save(user);
			}else{
				status.setStatus(StatusConstant.USER_EXIST);
				status.setMessage("用户存在");
			}
			
		}else{
			status.setStatus(StatusConstant.USER_REGISTERINFO_INVALIDATE);
			status.setMessage("用户注册信息无效");
		}
		//返回结果
		return JsonTool.generateModelAndView( status);
	}
	
	
	/**
	 * 退出
	 * 用户名，密码
	 * @param formbean
	 * @return
	 */
	@RequestMapping(value="exit")
	public ModelAndView exit(String userName,String password,HttpServletRequest request){
		
		MyStatus status = new MyStatus();
		//校验用户名，密码
		if( !WebUtils.isNull(userName) && !WebUtils.isNull(password)){
		    suserBaseService.logout(userName, password, request.getRemoteHost());
		}else{
			status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
			status.setMessage("用户名或者密码有误");
		}
		//返回结果
		return JsonTool.generateModelAndView( status);
	}
	public IUserBaseService getSuserBaseService() {
		return suserBaseService;
	}
	@Resource
	public void setSuserBaseService(IUserBaseService suserBaseService) {
		this.suserBaseService = suserBaseService;
	}
	
	
	
}
