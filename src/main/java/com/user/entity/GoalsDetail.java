package com.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author 刘玉婷
* @version 创建时间：2016年6月29日 下午 16:58:05
* 金币收支明细
*/
@Entity   
@Table(name = "t_goalsdetail")
public class GoalsDetail {
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 private int id;
	 @Column(name = "userid")
	 private String userId;
	 private int goalsBefore;
	 private int inORout;
	 private int content;
	 private int goalsAfter;
	 private Date time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public GoalsDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GoalsDetail(String userId, int goalsBefore, int inORout, int content, int goalsAfter, Date time) {
		super();
		this.userId = userId;
		this.goalsBefore = goalsBefore;
		this.inORout = inORout;
		this.content = content;
		this.goalsAfter = goalsAfter;
		this.time = time;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getGoalsBefore() {
		return goalsBefore;
	}
	public void setGoalsBefore(int goalsBefore) {
		this.goalsBefore = goalsBefore;
	}
	
	public int getInORout() {
		return inORout;
	}
	public void setInORout(int inORout) {
		this.inORout = inORout;
	}
	public int getContent() {
		return content;
	}
	public void setContent(int content) {
		this.content = content;
	}
	public int getGoalsAfter() {
		return goalsAfter;
	}
	public void setGoalsAfter(int goalsAfter) {
		this.goalsAfter = goalsAfter;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	 
}
