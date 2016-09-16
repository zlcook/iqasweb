package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author 刘玉婷
* @version 创建时间：2016年6月29日 下午 16:08:05
* 用户道具
*/
@Entity   
@Table(name = "t_usertool")
public class UserTool {
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 private int id;
	 @Column(name = "userid")
	 private String userId;
	 public UserTool() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserTool(String userId, String toolId, int count) {
		super();
		this.userId = userId;
		this.toolId = toolId;
		this.count = count;
	}
	@Column(name = "toolid")
	 private String toolId;
	 private int count;
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
	public String getToolId() {
		return toolId;
	}
	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
