package com.cnu.offline.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.offline.exception.ResourceFileException;

/**
* @author 周亮 
* @version 创建时间：2016年11月10日 下午7:03:04
* 类说明
*/
public class ExcelUtils {
	/**
	 * 获取Sheet中一行的中某列的值字符串
	 * @param row  行
	 * @param cellIndex 行中的第几列，从0开始
	 * @return
	 * 返回该列的值，如果该列没有值返回null,如果该列为null同样返回null
	 */
	public static String getCellString(Row row ,int cellIndex){
		Cell cell =row.getCell(cellIndex);
		if( cell!=null )
			return cell.getStringCellValue().trim();
		else
			return null;
	}
	/**
	 * 获取Sheet中一行的中某列值中的数字
	 * @param row  行
	 * @param cellIndex 行中的第几列，从0开始
	 * @return
	 * 返回该列值中的数字，如果该列没有值返回null
	 */
	public static  Integer getCellInteger(Row row ,int cellIndex){
		String value =getCellString(row,cellIndex);
		if( value ==null || value.trim().equals(""))
			return null;
		else
			return Integer.parseInt(value.trim());
	}
	/**
	 * 设置Row的列为String类型
	 * @param row row
	 * @param start  开始列位置，从0开始
	 * @param end	结束列位置
	 */
	public static  void setCellString(Row row,int start,int end){
		for( ;start<=end;start++){
			//设置列为string类型
			Cell cell =row.getCell(start);
			if( cell!=null )
				cell.setCellType(Cell.CELL_TYPE_STRING);
		}
	}
	
	/**
	 * 检验文件路径是否符合规范
	 * @param filePath 文件路径
	 * @return
	 * 如果不符合"audio/*.mp3"、"picture/*.*"、"pronunciation/*.mp3"、"video/*.mp4",4种格式则返回false，*代表任何字符。
	 * 符合返回true
	 */
	public static boolean validateIdetifyPath(String filePath){
		if(filePath!=null&& !filePath.trim().equals("无")&& !filePath.trim().equals("")){
			/*String audioReg= "audio/\\S+\\s*\\S+\\.mp3";
			String picReg= "picture/\\S+\\s*\\S+\\.\\S+";
			String proReg= "pronunciation/\\S+\\s*\\S+\\.mp3";
			String videoReg= "video/\\S+\\s*\\S+\\.mp4";*/
			String audioReg= "audio/\\S+(\\s*\\S+)*\1*\\.mp3";
			String picReg= "picture/\\S+(\\s*\\S+)*\1*\\.\\S+";
			String proReg= "pronunciation/\\S+(\\s*\\S+)*\1*\\.mp3";
			String videoReg= "video/\\S+(\\s*\\S+)*\1*\\.mp4";
			String[] paths = filePath.split("&");
			if( paths !=null ){
				for( String path:paths){
					path = path.trim();
					if( path.matches(audioReg) || path.matches(picReg)|| path.matches(proReg)|| path.matches(videoReg))
						continue;
					else
						return false;
				}
			}
		}
		return true;
	}
	/**
	 * 
	 * @param resourceDir  资源文件根目录
	 * @param orginFilePathNames  源文件路径，该路径在excel中读取到的，是相对于resourceDir所指目录的相对路径
	 * @param savePathKey	              目标文件存放目录对应的key值，该值在savepath.properties文件中配置
	 * @return 
	 * 新文件在数据库中的存储路径 ，保存的相对路径+文件名称。
	 * 如：orginFilePathNames=audio/xxxx.mp3,则返回ifilesystem/ios/wordres/audio/xxxx.mp3
	 * 
	 * 如果orginFilePathNames中包含多条路径会以字符‘&’分隔，同时返回的的在数据库中的存储路径也会也字符‘&’分隔。
	 * 如：orginFilePathNames=pronunciation/juice_phase1.mp3&pronunciation/juice_phase2.mp3
	 * 返回：ifilesystem/ios/wordres/pronunciation/juice_phase1.mp3&ifilesystem/ios/wordres/pronunciation/juice_phase2.mp3
	 * @throws ResourceFileException
	 */
	public static String copyFile(String resourceDir, String orginFilePathNames, String savePathKey)
			throws  ResourceFileException {
		String savePath="";
		if( orginFilePathNames!=null &&!orginFilePathNames.trim().equals("")&&!orginFilePathNames.trim().equals("无")){
			String[] ofpns=orginFilePathNames.split("&");
			for(String orginFilePathName: ofpns){
				String targetDir =PropertyUtils.getFileSaveAbsolutePath(savePathKey);
					
					try {
						//源文件路径
						//源文件名称
						String orginName = orginFilePathName.substring(orginFilePathName.lastIndexOf("/")+1);
						//源文件
						File orginFile = new File(resourceDir+"/"+orginFilePathName);
						//输出文件
						File outDir = new File(targetDir);
						if(!outDir.exists())
							outDir.mkdirs();
						File outFile = new File(outDir,orginName);
						//传输
						BufferedInputStream bis = new BufferedInputStream(new FileInputStream(orginFile));
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
						byte[] buff = new byte[1024*10];
						int len =0;
						while( (len = bis.read(buff))!=-1){
							bos.write(buff,0,len);
						}
						bis.close();
						bos.close();
						savePath += PropertyUtils.getFileSaveDir(savePathKey)+"/"+orginName+"&";
					} catch (Exception e) {
						//loger.error("导入文件["+orginFilePathName+"]时出错。"+e.getMessage());
						throw new ResourceFileException("导入文件["+orginFilePathName+"]时出错。"+e.getMessage(),e);
					}
				
			}
		}
		//去掉savePath最后的&
		if( !savePath.trim().equals(""))
		{
			savePath=savePath.substring(0, savePath.lastIndexOf("&"));
			return savePath;
		}else
			return null;
	}
	/**
	 * 根据路径返回excel对象
	 * @param path excel文件对应路径
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Workbook getWorkBook(String path) throws FileNotFoundException{
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

	/**
	 * 根据路径返回excel对象
	 * @param file excel文件
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Workbook getWorkBook(CommonsMultipartFile file) throws FileNotFoundException{
		
        boolean isExcel2003 = false;  
        if (WebUtils.isExcel2003(file.getName()))  
        {  
            isExcel2003 = true;  
        }  
        Workbook wb = null;  
		try {    
			InputStream is = file.getInputStream();
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
}
