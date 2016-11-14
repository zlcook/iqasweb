package com.cnu.offline.excel.service;

import org.apache.poi.ss.usermodel.Sheet;

import com.cnu.offline.exception.ResourceFileException;
import com.cnu.offline.exception.RowDataException;

/**
* @author 周亮 
* @version 创建时间：2016年11月10日 下午4:27:30
* 类说明
* android导入excel表和对应的资源服务接口。
* 导入表：kwyjqjdl.xlsx、masterVideo_pic_pron.xlsx、slaveWord.xlsx
* 表名称可以不同，存储内容的格式一致就行。
* 不同表的内容格式说明如下：
* 
* wyjqjdl.xlsx表
* 表中存放着主单词的单词、发音、图片、视频信息。
* 一行的列信息：系统	单词	年级	课文原句	课文原句发音	情景段落	情景段落发音
* 说明：一行信息中从第1列开始读。下标从0开始。
* 
* masterVideo_pic_pron.xlsx表
* 表中存放着主单词的单词、单词图片路径、单词发音路径、单词视频路径信息。
* 一行的列信息：系统	单词	单词图片路径	单词发音路径	单词视频路径
* 说明：一行信息中从第1列开始读。下标从0开始。
* 
* slaveWord.xlsx表
* 表中存放着从单词的单词、图片、发音信息
* 一行的列信息：系统	单词	单词图片路径	单词发音路径
* 说明：一行信息中从第1列开始读。下标从0开始。
*/
public interface AndroidResourceImportService {

	/**
	 * 导入kwyjqjdl.xlsx表,同时导入对应的资源文件，表中存放着主单词的单词、年级、课文原句	、课文原句发音、	情景段落、	情景段落发音信息。
	 * @param sheet kwyjqjdl.xlsx表
	 * 表中一行数据排列如下：
	 * 系统	单词	年级	课文原句	课文原句发音	情景段落	情景段落发音
	 * 导入一行的数据从第1列开始，列下表从0开始。
	 * @param resourceDir 资源文件在硬盘上的根目录，该目录下应该有存放单词资源的audio、picture、pronunciation、video文件夹
	 * 如：I://wordresource//android
	 * 
	 * @return
	 * @throws RowDataException     excel文档有误异常
	 * @throws ResourceFileException   导入资源出错异常
	 *  导入的资源有图片、发音
	 * 1:正常
	 */
	
	public int importKwyjqjdl(Sheet sheet,String resourceDir)throws RowDataException,ResourceFileException ;

	/**
	 * 导入masterVideo_pic_pron.xlsx表,同时导入对应的资源文件，表中存放着主单词的单词、发音、图片、视频信息。
	 * @param sheet masterVideo_pic_pron.xlsx表
	 * 表中一行数据排列如下：
	 * 系统	单词	单词图片路径	单词发音路径	单词视频路径
	 * 导入一行的数据从第1列开始，列下表从0开始。
	 * @param resourceDir 
	 * 资源文件在硬盘上的根目录，该目录下应该有存放单词资源的audio、picture、pronunciation、video文件夹
	 * 如：I://wordresource//android
	 * 
	 * @return
	 * @throws RowDataException     excel文档有误异常
	 * @throws ResourceFileException   导入资源出错异常
	 *  导入的资源有图片、发音
	 * 1:正常
	 */
	public int importVideo_pic_pron(Sheet sheet,String resourceDir)throws RowDataException,ResourceFileException ;

	/**
	 * 导入slaveWord.xlsx表,同时导入对应的资源文件。表中存放着从单词的单词、图片、发音信息
	 * @param sheet slaveWord.xlsx表
	 * 表中一行数据排列如下：
	 * 系统	单词	单词图片路径	单词发音路径
	 * 导入一行的数据从第1列开始，列下表从0开始。
	 * @param resourceDir 
	 * 资源文件在硬盘上的根目录，该目录下应该有存放单词资源的audio、picture、pronunciation、video文件夹
	 * 如：I://wordresource//android
	 * 
	 * @return
	 * @throws RowDataException     excel文档有误异常
	 * @throws ResourceFileException   导入资源出错异常
	 *  导入的资源有图片、发音
	 * 1:正常
	 */
	public int importSlaveWord(Sheet sheet,String resourceDir)throws RowDataException,ResourceFileException ;
}
