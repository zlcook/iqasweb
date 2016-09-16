package com.cnu.iqas.dao.user.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.user.Friend;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.user.FriendDao;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午4:42:19
* 类说明
*/
@Repository("friendDao")
public class FriendDaoImpl extends DaoSupport<Friend>implements FriendDao {

	@Override
	public Friend findFriendRel(String oneUserName, String otherUserName) {
		
		List<Friend> list =(List<Friend>) getHt().find("From Friend o where o.oneUserName=? and o.otherUserName=?", oneUserName,otherUserName);
		
		Friend friend= (list!=null && list.size()>0)?list.get(0):null;
		//如果没找到，在调换位置找
		if( friend ==null){
		  list =(List<Friend>) getHt().find("From Friend o where o.oneUserName=? and o.otherUserName=?",otherUserName ,oneUserName);
		  friend= (list!=null && list.size()>0)?list.get(0):null;
		}
		return friend;
	}

	@Override
	public void deleteFriend(String userName, String friendUserName) {
		// TODO Auto-generated method stub
		Friend friend = findFriendRel(userName, friendUserName);
		if( friend !=null)
		   getHt().delete(friend);
	}

}
