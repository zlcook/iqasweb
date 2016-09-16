package com.cnu.iqas.dao.ios.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.ios.SuserLogin;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.ios.SUserLoginDao;

/**
* @author 周亮 
* @version 创建时间：2016年3月1日 下午4:48:19
* 类说明
*/
@Repository("suserLoginDao")
public class SUserLoginDaoImpl extends DaoSupport<SuserLogin> implements SUserLoginDao {

	@Override
	public SuserLogin findCurrentLogin(String userId, String ip) {
		List<SuserLogin> list =(List<SuserLogin>) getHt().find(" From SuserLogin o where o.userId=? and ip =? and loginState=1", userId,ip);
		
		if( list !=null && list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public List<SuserLogin> findLogining(String userId) {
		List<SuserLogin> list =(List<SuserLogin>) getHt().find(" From SuserLogin o where o.userId=? and loginState=1", userId);
		return list;
	}


}
