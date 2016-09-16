package com.cnu.iqas.service.iword.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.VersionWordCount;
import com.cnu.iqas.dao.iword.IwordDao;
import com.cnu.iqas.service.iword.IwordService;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 下午10:42:38
* 类说明
*/
@Service("iwordService")
public class IwordServiceImpl  implements IwordService {

	private IwordDao iwordDao;
	/**
	 * 批量插入，
	 */
	@Override
	public void batchSave(final List<Iword> iwords){
		iwordDao.batchSave(iwords);
	}

	@Override
	public List<VersionWordCount> statisticsVersionAndWordCount() {
		
		return iwordDao.statisticsVersionAndWordCount();
	}
	@Override
	public void save(Iword word) {
		iwordDao.save(word);
		
	}

	@Override
	public QueryResult<Iword> getScrollData(int firstResult, int maxresult, String string, Object[] array,
			LinkedHashMap<String, String> orderby) {
		// TODO Auto-generated method stub
		return iwordDao.getScrollData(firstResult, maxresult, string, array, orderby);
	}

	@Override
	public Iword find(String uuid) {
		// TODO Auto-generated method stub
		return iwordDao.find(uuid);
	}
	public IwordDao getIwordDao() {
		return iwordDao;
	}
	@Resource
	public void setIwordDao(IwordDao iwordDao) {
		this.iwordDao = iwordDao;
	}

	@Override
	public QueryResult<Iword> getScrollData(int firstindex, int maxresult) {
		// TODO Auto-generated method stub
		return iwordDao.getScrollData(firstindex, maxresult);
	}

	@Override
	public Iword find(String wherejpql, Object attribute) {
		// TODO Auto-generated method stub
		return iwordDao.find(wherejpql, attribute);
	}

	@Override
	public Iword findWord(String wordStr) {
		// TODO Auto-generated method stub
		String sql ="o.content = ?";
		return iwordDao.find(sql, wordStr);
	}
	
}
