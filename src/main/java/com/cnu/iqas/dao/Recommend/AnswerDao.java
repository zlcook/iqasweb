package com.cnu.iqas.dao.Recommend;
import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.dao.base.DAO;
/**
* @author  王文辉 
* @version 创建时间：2016年4月20日 下午6:40:43
* 类说明
* */
 public interface AnswerDao extends DAO<Answer> {	
	 
	 public int updateAnswer(Answer answer);
	 
}
