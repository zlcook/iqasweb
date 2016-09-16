package com.cnu.iqas.dao.ios.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.ios.SUserDao;

/**
* @author 周亮 
* @version 创建时间：2016年3月1日 上午10:45:12
* 类说明:ios用户接口
*/
@Repository("suserDao")
public class SUserDaoImpl  extends DaoSupport<Suser> implements SUserDao {

	@Override
	public Suser findByNameAndPas(String username, String password) {
		// TODO Auto-generated method stub
		List<Suser> users =(List<Suser>) getHt().find("From Suser o where  o.userName=? and  o.password=?", username,password);
		
		if(users!=null&&users.size()==1)
			return users.get(0);
		return null;
	}

	@Override
	public Suser findByName(String userName) {
		List<Suser> users =(List<Suser>) getHt().find("From Suser o where  o.userName=?", userName);
		if(users!=null&&users.size()==1)
			return users.get(0);
		return null;
	}

}
