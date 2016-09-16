package com.cnu.iqas.vo.mobile;
/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午3:44:34
* 类说明   移动端好友管理vo类
*/
public class FriendVoManage {
	
	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 好友用户名
	 */
	private String friendUserName;
	/**
	 * 发送添加好友请求时填写的备注
	 */
	private String content;
	/**
	 * 好友请求记录id
	 */
	private String requestId;
	/**
	 * 好友关系id
	 */
	private String friendRelId;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFriendUserName() {
		return friendUserName;
	}
	public void setFriendUserName(String friendUserName) {
		this.friendUserName = friendUserName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getFriendRelId() {
		return friendRelId;
	}
	public void setFriendRelId(String friendRelId) {
		this.friendRelId = friendRelId;
	}
	
	

}
