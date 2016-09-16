package com.cnu.iqas.dao.user;

import com.cnu.iqas.bean.user.Friend;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午4:06:12
* 类说明:好友关系表数据访问类
*/
public interface FriendDao extends DAO<Friend> {
	/**
	 * 根据用户名和好友名查找好友关系
	 * @param oneUserName
	 * @param otherUserName
	 * @return
	 */
	Friend findFriendRel(String oneUserName, String otherUserName);
	/**
	 * 删除好友
	 * @param userName
	 * @param friendUserName
	 */
	void deleteFriend(String userName, String friendUserName);

}
