package com.cnu.offline.dao.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.offline.bean.ExpandWord;
import com.cnu.offline.bean.Word;
import com.cnu.offline.dao.ExpandWordDao;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午10:33:43
* 类说明
*/
@Repository("expandWordDao")
public class ExpandWordDaoImpl extends DaoSupport<ExpandWord>implements ExpandWordDao {

	@Override
	public void saveOrUpdate(ExpandWord entity) {
		// TODO Auto-generated method stub
		if(entity ==null)
			throw new RuntimeException("saveOrUpdate操作的ExpandWord为null");
		ExpandWord wr =find(entity.getWord());
		if(wr!=null)
			update(entity);
		else
			save(entity);
	}

}
