package com.cnu.iqas.bean.ios;

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
* @version 创建时间：2016年3月9日 下午12:53:13
* 类说明：用户作品表
*/
@Entity
@Table(name="t_suserwork")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class SuserWork {
	/**
	 * 作品id
	 */
	private String workId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 作品所属单词
	 */
	private String word;
	/**
	 * 文件大小
	 */
	private Long size;
	/**
	 * 文件后缀
	 */
	private String ext;
	/**
	 * 作品保存路径
	 */
	private String savePath;
	/**
	 * 作品金币数
	 */
	private Integer golden=0;
	/**
	 * 作品得分
	 */
	private Integer score=0;
	/**
	 * 作品类型
	 */
	private Integer type;
	/**
	 * 浏览次数
	 */
	private Integer viewTimes=0;
	/**
	 * 上传日期
	 */
	private Date createTime=new Date();
	
	@Id  @GeneratedValue(generator="uuidGenderator")
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	@Column(nullable=false)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(nullable=false)
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Column(nullable=false)
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public Integer getGolden() {
		return golden;
	}
	public void setGolden(Integer golden) {
		this.golden = golden;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Column(nullable=false)
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getViewTimes() {
		return viewTimes;
	}
	public void setViewTimes(Integer viewTimes) {
		this.viewTimes = viewTimes;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
}
