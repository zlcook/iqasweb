package com.cnu.offline.excel.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.offline.excel.ExcelFileEnum;
import com.cnu.offline.exception.ResourceFileException;
import com.cnu.offline.exception.RowDataException;

/**
* @author 周亮 
* @version 创建时间：2016年11月11日 上午10:05:36
* 类说明
*/
@Service("importExcelService")
public class ImporExcelServiceImpl implements ImportExcelService {

	private AndroidResourceImportService androidResourceImportService;
	private IosResourceImportService iosResourceImportService;
	@Override
	public int importExcelAndResource(Sheet sheet, String resourceDir, ExcelFileEnum excelEnum)throws RowDataException,ResourceFileException  {
		// TODO Auto-generated method stub
		int result =0;
		if( excelEnum.equals(ExcelFileEnum.ANDROID_KWYJQJDL))
			result=androidResourceImportService.importKwyjqjdl(sheet, resourceDir);
		else if( excelEnum.equals(ExcelFileEnum.ANDROID_SLAVEWORD)){
			result=androidResourceImportService.importSlaveWord(sheet, resourceDir);
		}else if( excelEnum.equals(ExcelFileEnum.ANDROID_VIDEO_PIC_PRON)){
			result=androidResourceImportService.importVideo_pic_pron(sheet, resourceDir);
		}else if( excelEnum.equals(ExcelFileEnum.IOS_EXPANDWORD)){
			result=iosResourceImportService.importExpandword(sheet, resourceDir);
		}else if( excelEnum.equals(ExcelFileEnum.IOS_RESOURCE)){
			result=iosResourceImportService.importResource(sheet, resourceDir);
		}else if( excelEnum.equals(ExcelFileEnum.IOS_VIDEOTESTQUESTION)){
			result=iosResourceImportService.importVideotestquestion(sheet);
		}else if( excelEnum.equals(ExcelFileEnum.IOS_WORD)){
			result=iosResourceImportService.importWord(sheet);
		}else if( excelEnum.equals(ExcelFileEnum.IOS_WORDGRADE)){
			result=iosResourceImportService.importWordGrade(sheet, resourceDir);
		}
		return result;
	}
	
	public AndroidResourceImportService getAndroidResourceImportService() {
		return androidResourceImportService;
	}
	@Autowired
	public void setAndroidResourceImportService(AndroidResourceImportService androidResourceImportService) {
		this.androidResourceImportService = androidResourceImportService;
	}
	public IosResourceImportService getIosResourceImportService() {
		return iosResourceImportService;
	}
	@Autowired
	public void setIosResourceImportService(IosResourceImportService iosResourceImportService) {
		this.iosResourceImportService = iosResourceImportService;
	}
}
