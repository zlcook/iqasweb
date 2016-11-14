package com.cnu.offline.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Java utils 实现的Zip工具
 * zip压缩工具
 * 
 * zip压缩方法
 * unzip解压方法
 * @author
 */
public class ZipUtils {
	private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
	/**
	 * 压缩文件或者文件夹
	 * 
	 * @param sourcePath
	 *            原文件路径（将要压缩的文件）
	 * @param outPath
	 *            输出文件的路径
	 * @throws IOException
	 *             eg: sourcePath = "D:\\temp\\文件" outPaht
	 *             ="D:\\tem\\zip\\文件.zip"
	 */
	public static void zip(String sourcePath, String outPath) throws IOException {
		// 文件输出流
		FileOutputStream fout = new FileOutputStream(outPath);
		// zip格式的输出流
		ZipOutputStream zout = new ZipOutputStream(fout);
		// 要压缩的文件或者目录
		File sourceFile = new File(sourcePath);
		// 压缩条目
		String zipEntryName = sourceFile.getName();
		if (sourceFile.isFile()) {
			// 压缩文件
			zipFile(zout, sourceFile, zipEntryName);
		} else {
			// 压缩目录
			zipDirectory(zout, sourceFile, zipEntryName);
		}
		zout.close();
		fout.close();

	}

	/**
	 * 压缩目录
	 * 
	 * @param zout
	 *            zip格式的文件输出流
	 * @param sourceFile
	 *            压缩目录
	 * @param zipEntryName
	 *            压缩条目（实际是一个相对目录）
	 * @throws IOException
	 */
	private static void zipDirectory(ZipOutputStream zout, File sourceFile, String zipEntryName) throws IOException {
		// 压缩目录，遍历目录里的所有文件
		for (File file : sourceFile.listFiles()) {
			if (file.isFile()) {
				// 如果是文件，则直接压缩
				zipFile(zout, file, zipEntryName + "/" + file.getName());
			} else {
				// 说明file是目录，则将需要将该目录所有文件都压缩
				if (file.listFiles().length > 0) {// 非空文件夹
					// 递归调用压缩文件方法
					zipDirectory(zout, file, zipEntryName + "/" + file.getName());
				} else {
					// 空文件夹，将压缩条目写入到压缩对象中
					zout.putNextEntry(new ZipEntry(zipEntryName + "/" + file.getName() + "/"));
					zout.closeEntry();
				}
			}
		}
	}

	/**
	 * zip压缩文件
	 * 
	 * @param zout
	 *            zip格式的文件输出流
	 * @param sourceFile
	 *            源文件路径(将要压缩的文件)
	 * @param zipEntryName
	 *            压缩条目（实际是一个相对目录）
	 * @throws IOException
	 */
	private static void zipFile(ZipOutputStream zout, File sourceFile, String zipEntryName) throws IOException {
		// 将一个将要压缩的文件写入到压缩条目中
		zout.putNextEntry(new ZipEntry(zipEntryName));
		// 读入将要压缩的文件
		FileInputStream fin = new FileInputStream(sourceFile);
		byte[] buff = new byte[BUFF_SIZE];
		int length;
		while ((length = fin.read(buff)) > 0) {
			zout.write(buff, 0, length);
		}
		fin.close();
		zout.closeEntry();
	}

	/**
	 * 解压缩一个文件
	 *
	 * @param zipFile
	 *            压缩文件
	 * @param folderPath
	 *            解压缩的目标目录
	 * @throws IOException
	 *             当解压缩过程出错时抛出
	 */
	public static void upZipFile(File zipFile, String folderPath) throws ZipException, IOException {

		// 解压文件存放根目录
		File desDir = new File(folderPath);
		if (!desDir.exists()) {
			desDir.mkdirs();
		}
		// 文件读取流
		FileInputStream fis = new FileInputStream(zipFile);
		// 压缩读取流
		ZipInputStream zis = new ZipInputStream(fis);
		ZipEntry zipEntry = null;
		// 遍历压缩条目
		int count = 0;
		while ((zipEntry = zis.getNextEntry()) != null) {
			count++;
			String zipName = zipEntry.getName();
			// 压缩条目输出路径
			File targetFile = new File(folderPath + File.separator + zipEntry.getName());
			// 判断父目录是否存在
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			// 压缩条目是目录还是文件
			if (zipEntry.isDirectory()) {
				// 创建目录
				targetFile.mkdirs();
			} else {
				// 输出文件
				FileOutputStream fos = new FileOutputStream(targetFile);
				byte buffer[] = new byte[BUFF_SIZE];
				int realLength;
				while ((realLength = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, realLength);
				}
				fos.close();
			}
		}
		zis.close();
		fis.close();
	}

}
