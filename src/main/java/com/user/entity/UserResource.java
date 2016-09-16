package com.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 上午10:40:49 
 * 类说明 
*/
@Entity
@Table(name="t_userstudyresource")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class UserResource {

	/**
	 * 正常结束学习
	 *//*
	public static int LEARNING = 1;
	
	*//**
	 * 结束学习
	 *//*
	public static int LEARNEND = 2;
	
	*//**
	 * 异常结束学习
	 *//*
	public static int UNNORMAL_LEARNEND = 3;*/
	/**
	 * 用户学习资源id
	 */
	private String learnId;
	/**
	 * 用户登录id
	 */
	private String userId;
	/**
	 * 单词
	 */
	private String word;
	/**
	 * 资源id
	 */
	private String resourceId;
	/**
	 * 资源媒体类型：取值1~5 1：文本
                       2：图片
                       3：视频
                       4：音频
                       5：发音
	 */
	private int mediaType;
	/**
	 * 开始学习时间
	 */
	private Date learnStartTime;
	/**
	 * 结束学习时间
	 */
	private Date learnEndTime;
	/**
	 * 过去学习时长
	 */
	private long learnDuration;
	/**
	 * 学习次数
	 */
	private int learnCount;
	/**
	 * 学习状态：1：学习中；2：结束学习；3：异常结束学习
	 
	private Integer learnState = LEARNING;*/
	
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getLearnId() {
		return learnId;
	}
	public UserResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setLearnId(String learnId) {
		this.learnId = learnId;
	}
	@Column(nullable=false)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserResource(String userId, String word, String resourceId, int mediaType, Date learnStartTime,
			Date learnEndTime, long learnDuration, int learnCount) {
		super();
		this.userId = userId;
		this.word = word;
		this.resourceId = resourceId;
		this.mediaType = mediaType;
		this.learnStartTime = learnStartTime;
		this.learnEndTime = learnEndTime;
		this.learnDuration = learnDuration;
		this.learnCount = learnCount;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public long getLearnDuration() {
		return learnDuration;
	}
	public void setLearnDuration(long learnDuration) {
		this.learnDuration = learnDuration;
	}
	public int getLearnCount() {
		return learnCount;
	}
	public void setLearnCount(int learnCount) {
		this.learnCount = learnCount;
	}
	@Column(nullable=false) 
	public String getResourceId() {
		return resourceId;
	}	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}	
	public int getMediaType() {
		return mediaType;
	}
	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	public Date getLearnStartTime() {
		return learnStartTime;
	}
	public void setLearnStartTime(Date learnStartTime) {
		this.learnStartTime = learnStartTime;
	}
	
	public Date getLearnEndTime() {
		return learnEndTime;
	}
	public void setLearnEndTime(Date learnEndTime) {
		this.learnEndTime = learnEndTime;
	}
	/*public Integer getLearnState() {
		return learnState;
	}	
	public void setLearnState(Integer learnState) {
		this.learnState = learnState;
	}	*/
}
