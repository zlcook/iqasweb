package com.cnu.iqas.controller.web.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnu.iqas.bean.admin.Admin;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.service.admin.AdminService;
import com.cnu.iqas.utils.PropertyUtils;

/**
* @author 周亮 
* @version 创建时间：2016年11月23日 下午4:00:04
* 类说明：初始化系统类
* 初始化管理员账号
*/
@Controller
@RequestMapping(value = "/initSystem/")
public class InitSystemController {
	  //注入Spring容器中的Bean
	private  AdminService adminService;
	private Logger log = LogManager.getLogger(InitSystemController.class);
	
	@RequestMapping(value="initadmin")
	public String initAdmin(HttpServletRequest request){

		String adminAccount=PropertyUtils.get("init.account");
		String adminPassword=PropertyUtils.get("init.password");
		String ip =request.getRemoteAddr();
		//只初始化一次
		if(!adminService.existAccount(adminAccount)){
			Admin ad = new Admin();
			ad.setAccount(adminAccount);
			ad.setPassword(adminPassword);
			adminService.save(ad);
			log.info("初始化管理员账号成功,来自："+ip);
		}else{
			log.info("已经初始化管理员账号,来自："+ip);
		}
		//返回登录界面
		return PageViewConstant.generatorRedirect("/admin/loginUI");
	}

	public AdminService getAdminService() {
		return adminService;
	}
	@Autowired
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
}
