package com.cnu.iqas.bean.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午1:08:03
* 类说明:好友请求表，存放用户之间发送添加好友请求。
*/
@Entity
@Table(name="t_friendRequest")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class FriendRequest {

	private String id;
	/**
	 * 请求方账号，
	 */
	private String ownUserName; 
	/**
	 *  被请求方账号
	 */
	private String friendUserName;
	
	/**
	 * 备注
	 */
	private String content;
	/**
	 * 请求方的头像路径
	 */
	private String picturePath;
	/**
	 * 请求是否被处理，初始值false,未被处理
	 */
	private boolean isHandle=false;
	/**
	 * 时间
	 */
	private Date createTime=new Date();
	
	
	@Id @GeneratedValue(generator="uuidGenderator")
	@Column(length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(nullable=true)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	@Column(nullable=false)
	public boolean getIsHandle() {
		return isHandle;
	}
	public void setIsHandle(boolean isHandle) {
		this.isHandle = isHandle;
	}
	@Column(nullable=false)
	public String getOwnUserName() {
		return ownUserName;
	}
	public void setOwnUserName(String ownUserName) {
		this.ownUserName = ownUserName;
	}
	@Column(nullable=false)
	public String getFriendUserName() {
		return friendUserName;
	}
	public void setFriendUserName(String friendUserName) {
		this.friendUserName = friendUserName;
	}

	
}
