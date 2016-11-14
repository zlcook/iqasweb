package com.cnu.offline.dao.impl;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.offline.bean.Word;
import com.cnu.offline.dao.WordDao;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午10:34:25
* 类说明
*/

@Repository
public class WordDaoImpl extends DaoSupport<Word> implements WordDao {

	@Override
	public void saveOrUpdate(Word word) {
		// TODO Auto-generated method stub
		if(word ==null)
			throw new RuntimeException("saveOrUpdate操作的word为null");
		Word wr =find(word.getWord());
		if(wr!=null)
			update(word);
		else
			save(word);
	}

}
