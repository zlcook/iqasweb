package com.cnu.offline.excel.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.iword.WordRes;
import com.cnu.iqas.service.iword.WordResService;
import com.cnu.offline.excel.ExcelUtils;
import com.cnu.offline.exception.ResourceFileException;
import com.cnu.offline.exception.RowDataException;

/**
* @author 周亮 
* @version 创建时间：2016年11月10日 下午4:35:31
* 类说明
*/
@Service("androidResouceImportService")
public class AndroidResourceImporServiceImpl implements AndroidResourceImportService {
	private Logger loger = LogManager.getLogger(AndroidResourceImporServiceImpl.class);
	/**
	 * 图片压缩宽度
	 *//*
	private final int resizeWidth =300;
	private final String RESDIR="I://wordresource//android//";*/
	private WordResService wordResService;
	//资源文件存储路径的key值，具体值在savepath.properties中设置
	private final String picturedirkey="android.word.picturedir";
	private final String pronunciationdirkey ="android.word.pronunciationdir";
	private final String audiokey ="android.word.audiodir"; 
	private final String videokey ="android.word.videodir";
	@Override
	public int importKwyjqjdl(Sheet sheet, String resourceDir)throws RowDataException,ResourceFileException {
		int result =1;
		if(sheet ==null)
			throw new RuntimeException("excel不存在!");
			//获得当前页的行数
			// 循环行Row
			int allrows =sheet.getLastRowNum();
			//从第1行开始读，第0行是导航行
			int rowNum = 1;
			for (rowNum = 1; rowNum <= allrows; rowNum++) {
				//将该行数据填充到word中
				WordRes wr = null;
				try {
					//读出excel中数据
					TextPhaseRowData rowData = inflateTextPhaseRowData(sheet,rowNum);
					String word =rowData.getWord();
					wr=wordResService.find(word);
					boolean isnew = false;
					if( wr ==null ){
						wr = new WordRes();
						isnew = true;
					}
					//导入资源:课文原句发音、情景段落发音
					importTextPhaseourceFile(wr,rowData,resourceDir);
					if(isnew)
						wordResService.save(wr);
					else
						wordResService.update(wr);
				} catch (RowDataException e1) {
					String error ="importKwyjqjdl：excel中数据有误："+e1.getMessage();
					loger.error(error);
					throw new RowDataException(error);
					
				} catch (ResourceFileException e) {
					String error ="importKwyjqjdl:导入"+rowNum+"行 "+wr.getWord()+" 资源文件出错."+e.getMessage();
					loger.error(error);
					throw new ResourceFileException(error);
				} 
			}
		return result;
	}

	@Override
	public int importVideo_pic_pron(Sheet sheet, String resourceDir) throws RowDataException,ResourceFileException {
		int result =1;
		if(sheet ==null)
			throw new RuntimeException("excel不存在!");
			//获得当前页的行数
			// 循环行Row
			int allrows =sheet.getLastRowNum();
			//从第1行开始读，第0行是导航行
			int rowNum = 1;
			for (rowNum = 1; rowNum <= allrows; rowNum++) {
				//将该行数据填充到word中     
				WordRes wr = null;
				try {
					//读出excel中数据
					VideoPicSoundRowData rowData = inflateVideoPicSoundRowData(sheet,rowNum);
					String word =rowData.getWord();
					wr=wordResService.find(word);
					boolean isnew = false;
					if( wr ==null ){
						wr = new WordRes();
						isnew = true;
					}
					//导入资源
					importVideoPicSoundFile(wr,rowData,resourceDir);
					if(isnew)
						wordResService.save(wr);
					else
						wordResService.update(wr);
				} catch (RowDataException e1) {
					String error ="importVideo_pic_pron：excel中数据有误："+e1.getMessage();
					loger.error(error);
					throw new RowDataException(error);
				} catch (ResourceFileException e) {
					
					String error ="importVideo_pic_pron:导入"+rowNum+"行 "+wr.getWord()+" 资源文件出错."+e.getMessage();
					loger.error(error);
					throw new ResourceFileException(error);
				} 
			}
		return result;
	}
	
