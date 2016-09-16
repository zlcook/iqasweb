package com.cnu.iqas.service.user;

import java.util.List;

import com.cnu.iqas.bean.user.Friend;
import com.cnu.iqas.bean.user.FriendRequest;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午3:52:28
* 类说明
*/
public interface FriendService {

	/**
	 * 发送添加好友请求
	 * @param ownUserName 自己用户名
	 * @param friendUserName 好友用户名
	 * @param note 备注
	 * @return
	 */
	public boolean sendAddFriendRequest(String ownUserName,String friendUserName,String note);
	/**
	 * 获取好友发给用户的未被处理过的添加请求
	 * @param userName 用户名
	 * @return
	 */
	public List<FriendRequest> fetchRequests(String userName);
	/**
	 * 添加好友并更新请求状态,,,具有事务性
	 * @param ownUserName     自己的用户名
	 * @param friendUserName  好友的用户名
	 * @param request  好友请求
	 * @return
	 */
	public boolean addFriendUpdateRequest(String ownUserName,String friendUserName,FriendRequest request);
	
	/**
	 * 检查好友是否已存在
	 * @param ownUserName
	 * @param friendUserName
	 * @return true:存在，false：不存在
	 */
	public boolean isExistFriend(String ownUserName,String friendUserName);
	/**
	 * 根据自己用户名和好友用户名查看之前的请求记录，如果之前不存在请求记录则返回空
	 * @param ownUserName
	 * @param friendUserName
	 * @return
	 */
	public FriendRequest findRequest(String ownUserName, String friendUserName);
	/**
	 * 更新用户发送好友请求的处理状态。
	 * @param frequest
	 */
	public void updateRequest(FriendRequest frequest);
	/**
	 * 根据请求id查看好友请求记录
	 * @param requestId
	 * @return
	 */
	public FriendRequest findRequest(String requestId);
	
	/**
	 * 根据id删除好友关系
	 * @param friendRelId
	 */
	public void deleteFriend(String friendRelId);
	/**
	 * 我的好友
	 * @param userName
	 * @return
	 */
	public List<Friend> myFriends(String userName);
	
	
}
