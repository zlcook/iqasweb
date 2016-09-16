package com.user.dao.userlogin;

import java.util.List;

import com.cnu.iqas.dao.base.DAO;
import com.cnu.iqas.dao.common.IFindUserLoginDao;
import com.user.entity.UserLogin;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 上午9:35:37 
 * 类说明 
*/
public interface UserLoginDao extends DAO<UserLogin>,IFindUserLoginDao<UserLogin> {

	
	public List<UserLogin> find(String userId);
}

