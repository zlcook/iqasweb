package com.cnu.iqas.bean.iword;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2016年5月10日 上午11:00:51
* 类说明：过时，请使用WordResouce
*/
@Entity
@Table(name="t_wordResoucecustom")
@Deprecated 
public class WordResouceCustom {
	/**
	 * 单词id
	 */
	private WordId id;
	
	/**
	 * 图片保存路径，相对于文件系统根目录的路径
	 */
	private String picRelativePath;
	/**
	 * 绘本路径
	 */
	private String bookRelativePath;
	/**
	 * 发音路径
	 */
	private String voiceRelativePath;
	
	public WordResouceCustom(WordId id, String picRelativePath, String bookRelativePath, String voiceRelativePath) {
		super();
		this.id = id;
		this.picRelativePath = picRelativePath;
		this.bookRelativePath = bookRelativePath;
		this.voiceRelativePath = voiceRelativePath;
	}
	public WordResouceCustom() {
		super();
		// TODO Auto-generated constructor stub
	}
	@EmbeddedId
	public WordId getId() {
		return id;
	}
	public void setId(WordId id) {
		this.id = id;
	}
	
	public String getPicRelativePath() {
		return picRelativePath;
	}
	public void setPicRelativePath(String picRelativePath) {
		this.picRelativePath = picRelativePath;
	}
	public String getBookRelativePath() {
		return bookRelativePath;
	}
	public void setBookRelativePath(String bookRelativePath) {
		this.bookRelativePath = bookRelativePath;
	}
	public String getVoiceRelativePath() {
		return voiceRelativePath;
	}
	public void setVoiceRelativePath(String voiceRelativePath) {
		this.voiceRelativePath = voiceRelativePath;
	}
	
	

}
