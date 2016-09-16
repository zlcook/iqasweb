package com.cnu.iqas.bean.Recommend;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 王文辉
 * @version 创建时间：2016年1月14日    知识水平表
 */
@Entity
@Table(name = "t_knowledgeLevel")
@GenericGenerator(strategy = "uuid", name = "uuidGenerator")
public class KnowledgeLevel {
	/**
	 * id，标识
	 */
	private String id;
	/**
	 * 用户id，外键
	 */
	private String userId;
	/**
	 * 知识id，外键
	 */
	private String knowledgeId;
	/**
	 * 浏览次数，初始值为0
	 */
	private Integer learnResourceCount = 0;
	/**
	 * 浏览时长，初始值为0，单位为s
	 */
	private Integer learnResourceDuration = 0;
	/**
	 * 答题数量，初始值为0
	 */
	private Integer testCount = 0;
	/**
	 * 答题成绩，取值范围[0,3]
	 */
	private double testScore = 0;
	/**
	 * 记录生成日期
	 */
	private Date createTime = new Date();

	@Id
	@GeneratedValue(generator = "uuidGenerator")
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

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public Integer getLearnResourceCount() {
		return learnResourceCount;
	}

	public void setLearnResourceCount(Integer learnResourceCount) {
		this.learnResourceCount = learnResourceCount;
	}

	public Integer getLearnResourceDuration() {
		return learnResourceDuration;
	}

	public void setLearnResourceDuration(Integer learnResourceDuration) {
		this.learnResourceDuration = learnResourceDuration;
	}

	public Integer getTestCount() {
		return testCount;
	}

	public void setTestCount(Integer testCount) {
		this.testCount = testCount;
	}

	public double getTestScore() {
		return testScore;
	}

	public void setTestScore(double testScore) {
		this.testScore = testScore;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
