package com.user.dao.candidatetestwords.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.candidatetestwords.CandidateTestWordsDao;
import com.user.entity.CandidateTestWords;
import com.user.entity.TestPreference;
@Repository("candidateTestWordsDao")
public class CandidateTestWordsDaoImpl extends DaoSupport<CandidateTestWords> implements CandidateTestWordsDao {

	@Override
	public CandidateTestWords find(String userId, int difficulty) {
		// TODO Auto-generated method stub
		List<CandidateTestWords> list = (List<CandidateTestWords>) getHt().find("From CandidateTestWords o where o.userId=? and o.difficulty=?", userId,difficulty);
		if(list != null && list.size() > 0)
		{
			this.delete(list.get(0).getId());
			return list.get(0);
		}
		return null;
	}

	@Override
	public CandidateTestWords find(String userId, String word) {
		// TODO Auto-generated method stub
		List<CandidateTestWords> list = (List<CandidateTestWords>) getHt().find("From CandidateTestWords o where o.userId=? and o.word=?", userId,word);
		if(list != null && list.size() > 0) {
			
			System.out.println("存在！");
			System.out.println(list.get(0).getUserId() + "/" + list.get(0).getWord());
			return list.get(0);
		}
	    return null;
	}

}
