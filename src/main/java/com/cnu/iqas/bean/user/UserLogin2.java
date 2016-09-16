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
* @version 创建时间：2016年1月27日 上午11:05:40
* 类说明：用户登录记录表
*/

@Entity
@Table(name="t_userLogin2")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class UserLogin2 {
	/**
	 * 登录中
	 */
	public static int LOGINING=1;
	/**
	 * 正常退出
	 */
	public static int NORMAL_LOGOUT=2;
	/**
	 * 非正常退出
	 */
	public static int UNNORMAL_NOLOGOUT=3;
	/**
	 * 标识id
	 */
	private String id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 登录ip地址
	 */
	private String ip;
	/**
	 * 登录时间
	 */
	private Date loginTime=new Date();
	/**
	 * 登出时间
	 */
	private Date logoutTime =null;
	

	/**
	 * 登录状态	1：登录中 2：退出登录 3:非正常退出,默认值1
	 */
	private Integer loginState=LOGINING;
	
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(nullable=false)
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
	public Date getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}
	
	public Integer getLoginState() {
		return loginState;
	}
	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
