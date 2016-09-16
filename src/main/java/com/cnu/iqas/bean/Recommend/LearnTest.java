package com.cnu.iqas.bean.Recommend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 王文辉
 * @version 创建时间：2016年1月14日 参与测试行为表
 */
@Entity
@Table(name = "t_learnTest")
@GenericGenerator(strategy = "uuid", name = "uuidGenerator")
public class LearnTest {
	/**
	 * id，标识
	 */
	private String id;
	/**
	 * 外键用户id
	 */
	private String userId;
	/**
	 * 外键测试知识id
	 */
	private String testKnowledgeId;
	/**
	 * 测试类型--对应若干种题型[1,N]
	 */
	private Integer testType;
	/**
	 * 偏好--对应若干种考查方面
	 */
	private Integer aspect;
	/**
	 * 测试难度 1：低2：中 3：高
	 */
	private Integer testDifficulty;
	/**
	 * 回答次数
	 */
	private Integer answerTime;
	/**
	 * 0：未答对 1：答对
	 */
	private Integer stuPass;
	@Id @GeneratedValue(generator="uuidGenerator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTestKnowledgeId() {
		return testKnowledgeId;
	}

	public void setTestKnowledgeId(String testKnowledgeId) {
		this.testKnowledgeId = testKnowledgeId;
	}

	public Integer getTestType() {
		return testType;
	}

	public void setTestType(Integer testType) {
		this.testType = testType;
	}

	public Integer getAspect() {
		return aspect;
	}

	public void setAspect(Integer aspect) {
		this.aspect = aspect;
	}

	public Integer getTestDifficulty() {
		return testDifficulty;
	}

	public void setTestDifficulty(Integer testDifficulty) {
		this.testDifficulty = testDifficulty;
	}

	public Integer getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Integer answerTime) {
		this.answerTime = answerTime;
	}

	public Integer getStuPass() {
		return stuPass;
	}

	public void setStuPass(Integer stuPass) {
		this.stuPass = stuPass;
	}
    


}
