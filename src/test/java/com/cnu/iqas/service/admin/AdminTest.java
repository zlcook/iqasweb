package com.cnu.iqas.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.cnu.iqas.bean.admin.Admin;
import com.cnu.iqas.service.admin.AdminService;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 下午5:22:57
* 类说明
*/

@ContextConfiguration(locations = {"/applicationContext.xml"})//启动Spring容器
public class AdminTest extends AbstractTestNGSpringContextTests{//基于TestNG的spring测试框架
	@Autowired  //注入Spring容器中的Bean
	private  AdminService adminService;

	@Test(enabled = false)
	public void tt(){
		System.out.println("dd");
		Admin ad = new Admin();
		ad.setAccount("liang");
		ad.setPassword("123");
		adminService.save(ad);
	}
}
