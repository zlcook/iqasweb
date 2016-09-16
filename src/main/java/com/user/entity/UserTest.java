package com.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author 刘玉婷
* @version 创建时间：2016年6月16日 下午 15:28:05
* 用户的测试数据
*/
@Entity   
@Table(name = "t_usertest")
public class UserTest {
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 private int id;
	 @Column(name = "userId")
	 private String userId;
	 private String word;
	 public int getRightTimes() {
		return rightTimes;
	}
	public void setRightTimes(int rightTimes) {
		this.rightTimes = rightTimes;
	}
	public int getWrongTimes() {
		return wrongTimes;
	}
	public void setWrongTimes(int wrongTimes) {
		this.wrongTimes = wrongTimes;
	}
	public int getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private int testType;
	 private int testAspect;
	 private int testDifficulty;
	 @Column(name = "righttimes")
	 private int rightTimes;
	 @Column(name = "wrongtimes")
	 private int wrongTimes;
	 @Column(name = "totaltimes")
	 private int totalTimes;
	 @Column(name = "starttime")
	 private Date startTime;
	 @Column(name = "endtime")
	 private Date endTime;
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
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getTestType() {
		return testType;
	}
	public void setTestType(int testType) {
		this.testType = testType;
	}
	public int getTestAspect() {
		return testAspect;
	}
	public void setTestAspect(int testAspect) {
		this.testAspect = testAspect;
	}
	public UserTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTestDifficulty() {
		return testDifficulty;
	}
	public void setTestDifficulty(int testDifficulty) {
		this.testDifficulty = testDifficulty;
	}
	
	public UserTest(String userId, String word, int testType, int testAspect, int testDifficulty,
			int rightTimes, int wrongTimes, int totalTimes, Date startTime, Date endTime) {
		super();
		this.userId = userId;
		this.word = word;
		this.testType = testType;
		this.testAspect = testAspect;
		this.testDifficulty = testDifficulty;
	    this.rightTimes = rightTimes;
	    this.wrongTimes = wrongTimes;
	    this.totalTimes = totalTimes;
	    this.startTime = startTime;
	    this.endTime = endTime;
	}
	 

}
