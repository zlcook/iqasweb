package com.cnu.offline.excel.service;

import org.apache.poi.ss.usermodel.Sheet;

import com.cnu.offline.excel.ExcelFileEnum;
import com.cnu.offline.exception.ResourceFileException;
import com.cnu.offline.exception.RowDataException;

/**
* @author 周亮 
* @version 创建时间：2016年11月11日 上午10:03:15
* 类说明
* 导入excel文件及文件中对应的资源
*/
public interface ImportExcelService {
	/**
	 * 导入excel文件及文件中对应的资源
	 * @param sheet  excel文件中的一页
	 * @param resourceDir  资源文件根目录绝对路径
	 * @param excelEnum   excel资源文件类型
	 * @return
	 * 1:正常
	 * -1：excel文档有误
	 * -2：导入资源出错
	 */
	public int importExcelAndResource(Sheet sheet,String resourceDir,ExcelFileEnum excelEnum)throws RowDataException,ResourceFileException ;
}
