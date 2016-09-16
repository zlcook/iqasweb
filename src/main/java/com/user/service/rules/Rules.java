package com.user.service.rules;

import java.util.List;

/**
 * @author 刘玉婷
 * @version 创建时间：2016年7月4日 下午17:35:10
 */
public interface Rules {
	//-------------------------IOS---------------------------//
    /**
     * 根据用户ID向其推荐唯一的一个一级主题
     * @param userId
     * @return 登录次数及一级主题
     */
	public Object[] topicRecommendIOS(String userId);
	
	/**
	 * 根据用户ID和一级主题推荐其二级主题的优先级排序
	 * @param userId
	 * @param topic 一级主题
	 * @return 登录次数及该一级主题下二级主题的排序
	 */
	public Object[] secondTopicRecommend(String userId, String topic);
	
	/**
	 * 根据用户ID和二级主题确定单词推荐的优先级排序
	 * @param userId
	 * @param secondTopic 二级主题
	 * @return 登录次数及该二级主题下单词的排序
	 */
	public Object[] wordRecommend(String userId, String secondTopic);
	
	/**
	 * 根据用户id确定其资源媒体类型优先级
	 * @param userId
	 * @return 登录次数及媒体类型优先级排序
	 */
	public int[] wordLearnIOS(String userId);
	
	
	
	//-------------------------Android-------------------------//
	/**
	 * 根据用户ID去推荐一级主题（可多个）
	 * @param userId
	 * @return （多个）一级主题
	 * 0单元为登录次数int，如果登录次数<5，则单元1~13为所有主题；如果登录次数>=5则单元1为推荐主题，其他单元均为-1
	 */
	public Object[] topicRecommendAndroid(String userId);
	
	/**
	 * 获取自适应推荐年级
	 * @param userId
	 * @param grade 实际年级
	 * @return  
	 */
	public int recommendGrade(String userId, int grade);
	
	/**
	 * 根据用户id和资源对应属性
	 * @param userId
	 * @param aspect
	 * @return 判定其媒体类型优先级顺序
	 */
	
	/**
	 * 根据用户ID生成推荐序列
	 * @param userId
	 * @return
	 */
	public Object[][] recommendSequence(String userId);
	
	
	public int[] wordLearnMedia(String userId, String aspect);
	
	/**
	 * 根据用户id判定学习哪些属性
	 * @param userId
	 * @return 属性集合
	 */
	public List<String> wordLearnAspect(String userId);
	
}
