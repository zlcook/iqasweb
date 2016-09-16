package com.user.dao.userword.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.userword.UserWordDao;
import com.user.entity.UserWord;
@Repository("userWordDao")
public class UserWordDaoImpl extends DaoSupport<UserWord> implements UserWordDao {

	@Override
	public List<UserWord> find(String userId, String word) {
		// TODO Auto-generated method stub
		List<UserWord> list = (List<UserWord>) getHt().find("From UserWord o where o.userId=? and o.word=?", userId,word);
		return list;
	}

	@Override
	public List<UserWord> findAll(String userId, String word, int topiclevel) {
		// TODO Auto-generated method stub
		List<UserWord> list = (List<UserWord>) getHt().find("From UserWord o where o.userId=? and o.word=? and o.topicLevel=?",userId,word,topiclevel);
		return list;
	}

	@Override
	public List<UserWord> findlearncount(String word, int topiclevel) {
		// TODO Auto-generated method stub
		List<UserWord> list = (List<UserWord>) getHt().find("From UserWord o where o.word=? and o.topicLevel=?",word,topiclevel);
		return list;
	}

	
}
