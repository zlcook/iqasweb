package com.cnu.offline.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午10:04:11
* 类说明:ios
* 存放ios主单词的资源存放路径，如果一个属性包含多个资源以符号'&'分隔：
* 发音、图片、绘本、情景段落图片、情景段落发音、课文原句图片、课文原句发音、扩展图片、扩展发音、英文图片、
* 视频1、视频1问题、视频1问题答案、视频2、视频2问题、视频2问题答案、视频3、视频3问题、视频3问题答案。
* 
* 
* 
*/
@Entity
@Table(name="i_wresource")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class Wresource {
	@Id
	private String word;
	//单词图片
	private String photoUrl;
	//单词绘本
	private String pictureUrl;
	//单词发音
	private String soundUrl;
	//课文原句发音
	private String textsentencesoundUrl;
	//情景段落发音
	private String phasesoundUrl;
	//扩展发音
	private String expandsentencesoundUrl;
	//课文原句图片
	private String textsentencephotoUrl;
	//情景段落图片
	private String phasephotoUrl;
	//扩展图片
	private String expandsentencephotoUrl;
	//英文意思发音
	private String englishimeaningsoundUrl;
	//视频1
	private String videoUrl1;
	//视频1提问1
	private String v1q1;
	//提问1答案1
	private String v1a1;
	//视频1提问2
	private String v1q2;
	//提问2答案2
	private String v1a2;
	//视频2
	private String videoUrl2;
	//视频2提问1
	private String v2q1;
	//提问1答案1
	private String v2a1;
	//视频2提问2
	private String v2q2;
	//提问2答案2
	private String v2a2;
	//视频3
	private String videoUrl3;
	//视频3提问1
	private String v3q1;
	//提问1答案1
	private String v3a1;
	//视频3提问2
	private String v3q2;
	//提问2答案2
	private String v3a2;
	
	
	public Wresource() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Wresource(String word, String photoUrl, String pictureUrl, String soundUrl, String textsentencesoundUrl,
			String phasesoundUrl, String expandsentencesoundUrl, String textsentencephotoUrl, String phasephotoUrl,
			String expandsentencephotoUrl, String englishimeaningsoundUrl,String videoUrls) {
		super();
		this.word = word;
		this.photoUrl = photoUrl;
		this.pictureUrl = pictureUrl;
		this.soundUrl = soundUrl;
		this.textsentencesoundUrl = textsentencesoundUrl;
		this.phasesoundUrl = phasesoundUrl;
		this.expandsentencesoundUrl = expandsentencesoundUrl;
		this.textsentencephotoUrl = textsentencephotoUrl;
		this.phasephotoUrl = phasephotoUrl;
		this.expandsentencephotoUrl = expandsentencephotoUrl;
		this.englishimeaningsoundUrl = englishimeaningsoundUrl;
		String[] vUrls = videoUrls.split("&");
		this.videoUrl1 = vUrls[0];
		this.videoUrl2 = vUrls[1];
		this.videoUrl3 = vUrls[2];
	}

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
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getSoundUrl() {
		return soundUrl;
	}
	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}
	public String getTextsentencesoundUrl() {
		return textsentencesoundUrl;
	}
	public void setTextsentencesoundUrl(String textsentencesoundUrl) {
		this.textsentencesoundUrl = textsentencesoundUrl;
	}
	public String getPhasesoundUrl() {
		return phasesoundUrl;
	}
	public void setPhasesoundUrl(String phasesoundUrl) {
		this.phasesoundUrl = phasesoundUrl;
	}
	public String getExpandsentencesoundUrl() {
		return expandsentencesoundUrl;
	}
	public void setExpandsentencesoundUrl(String expandsentencesoundUrl) {
		this.expandsentencesoundUrl = expandsentencesoundUrl;
	}
	public String getTextsentencephotoUrl() {
		return textsentencephotoUrl;
	}
	public void setTextsentencephotoUrl(String textsentencephotoUrl) {
		this.textsentencephotoUrl = textsentencephotoUrl;
	}
	public String getPhasephotoUrl() {
		return phasephotoUrl;
	}
	public void setPhasephotoUrl(String phasephotoUrl) {
		this.phasephotoUrl = phasephotoUrl;
	}
	public String getExpandsentencephotoUrl() {
		return expandsentencephotoUrl;
	}
	public void setExpandsentencephotoUrl(String expandsentencephotoUrl) {
		this.expandsentencephotoUrl = expandsentencephotoUrl;
	}
	public String getEnglishimeaningsoundUrl() {
		return englishimeaningsoundUrl;
	}
	public void setEnglishimeaningsoundUrl(String englishimeaningsoundUrl) {
		this.englishimeaningsoundUrl = englishimeaningsoundUrl;
	}
	public String getVideoUrl1() {
		return videoUrl1;
	}
	public void setVideoUrl1(String videoUrl1) {
		this.videoUrl1 = videoUrl1;
	}
	public String getV1q1() {
		return v1q1;
	}
	public void setV1q1(String v1q1) {
		this.v1q1 = v1q1;
	}
	public String getV1a1() {
		return v1a1;
	}
	public void setV1a1(String v1a1) {
		this.v1a1 = v1a1;
	}
	public String getV1q2() {
		return v1q2;
	}
	public void setV1q2(String v1q2) {
		this.v1q2 = v1q2;
	}
	public String getV1a2() {
		return v1a2;
	}
	public void setV1a2(String v1a2) {
		this.v1a2 = v1a2;
	}
	public String getV2q1() {
		return v2q1;
	}
	public void setV2q1(String v2q1) {
		this.v2q1 = v2q1;
	}
	public String getV2a1() {
		return v2a1;
	}
	public void setV2a1(String v2a1) {
		this.v2a1 = v2a1;
	}
	public String getV2q2() {
		return v2q2;
	}
	public void setV2q2(String v2q2) {
		this.v2q2 = v2q2;
	}
	public String getV2a2() {
		return v2a2;
	}
	public void setV2a2(String v2a2) {
		this.v2a2 = v2a2;
	}
	public String getV3q1() {
		return v3q1;
	}
	public void setV3q1(String v3q1) {
		this.v3q1 = v3q1;
	}
	public String getV3a1() {
		return v3a1;
	}
	public void setV3a1(String v3a1) {
		this.v3a1 = v3a1;
	}
	public String getV3q2() {
		return v3q2;
	}
	public void setV3q2(String v3q2) {
		this.v3q2 = v3q2;
	}
	public String getV3a2() {
		return v3a2;
	}
	public void setV3a2(String v3a2) {
		this.v3a2 = v3a2;
	}
	public String getVideoUrl2() {
		return videoUrl2;
	}
	public void setVideoUrl2(String videoUrl2) {
		this.videoUrl2 = videoUrl2;
	}
	public String getVideoUrl3() {
		return videoUrl3;
	}
	public void setVideoUrl3(String videoUrl3) {
		this.videoUrl3 = videoUrl3;
	}
	
}
