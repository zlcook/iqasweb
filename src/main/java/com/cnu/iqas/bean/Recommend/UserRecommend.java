package com.cnu.iqas.bean.Recommend;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 王文辉
 * @version 创建时间：2016年4月20日  用户信息推荐表
 */
@Entity
@Table(name = "t_userRecommend")
@GenericGenerator(strategy = "uuid", name = "uuidGenerator")
public class UserRecommend {
	/**
	 * Id主键
	 */
	 private String id;
	 /**
	 * 用户id
	 */
	private String userId;
	/**
	 * 答案id
	 */
	private String answerId;
	/**
	 * 创建日期
	 */
	private Date createDate= new Date();
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
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
