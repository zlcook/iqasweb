package com.cnu.iqas.service.admin;

import com.cnu.iqas.bean.admin.Admin;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 上午10:57:05
* 类说明
*/
public interface AdminService  {
	/**
	 * 判断用户输入账号或密码是否正确
	 * @param account 账号
	 * @param password 密码
	 * @return 返回用户实例
	 */
	public Admin validate(String account, String password);
	/**
	 * 保存管理员
	 * @param ad
	 */
	public void save(Admin ad);
	
	
}
