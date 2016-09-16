package com.cnu.iqas.service.user.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.bean.ios.SuserLogin;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.bean.user.UserLogin2;
import com.cnu.iqas.dao.user.UserDao;
import com.cnu.iqas.dao.user.UserLoginDao2;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.utils.WebUtils;
@Service("userService")
public class UserServiceImpl implements IUserBaseService<User> {
	private UserDao userDao;
	/**
	 * 登录记录操作表
	 */
	private  UserLoginDao2 userLoginDao2;
	//给密码加密
	public void save(User entity) {
		if( entity !=null){
			entity.setPassword(WebUtils.MD5Encode(entity.getPassword().trim()));
			userDao.save(entity);
		}else{
			throw new RuntimeException("保存用户为空!");
		}
	}

	public User findByName(String userName) {
		// TODO Auto-generated method stub
		if( userName!=null &&!userName.equals(""))
		    return userDao.findByName(userName);
		else
			return null;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserLoginDao2 getUserLoginDao2() {
		return userLoginDao2;
	}
	@Resource
	public void setUserLoginDao2(UserLoginDao2 userLoginDao) {
		this.userLoginDao2 = userLoginDao;
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	@Override
	public User findUser(String userName, String password) {
		if( !isNull(userName) && !isNull(password))
			   return userDao.findByNameAndPas(userName, WebUtils.MD5Encode(password.trim()));
			else
				return null;
	}

	@Override
	public void addLoginRecord(String userId, String userName, String ip) {
		//添加记录之前要将上次未正常退出的记录的“登录状态”标注为非正常退出状态
		//问题
		List<UserLogin2> loginings=userLoginDao2.findLogining(userId);
		for(UserLogin2 lo :loginings){
			//将用户之前的登录状态改为”非正常退出状态“
			lo.setLoginState(SuserLogin.UNNORMAL_NOLOGOUT);
			userLoginDao2.update(lo);
		}
		UserLogin2 entity = new UserLogin2();
		entity.setUserName(userName);
		entity.setUserId(userId);
		entity.setIp(ip);
		userLoginDao2.save(entity);
	}
	/**
	 * 校验字符串是否为空或者空字符串
	 * @param str
	 * @return 空或者空字符串返回true
	 */
	public boolean isNull(String str){
		if( str!=null && str.trim()!="")
			return false;
		else
			return true;
	}

	@Override
	public void logout(String userName, String password, String ip) {
		// TODO Auto-generated method stub
		User user =findUser(userName,  password);
		if( user !=null){ 
			//获取当前登录记录，修改退出时间和登录状态
			UserLogin2 loginRecord= userLoginDao2.findCurrentLogin(user.getUserId(), ip);
			if( loginRecord!=null){
				loginRecord.setLogoutTime(new Date());
				//状态设置为正常退出
				loginRecord.setLoginState(UserLogin2.NORMAL_LOGOUT);
				userLoginDao2.update(loginRecord);
			}
		}
	}
	//保存金币和场景
	@Override
	public void SaveCoinAndScene(User user) {
		
			userDao.update(user);
		
	}
}
