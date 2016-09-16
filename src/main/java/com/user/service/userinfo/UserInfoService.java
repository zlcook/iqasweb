package com.user.service.userinfo;

import com.user.entity.UserInfo;

/**
 * @author 刘玉婷
 * @version 创建时间：2016年6月29日 下午15:15:10
 */
public interface UserInfoService {

	/**
	 * 添加一条用户信息
	 * @param ui 用户信息对象
	 */
	public void add(UserInfo ui);
	
	/**
	 * 根据用户ID删除一条用户信息
	 * @param userId
	 * @return 1:删除成功 0：该用户信息不存在 -1：其他异常失败情况
	 */
	public int delete(String userId);
	
	/**
	 * 根据用户id查找一条用户信息
	 * @param userId
	 */
	public UserInfo find(String userId);
	
	/**
	 * 更新一条用户信息
	 * @param ui
	 */
	public void update(UserInfo ui);
}
