package com.user.service.userword;

import java.util.Date;
import java.util.List;

import com.user.entity.UserWord;

/**
 * @author 刘玉婷
 * @version 创建时间：2016年6月30日 下午12:32:10
 */
public interface UserWordService {

	/**
	 * 添加一条用户单词学习记录
	 * @param userId 
	 * @param word
	 * @param topicLevel
	 */
	public void add(String userId,String word,int topicLevel);
	
	/**
	 * 查询指定用户和指定单词的（多条）学习记录
	 * @param userId
	 * @param word
	 * @return
	 */
	public List<UserWord> find(String userId,String word);
	
	/**
	 * 返回指定用户指定单词的学习次数
	 * @param userId
	 * @param word
	 * @return
	 */
	public int learnCount(String userId,String word);
	
}
