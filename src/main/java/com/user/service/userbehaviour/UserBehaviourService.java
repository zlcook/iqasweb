package com.user.service.userbehaviour;

import java.util.Date;
import java.util.List;

import com.user.entity.UserBehaviour;

/**
 * 用户行为数据
 * @author 刘玉婷
 * @version 创建时间：2016年8月22日 下午16:05:10
 */
public interface UserBehaviourService {

	/**
	 * 添加一条记录
	 * @param userId
	 * @param doWhere 何地
	 * @param doWhat 相关内容
	 * @param doWhen 何时
	 */
	public void addUserBehaviour(String userId,String doWhere,String doWhat,Date doWhen);
	
	/**
	 * 查找指定用户的行为数据记录
	 * @param userId
	 * @return
	 */
	public List<UserBehaviour> find(String userId);
}
