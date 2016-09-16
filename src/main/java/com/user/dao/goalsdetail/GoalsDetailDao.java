package com.user.dao.goalsdetail;

import java.util.Date;
import java.util.List;

import com.cnu.iqas.dao.base.DAO;
import com.user.entity.GoalsDetail;


/**
 * 
 * @author 作者：刘玉婷
 * @version 创建时间：2016年6月29日下午 17:13:00
 * 
 */
public interface GoalsDetailDao extends DAO<GoalsDetail>{

	/**
	 * 查询指定用户在指定时间段内的收入或支出金币的记录
	 * @param userId
	 * @param inOut
	 * @return 用户金币收入或支出记录集合
	 */
	public List<GoalsDetail> find(String userId,int inOut,Date time1,Date time2);
	
	/**
	 * 查询指定用户的所有交易记录
	 * @param userId
	 * @return
	 */
	public List<GoalsDetail> find(String userId);
}
