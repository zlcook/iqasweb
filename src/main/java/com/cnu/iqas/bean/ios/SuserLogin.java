package com.cnu.iqas.bean.ios;

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
* @version 创建时间：2016年3月1日 上午10:13:47
* 类说明: ios用户登录表
* 

*/
@Entity
@Table(name="t_suserlogin")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class SuserLogin {
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
	 * 主键
	 */
	private String userLoginId;
	/**
	 * 用户id		外键User
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
	 * 登录地区
	 */
	private String area;
	/**
	 * 登录时间,默认当前时间
	 */
	private Date loginTime=new Date();
	
	/**
	 * 登出时间
	 */
	private Date logoutTime;
	/**
	 * 登录状态	1：登录中 2：退出登录 3:非正常退出,默认值1
	 */
	private Integer loginState=LOGINING;
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	@Column(nullable=false)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
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
	@Column(nullable=false)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getLoginState() {
		return loginState;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
}
