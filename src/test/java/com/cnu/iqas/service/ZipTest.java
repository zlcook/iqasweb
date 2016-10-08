package com.cnu.iqas.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.junit.Test;

/**
* @author 周亮 
* @version 创建时间：2016年9月20日 上午10:25:28
* 类说明
*/
public class ZipTest {

	public static int BUFF_SIZE =1024*1024;
	
	
	@Test
	public void test(){
		
		String str  ="2-1-1\\audio\\1243242.mp3";
		
		System.out.println(str);
		
		String rep=str.replace("\\", "/");
		
		System.out.println(rep);
	}
	
	@Test
	public void UnZipTest()
	{
		File zipFile = new File("D:/Soft/autoiqasweb/ifilesystem/noumenon/theme/offlinebag/2016/09/19/2-17-4-3.zip");
		String folderPath = "I:/test";
		try {
			upZipFile(zipFile,folderPath);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	   * 解压缩一个文件
	   *
	   * @param zipFile 压缩文件
	   * @param folderPath 解压缩的目标目录
	   * @throws IOException 当解压缩过程出错时抛出
	   */
	  public static void upZipFile(File zipFile, String folderPath) throws ZipException, IOException {
	    File desDir = new File(folderPath);
	    if (!desDir.exists()) {
	      desDir.mkdirs();
	    }
	    ZipFile zf = new ZipFile(zipFile);
	    for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
	      ZipEntry entry = ((ZipEntry)entries.nextElement());
	      InputStream in = zf.getInputStream(entry);
	      String pathName =entry.getName();
	      pathName.replace("\\", "/");
	      
	      String str = folderPath + File.separator + entry.getName();
	      System.out.println(str);
	      str = new String(str.getBytes("8859_1"), "GB2312");
	      File desFile = new File(str);
	      if (!desFile.exists()) {
	        File fileParentDir = desFile.getParentFile();
	        if (!fileParentDir.exists()) {
	          fileParentDir.mkdirs();
	        }
	        desFile.createNewFile();
	      }
	      OutputStream out = new FileOutputStream(desFile);
	      byte buffer[] = new byte[BUFF_SIZE];
	      int realLength;
	      while ((realLength = in.read(buffer)) > 0) {
	        out.write(buffer, 0, realLength);
	      }
	      in.close();
	      out.close();
	    }

	  }
}
