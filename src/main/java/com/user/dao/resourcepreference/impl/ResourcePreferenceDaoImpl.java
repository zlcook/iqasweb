package com.user.dao.resourcepreference.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.resourcepreference.ResourcePreferenceDao;
import com.user.entity.ResourcePreference;
import com.user.entity.UserBehaviour;
@Repository("resourcePreferenceDao")
public class ResourcePreferenceDaoImpl extends DaoSupport<ResourcePreference> implements ResourcePreferenceDao {

	@Override
	public ResourcePreference find(String userId, String feature, String featureValue) {
		// TODO Auto-generated method stub
		List<ResourcePreference> list = (List<ResourcePreference>) getHt().find("From ResourcePreference o where o.userId=? and o.feature=? and o.featureValue=?", userId,feature,featureValue);
		if(list != null && list.size() > 0)
			return list.get(0);
		else return null;
			
	}


}
