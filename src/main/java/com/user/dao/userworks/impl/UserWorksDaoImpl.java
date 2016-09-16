package com.user.dao.userworks.impl;


import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.userworks.UserWorksDao;
import com.user.entity.UserWorks;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 下午8:53:26 
 * 类说明 
*/
@Repository("userWorksDao")
public class UserWorksDaoImpl extends DaoSupport<UserWorks> implements UserWorksDao {

	@Override
	public UserWorks findUserWorks(String wherejpql, Object[] attribute) {
		// TODO Auto-generated method stub
		HibernateTemplate ht = this.getHt();
		List<UserWorks> userWorks = (List<UserWorks>) ht.find(wherejpql, attribute);
		UserWorks uWorks = userWorks.get(0);
		return uWorks;
	}

	@Override
	public List<UserWorks> find(String worksName, String userId) {
		// TODO Auto-generated method stub
		List<UserWorks> uwlist = (List<UserWorks>) getHt().find("From UserWorks o where o.worksName = ? and o.userId = ?", worksName,userId);
		if(uwlist != null && uwlist.size() > 0)
			return uwlist;
		return null;
	}

}
