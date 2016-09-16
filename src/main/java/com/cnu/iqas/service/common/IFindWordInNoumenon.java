package com.cnu.iqas.service.common;

import java.util.List;

import com.cnu.iqas.bean.iword.Iword;

/**
* @author 周亮 
* @version 创建时间：2016年3月2日 下午5:53:09
* 类说明，  在本体中查找单词接口
*/
public interface IFindWordInNoumenon {
	 /**
	 * 用户输入一个单词或词组的时候，执行这个方法，通过对本题库的查询，查询到该单词在本体库中所有的属性。
	 * @param  用户输入一个单词或词组
	 * @return 该单词所对应的所有的属性
     */
   public List<Iword> findWord(String word);
}
