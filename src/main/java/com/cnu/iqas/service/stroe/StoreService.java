package com.cnu.iqas.service.stroe;

import java.util.List;

import com.cnu.iqas.bean.store.Commodity;
import com.cnu.iqas.bean.store.CommodityType;
import com.cnu.iqas.bean.store.UserCommodityRel;
import com.cnu.iqas.bean.user.User;

/**
* @author 周亮 
* @version 创建时间：2015年12月22日 上午11:14:50
* 类说明 ,商品服务类，功能有：
* 1.保存商品，并更新商品类型表的商品数量
* 2.查看用户在某商品类型下所购买的商品
*/
public interface StoreService {
	
	/**
	 * 保存商品，并更新商品类型表的商品数量
	 * @param commodity
	 * @param type
	 * @return
	 */
	public void saveCommodityAndUpdateType(Commodity commodity,CommodityType type);
	/**
	 * 更新用户数据和购物记录
	 * @param user 用户
	 * @param ucRel 购买记录
	 */
	public void updateUserAndCommodity(User user, UserCommodityRel ucRel) ;
	/**
	 * 根据商品id删除商品
	 * @param id
	 * @return
	 */
	public boolean deleteCommodity(String id);

}
