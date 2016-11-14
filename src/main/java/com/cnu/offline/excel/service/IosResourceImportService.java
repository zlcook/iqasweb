package com.cnu.offline.excel.service;

import org.apache.poi.ss.usermodel.Sheet;

import com.cnu.offline.exception.ResourceFileException;
import com.cnu.offline.exception.RowDataException;

/**
 * 类说明
* ios导入excel表和对应的资源服务接口。
* 导入表：word.xlsx、expandword.xlsx、Resource.xlsx、Wordgarde.xlsx、videotestquestion.xlsx
* 表名称可以不同，存储内容的格式一致就行。
* 不同表的内容格式说明如下：
* 
* word.xlsx表，该表需要在其它表导入之前导入
* 表中存放着主单词的文本信息。
* 一行的列信息：wordId	Word	topic	poperty	wordclass	meaning	version	ceshu	diffcultlevel	textsentence	expandsentence	imageword	expandword	wordgroup	phase	learningcount	baike	synonym	antongym	usemethod	Englishimeaning
* 说明：一行信息中从第1列开始读。下标从0开始。
* 
* expandword.xlsx表
* 表中存放着从单词的单词、单词图片路径、单词发音路径、课文原句信息。
* 一行的列信息：word	 photoUrl	soundUrl   textsentence
* 说明：一行信息中从第0列开始读。下标从0开始。
* 
* Resource.xlsx表
* 表中存放着主单词的图片、发音、视频等相关资源信息
* 一行的列信息：resourceId	Word	photoUrl	pictureUrl	soundUrl	videoUrl	version	songUrl	clickphoto	clicksound	clickvideo1	clilckvideo2	clickvideo3	textsentencesoundeUrl	phasesoundUrl	expandsentencesoundUrl	textsentencephotoUrl	phasephotoUrl	expandsentencephotoUrl	EnglishimeaningUrl
* 说明：一行信息中从第1列开始读。下标从0开始。
* 
* Wordgarde.xlsx
* * 表中存放着主单词的不同年级的课文原句和情景段落的文本和资源信息
* 一行的列信息：grade	word	textsentence	textsentencesoundUrl	phase	phasesoundUrl	textsentencephotoUrl	phasephotoUrl
* 说明：一行信息中从第0列开始读。下标从0开始。
* 
* videotestquestion.xlsx
* * 表中存放着主单词的不同视频对应的问题和答案信息，该表需要在Resource.xlsx表导入之后导入
* 一行的列信息：videotestId	videoUrl	question1	answer1	question2	answer2
* 说明：一行信息中从第1列开始读。下标从0开始。
* 
* @author 周亮 
* @version 创建时间：2016年10月27日 下午2:38:54
* 
*/

public interface IosResourceImportService {

	/**
	 * 导入word.xlsx表,同时导入对应的资源文件
	 * @param sheet word.xlsx表
	 * word.xlsx表，该表需要在其它表导入之前导入
	 * 表中存放着主单词的文本信息。
	 * 一行的列信息：wordId	Word	topic	poperty	wordclass	meaning	version	ceshu	diffcultlevel	textsentence	expandsentence	imageword	expandword	wordgroup	phase	learningcount	baike	synonym	antongym	usemethod	Englishimeaning
	 * 说明：一行信息中从第1列开始读。下标从0开始。
	 * @return
	 * @throws RowDataException     excel文档有误异常
	 * @throws ResourceFileException   导入资源出错异常
	 *  导入的资源有图片、发音
	 * 1:正常
	 */
	public int importWord(Sheet sheet)throws RowDataException,ResourceFileException;
	/**
	 * 导入expandword.xlsx表,同时导入对应的资源文件
	 * @param sheet expandword.xlsx表
	 *  expandword.xlsx表
	 * 表中存放着从单词的单词、单词图片路径、单词发音路径、课文原句信息。
	 * 一行的列信息：word	 photoUrl	soundUrl   textsentence
	 * 说明：一行信息中从第0列开始读。下标从0开始。
	 * @param resourceDir 
	 * 资源文件在硬盘上的根目录，该目录下应该有audio、picture、pronunciation、video
	 * 如：I://wordresource//iosdir
	 * 
	 * @return
	 * @throws RowDataException     excel文档有误异常
	 * @throws ResourceFileException   导入资源出错异常
	 *  导入的资源有图片、发音
	 * 1:正常
	 */
	public int importExpandword(Sheet sheet,String resourceDir)throws RowDataException,ResourceFileException;
	/**
	 * 导入Resource.xlsx表,同时导入对应的资源文件
	 * @param sheet Resource.xlsx表
	 *  Resource.xlsx表
	 * 表中存放着主单词的图片、发音、视频等相关资源信息
	 * 一行的列信息：resourceId	Word	photoUrl	pictureUrl	soundUrl	videoUrl	version	songUrl	clickphoto	clicksound	clickvideo1	clilckvideo2	clickvideo3	textsentencesoundeUrl	phasesoundUrl	expandsentencesoundUrl	textsentencephotoUrl	phasephotoUrl	expandsentencephotoUrl	EnglishimeaningUrl
	 * 说明：一行信息中从第1列开始读。下标从0开始。
	 * @param resourceDir  
	 * 资源文件在硬盘上的根目录，该目录下应该有audio、picture、pronunciation、video
	 * 如：I://wordresource//iosdir
	 * 
	 * @return
	 * @throws RowDataException     excel文档有误异常
	 * @throws ResourceFileException   导入资源出错异常
	 * 1:正常
	 * 导入的资源有主单词9类资源：图片、发音、视频、情景段落图片、情景段落发音、课文原句图片、课文原句发音、扩展图片、扩展发音、英文图片。
	 */
	public int importResource(Sheet sheet,String resourceDir)throws RowDataException,ResourceFileException;
	/**
	 * 导入videotestquestion.xlsx表，该表中的内容是对Resource.xlsx表中内容的填充，Wresource的视频属性对应的问题和答案在该表中存放着。
	 * Wresource对象存储了每个单词的三个视频，每个视频都有2单问答题，题目就存在该表格中，导入该表格就是填充Wresource对象的问答数据。
	 * @param sheet videotestquestion.xlsx表
	 *  videotestquestion.xlsx
	 *  表中存放着主单词的不同视频对应的问题和答案信息，该表需要在Resource.xlsx表导入之后导入
	 * 一行的列信息：videotestId	videoUrl	question1	answer1	question2	answer2
	 * 说明：一行信息中从第1列开始读。下标从0开始。
	 * @return
	 * @throws RowDataException     excel文档有误异常
	 * @throws ResourceFileException   导入资源出错异常
	 * 1:正常
	 */
	public int importVideotestquestion(Sheet sheet)throws RowDataException,ResourceFileException;
	/**
	 * 导入Wordgarde.xlsx表,同时导入对应的资源文件
	 * @param sheet Wordgarde.xlsx表
	 * Wordgarde.xlsx
	 *  表中存放着主单词的不同年级的课文原句和情景段落的文本和资源信息
	 * 一行的列信息：grade	word	textsentence	textsentencesoundUrl	phase	phasesoundUrl	textsentencephotoUrl	phasephotoUrl
	 * 说明：一行信息中从第0列开始读。下标从0开始。
	 * @param resourceDir  
	 * 资源文件在硬盘上的根目录，该目录下应该有audio、picture、pronunciation、video
	 * 如：I://wordresource//iosdir
	 * @return
	 * @throws RowDataException     excel文档有误异常
	 * @throws ResourceFileException   导入资源出错异常
	 */
	public int importWordGrade(Sheet sheet,String resourceDir)throws RowDataException,ResourceFileException;
	
}
