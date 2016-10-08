package com.cnu.offline.dao.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.dao.OffLineBagDao;

/**
* @author 周亮 
* @version 创建时间：2016年9月10日 下午2:59:21
* 类说明
*/
@Repository("offLineBagDao")
public class OffLineBagDaoImpl extends DaoSupport<OffLineBag>implements OffLineBagDao {

	@Override
	public OffLineBag find(String themenumber, int recommendGrade, int realGrade) {
		
		List<OffLineBag> bags= (List<OffLineBag>) getHt().find("from OffLineBag o where o.themenumber=? and o.recommendGrade=? and o.realGrade=? ", new Object[]{themenumber, recommendGrade , realGrade});
		if( bags!=null&& bags.size()==1)
			return bags.get(0);
		return null;
	}
	/**
	 * 判断主题的主离线包是否存在
	 * @param themenumber 主题
	 * @param realGrade 实际年级
	 * @return true:存在,false不存在
	 */
	public boolean existMasterBag(String themenumber, int realGrade){
		
		List<OffLineBag> bags= (List<OffLineBag>) getHt().find("from OffLineBag o where o.themenumber=? and o.realGrade=? and o.recommendGrade=? ", new Object[]{themenumber , realGrade,realGrade});
		if( bags!=null&& bags.size()>=1)
			return true;
		return false;
	}
}
