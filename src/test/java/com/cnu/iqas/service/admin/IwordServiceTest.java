package com.cnu.iqas.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.cnu.iqas.bean.iword.VersionWordCount;
import com.cnu.iqas.service.iword.IwordService;

/**
* @author 周亮 
* @version 创建时间：2015年11月23日 上午10:09:05
* 类说明
*/

@ContextConfiguration(locations = {"/applicationContext.xml"})//启动Spring容器
public class IwordServiceTest extends AbstractTestNGSpringContextTests{
	@Autowired  //注入Spring容器中的Bean
	private  IwordService iwordService;
	
	@Test(enabled=false)
	public void testStatisticsVersionAndWordCount(){
		List list =iwordService.statisticsVersionAndWordCount();
		if( list == null)
		System.out.println("kong");
		for( int i =0 ;i <list.size();i++){
			VersionWordCount o =(VersionWordCount) list.get(i);
			System.out.println(o.toString());
		}
	}

}
