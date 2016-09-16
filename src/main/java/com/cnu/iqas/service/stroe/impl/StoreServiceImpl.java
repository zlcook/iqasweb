package com.cnu.iqas.service.stroe.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnu.iqas.bean.store.Commodity;
import com.cnu.iqas.bean.store.CommodityType;
import com.cnu.iqas.bean.store.UserCommodityRel;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.dao.store.CommodityDao;
import com.cnu.iqas.dao.store.CommodityTypeDao;
import com.cnu.iqas.dao.store.UserCommodityRelDao;
import com.cnu.iqas.dao.user.UserDao;
import com.cnu.iqas.service.stroe.StoreService;
import com.cnu.iqas.service.stroe.UserCommodityRelService;

/**
* @author 周亮 
* @version 创建时间：2015年12月22日 上午11:15:09
* 类说明 服务中的方法具有事务属性
*/
@Service("storeService")
@Transactional
public class StoreServiceImpl implements StoreService {

	/**
	 * 商品数据访问接口
	 */
	private CommodityDao commodityDao;
	/**
	 * 商品类型数据访问接口
	 */
	private CommodityTypeDao commodityTypeDao;
	/**
	 * 用户商品关系数据访问接口
	 */
	private UserCommodityRelDao userCommodityRelDao;
	/**
	 * 用户数据访问接口
	 */
	private UserDao userDao;
	/**
	 * 保存商品，并更新商品类型表的商品数量
	 * @param commodity
	 * @param type
	 * @return
	 */
	public void saveCommodityAndUpdateType(Commodity commodity,CommodityType type){
		commodityTypeDao.update(type);
		commodityDao.save(commodity);
		
	}

	@Override
	public void updateUserAndCommodity(User user, UserCommodityRel ucRel)  {
		// TODO Auto-generated method stub
		userDao.update(user);
		userCommodityRelDao.update(ucRel);
	}

	public CommodityDao getCommodityDao() {
		return commodityDao;
	}
	@Resource
	public void setCommodityDao(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}
	public CommodityTypeDao getCommodityTypeDao() {
		return commodityTypeDao;
	}
	@Resource
	public void setCommodityTypeDao(CommodityTypeDao commodityTypeDao) {
		this.commodityTypeDao = commodityTypeDao;
	}
	
	public UserCommodityRelDao getUserCommodityRelDao() {
		return userCommodityRelDao;
	}
	@Resource
	public void setUserCommodityRelDao(UserCommodityRelDao userCommodityRelDao) {
		this.userCommodityRelDao = userCommodityRelDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean deleteCommodity(String id) {
		
		Commodity com= commodityDao.find(id);
		if( com !=null){
			commodityDao.delete(id);
			CommodityType type = commodityTypeDao.find(com.getTypeid());
			if( type !=null){
				int count =type.getCount();
				if( count > 0){
					 type.setCount(count-1);
					 commodityTypeDao.update(type);
					 return true;
				}
			}
		}
		return false;
	}

	
}
