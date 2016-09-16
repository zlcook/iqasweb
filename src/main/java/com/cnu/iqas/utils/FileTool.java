package com.cnu.iqas.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author 周亮 
* @version 创建时间：2016年8月22日 下午4:21:12
* 类说明
*/
public class FileTool {
	/**
	 * 保存文件
	 * @param outdir 输出文件绝对根目录 ,如：D://Soft/ifsystem
	 * @param is  输入文件读取流
	 * @param ext 输入文件的后缀名,不带点(.)，比如txt,doc等
	 * @return 文件保存相对路径，相对于outdir的路径,如：,如2016/08/22/17/2212334234.mp3
	 * @throws IOException 
	 */
	public static String saveFile(String outdir,InputStream is,String ext) throws IOException{
		//生成输出文件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//文件名
		String fileName= System.currentTimeMillis()+"."+ext;
		String relPath = sdf.format(new Date());
		String moutdir=outdir+"/"+relPath;
		File outdirfile = new File(moutdir);
		if(!outdirfile.exists())
			outdirfile.mkdirs();
		File file = new File(outdirfile, fileName);
		//传输
		FileOutputStream fos = new FileOutputStream(file);
		write(fos,is);
		
		return relPath+"/"+fileName;
	}
	
	/**
	 * 输出
	 * @param os
	 * @param is
	 * @throws IOException
	 */
	public static  void write(OutputStream os ,InputStream is) throws IOException{
		BufferedInputStream bis =null;
		BufferedOutputStream bos =null;
		try {
			 bis = new BufferedInputStream(is);
			 bos = new BufferedOutputStream(os);
			byte[] buf = new byte[1024*1024];
			while(bis.read(buf)!=-1){
				bos.write(buf);
			}
			bis.close();
			bos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}finally{
			if( bis!=null)
				bis.close();
			if( bos!=null)
				bos.close();
		}
	}
	/**
	 * 将orginal文件中的内容复制到des中
	 * @param orginal
	 * @param des
	 * @throws Exception
	 * 如果orginal或des为null或者是目录则返回异常
	 */
	public static void copy(File orginal,File des) throws Exception{
		FileInputStream fis = null;
		FileOutputStream fos =null;
		try {
			if( orginal==null || orginal.isDirectory())
				throw new RuntimeException("orginal文件有问题");
			if( des==null || des.isDirectory())
				throw new RuntimeException("des文件有问题");
			
			 fis = new FileInputStream(orginal);
			 fos = new FileOutputStream(des);
			 write(fos,fis);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(fis!=null)
				fis.close();
			if( fos!=null)
				fos.close();
		}
		
	}
	
	/**
	 * 从文件名中返回文件后缀名，也可以是带文件名的路径
	 * @param filename 文件名，或者带文件名的的文件路径
	 * @return
	 */
	public static String getExtFromFileName(String filename){
		if( filename==null)
			throw new RuntimeException("文件名为null");
		return filename.substring(filename.lastIndexOf(".")+1);
	}
	/**
	 * 根据当前时间创建相对目录
	 * @return  如：2016/02/06
	 */
	public static String createRelDir() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String relPath = sdf.format(new Date());
		return relPath;
	}
}
