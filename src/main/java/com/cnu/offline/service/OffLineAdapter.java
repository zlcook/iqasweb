package com.cnu.offline.service;

import com.cnu.offline.MobileStyleEnum;
import com.cnu.offline.exception.ThemeWordNotExistException;

/**
* @author 周亮 
* @version 创建时间：2016年11月9日 下午8:42:44
* 类说明
* 离线适配器，ios和android
*/
public interface OffLineAdapter<M,R> extends ICreateDocument<R>{
	/**
	 * 生成离线资源数据
	 * @param themenumber
	 * @param realGrade
	 * @param recommendGrade
	 * @return
	 * @throws ThemeWordNotExistException
	 */
	public OffLineBagResource<M,R> createOffLineBagResource(String themenumber, int realGrade, int recommendGrade) throws ThemeWordNotExistException;
	
	/**
	 * 创建离线包
	 * @param offLineBagResource
	 * @param ismasterPackage
	 */
	public void createOffLinePack(OffLineBagResource<M,R> offLineBagResource,boolean ismasterPackage);
	/**
	 * 返回版本，
	 * @return
	 */
	public MobileStyleEnum getMobileStyleEnum();
}
