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
	 * 
	 * @param offLineBagResource
	 * @param wordResService
	 * @param ismaster  false：为从离线包准备的
	 *  生成的wordElement是为主离线包准备的是还为从离线包准备的
	 * true:为主离线包准备的
	 * false:为从离线包准备的,那么wordElement中只有"propertyScene"、"propertyText"、"video"三个属性有值
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
