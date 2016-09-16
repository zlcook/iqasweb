package com.cnu.iqas.test;
/**
* @author 周亮 
* @version 创建时间：2015年11月22日 下午8:10:24
* 类说明
*/
public class RegTest {

public static void main(String[] args){
	String id =null;  
	String con=null; //至少一个字母
	System.out.println(validate(id,con));
	}
public static boolean validate(String id,String content){
	if( null==id || null == content){
		return false;
	}else{
		String idreg ="\\d{1,2}/\\d{1,2}/\\d{1,2}/\\d{1,2}";  
		String conreg="[A-Za-z]+( *[A-Za-z]+)*"; //至少一个字母中间可以有空格，有空格时后面一定要有字母
		if( id.matches(idreg)&& content.matches(conreg))
			return true;
	}
	return false;
}
}
