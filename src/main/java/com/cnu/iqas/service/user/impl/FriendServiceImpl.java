package com.cnu.iqas.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.user.Friend;
import com.cnu.iqas.bean.user.FriendRequest;
import com.cnu.iqas.dao.user.FriendDao;
import com.cnu.iqas.dao.user.FriendRequestDao;
import com.cnu.iqas.service.user.FriendService;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午4:03:07
* 类说明:好友服务实现类
*/
@Service("friendService")
public class FriendServiceImpl implements FriendService{

	/**
	 * 好友关系表数据访问类
	 */
	private FriendDao friendDao;
	/**
	 * 好友请求关系表数据访问类
	 */
	private FriendRequestDao friendRequestDao;
	
	
	@Override
	public boolean sendAddFriendRequest(String ownUserName, String friendUserName, String note) {
		
		if(validateStr(ownUserName) && validateStr(friendUserName))
		{
			//建立一个新的请求
			FriendRequest addRequest = new FriendRequest();
			//备注
			if(note==null)
			  addRequest.setContent("");
			else
			  addRequest.setContent(note);
			addRequest.setFriendUserName(friendUserName);
			addRequest.setOwnUserName(ownUserName);
			friendRequestDao.save(addRequest);
			return true;
		}
		
		return false;
	}

	@Override
	public List<FriendRequest> fetchRequests(String userName) {
		List<FriendRequest> list = null;
		// TODO Auto-generated method stub
		if( validateStr(userName)){
		    list =friendRequestDao.fetchRequests(userName);
		}
		
		return list;
	}


	/**
	 * 添加好友并更新请求状态,,,具有事务性
	 * @param ownUserName     自己的用户名
	 * @param friendUserName  好友的用户名
	 * @param request  好友请求
	 * @return
	 */
	@Override
	@Transactional
	public boolean addFriendUpdateRequest(String ownUserName, String friendUserName,FriendRequest request) {
		
		if( validateStr(ownUserName)&& validateStr(friendUserName)){
			
			//构造好友关系
			Friend friend = new Friend();
			friend.setOneUserName(ownUserName);
			friend.setOtherUserName(friendUserName);
			//保存
			friendDao.save(friend);
			//更新请求状态
			friendRequestDao.update(request);
			
		}else{
			throw new RuntimeException("参数不能为空!");
		}
		return false;
	}

	@Override
	public boolean isExistFriend(String ownUserName, String friendUserName) {
		if( validateStr(ownUserName)&& validateStr(friendUserName)){
			//根据用户名和好友名查找关系
			Friend friend =friendDao.findFriendRel(ownUserName,friendUserName);
			if( friend !=null)
				return true;
			
		}
		return false;
	}

	
	public FriendDao getFriendDao() {
		return friendDao;
	}

	@Resource
	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	public FriendRequestDao getFriendRequestDao() {
		return friendRequestDao;
	}

	@Resource
	public void setFriendRequestDao(FriendRequestDao friendRequestDao) {
		this.friendRequestDao = friendRequestDao;
	}

	/**
	 * 校验字符串是否为空或空字符串
	 * @param userName
	 * @return：true:有效
	 */
	private boolean validateStr(String str){
		if(str==null || str.trim().equals(""))
			return false;
		else
			return true;
	}

	@Override
	public FriendRequest findRequest(String ownUserName, String friendUserName) {
		if(validateStr(ownUserName)&& validateStr(friendUserName)){
			return friendRequestDao.findRequest(ownUserName,friendUserName);
		}
		return null;
	}

	@Override
	public void updateRequest(FriendRequest frequest) {
		if( frequest !=null&& validateStr(frequest.getFriendUserName())&& validateStr(frequest.getOwnUserName())){
			
			friendRequestDao.update(frequest);
		}else{
			throw new RuntimeException("好友请求不能为空");
		}
		
	}

	@Override
	public FriendRequest findRequest(String requestId) {
		// TODO Auto-generated method stub
		if( validateStr(requestId)){
			return friendRequestDao.find(requestId);
		}
		return null;
	}


	@Override
	public void deleteFriend(String friendRelId) {
		// TODO Auto-generated method stub
		if( validateStr(friendRelId)){
			friendDao.delete(friendRelId);
		}
	}

	@Override
	public List<Friend> myFriends(String userName) {
		if( validateStr(userName)){
			List<String> queryParams = new ArrayList<String>();
			queryParams.add(userName);
			queryParams.add(userName);
			
		    return  friendDao.getAllData("o.oneUserName =? or o.otherUserName=? ", queryParams.toArray());
		}
		return null;
	}
	
}
