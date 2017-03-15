package com.cnu.ds.untils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author 周亮 
* @version 创建时间：2016年11月28日 下午3:22:52
* 类说明
*/
public class ParseUntils {
	/**
	 * 将str字符串转换成数值
	 * @param str 数值字符串
	 * @return  
	 * 
	 * 如果str为null或者为空字符串或者包含非数值字符返回0
	 * 否则返回对应的数值
	 */
	public static int parseInt(String str){
		if( str==null || str.trim().equals("")){
			return 0;
		}else{
			try {
				int res= Integer.parseInt(str);
				return res;
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		
	}
	/**
	 * 字符串转换成日期格式
	 * @param str  日期字符串
	 * @param sdf  日期格式，不提供则使用默认的"yyyy-MM-dd-HH:mm:ss"模式
	 * @return
	 * 返回解析后的日期，如果解析失败返回null
	 */
	public static Date parseDate(String str,SimpleDateFormat sdf ){
		if( sdf ==null)
		 sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		if( str!=null && !str.trim().equals("")){
			try {
				Date date = sdf.parse(str);
				return date;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
}
