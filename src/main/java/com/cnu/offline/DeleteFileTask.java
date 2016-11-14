package com.cnu.offline;

import java.io.File;

/**
* @author 周亮 
* @version 创建时间：2016年9月12日 上午11:11:00
* 类说明:离线包生成好后，用于删除压缩前的文件夹及其中的文件
*/
public class DeleteFileTask implements Runnable{
	/**
	 * 要删除的文件或者文件夹
	 */
	private File file;

	
	public DeleteFileTask(File file) {
		super();
		this.file = file;
	}


	@Override
	public void run() {
		deleteFile(file);
	}
	
	private void deleteFile(File file){
		if(file!=null){
			if( file.isDirectory()){
				File[] list= file.listFiles();
				if( list!=null && list.length>0){
					for( File fl:list){
						deleteFile(fl);
					}
					file.delete();
				}else{
					file.delete();
				}
			}else{
				file.delete();
			}
		}
	}
}
