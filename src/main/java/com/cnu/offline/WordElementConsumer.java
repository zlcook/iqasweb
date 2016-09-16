package com.cnu.offline;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
* @author 周亮 
* @version 创建时间：2016年9月9日 下午2:24:26
* 类说明
*/
public class WordElementConsumer implements Runnable {

	private static final Logger logger= LogManager.getLogger(WordElementConsumer.class);

	//和WordElementProducer消费者共享的资源
	private OffLineBagResource  offLineBagResouce;


	/**
	 * 离线包文件夹
	 */
	private File offlinebagDir;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
