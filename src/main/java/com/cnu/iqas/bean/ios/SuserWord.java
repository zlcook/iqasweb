package com.cnu.iqas.bean.ios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年3月6日 下午10:00:49
* 类说明
*/
@Entity
@Table(name="t_suserword")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class SuserWord {
	/**
	 * id标识
	 */
	private String userWordId;
	/**
	 * 单词
	 */
	private String word;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 单词id
	 */
	private String wordId;
	/**
	 * 所属主题编号
	 */
	private String theme;
	/**
	 * 单词测试标志
	 * 0 未点击 1 已经点击（学习过）2未测试 3 未通过测试 4通过测试,默认值0
	 */
	private Integer test=0;
	
	@Id  @GeneratedValue(generator="uuidGenderator")
	public String getUserWordId() {
		return userWordId;
	}
	public void setUserWordId(String userWordId) {
		this.userWordId = userWordId;
	}
	@Column(nullable=false)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(nullable=false)
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	@Column(nullable=false)
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	@Column(nullable=false)
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	@Column(nullable=false)
	public Integer getTest() {
		return test;
	}
	public void setTest(Integer test) {
		this.test = test;
	}
	
}
