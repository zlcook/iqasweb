package com.cnu.iqas.service.Recommend.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.Recommend.Question;
import com.cnu.iqas.dao.Recommend.QuestionDao;
import com.cnu.iqas.service.Recommend.QuestionService;
@Service("questionService")
public class QuestionServiceImpl implements QuestionService{
	
    private QuestionDao questionDao;
	@Override
	public void save(Question questiuon) {
		
		questionDao.save(questiuon);
	}
	public QuestionDao getQuestionDao() {
		return questionDao;
	}
	@Resource
	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}
	@Override
	public Question find(String wherejpql, Object attribute) {
		// TODO Auto-generated method stub
		return questionDao.find(wherejpql, attribute);
	}
}