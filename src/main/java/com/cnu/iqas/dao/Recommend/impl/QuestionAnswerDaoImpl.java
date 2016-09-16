package com.cnu.iqas.dao.Recommend.impl;
import org.springframework.stereotype.Repository;
import com.cnu.iqas.bean.Recommend.Question;
import com.cnu.iqas.bean.Recommend.QuestionAnswer;
import com.cnu.iqas.dao.Recommend.QuestionAnswerDao;
import com.cnu.iqas.dao.Recommend.QuestionDao;
import com.cnu.iqas.dao.base.DaoSupport;
@Repository("questionAnswerDao")
public class QuestionAnswerDaoImpl extends DaoSupport<QuestionAnswer>implements QuestionAnswerDao {
}