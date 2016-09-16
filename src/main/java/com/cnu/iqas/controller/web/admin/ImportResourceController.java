package com.cnu.iqas.controller.web.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jetty.util.UrlEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.constant.ResourceConstant;
import com.cnu.iqas.exception.word.ResourceTypeNotExisting;
import com.cnu.iqas.exception.word.SaveDirNoExsitingException;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.service.iword.ResourceCustomService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;


/**
* @author 周亮 
* @version 创建时间：2016年5月10日 上午9:44:46
* 类说明
*/
@Controller
@RequestMapping(value="/admin/control/import/")
public class ImportResourceController implements ServletContextAware, ResourceLoaderAware {
	//资源加载器
	private ResourceLoader resourceLoader;
	//日志类
	private final static Logger logger = LogManager.getLogger(ImportResourceController.class);
	 //单词资源服务类
	 private WordResourceService wordResourceService;
	 //应用对象
	 private ServletContext servletContext;
	 //单词服务类
	 private IwordService iwordService;
	private ResourceCustomService resourceCustomService;
	/**
	 * 该方法为完成读取Excel中的数据并将数据插入到对应的数据库表中的操作
	 * @param file execl文件
	 * @return
	 */
	@RequestMapping(value="resource")
	public ModelAndView importword( @RequestParam("resource") CommonsMultipartFile  file){
		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		
		if( !file.isEmpty()){
				 /** 判断文件的类型，是2003还是2007 */  
				  
	            boolean isExcel2003 = false;  
	            if (WebUtils.isExcel2003(file.getName()))  
	            {  
	                isExcel2003 = true;  
	            }  
				try {
					 /** 根据版本选择创建Workbook的方式 */  
					  
		            Workbook wb = null;  
		            if (isExcel2003)  
		            {  
		                wb = new HSSFWorkbook(file.getInputStream());  
		            }else{  
		                wb = new XSSFWorkbook(file.getInputStream());  
		            }  
						//hssfWorkbook = new HSSFWorkbook(file.getInputStream());
						// 只取第一页工作表Sheet
						//得到第一页
						Sheet sheet = wb.getSheetAt(0);
						if (sheet != null) {
							//批量保存，每次保存数据放在iwords中
							List<Iword> iwords = new ArrayList<Iword>();
							//获得当前页的行数
							// 循环行Row
							int allrows =sheet.getLastRowNum();
							Map<String,List<String>> nofindFile = new HashMap<String,List<String>>();
							//List<String> nofindFile = new ArrayList<String>();
							int rowNum = 1;
							for (rowNum = 1; rowNum <= allrows; rowNum++) {
								
								//一行数据
								Row row = sheet.getRow(rowNum);
								if (row != null) {
									//设置第2,3,4列为string类型
									if(row.getCell(2) != null)
										row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
									if(row.getCell(3) != null)
										row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
									if(row.getCell(4) != null)
										row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
									//读出第2,3,4列数据
									String word=row.getCell(2).getStringCellValue().trim();
									String pronunciation=row.getCell(3).getStringCellValue().trim();
									String picture=row.getCell(4).getStringCellValue().trim();
									
									if( word!=null && !word.trim().equals("")){

										System.out.println("----第"+(rowNum-1)+"行："+word);
										//保存发音资源
										saveWordResourced(word, pronunciation, 3, nofindFile);
										
										String pictureBook = null;
										if( picture.contains("pictureBook"))
										{
											pictureBook= picture;
											//保存绘本资源
											saveWordResourced(word, pictureBook, 2, nofindFile);
										}else{
											//保存图片资源
											saveWordResourced(word, picture, 1, nofindFile);
										}
									}else{
										break;
									}
								}
							}

							mv.addObject("message", "录入成功,共录入"+rowNum+"条数据!");
							System.out.println("共"+rowNum+"行，不存在的资源有：");
							for(String key :nofindFile.keySet()){
								List<String> value = nofindFile.get(key);
								System.out.println(key+value.size()+"条:");
								for(String str : value)
									System.out.println(str);
							}
							
						}else{  //第一页为空
							logger.error("导入单词：没有内容");
							mv.addObject("message", "没有内容");
						}
					
				} catch (Exception e) {
					logger.error("导入单词：未知异常");
					mv.addObject("message", "未知异常!");
					e.printStackTrace();
				}
		}else{
			mv.addObject("message", "文件不存在!");
		}
		mv.addObject("urladdress",PageViewConstant.generatorMessageLink("admin/control/word/importUI"));
		return mv;
	}
	
