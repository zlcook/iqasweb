package com.user.dao.testpreference.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.testpreference.TestPreferenceDao;

import com.user.entity.TestPreference;
@Repository("testPreferenceDao")
public class TestPreferenceDaoImpl extends DaoSupport<TestPreference> implements TestPreferenceDao {

	@Override
	public TestPreference find(String userId, String feature, int featureValue) {
		// TODO Auto-generated method stub
		double[] tpFeedback = new double[3];

        List<TestPreference> tpList = (List<TestPreference>) getHt().find("From TestPreference o where o.userId=? and o.feature=? and o.featureValue=?", userId,feature,featureValue);
        if(tpList.size() > 0 && tpList != null)
        	{
            	System.out.println("find!");
        	    return tpList.get(0);
        	}
		return null;
	}

	
}
