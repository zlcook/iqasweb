package com.cnu.iqas.service.user;

import java.util.List;

import com.cnu.iqas.bean.user.User;

/**
* @author 周亮 
* @version 创建时间：2016年1月19日 上午9:26:27
* 类说明 :学习日记接口
*/
public interface StudyDiaryService {

	/**
	 * 根据用户返回用户学习记录信息
	 * @param user
	 * @return
	 */
	public String studyRecord(User user);
	/**
	 * 闯关类型对比
	 * @param user
	 * @return
	 */
	public List<String> gameTypeContrast(User user);
}
