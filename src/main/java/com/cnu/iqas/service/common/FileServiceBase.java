package com.cnu.iqas.service.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.utils.PropertyUtils;

/**
* @author 周亮 
* @version 创建时间：2016年7月21日 下午4:08:08
* 类说明
*/
@Service("iFileService")
public class FileServiceBase implements IFileService {

	//资源加载器
	private ResourceLoader resourceLoader;
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		// TODO Auto-generated method stub
		this.resourceLoader = resourceLoader;
	}

	@Override
	public Resource getFileResource(String savePath) {
		// TODO Auto-generated method stub
		return resourceLoader.getResource(savePath);
	}

	@Override
	public String saveFile(String relativedir, CommonsMultipartFile file) throws Exception {

		//文件原名称
	   String fileName = file.getOriginalFilename();
	   //文件后缀名
	   String fileExt = getExt(fileName);
	   //生成日期的文件名
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
	   String savefilename = sdf.format(new Date())+ "."+fileExt;
	  
	   //获取保存的绝对路径
	   String absolutepath=null;
	   if( relativedir != null && !relativedir.equals("")){
		   //项目实际根目录
		   String fileSystemDir=PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR);
		   
		   absolutepath=fileSystemDir+relativedir;
		   //生成保存路径文件
		   File filedir = new File(absolutepath);
		   //不存在生成
		   if(!filedir.exists()){
			   filedir.mkdirs();
		   }
		   File savefile = new File(filedir,savefilename); //新建保存的文件 
		   try {//将上传的文件写入  新建的文件中
			    file.getFileItem().write(savefile); 
			  //相对路径   relativedir+"/"+filesavename
				 return relativedir+"/"+savefilename;
		   }catch(FileNotFoundException fe){
			   throw new RuntimeException("文件不存在！");
		   }catch (Exception e) {
			    e.printStackTrace();
			    throw new RuntimeException("保存图片操作文件出错！");
		   }
	   }else{
		  throw new RuntimeException("保存路径未设置！");
	   }
	}

	/**
	 * 得到文件后缀名
	 * @param fileFileName
	 * @return
	 */
	private  String getExt(String fileFileName){
		return fileFileName.substring(fileFileName.lastIndexOf('.')+1).toLowerCase();
	}

	@Override
	public void loadFile(HttpServletResponse response, Resource fileResource, boolean isLoad) {

		response.setCharacterEncoding("utf-8");
        //response.setContentType("multipart/form-data");
        if( isLoad){
        		response.setContentType("multipart/form-data");
        		response.setHeader("Content-Disposition", "attachment;fileName="+ fileResource.getFilename());
         }
		//3.建立文件
		try {
			//4.建立字节读取流
			InputStream is = fileResource.getInputStream();
			//5.建立缓冲流
			BufferedInputStream bis = new BufferedInputStream(is);
			//6.获取response的输出流
			OutputStream os = response.getOutputStream();
			//7.缓冲器2kb
			byte[] buf= new byte[2048];
			//8.进行传输
			int len=0;
			int count=0;
			while( (len=bis.read(buf))!=-1)
			{
				count+=len;
				os.write(buf, 0, len);
			}
			//9.刷新缓冲器
			os.flush();
			os.close();
			bis.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
