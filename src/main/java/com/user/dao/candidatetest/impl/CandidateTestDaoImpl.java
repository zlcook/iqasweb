package com.user.dao.candidatetest.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.candidatetest.CandidateTestDao;
import com.user.entity.CandidateTest;
import com.user.entity.TestPreference;
@Repository("candidateTestDao")
public class CandidateTestDaoImpl extends DaoSupport<CandidateTest> implements CandidateTestDao {

	@Override
	public List<CandidateTest> getCandidate(String userId) {
		// TODO Auto-generated method stub
		List<CandidateTest> list = (List<CandidateTest>) getHt().find("From CandidateTest o where o.userId=? and o.candidate=1",userId);
		if(list != null && list.size() >= 5)
			return list;
		return null;
	}

	@Override
	public CandidateTest find(String userId, int testType, int testAspect, int testDifficulty) {
		// TODO Auto-generated method stub
		List<CandidateTest> list = (List<CandidateTest>) getHt().find("From CandidateTest o where o.userId=? and o.testType=? and o.testAspect=? and o.testDifficulty=?",userId,testType,testAspect,testDifficulty);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	
}
