package com.cnu.iqas.bean.iword;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Required;

/**
* @author 周亮 
* @version 创建时间：2015年11月9日 
* @author 王文辉(修改)
* @version 创建时间：2016年1月15日 
* 类说明:单词类。
*/
@Entity
@Table(name="t_iword")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class Iword {
	//单词存储uuid
	private String uuid;
	/**
	 * 单词标识，由版本+册数+单元+序号 组成：如2/3/6/1 即北京版第3册第6单元的第一个单词
	 */
	private String id;
	//单词内容
	private String content;
	/**
	 * 版本
	 * 1：北师大版
	 * 2：北京版
	 * 3：外研社新标准
	 * 4：外研社一年级起
	 * 5：人教版
	 * 6：朗文版
	 */
	private int version;
	//册数
	private int book;
	//单元
	private int unit;
	//序号,单词在本单元的序号
	private int rank;
	
	/*单词所拥有资源 1对多
	private Set<Resource> resources = new HashSet<Resource>();
	//单词属性所拥有的资源 1对多
	private Set<AttributeResource> attributeresources = new HashSet<AttributeResource>();*/
	
	//课文原句
	private String propertyText;
	//情景段落
	private String propertyScene;
	//延伸例句
	private String propertyExtend;
	
	//联想
	private String propertyAssociate;
	//同义词
	private String propertySynonyms;
	//反义词
	private String propertyAntonym;
	//拓展
	private String propertyExpand; 
	//常用  
	private String propertyCommonUse;
	
	//百科
	private String propertyNcyclopedia;
	//用法   
	private String propertyUse;
	//创建时间
	private Date createtime=new Date();
	public Iword(String id, String content) {
		super();
		this.id = id;
		this.content = content;
		String[] cols= this.id.split("/");
		this.version=Integer.parseInt(cols[0]);
		this.book=Integer.parseInt(cols[1]);
		this.unit=Integer.parseInt(cols[2]);
		this.rank=Integer.parseInt(cols[3]);
		
	}
	public Iword() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id @Column(nullable=false) @GeneratedValue(generator="uuidGenderator")
	public String getUuid() {
		return uuid;
	}
	public Iword(String id,String propertyText, String propertyScene, String propertyExtend, String propertyAssociate,
			String propertySynonyms, String propertyAntonym, String propertyExpand, String propertyCommonUse,
			String propertyNcyclopedia, String propertyUse) {
		super();
		this.id =id;
		this.propertyText = propertyText;
		this.propertyScene = propertyScene;
		this.propertyExtend = propertyExtend;
		this.propertyAssociate = propertyAssociate;
		this.propertySynonyms = propertySynonyms;
		this.propertyAntonym = propertyAntonym;
		this.propertyExpand = propertyExpand;
		this.propertyCommonUse = propertyCommonUse;
		this.propertyNcyclopedia = propertyNcyclopedia;
		this.propertyUse = propertyUse;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Column(nullable=false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/*@OneToMany(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},mappedBy="iword")//有Resources一方来维护关系，
	@JoinColumn(name="iwordid")
	public Set<Resource> getResources() {
		return resources;
	}
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},mappedBy="iword")//有Resources一方来维护关系，
	@JoinColumn(name="iwordid")
	public Set<AttributeResource> getAttributeresources() {
		return attributeresources;
	}
	public void setAttributeresources(Set<AttributeResource> attributeresources) {
		this.attributeresources = attributeresources;
	}*/

	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return createtime;
	}
	public String getPropertyText() {
		return propertyText;
	}
	public void setPropertyText(String propertyText) {
		this.propertyText = propertyText;
	}
	public String getPropertyScene() {
		return propertyScene;
	}
	public void setPropertyScene(String propertyScene) {
		this.propertyScene = propertyScene;
	}
	public String getPropertyExtend() {
		return propertyExtend;
	}
	public void setPropertyExtend(String propertyExtend) {
		this.propertyExtend = propertyExtend;
	}
	public String getPropertyAssociate() {
		return propertyAssociate;
	}
	public void setPropertyAssociate(String propertyAssociate) {
		this.propertyAssociate = propertyAssociate;
	}
	public String getPropertySynonyms() {
		return propertySynonyms;
	}
	public void setPropertySynonyms(String propertySynonyms) {
		this.propertySynonyms = propertySynonyms;
	}
	public String getPropertyAntonym() {
		return propertyAntonym;
	}
	public void setPropertyAntonym(String propertyAntonym) {
		this.propertyAntonym = propertyAntonym;
	}
	public String getPropertyExpand() {
		return propertyExpand;
	}
	public void setPropertyExpand(String propertyExpand) {
		this.propertyExpand = propertyExpand;
	}
	public String getPropertyCommonUse() {
		return propertyCommonUse;
	}
	public void setPropertyCommonUse(String propertyCommonUse) {
		this.propertyCommonUse = propertyCommonUse;
	}
	public String getPropertyNcyclopedia() {
		return propertyNcyclopedia;
	}
	public void setPropertyNcyclopedia(String propertyNcyclopedia) {
		this.propertyNcyclopedia = propertyNcyclopedia;
	}
	public String getPropertyUse() {
		return propertyUse;
	}
	public void setPropertyUse(String propertyUse) {
		this.propertyUse = propertyUse;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 版本
	 * 1：北师大版
	 * 2：北京版
	 * 3：外研社新标准
	 * 4：外研社一年级起
	 * 5：人教版
	 * 6：朗文版
	 */
	@Column(nullable=false)
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Column(nullable=false)
	public int getBook() {
		return book;
	}
	public void setBook(int book) {
		this.book = book;
	}
	@Column(nullable=false)
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	
	@Column(nullable=false)
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "Iword [id=" + id + ", content=" + content + ", version=" + version + ", book=" + book + ", unit=" + unit
				+ ", rank=" + rank + ", createtime=" + createtime + "]";
	}

	
}
