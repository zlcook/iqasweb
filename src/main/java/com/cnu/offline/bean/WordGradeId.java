package com.cnu.offline.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
* @author 周亮 
* @version 创建时间：2016年11月2日 下午2:37:14
* 类说明
* WordGrade的id类。
*/
@Embeddable
public class WordGradeId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1123181917740883452L;
	//单词
	private String word;
	//所属年级
	private Integer grade;
	
	public WordGradeId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WordGradeId(String word, Integer grade) {
		super();
		this.word = word;
		this.grade = grade;
	}
	@Column(nullable = false,length=30)
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Column(nullable = false)
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
}
