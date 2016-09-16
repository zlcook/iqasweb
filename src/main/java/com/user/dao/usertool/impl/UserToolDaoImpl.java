package com.user.dao.usertool.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.usertool.UserToolDao;
import com.user.entity.UserTool;
@Repository("userToolDao")
public class UserToolDaoImpl extends DaoSupport<UserTool> implements UserToolDao {

	
	@Override
	public UserTool find(String userId, String toolId) {
		// TODO Auto-generated method stub
		List<UserTool> list = (List<UserTool>) getHt().find("From UserTool o where o.userId=? and o.toolId=?", userId,toolId);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public List<UserTool> find(String userId) {
		// TODO Auto-generated method stub
		List<UserTool> list = (List<UserTool>) getHt().find("From UserTool o where o.userId=?", userId);
		return list;
	}

}
