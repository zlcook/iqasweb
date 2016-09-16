package com.cnu.iqas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.utils.PropertyUtils;

@Controller
//该类负责访问某个界面
@RequestMapping(value="/base")
public class BaseController implements ResourceLoaderAware{
	//资源加载器
	private ResourceLoader resourceLoader;
	/**
	 * mv.setViewName("redirect:/myforward.html?page=front/main");
	 * @param urladdress
	 * @return
	 */
	@RequestMapping(value="/myforward")
	public String forward(String page){
		return page;
	}
	
	/**
	 * 根据文件保存的相对路径查看图片
	 * @param savePath 文件保存的相对路径
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="look")
	public Void getResource(String savePath,HttpServletRequest request,HttpServletResponse response){
		
		    //1.获取文件系统的根路径:D:/Soft/autoiqasweb/
			String fileSystemRoot = PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR);
			//2.生成文件的绝对路径:D:/Soft/autoiqasweb/ifilesystem/noumenon/wordresource/voices/9fc81b51-8907-4acc-ae68-6da7a88924e5.mp3
			String fileSavePath = fileSystemRoot+savePath;
			
			//2.1获取文件资源
			Resource fileResource =resourceLoader.getResource("file:"+fileSavePath);
			//查看图片
			BaseForm.loadFile(response, fileResource);
			return null;
			
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		// TODO Auto-generated method stub
		this.resourceLoader = resourceLoader;
	}
}
