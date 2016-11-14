package com.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 刘玉婷
 * @version 创建时间：2016年8月21日 20:30
 * 类说明 
*/
@Entity
@Table(name="t_userbehaviour")
public class UserBehaviour {
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 private int id;
	 @Column(name = "userId")
	 private String userId;
	 private String doWhere;
	 private String doWhat;
	 private Date doWhen;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDoWhere() {
		return doWhere;
	}
	public void setDoWhere(String doWhere) {
		this.doWhere = doWhere;
	}
	public String getDoWhat() {
		return doWhat;
	}
	public void setDoWhat(String doWhat) {
		this.doWhat = doWhat;
	}
	public Date getDoWhen() {
		return doWhen;
	}
	public void setDoWhen(Date doWhen) {
		this.doWhen = doWhen;
	}
	public UserBehaviour() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserBehaviour(String userId, String doWhere, String doWhat, Date doWhen) {
		super();
		this.userId = userId;
		this.doWhere = doWhere;
		this.doWhat = doWhat;
		this.doWhen = doWhen;
	}
	 
	
}
