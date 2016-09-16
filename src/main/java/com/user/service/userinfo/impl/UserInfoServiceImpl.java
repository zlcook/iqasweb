package com.user.service.userinfo.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.userinfo.UserInfoDao;
import com.user.entity.UserInfo;
import com.user.service.userinfo.UserInfoService;
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    private UserInfoDao userInfoDao;
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	@Resource
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	@Override
	public void add(UserInfo ui) {
		// TODO Auto-generated method stub
       userInfoDao.save(ui);
	}

	@Override
	public int delete(String userId) {
		// TODO Auto-generated method stub
      UserInfo ui = userInfoDao.find(userId);
      if(ui != null){
    	  userInfoDao.delete(userId);
    	  return 1;
      }
          return 0;
	}

	@Override
	public UserInfo find(String userId) {
		// TODO Auto-generated method stub
		return userInfoDao.find(userId);
	}

	@Override
	public void update(UserInfo ui) {
		// TODO Auto-generated method stub
       userInfoDao.update(ui);
	}

}
