package com.cnu.iqas.service.user;

import com.cnu.iqas.bean.user.User;

/**
* @author 周亮 
* @version 创建时间：2016年1月19日 上午9:38:25
* 类说明：你的进步信息获取接口
*/
public interface StudyProgressService {
	/**
	 * 通过率对比
	 * @param user
	 * @return
	 */
	public String successRate(User user);
	/**
	 * 最爱的闯关
	 * @param user
	 * @return
	 */
	public String favoriteGameType(User user);
	/**
	 * 玩游戏时间
	 * @param user
	 * @return
	 */
	public String timeOfGame(User user);
}
