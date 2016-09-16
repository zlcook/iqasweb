package com.user.service.userbehaviour.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.user.dao.userbehaviour.UserBehaviourDao;
import com.user.entity.UserBehaviour;
import com.user.service.userbehaviour.UserBehaviourService;
@Service("userBehaviourService")
public class UserBehaviourServiceImpl implements UserBehaviourService {

	private UserBehaviourDao userBehaviourDao;
	
	@Override
	public void addUserBehaviour(String userId, String doWhere, String doWhat, Date doWhen) {
		// TODO Auto-generated method stub
     UserBehaviour ub = new UserBehaviour(userId,doWhere,doWhat,doWhen);
     if(ub != null)
    	 userBehaviourDao.save(ub);
	}

	@Override
	public List<UserBehaviour> find(String userId) {
		// TODO Auto-generated method stub
		return userBehaviourDao.find(userId);
	}

}
