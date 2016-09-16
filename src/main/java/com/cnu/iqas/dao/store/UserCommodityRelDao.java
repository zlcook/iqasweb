package com.cnu.iqas.dao.store;

import java.util.List;

import com.cnu.iqas.bean.store.Commodity;
import com.cnu.iqas.bean.store.UserCommodityRel;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2015年12月25日 下午1:45:51
* 类说明   用户商品关系数据操作接口，用于操作用户商品关系表的数据
*/
public interface UserCommodityRelDao extends DAO<UserCommodityRel> {
	/**
	 * 查看用户在某商品类型下所购买的商品信息
	 * @param userId  用户id
	 * @param typeId  商品类型id
	 * @return
	 */
	public List<UserCommodityRel> findUserCommodityRels(String userId, String typeId);
	/**
	 * 根据用户id和商品id查看对该商品的购买记录
	 * @param userId 用户id
	 * @param coId 商品id
	 * @return
	 */
	public UserCommodityRel findUserCommodityRel(String userId,String coId);
}
