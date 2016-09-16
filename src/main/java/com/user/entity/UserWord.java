package com.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author 刘玉婷
* @version 创建时间：2016年6月30日 下午 12:20:10
* 用户单词学习记录
*/
@Entity   
@Table(name = "t_userword")
public class UserWord {
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 private int id;
	 @Column(name = "userid")
	 private String userId;
	 private String word;
	 private int topicLevel;
	 @Column(name = "wordlearn")
	 private int wordLearn;
	 private int test;
	 private Date time;
	public int getId() {
		return id;
	}
	public UserWord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserWord(String userId, String word, int topicLevel, int wordLearn, int test, Date time) {
		super();
		this.userId = userId;
		this.word = word;
		this.topicLevel = topicLevel;
		this.wordLearn = wordLearn;
		this.test = test;
		this.time = time;
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
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getTopicLevel() {
		return topicLevel;
	}
	public void setTopicLevel(int topicLevel) {
		this.topicLevel = topicLevel;
	}
	public int getWordLearn() {
		return wordLearn;
	}
	public void setWordLearn(int wordLearn) {
		this.wordLearn = wordLearn;
	}
	public int getTest() {
		return test;
	}
	public void setTest(int test) {
		this.test = test;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
