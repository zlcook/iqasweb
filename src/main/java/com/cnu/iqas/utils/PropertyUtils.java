package com.cnu.iqas.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 包含项目中各个资源的key值和通过key值获取资源相对路径的方法。
 * 具体路径在savepath.properties中配置
 * @author dell
 * 
 */
public class PropertyUtils {
	
	/**
	 * 项目文件系统根目录文件夹存放的路径 D:/Soft/autoiqasweb/
	 */
	public static final String IQASWEB_FILE_SYSTEM_DIR="fileSystemRoot";
	/**
	 * 项目文件系统的根目录文件夹名称 ifilesystem/
	 */
	public static final String IQASWEB_FILE_SYSTEM_NAME="ifilesystem";
	
	/**
	 * 项目的log图片存放路径
	 */
	public static final String LOG ="log";
	/**
	 * wordnet路径
	 */
	public static final String JWSDIR ="jwsdir";
	/**
	 * wordnet版本号
	 */
	public static final String JWSVERSION = "jwsversion";
	/**
	 * android单词图片资源路径
	 */
	public static final String ANDROID_WORD_IMAGE_DIR ="android.word.picturedir";
	/**
	 * android单词视频资源路径
	 */
	public static final String ANDROID_WORD_VIDEO_DIR = "android.word.videodir";
	/**
	 * android单词声音资源路径
	 */
	public static final String ANDROID_WORD_VOICE_DIR ="android.word.pronunciationdir";
	/**
	 * 单词绘本资源路径
	 */
	public static final String WORD_PICTUREBOOK_DIR = "word.picturebooksavedir";
	
	/**
	 * 项目一：商品图片保存相对路径
	 */
	public static final String FIRST_COMMODITY_PIC="first.commodity.picturesavedir";

	/**
	 * 项目一：学生头像图片保存相对路径
	 */
	public static final String FIRST_USER_LOG="first.user.logosavedir";
	
	
	
	/**
	 * 项目ios：学生作品图片保存相对路径
	 */
	public static final String IOS_WORK_IMAGE="ios.works.picturesavedir";
	/**
	 * 项目ios：学生作品视频保存相对路径
	 */
	public static final String IOS_WORK_VIDEO="ios.works.videosavedir";
	/**
	 * 项目ios：学生作品声音保存相对路径
	 */
	public static final String IOS_WORK_VOICE="ios.works.voicesavedir";
	
	/**
	 * 项目ios：学生头像图片保存相对路径
	 */
	public static final String IOS_USER_LOG="ios.user.logosavedir";
	/**
	 * 项目ios：单词主题图片保存相对路径
	 */
	public static final String IOS_WORD_THEME_LOG="ios.wordtheme.logosavedir";

	/**
	 * 主题离线包对应的key
	 */
	public static final String THEME_OFFLINE_BAG= "theme.offlinebag";
	
	private static Properties properties = new Properties();
	static{
		try {
			properties.load(PropertyUtils.class.getClassLoader().getResourceAsStream("savepath.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据key值读取savepath.properties文件中配置的值
	 * @param key
	 * @return
	 */
	public static String get(String key){		
		return properties.getProperty(key);
	}
	/**
	 * 整个项目文件系统的跟文件夹名称，文件系统目录在IQASWEB_FILE_SYSTEM_DIR参数中保存着
	 * @return ifilesystem/
	 */
	private static String getFileSystemFileName(){
		return properties.getProperty(PropertyUtils.IQASWEB_FILE_SYSTEM_NAME);
	}
	/**
	 * 获取资源保存路径，该路径会保存在数据库中，但是这只是保存路径的前半部分，后半部分为文件在该路径下的进一步存储路径。
	 * @param key,系统中文件资源的存放路径的key，
	 * @return 如果key =word.picturesavedir,则返回： ifilesystem/noumenon/wordresource/pictures 。
	 * 
	 * 根据PropertiesUtils中常量的值获取资源保存路径，该路径会保存在数据库中，但是这只是保存路径的前半部分，后半部分为文件在该路径下的进一步存储路径。
	 * 该路径由整个项目文件系统的根文件夹名称和资源key值对应的相对路径构成。构成的路径是相对路径，相对项目文件系统目录的路径。文件系统目录在IQASWEB_FILE_SYSTEM_DIR参数中保存着。
	 * 
	 * 例如：保存发音图片，发音图片的存储路径key为“voice”,则保存在数据库的记录为:getFileSaveDir(key)+文件进一步路径。
	 * 文件进一步路径指：文件在getFileSaveDir(key)目录下会进一步生成保存路径，一般会按时间生成保存目录和保存文件名，如2016/08/22/123434.mp3
	 * 所以保存在数据库的最终记录为：getFileSaveDir(key)+"/"+2016/08/22/123434.mp3，组合生成的值。如果getFileSaveDir(key)方法返回值为ifilesystem/wordres/voice
	 * 则具体值存储在数据库中的值为：ifilesystem/wordres/voice/2016/08/22/123434.mp3。
	 * 由此可见数据库中存储的值实际上是相对路径，那么相对谁的路径呢？相对IQASWEB_FILE_SYSTEM_DIR参数指明的路径，即get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR)获取的值，
	 * 该值为项目的系统在磁盘上的根目录,例如：D:/Soft/autoiqasweb/。在savepath.properties中可以设置。
	 * 
	 */
	public static String getFileSaveDir(String key){
		return  getFileSystemFileName()+properties.getProperty(key);
	}
	
	/**
	 * 获取文件目录保存的绝对路径
	 * @param fileKey 文件key，在savepath.properties中保存着，如：
	 * @return
	 * 如何filekey为：word.picturebooksavedir 则返回 D:/Soft/autoiqasweb/ifilesystem/noumenon/wordresource/picturebooks
	 * 
	 * 返回值是通过调用3次get(key)方法组成，3次调用的key值分别为
	 * 其中D:/Soft/autoiqasweb/ 的key为IQASWEB_FILE_SYSTEM_DIR
	 *            ifilesystem/ 的key为IQASWEB_FILE_SYSTEM_NAME
	 * noumenon/wordresource/picturebooks的key为fileKey
	 * 
	 */
	public static String getFileSaveAbsolutePath(String fileKey){
		return   properties.getProperty(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR)+ getFileSystemFileName()+properties.getProperty(fileKey);
	}
	/**
	 * 获取保存路径的绝对路径
	 * @param savePath 在数据中保存的路径 ifilesystem/noumenon/wordresource/voices/9fc81b51-8907-4acc-ae68-6da7a88924e5.mp3
	 * @return
	 * 返回
	 * D:/Soft/autoiqasweb/ifilesystem/noumenon/wordresource/voices/9fc81b51-8907-4acc-ae68-6da7a88924e5.mp3
	 */
	public static String appendFileSystemDir(String savePath){
		 //1.获取文件系统的根路径:D:/Soft/autoiqasweb/
		String fileSystemRoot = PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR);
		//2.生成文件的绝对路径:D:/Soft/autoiqasweb/ifilesystem/noumenon/wordresource/voices/9fc81b51-8907-4acc-ae68-6da7a88924e5.mp3
		String fileSavePath = fileSystemRoot+savePath;
		return fileSavePath;
	}
}
