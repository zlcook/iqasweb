package com.user.service.userresource;

import java.util.Date;
import java.util.List;

import com.user.entity.UserResource;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 下午4:09:19 
 * 类说明 
*/
public interface UserResourceService {

	/**
	 * 新增或更新记录
	 * @param userId
	 * @param word
	 * @param resourceId
	 * @param mediaType
	 */
	public void addUserResource(String userId,String word,String resourceId,int mediaType,Date learnStartTime,Date learnEndTime);
	
    /**
     * 查找指定用户在指定单词指定资源指定类型的学习记录（唯一记录）
     * @param userId
     * @param word
     * @param resourceId
     * @param mediaType
     * @return 
     */
	public UserResource findUserResource(String userId,String word,String resourceId,int mediaType);

	/**
	 * 查找指定用户的所有学习记录
	 * @param userId
	 * @return
	 */
	public List<UserResource> findByuserId(String userId);
//	public void learnout(String userId, String resourceId);


}
