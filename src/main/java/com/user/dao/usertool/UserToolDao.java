package com.user.dao.usertool;

import java.util.List;

import com.cnu.iqas.dao.base.DAO;
import com.user.entity.UserTool;

/**
 * 
 * @author 作者：刘玉婷
 * @version 创建时间：2016年6月29日下午 16:13:00
 * 
 */
public interface UserToolDao extends DAO<UserTool>{
 
	/**
	 * 查询指定用户指定道具的信息
	 * @param userId
	 * @param toolId
	 * @return 用户道具对象
	 */
	public UserTool find(String userId,String toolId);
	
	/**
	 * 查询指定用户所有的道具信息
	 * @param userId
	 * @return 用户道具对象集合
	 */
	public List<UserTool> find(String userId);
}
