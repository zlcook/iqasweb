package com.cnu.iqas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


@ContextConfiguration(locations = {"/applicationContext.xml"})//启动Spring容器
public class SearchTest  extends AbstractTestNGSpringContextTests {//基于TestNG的spring测试框架

	//@Autowired  //注入Spring容器中的Bean
	//private  AdminService adminService;
	
	
	//public void getFilePath(){
		/*URL path =SearchTest.class.getClassLoader().getResource("english-left3words-distsim.tagger");
		System.out.println("path:"+path.getPath());*/
		
	//}

	/*@Test
	public void tt(){
		System.out.println("dd");
		Admin ad = new Admin();
		ad.setAccount("liang");
		ad.setPassword("123");
		adminService.save(ad);
	}*/

}
