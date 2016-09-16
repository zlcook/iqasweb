package com.user.dao.resourcepreference;

import com.cnu.iqas.dao.base.DAO;
import com.user.entity.ResourcePreference;


/**
 * 用户资源偏好
 * @author 刘玉婷
 * @version 创建时间：2016年8月22日下午 17:54:00
 */
public interface ResourcePreferenceDao extends DAO<ResourcePreference>{

	/**
	 * 获取指定用户对指定属性取指定值的偏好信息
	 * @param userId
	 * @param feature
	 * @param featureValue
	 * @return
	 */
	public ResourcePreference find(String userId, String feature, String featureValue);
	
}
