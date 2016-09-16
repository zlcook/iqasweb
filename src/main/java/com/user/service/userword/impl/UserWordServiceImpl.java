package com.user.service.userword.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.userword.UserWordDao;
import com.user.entity.UserWord;
import com.user.service.userword.UserWordService;
@Service("userWordService")
public class UserWordServiceImpl implements UserWordService {
    private UserWordDao userWordDao;
    
	public UserWordDao getUserWordDao() {
		return userWordDao;
	}
	@Resource
	public void setUserWordDao(UserWordDao userWordDao) {
		this.userWordDao = userWordDao;
	}
	@Override
	public void add(String userId, String word, int topicLevel) {
		// TODO Auto-generated method stub
		List<UserWord> list = userWordDao.find(userId, word);
		UserWord uw;
		if(list == null || list.size() == 0)
		    uw = new UserWord(userId,word,topicLevel,1,0,new Date());
		else
			uw = new UserWord(userId,word,topicLevel,list.size()+1,0,new Date());
		userWordDao.save(uw);
	}
	@Override
	public List<UserWord> find(String userId, String word) {
		// TODO Auto-generated method stub
		return userWordDao.find(userId, word);
	}
	@Override
	public int learnCount(String userId, String word) {
		// TODO Auto-generated method stub
		List<UserWord> list = userWordDao.find(userId, word);
		if(list != null && list.size() > 0)
			return list.size();
		return 0;
	}




}
