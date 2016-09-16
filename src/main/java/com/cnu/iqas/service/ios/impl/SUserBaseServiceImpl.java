package com.cnu.iqas.service.ios.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.bean.ios.SuserLogin;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.dao.ios.SUserDao;
import com.cnu.iqas.dao.ios.SUserLoginDao;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.utils.WebUtils;

/**
* @author 周亮 
* @version 创建时间：2016年3月1日 上午11:02:28
* 类说明：用户基本服务类
* 1.保存用户
*
*/
@Service("suserBaseService")
public class SUserBaseServiceImpl implements IUserBaseService<Suser> {
	/**
	 * 用户表数据访问接口
	 */
	private SUserDao suserDao;
	/**
	 * 登录表数据访问接口
	 */
	private SUserLoginDao suserLoginDao;
	@Override
	public void save(Suser entity) {
		if( entity !=null){
			entity.setPassword(WebUtils.MD5Encode(entity.getPassword().trim()));
			suserDao.save(entity);
		}else{ 
			throw new RuntimeException("保存用户为空!");
		}
	}

	@Override
	public Suser findUser(String userName, String password) {
		if( !isNull(userName) && !isNull(password))
		   return suserDao.findByNameAndPas(userName, WebUtils.MD5Encode(password.trim()));
		else
			return null;
	}

	@Override
	public Suser findByName(String userName) {
		if( ! isNull(userName))
			return suserDao.findByName(userName);
		return null;
	}

	@Override
	public void update(Suser user) {
		suserDao.update(user);
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
	public void addLoginRecord(String userId,String userName, String ip) {
		//添加记录之前要将上次未正常退出的记录的“登录状态”标注为非正常退出状态
		//问题
		List<SuserLogin> loginings=suserLoginDao.findLogining(userId);
		for(SuserLogin lo :loginings){
			//将用户之前的登录状态改为”非正常退出状态“
			lo.setLoginState(SuserLogin.UNNORMAL_NOLOGOUT);
			suserLoginDao.update(lo);
		}
		SuserLogin loginRecord = new SuserLogin();
		loginRecord.setLoginTime(new Date());
		loginRecord.setIp(ip);
		loginRecord.setUserId(userId);
		loginRecord.setUserName(userName);
		//保存
		suserLoginDao.save(loginRecord);
	}

	/*@Override
	public Suser login(String userName, String password, String ip) {
		Suser user =findUser( userName,  password);
		if( user !=null){ 
			//添加登录记录
			addLoginRecord(user.getUserId(),userName,ip);
		}
		return null;
	}
*/
	@Override
	public void logout(String userName, String password, String ip) {
		Suser user =findUser( userName,  password);
		if( user !=null){ 
			//获取当前登录记录，修改退出时间和登录状态
			SuserLogin loginRecord= suserLoginDao.findCurrentLogin(user.getUserId(), ip);
			if( loginRecord!=null){
				loginRecord.setLogoutTime(new Date());
				//状态设置为正常退出
				loginRecord.setLoginState(SuserLogin.NORMAL_LOGOUT);
				suserLoginDao.update(loginRecord);
			}
		}
	}
	public SUserDao getSuserDao() {
		return suserDao;
	}
	@Resource
	public void setSuserDao(SUserDao suserDao) {
		this.suserDao = suserDao;
	}
	
	public SUserLoginDao getSuserLoginDao() {
		return suserLoginDao;
	}
	@Resource
	public void setSuserLoginDao(SUserLoginDao suserLoginDao) {
		this.suserLoginDao = suserLoginDao;
	}

	@Override
	public void SaveCoinAndScene(User user) {
		// TODO Auto-generated method stub
		
	}
}
