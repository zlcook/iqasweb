package com.user.service.passinfo;

import com.user.entity.PassInfo;

/**
 * @author 刘玉婷
 * @version 创建时间：2016年6月29日 下午15:47:10
 */
public interface PassInfoService {

	
	/**
	 * 更新一条用户主题闯关进度,用户在指定主题里每进入一个新的场景时触发，onscene加1
	 * @param userId
	 * @param topic
	 */
	public void addonScene(String userId,String topic);
	
	/**
	 * 更新一条用户在指定主题的金币获取情况，用户在指定主题里每获取一次金币，就更新
	 * @param userId
	 * @param topic
	 * @param goals 新获取的金币数
	 */
	public void addGoals(String userId,String topic,int goals);
	
	/**
	 * 更新一条用户在指定主题的勋章获取情况，用户在指定主题里每获取一次勋章，就更新
	 * @param userId
	 * @param topic
	 * @param medals 新获取的勋章数
	 */
	public void addMedals(String userId,String topic,int medals);
	
	
	/**
	 * 删除指定用户指定主题的闯关信息
	 * @param userId
	 * @param topic
	 * @return 1：删除成功 0：指定闯关信息不存在 -1：其他异常删除失败
	 */
	public int delete(String userId,String topic);
	

	
	/**
	 * 查找指定用户指定主题的闯关信息
	 * @param userId
	 * @param topic
	 * @return
	 */
	public PassInfo find(String userId,String topic);
}
