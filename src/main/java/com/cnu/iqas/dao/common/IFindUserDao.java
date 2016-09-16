package com.cnu.iqas.dao.common;

/**
* @author 周亮 
* @version 创建时间：2016年3月1日 上午10:38:01
* 类说明,查找用户接口
* 1.根据用户名和密码查找用户
* 2.根据用户名查用户
*/
public interface IFindUserDao<T> {
	/**
	 * 根据用户输入账号或密码查找用户
	 * @param username 账号
	 * @param password 密码
	 * @return 返回用户实例
	 */
	public T findByNameAndPas(String username, String password);
	/**
	 * 根据用户名查询用户是否存在
	 * @param userName
	 * @return
	 */
	public T findByName(String userName);
}
