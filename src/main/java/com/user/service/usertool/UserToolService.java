package com.user.service.usertool;

import com.user.entity.UserTool;

/**
 * @author 刘玉婷
 * @version 创建时间：2016年6月29日 下午16:40:10
 */
public interface UserToolService {

	/**
	 * 添加或更新用户道具个数
	 * @param UserId
	 * @param toolId
	 * @param count 添加该道具个数
	 */
	
	public void update(String userId,String toolId,int count);
    /**
     * 删除指定用户指定道具的信息
     * @param userId
     * @param toolId
     * @return 1：删除成功 0：该对象不存在 -1：其他异常删除失败
     */
	public int delete(String userId,String toolId);

	 
	/**
	 * 查询指定用户指定道具信息
	 * @param userId
	 * @param toolId
	 * @return 用户道具对象
	 */
	public UserTool find(String userId,String toolId);
}
