package com.cnu.iqas.service.stroe;

import java.util.List;

import com.cnu.iqas.bean.store.UserCommodityRel;

/**
* @author 周亮 
* @version 创建时间：2015年12月26日 下午1:19:41
* 类说明 用户购买商品关系接口
*/
public interface UserCommodityRelService {

	/**
	 * 查看用户在某商品类型下所购买的商品
	 * @param userId  用户id
	 * @param typeid  商品类型id
	 * @return
	 */
	public List<UserCommodityRel> findUserCommodityRels(String userId, String typeid);
	
	/**
	 * 根据用户id和商品id查看对该商品的购买记录
	 * @param userId 用户id
	 * @param coId 商品id
	 * @return
	 */
	public UserCommodityRel findUserCommodityRel(String userId,String coId);
}
