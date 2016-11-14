package com.cnu.offline.excel;
/**
* @author 周亮 
* @version 创建时间：2016年11月11日 上午9:48:05
* 类说明
* 导入单词资源类型。
* 导入的资源类型分为ios和android两类，各类文件说明如下：
* 
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
	* 
 ios导入excel表。
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
*/
public enum ExcelFileEnum {
	
	/**
	 * 导入表：kwyjqjdl.xlsx、masterVideo_pic_pron.xlsx、slaveWord.xlsx
	 * 表名称可以不同，存储内容的格式一致就行。
	 * 不同表的内容格式说明如下：
	 */
	ANDROID_KWYJQJDL,  //android端存放主单词课文原句excel文件
	/**
	 *  masterVideo_pic_pron.xlsx表
	* 表中存放着主单词的单词、单词图片路径、单词发音路径、单词视频路径信息。
	* 一行的列信息：系统	单词	单词图片路径	单词发音路径	单词视频路径
	* 说明：一行信息中从第1列开始读。下标从0开始。
	 */
	ANDROID_VIDEO_PIC_PRON,//android端存放主单词视频、图片、发音资源excel文件
	/**
	 * slaveWord.xlsx表
	* 表中存放着从单词的单词、图片、发音信息
	* 一行的列信息：系统	单词	单词图片路径	单词发音路径
	* 说明：一行信息中从第1列开始读。下标从0开始。
	 */
	ANDROID_SLAVEWORD,//android端存放从单词资源excel文件
	
	/**
	 * word.xlsx表，该表需要在其它表导入之前导入
	* 表中存放着主单词的文本信息。
	* 一行的列信息：wordId	Word	topic	poperty	wordclass	meaning	version	ceshu	diffcultlevel	textsentence	expandsentence	imageword	expandword	wordgroup	phase	learningcount	baike	synonym	antongym	usemethod	Englishimeaning
	* 说明：一行信息中从第1列开始读。下标从0开始。
	 */
	IOS_WORD,
	/**
	 * expandword.xlsx表
	* 表中存放着从单词的单词、单词图片路径、单词发音路径、课文原句信息。
	* 一行的列信息：word	 photoUrl	soundUrl   textsentence
	* 说明：一行信息中从第0列开始读。下标从0开始。
	 */
	IOS_EXPANDWORD,
	/**
	 *  Resource.xlsx表
	* 表中存放着主单词的图片、发音、视频等相关资源信息
	* 一行的列信息：resourceId	Word	photoUrl	pictureUrl	soundUrl	videoUrl	version	songUrl	clickphoto	clicksound	clickvideo1	clilckvideo2	clickvideo3	textsentencesoundeUrl	phasesoundUrl	expandsentencesoundUrl	textsentencephotoUrl	phasephotoUrl	expandsentencephotoUrl	EnglishimeaningUrl
	* 说明：一行信息中从第1列开始读。下标从0开始。
	 */
	IOS_RESOURCE,
	/**
	 *  Wordgarde.xlsx
	* * 表中存放着主单词的不同年级的课文原句和情景段落的文本和资源信息
	* 一行的列信息：grade	word	textsentence	textsentencesoundUrl	phase	phasesoundUrl	textsentencephotoUrl	phasephotoUrl
	* 说明：一行信息中从第0列开始读。下标从0开始。
	 */
	IOS_WORDGRADE,
	/**
	 *  videotestquestion.xlsx
	* * 表中存放着主单词的不同视频对应的问题和答案信息，该表需要在Resource.xlsx表导入之后导入
	* 一行的列信息：videotestId	videoUrl	question1	answer1	question2	answer2
	* 说明：一行信息中从第1列开始读。下标从0开始。
	 */
	IOS_VIDEOTESTQUESTION
}
