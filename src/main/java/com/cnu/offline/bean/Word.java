package com.cnu.offline.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午10:15:30
* 类说明:ios
* 存放ios端单词的属性信息，没有使用本地资源，因为本体资源太少。
*/
@Entity
@Table(name="i_word")
public class Word {

	@Id
	private String word;
	private String topic;
	private String property;
	private String wordclass;
	private String meaning;
	private String version;
	private Integer ceshu;
	private Integer diffcultlevel;
	private String textsentence;
	private String expandsentence;
	private String imageword;
	private String expandword;
	private String wordgroup;
	private String phase;
	private Integer learningcount;
	private String baike;
	private String synonym;
	private String antongym;
	private String usemethod;
	private String englishmeaning;
	
	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Word(String word, String topic, String property, String wordclass, String meaning, String version,
			Integer ceshu, Integer diffcultlevel, String textsentence, String expandsentence, String imageword,
			String expandword, String wordgroup, String phase, Integer learningcount, String baike, String synonym,
			String antongym, String usemethod, String englishmeaning) {
		super();
		this.word = word;
		this.topic = topic;
		this.property = property;
		this.wordclass = wordclass;
		this.meaning = meaning;
		this.version = version;
		this.ceshu = ceshu;
		this.diffcultlevel = diffcultlevel;
		this.textsentence = textsentence;
		this.expandsentence = expandsentence;
		this.imageword = imageword;
		this.expandword = expandword;
		this.wordgroup = wordgroup;
		this.phase = phase;
		this.learningcount = learningcount;
		this.baike = baike;
		this.synonym = synonym;
		this.antongym = antongym;
		this.usemethod = usemethod;
		this.englishmeaning = englishmeaning;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getPoperty() {
		return property;
	}
	public void setPoperty(String poperty) {
		this.property = poperty;
	}
	public String getWordclass() {
		return wordclass;
	}
	public void setWordclass(String wordclass) {
		this.wordclass = wordclass;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getCeshu() {
		return ceshu;
	}
	public void setCeshu(Integer ceshu) {
		this.ceshu = ceshu;
	}
	public Integer getDiffcultlevel() {
		return diffcultlevel;
	}
	public void setDiffcultlevel(Integer diffcultlevel) {
		this.diffcultlevel = diffcultlevel;
	}
	public String getTextsentence() {
		return textsentence;
	}
	public void setTextsentence(String textsentence) {
		this.textsentence = textsentence;
	}
	public String getExpandsentence() {
		return expandsentence;
	}
	public void setExpandsentence(String expandsentence) {
		this.expandsentence = expandsentence;
	}
	public String getImageword() {
		return imageword;
	}
	public void setImageword(String imageword) {
		this.imageword = imageword;
	}
	public String getExpandword() {
		return expandword;
	}
	public void setExpandword(String expandword) {
		this.expandword = expandword;
	}
	public String getWordgroup() {
		return wordgroup;
	}
	public void setWordgroup(String wordgroup) {
		this.wordgroup = wordgroup;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public Integer getLearningcount() {
		return learningcount;
	}
	public void setLearningcount(Integer learningcount) {
		this.learningcount = learningcount;
	}
	public String getBaike() {
		return baike;
	}
	public void setBaike(String baike) {
		this.baike = baike;
	}
	public String getSynonym() {
		return synonym;
	}
	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}
	public String getAntongym() {
		return antongym;
	}
	public void setAntongym(String antongym) {
		this.antongym = antongym;
	}
	public String getUsemethod() {
		return usemethod;
	}
	public void setUsemethod(String usemethod) {
		this.usemethod = usemethod;
	}
	
	public String getEnglishmeaning() {
		return englishmeaning;
	}
	public void setEnglishmeaning(String englishmeaning) {
		this.englishmeaning = englishmeaning;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ceshu == null) ? 0 : ceshu.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (ceshu == null) {
			if (other.ceshu != null)
				return false;
		} else if (!ceshu.equals(other.ceshu))
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

}
