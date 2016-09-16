package com.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户资源偏好表
 * @author 刘玉婷
 * @version 创建时间：2016年8月22日 16:54
 */
@Entity
@Table(name="t_resourcepreference")
public class ResourcePreference {

	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 private int id;
	 @Column(name = "userId")
	 private String userId;
	 @Column(name = "feature")
	 private String feature;
	 public ResourcePreference() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResourcePreference(String userId, String feature, String featureValue, double pfeedback1, double pfeedback2,
			double pfeedback3) {
		super();
		this.userId = userId;
		this.feature = feature;
		this.featureValue = featureValue;
		this.pfeedback1 = pfeedback1;
		this.pfeedback2 = pfeedback2;
		this.pfeedback3 = pfeedback3;
	}
	@Column(name = "featurevalue")
	 private String featureValue;
	 @Column(name = "pfeedback1")
	 private double pfeedback1;
	 @Column(name = "pfeedback2")
	 private double pfeedback2;
	 @Column(name = "pfeedback3")
	 private double pfeedback3;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getFeatureValue() {
		return featureValue;
	}
	public void setFeatureValue(String featureValue) {
		this.featureValue = featureValue;
	}
	public double getPfeedback1() {
		return pfeedback1;
	}
	public void setPfeedback1(double pfeedback1) {
		this.pfeedback1 = pfeedback1;
	}
	public double getPfeedback2() {
		return pfeedback2;
	}
	public void setPfeedback2(double pfeedback2) {
		this.pfeedback2 = pfeedback2;
	}
	public double getPfeedback3() {
		return pfeedback3;
	}
	public void setPfeedback3(double pfeedback3) {
		this.pfeedback3 = pfeedback3;
	}
}
