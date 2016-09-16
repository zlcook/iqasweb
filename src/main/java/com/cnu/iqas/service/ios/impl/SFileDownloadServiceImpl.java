package com.cnu.iqas.service.ios.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.cnu.iqas.service.common.IFileDownloadService;

/**
* @author 周亮 
* @version 创建时间：2016年3月9日 下午8:46:11
* 类说明
*/
@Service("sfileDownloadService")
public class SFileDownloadServiceImpl implements IFileDownloadService {
	//资源加载器
	private ResourceLoader resourceLoader;
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		// TODO Auto-generated method stub
		this.resourceLoader = resourceLoader;
	}
	/**
	 * 获取资源接口
	 * @param savePath 资源路径：资源前缀+资源路径
	 * file:c:/shop/logo.img
	 * classpath:banner.txt
	 * http://www.apress.com/hehe.mp4
	 * @return
	 */
	public Resource getFileResource(String savePath){
		return resourceLoader.getResource(savePath);
	}
}
