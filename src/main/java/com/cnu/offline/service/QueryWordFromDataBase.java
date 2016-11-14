package com.cnu.offline.service;

import java.util.Hashtable;
import java.util.Map;

import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年9月6日 下午10:15:05
* 类说明
* 根据主题和年级查询要处理的单词
*/
public interface QueryWordFromDataBase {

	/**
	 * 根据年级和主题从本体库中查询学习单词
	 * @param grade 年级，1代表一年级
	 * @param theme  主题，如："17.旅游与交通"主题
	 * @return 存放单词的map集合，其中key代表单词，PropertyEntity代表单词实体
	 */
	public <E> Hashtable<String,E> queryWordByThemeAndGrade(int grade,String theme);
}
