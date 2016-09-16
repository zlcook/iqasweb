package com.cnu.iqas.service.iword;

import com.cnu.iqas.bean.iword.WordRes;

/**
* @author 周亮 
* @version 创建时间：2016年7月21日 上午10:12:50
* 类说明
*/
public interface WordResService {

	public WordRes find(String word);
	public void update(WordRes wordRes);
	public void save(WordRes res);
}
