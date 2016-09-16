package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author 刘玉婷
* @version 创建时间：2016年6月16日 下午 15:36:52
* 用户的备选测试类型信息
*/
@Entity   
@Table(name = "t_candidatetest")
public class CandidateTest {
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 private int id;
	 @Column(name = "userId")
	 private String userId;
	 private int testType;
	 private int testAspect;
	 private int testDifficulty;
	 private int pass1Count;
	 private int pass2Count;
	 private int pass3Count;
	 private int candidate;
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
	public int getTestDifficulty() {
		return testDifficulty;
	}
	public void setTestDifficulty(int testDifficulty) {
		this.testDifficulty = testDifficulty;
	}
	public int getPass1Count() {
		return pass1Count;
	}
	public void setPass1Count(int pass1Count) {
		this.pass1Count = pass1Count;
	}
	public int getPass2Count() {
		return pass2Count;
	}
	public void setPass2Count(int pass2Count) {
		this.pass2Count = pass2Count;
	}
	public int getPass3Count() {
		return pass3Count;
	}
	public void setPass3Count(int pass3Count) {
		this.pass3Count = pass3Count;
	}
	public int getCandidate() {
		return candidate;
	}
	public void setCandidate(int candidate) {
		this.candidate = candidate;
	}
	public CandidateTest(String userId, int testType, int testAspect, int testDifficulty, int pass1Count,
			int pass2Count, int pass3Count, int candidate) {
		super();
		this.userId = userId;
		this.testType = testType;
		this.testAspect = testAspect;
		this.testDifficulty = testDifficulty;
		this.pass1Count = pass1Count;
		this.pass2Count = pass2Count;
		this.pass3Count = pass3Count;
		this.candidate = candidate;
	}
	public CandidateTest(int testType, int testAspect, int testDifficulty) {
		super();
		this.testType = testType;
		this.testAspect = testAspect;
		this.testDifficulty = testDifficulty;
	}
	public CandidateTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
}
