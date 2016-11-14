package com.cnu.offline.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午9:56:54
* 类说明，ios，
* 存储ios从单词资源路径，每个属性只会有一个资源路径：图片、发音、课文原句
*/
@Entity
@Table(name="i_expandword")
public class ExpandWord {
	//扩展单词
	@Id
	private String word;
	private String photoUrl;
	private String soundUrl;
	private String textsentence;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getSoundUrl() {
		return soundUrl;
	}
	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}
	public String getTextsentence() {
		return textsentence;
	}
	public void setTextsentence(String textsentence) {
		this.textsentence = textsentence;
	}
	public ExpandWord(String word, String photoUrl, String soundUrl, String textsentence) {
		super();
		this.word = word;
		this.photoUrl = photoUrl;
		this.soundUrl = soundUrl;
		this.textsentence = textsentence;
	}
	public ExpandWord() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
