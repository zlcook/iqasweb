package com.cnu.offline.exception;
/**
* @author 周亮 
* @version 创建时间：2016年9月12日 上午9:57:05
* 类说明:根据主题和年级在本体库中未查询到单词
*/
public class ThemeWordNotExistException extends Exception {

	public ThemeWordNotExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ThemeWordNotExistException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public ThemeWordNotExistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ThemeWordNotExistException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ThemeWordNotExistException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
