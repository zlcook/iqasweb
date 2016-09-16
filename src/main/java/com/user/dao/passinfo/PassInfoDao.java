package com.user.dao.passinfo;

import java.util.List;

import com.cnu.iqas.dao.base.DAO;
import com.user.entity.PassInfo;

/**
 * 
 * @author 作者：刘玉婷
 * @version 创建时间：2016年6月29日下午 15:34:00
 * 
 */
public interface PassInfoDao extends DAO<PassInfo> {

	/**
	 * 查询指定用户指定主题闯关信息
	 * @param userId
	 * @param topic
	 * @return 
	 */
	public PassInfo find(String userId,String topic);
	
	/**
	 * 查询指定用户所有主题闯关信息
	 * @param userId
	 * @return 用户闯关对象集合
	 */
	public List<PassInfo> find(String userId);
}
