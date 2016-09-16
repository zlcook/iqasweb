package com.cnu.iqas.formbean.store;

import com.cnu.iqas.formbean.BaseForm;

/**
* @author 周亮 
* @version 创建时间：2015年12月21日 下午8:13:03
* 类说明
*/
public class StoreForm extends BaseForm {
	/**
	 * 商品类型id或者商品id
	 */
	private String id;
	/**
	 * 商品类型id，在添加商品时，需要商品类型id，但是商品本身也有id，所以为了区分，此时，就让typeid作为商品类型的id,id作为商品的id
	 */
	private String typeid;
	
	
	/**
	 * 商品类型名，或者商品名称
	 */
	private String name;
	/**
	 * 商品类型等级
	 */
	private Integer grade;
	/**
	 * 商品所值金币数
	 */
	private Integer coinCount;
	
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
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public Integer getCoinCount() {
		return coinCount;
	}
	public void setCoinCount(Integer coinCount) {
		this.coinCount = coinCount;
	}
	
}
