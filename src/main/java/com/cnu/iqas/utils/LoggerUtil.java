package com.cnu.iqas.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cnu.iqas.test.LogTest;

/**
* @author 周亮 
* @version 创建时间：2015年11月11日 下午10:38:01
* 类说明
*/
public class LoggerUtil {
	private static final Logger logger= LogManager.getLogger(LoggerUtil.class);

	public  static void trace(String msg){
		logger.trace(msg);
	}
	public  static void debug(String msg){
		logger.debug(msg);
	}
	public  static void info(String msg){
		logger.info(msg);
	}
	public  static void warn(String msg){
		logger.warn(msg);
	}
	public  static void error(String msg){
		logger.error(msg);
	}
	public  static void fatal(String msg){
		logger.fatal(msg);
	}
}
