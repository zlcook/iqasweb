package com.cnu.iqas.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.service.iword.WordThemeService;
import com.cnu.offline.ThemeWordNotExistException;
import com.cnu.offline.service.OfflineService;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.OntologyManage.OntologyManage;

/**
* @author 周亮 
* @version 创建时间：2016年7月10日 下午5:17:41
* 类说明
*/
//@ContextConfiguration(locations = {"/applicationContext.xml"})//启动Spring容器
public class OfflineServiceTest {

	@Autowired //注入Spring容器中的Bean
	private  OfflineService offlineService;
	@Autowired 
	private OntologyManage ontologyManage ;
	private WordThemeService wordThemeService;
	@Before
	public void init(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		offlineService=ac.getBean(OfflineService.class);
		wordThemeService = ac.getBean(WordThemeService.class);
	}
	
	@Test
	public void create(){
		String th = "17.旅游与交通-（58）交通运输方式";
		String number="2-17-58";
		WordTheme theme =wordThemeService.findByNumber(number);
		theme.setContent(th);
		offlineService.createByThme(theme);
	}
	
	@Test
	public void crateOfflineBag4Android() throws ThemeWordNotExistException{
		offlineService.createOfflineBag(4, 4, "2-17");
	}

	public OfflineService getOfflineService() {
		return offlineService;
	}
	public void setOfflineService(OfflineService offlineService) {
		this.offlineService = offlineService;
	}
	public OntologyManage getOntologyManage() {
		return ontologyManage;
	}
	public void setOntologyManage(OntologyManage ontologyManage) {
		this.ontologyManage = ontologyManage;
	}
}
