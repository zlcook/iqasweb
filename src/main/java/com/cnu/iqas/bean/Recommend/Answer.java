package com.cnu.iqas.bean.Recommend;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 王文辉
 * @version 创建时间：2016年4月20日  答案表
 */
@Entity
@Table(name = "t_answer")
/*@GenericGenerator(strategy = "uuid", name = "uuidGenerator")*/
public class Answer {
	/**
	 * answerId，答案标识
	 */
	 private int answerId;
	 /**
		 * 内容
		 */
	private String content;
	 /**
	 * 属性
	 */
	private String attributes;
	/**
	 * 媒体类型1.文本 2、视频 3、MP3 4、绘本
	 */
	private String mediaType;
	/**
	 * 难度值       1.高 2.中 3.低
	 */
	private String difficulty;
	/**
	 * 是否通过检查
	 */
	private String  checked;
	/**
	 * 添加类型  1.自动      2.人工
	 */
	private String addType;
	/**
	 * 媒体文件的路径
	 */
	private String mediaUrl;
	/**
	 * 创建日期
	 */
	private Date createDate= new Date();
	/**
	 * 是否可见，默认可见值为true
	 */
	private boolean visible=true;
	private int questionId;
	@Id
	/*@GeneratedValue(generator = "uuidGenerator")*/
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public String getAttributes() {
		return attributes;
	}
	
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
