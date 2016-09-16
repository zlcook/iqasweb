package com.cnu.iqas.service.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;

/**
* @author 周亮 
* @version 创建时间：2016年7月21日 下午4:21:49
* 类说明
*/
public interface IFileService extends IFileDownloadService ,ISaveFile{
	/**
	 * 下载文件/图片
	 * @param response  响应请求
	 * @param fileResource  文件资源
	 * @param isLoad 下载还是查看模式 ,false:查看模式，true:下载模式
	 */
	public void loadFile(HttpServletResponse response,Resource fileResource,boolean isLoad);

}
