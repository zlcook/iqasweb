package com.cnu.iqas.service.common;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
* @author 周亮 
* @version 创建时间：2016年7月21日 上午10:17:20
* 类说明
*/
public interface ISaveFile {

	/**
	 * 保存文件
	 * @param relativedir 文件保存相对目录，该相对目录的地址在savepath.properties配置文件中有配置，通过PropertyUtils.get(key)
	 * 方法来获取文件的相对目录
	 * @param file  保存的文件
	 * @return  返回文件保存的带文件名的相对路径
	 * @throws Exception
	 */
	public   String saveFile(String relativedir, CommonsMultipartFile file) throws Exception;
}
