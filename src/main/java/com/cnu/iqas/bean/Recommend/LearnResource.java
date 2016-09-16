package com.cnu.iqas.bean.Recommend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author 王文辉
 * @version 创建时间：2016年1月14日 类说明 学习资源表
 */
@Entity
@Table(name = "t_learnResource")
@GenericGenerator(strategy = "uuid", name = "uuidGenerator")
public class LearnResource {
	/**
	 * id，标识
	 */
	private String id;
	/**
	 * 用户表外键，用户id
	 */
	private String userId;
	/**
	 * 资源表外键，用户id
	 */
	private String resourceId;
	/**
	 * 媒体类型1：图像2：文本3：音频4：视频
	 */
	private Integer media;
	/**
	 * 偏好
	 */
	private Integer aspect = 0;
	/**
	 * 浏览次数，初始值为0
	 */
	private Integer learnTime = 0;
	/**
	 * 浏览时长，初始值为0，单位为s
	 */
	private Integer learnDuration = 0;
	/**
	 * 0：未赞 1：赞
	 */
	private Integer stuLike;

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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getMedia() {
		return media;
	}

	public void setMedia(Integer media) {
		this.media = media;
	}

	public Integer getAspect() {
		return aspect;
	}

	public void setAspect(Integer aspect) {
		this.aspect = aspect;
	}

	public Integer getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(Integer learnTime) {
		this.learnTime = learnTime;
	}

	public Integer getLearnDuration() {
		return learnDuration;
	}

	public void setLearnDuration(Integer learnDuration) {
		this.learnDuration = learnDuration;
	}

	public Integer getStuLike() {
		return stuLike;
	}

	public void setStuLike(Integer stuLike) {
		this.stuLike = stuLike;
	}

	

}
