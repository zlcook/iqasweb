package com.user.service.userworks.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.userworks.UserWorksDao;
import com.user.entity.UserWorks;
import com.user.service.userworks.UserWorksService;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 下午9:47:49 
 * 类说明 
*/
@Service("userWorksService")
public class UserWorksServiceImpl implements UserWorksService {

	private UserWorksDao userWorksDao;


	@Override
	public void delectUserWorks(String worksId) {
		// TODO Auto-generated method stub
		userWorksDao.delete(worksId);
	}



	@Override
	public UserWorks findUserWorks(String worksId, String userId) {
		// TODO Auto-generated method stub
		List<String> uWorks = new ArrayList<String>();
		uWorks.add(worksId);
		uWorks.add(userId);
		
		UserWorks userWorks = userWorksDao.findUserWorks("from UserWorks where worksId=? and userId=?", uWorks.toArray());
		return userWorks;
	}

	public UserWorksDao getUserWorksDao() {
		return userWorksDao;
	}

	@Resource
	public void setUserWorksDao(UserWorksDao userWorksDao) {
		this.userWorksDao = userWorksDao;
	}



	@Override
	public void add(String worksName, String userId, String word, int worksType, String worksUrl, int location) {
		// TODO Auto-generated method stub
		List<UserWorks> list = userWorksDao.find(worksName, userId);
		int count= 1;
		if(list != null)
			count = list.size() + 1;
		userWorksDao.save(new UserWorks(userId,word,worksName,0,location,worksType,worksUrl,count));
		
	}

}
