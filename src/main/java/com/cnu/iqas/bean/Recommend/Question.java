package com.cnu.iqas.bean.Recommend;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 王文辉
 * @version 创建时间：2016年4月20日   问题表
 */
@Entity
@Table(name = "t_question")
/*@GenericGenerator(strategy = "uuid", name = "uuidGenerator")*/
public class Question {
	/**
	 * questionId，问题标识
	 */
	private int questionId;
	/**
	 * 问题内容
	 */
	private String content;
	/**
	 * 句型、单词
	 */
	private String type;
	/**
	 * 年月日
	 */
	private Date createDate= new Date();
	/**
	 * 用户id 外键
	 */
    private String  userId;
    @Id
	/*@GeneratedValue(generator = "uuidGenerator")*/
    public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
