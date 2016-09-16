package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author 刘玉婷
* @version 创建时间：2016年6月29日 下午 14:31:05
* 用户数据
*/
@Entity   
@Table(name = "t_userinfo")
public class UserInfo {
	 @Id
	 @Column(name = "userid", unique = true, nullable = false)
	 private String userId;
	 private int system;
	 public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfo(String userId,int system, int beforeLevel, int englishScore, int learningStyle1, int learningStyle2, int learningStyle3,
			int learningStyle4) {
		super();
		this.userId = userId;
		this.system = system;
		this.beforeLevel = beforeLevel;
		this.EnglishScore = englishScore;
		this.learningStyle1 = learningStyle1;
		this.learningStyle2 = learningStyle2;
		this.learningStyle3 = learningStyle3;
		this.learningStyle4 = learningStyle4;
	}
	public int getSystem() {
		return system;
	}
	public void setSystem(int system) {
		this.system = system;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getBeforeLevel() {
		return beforeLevel;
	}
	public void setBeforeLevel(int beforeLevel) {
		this.beforeLevel = beforeLevel;
	}
	public int getEnglishScore() {
		return EnglishScore;
	}
	public void setEnglishScore(int englishScore) {
		EnglishScore = englishScore;
	}
	public int getLearningStyle1() {
		return learningStyle1;
	}
	public void setLearningStyle1(int learningStyle1) {
		this.learningStyle1 = learningStyle1;
	}
	public int getLearningStyle2() {
		return learningStyle2;
	}
	public void setLearningStyle2(int learningStyle2) {
		this.learningStyle2 = learningStyle2;
	}
	public int getLearningStyle3() {
		return learningStyle3;
	}
	public void setLearningStyle3(int learningStyle3) {
		this.learningStyle3 = learningStyle3;
	}
	public int getLearningStyle4() {
		return learningStyle4;
	}
	public void setLearningStyle4(int learningStyle4) {
		this.learningStyle4 = learningStyle4;
	}
	@Column(name = "beforelevel")
	 private int beforeLevel;
	 private int EnglishScore;
	 private int learningStyle1;
	 private int learningStyle2;
	 private int learningStyle3;
	 private int learningStyle4;
	 
}
