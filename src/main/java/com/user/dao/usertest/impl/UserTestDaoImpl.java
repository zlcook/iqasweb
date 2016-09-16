package com.user.dao.usertest.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.usertest.UserTestDao;
import com.user.entity.TestPreference;
import com.user.entity.UserTest;
@Repository("userTestDao")
public class UserTestDaoImpl extends DaoSupport<UserTest> implements UserTestDao {

	@Override
	public List<UserTest> find(String userId, int flag) {
		// TODO Auto-generated method stub
		if(flag == 0)
		{
			List<UserTest> list = (List<UserTest>) getHt().find("From UserTest o where o.userId=? and o.totalTimes=0", userId);
			return list;
		}
		else
		{
			List<UserTest> list = (List<UserTest>) getHt().find("From UserTest o where o.userId=? and o.rightTimes=1", userId);
			return list;
		}
	}


}