	@Override
	public int importSlaveWord(Sheet sheet, String resourceDir) throws RowDataException,ResourceFileException {
		int result =1;
		if(sheet ==null)
			throw new RuntimeException("excel不存在!");
			//获得当前页的行数
			// 循环行Row
			int allrows =sheet.getLastRowNum();
			//从第1行开始读，第0行是导航行
			int rowNum = 1;
			for (rowNum = 1; rowNum <= allrows; rowNum++) {
				//将该行数据填充到word中     
				WordRes wr = null;
				try {
					//读出excel中数据
					SlaveWordRowData rowData = inflateSlaveWordRowData(sheet,rowNum);
					String word =rowData.getWord();
					wr=wordResService.find(word);
					boolean isnew = false;
					if( wr ==null ){
						wr = new WordRes();
						isnew = true;
					}
					//导入资源
					importSlaveWordFile(wr,rowData,resourceDir);
					if(isnew)
						wordResService.save(wr);
					else
						wordResService.update(wr);
				}catch (RowDataException e1) {
					String error ="importSlaveWord：excel中数据有误："+e1.getMessage();
					loger.error(error);
					throw new RowDataException(error);
				} catch (ResourceFileException e) {
					
					String error ="importSlaveWord:导入"+rowNum+"行 "+wr.getWord()+" 资源文件出错."+e.getMessage();
					loger.error(error);
					throw new ResourceFileException(error);
				}  
			}
		return result;
	}

	private void importSlaveWordFile(WordRes res, SlaveWordRowData rowData, String resourceDir) throws  ResourceFileException{
		// TODO Auto-generated method stub

		String word =rowData.getWord();
		String photoUrl =rowData.getPhotoUrl();
		String soundUrl =rowData.getSoundUrl();

		String save_photoUrl =ExcelUtils.copyFile(resourceDir,photoUrl, picturedirkey);
		String save_soundUrl =ExcelUtils.copyFile(resourceDir,soundUrl, pronunciationdirkey);
	
		res.setWord(word);
		res.setPicPath(save_photoUrl);
		res.setVoicePath(save_soundUrl);
	}

