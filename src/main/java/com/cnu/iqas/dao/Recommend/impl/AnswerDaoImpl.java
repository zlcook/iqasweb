package com.cnu.iqas.dao.Recommend.impl;
import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.dao.Recommend.AnswerDao;
import com.cnu.iqas.dao.base.DaoSupport;
@Repository("answerDao")
public class AnswerDaoImpl extends DaoSupport<Answer>implements AnswerDao {
private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Override
	public int updateAnswer(Answer answer) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(answer);
		return answer.getAnswerId();
	}

}