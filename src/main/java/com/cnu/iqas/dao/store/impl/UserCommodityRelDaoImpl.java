package com.cnu.iqas.dao.store.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.Commodity;
import com.cnu.iqas.bean.store.UserCommodityRel;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.store.UserCommodityRelDao;

/**
* @author 周亮 
* @version 创建时间：2015年12月25日 下午1:48:04
* 类说明   用户商品关系数据操作接口实现类，用于操作用户商品关系表的数据
*/
@Repository("userCommodityRelDao")
public class UserCommodityRelDaoImpl extends DaoSupport<UserCommodityRel>implements UserCommodityRelDao {

	@Override
	public List<UserCommodityRel> findUserCommodityRels(String userId, String typeId) {
		String hql = " from UserCommodityRel where userId =? and typeId=?";
		List<UserCommodityRel> list= (List<UserCommodityRel>) getHt().find(hql, userId,typeId);
		return list;
	}
	/**
	 * 根据用户id和商品id查看对该商品的购买记录
	 * @param userId 用户id
	 * @param coId 商品id
	 * @return 返回购买商品记录UserCommodityRel
	 */
	@Override
	public UserCommodityRel findUserCommodityRel(String userId, String coId) {
		// TODO Auto-generated method stub
		String hql = " from UserCommodityRel where userId =? and coId=?";
		List<UserCommodityRel> list= (List<UserCommodityRel>) getHt().find(hql, userId,coId);
	
		if( list!=null &&list.size()>0)
			return list.get(0);
		return null;
	}

}
