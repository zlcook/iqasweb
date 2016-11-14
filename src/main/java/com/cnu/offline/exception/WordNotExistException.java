package com.cnu.offline.exception;
/**
* @author 周亮 
* @version 创建时间：2016年10月31日 下午7:09:08
* 类说明ios对应的学习单词。
* 单词不存在异常
*/
public class WordNotExistException extends Exception {

	public WordNotExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WordNotExistException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public WordNotExistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public WordNotExistException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public WordNotExistException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
