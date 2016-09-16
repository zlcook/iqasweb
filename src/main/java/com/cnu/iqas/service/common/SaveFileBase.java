package com.cnu.iqas.service.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.utils.PropertyUtils;

/**
* @author 周亮 
* @version 创建时间：2016年7月21日 上午10:20:10
* 类说明
*/
@Service("iSaveFile")
public class SaveFileBase implements ISaveFile {

	/**
	 * 保存文件
	 * @param relativedir 文件保存相对目录，该相对目录的地址在savepath.properties配置文件中有配置，通过PropertyUtils.get(key)
	 * 方法来获取文件的相对目录
	 * @param file  保存的文件
	 * @return  返回文件保存的带文件名的相对路径
	 * @throws Exception
	 */
	public String saveFile(String relativedir, CommonsMultipartFile file) throws Exception{
	
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


	private static String getExt(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
