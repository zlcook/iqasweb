package com.user.entity;

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
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 上午9:33:47 
 * 类说明 
*/
@Entity
@Table(name="t_userlogininformation")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class UserLogin {

	/**
	 * 登录中
	 * */
	public static int LOGINING=1;
	/**
	 * 正常退出
	 * */
	public static int NORMAL_LOGOUT=2;
	/**
	 * 异常退出
	 * */
	public static int UNNORMAL_NOLOGOUT=3;
	/**
	 * 标识id
	 * */
	private String loginId;	
	/**
	 * 用户id
	 * */
	private String userId;	
/*	*//**
	 * 用户名
	 * *//*
	private String userName;*/
	/**
	 * 登录ip地址
	 * */
	private String ip;
	/**
	 * 登录时间
	 * */
	private Date loginTime=new Date();	
	/**
	 * 退出时间
	 * */
	private Date loginoutTime=null;
	/**
	 * 登录状态 1：登录中；2：退出登录；3：非正常退出，默认值1
	 * */
	private Integer loginState=LOGINING;		
	
	public UserLogin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserLogin(String loginId, String userId, String ip, Date loginTime, Date loginoutTime,
			Integer loginState) {
		super();
		this.loginId = loginId;
		this.userId = userId;
	//	this.userName = userName;
		this.ip = ip;
		this.loginTime = loginTime;
		this.loginoutTime = loginoutTime;
		this.loginState = loginState;
	}

	@Id @GeneratedValue(generator="uuidGenderator")
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	@Column(nullable=false)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/*public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}*/
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLoginoutTime() {
		return loginoutTime;
	}
	public void setLoginoutTime(Date loginoutTime) {
		this.loginoutTime = loginoutTime;
	}
	public Integer getLoginState() {
		return loginState;
	}
	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
	}
	
}

