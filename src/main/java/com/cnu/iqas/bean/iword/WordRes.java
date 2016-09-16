package com.cnu.iqas.bean.iword;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年7月20日 下午4:56:31
* 类说明:单词资源      
* 一个单词有1个图片，1个发音，6个课文原句发音，6个情景段落发音，6个视频 (6对应1到6年级的内容)
*/

@Entity
@Table(name="t_wordres")
public class WordRes {
	/**
	 * 单词名称
	 */
	private String word;
	/**
	 * 图片保存路径，一个单词只有一张图片，或者一张绘本，两者互斥存在。
	 */
	private String picPath;

	/**
	 * 单词发音资源路径
	 */
	private String voicePath;
	/**
	 * 课文原句发音路径
	 */
	private String kwyjVoicePath1;
	/**
	 * 课文原句
	 */
	private String kwyj1;
	/**
	 * 课文原句发音路径
	 */
	private String kwyjVoicePath2;
	/**
	 * 课文原句
	 */
	private String kwyj2;
	
	/**
	 * 课文原句发音路径
	 */
	private String kwyjVoicePath3;
	/**
	 * 课文原句
	 */
	private String kwyj3;
	/**
	 * 课文原句发音路径
	 */
	private String kwyjVoicePath4;

	/**
	 * 课文原句
	 */
	private String kwyj4;
	/**
	 * 课文原句发音路径
	 */
	private String kwyjVoicePath5;

	/**
	 * 课文原句
	 */
	private String kwyj5;
	/**
	 * 课文原句发音路径
	 */
	private String kwyjVoicePath6;

	/**
	 * 课文原句
	 */
	private String kwyj6;
	/**
	 * 情景段落发音路径
	 */
	private String qjdlVoicePath1;
	/**
	 * 情景段落
	 */
	private String qjdl1;
	/**
	 * 情景段落发音路径
	 */
	private String qjdlVoicePath2;
	/**
	 * 情景段落
	 */
	private String qjdl2;
	/**
	 * 情景段落易发音路径
	 */
	private String qjdlVoicePath3;
	/**
	 * 情景段落
	 */
	private String qjdl3;
	
	/**
	 * 情景段落发音路径
	 */
	private String qjdlVoicePath4;
	/**
	 * 情景段落
	 */
	private String qjdl4;
	/**
	 * 情景段落发音路径
	 */
	private String qjdlVoicePath5;
	/**
	 * 情景段落
	 */
	private String qjdl5;
	/**
	 * 情景段落易发音路径
	 */
	private String qjdlVoicePath6;
	/**
	 * 情景段落
	 */
	private String qjdl6;

