package com.cnu.iqas.service.stroe.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.store.UserCommodityRel;
import com.cnu.iqas.dao.store.UserCommodityRelDao;
import com.cnu.iqas.service.stroe.UserCommodityRelService;

/**
* @author 周亮 
* @version 创建时间：2015年12月26日 下午1:19:55
* 类说明 ：用户购买商品关系接口实现类
*/
@Service("UserCommodityRelService")
public class UserCommodityRelServiceImpl implements UserCommodityRelService {

	/**
	 * 用户商品关系数据访问类
	 */
	private UserCommodityRelDao userCommodityRelDao;
	
	
	public List<UserCommodityRel> findUserCommodityRels(String userId, String typeid) {
		if( userId!=null && !"".equals(userId)&& typeid!=null && !"".equals(typeid)){
			return userCommodityRelDao.findUserCommodityRels( userId,  typeid);
		}
		return null;
	}
	@Override
	public UserCommodityRel findUserCommodityRel(String userId, String coId) {
		return userCommodityRelDao.findUserCommodityRel(userId, coId);
	}
	
	public UserCommodityRelDao getUserCommodityRelDao() {
		return userCommodityRelDao;
	}
	@Resource
	public void setUserCommodityRelDao(UserCommodityRelDao userCommodityRelDao) {
		this.userCommodityRelDao = userCommodityRelDao;
	}

	
}
