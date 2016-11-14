package com.cnu.offline.xml;

import com.cnu.offline.bean.Word;

/**
* @author 周亮 
* @version 创建时间：2016年11月9日 上午10:37:23
* 类说明
* 
*/
public class Unit {
	//从单词和主单词共有的属性
	private String word;
	private String textsentence;
	private String releation;
	//主单词特有的属性
	private String topic;
	private String property;
	private String wordclass;
	private String meaning;
	private String version;
	private Integer ceshu;
	private Integer diffcultlevel;
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

	//从单词的特有属性
	private String photoUrl;
	private String soundUrl;
	
	public Unit(String releation) {
		super();
		this.releation = releation;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((releation == null) ? 0 : releation.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		Unit other = (Unit) obj;
		if (releation == null) {
			if (other.releation != null)
				return false;
		} else if (!releation.equals(other.releation))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getTextsentence() {
		return textsentence;
	}
	public void setTextsentence(String textsentence) {
		this.textsentence = textsentence;
	}
	public String getReleation() {
		return releation;
	}
	public void setReleation(String releation) {
		this.releation = releation;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
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
	
}
