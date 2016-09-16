package com.user.dao.userresource.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.userresource.UserResourceDao;
import com.user.entity.UserResource;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 上午11:31:58 
 * 类说明 
*/
@Repository("userResourceDao")
public class UserResourceDaoImpl extends DaoSupport<UserResource> implements UserResourceDao {

	@Override
	public UserResource findUserResource(String wherejpql, Object[] attribute) {
		// TODO Auto-generated method stub
		HibernateTemplate ht = this.getHt();
		List<UserResource> userResource = (List<UserResource>) ht.find(wherejpql,attribute);
		UserResource uResource = userResource.get(0);
		return uResource;
	}

	/*@Override
	public UserResource findCurrentLearnState(String userId, String resourceId) {
		// TODO Auto-generated method stub
		List<UserResource> list = getHt().find("From UserResource o where o.userId=? and resourceId=? and learnState=1",userId,resourceId); 
		if(list != null && list.size()>0)
			return list.get(0);
		return null;
	}*/

	@Override
	public List<UserResource> find(String userId) {
		// TODO Auto-generated method stub
		List<UserResource> list = (List<UserResource>) getHt().find("From UserResource o where o.userId=?",userId);
		if(list != null && list.size() > 0)
			return list;
		return null;
	}

	@Override
	public List<UserResource> findByUserMedia(String userId, int mediaType) {
		// TODO Auto-generated method stub
		List<UserResource> list = (List<UserResource>) getHt().find("From UserResource o where o.userId=? and o.mediaType=?",userId,mediaType);
		return list;
	}

	@Override
	public List<UserResource> findByMedia(int mediaType) {
		// TODO Auto-generated method stub
		List<UserResource> list = (List<UserResource>) getHt().find("From UserResource o where o.mediaType=?",mediaType);
		return list;
	}


	@Override
	public UserResource find(String userId, String word, String resourceId, int mediaType) {
		// TODO Auto-generated method stub
		List<UserResource> list = (List<UserResource>) getHt().find("From UserResource o where o.userId=? and o.word=? and o.resourceId=? and o.mediaType=?",userId,word,resourceId,mediaType);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

}
