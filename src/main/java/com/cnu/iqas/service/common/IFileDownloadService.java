package com.cnu.iqas.service.common;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
* @author 周亮 
* @version 创建时间：2016年3月9日 下午7:11:06
* 类说明:文件下载服务
*/
public interface IFileDownloadService extends ResourceLoaderAware {
	/**
	 * 获取资源接口
	 * @param savePath 资源路径：资源前缀+资源路径
	 * file:c:/shop/logo.img
	 * classpath:banner.txt
	 * http://www.apress.com/hehe.mp4
	 * @return
	 */
	public Resource getFileResource(String savePath);
}
