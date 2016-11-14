package com.cnu.offline.dao.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.offline.bean.ExpandWord;
import com.cnu.offline.bean.Wresource;
import com.cnu.offline.dao.WresourceDao;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午10:52:09
* 类说明
*/
@Repository
public class WresourceDaoImpl extends DaoSupport<Wresource>implements WresourceDao {

	/*@Override
	public void saveOrUpdate(Wresource entity) {
		if(entity ==null)
			throw new RuntimeException("saveOrUpdate操作的Wresource为null");
		Wresource wr =find(entity.getWord());
		if(wr!=null)
			update(entity);
		else
			save(entity);
	}*/

}
