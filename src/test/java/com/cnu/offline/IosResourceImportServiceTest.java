package com.cnu.offline;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.cnu.iqas.dao.iword.WordThemeDao;
import com.cnu.iqas.service.iword.WordResService;
import com.cnu.iqas.utils.FileTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.offline.excel.ExcelUtils;
import com.cnu.offline.excel.service.IosResourceImportService;
import com.cnu.offline.service.OfflineService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.noumenon.OntologyManage.OntologyManage;

/**
* @author 周亮 
* @version 创建时间：2016年10月31日 下午8:11:02
* 类说明
* 测试ios端资源导入接口
*/
public class IosResourceImportServiceTest {

	private static final Logger logger= LogManager.getLogger(IosResourceImportServiceTest.class);
	
	private String resourceDir="I://wordresource//iosdir"; 
	private IosResourceImportService iosResourceImportService;
	@Before
	public void init(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		iosResourceImportService=ac.getBean(IosResourceImportService.class);
		
	}
	
	@Test
	public void testRegx(){
		String picReg= "picture/\\S+\\s*\\S+\\.mp3";
		String pic = "picture/ve ds.mp3";
		String pic1= "picture/ve dsmp3";
		String pic2= "picture/ve ds.m3";
		String pic3 = "picture/v.mp3";
		System.out.println(pic.matches(picReg));//true
		//以下都是false
		System.out.println(pic1.matches(picReg));
		System.out.println(pic2.matches(picReg));
		System.out.println(pic3.matches(picReg));
		
		String reg= "picture/\\S+(\\s*\\S+)*\1*\\.mp3";
		String p = "picture/ve ds ds dd.mp3";

		System.out.println(pic3.matches(reg));
		System.out.println(p.matches(reg));
		System.out.println(p.matches(picReg));
	}
	/**
	 * 导入word.xlsx表,同时导入对应的资源文件
	 * @param sheet word.xlsx表
	 * @return
	 * 1:正常
	 * -1：excel文档有误
	 */
	@Test
	public void testImportWord(){
		//读取相应文件
		String excelname ="word.xlsx";
		try {    
			Sheet sheet = getSheet(excelname);
			iosResourceImportService.importWord(sheet);	
		}catch(DataIntegrityViolationException e1){
			e1.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导入expandword.xlsx表,同时导入对应的资源文件
	 * @param sheet expandword.xlsx表
	 * @param resourceDir  资源文件的根目录，该目录下应该有audio、picture、pronunciation、video
	 * @return
	 *  导入的资源有图片、发音
	 * 1:正常
	 * -1：excel文档有误
	 * -2：导入资源出错
	 */
	@Test
	public void testImportExpandword(){
		//读取相应文件
		String excelname ="expandword.xlsx";
		try {    
			Sheet sheet = getSheet(excelname);
			iosResourceImportService.importExpandword(sheet, resourceDir);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 导入Resource.xlsx表,同时导入对应的资源文件
	 * @param sheet Resource.xlsx表
	 * @param resourceDir  资源文件的根目录，该目录下应该有audio、picture、pronunciation、video
	 * @return
	 * 1:正常
	 * -1：excel文档有误
	 * -2：导入资源出错
	 * 导入的资源有主单词9类资源：图片、发音、视频、情景段落图片、情景段落发音、课文原句图片、课文原句发音、扩展图片、扩展发音、英文图片。
	 */
	@Test
	public void testImportResource(){
		//读取相应文件
		String excelname ="Resource.xlsx";
		try {    
			Sheet sheet = getSheet(excelname);
			iosResourceImportService.importResource(sheet, resourceDir);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 导入videotestquestion.xlsx表，该表中的内容是对Resource.xlsx表中内容的填充，Wresource的视频属性对应的问题和答案在该表中存放着。
	 * Wresource对象存储了每个单词的三个视频，每个视频都有2单问答题，题目就存在该表格中，导入该表格就是填充Wresource对象的问答数据。
	 * @param sheet videotestquestion.xlsx表
	 * @return
	 * 1:正常
	 * -1：excel文档有误。
	 * -2:导入对应的单词视频不存在。
	 */
	@Test
	public void testImportVideotestquestion(){
		//读取相应文件
		String excelname ="videotestquestion.xlsx";
		try {    
			Sheet sheet = getSheet(excelname);
			iosResourceImportService.importVideotestquestion(sheet);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 导入Wordgarde.xlsx表,同时导入对应的资源文件
	 * @param sheet Wordgarde.xlsx表
	 * @param resourceDir  资源文件的根目录，该目录下应该有audio、picture、pronunciation、video
	 * @return
	 */
	@Test
	public void testImportWordGrade(){
		//读取相应文件
		String excelname ="wordgarde.xlsx";
		try {    
			Sheet sheet = getSheet(excelname);
			iosResourceImportService.importWordGrade(sheet, resourceDir);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 根据excel名称获取resourceDir目录下的excel表的第一个Sheet
	 * @param excelName excel名称
	 * @return
	 */
	private  Sheet getSheet(String excelName){
		//读取相应文件excelName
        Workbook wb = null;  
		try {    
			String path = resourceDir+"//"+excelName;
			wb=ExcelUtils.getWorkBook(path);
			// 只取第一页工作表Sheet
			//得到第一页
			Sheet sheet = wb.getSheetAt(0);
			return sheet;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
