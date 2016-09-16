package com.cnu.offline.bean;

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
* @version 创建时间：2016年7月4日 上午10:10:30
* 类说明:存放某个主题下所有单词，并以xml文件存储。
* 
*/

@Entity
@Table(name="t_offlinewordxml")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class OffLineWordXml {
	/**
	 * id
	 */
	private String id;
	/**
	 * 所属主题
	 */
	private String themeId;
	/**
	 * 文件名称
	 */
	private String name;
	/**
	 * 版本
	 */ 
	private String version;
	/**
	 * 时间
	 */
	private Date createTime=new Date();
	/**
	 * 大小
	 */
	private long size;
	/**
	 * 保存路径
	 */
	private String savePath;
	/**
	 * 下载次数
	 */
	private int count=0;
	
	private boolean visible=true;

	public OffLineWordXml(String themeId, String name, String version, long size, String savePath, int count) {
		super();
		this.themeId = themeId;
		this.name = name;
		this.version = version;
		this.size = size;
		this.savePath = savePath;
		this.count = count;
	}
	public OffLineWordXml() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Column(nullable=false)
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
