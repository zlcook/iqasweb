package com.user.service.goalsdetail;

import java.util.Date;
import java.util.List;

import com.user.entity.GoalsDetail;

/**
 * @author 刘玉婷
 * @version 创建时间：2016年6月29日 下午20:30:10
 */
public interface GoalsDetailService {

    /**
     * 添加一条用户金币交易记录
     * @param userId
     * @param inORout 1：收入 2：支出
     * @param goals 本次交易金币数
     * @param content 金币来源/消费用处 1：前测获得 2：小测获得 3：购买道具 4：其他方式消费
     * @param time 交易时间
     */
	public void add(String userId,int inORout,int goals,int content,Date time);
	
	/**
	 * 查找该用户在指定时间段的收入或支出的金币交易记录
	 * @param userId
	 * @param inOut 1：收入 2：支出
	 * @param time1 开始时间
	 * @param time2 截止时间
	 * @return 交易记录list
	 */
	public List<GoalsDetail> find(String userId,int inORout,Date time1,Date time2);
	
	/**
	 * 获取当前用户的金币数
	 * @param userId
	 * @return 金币数
	 */
	public int getGoals(String userId);
}
