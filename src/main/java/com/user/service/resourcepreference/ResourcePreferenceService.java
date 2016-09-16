package com.user.service.resourcepreference;
/**
 * 
 * @author 刘玉婷
 * @version 创建时间：2016年8月22日 下午18:02:10
 *
 */
public interface ResourcePreferenceService {

	/**
	 * 添加一条新纪录
	 * @param userId
	 * @param feature
	 * @param featureValue
	 * @param pfeedback1
	 * @param pfeedback2
	 * @param pfeedback3
	 */
	public void add(String userId, String feature, String featureValue, double pfeedback1, double pfeedback2, double pfeedback3);

	/**
	 * 更新用户资源偏好信息
	 * @param userId
	 */
	public void update(String userId);
	
	/**
	 * 根据用户对资源属性和媒体类型的偏好进行推荐度的计算
	 * @param userId
	 * @param aspect
	 * @param mediaType
	 * @return
	 */
	public double recommendByPreference(String userId,String aspect,int mediaType);
	
}
