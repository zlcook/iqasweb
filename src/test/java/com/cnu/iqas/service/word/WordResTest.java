package com.cnu.iqas.service.word;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cnu.iqas.bean.iword.WordRes;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.service.iword.WordResService;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.FileTool;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.offline.utils.ImageSizer;

/**
* @author 周亮 
* @version 创建时间：2016年8月22日 上午9:48:09
* 类说明
*/
public class WordResTest {
	/**
	 * 图片压缩宽度
	 */
	private final int resizeWidth =300;
	private WordResService wordResService;
	
	public WordResService getWordResService() {
		return wordResService;
	}
	public void setWordResService(WordResService wordResService) {
		this.wordResService = wordResService;
	}
	
	@Before
	public void init(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		wordResService=ac.getBean(WordResService.class);
	}
	
	
	@Test
	public void getWordRes(){
		
		WordRes res =wordResService.find("boat");
		System.out.println(res);
	}
	
	/**
	 * 读取grade年级课文原句和情景段落
	 * @throws IOException
	 */
	@Test
	public void importkqVoice() throws IOException{
		  
		int grade =5;//年级
		//读取相应文件
		String path ="I://"+grade+"jzfy.xlsx";
        Workbook wb = null;  
        int allrows = 0;//总行数
        int rowNum=1;//当前行
		try {    
			wb=getWorkBook(path);
				// 只取第一页工作表Sheet
				//得到第一页
				Sheet sheet = wb.getSheetAt(0);
				if (sheet != null) {
					//获得当前页的行数
					// 循环行Row
					 allrows =sheet.getLastRowNum();
					 rowNum = 1;
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
						    Cell cell=row.getCell(1);
						    if( cell==null)
						    	break;
							String word=row.getCell(1).getStringCellValue().trim();
					
							Cell yjcell = row.getCell(3);
							Cell qjcell = row.getCell(5);
							
							//读取文件
							if( word!=null && !word.trim().equals("")){
								WordRes res = wordResService.find(word);
								boolean isnew = false;
								if( res==null){
									isnew = true;
									res = new WordRes();
									res.setWord(word);
								}
								
								String yjprrelPath=null;
								String qjprrelPath=null;
								String abPath = PropertyUtils.WORD_VOICE_DIR;
								//保存课文原句
								if( yjcell!=null && !yjcell.getStringCellValue().trim().equals("")){
									try {
										String yjpr=yjcell.getStringCellValue().trim();
										//如果有多个课文原句则以&分隔，则分隔多个课文原句并保存
										String[] yjprs =yjpr.split("&");
										for( String yj :yjprs){
											//保存课文原句
											yjprrelPath = saveRes(yj,abPath);
											String relPath=PropertyUtils.getFileSaveDir(PropertyUtils.WORD_VOICE_DIR)+"/"+yjprrelPath;
											//调用相应方法保存在对应年级字段中
											Method get = WordRes.class.getMethod("getKwyjVoicePath"+grade);
											String exist=(String) get.invoke(res);
											//拼接到之前的值后面，以分号（&）隔开
											if( exist!=null &&!exist.trim().equals(""))
												relPath=exist+"&"+relPath;
											Method set = WordRes.class.getMethod("setKwyjVoicePath"+grade,String.class);
											set.invoke(res, relPath);
										}
										//保存课文语句文本
										String kwyj =row.getCell(2).getStringCellValue().trim();
										Method set = WordRes.class.getMethod("setKwyj"+grade,String.class);
										set.invoke(res, kwyj);
										
									} catch (Exception e) {
										e.printStackTrace();
										System.out.println("----第"+(rowNum+1)+"行："+word+"课文原句出错....."+e.getMessage());
									}
								}
								if( qjcell!=null &&!qjcell.getStringCellValue().trim().equals("") ){
									try {
										String qjpr=qjcell.getStringCellValue().trim();
										String[] qjprs =qjpr.split("&");
										for( String qj :qjprs){
											qjprrelPath = saveRes(qj,abPath);
											String relPath=PropertyUtils.getFileSaveDir(PropertyUtils.WORD_VOICE_DIR)+"/"+qjprrelPath;
										    
											//调用相应方法保存在对应年级字段中
											Method get = WordRes.class.getMethod("getQjdlVoicePath"+grade);
											String exist=(String) get.invoke(res);
											//拼接到之前的值后面，以分号（&）隔开
											if( exist!=null &&!exist.trim().equals(""))
												relPath=exist+"&"+relPath;
											Method set = WordRes.class.getMethod("setQjdlVoicePath"+grade,String.class);
											set.invoke(res, relPath);
										}
										//保存情景段落文本
										String qjdl =row.getCell(4).getStringCellValue().trim();
										Method set = WordRes.class.getMethod("setQjdl"+grade,String.class);
										set.invoke(res, qjdl);
									} catch (Exception e) {
										
										System.out.println("----第"+(rowNum+1)+"行："+word+"情景段落出错....."+e.getMessage());
									}
								}
								if( isnew)
									wordResService.save(res);
								else
									wordResService.update(res);
							}else
								break;
							
						}
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if( wb!=null)
				wb.close();
			System.out.println("总行数(总行数不一定是有数据的行数)："+allrows+"　　当前行："+rowNum);
		}
	}
	
