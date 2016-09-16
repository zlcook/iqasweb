package com.cnu.iqas.service.Recommend.Impl;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cnu.iqas.bean.Recommend.QuestionAnswer;
import com.cnu.iqas.dao.Recommend.QuestionAnswerDao;
import com.cnu.iqas.service.Recommend.QuestionAnswerService;
@Service("questionAnswerService")
public class QuestionAnswerServiceImpl implements QuestionAnswerService{
	
    private QuestionAnswerDao questionAnswerDao;

	@Override
	public void save(QuestionAnswer questionAnswer) {
		// TODO Auto-generated method stub
		questionAnswerDao.save(questionAnswer);
	}
	public QuestionAnswerDao getQuestionAnswerDao() {
		return questionAnswerDao;
	}
	@Resource
	public void setQuestionAnswerDao(QuestionAnswerDao questionAnswerDao) {
		this.questionAnswerDao = questionAnswerDao;
	}
	@Override
	public QuestionAnswer find(String wherejpql, Object attribute) {
		// TODO Auto-generated method stub
		return questionAnswerDao.find(wherejpql, attribute);
	}
	@Override
	public List<QuestionAnswer> getAllData(String wherejpql, Object[] queryParams) {
		// TODO Auto-generated method stub
		return questionAnswerDao.getAllData(wherejpql, queryParams);
	}
}