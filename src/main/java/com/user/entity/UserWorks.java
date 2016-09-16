package com.user.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 下午4:30:48 
 * 类说明 
*/
@Entity
@Table(name="t_userWorks")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class UserWorks {

	/**
	 * 用户作品id
	 * */
	private String worksId;
	/**
	 * 用户id
	 * */
	private String userId;
	/**
	 * 作品所属单词
	 * */
	private String word;	
	/**
	 * 作品名
	 * */
	private String worksName;
	/**
	 * 作品获取金币数 
	 * */
	private int golden;
	/**
	 * 作品上传位置，本地(0)或服务器(1)
	 * */
	private int location;
	/**
	 * 作品类型
	 * */
	private int worksType;
	/**
	 * 作品URL
	 * */
	private String worksUrl;
	/**
	 * 作品上传时间
	 * */
	private Date uploadTime = new Date();
	/**
	 * 作品提交次数
	 */
	private int worksCount;
	public UserWorks(String userId, String word, String worksName, int golden, int location, int worksType,
			String worksUrl,int worksCount) {
		super();
		this.userId = userId;
		this.word = word;
		this.worksName = worksName;
		this.golden = golden;
		this.location = location;
		this.worksType = worksType;
		this.worksUrl = worksUrl;
		this.worksCount = worksCount;
	}
	
	public int getWorksCount() {
		return worksCount;
	}

	public void setWorksCount(int worksCount) {
		this.worksCount = worksCount;
	}

	public UserWorks() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getWorksId() {
		return worksId;
	}
	public void setWorksId(String worksId) {
		this.worksId = worksId;
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
	public String getWorksName() {
		return worksName;
	}
	public void setWorksName(String worksName) {
		this.worksName = worksName;
	}
	public int getGolden() {
		return golden;
	}
	public void setGolden(int golden) {
		this.golden = golden;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getWorksType() {
		return worksType;
	}
	public void setWorksType(int worksType) {
		this.worksType = worksType;
	}
	public String getWorksUrl() {
		return worksUrl;
	}
	public void setWorksUrl(String worksUrl) {
		this.worksUrl = worksUrl;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
