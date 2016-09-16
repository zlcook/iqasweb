package com.cnu.iqas.bean.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午1:22:50
* 类说明:好友表
*/
@Entity
@Table(name="t_friend")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class Friend {

	private String id;
	/**
	 * 好友关系中其中一方的账号
	 */
	private String oneUserName; 
	/**
	 * 好友关系中另一方的账号
	 */
	private String otherUserName;
	/**
	 * 创建时间
	 */
	private Date createTime=new Date();
	@Id
	@GeneratedValue(generator="uuidGenderator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(nullable=false)
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(nullable=false)
	public String getOneUserName() {
		return oneUserName;
	}
	public void setOneUserName(String oneUserName) {
		this.oneUserName = oneUserName;
	}
	@Column(nullable=false)
	public String getOtherUserName() {
		return otherUserName;
	}
	public void setOtherUserName(String otherUserName) {
		this.otherUserName = otherUserName;
	}
	
	
}
