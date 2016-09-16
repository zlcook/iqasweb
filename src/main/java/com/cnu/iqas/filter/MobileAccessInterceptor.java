package com.cnu.iqas.filter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.utils.WebUtils;

import net.sf.json.JSONObject;

/**
* @author 周亮 
* @version 创建时间：2016年8月26日 上午11:05:43
* 类说明
*/
public class MobileAccessInterceptor implements HandlerInterceptor {


	private IUserBaseService<User> userService;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//System.out.println("mobile访问控制访问之后");
		  
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

		//System.out.println("mobile访问控制进入方法后");
	}
   	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub

		//System.out.println("mobile访问控制进入方法之前");
   		
   		//检查token从而检查用户是否已经登录
   		String token =request.getParameter("token");
   		String userName =WebUtils.getUserNameFromToken(token);
   		User user = userService.findByName(userName);
   		if(user !=null){
   			request.getSession().setAttribute("user", user);
   			
   			return true;
		  
   		}else{
   			//返回提示
   			MyStatus status = new MyStatus(StatusConstant.NO_LOGIN, "请先登录");
   			JSONObject json = JSONObject.fromObject(status);
   			request.setAttribute("json", json.toString());
   			request.getRequestDispatcher("/WEB-INF/pages/share/json.jsp").forward(request, response);
   		}
   			return false;
	}

	public IUserBaseService<User> getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserBaseService<User> userService) {
		this.userService = userService;
	}


}
