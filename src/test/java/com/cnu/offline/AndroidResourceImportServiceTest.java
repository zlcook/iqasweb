package com.cnu.offline;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import com.cnu.offline.excel.ExcelUtils;
import com.cnu.offline.excel.service.AndroidResourceImportService;

/**
* @author 周亮 
* @version 创建时间：2016年8月22日 上午9:48:09
* 类说明
* 测试android端资源导入接口
*/
public class AndroidResourceImportServiceTest {
	
	private final static Logger log = LogManager.getLogger(AndroidResourceImportServiceTest.class);
	private String resourceDir="I://wordresource//androiddir"; 
	private AndroidResourceImportService androidResouceImportService;
	
	@Before
	public void init(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		androidResouceImportService = ac.getBean(AndroidResourceImportService.class);
	}
	
	@Test
	public void testimportKwyjqjdl(){
		//读取相应文件
		String excelname ="kwyjqjdl.xlsx";
		try {    
			Sheet sheet = getSheet(excelname);
			androidResouceImportService.importKwyjqjdl(sheet, resourceDir);
		}catch(DataIntegrityViolationException e1){
			e1.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testimportVideo_pic_pron(){
		//读取相应文件
		String excelname ="masterVideo_pic_pron.xlsx";
		try {    
			Sheet sheet = getSheet(excelname);
			androidResouceImportService.importVideo_pic_pron(sheet, resourceDir);
		}catch(DataIntegrityViolationException e1){
			e1.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testimportSlaveWord(){
		//读取相应文件
		String excelname ="slaveWord.xlsx";
		try {    
			Sheet sheet = getSheet(excelname);
			androidResouceImportService.importSlaveWord(sheet, resourceDir);
		}catch(DataIntegrityViolationException e1){
			e1.printStackTrace();
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
