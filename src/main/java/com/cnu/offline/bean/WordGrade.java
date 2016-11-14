package com.cnu.offline.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午10:53:41
* 类说明,ios
* ios端存放主单词的资源，该资源有年级之分，根据自适应结果选择不同的资源。
* 课文原句及发音和图片、情景段落及发音和图片。
*/
@Entity
@Table(name="i_wordgrade")
public class WordGrade {

	@EmbeddedId
	private WordGradeId id;
	//课文原句、图片、发音
	private String textsentence;
	private String textsentencephotoUrl;
	private String textsentencesoundUrl;
	//情景段落、图片、发音
	private String phase;
	private String phasephotoUrl;
	private String phasesoundUrl;
	
	
	public WordGrade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WordGrade(String word, Integer grade, String textsentence, String textsentencephotoUrl,
			String textsentencesoundUrl, String phase, String phasephotoUrl, String phasesoundUrl) {
		super();
		id = new WordGradeId(word, grade);
		this.textsentence = textsentence;
		this.textsentencephotoUrl = textsentencephotoUrl;
		this.textsentencesoundUrl = textsentencesoundUrl;
		this.phase = phase;
		this.phasephotoUrl = phasephotoUrl;
		this.phasesoundUrl = phasesoundUrl;
	}
	
	public WordGradeId getId() {
		return id;
	}
	public void setId(WordGradeId id) {
		this.id = id;
	}
	public String getTextsentence() {
		return textsentence;
	}
	public void setTextsentence(String textsentence) {
		this.textsentence = textsentence;
	}
	public String getTextsentencephotoUrl() {
		return textsentencephotoUrl;
	}
	public void setTextsentencephotoUrl(String textsentencephotoUrl) {
		this.textsentencephotoUrl = textsentencephotoUrl;
	}
	public String getTextsentencesoundUrl() {
		return textsentencesoundUrl;
	}
	public void setTextsentencesoundUrl(String textsentencesoundUrl) {
		this.textsentencesoundUrl = textsentencesoundUrl;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getPhasephotoUrl() {
		return phasephotoUrl;
	}
	public void setPhasephotoUrl(String phasephotoUrl) {
		this.phasephotoUrl = phasephotoUrl;
	}
	public String getPhasesoundUrl() {
		return phasesoundUrl;
	}
	public void setPhasesoundUrl(String phasesoundUrl) {
		this.phasesoundUrl = phasesoundUrl;
	}
	
}