	/**
	 * 读取发音和图片
	 * @throws IOException
	 */
	@Test
	public void importVoiceAndPicture() throws IOException{
		  
		String path ="I://tfs.xlsx";
        Workbook wb = null;  
		try {    
			wb=getWorkBook(path);
				// 只取第一页工作表Sheet
				//得到第一页
				Sheet sheet = wb.getSheetAt(0);
				if (sheet != null) {
					//获得当前页的行数
					// 循环行Row
					int allrows =sheet.getLastRowNum();
					int rowNum = 1;
					for (rowNum = 1; rowNum <= allrows; rowNum++) {
						//一行数据
						Row row = sheet.getRow(rowNum);
						if (row != null) {
							//设置第1,2,4,5列为string类型
							if(row.getCell(1) != null)
								row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							if(row.getCell(2) != null)
								row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							if(row.getCell(4) != null)
								row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							if(row.getCell(5) != null)
								row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
							//读出第1，2,4，5列数据
							String word=row.getCell(1).getStringCellValue().trim();
							String photoUrl=row.getCell(2).getStringCellValue().trim();
							String soundUrl=row.getCell(4).getStringCellValue().trim();
								//读取文件
								if( photoUrl!=null && !photoUrl.equals("")){
									WordRes res = wordResService.find(word);
									boolean isnew = false;
									if( res==null){
										isnew = true;
										res = new WordRes();
										res.setWord(word);
									}
									//图片资源保存路径
									String pabPath = PropertyUtils.WORD_IMAGE_DIR;
									//发音资源保存路径
									String vabPath = PropertyUtils.WORD_VOICE_DIR;
									try {
										//图片资源保存
										String phoRePath=saveRes(photoUrl,pabPath,true);
										String relPath=PropertyUtils.getFileSaveDir(PropertyUtils.WORD_IMAGE_DIR)+"/"+phoRePath;
										res.setPicPath(relPath);
									} catch (Exception e) {
										System.out.println("----第"+(rowNum+1)+"行："+word+" 图片出错"+e.getMessage());
									}
									try {
										//发音资源保存
										String voicPath=saveRes(soundUrl,vabPath);
										String relPath=PropertyUtils.getFileSaveDir(PropertyUtils.WORD_VOICE_DIR)+"/"+voicPath;
										res.setVoicePath(relPath);
									} catch (Exception e) {
										System.out.println("----第"+(rowNum+1)+"行："+word+" 发音出错"+e.getMessage());
									}
									if( isnew)
										wordResService.save(res);
									else
										wordResService.update(res);
								}
							}else
								break;
						}
				}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if( wb!=null)
				wb.close();
		}
	}
	/**
	 * 读取视频
	 * @throws IOException  
	 */
	@Test
	public void importVideo() throws IOException{
		
		String path ="I://tfs.xlsx";
        Workbook wb = null;
		try {
			wb=getWorkBook(path);
				
				//只取第一页工作表Sheet
				//得到第一页
				Sheet sheet = wb.getSheetAt(0);
				if (sheet != null) {
					//获得当前页的行数
					// 循环行Row
					int allrows =sheet.getLastRowNum();
					int rowNum = 1;
					for (rowNum = 1; rowNum <= allrows; rowNum++) {
						//一行数据
						Row row = sheet.getRow(rowNum);
						if (row != null) {
							//设置第1,2,4,5列为string类型
							if(row.getCell(1) != null)
								row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							if(row.getCell(2) != null)
								row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							if(row.getCell(4) != null)
								row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							if(row.getCell(5) != null)
								row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
							//读出第1，2,4，5列数据
							String word=row.getCell(1).getStringCellValue().trim();
							String photoUrl=row.getCell(2).getStringCellValue().trim();
							String soundUrl=row.getCell(4).getStringCellValue().trim();
							Cell cell =row.getCell(5);
							
							
							//读取文件
							if( cell!=null && word!=null){
								WordRes res = wordResService.find(word);
								boolean isnew = false;
								if( res==null){
									isnew = true;
									res = new WordRes();
									res.setWord(word);
								}
								
								String videoUrl=cell.getStringCellValue().trim();
								String[] vss = videoUrl.split("&");
								//视频资源保存路径
								String abPath =PropertyUtils.WORD_VIDEO_DIR;
								for(int i =0;i < vss.length;i++){
									String vs = vss[i];
									String vsPath=null;
									try {
										vsPath = saveRes(vs,abPath);
										String relPath=PropertyUtils.getFileSaveDir(PropertyUtils.WORD_VIDEO_DIR)+"/"+vsPath;
										if( i==0)
											res.setVideoPath1(relPath);
										if( i==1)
											res.setVideoPath2(relPath);
										 if( i==2)
											 res.setVideoPath3(relPath);
									} catch (Exception e) {
										System.out.println("----第"+(rowNum+1)+"行："+word+" 第"+(i+1)+"个资源出错"+e.getMessage());
									}
								}
								if( isnew)
									wordResService.save(res);
								else
									wordResService.update(res);
							}else
								break;
							
						}
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if( wb!=null)
				wb.close();
		}
	}
	/**
	 *  保存资源
	 * @param relapath 相对路径，在execl中获取
	 * @param savekey： savepath.properties文件中保存的key值，会调用 PropertyUtils.getFileSaveAbsolutePath(savekey)方法获取key对应的值
	 * @return，相对savekey对应值的路径,返回值例如：2016/08/22/17/2212334234.mp3
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String saveRes(String relapath,String savekey) throws FileNotFoundException, IOException {
		//输出文件根目录
		String outdir =  PropertyUtils.getFileSaveAbsolutePath(savekey); 
		//输入文件根目录
		String indir ="I://resdir";
		File infile = new File(indir+"/"+relapath);
		//输出文件读取流
		FileInputStream fis =new FileInputStream(infile);
		
		//输入文件相对路径
		String relPath =FileTool.saveFile(outdir, fis, FileTool.getExtFromFileName(relapath));
		return relPath;
	}
	private String saveRes(String relapath,String savekey,boolean isSizer) throws FileNotFoundException, IOException {
		//输出文件根目录
		String outdir =  PropertyUtils.getFileSaveAbsolutePath(savekey); 
		//输入文件根目录
		String indir ="I://resdir";
		File infile = new File(indir+"/"+relapath);
		//输出文件读取流
		FileInputStream fis =new FileInputStream(infile);
		String ext =FileTool.getExtFromFileName(relapath);
		//生成输出文件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//文件名
		String fileName= System.currentTimeMillis()+"."+ext;
		String relPath = sdf.format(new Date());
		String moutdir=outdir+"/"+relPath;
		File outdirfile = new File(moutdir);
		if(!outdirfile.exists())
			outdirfile.mkdirs();
		File file = new File(outdirfile, fileName);
		//传输
		FileOutputStream fos = new FileOutputStream(file);
		FileTool.write(fos,fis);
		String sizerPath ="";
		//压缩
		if( isSizer ){
			//压缩文件夹在源文件夹中多一个压缩文件夹，文件夹名称已压缩比例命名
			
			//压缩目录
			File resizeDir = new File(outdirfile,resizeWidth+"");
			if(!resizeDir.exists())
				resizeDir.mkdirs();
			File resizedFile = new File(resizeDir, fileName);
			//压缩
			ImageSizer.resize(file, resizedFile, resizeWidth, ext);
			sizerPath+=resizeWidth;
		}
		//输入文件相对路径
		return relPath+"/"+sizerPath+"/"+fileName;
	}
	
	public Workbook getWorkBook(String path) throws FileNotFoundException{
		File file=new File(path);
		InputStream is = new FileInputStream(file);
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
                wb = new HSSFWorkbook(is);  
            }else{  
                wb = new XSSFWorkbook(is);  
            }  
		}catch(Exception e){
			e.printStackTrace();
		}
		return wb;
	}
	@Test
	public void getPro(){
		System.out.println(PropertyUtils.getFileSaveAbsolutePath(PropertyUtils.WORD_VOICE_DIR));
	}
}
