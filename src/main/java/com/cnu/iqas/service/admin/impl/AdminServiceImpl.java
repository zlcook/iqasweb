package com.cnu.iqas.service.admin.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.admin.Admin;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.dao.admin.AdminDao;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.service.admin.AdminService;
import com.cnu.iqas.utils.WebUtils;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 上午10:58:18
* 类说明
*/
@Service("adminService")
public class AdminServiceImpl  implements AdminService{
	private AdminDao adminDao;
	
	//重写父类方法给密码加密

	public void save(Admin entity) {
		entity.setPassword(WebUtils.MD5Encode(entity.getPassword().trim()));
		adminDao.save(entity);
	}

	@Override
	public Admin validate(String account, String password) {
		// TODO Auto-generated method stub
		Admin admin = null;
		admin =adminDao.validate(account, WebUtils.MD5Encode(password.trim()));
		return admin;
	}

	public AdminDao getAdminDao() {
		return adminDao;
	}
	@Resource
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	
}
