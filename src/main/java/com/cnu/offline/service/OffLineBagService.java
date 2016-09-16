package com.cnu.offline.service;

import com.cnu.offline.bean.OffLineBag;

/**
* @author 周亮 
* @version 创建时间：2016年9月10日 下午3:00:27
* 类说明
*/
public interface OffLineBagService {
	/**
	 * 根据主题、推荐年级、实际年级查询主离线包
	 * @param themenumber
	 * @param recommendGrade
	 * @param realGrade
	 * @return
	 */
	public OffLineBag find(String themenumber,int recommendGrade,int realGrade);
	/**
	 * 判断主题的主离线包是否存在
	 * @param themenumber 主题
	 * @param realGrade 实际年级
	 * @return true:存在,false不存在
	 */
	public boolean existMasterBag(String themenumber, int realGrade);
	/**
	 * 添加离线包
	 * @param offLineBag
	 */
	public void add(OffLineBag offLineBag);
	
	/**
	 * 更新
	 * @param offLineBag
	 */
	public void update(OffLineBag offLineBag);
	
	
}
