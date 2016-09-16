package com.cnu.iqas.dao.Recommend.impl;
import org.springframework.stereotype.Repository;
import com.cnu.iqas.bean.Recommend.Question;
import com.cnu.iqas.dao.Recommend.QuestionDao;
import com.cnu.iqas.dao.base.DaoSupport;
@Repository("questionDao")
public class QuestionDaoImpl extends DaoSupport<Question>implements QuestionDao {

}