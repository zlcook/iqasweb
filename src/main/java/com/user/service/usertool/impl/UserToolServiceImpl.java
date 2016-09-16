package com.user.service.usertool.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.usertool.UserToolDao;
import com.user.entity.UserTool;
import com.user.service.usertool.UserToolService;
@Service("userToolService")
public class UserToolServiceImpl implements UserToolService {

	private UserToolDao userToolDao;
	
	public UserToolDao getUserToolDao() {
		return userToolDao;
	}
	@Resource
	public void setUserToolDao(UserToolDao userToolDao) {
		this.userToolDao = userToolDao;
	}


	@Override
	public int delete(String userId, String toolId) {
		// TODO Auto-generated method stub
		UserTool ut = this.find(userId, toolId);
		if(ut != null && ut.getCount() == 0)
		{
			userToolDao.delete(ut.getId());
			return 1;
		}
		return 0;
	}


	@Override
	public UserTool find(String userId, String toolId) {
		// TODO Auto-generated method stub
		return userToolDao.find(userId, toolId);
	}
	@Override
	public void update(String userId, String toolId, int count) {
		// TODO Auto-generated method stub
		UserTool ut = this.find(userId, toolId);
		if(ut != null)
		{
			ut.setCount(count);
			userToolDao.update(ut);
		}
		else
		{
			userToolDao.save(new UserTool(userId,toolId,count));
		}
	}

}
