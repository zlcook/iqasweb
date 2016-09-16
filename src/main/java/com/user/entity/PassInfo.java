package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author 刘玉婷
* @version 创建时间：2016年6月29日 下午 15:30:05
* 用户闯关数据
*/
@Entity   
@Table(name = "t_passinfo")
public class PassInfo {
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 private int id;
	 @Column(name = "userid")
	 private String userId;
	 private String topic;
	 private int onScene;
	 private int goals;
	 private int medals;
	public PassInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PassInfo(String userId, String topic, int onScene, int goals, int medals) {
		super();
		this.userId = userId;
		this.topic = topic;
		this.onScene = onScene;
		this.goals = goals;
		this.medals = medals;
	}
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
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getOnScene() {
		return onScene;
	}
	public void setOnScene(int onScene) {
		this.onScene = onScene;
	}
	public int getGoals() {
		return goals;
	}
	public void setGoals(int goals) {
		this.goals = goals;
	}
	public int getMedals() {
		return medals;
	}
	public void setMedals(int medals) {
		this.medals = medals;
	}
}    