	/**
	 * 该方法为完成读取Excel中的数据并将数据插入到对应的数据库表中的操作,
	 * 读取得excel中的一条记录中内容为:单词，单词发音，单词课文原句，课文原句发音，情景段落，情景段落发音，视频
	 * @param file execl文件
	 * @return
	 */
	@RequestMapping(value="importWordRes")
	public ModelAndView importWordRes( @RequestParam("resource") CommonsMultipartFile  file){
		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		
		if( !file.isEmpty()){
				 /** 判断文件的类型，是2003还是2007 */  
				  
	            boolean isExcel2003 = false;  
	            if (WebUtils.isExcel2003(file.getName()))  
	            {  
	                isExcel2003 = true;  
	            }  
	            Workbook wb = null;  
				try {    
					 /** 根据版本选择创建Workbook的方式 */  
					  
		            if (isExcel2003)  
		            {  
		                wb = new HSSFWorkbook(file.getInputStream());  
		            }else{  
		                wb = new XSSFWorkbook(file.getInputStream());  
		            }  
						
						// 只取第一页工作表Sheet
						//得到第一页
						Sheet sheet = wb.getSheetAt(0);
						if (sheet != null) {
							//获得当前页的行数
							// 循环行Row
							int allrows =sheet.getLastRowNum();
							Map<String,List<String>> nofindFile = new HashMap<String,List<String>>();
							int rowNum = 1;
							for (rowNum = 1; rowNum <= allrows; rowNum++) {
								//一行数据
								Row row = sheet.getRow(rowNum);
								if (row != null) {
									//设置第1,2,3,4,5列为string类型
									if(row.getCell(1) != null)
										row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
									if(row.getCell(2) != null)
										row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
									if(row.getCell(3) != null)
										row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
									if(row.getCell(4) != null)
										row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
									if(row.getCell(5) != null)
										row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
									//读出第1，2,3,4，5列数据
									String word=row.getCell(1).getStringCellValue().trim();
									String yj=row.getCell(2).getStringCellValue().trim();
									String yjpr=row.getCell(3).getStringCellValue().trim();
									String qj=row.getCell(4).getStringCellValue().trim();
									String qjpr=row.getCell(5).getStringCellValue().trim();
									
									if( word!=null && !word.trim().equals("")){

										System.out.println("----第"+(rowNum-1)+"行："+word);
										//保存发音资源
										//saveWordResourced(word, pronunciation, 3, nofindFile);
										
									}else{
										break;
									}
								}
							}

							mv.addObject("message", "录入成功,共录入"+rowNum+"条数据!");
							System.out.println("共"+rowNum+"行，不存在的资源有：");
							for(String key :nofindFile.keySet()){
								List<String> value = nofindFile.get(key);
								System.out.println(key+value.size()+"条:");
								for(String str : value)
									System.out.println(str);
							}
							
						}else{  //第一页为空
							logger.error("导入单词：没有内容");
							mv.addObject("message", "没有内容");
						}
					
				} catch (Exception e) {
					logger.error("导入单词：未知异常");
					mv.addObject("message", "未知异常!");
					e.printStackTrace();
				}finally {
						try {
							if(wb!=null)
							wb.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
		}else{
			mv.addObject("message", "文件不存在!");
		}
		mv.addObject("urladdress",PageViewConstant.generatorMessageLink("admin/control/word/importUI"));
		return mv;
	}
	
	@RequestMapping(value="video")
	public ModelAndView importVideo( @RequestParam("video") CommonsMultipartFile  file){
		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		
		if( !file.isEmpty()){
				 /** 判断文件的类型，是2003还是2007 */  
				  
	            boolean isExcel2003 = false;  
	            if (WebUtils.isExcel2003(file.getName()))  
	            {  
	                isExcel2003 = true;  
	            }  
				try {
					 /** 根据版本选择创建Workbook的方式 */  
					  
		            Workbook wb = null;  
		            if (isExcel2003)  
		            {  
		                wb = new HSSFWorkbook(file.getInputStream());  
		            }else{  
		                wb = new XSSFWorkbook(file.getInputStream());  
		            }  
						//hssfWorkbook = new HSSFWorkbook(file.getInputStream());
						// 只取第一页工作表Sheet
						//得到第一页
						Sheet sheet = wb.getSheetAt(0);
						if (sheet != null) {
							//批量保存，每次保存数据放在iwords中
							List<Iword> iwords = new ArrayList<Iword>();
							//获得当前页的行数
							// 循环行Row
							int allrows =sheet.getLastRowNum();
							List<String> nofindFile = new ArrayList<String>();
							//List<String> nofindFile = new ArrayList<String>();
							int rowNum = 1;
							for (rowNum = 1; rowNum <= allrows; rowNum++) {
								
								//一行数据
								Row row = sheet.getRow(rowNum);
								if (row != null) {
									//设置第1,2,列为string类型
									if(row.getCell(2) != null)
										row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
									if(row.getCell(1) != null)
										row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
									//读出第2,3,4列数据
									String video=row.getCell(1).getStringCellValue().trim();
									System.out.println("第："+rowNum+" 行："+video+"");
									String words=row.getCell(2).getStringCellValue().trim();
									
									 
									if( video!=null && !video.trim().equals("")){
										 if( video.indexOf("#")>0){
											  video= video.replace("#", UrlEncoded.encodeString("#"));
										  }
										//Resource res = getFileResource(localPath);
										Resource res = getVideoResourc(video,nofindFile);
										if( res!=null){
										//保存视频
										 String filesavepath=saveWordResourceFile(servletContext, res, 4);
										 saveWordVideo(words,filesavepath);
										}
									}else{
										break;
									}
								}
							}

							mv.addObject("message", "录入成功,共录入"+rowNum+"条数据!");
							System.out.println("共"+rowNum+"行，不存在的资源有：");
							for(String str : nofindFile)
								System.out.println(str);
							
							
						}else{  //第一页为空
							logger.error("导入单词：没有内容");
							mv.addObject("message", "没有内容");
						}
					
				} catch (Exception e) {
					logger.error("导入单词：未知异常");
					mv.addObject("message", "未知异常!");
					e.printStackTrace();
				}
		}else{
			mv.addObject("message", "文件不存在!");
		}
		mv.addObject("urladdress",PageViewConstant.generatorMessageLink("admin/control/word/importUI"));
		return mv;
	}
	/**
	 * 获得视频资源或者歌曲资源
	 * @param video
	 * @param nofindFile
	 * @return
	 * @throws IOException
	 */
	private Resource getVideoResourc(String video,List<String> nofindFile) throws IOException {
		// TODO Auto-generated method stub
		String root= "file:G:/wordResource/root/";
		String localPath = root+"video/";
		if( video.lastIndexOf(".avi")>0)
			 localPath = video;
		else
			 localPath = video+".avi";
		Resource res = getFileResource(localPath);
		//在video目录下,avi后缀
		if( res!=null &&res.getFile().exists())
			return res;
		//在video目录下,swf后缀
		localPath = root+"video/"+video+".swf";
		res = getFileResource(localPath);
		if( res!=null &&res.getFile().exists())
			return res;
		//在video目录下,mp4后缀
		localPath = root+"video/"+video+".mp4";
		res = getFileResource(localPath);
		if( res!=null &&res.getFile().exists())
			return res;
		//在video目录下,f4v后缀
		localPath = root+"video/"+video+".f4v";
		res = getFileResource(localPath);
		if( res!=null &&res.getFile().exists())
			return res;
		//在song目录下,mp3后缀
		localPath = root+"song/"+video+".mp3";
		res = getFileResource(localPath);
		if( res!=null &&res.getFile().exists())
			return res;
		
		//在vido和song文件夹中未找到
		nofindFile.add(video);
		return null;
	}


	/**
	 * 保存视频
	 * @param word
	 * @param savePath
	 * @param resType
	 * @param nofindFile
	 * @throws IOException
	 * @throws Exception
	 */
	public void saveWordVideo(String words,String savePath){
		 if( words!=null && !words.trim().equals("")){
			 String[] wds = words.split("&");
			 for( String wd : wds){
				 String id=null;
				 //kind(2/7/10/2)
				 String wordRex = "\\D+(/\\d{1}/\\d+/\\d+/\\d+)";
				 if( wd.matches(wordRex)){
					 id= wd.substring( wd.lastIndexOf("(")+1,wd.length()-1);
					 wd= wd.substring(0,wd.lastIndexOf("("));
				 }
				  saveWordResource(savePath, wd, id, 4);
			 }
		 }
			
	}
	/**
	 * 保存图片；绘本；发音
	 * @param word
	 * @param savePath
	 * @param resType
	 * @param nofindFile
	 * @throws IOException
	 * @throws Exception
	 */
	public void saveWordResourced(String word,String savePath,int resType,Map<String,List<String>> nofindFile) throws IOException, Exception{
		 if( word!=null && !word.trim().equals("")){
			 String id=null;
			 //kind(2/7/10/2)
			 String wordRex = "\\D+(/\\d{1}/\\d+/\\d+/\\d+)";
			 if( word.matches(wordRex)){
				 id= word.substring( word.lastIndexOf("(")+1,word.length()-1);
				 word= word.substring(0,word.lastIndexOf("("));
			 }
			
			 
			 String[] savePaths=savePath.split("&");
			 //保存绘本，获得保存路径
			 String root= "file:G:/wordResource/root/";
			 for( String sp :savePaths){
				 if( sp.indexOf("#")>0){
					 sp= sp.replace("#", UrlEncoded.encodeString("#"));
				  }
				 String localPath = root+sp;
				 Resource res = getFileResource(localPath);
				 if( res!=null && res.getFile().exists()){
				     String bookfilesavepath=null;
				     String originName=getOriginNmae(sp);
		   
				     bookfilesavepath=saveWordResourceFile(servletContext, res, resType);
				     saveWordResource(bookfilesavepath, word, id, resType);
				 }else{
					 switch(resType){
					 case 1:
						 List<String> plist= nofindFile.get("picture");
						 if( plist==null)
							 plist = new ArrayList<String>();
					     plist.add(sp);
						 nofindFile.put("picture", plist);
						 break;
					 case 2:
						 List<String> pilist= nofindFile.get("pictureBook");
						 if( pilist==null)
							 pilist = new ArrayList<String>();
						 pilist.add(sp);
						 nofindFile.put("pictureBook", pilist);
						 break;
					 case 3: 
						 List<String> prlist= nofindFile.get("pronunciation");
					     if( prlist==null)
					    	 prlist = new ArrayList<String>();
					     prlist.add(sp);
						 nofindFile.put("pronunciation", prlist);
						 break;
					 case 4:
						 List<String> vlist= nofindFile.get("video");
						 if( vlist==null)
							 vlist = new ArrayList<String>();
						 vlist.add(sp);
						 nofindFile.put("video", vlist);
						 break;
					 }
					 
				 }
			 }
		 }
	}
	/**
	 * 从路径中获得文件名
	 * @param relPath  picture/boat.jpg
	 * @return boat.jpg
	 */
	private String getOriginNmae(String relPath){
		
		return relPath.substring(relPath.lastIndexOf("/")+1);
	}
	/**
	 * 保存单词资源实体
	 * @param filesavepath
	 * @param originName
	 * @param wordId
	 * @param resourceType
	 */
	public void saveWordResource(String filesavepath,String originName,String wordId,int resourceType){	
		if( filesavepath!=null){
		//4.建立资源类保存信息
		 WordResource resource = new WordResource();
		 //5.设置资源所属的单词的id
	     resource.setWordId(wordId);
		 resource.setName(originName);//单词原名称
		 resource.setSavepath(filesavepath);//包含文件名的相对保存路径
		 resource.setType(resourceType);//资源类型
		 //保存到数据库
		 wordResourceService.save(resource);
		}
	}
	
	
	public String saveWordResourceFile(ServletContext servletContext, Resource resource, int filetype) throws Exception {
		
		//获取单词资源的文件保存的相对路径
		   String relativepath=null;
		   switch(filetype){
			    case ResourceConstant.TYPE_IMAGE: //上传图片类型文件
			    	relativepath = PropertyUtils.getFileSaveDir(PropertyUtils.WORD_IMAGE_DIR);  //获取本地存储路径
			    	break;
			    case ResourceConstant.TYPE_VOICE://声音
			    	relativepath = PropertyUtils.getFileSaveDir(PropertyUtils.WORD_VOICE_DIR);  //获取本地存储路径
			    	break;
			    case ResourceConstant.TYPE_VIDEO://视频
			    	relativepath = PropertyUtils.getFileSaveDir(PropertyUtils.WORD_VIDEO_DIR);  //获取本地存储路径
			    	break;
			    case ResourceConstant.TYPE_PICTUREBOOK://绘本
			    	relativepath = PropertyUtils.getFileSaveDir(PropertyUtils.WORD_PICTUREBOOK_DIR);  //获取本地存储路径
			    	break;
			    default:
			    	throw new ResourceTypeNotExisting("单词资源文件类型未确定!");
		   }
		   if(relativepath==null){
			   throw new SaveDirNoExsitingException(filetype+"类型单词资源保存的相对路径不存在!,请在savepath.properties中设置。");
		   }
		//文件原名称
		   String fileName = resource.getFile().getName();
		   //文件后缀名
		   String fileExt = getExt(fileName);
		   //生成随机的文件名
		   String savefilename = UUID.randomUUID().toString()+ "."+fileExt;
		  
		   //获取保存的绝对路径
		   String absolutepath=null;
		   if( relativepath != null && !relativepath.equals("")){
			   //项目实际根目录
			   String fileSystemDir=PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR);
			   
			   absolutepath=fileSystemDir+relativepath;
			   //生成保存路径文件
			   File filedir = new File(absolutepath);
			   //不存在生成
			   if(!filedir.exists()){
				   filedir.mkdirs();
			   }
			   File savefile = new File(filedir,savefilename); //新建保存的文件 
			   try {//将上传的文件写入  新建的文件中
				    BufferedInputStream bis= new BufferedInputStream(resource.getInputStream());
				    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(savefile));
				    byte[] buffer = new byte[1024];
				    int len =0;
				    while( (len =bis.read(buffer))!=-1){
				    	bos.write(buffer);
				    }
				    bis.close();
				    bos.close();
				  //相对路径   relativedir+"/"+filesavename
					 return relativepath+"/"+savefilename;
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
	private String getExt(String fileFileName){
		return fileFileName.substring(fileFileName.lastIndexOf('.')+1).toLowerCase();
	}
	
	 public WordResourceService getWordResourceService() {
			return wordResourceService;
		}
	 @Autowired
		public void setWordResourceService(WordResourceService wordResourceService) {
			this.wordResourceService = wordResourceService;
		}

		@Override
		public void setServletContext(ServletContext servletContext) {
			// TODO Auto-generated method stub
			this.servletContext = servletContext;
		}

		public IwordService getIwordService() {
			return iwordService;
		}
		@Autowired
		public void setIwordService(IwordService iwordService) {
			this.iwordService = iwordService;
		}

	public ResourceCustomService getResourceCustomService() {
		return resourceCustomService;
	}
	@Autowired
	public void setResourceCustomService(ResourceCustomService resourceCustomService) {
		this.resourceCustomService = resourceCustomService;
	}
	
		
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
