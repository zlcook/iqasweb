package com.cnu.offline.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cnu.iqas.utils.WebUtils;
import com.cnu.offline.MobileStyleEnum;

/**
* @author 周亮 
* @version 创建时间：2016年9月2日 下午8:49:58
* 类说明:
* 存放离线压缩包文件。
* 1.一个压缩包的关键属性有：
* 移动端类型 ：1：ios; 2:android
* 主题编号：
* 推荐年级：用户经过测试得到的推荐年级
* 版本：压缩包的版本
* 
* 2.触发生成一个新的压缩包的条件有：
* 新的大主题
* 新的小主题
* 新的推荐年级
* 新的版本
* 
* 3.压缩包中有：
* words.xml文件：存储主题下所有单词的文本资源
* pictures:存放图片的文件夹
* videos：存放视频的文件夹
* pronunciations:存放发音资源文件夹
* audio:存放单词发音资源文件夹
*/
@Entity
@Table(name="t_offlinebag")
public class OffLineBag {
	/**
	 * 由移动端类型、主题编号、推荐年级的md5值所确定
	 */
	private String id;
	/**
	 * 压缩包名称，生成策略时间加后缀
	 */
	private String name;
	/**
	 * 移动端类型 android、ios
	 */
	private MobileStyleEnum mobilestyle;
	/**
	 * 所属主题编号
	 */
	private String themenumber;
	/**
	 * 推荐年级
	 */
	private int  recommendGrade;
	/**
	 * 实际年级
	 */
	private int realGrade;
	/**
	 * 视频推荐等级
	 */
	private int videoRecoDegree;
	/**
	 * 版本号
	 */
	private int version;
	/**
	 * 保存路径，相对路径
	 */
	private String savePath;
	/**
	 * 大小
	 */
	private long size;
	/**
	 * 压缩文件后缀
	 */
	private String ext;
	/**
	 * 创建时间
	 */
	private Date createTime=new Date();
	/**
	 * 下载量
	 */
	private long downsize=0L;
	/**
	 * 包含单词数
	 */
	private int wordsum;
	
	public OffLineBag() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OffLineBag(String name, MobileStyleEnum mobilestyle, String themenumber, int recommendGrade, int realGrade,
			String ext,int wordsum) {
		super();
		this.name = name;
		this.mobilestyle = mobilestyle;
		this.themenumber = themenumber;
		this.recommendGrade = recommendGrade;
		this.realGrade = realGrade;
		this.ext = ext;
		this.wordsum = wordsum;
		if( realGrade>recommendGrade)
			this.videoRecoDegree=1;
		else if(realGrade<recommendGrade)
			this.videoRecoDegree=3;
		else 
			this.videoRecoDegree=2;
	}

	public int getWordsum() {
		return wordsum;
	}

	public void setWordsum(int wordsum) {
		this.wordsum = wordsum;
	}

	@Id
	public String getId() {
		String idstr=this.themenumber+this.realGrade+this.recommendGrade;
		return WebUtils.MD5Encode(idstr);
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(length=100,nullable=false,unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRealGrade() {
		return realGrade;
	}

	public void setRealGrade(int realGrade) {
		this.realGrade = realGrade;
	}

	public int getVideoRecoDegree() {
		return videoRecoDegree;
	}
	public void setVideoRecoDegree(int videoRecoDegree) {
		this.videoRecoDegree = videoRecoDegree;
	}
	@Column(updatable=false,nullable=false)
	@Enumerated(EnumType.STRING)
	public MobileStyleEnum getMobilestyle() {
		return mobilestyle;
	}
	public void setMobilestyle(MobileStyleEnum mobilestyle) {
		this.mobilestyle = mobilestyle;
	}
	@Column(nullable=false,updatable=false)
	public String getThemenumber() {
		return themenumber;
	}
	public void setThemenumber(String themenumber) {
		this.themenumber = themenumber;
	}
	@Column(nullable=false,updatable=false)
	public int getRecommendGrade() {
		return recommendGrade;
	}
	public void setRecommendGrade(int recommendGrade) {
		this.recommendGrade = recommendGrade;
	}
	@Column(nullable=false,updatable=false)
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Column(nullable=false,updatable=false)
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	@Column(nullable=false,updatable=false)
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	@Column(nullable=false,updatable=false)
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public long getDownsize() {
		return downsize;
	}
	public void setDownsize(long downsize) {
		this.downsize = downsize;
	}

	@Override
	public String toString() {
		return "OffLineBag [id=" + id + ", name=" + name + ", mobilestyle=" + mobilestyle + ", themenumber="
				+ themenumber + ", recommendGrade=" + recommendGrade + ", realGrade=" + realGrade + ", videoRecoDegree="
				+ videoRecoDegree + ", version=" + version + ", savePath=" + savePath + ", size=" + size + ", ext="
				+ ext + ", createTime=" + createTime + ", downsize=" + downsize + "]";
	}
	
	
}
