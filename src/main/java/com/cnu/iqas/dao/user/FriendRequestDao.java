package com.cnu.iqas.dao.user;

import java.util.List;

import com.cnu.iqas.bean.user.FriendRequest;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午4:05:05
* 类说明，好友请求数据访问类
*/
public interface FriendRequestDao extends DAO<FriendRequest> {

	/**
	 * 根据自己用户名和好友用户名查看之前的请求记录，如果之前不存在请求记录则返回空
	 * @param ownUserName
	 * @param friendUserName
	 * @return
	 */
	public FriendRequest findRequest(String ownUserName, String friendUserName);
	/**
	 * 获取好友发给用户的未被处理过的添加请求
	 * @param userName
	 * @return
	 */
	public List<FriendRequest> fetchRequests(String userName);
	

}
