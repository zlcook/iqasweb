package com.cnu.iqas.formbean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.constant.ResourceConstant;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;

public class BaseForm {
	/*当前页*/
	private int page = 1;  
	/**
	 * 每页显示最大数，默认10
	 */
	private int maxresult=10;
	/*是否查询*/
	private String query; 
	/*错误集合*/
	private Map<String,String> errors = new HashMap<String,String>();
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page < 1 ? 1 : page;
	}
	
	public int getMaxresult() {
		return maxresult;
	}
	public void setMaxresult(int maxresult) {
		this.maxresult = maxresult;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
	/**
	 * 检查字符串是否有效
	 * @param str
	 * @return true：有效，
	 */
	public static boolean validate(String str)
	{
		if( null !=str && !"".equals(str.trim()))
				return true;
		else {
			return false;
		}
	}
	/**
	 * 保存文件
	 * @param relativedir 文件保存相对目录，该相对目录的地址在savepath.properties配置文件中有配置，通过PropertyUtils.get(key)
	 * 方法来获取文件的相对目录
	 * @param file  保存的文件
	 * @return  返回文件保存的带文件名的相对路径
	 * @throws Exception
	 */
	public static  String saveFile(ServletContext servletContext,String relativedir, CommonsMultipartFile file) throws Exception{
	
		//文件原名称
	   String fileName = file.getOriginalFilename();
	   //文件后缀名
	   String fileExt = getExt(fileName);
	   //生成随机的文件名
	   String savefilename = UUID.randomUUID().toString()+ "."+fileExt;
	  
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
	  //return null;
}
	
	
	/**
	 * 检验上传资源格式和大小是否合理
	 * @param file  上传的文件
	 * @return  格式和大小符合要求返回正确
	 */
	public  boolean validateResourceTypeAndSize(CommonsMultipartFile  file,int type){
		//校验文件不为空，字节大于0
		if(!file.isEmpty() && file.getSize()>0){
			//获取文件MIME类型，如image/pjpeg、text/plain
			String fileContentType =file.getContentType();
			
			//上传文件原名
			String fileName = file.getOriginalFilename();
			//获取文件字节大小,单位byte
			long filesize = file.getSize();
			
			//文件类型校验结果
			boolean typeresult = false;
			//文件大小校验结果
			boolean sizeresult = false;
			switch(type){
			case ResourceConstant.TYPE_IMAGE: //上传图片类型文件
				typeresult=validateImageFileType(fileName, fileContentType);
				sizeresult = validateFileSize(filesize,ResourceConstant.UPLOAD_SIZE_IMAGE);
				if(!sizeresult)
				    errors.put("error", "上传文件大小不能超过"+WebUtils.convertStorage(ResourceConstant.UPLOAD_SIZE_IMAGE));
				break;
			case ResourceConstant.TYPE_PICTUREBOOK://绘本
				typeresult=validateImageFileType(fileName, fileContentType);
				sizeresult = validateFileSize(filesize,ResourceConstant.UPLOAD_SIZE_PICTUREBOOK);
				if(!sizeresult)
				    errors.put("error", "上传文件大小不能超过"+WebUtils.convertStorage(ResourceConstant.UPLOAD_SIZE_PICTUREBOOK));
				break;
			case ResourceConstant.TYPE_VOICE://声音
				typeresult=validateVoiceFileType(fileName, fileContentType);
				sizeresult = validateFileSize(filesize,ResourceConstant.UPLOAD_SIZE_VOICE);
				if(!sizeresult)
				    errors.put("error", "上传文件大小不能超过"+WebUtils.convertStorage(ResourceConstant.UPLOAD_SIZE_VOICE));
				break;
			case ResourceConstant.TYPE_VIDEO://视频
				//typeresult=validateImageFileType(fileName, fileContentType);
				sizeresult = validateFileSize(filesize,ResourceConstant.UPLOAD_SIZE_VIDEO);
				if(!sizeresult)
				    errors.put("error", "上传文件大小不能超过"+WebUtils.convertStorage(ResourceConstant.UPLOAD_SIZE_VIDEO));
				break;
			
			}
			//文件类型和大小都通过则返回正确
			if( typeresult && sizeresult)
				return true;
			else{
				if( !typeresult){
					errors.put("error", "上传文件格式有误！");
				}
			}
		}else{
			errors.put("error", "请上传文件！");
		}
		return false;
	}
	/**
	 * 上传文件大小和限制大小的比较
	 * @param fileSize 文件大小
	 * @param limitSize 限制的大小
	 * @return  返回true 表示符合要求fileSize<=limitSize
	 */
	public  boolean validateFileSize(long fileSize,long limitSize){
		return fileSize <=limitSize;
	}
	/**
	 * 验证上传文件类型是否属于图片格式
	 * @param fileFileName  文件名
	 * @param fileContentType 文件类型
	 * @return true表示属于图片，false：有误
	 */
	public static boolean validateImageFileType(String fileFileName,String fileContentType){
		
			List<String> arrowType = Arrays.asList("image/bmp","image/png","image/gif","image/jpg","image/jpeg","image/pjpeg");
			List<String> arrowExtension = Arrays.asList("gif","jpg","bmp","png","jpeg");
			String ext = getExt(fileFileName);
			return arrowType.contains(fileContentType.toLowerCase()) && arrowExtension.contains(ext);
		
	}
	
	//audio/wav
	/**
	 * 验证上传文件类型是否属于声音格式
	 * @param fileFileName  文件名
	 * @param fileContentType 文件类型
	 * @return true表示没错或者文件为null，false：有误
	 */
	public static boolean validateVoiceFileType(String fileFileName,String fileContentType){
		
			List<String> arrowType = Arrays.asList("audio/wav","audio/mpeg");
			List<String> arrowExtension = Arrays.asList("wav","mp3");
			String ext = getExt(fileFileName);
			System.out.println(ext+":"+fileContentType);
			return arrowType.contains(fileContentType.toLowerCase()) && arrowExtension.contains(ext);
		
	}
	/**
	 * 得到文件后缀名
	 * @param fileFileName
	 * @return
	 */
	public static String getExt(String fileFileName){
		return fileFileName.substring(fileFileName.lastIndexOf('.')+1).toLowerCase();
	}
	/**
	 * 下载文件/图片
	 * @param response  响应请求
	 * @param fileResource  文件资源
	 * @param isLoad 下载还是查看模式 ,false:查看模式，true:下载模式
	 */
	public static void loadFile(HttpServletResponse response,Resource fileResource,boolean isLoad){
		
		response.setCharacterEncoding("utf-8");
        //response.setContentType("multipart/form-data");
        if( isLoad){
        response.setHeader("Content-Disposition", "attachment;fileName="
                + fileResource.getFilename());
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

	/**
	 * 加载文件
	 * @param response 响应请求
	 * @param fileResource  文件资源
	 */
	public static void loadFile(HttpServletResponse response,Resource fileResource){
		loadFile( response, fileResource,true);
	}
}
