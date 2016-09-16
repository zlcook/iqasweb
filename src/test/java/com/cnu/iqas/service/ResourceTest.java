package com.cnu.iqas.service;

import java.beans.Encoder;
import java.io.IOException;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.cnu.iqas.service.ios.impl.SFileDownloadServiceImpl;

import jxl.biff.EncodedURLHelper;

/**
* @author 周亮 
* @version 创建时间：2016年5月16日 下午1:53:08
* 类说明
*/
public class ResourceTest {

	private static SFileDownloadServiceImpl resourceLoader;
	/*@Before
	public void init(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		 resourceLoader=ac.getBean(SFileDownloadServiceImpl.class);
	}
	
	@Test
	public void loadFile() throws IOException{
		
		String root= "file:G:/wordResource/root/";
	    
		    String localPath = root+"picture/took(4#7#6#11).png";
		    localPath= localPath.replace("#", UrlEncoded.encodeString("#"));
			  if( localPath.indexOf("#")>0){
				  System.out.println("存在#");
			  }
			  System.out.println(localPath);
			 Resource res = resourceLoader.getFileResource(localPath);
			 if( res!=null &&  res.getFile().exists()){
				 System.out.println("存在");
			 }else{
				 System.out.println("不存在："+localPath);
			 }
	}*/
}
