package com.cnu.offline;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.exception.ThemeWordNotExistException;
import com.cnu.offline.service.OffLineAdapter;
import com.cnu.offline.service.OffLineBagService;
import com.cnu.offline.xml.Unit;
import com.cnu.offline.xml.WordNode;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年11月8日 上午10:48:55
* 类说明
* 测试ios、android端离线包的生成下载。
*/
public class OffLineBagTest {

	private OffLineBagService offLineBagService;
	 /**
		 * 离线包文件夹
		 */
	private File offlinebagDir;

	private OffLineAdapter<Unit,WordNode> iosadapter;
	private OffLineAdapter<PropertyEntity,WordElement> androidadapter;
	private static final Logger logger= LogManager.getLogger(OffLineBagTest.class);
	@Before
	public void init(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		offLineBagService=ac.getBean("iosOffLineBagService", OffLineBagService.class);
		iosadapter =ac.getBean("iosOffLineAdapter",OffLineAdapter.class);
		androidadapter =ac.getBean("androidOffLineAdapter",OffLineAdapter.class);
	}
	@Test
	public void ios_createMasterOffLineBag(){
		try {
			  int realGrade=4;
			  int recommendGrade=5;
			 String themenumber="2-12";
			OffLineBag masterbag= offLineBagService.createMasterOfflineBag(realGrade, themenumber,iosadapter);
			System.out.println(masterbag.toString());
		} catch (ThemeWordNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	@Test
	public void ios_createSlaveOffLineBag(){
		try {
			  int realGrade=4;
			  int recommendGrade=5;
			 String themenumber="2-12";
			OffLineBag slavebag= offLineBagService.createSlaveOfflineBag(recommendGrade,realGrade, themenumber,iosadapter);
			System.out.println(slavebag.toString());
		} catch (ThemeWordNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	@Test
	public void android_createMasterOffLineBag(){
		try {
			  int realGrade=4;
			  int recommendGrade=5;
			 String themenumber="2-17";
			OffLineBag masterbag= offLineBagService.createMasterOfflineBag(realGrade, themenumber,androidadapter);
			System.out.println(masterbag.toString());
		} catch (ThemeWordNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	@Test
	public void android_createSlaveOffLineBag(){
		try {
			  int realGrade=4;
			  int recommendGrade=5;
			 String themenumber="2-17";
			OffLineBag slavebag= offLineBagService.createSlaveOfflineBag(recommendGrade,realGrade, themenumber,androidadapter);
			System.out.println(slavebag.toString());
		} catch (ThemeWordNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
