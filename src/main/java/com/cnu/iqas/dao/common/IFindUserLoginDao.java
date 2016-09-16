package com.cnu.iqas.dao.common;

import java.util.List;


/**
* @author 周亮 
* @version 创建时间：2016年3月1日 下午8:33:42
* 类说明
*/
public interface IFindUserLoginDao<T> {
	/**
	 * 查找当前登录记录
	 * @param userId
	 * @param ip
	 * @return
	 */
	public T findCurrentLogin(String userId,String ip);
	/**
	 * 查询用户登录状态为”登录中的记录“
	 * @param userId
	 * @return
	 */
	public List<T> findLogining(String userId);
}
