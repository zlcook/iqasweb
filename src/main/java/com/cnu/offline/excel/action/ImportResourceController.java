package com.cnu.offline.excel.action;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.offline.excel.ExcelFileEnum;
import com.cnu.offline.excel.ExcelUtils;
import com.cnu.offline.excel.service.ImportExcelService;
import com.cnu.offline.exception.ResourceFileException;
import com.cnu.offline.exception.RowDataException;


/**
* @author 周亮 
* @version 创建时间：2016年11月11日 上午9:09:58
* 类说明
* 将单词资源从excel中导入控制类
*/
@Controller
@RequestMapping(value="/mobile/offline/")
public class ImportResourceController {

	private Logger logger = LogManager.getLogger(ImportResourceController.class);
	private ImportExcelService importExcelService;
	
	/**
	 *  导入excel及其资源
	 * @param file excel文件
	 * @param resourceType excel文件类型，
	 * int类型，其值参考枚举类型ExcelFileEnum中对不同类型文件的描述。
	 *  0：ANDROID_KWYJQJDL 
	 * 	1：ANDROID_VIDEO_PIC_PRON 
	 *	2：ANDROID_SLAVEWORD 
	 *	3：IOS_WORD 
	 *	4：IOS_EXPANDWORD <
	 *	5：IOS_RESOURCE 
	 *	6：IOS_WORDGRADE 
	 *	7：IOS_VIDEOTESTQUESTION
	 * @param resourceDir  资源文件存放根目录，该目录下要有audio、picture、pronunciation、video文件夹，里面存放这excel中描述的资源文件
	 * @return
	 * http://localhost:8080/iqasweb/mobile/offline/import.html
	 */
	@RequestMapping(value="import")
	public ModelAndView importExcel( @RequestParam("file") CommonsMultipartFile file,@RequestParam(required=true)int resourceType,@RequestParam(required=true)String resourceDir){
		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		String message="导入成功";
		if( !file.isEmpty()){
			try {
				 Workbook wb = ExcelUtils.getWorkBook(file);
				 Sheet sheet= wb.getSheetAt(0);
				 
				 if( ExcelFileEnum.values().length>resourceType){
					 int result=1 ;
					 ExcelFileEnum fileEnum= ExcelFileEnum.values()[resourceType];
					result = importExcelService.importExcelAndResource(sheet, resourceDir, fileEnum);
					if( result ==1 ){
						 message = file.getOriginalFilename()+"导入成功："+fileEnum.toString();
						 logger.info(message);
					 }
				 }else
					 message="文件类型不存在,resourceType的可选范围如下：<br>"
					 		+ "0：ANDROID_KWYJQJDL <br>  "
					 		+ "1：ANDROID_VIDEO_PIC_PRON <br>"
					 		+ "2：ANDROID_SLAVEWORD <br>"
					 		+ "3：IOS_WORD <br>"
					 		+ "4：IOS_EXPANDWORD <br>"
					 		+ "5：IOS_RESOURCE <br>"
					 		+ "6：IOS_WORDGRADE <br>"
					 		+ "7：IOS_VIDEOTESTQUESTION";
				 
			}catch(RowDataException e1){
				message = e1.getMessage();
				logger.error(message);
			}catch (ResourceFileException e2) {
				message = e2.getMessage();
				logger.error(message);
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error("导入excel文件未读取到");
				message="导入excel文件未读取到";
			}
		}
		mv.addObject("message",message);
		return mv;
	}

	public ImportExcelService getImportExcelService() {
		return importExcelService;
	}
	@Autowired
	public void setImportExcelService(ImportExcelService importExcelService) {
		this.importExcelService = importExcelService;
	}
	
}
