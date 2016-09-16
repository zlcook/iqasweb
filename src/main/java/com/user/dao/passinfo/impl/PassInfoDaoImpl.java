package com.user.dao.passinfo.impl;

import java.io.Serializable;
import com.cnu.iqas.dao.base.DaoSupport;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.user.dao.passinfo.PassInfoDao;
import com.user.entity.PassInfo;
@Repository("passInfoDao")
public class PassInfoDaoImpl extends DaoSupport<PassInfo> implements PassInfoDao {

	@Override
	public PassInfo find(String userId, String topic) {
		// TODO Auto-generated method stub
		List<PassInfo> list = (List<PassInfo>) getHt().find("From PassInfo o where o.userId=? and o.topic=?", userId,topic);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public List<PassInfo> find(String userId) {
		// TODO Auto-generated method stub
		List<PassInfo> list = (List<PassInfo>) getHt().find("From PassInfo o where o.userId=?", userId);
		return list;
	}
 

}
