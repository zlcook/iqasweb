package com.cnu.offline.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
* @author 周亮 
* @version 创建时间：2017年2月23日 下午3:18:50
* 类说明:资源配置解析类，每次读取的内容都是最新的,读的内容在studyword.properties文件中配置
*/
public class ResourceConfigParseUntils {

	public static final  String PROFILEPATH ="studyword.properties";
	/**
	 * 获取配置信息
	 * @param key
	 * @return {@PROFILEPATH}配置文件下内容
	 * @throws IOException
	 */
	public static String getProByKey(String key) throws IOException{
		//相对于classes下的资源路径
		String path =ResourceConfigParseUntils.class.getClassLoader().getResource(PROFILEPATH).getPath();
		FileInputStream fis = new FileInputStream(path);
		Properties pro= new Properties();
		pro.load(fis);
		String res =(String) pro.get(key);
		return res;
	}
	/**
	 * 将key键对应的value通过split_Regx正则表达式分隔后以集合的形式返回
	 * @param key key信息
	 * @param split_Regx  分隔正则表达式
	 * @return
	 * @throws IOException
	 */
	public static List<String> getProsByKey(String key,String split_Regx) throws IOException{
		String pro =getProByKey(key);
		String[] pros =pro.split(split_Regx);
		return Arrays.asList(pros);
	}
}
