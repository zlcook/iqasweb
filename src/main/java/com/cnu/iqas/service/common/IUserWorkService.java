package com.cnu.iqas.service.common;

import com.cnu.iqas.bean.ios.SuserWork;

/**
* @author 周亮 
* @version 创建时间：2016年3月9日 下午4:04:55
* 类说明:用户作品服务类
*/
public interface IUserWorkService<T> {
	/**
	 * 保存学生作品
	 * @param work
	 */
	public void save(T work);
}
