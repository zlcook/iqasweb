package com.cnu.offline.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cnu.iqas.utils.FileTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.offline.MobileStyleEnum;

/**
* @author 周亮 
* @version 创建时间：2016年11月9日 下午9:53:10
* 类说明
*/
public class OffLineBagUntils {
	/**
	 * 
	 *拷贝资源文件到压缩文件路径下
	 * @param offlinebagDir 压缩文件根目录 
	 * @param filedir   相对于压缩文件的中存储文件的目录如： auido
	 * @param relaPath  文件相对路径,比如ifilesystem/wordres/voices/2016/09/03/1472913772722.mp3
	 * @return 返回文件在压缩中的相对位置，相对于压缩包根目录
	 *  比如：auido/1472913772722.mp3
	 * @throws Exception
	 */
	public static String copyfile2offliebag(File offlinebagDir,String filedir,String relaPath) throws Exception{
		
		if( offlinebagDir==null )
			throw new RuntimeException("压缩文件夹为null");
		if( filedir ==null )
			throw new RuntimeException("filedir为null");
		if( relaPath ==null)
			return "";
			//throw new RuntimeException("拷贝文件出错，被拷贝文件路径为null");
		File outDir = new File(offlinebagDir,filedir);
		if( !outDir.exists() ){
			outDir.mkdir();
		}
		//
		String fileName =relaPath.substring(relaPath.lastIndexOf("/")+1);
		//建立接收文件
		File  outfile = new File(outDir,fileName);
		//读取文件  D:/Soft/autoiqasweb/ifilesystem/wordres/voices/2016/09/03/1472913772722.mp3
		String saveFilepath = PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR)+relaPath;
		File infile = new File(saveFilepath);
		
		FileTool.copy(infile, outfile);
		return filedir+"/"+fileName;
	}
	/**
	 * 离线包的名称
	 * @param recommendGrade
	 * @param realGrade
	 * @param themenumber
	 * @return
	 * themenumber+"-"+realGrade+"-"+recommendGrade
	 */
	public static String createOffLineBagName(int recommendGrade, int realGrade, String themenumber,MobileStyleEnum mobile){
		String prex ="";
		if( mobile.equals(MobileStyleEnum.ANDROID))
			prex ="andorid";
		else if( mobile.equals(MobileStyleEnum.IOS))
			prex ="ios";
		String rootName = prex+"-"+themenumber+"-"+realGrade+"-"+recommendGrade;
		return rootName;
	}
	/**
	 * 生成离线包
	 * @param recommendGrade
	 * @param realGrade
	 * @param themenumber
	 * @param mobile
	 * @return
	 */
	public static String createOffLineBagId(int recommendGrade, int realGrade, String themenumber,MobileStyleEnum mobile){
		String idstr=themenumber+realGrade+recommendGrade+mobile.toString();
		return WebUtils.MD5Encode(idstr);
	}
	
}
