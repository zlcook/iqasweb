package com.cnu.iqas.bean.iword;

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
* @author 周亮 
* @version 创建时间：2015年12月7日 上午9:00:07
* 类说明
*/
@Entity
@Table(name="t_wordtheme")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class WordTheme {
	/**id
	 * 
	 */
	private String id;
	
	/**
	 * 查询编号
	 */
	private String number;
	/**
	 * 主题内容，
	 */
	private String content;
	/**
	 * 英文意思
	 */
	private String english;
	/**
	 * 父类主题id
	 */
	private String parentId;
	/**
	 * 测试
	 */
	private Integer test=0;
	/**
	 * 是否开启，默认不开启,false
	 */
	private Boolean visible=true;
	/**
	 * 主题logo保存路径
	 */
	private String picturePath;
	
	/**
	 * 创建时间
	 */
	private Date createTime=new Date();
	
	
	public WordTheme() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(nullable=false,unique=true)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	@Column(nullable=false,unique=false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}
	@Column(nullable=true)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getTest() {
		return test;
	}

	public void setTest(Integer test) {
		this.test = test;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
