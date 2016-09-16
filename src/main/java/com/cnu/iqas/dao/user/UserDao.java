package com.cnu.iqas.dao.user;

import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.dao.base.DAO;
import com.cnu.iqas.dao.common.IFindUserDao;

/**
* @author 周亮 
* @version 创建时间：2015年11月9日 下午8:39:09
* 类说明
*/
public interface UserDao  extends DAO<User>,IFindUserDao<User>{
	
}
