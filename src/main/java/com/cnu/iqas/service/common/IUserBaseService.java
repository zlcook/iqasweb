package com.cnu.iqas.service.common;

import com.cnu.iqas.bean.user.User;

/**
* @author 周亮 
* @version 创建时间：2016年3月1日 上午10:57:57
* 类说明
*/
public interface IUserBaseService<T> {
	/**
	 * 保存用户
	 * @param user
	 */
	public void save(T user);
	/**
	 * 保存user属性
	 * @param user
	 */
	public void SaveCoinAndScene(User user);
	/**
	 *查找用户
	 * @param username 账号
	 * @param password 密码
	 * @return 返回用户实例
	 */  
	public T findUser(String userName, String password);
	/**
	 * 根据用户名查询用户是否存在
	 * @param userName
	 * @return
	 */
	public T findByName(String userName);
	/**
	 * 更新用户
	 * @param user
	 */
	public void update(T user);
	
	/**
	 * 添加登录记录
	 * @param userId 用户id
	 * @param userName 用户名
	 * @param password
	 * @param ip   //登录ip地址
	 * @return
	 */
	//public void addLoginRecord(String userId,String userName,String ip);
	
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @param ip
	 *//*
	public T login(String userName,String password,String ip);
	*/
	/**
	 * 注销用户
	 * @param userName
	 * @param password
	 */
	//public void logout(String userName,String password,String ip);
}
