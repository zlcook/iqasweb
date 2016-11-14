package com.cnu.ds.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.userbehaviour.UserBehaviourDao;
import com.user.dao.userresource.UserResourceDao;
import com.user.dao.userword.UserWordDao;
import com.user.entity.UserBehaviour;
import com.user.entity.UserResource;
import com.user.entity.UserWord;

/**
* @author 周亮 
* @version 创建时间：2016年10月25日 下午7:10:58
* 类说明
*/
@Service("androidDataSynService")
public class AndroidDataSynService {
	
	private Logger loger = LogManager.getLogger(AndroidDataSynService.class);
	private UserWordDao userWordDao;
	private UserBehaviourDao userBehaviourDao;
	private UserResourceDao userResourceDao;

	@Transactional
	public boolean tableDataSyn(List<UserWord> uws,List<UserBehaviour> ubs,List<UserResource> urs){
		
		boolean flage = false;
		try{
			if( uws !=null)
			for( UserWord uw : uws)
				userWordDao.save(uw);
			if( ubs !=null)
			for( UserBehaviour ub : ubs)
				userBehaviourDao.save(ub);
			if( urs!=null )
				for( UserResource ur : urs)
					userResourceDao.save(ur);
			flage = true;
		}catch(Exception e){
			e.printStackTrace();
			loger.error("数据同步失败:"+e.getMessage());
		}
		return flage;
	}

	public UserWordDao getUserWordDao() {
		return userWordDao;
	}
	@Resource
	public void setUserWordDao(UserWordDao userWordDao) {
		this.userWordDao = userWordDao;
	}
	public UserBehaviourDao getUserBehaviourDao() {
		return userBehaviourDao;
	}
	@Resource
	public void setUserBehaviourDao(UserBehaviourDao userBehaviourDao) {
		this.userBehaviourDao = userBehaviourDao;
	}
	public UserResourceDao getUserResourceDao() {
		return userResourceDao;
	}
	@Resource
	public void setUserResourceDao(UserResourceDao userResourceDao) {
		this.userResourceDao = userResourceDao;
	}
	
}
