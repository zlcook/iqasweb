package com.cnu.iqas.service.Recommend.Impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.bean.Recommend.Question;
import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.Recommend.AnswerDao;
import com.cnu.iqas.service.Recommend.AnswerService;
@Service("answerService")
public class AnswerServiceImpl implements AnswerService{
	
    private AnswerDao answerDao;
    @Override
	public void save(Answer answer) {
		System.out.println("answer save");
		answerDao.save(answer);
		System.out.println("answer saved!!!!!");
		}
	public AnswerDao getAnswerDao() {
		return answerDao;
	}
	@Resource
	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}
	@Override
	public Answer find(String wherejpql, Object attribute) {
		// TODO Auto-generated method stub
		return answerDao.find(wherejpql, attribute);
	}
	@Override
	public List<Answer> getAllData(String wherejpql, Object[] queryParams) {
		// TODO Auto-generated method stub
		return answerDao.getAllData(wherejpql, queryParams);
	}
	@Override
	public int updateAnswer(Answer answer) {
		// TODO Auto-generated method stub
		return answerDao.updateAnswer(answer);
	}
	@Override
	public QueryResult<Answer> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams) {
		// TODO Auto-generated method stub
		return answerDao.getScrollData(firstindex, maxresult, wherejpql, queryParams);
	}
	
	
}