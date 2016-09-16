package com.cnu.iqas.dao.iword.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.WordAttributeResource;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.iword.WordAttributeResourceDao;

/**
* @author 周亮 
* @version 创建时间：2016年1月8日 下午4:31:18
* 类说明
*/
@Repository("wordAttributeResourceDao")
public class WordAttributeResourceDaoImpl extends DaoSupport<WordAttributeResource>implements WordAttributeResourceDao {

	@Override
	public List<WordAttributeResource> findByWord(String wordId, int type) {
		return (List<WordAttributeResource>) getHt().find("From WordAttributeResource o where o.wordId=? and o.type=?", wordId,type);
	}

	
}
