package com.user.service.userresource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.userresource.UserResourceDao;
import com.user.entity.UserResource;
import com.user.service.userresource.UserResourceService;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 下午4:10:10 
 * 类说明 
*/
@Service("userResourceService")
public class UserResourceServiceImpl implements UserResourceService  {

	private UserResourceDao userResourceDao;
	/**
	 * 2016/8/26
	 */
	@Override
	public void addUserResource(String userId, String word, String resourceId, int mediaType,Date learnStartTime,Date learnEndTime) {
		// TODO Auto-generated method stub
		UserResource ur = userResourceDao.find(userId, word, resourceId, mediaType);
		if(ur == null)  //如果该资源记录不存在则新增
		{
			long learnDuration = 0;
			if(learnStartTime != null && learnEndTime != null)
		       	learnDuration = learnEndTime.getTime() - learnStartTime.getTime();
			userResourceDao.save(new UserResource(userId,word,resourceId,mediaType,learnStartTime,learnEndTime,learnDuration,1));
		}
		else 
		{
			long learnDuration = ur.getLearnDuration();
			if(learnStartTime != null && learnEndTime != null)
			{
				ur.setLearnStartTime(learnStartTime);
				ur.setLearnEndTime(learnEndTime);
				ur.setLearnDuration(ur.getLearnDuration() + learnEndTime.getTime() - learnStartTime.getTime());
			}
			ur.setLearnCount(ur.getLearnCount() + 1);
			userResourceDao.update(ur);
		}
		/*//添加记录之前要将上次未正常结束的学习记录的“学习状态”标注为异常结束学习状态
		List<UserResource> learning = userResourceDao.find(userId);
		for(UserResource re:learning){
			//将用户之前的学习状态改为“异常结束状态”
			re.setLearnState(UserResource.UNNORMAL_LEARNEND);
			userResourceDao.update(re);			
		}		
		
		UserResource entity = new UserResource();
		entity.setUserId(userId);
		entity.setResourceId(resourceId);
		entity.setMediaType(mediaType);
		userResourceDao.save(entity);		*/
	}


	
	/*@Override
	public void learnout(String userId, String resourceId) {
		// TODO Auto-generated method stub
		//修改当前学习记录，修改结束学习时间和学习状态
		UserResource learnRecord = userResourceDao.findCurrentLearnState(userId, resourceId);
		if(learnRecord != null){
			learnRecord.setLearnendTime(new Date());
			//状态设置为正常结束学习状态
			learnRecord.setLearnState(UserResource.LEARNEND);
			userResourceDao.update(learnRecord);
		}
	}*/

	public UserResourceDao getUserResourceDao() {
		return userResourceDao;
	}

	@Resource
	public void setUserResourceDao(UserResourceDao userResourceDao) {
		this.userResourceDao = userResourceDao;
	}



	@Override
	public List<UserResource> findByuserId(String userId) {
		// TODO Auto-generated method stub
		
		return userResourceDao.find(userId);
	}



	@Override
	public UserResource findUserResource(String userId, String word, String resourceId, int mediaType) {
		// TODO Auto-generated method stub
		return userResourceDao.find(userId, word, resourceId, mediaType);
	}

	
}
