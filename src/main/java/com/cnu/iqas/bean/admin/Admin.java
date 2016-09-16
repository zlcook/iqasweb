package com.cnu.iqas.bean.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 上午10:46:05
* 类说明,系统管理员
*/
@Entity
@Table(name="t_admin")
public class Admin {

	private String account;
	private String password;
	
	@Id @Column(length=15) 
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