	/**
	 * 视频路径
	 */
	private String videoPath1;
	/**
	 * 难度视频路径
	 */
	private String videoPath2;
	/**
	 * 视频路径
	 */
	private String videoPath3;
	/**
	 * 视频路径
	 */
	private String videoPath4;
	/**
	 * 难度视频路径
	 */
	private String videoPath5;
	/**
	 * 视频路径
	 */
	private String videoPath6;
	
	
	@Id
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getVideoPath1() {
		return videoPath1;
	}
	public void setVideoPath1(String videoPath1) {
		this.videoPath1 = videoPath1;
	}
	public String getVideoPath2() {
		return videoPath2;
	}
	public void setVideoPath2(String videoPath2) {
		this.videoPath2 = videoPath2;
	}
	public String getVideoPath3() {
		return videoPath3;
	}
	public void setVideoPath3(String videoPath3) {
		this.videoPath3 = videoPath3;
	}
	public String getVoicePath() {
		return voicePath;
	}
	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}
	public String getKwyjVoicePath1() {
		return kwyjVoicePath1;
	}
	public void setKwyjVoicePath1(String kwyjVoicePath1) {
		this.kwyjVoicePath1 = kwyjVoicePath1;
	}
	public String getKwyjVoicePath2() {
		return kwyjVoicePath2;
	}
	public void setKwyjVoicePath2(String kwyjVoicePath2) {
		this.kwyjVoicePath2 = kwyjVoicePath2;
	}
	public String getKwyjVoicePath3() {
		return kwyjVoicePath3;
	}
	public void setKwyjVoicePath3(String kwyjVoicePath3) {
		this.kwyjVoicePath3 = kwyjVoicePath3;
	}
	public String getQjdlVoicePath1() {
		return qjdlVoicePath1;
	}
	public void setQjdlVoicePath1(String qjdlVoicePath1) {
		this.qjdlVoicePath1 = qjdlVoicePath1;
	}
	public String getQjdlVoicePath2() {
		return qjdlVoicePath2;
	}
	public void setQjdlVoicePath2(String qjdlVoicePath2) {
		this.qjdlVoicePath2 = qjdlVoicePath2;
	}
	public String getQjdlVoicePath3() {
		return qjdlVoicePath3;
	}
	public void setQjdlVoicePath3(String qjdlVoicePath3) {
		this.qjdlVoicePath3 = qjdlVoicePath3;
	}
	public String getKwyjVoicePath4() {
		return kwyjVoicePath4;
	}
	public void setKwyjVoicePath4(String kwyjVoicePath4) {
		this.kwyjVoicePath4 = kwyjVoicePath4;
	}
	public String getKwyjVoicePath5() {
		return kwyjVoicePath5;
	}
	public void setKwyjVoicePath5(String kwyjVoicePath5) {
		this.kwyjVoicePath5 = kwyjVoicePath5;
	}
	public String getKwyjVoicePath6() {
		return kwyjVoicePath6;
	}
	public void setKwyjVoicePath6(String kwyjVoicePath6) {
		this.kwyjVoicePath6 = kwyjVoicePath6;
	}
	public String getQjdlVoicePath4() {
		return qjdlVoicePath4;
	}
	public void setQjdlVoicePath4(String qjdlVoicePath4) {
		this.qjdlVoicePath4 = qjdlVoicePath4;
	}
	public String getQjdlVoicePath5() {
		return qjdlVoicePath5;
	}
	public void setQjdlVoicePath5(String qjdlVoicePath5) {
		this.qjdlVoicePath5 = qjdlVoicePath5;
	}
	public String getQjdlVoicePath6() {
		return qjdlVoicePath6;
	}
	public void setQjdlVoicePath6(String qjdlVoicePath6) {
		this.qjdlVoicePath6 = qjdlVoicePath6;
	}
	public String getVideoPath4() {
		return videoPath4;
	}
	public void setVideoPath4(String videoPath4) {
		this.videoPath4 = videoPath4;
	}
	public String getVideoPath5() {
		return videoPath5;
	}
	public void setVideoPath5(String videoPath5) {
		this.videoPath5 = videoPath5;
	}
	public String getVideoPath6() {
		return videoPath6;
	}
	public void setVideoPath6(String videoPath6) {
		this.videoPath6 = videoPath6;
	}
	
	public String getKwyj1() {
		return kwyj1;
	}
	public void setKwyj1(String kwyj1) {
		this.kwyj1 = kwyj1;
	}
	public String getKwyj2() {
		return kwyj2;
	}
	public void setKwyj2(String kwyj2) {
		this.kwyj2 = kwyj2;
	}
	public String getKwyj3() {
		return kwyj3;
	}
	public void setKwyj3(String kwyj3) {
		this.kwyj3 = kwyj3;
	}
	public String getKwyj4() {
		return kwyj4;
	}
	public void setKwyj4(String kwyj4) {
		this.kwyj4 = kwyj4;
	}
	public String getKwyj5() {
		return kwyj5;
	}
	public void setKwyj5(String kwyj5) {
		this.kwyj5 = kwyj5;
	}
	public String getKwyj6() {
		return kwyj6;
	}
	public void setKwyj6(String kwyj6) {
		this.kwyj6 = kwyj6;
	}
	public String getQjdl1() {
		return qjdl1;
	}
	public void setQjdl1(String qjdl1) {
		this.qjdl1 = qjdl1;
	}
	public String getQjdl2() {
		return qjdl2;
	}
	public void setQjdl2(String qjdl2) {
		this.qjdl2 = qjdl2;
	}
	public String getQjdl3() {
		return qjdl3;
	}
	public void setQjdl3(String qjdl3) {
		this.qjdl3 = qjdl3;
	}
	public String getQjdl4() {
		return qjdl4;
	}
	public void setQjdl4(String qjdl4) {
		this.qjdl4 = qjdl4;
	}
	public String getQjdl5() {
		return qjdl5;
	}
	public void setQjdl5(String qjdl5) {
		this.qjdl5 = qjdl5;
	}
	public String getQjdl6() {
		return qjdl6;
	}
	public void setQjdl6(String qjdl6) {
		this.qjdl6 = qjdl6;
	}
	@Override
	public String toString() {
		return "WordRes [word=" + word + ", picPath=" + picPath + ", voicePath=" + voicePath + ", kwyjVoicePath1="
				+ kwyjVoicePath1 + ", kwyj1=" + kwyj1 + ", kwyjVoicePath2=" + kwyjVoicePath2 + ", kwyj2=" + kwyj2
				+ ", kwyjVoicePath3=" + kwyjVoicePath3 + ", kwyj3=" + kwyj3 + ", kwyjVoicePath4=" + kwyjVoicePath4
				+ ", kwyj4=" + kwyj4 + ", kwyjVoicePath5=" + kwyjVoicePath5 + ", kwyj5=" + kwyj5 + ", kwyjVoicePath6="
				+ kwyjVoicePath6 + ", kwyj6=" + kwyj6 + ", qjdlVoicePath1=" + qjdlVoicePath1 + ", qjdl1=" + qjdl1
				+ ", qjdlVoicePath2=" + qjdlVoicePath2 + ", qjdl2=" + qjdl2 + ", qjdlVoicePath3=" + qjdlVoicePath3
				+ ", qjdl3=" + qjdl3 + ", qjdlVoicePath4=" + qjdlVoicePath4 + ", qjdl4=" + qjdl4 + ", qjdlVoicePath5="
				+ qjdlVoicePath5 + ", qjdl5=" + qjdl5 + ", qjdlVoicePath6=" + qjdlVoicePath6 + ", qjdl6=" + qjdl6
				+ ", videoPath1=" + videoPath1 + ", videoPath2=" + videoPath2 + ", videoPath3=" + videoPath3
				+ ", videoPath4=" + videoPath4 + ", videoPath5=" + videoPath5 + ", videoPath6=" + videoPath6 + "]";
	}
	
}
