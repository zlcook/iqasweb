package com.user.dao.userlogin.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.userlogin.UserLoginDao;
import com.user.entity.UserLogin;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 上午9:37:00 
 * 类说明 
*/
@Repository("userLoginDao")
public class UserLoginDaoImpl extends DaoSupport<UserLogin> implements UserLoginDao {

	@Override
	public UserLogin findCurrentLogin(String userId, String ip) {
		// TODO Auto-generated method stub
		List<UserLogin> list = (List<UserLogin>) getHt().find("From UserLogin o where o.userId=? and ip=? and loginState=1",userId,ip);
		if(list != null&&list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public List<UserLogin> findLogining(String userId) {
		// TODO Auto-generated method stub
		List<UserLogin> list = (List<UserLogin>) getHt().find("From UserLogin o where o.userId=? and loginState=1",userId);
		return list;
	}

	@Override
	public List<UserLogin> find(String userId) {
		// TODO Auto-generated method stub
		List<UserLogin> list = (List<UserLogin>) getHt().find("From UserLogin o where o.userId=?", userId);
		return list;
	}

}