package com.cnu.iqas.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cnu.iqas.bean.iword.WordThemeTypeEnum;
import com.cnu.iqas.bean.user.User;

/**
* @author 周亮 
* @version 创建时间：2015年11月10日 下午8:27:21
* 类说明
*/
public class LogTest {
	
	private static final Logger logger= LogManager.getLogger(LogTest.class);
	public static void main(String[] args){
		
		
		
		/*User user = new User();
		user.setPassword("1323");
		user.setUsername("zhangsan");
		logger.trace(user.getUsername());
		logger.debug(user.getUsername());
		logger.info(user.getUsername());
		logger.warn(user.getUsername());
		logger.error(user.getUsername());
		logger.fatal(user.getUsername());*/
		/*String rex="[123456]{1}";
		String rex2="\\d{1}";
		String word = "67";
		System.out.println(word.matches(rex2));
		
		testEnu();*/
		
		doubleNumber();
	}
	
	public static void testEnu(){
		WordThemeTypeEnum en = WordThemeTypeEnum.PRAIRIE;
		System.out.println(en.ordinal());
	}
	
	public static void doubleNumber(){
		Double dd =0.6375; 
		//四舍五入只保留2位小数
		Double d = dd*100;;
		DecimalFormat   df   =new   DecimalFormat("#"); 
		//生成字符66%
		String successRate =df.format(d) +"%";
		System.out.println(successRate);
	}
}
