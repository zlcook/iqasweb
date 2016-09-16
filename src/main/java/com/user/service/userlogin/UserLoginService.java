package com.user.service.userlogin;

import java.util.List;

import com.user.entity.UserLogin;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 上午9:39:01 
 * 类说明 
*/
public interface UserLoginService {

	/**
	 * 添加登录记录
	 * @param userId 用户id
	 * @param password
	 * @param ip   //登录ip地址
	 * @return
	 */
	public void addLoginRecord(String userId,String ip);
	
	/**
	 * 注销用户
	 * @param userId
	 */
	public void logout(String userId, String ip);
	

	
}
