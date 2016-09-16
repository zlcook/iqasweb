package com.cnu.iqas.bean.store;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author 周亮 
* @version 创建时间：2015年12月18日 下午2:01:06
* 类说明 商品类型
*/
@Entity
@Table(name="t_commoditytype")
@GenericGenerator(strategy="uuid",name="uuidGenerator")
public class CommodityType {
	/**
	 * id，标识
	 */
	private String id;
	/**
	 * 商品类型名称，唯一
	 */
	private String name;
	/**
	 * 商品类型的等级，唯一
	 */
	private Integer grade;
	/**
	 * 包含商品数，默认0
	 */
	private Integer count=0;
	/**
	 * 是否可见，默认可见值为true
	 */
	private boolean visible=true;
	/**
	 * 创建日期,默认当前日期
	 */
	private Date createTime=new Date();
	@Id @GeneratedValue(generator="uuidGenerator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(nullable=false,unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@Column(nullable = false)
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(unique=true,nullable=false)
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	

}
