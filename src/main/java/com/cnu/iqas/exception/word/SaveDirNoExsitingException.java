package com.cnu.iqas.exception.word;
/**
* @author 周亮 
* @version 创建时间：2016年1月14日 下午8:52:25
* 类说明:保存资源文件的存放路径未指定
*/
public class SaveDirNoExsitingException extends RuntimeException {

	public SaveDirNoExsitingException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SaveDirNoExsitingException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaveDirNoExsitingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SaveDirNoExsitingException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SaveDirNoExsitingException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
