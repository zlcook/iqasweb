package com.cnu.offline.service.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.offline.bean.WordGrade;
import com.cnu.offline.bean.Wresource;
import com.cnu.offline.dao.WordGradeDao;
import com.cnu.offline.dao.WresourceDao;
import com.cnu.offline.service.WresourceService;

/**
* @author 周亮 
* @version 创建时间：2016年11月6日 下午7:34:22
* 类说明
*/
@Service
public class WresourceServiceImpl implements WresourceService {
	
	private WordGradeDao wordGradeDao;
	private WresourceDao wresouceDao;
	@Override
	public Wresource find(String word) {
		// TODO Auto-generated method stub
		return wresouceDao.find(word);
	}
	@Override
	public List<WordGrade> findWordGrade(String word) {
		// TODO Auto-generated method stub
		String wherejpql="o.id.word=?";
		Object[] queryParams =new Object[]{word};
		return wordGradeDao.getAllData(wherejpql, queryParams);
	}
	
	
	public WresourceDao getWresouceDao() {
		return wresouceDao;
	}
	@Autowired
	public void setWresouceDao(WresourceDao wresouceDao) {
		this.wresouceDao = wresouceDao;
	}
	public WordGradeDao getWordGradeDao() {
		return wordGradeDao;
	}
	@Autowired
	public void setWordGradeDao(WordGradeDao wordGradeDao) {
		this.wordGradeDao = wordGradeDao;
	}
}
