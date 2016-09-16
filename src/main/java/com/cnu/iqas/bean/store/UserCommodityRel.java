package com.cnu.iqas.bean.store;

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
* @version 创建时间：2015年12月22日 下午1:05:09
* 类说明 用户商品关系类
*/

@Entity
@Table(name="t_usercommodityrel")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class UserCommodityRel {
	
	/**
	 * 关系id
	 */
	private String id;
	/**
	 * 商品id
	 */
	private String coId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户拥有该商品的数量，初始值为0
	 */
	private Integer count=0;
	/**
	 * 商品类型id
	 */
	private String typeId;
	
	/**
	 * 该记录是否被删除
	 */
	private Boolean visible=true;
	/**
	 * 第一次购买时间，初始值为当前时间
	 */
	private Date createTime=new Date();
	/**
	 * 修改时间/最后一次购买时间
	 */
	private Date modifyTime;
	
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(nullable=false)
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	@Column(nullable=false)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Column(nullable=false)
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	@Column(nullable=false)
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
