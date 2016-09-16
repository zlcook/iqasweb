package com.cnu.iqas.controller.web.ontology;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.http.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.MetaValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
* @author 周亮 
* @version 创建时间：2015年11月12日 下午10:36:05
* 类说明:处理文件上传、下载任务
*/
@Controller
@RequestMapping(value="/file")
public class FileController implements ServletContextAware{
	private ServletContext servletContext;
	
	private final static Logger logger = LogManager.getLogger(FileController.class);
	@RequestMapping(value="/uploadPage")
	public String updatePage(){
		return "admincenter/resource/uploadPage";
	}
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public ModelAndView updateFile(@RequestParam("name") String name, @RequestParam("file") CommonsMultipartFile  file){
		ModelAndView mv = new ModelAndView();
		if (!file.isEmpty()) {
			   String path = servletContext.getRealPath("/upload/word/images");  //获取本地存储路径
			   logger.info("文件路径："+path);
			   System.out.println(path);
			   File dir = new File(path);
			   if( !dir.exists())
				   dir.mkdirs();
			   String fileName = file.getOriginalFilename();
			   String fileType = fileName.substring(fileName.lastIndexOf("."));
			   System.out.println(fileType);
			   File file2 = new File(dir,new Date().getTime() + fileType); //新建一个文件 
			   try {
				    file.getFileItem().write(file2); //将上传的文件写入  新建的文件中
			   } catch (Exception e) {
				    e.printStackTrace();
			   }
			    	//返回页面,此处应该防止表单重复提交
					mv.setViewName("share/message");
					mv.addObject("message", "上传成功!");
					mv.addObject("urladdress", "/file/uploadPage");
					//mv.setViewName("redirect:/base/myforward.html?page=share/message&message="+URLEncoder.encode("上传成功", "UTF-8")+"&urladdress=file/uploadPage");
					
			}else{
				mv.setViewName("share/message");
				mv.addObject("message", "上传失败!");
				mv.addObject("urladdress", "/file/uploadPage.html");
			}
		return mv;
	}
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
}
