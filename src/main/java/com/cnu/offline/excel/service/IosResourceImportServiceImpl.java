package com.cnu.offline.excel.service;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import com.cnu.offline.bean.ExpandWord;
import com.cnu.offline.bean.Word;
import com.cnu.offline.bean.WordGrade;
import com.cnu.offline.bean.Wresource;
import com.cnu.offline.dao.ExpandWordDao;
import com.cnu.offline.dao.WordDao;
import com.cnu.offline.dao.WordGradeDao;
import com.cnu.offline.dao.WresourceDao;
import com.cnu.offline.excel.ExcelUtils;
import com.cnu.offline.exception.ResourceFileException;
import com.cnu.offline.exception.RowDataException;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 下午2:46:23
* 类说明:
* 将ios资源从excel中导入系统的接口
*/
@Service
public class IosResourceImportServiceImpl implements IosResourceImportService {
	
	private Logger loger = LogManager.getLogger(IosResourceImportService.class);
	private WordDao wordDao;
	private ExpandWordDao expandWordDao;
	private WresourceDao wresourceDao;
	private WordGradeDao wordGradeDao;
	//资源文件存储路径的key值，具体值在savepath.properties中设置
	private final String picturedirkey="ios.word.picturedir";
	private final String pronunciationdirkey ="ios.word.pronunciationdir";
	private final String audiokey ="ios.word.audiodir";
	private final String videokey ="ios.word.videodir";
	@Override
	public int importWord(Sheet sheet)throws RowDataException,ResourceFileException {
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
				Word word = null;
				try {
					word = inflateWord(sheet,rowNum);
					//导入资源
					//importWordFile(word,resourceDir);
					wordDao.saveOrUpdate(word);
					//save(word);
				}catch (RowDataException e1) {
					String error ="importWord：excel中数据有误："+e1.getMessage();
					loger.error(error);
					throw new RowDataException(error);
					
				} 
			}
		return result;
	}
	
	@Override
	public int importExpandword(Sheet sheet,String resourceDir)throws RowDataException,ResourceFileException {
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
				ExpandWord ew = null;
				try {
					ew = inflateExpandWord(sheet,rowNum);
					//导入资源
					importExpandWordResourceFile(ew,resourceDir);
					expandWordDao.saveOrUpdate(ew);
				} catch (RowDataException e1) {
					String error ="importExpandword：excel中数据有误："+e1.getMessage();
					loger.error(error);
					throw new RowDataException(error);
					
				} catch (ResourceFileException e) {
					String error ="importExpandword:导入"+rowNum+"行 "+ew.getWord()+" 资源文件出错."+e.getMessage();
					loger.error(error);
					throw new ResourceFileException(error);
				} 
			}
		return result;
	}

	@Override
	public int importResource(Sheet sheet,String resourceDir) throws RowDataException,ResourceFileException{
		int result =1;
		if(sheet ==null)
			throw new RuntimeException("excel不存在!");
			//获得当前页的行数
			// 循环行Row
			int allrows =sheet.getLastRowNum();
			//从第1行开始读，第0行是导航行
			int rowNum = 1;
			for (rowNum = 1; rowNum <= allrows; rowNum++) {
				//将该行数据填充到Wresource中
				WresourceRowData rowData = null;
				try {
					rowData = inflateWresourceRowData(sheet,rowNum);
					
					Wresource wr =wresourceDao.find(rowData.getWord());
					boolean isnew = false;
					if( wr==null){
						wr= new Wresource();
						isnew = true;
					}
					//导入资源
					importWResourceFile(wr,rowData,resourceDir);
					if( isnew )
						wresourceDao.save(wr);
					else
						wresourceDao.update(wr);
				} catch (RowDataException e1) {
					String error ="importResource：excel中数据有误："+e1.getMessage();
					loger.error(error);
					throw new RowDataException(error);
					
				} catch (ResourceFileException e) {
					String error ="importResource:导入"+rowNum+"行 "+rowData.getWord()+" 资源文件出错."+e.getMessage();
					loger.error(error);
					throw new ResourceFileException(error);
				}
			}
		return result;
	}
	
	@Override
	public int importVideotestquestion(Sheet sheet) throws RowDataException,ResourceFileException{
		int result =1;
		if(sheet ==null)
			throw new RuntimeException("excel不存在!");
			//获得当前页的行数
			// 循环行Row
			int allrows =sheet.getLastRowNum();
			//从第1行开始读，第0行是导航行
			int rowNum = 1;
			for (rowNum = 1; rowNum <= allrows; rowNum++) {
				//将该行数据填充到Wresource中
				try {
					VideoTestQuestionRowData vtq =inflateVideoTestQuestionRowData(sheet,rowNum);
					//查询vtq对应的单词
					Wresource wr =wresourceDao.find(" o.word= ?", vtq.getWord());
					if( wr ==null ){
						throw new RowDataException("第 "+rowNum+"行，"+vtq.getWord()+"主单词不存在资源所以无法为其视频添加问题。");
					}
					vtq.transfer2Wresource(wr);
					wresourceDao.update(wr);
				} catch (RowDataException e1) {
					String error ="importVideotestquestion：excel中数据有误："+e1.getMessage();
					loger.error(error);
					throw new RowDataException(error);
					
				} 
			}
		return result;
	}


	@Override
	public int importWordGrade(Sheet sheet,String resourceDir) throws RowDataException,ResourceFileException{
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
				WordGrade wg = null;
				try {
					wg = inflateWordGrade(sheet,rowNum);
					//导入资源
					importWordGradeFile(wg,resourceDir);
					wordGradeDao.saveOrUpdate(wg);
				} catch (RowDataException e1) {
					String error ="importWordGrade：excel中数据有误："+e1.getMessage();
					loger.error(error);
					throw new RowDataException(error);
					
				} catch (ResourceFileException e) {
					String error ="importWordGrade:导入"+rowNum+"行 资源文件出错."+e.getMessage();
					loger.error(error);
					throw new ResourceFileException(error);
				} 
			}
		return result;
	}
	
	/**
	 * 将WordGrade资源从外部导入到系统中
	 * @param wr WordGrade对象
	 * @param resourceDir   外部资源所在目录，该目录下应该有audio、picture、pronunciation、video
	 * @throws ResourceFileException
	 * 导入的资源有：课文原句发音和图片、情景段落发音和图片。
	 */
	private void importWordGradeFile(WordGrade wg, String resourceDir) throws ResourceFileException {
		
		String phasePhotoUrl =wg.getPhasephotoUrl();
		String phaseSoundUrl =wg.getPhasesoundUrl();
		
		String textsentencephotoUrl = wg.getTextsentencephotoUrl();
		String textsentencesoundUrl =  wg.getTextsentencesoundUrl();
		wg.setPhasephotoUrl(ExcelUtils.copyFile(resourceDir, phasePhotoUrl, picturedirkey));
		wg.setPhasesoundUrl(ExcelUtils.copyFile(resourceDir, phaseSoundUrl, audiokey));
		wg.setTextsentencephotoUrl(ExcelUtils.copyFile(resourceDir, textsentencephotoUrl, picturedirkey));
		wg.setTextsentencesoundUrl(ExcelUtils.copyFile(resourceDir, textsentencesoundUrl, audiokey));
		
	}

	/**
	 * 将Wresource资源从外部导入到系统中
	 * @param wr Wresource对象
	 * @param resourceDir   外部资源所在目录，该目录下应该有audio、picture、pronunciation、video
	 * @throws ResourceFileException
	 * 导入的资源有主单词9类资源：图片、发音、视频、情景段落图片、情景段落发音、课文原句图片、课文原句发音、扩展图片、扩展发音、英文图片。
	 */
	private void importWResourceFile(Wresource wr,WresourceRowData rowData, String resourceDir) throws  ResourceFileException {

		wr.setWord(rowData.getWord());
		
		String photoUrl =rowData.getPhotoUrl();
		String soundUrl =rowData.getSoundUrl();

		String videoUrl1= null;
		String videoUrl2= null;
		String videoUrl3= null;
		String[] vUrls =rowData.getVideoUrls().split("&");
		if( vUrls !=null){
			if(vUrls.length>=1)
				videoUrl1= vUrls[0];
			if( vUrls.length>=2)
				videoUrl2= vUrls[1];
			if(vUrls.length>=3)
				videoUrl3= vUrls[2];
		}
		String textsentencephotoUrl=rowData.getTextsentencephotoUrl();
		String expandsentencephotoUrl=rowData.getExpandsentencephotoUrl();
		String phasephotoUrl=rowData.getPhasephotoUrl();
		String englishimeaningsoundUrl=rowData.getEnglishimeaningsoundUrl();
		String expandsentencesoundUrl=rowData.getExpandsentencesoundUrl();
		String textsentencesoundUrl=rowData.getTextsentencesoundUrl();
		String phasesoundUrl=rowData.getPhasesoundUrl();
		
		
		wr.setPhotoUrl(ExcelUtils.copyFile(resourceDir,photoUrl, picturedirkey));
		wr.setSoundUrl(ExcelUtils.copyFile(resourceDir, soundUrl,pronunciationdirkey));
		wr.setVideoUrl1(ExcelUtils.copyFile(resourceDir,videoUrl1, videokey));
		wr.setVideoUrl2(ExcelUtils.copyFile(resourceDir,videoUrl2, videokey));
		wr.setVideoUrl3(ExcelUtils.copyFile(resourceDir,videoUrl3, videokey));
		wr.setTextsentencephotoUrl(ExcelUtils.copyFile(resourceDir, textsentencephotoUrl, picturedirkey));
		wr.setExpandsentencephotoUrl(ExcelUtils.copyFile(resourceDir,expandsentencephotoUrl,picturedirkey));
		wr.setPhasephotoUrl(ExcelUtils.copyFile(resourceDir,  phasephotoUrl, picturedirkey));
		wr.setEnglishimeaningsoundUrl(ExcelUtils.copyFile(resourceDir,englishimeaningsoundUrl, audiokey));
		wr.setExpandsentencesoundUrl(ExcelUtils.copyFile(resourceDir, expandsentencesoundUrl, audiokey));
		wr.setTextsentencesoundUrl(ExcelUtils.copyFile(resourceDir, textsentencesoundUrl, audiokey));
		wr.setPhasesoundUrl(ExcelUtils.copyFile(resourceDir, phasesoundUrl, audiokey));
		
	}
	/**
	 * 将ExpandWord资源从外部导入到系统中
	 * @param ew  ExpandWord对象
	 * @param resourceDir  外部资源所在目录，该目录下应该有audio、picture、pronunciation、video
	 * @throws ResourceFileException
	 * 导入的资源有图片、发音
	 */
	private void importExpandWordResourceFile(ExpandWord ew, String resourceDir) throws  ResourceFileException {
		String photoUrl =ew.getPhotoUrl();
		String soundUrl =ew.getSoundUrl();
		ew.setPhotoUrl(ExcelUtils.copyFile(resourceDir,photoUrl, picturedirkey));
		ew.setSoundUrl(ExcelUtils.copyFile(resourceDir, soundUrl,pronunciationdirkey));
	}
		
	
	/**
	 * 根据sheet的第rowIndex行内容生成WordGrade对象
	 * @param sheet  sheet
	 * @param rowIndex  sheet的行，从0开始
	 * @return	返回填充完数据的WordGrade,可以为null
	 * @throws RowDataException 
	 * @throws Exception
	 */
	private WordGrade inflateWordGrade(Sheet sheet, int rowNum) throws RowDataException {
		//从第一列读取，第0列为标记不读
		WordGrade wg=null;
		try {
			Row row = sheet.getRow(rowNum);
			if( row!=null ){
				ExcelUtils.setCellString(row,0,7);
				String grade =ExcelUtils.getCellString(row,0);
				String wr =ExcelUtils.getCellString(row,1);
				String textsentence =ExcelUtils.getCellString(row,2);
				String textsentencesoundUrl =ExcelUtils.getCellString(row,3);
				String phase =ExcelUtils.getCellString(row,4);
				String phasesoundUrl =ExcelUtils.getCellString(row,5);
				String textsentencephotoUrl =ExcelUtils.getCellString(row,6);
				String phasephotoUrl =ExcelUtils.getCellString(row,7);
				if( !ExcelUtils.validateIdetifyPath(textsentencesoundUrl))
					throw new RuntimeException(wr+"的textsentencesoundUrl属性的数据格式有误："+textsentencesoundUrl);
				if( !ExcelUtils.validateIdetifyPath(textsentencephotoUrl))
					throw new RuntimeException(wr+"的textsentencephotoUrl属性的数据格式有误："+textsentencephotoUrl);
				if( !ExcelUtils.validateIdetifyPath(phasesoundUrl))
					throw new RuntimeException(wr+"的phasesoundUrl属性的数据格式有误："+phasesoundUrl);
				if( !ExcelUtils.validateIdetifyPath(phasephotoUrl))
					throw new RuntimeException(wr+"的phasephotoUrl属性的数据格式有误："+phasephotoUrl);
				wg = new WordGrade(wr,Integer.parseInt(grade), textsentence, textsentencephotoUrl, textsentencesoundUrl, phase, phasephotoUrl, phasesoundUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("根据第"+rowNum+"行内容，生成WordGrade时出错。"+e.getMessage());
			throw new RowDataException("根据第"+rowNum+"行内容，生成WordGrade时出错。"+e.getMessage());
		}
		return wg;
	}
	/**
	 * 根据sheet的第rowIndex行内容生成VideoTestQuestion对象
	 * @param sheet  sheet
	 * @param rowIndex  sheet的行，从0开始
	 * @return	返回填充完数据的VideoTestQuestion,可以为null
	 * @throws RowDataException 
	 * @throws Exception
	 */
	private VideoTestQuestionRowData inflateVideoTestQuestionRowData(Sheet sheet, int rowNum) throws RowDataException {
		//从第一列读取，第0列为标记不读
		VideoTestQuestionRowData vtq=null;
		try {
			Row row = sheet.getRow(rowNum);
			if( row!=null ){
				ExcelUtils.setCellString(row,1,5);
				String videoUrl =ExcelUtils.getCellString(row,1);
				String question1 =ExcelUtils.getCellString(row,2);
				String answer1 =ExcelUtils.getCellString(row,3);
				String question2 =ExcelUtils.getCellString(row,4);
				String answer2 =ExcelUtils.getCellString(row,5);
				if( !ExcelUtils.validateIdetifyPath(videoUrl))
					throw new RuntimeException("videoUrl属性的数据格式有误："+videoUrl);
				vtq = new VideoTestQuestionRowData(videoUrl, question1, answer1, question2, answer2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//loger.error("根据第"+rowNum+"行内容，生成ExpandWord时出错。"+e.getMessage());
			throw new RowDataException("根据第"+rowNum+"行内容，生成ExpandWord时出错。"+e.getMessage());
		}
		return vtq;
	}
	
	/**
	 * 根据sheet的第rowIndex行内容生成Word对象
	 * @param sheet  sheet
	 * @param rowIndex  sheet的行，从0开始
	 * @return	返回填充完数据的word,可以为null
	 * @throws Exception
	 */
	private Word  inflateWord(Sheet sheet,int rowIndex)throws RowDataException {
		//从第一列读取，第0列为标记不读
		Word word=null;
		try {
			Row row = sheet.getRow(rowIndex);
			if( row!=null ){
				ExcelUtils.setCellString(row,1,20);
				String wr =ExcelUtils.getCellString(row,1);
				String topic =ExcelUtils.getCellString(row,2);
				String poperty =ExcelUtils.getCellString(row,3);
				String wordclass =ExcelUtils.getCellString(row,4);
				String meaning =ExcelUtils.getCellString(row,5);
				String version =ExcelUtils.getCellString(row,6);
				Integer ceshu =ExcelUtils.getCellInteger(row,7);
				Integer diffcultlevel =ExcelUtils.getCellInteger(row,8);
				String textsentence =ExcelUtils.getCellString(row,9);
				String expandsentence =ExcelUtils.getCellString(row,10);
				String imageword =ExcelUtils.getCellString(row,11);
				String expandword =ExcelUtils.getCellString(row,12);
				String wordgroup =ExcelUtils.getCellString(row,13);
				String phase =ExcelUtils.getCellString(row,14);
				Integer learningcount =ExcelUtils.getCellInteger(row,15);
				String baike =ExcelUtils.getCellString(row,16);
				String synonym =ExcelUtils.getCellString(row,17);
				String antongym =ExcelUtils.getCellString(row,18);
				String usemethod =ExcelUtils.getCellString(row,19);
				String englishmeaning =ExcelUtils.getCellString(row,20);
				
				word = new Word(wr, topic, poperty, wordclass, meaning, version, ceshu, diffcultlevel, textsentence, expandsentence, imageword, expandword, wordgroup, phase, learningcount, baike, synonym, antongym, usemethod, englishmeaning);
			}
			
		} catch (Exception e) {
			loger.error("根据第"+rowIndex+"行内容，生成word时出错。"+e.getMessage());
			throw new RowDataException("根据第"+rowIndex+"行内容，生成word时出错。"+e.getMessage());
		}
		return word;
	}

	/**
	 * 根据sheet的第rowIndex行内容生成Wresource对象，该对象的中每个视频的问题不存在，需要从videotestquestion.xlsx中导入
	 * @param sheet  sheet
	 * @param rowIndex  sheet的行，从0开始
	 * @return	返回填充完数据的Wresource,可以为null
	 * @return
	 * @throws RowDataException 
	 */
	private WresourceRowData inflateWresourceRowData(Sheet sheet, int rowNum) throws RowDataException {
		//从第一列读取，第0列为标记不读
		WresourceRowData wrs=null;
		try {
			Row row = sheet.getRow(rowNum);
			if( row!=null ){
				ExcelUtils.setCellString(row,1,19);
				String wr =ExcelUtils.getCellString(row,1);
				String photoUrl =ExcelUtils.getCellString(row,2);
				String pictureUrl =ExcelUtils.getCellString(row,3);
				String soundUrl =ExcelUtils.getCellString(row,4);
				String videoUrls=ExcelUtils.getCellString(row,5);
				String textsentencesoundUrl=ExcelUtils.getCellString(row,13);
				String phasesoundUrl=ExcelUtils.getCellString(row,14);
				String expandsentencesoundUrl=ExcelUtils.getCellString(row,15);
				String textsentencephotoUrl=ExcelUtils.getCellString(row,16);
				String phasephotoUrl=ExcelUtils.getCellString(row,17);
				String expandsentencephotoUrl=ExcelUtils.getCellString(row,18);
				String englishimeaningsoundUrl=ExcelUtils.getCellString(row,19);
				
				if( !ExcelUtils.validateIdetifyPath(photoUrl))
					throw new RuntimeException(wr+"的photoUrl属性的数据格式有误："+photoUrl);
				if( !ExcelUtils.validateIdetifyPath(pictureUrl))
					throw new RuntimeException(wr+"的pictureUrl属性的数据格式有误："+pictureUrl);
				if( !ExcelUtils.validateIdetifyPath(soundUrl))
					throw new RuntimeException(wr+"的soundUrl属性的数据格式有误："+soundUrl);
				if( !ExcelUtils.validateIdetifyPath(videoUrls))
					throw new RuntimeException(wr+"的videoUrl属性的数据格式有误："+videoUrls);
				if( !ExcelUtils.validateIdetifyPath(textsentencesoundUrl))
					throw new RuntimeException(wr+"的videoUrl属性的数据格式有误："+textsentencesoundUrl);
				if( !ExcelUtils.validateIdetifyPath(phasesoundUrl))
					throw new RuntimeException(wr+"的phasesoundUrl属性的数据格式有误："+phasesoundUrl);

				if( !ExcelUtils.validateIdetifyPath(expandsentencesoundUrl))
					throw new RuntimeException(wr+"的expandsentencesoundUrl属性的数据格式有误："+expandsentencesoundUrl);
				if( !ExcelUtils.validateIdetifyPath(textsentencephotoUrl))
					throw new RuntimeException(wr+"的textsentencephotoUrl属性的数据格式有误："+textsentencephotoUrl);
				if( !ExcelUtils.validateIdetifyPath(phasephotoUrl))
					throw new RuntimeException(wr+"的phasephotoUrl属性的数据格式有误："+phasephotoUrl);
				if( !ExcelUtils.validateIdetifyPath(expandsentencephotoUrl))
					throw new RuntimeException(wr+"的expandsentencephotoUrl属性的数据格式有误："+expandsentencephotoUrl);
				if( !ExcelUtils.validateIdetifyPath(englishimeaningsoundUrl))
					throw new RuntimeException(wr+"的englishimeaningsoundUrl属性的数据格式有误："+englishimeaningsoundUrl);
				wrs = new WresourceRowData(wr, photoUrl, pictureUrl, soundUrl, videoUrls, textsentencesoundUrl, phasesoundUrl, expandsentencesoundUrl, textsentencephotoUrl, phasephotoUrl, expandsentencephotoUrl, englishimeaningsoundUrl);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//loger.error("根据第"+rowNum+"行内容，生成Wresource时出错。"+e.getMessage());
			throw new RowDataException("根据第"+rowNum+"行内容，生成Wresource时出错。"+e.getMessage());
		}
		return wrs;
	}
	/**
	 * 根据sheet的第rowIndex行内容生成ExpandWord对象
	 * @param sheet  sheet
	 * @param rowIndex  sheet的行，从0开始
	 * @return	返回填充完数据的ExpandWord,可以为null
	 * @throws RowDataException 
	 * @throws Exception
	 */
	private ExpandWord inflateExpandWord(Sheet sheet, int rowNum) throws RowDataException {
		//从第一列读取，第0列为标记不读
		ExpandWord ew=null;
		try {
			Row row = sheet.getRow(rowNum);
			if( row!=null ){
				ExcelUtils.setCellString(row,0,3);
				String wr =ExcelUtils.getCellString(row,0);
				String photoUrl =ExcelUtils.getCellString(row,1);
				String soundUrl =ExcelUtils.getCellString(row,2);
				String textsentence =ExcelUtils.getCellString(row,3);
				if( !ExcelUtils.validateIdetifyPath(photoUrl))
					throw new RuntimeException(wr+"的photoUrl属性的数据格式有误："+photoUrl);
				if( !ExcelUtils.validateIdetifyPath(soundUrl))
						throw new RuntimeException(wr+"的soundUrl属性的数据格式有误："+soundUrl);
				ew = new ExpandWord(wr, photoUrl, soundUrl, textsentence);
			}
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("根据第"+rowNum+"行内容，生成ExpandWord时出错。"+e.getMessage());
			throw new RowDataException("根据第"+rowNum+"行内容，生成ExpandWord时出错。"+e.getMessage());
		}
		return ew;
	}

	
	public WordDao getWordDao() {
		return wordDao;
	}
	@Resource
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	public ExpandWordDao getExpandWordDao() {
		return expandWordDao;
	}
	@Resource
	public void setExpandWordDao(ExpandWordDao expandWordDao) {
		this.expandWordDao = expandWordDao;
	}
	public WresourceDao getWresourceDao() {
		return wresourceDao;
	}
	@Resource
	public void setWresourceDao(WresourceDao wresourceDao) {
		this.wresourceDao = wresourceDao;
	}
	public WordGradeDao getWordGradeDao() {
		return wordGradeDao;
	}
	@Resource
	public void setWordGradeDao(WordGradeDao wordGradeDao) {
		this.wordGradeDao = wordGradeDao;
	}

	private class VideoTestQuestionRowData{
		//视频对应单词
		private String word;
		//视频等级
		private int videoGrade;
		//视频url
		private String videoUrl;
		//问题1
		private String question1;
		//问题1答案
		private String answer1;
		//问题2
		private String question2;
		private String answer2;
		public VideoTestQuestionRowData(String videoUrl, String question1, String answer1, String question2, String answer2) {
			super();
			//从videoUrl中抽取出word和videoGrade
			extractData(videoUrl);
			this.videoUrl = videoUrl;
			this.question1 = question1;
			this.answer1 = answer1;
			this.question2 = question2;
			this.answer2 = answer2;
		}
		/**
		 * 从url中抽取出word和videoGrade属性值
		 * @param url
		 * url的值的正则表达式格式如下：video/[\S]+[123]{1}.mp4
		 * 例如video/coffee1.mp4符合要求
		 */
		private void extractData(String url) {
			boolean flage = false;
			if( url ==null || url.trim().equals(""))
				throw new RuntimeException("视频路径不存在");
			url= url.trim();
			// TODO Auto-generated method stub
			String videoReg= "video/[\\S]+[123]{1}.mp4";
			if( url.matches(videoReg)){
				//获取vidoGrage
				int potindex = url.lastIndexOf(".");
				if( potindex!=-1){
					videoGrade = Integer.parseInt(url.substring(potindex-1,potindex));
					//获取单词
					word= url.substring(url.indexOf("/")+1, url.lastIndexOf(".")-1);
					flage = true;
				}
			}
			if( !flage)
				throw new RuntimeException("视频路径格式不满足video/*.mp4规范。:"+url);
			
		}
		/**
		 * 将本对象中数据转移到Wresource中
		 * @param Wresource
		 */
		public void transfer2Wresource(Wresource wr){
			switch (videoGrade) {
			case 1:
				wr.setV1q1(question1);
				wr.setV1q2(question2);
				wr.setV1a1(answer1);
				wr.setV1a2(answer2);
				break;
			case 2:

				wr.setV2q1(question1);
				wr.setV2q2(question2);
				wr.setV2a1(answer1);
				wr.setV2a2(answer2);
				break;
			case 3:

				wr.setV3q1(question1);
				wr.setV3q2(question2);
				wr.setV3a1(answer1);
				wr.setV3a2(answer2);
				break;
			default:
				throw  new RuntimeException("视频等级不存在只有1,2,3");
			}
		}
		public String getWord() {
			return word;
		}
		
	}
	
	private class WresourceRowData{
		private String word;
		private String photoUrl;
		private String pictureUrl;
		private String soundUrl;
		private String videoUrls;
		private String textsentencesoundUrl;
		private String phasesoundUrl;
		private String expandsentencesoundUrl;
		private String textsentencephotoUrl;
		private String phasephotoUrl;
		private String expandsentencephotoUrl;
		private String englishimeaningsoundUrl;
		public WresourceRowData(String word, String photoUrl, String pictureUrl, String soundUrl, String videoUrls,
				String textsentencesoundUrl, String phasesoundUrl, String expandsentencesoundUrl,
				String textsentencephotoUrl, String phasephotoUrl, String expandsentencephotoUrl,
				String englishimeaningsoundUrl) {
			super();
			this.word = word;
			this.photoUrl = photoUrl;
			this.pictureUrl = pictureUrl;
			this.soundUrl = soundUrl;
			this.videoUrls = videoUrls;
			this.textsentencesoundUrl = textsentencesoundUrl;
			this.phasesoundUrl = phasesoundUrl;
			this.expandsentencesoundUrl = expandsentencesoundUrl;
			this.textsentencephotoUrl = textsentencephotoUrl;
			this.phasephotoUrl = phasephotoUrl;
			this.expandsentencephotoUrl = expandsentencephotoUrl;
			this.englishimeaningsoundUrl = englishimeaningsoundUrl;
		}
		
		public String getWord() {
			return word;
		}

		public String getPhotoUrl() {
			return photoUrl;
		}
		public String getPictureUrl() {
			return pictureUrl;
		}
		public String getSoundUrl() {
			return soundUrl;
		}
		public String getVideoUrls() {
			return videoUrls;
		}
		public String getTextsentencesoundUrl() {
			return textsentencesoundUrl;
		}
		public String getPhasesoundUrl() {
			return phasesoundUrl;
		}
		public String getExpandsentencesoundUrl() {
			return expandsentencesoundUrl;
		}
		public String getTextsentencephotoUrl() {
			return textsentencephotoUrl;
		}
		public String getPhasephotoUrl() {
			return phasephotoUrl;
		}
		public String getExpandsentencephotoUrl() {
			return expandsentencephotoUrl;
		}
		public String getEnglishimeaningsoundUrl() {
			return englishimeaningsoundUrl;
		}
		
	}
}
