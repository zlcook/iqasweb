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
* @version 创建时间：2015年12月7日 下午6:32:05
* 类说明 单词和单词主题关系类
*/
@Entity
@Table(name="t_wordtheme_word")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class WordThemeWordRel {
	//id
	private String id;
	//单词id
	private String wordId;
	//单词主题id
	private String wordThemeId;
	//创建时间
	private Date createTime=new Date();

	public WordThemeWordRel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WordThemeWordRel(String wordId, String wordThemeId) {
		super();
		this.wordId = wordId;
		this.wordThemeId = wordThemeId;
	}
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(nullable=false)
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	@Column(nullable=false)
	public String getWordThemeId() {
		return wordThemeId;
	}
	public void setWordThemeId(String wordThemeId) {
		this.wordThemeId = wordThemeId;
	}
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
