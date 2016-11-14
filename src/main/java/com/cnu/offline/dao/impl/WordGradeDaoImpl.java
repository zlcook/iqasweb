package com.cnu.offline.dao.impl;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.offline.bean.Word;
import com.cnu.offline.bean.WordGrade;
import com.cnu.offline.dao.WordGradeDao;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 下午2:22:01
* 类说明
*/
@Repository
public class WordGradeDaoImpl extends DaoSupport<WordGrade>implements WordGradeDao {

	@Override
	public void saveOrUpdate(WordGrade wordGrade) {
		if(wordGrade ==null)
			throw new RuntimeException("saveOrUpdate操作的wordGrade为null");
		WordGrade wr =find(wordGrade.getId());
		if(wr!=null)
			update(wordGrade);
		else
			save(wordGrade);
	}

}
