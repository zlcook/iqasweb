package com.cnu.iqas.formbean.Recommend;
import java.util.Date;
import com.cnu.iqas.formbean.BaseForm;
/**
 * @author 王文辉
 * 2016-5-20
 */
public class AnswerForm extends BaseForm { 
	/**
	 * answerId，答案标识
	 */
	 private int answerId;
	 /**
	 * 内容
	*/
	private String content;
	 /**
	 * 属性
	 */
	private String attributes;
	/**
	 * 媒体类型1.文本 2、视频 3、MP3 4、绘本
	 */
	private String mediaType;
	/**
	 * 难度值       1.高 2.中 3.低
	 */
	private String difficulty;
	/**
	 * 是否通过检查
	 */
	private String  checked;
	/**
	 * 添加类型  1.自动      2.人工
	 */
	private String addType;
	/**
	 * 媒体文件的路径
	 */
	private String mediaUrl;

	private int questionId;

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
}
