package com.cnu.offline.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
* @author 周亮 
* @version 创建时间：2016年11月6日 下午7:24:19
* 类说明
* 转换任务类，将数据库中查询到的单词类转换成xml数据对象
*/
public class ConvertTask implements Runnable {
	private final Logger logger= LogManager.getLogger(ConvertTask.class);
	
	private OffLineBagResource offLineBagResource;
	private OffLineAdapter adapter;
	
	/**
	 * 将资源文件转换成离线包
	 * @param offLineBagResource  要转换的离线资源
	 * @param adapter  转换适配器
	 */
	public ConvertTask(OffLineBagResource offLineBagResource,OffLineAdapter adapter) {
		super();
		this.adapter = adapter;
		this.offLineBagResource = offLineBagResource;
	}

	@Override
	public void run() {
		adapter.createOffLinePack(offLineBagResource, offLineBagResource.isIsmasterPack());
		
	}
	
}