	private SlaveWordRowData inflateSlaveWordRowData(Sheet sheet, int rowNum) throws RowDataException{
		//从第一行读取，第0列为标记不读。
		SlaveWordRowData rowData  = null;
		try {
			Row row = sheet.getRow(rowNum);
			if( row!=null ){
				// 一行字段: 系统	Word	photoUrl	soundUrl
				ExcelUtils.setCellString(row,1,3);
				String wr =ExcelUtils.getCellString(row,1);
				String photoUrl =ExcelUtils.getCellString(row,2);
				String soundUrl =ExcelUtils.getCellString(row,3);
				if( !ExcelUtils.validateIdetifyPath(photoUrl))
					throw new RuntimeException(wr+"的[photoUrl]属性的数据格式有误："+photoUrl);
				if( !ExcelUtils.validateIdetifyPath(soundUrl))
					throw new RuntimeException(wr+"的[soundUrl]属性的数据格式有误："+soundUrl);
				rowData = new SlaveWordRowData(wr, photoUrl, soundUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("根据第"+rowNum+"行内容，生成SlaveWordRowData时出错。"+e.getMessage());
			throw new RowDataException("根据第"+rowNum+"行内容，生成SlaveWordRowData时出错。"+e.getMessage());
		}
		return rowData;
	}

	private void importVideoPicSoundFile(WordRes res, VideoPicSoundRowData rowData, String resourceDir) throws  ResourceFileException{

		String word =rowData.getWord();
		String photoUrl =rowData.getPhotoUrl();
		String soundUrl =rowData.getSoundUrl();
		String videoUrl =rowData.getVideoUrl();

		String save_photoUrl =ExcelUtils.copyFile(resourceDir,photoUrl, picturedirkey);
		String save_soundUrl =ExcelUtils.copyFile(resourceDir,soundUrl, pronunciationdirkey);
		String save_videoUrl =ExcelUtils.copyFile(resourceDir,videoUrl, videokey);
		String[] save_videoUrls = save_videoUrl.split("&");
		res.setWord(word);
		res.setPicPath(save_photoUrl);
		res.setVoicePath(save_soundUrl);
		if( save_videoUrls.length>=1)
			res.setVideoPath1(save_videoUrls[0]);
		if( save_videoUrls.length>=2)
			res.setVideoPath2(save_videoUrls[1]);
		if( save_videoUrls.length>=3)
			res.setVideoPath3(save_videoUrls[2]);
		
	}

	private VideoPicSoundRowData inflateVideoPicSoundRowData(Sheet sheet, int rowNum) throws RowDataException{
		//从第一行读取，第0列为标记不读。
		VideoPicSoundRowData rowData  = null;
		try {
			Row row = sheet.getRow(rowNum);
			if( row!=null ){
				// 一行字段: resourceId	Word	photoUrl	soundUrl	videoUrl
				ExcelUtils.setCellString(row,1,4);
				String wr =ExcelUtils.getCellString(row,1);
				String photoUrl =ExcelUtils.getCellString(row,2);
				String soundUrl =ExcelUtils.getCellString(row,3);
				String videoUrl =ExcelUtils.getCellString(row,4);
				if( !ExcelUtils.validateIdetifyPath(photoUrl))
					throw new RuntimeException(wr+"的[photoUrl]属性的数据格式有误："+photoUrl);
				if( !ExcelUtils.validateIdetifyPath(soundUrl))
					throw new RuntimeException(wr+"的[soundUrl]属性的数据格式有误："+soundUrl);
				if( !ExcelUtils.validateIdetifyPath(videoUrl))
					throw new RuntimeException(wr+"的[videoUrl]属性的数据格式有误："+videoUrl);
				rowData = new VideoPicSoundRowData(wr, photoUrl, soundUrl, videoUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("根据第"+rowNum+"行内容，生成VideoPicSoundRowData时出错。"+e.getMessage());
			throw new RowDataException("根据第"+rowNum+"行内容，生成VideoPicSoundRowData时出错。"+e.getMessage());
		}
		return rowData;
	}

	private void importTextPhaseourceFile(WordRes res ,TextPhaseRowData rowData, String resourceDir)throws  ResourceFileException {
		// TODO Auto-generated method stub
		
		String word =rowData.getWord();
		int grade =rowData.getGrade();
		String textsentence = rowData.getTextsentence();
		String phase = rowData.getPhase();
		String textsentencesoundUrl=rowData.getTextsentencesoundUrl();
		String phasesoundUrl=rowData.getPhasesoundUrl();

		String save_textsentencesoundUrl =ExcelUtils.copyFile(resourceDir,textsentencesoundUrl, audiokey);
		String save_phasesoundUrl =ExcelUtils.copyFile(resourceDir,phasesoundUrl, audiokey);
		
		res.setWord(word);
		wrapperWordRes(res, grade, textsentence, save_textsentencesoundUrl, phase, save_phasesoundUrl);
		
	}

	private TextPhaseRowData inflateTextPhaseRowData(Sheet sheet, int rowNum)throws RowDataException {
		//从第一行读取，第0列为标记不读。
		TextPhaseRowData rowData  = null;
		try {
			Row row = sheet.getRow(rowNum);
			if( row!=null ){
				// 一行字段: 系统  单词  grade	课文原句	课文原句发音	情景段落	情景段落发音
				ExcelUtils.setCellString(row,1,6);
				String wr =ExcelUtils.getCellString(row,1);
				Integer grade =ExcelUtils.getCellInteger(row,2);
				String textsentence =ExcelUtils.getCellString(row,3);
				String textsentencesoundUrl =ExcelUtils.getCellString(row,4);
				String phase =ExcelUtils.getCellString(row,5);
				String phasesoundUrl =ExcelUtils.getCellString(row,6);
				if( !ExcelUtils.validateIdetifyPath(textsentencesoundUrl))
					throw new RuntimeException(wr+"的[课文原句发音]属性的数据格式有误："+textsentencesoundUrl);
				if( !ExcelUtils.validateIdetifyPath(phasesoundUrl))
					throw new RuntimeException(wr+"的[情景段落发音]属性的数据格式有误："+phasesoundUrl);
				rowData = new TextPhaseRowData(wr, grade, textsentence, textsentencesoundUrl, phase, phasesoundUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("根据第"+rowNum+"行内容，生成TextPhaseRowData时出错。"+e.getMessage());
			throw new RowDataException("根据第"+rowNum+"行内容，生成TextPhaseRowData时出错。"+e.getMessage());
		}
		return rowData;
	}


	private void wrapperWordRes(WordRes res, int grade,String textsentence,String textsentencesoundUrl,String phase,String phasesoundUrl ){
		switch (grade) {
		case 1:
			res.setKwyj1(textsentence);
			res.setKwyjVoicePath1(textsentencesoundUrl);
			res.setQjdl1(phase);
			res.setQjdlVoicePath1(phasesoundUrl);
			break;
		case 2:
			res.setKwyj2(textsentence);
			res.setKwyjVoicePath2(textsentencesoundUrl);
			res.setQjdl2(phase);
			res.setQjdlVoicePath2(phasesoundUrl);
			break;
		case 3:
			res.setKwyj3(textsentence);
			res.setKwyjVoicePath3(textsentencesoundUrl);
			res.setQjdl3(phase);
			res.setQjdlVoicePath3(phasesoundUrl);
			break;
		case 4:
			res.setKwyj4(textsentence);
			res.setKwyjVoicePath4(textsentencesoundUrl);
			res.setQjdl4(phase);
			res.setQjdlVoicePath4(phasesoundUrl);
			break;
		case 5:
			res.setKwyj5(textsentence);
			res.setKwyjVoicePath5(textsentencesoundUrl);
			res.setQjdl5(phase);
			res.setQjdlVoicePath5(phasesoundUrl);
			break;
		case 6:
			res.setKwyj6(textsentence);
			res.setKwyjVoicePath6(textsentencesoundUrl);
			res.setQjdl6(phase);
			res.setQjdlVoicePath6(phasesoundUrl);
			break;
		default:
			throw new RuntimeException("grade值不在范围内："+grade);
		}
	}
	public WordResService getWordResService() {
		return wordResService;
	}
	@Autowired
	public void setWordResService(WordResService wordResService) {
		this.wordResService = wordResService;
	}
	/**
	 * 
	 * @author dell
	 *用于存储kwyjqjdl.xlxs中一行数据
	 *表中一行数据排列如下：
	 *系统	单词	grade	课文原句	课文原句发音	情景段落	情景段落发音
	 */
	private class TextPhaseRowData{
		private  String word;
		private  int grade;
		private String textsentence;
		private String textsentencesoundUrl;
		private String phase;
		private String phasesoundUrl;
		public TextPhaseRowData(String word, int grade, String textsentence, String textsentencesoundUrl, String phase,
				String phasesoundUrl) {
			super();
			this.word = word;
			this.grade = grade;
			this.textsentence = textsentence;
			this.textsentencesoundUrl = textsentencesoundUrl;
			this.phase = phase;
			this.phasesoundUrl = phasesoundUrl;
		}
		public String getWord() {
			return word;
		}
		public int getGrade() {
			return grade;
		}
		public String getTextsentence() {
			return textsentence;
		}
		public String getTextsentencesoundUrl() {
			return textsentencesoundUrl;
		}
		public String getPhase() {
			return phase;
		}
		public String getPhasesoundUrl() {
			return phasesoundUrl;
		}
	}

	/**
	 * 
	 * @author dell
	 *用于存储masterVideo_pic_pron.xlsx中一行数据
	 *表中一行数据排列如下：
	 *resourceId	Word	photoUrl	soundUrl	videoUrl
	 */
	private class VideoPicSoundRowData{
		private  String word;
		private String photoUrl;
		private String soundUrl;
		private String videoUrl;
		public VideoPicSoundRowData(String word, String photoUrl, String soundUrl, String videoUrl) {
			super();
			this.word = word;
			this.photoUrl = photoUrl;
			this.soundUrl = soundUrl;
			this.videoUrl = videoUrl;
		}
		public String getWord() {
			return word;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public String getSoundUrl() {
			return soundUrl;
		}
		public String getVideoUrl() {
			return videoUrl;
		}
		
	}
	
	/**
	 * 
	 * @author dell
	 *用于存储slaveWord.xlsx中一行数据
	 *表中一行数据排列如下：
	 *系统	Word	photoUrl	soundUrl
	 *导入一行的数据从第1列开始，列下表从0开始。
	 */
	private class SlaveWordRowData{
		private String word;
		private String photoUrl;
		private String soundUrl;
		
		public SlaveWordRowData(String word, String photoUrl, String soundUrl) {
			super();
			this.word = word;
			this.photoUrl = photoUrl;
			this.soundUrl = soundUrl;
		}
		public String getWord() {
			return word;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public String getSoundUrl() {
			return soundUrl;
		}
		
	}
	
}
