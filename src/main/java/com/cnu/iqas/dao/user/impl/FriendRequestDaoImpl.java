
package com.cnu.iqas.dao.user.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.user.FriendRequest;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.user.FriendRequestDao;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午5:08:08
* 类说明
*/
@Repository("friendRequestDao")
public class FriendRequestDaoImpl extends DaoSupport<FriendRequest>implements FriendRequestDao {

	public FriendRequest findRequest(String ownUserName, String friendUserName) {
		List<FriendRequest> list= (List<FriendRequest>) getHt().find("From FriendRequest o where o.ownUserName=? and o.friendUserName=? ", ownUserName,friendUserName);
		
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public List<FriendRequest> fetchRequests(String userName) {
		List<FriendRequest> list= (List<FriendRequest>) getHt().find("From FriendRequest o where o.friendUserName=? and isHandle = ?",userName,false);
		
		return list;
	}

}
