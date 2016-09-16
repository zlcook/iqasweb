package com.user.dao.userresource;

import java.util.List;

import com.cnu.iqas.dao.base.DAO;
import com.user.entity.UserResource;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 上午11:29:51 
 * 类说明 
*/
public interface UserResourceDao extends DAO<UserResource> {

	public UserResource findUserResource(String wherejpql, Object[] attribute);
	
//	public UserResource findCurrentLearnState(String userId, String resourceId);
	
	/**
	 * 查询指定用户的学习资源记录
	 * @param userId
	 * @return
	 */
	public List<UserResource> find(String userId);
	
	/**
	 * 查询指定用户对指定媒体类型的浏览记录
	 * @param userId
	 * @param mediaType
	 * @return
	 */
	public List<UserResource> findByUserMedia(String userId, int mediaType);
	
	/**
	 * 查询指定媒体类型的被浏览记录
	 * @param mediaType
	 * @return
	 */
	public List<UserResource> findByMedia(int mediaType);
	
	/** 2016/8/26新增
	 * 查询指定用户在指定单词指定资源指定类型上的学习记录（唯一记录）
	 * @param userId
	 * @param word
	 * @param resourceId
	 * @param mediaType
	 * @return 指定用户和指定类型指定资源的学习记录
	 */
	public UserResource find(String userId,String word,String resourceId,int mediaType);
	
	
}
