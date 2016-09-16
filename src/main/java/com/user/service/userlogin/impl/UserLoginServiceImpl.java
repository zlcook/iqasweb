package com.user.service.userlogin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.ios.SuserLogin;
import com.user.dao.userlogin.UserLoginDao;
import com.user.entity.UserLogin;
import com.user.service.userlogin.UserLoginService;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 上午9:40:12 
 * 类说明 
*/
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {

	/**
	 * 登录记录操作表
	 * */
	private UserLoginDao userLoginDao;
	
	@Override
	public void addLoginRecord(String userId, String ip) {
		// TODO Auto-generated method stub
		//添加记录之前要将上次未正常退出的记录的“登录状态”标注为非正常退出状态
		List<UserLogin> loginings = userLoginDao.findLogining(userId);
		for(UserLogin lo:loginings){
			//将用户之前的登录状态改为“非正常退出状态”
			lo.setLoginState(SuserLogin.UNNORMAL_NOLOGOUT);
			userLoginDao.update(lo);
		}		

		UserLogin entity = new UserLogin();
		entity.setUserId(userId);
		entity.setIp(ip);
		userLoginDao.save(entity);
	}

	@Override
	public void logout(String userId, String ip) {
		// TODO Auto-generated method stub	
		//获取当前登录记录，修改退出时间和登录状态
		UserLogin loginRecord = userLoginDao.findCurrentLogin(userId, ip);
		if(loginRecord != null){
			loginRecord.setLoginoutTime(new Date());
			//状态设置为正常退出
			loginRecord.setLoginState(UserLogin.NORMAL_LOGOUT);
			userLoginDao.update(loginRecord);
		}
	}	

	public UserLoginDao getUserLoginDao() {
		return userLoginDao;
	}

	@Resource
	public void setUserLoginDao(UserLoginDao userLoginDao) {
		this.userLoginDao = userLoginDao;
	}


}
