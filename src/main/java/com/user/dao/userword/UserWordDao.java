package com.user.dao.userword;

import java.util.Date;
import java.util.List;

import com.cnu.iqas.dao.base.DAO;
import com.user.entity.UserWord;

/**
 * 
 * @author 作者：刘玉婷
 * @version 创建时间：2016年6月30日下午 12:25:00
 * 
 */
public interface UserWordDao extends DAO<UserWord>{

	/**
	 * 查询指定用户和指定单词的学习记录
	 * @param userId
	 * @param word
	 * @return 
	 */
	public List<UserWord> find(String userId,String word);
	
	/**
	 * 查询指定用户和指定级别的单词的学习记录
	 * @param userId
	 * @param word
	 * @param topiclevel
	 * @return 
	 */
	public List<UserWord> findAll(String userId,String word,int topiclevel);
	
	/**
	 * 查询指定级别的单词的被学习记录
	 * @param word
	 * @param topiclevel
	 * @return
	 */
	public List<UserWord> findlearncount(String word,int topiclevel);
	
}
