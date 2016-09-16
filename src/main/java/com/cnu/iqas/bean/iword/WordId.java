package com.cnu.iqas.bean.iword;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
* @author 周亮 
* @version 创建时间：2016年5月10日 上午11:25:49
* 类说明:单词和单词id作为主键
*/
@Embeddable
public class WordId implements Serializable{
	/**
	 * 单词内容
	 */
	private String word;
	/**
	 * 单词编号
	 */
	private String wordId;
	
	public WordId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WordId(String word, String wordId) {
		super();
		this.word = word;
		this.wordId = wordId;
	}
	@Column(length=70,nullable=false)
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Column(nullable=true,length=10)
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	
}
