package com.user.dao.userbehaviour.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.userbehaviour.UserBehaviourDao;
import com.user.entity.UserBehaviour;
import com.user.entity.UserTest;
@Repository("userBehaviourDao")
public class UserBehaviourDaoImpl extends DaoSupport<UserBehaviour> implements UserBehaviourDao {

	@Override
	public List<UserBehaviour> find(String userId) {
		// TODO Auto-generated method stub
		List<UserBehaviour> list = (List<UserBehaviour>) getHt().find("From UserBehaviour o where o.userId=?", userId);
		return list;
	}

}
