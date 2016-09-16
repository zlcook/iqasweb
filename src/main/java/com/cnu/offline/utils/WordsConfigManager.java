package com.cnu.offline.utils;
/**
* @author 周亮 
* @version 创建时间：2016年7月4日 上午10:01:11
* 类说明
*/
public class WordsConfigManager {
	/**
	 * xml文件保存路径
	 */
	private String savePath;
	/**
	 * xml文件名称
	 */
	private String name;
	
	
	public WordsConfigManager(String savePath, String name) {
		super();
		this.savePath = savePath;
		this.name = name;
	}
	
}
