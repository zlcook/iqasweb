package com.cnu.user;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.user.dao.resourcepreference.ResourcePreferenceDao;
import com.user.dao.userresource.UserResourceDao;
import com.user.dao.userword.UserWordDao;
import com.user.entity.UserResource;
import com.user.service.resourcepreference.ResourcePreferenceService;
import com.user.service.rules.Rules;
import com.user.service.userinfo.UserInfoService;
import com.user.service.userresource.UserResourceService;
import com.user.service.userword.UserWordService;

public class Test826 {

	private static UserResourceDao urd;
	private static UserResourceService urs;
	private static ResourcePreferenceDao upd;
	private static ResourcePreferenceService ups;
	private static UserWordDao uwd;
	private static UserWordService uws;
	private static Rules rule;
	@Before
    public void init()
    {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		urd = ac.getBean("userResourceDao",UserResourceDao.class);
		urs = ac.getBean("userResourceService",UserResourceService.class);
		upd = ac.getBean("resourcePreferenceDao",ResourcePreferenceDao.class);
		ups = ac.getBean("resourcePreferenceService",ResourcePreferenceService.class);
		uwd = ac.getBean("userWordDao",UserWordDao.class);
		uws = ac.getBean("userWordService",UserWordService.class);
		rule = ac.getBean("rules",Rules.class);
    }
	/*@Test
	public void userResourceTest()
	{
		String userId = "002";
		String word = "by_bike";
		String resourceId = "imageword";
		int mediaType = 1;
		Date learnStartTime = new Date();
		long let = learnStartTime.getTime() + 15000;
		Date learnEndTime = new Date(let);
		System.out.println(learnStartTime + "||||||||" + learnEndTime);
	//	UserResource nur = urs.findUserResource(userId, word, resourceId, mediaType);
	//	System.out.println(nur.getUserId() + "_" + nur.getWord() + "_" + nur.getResourceId() + "_" + nur.getMediaType() + "_" + nur.getLearnStartTime() + "_" + nur.getLearnEndTime() + "_" + nur.getLearnDuration() + "_" + nur.getLearnCount());
		urs.addUserResource(userId, word, resourceId, mediaType, learnStartTime, learnEndTime);
	}
	@Test
	public void resourcePreferenceTest()
	{
		String userId = "002";
		String feature = "0";
		String featureValue = "0";
		String resourceId = "wordgroup";
		int mediaType = 1;
		System.out.println("推荐度:" + ups.recommendByPreference(userId, resourceId, mediaType));
	//	ups.add(userId, feature, featureValue, 0.5, 0.3, 0.2);
	//	ups.update(userId);
	}*/
	
/*	@Test
	public void userWordTest()
	{
		String userId = "003";
		String word = "trip";
		int topicLevel = 3;
	//	uws.add(userId, word, topicLevel);
		System.out.println("learncount=" + uws.learnCount(userId, word));
	}*/
	@Test
	public void ruleTest()
	{
		String userId = "001";
		Object[] topic1 = new Object[20];
	/*	topic1 = rule.topicRecommendAndroid(userId);
		for(int i = 0; i < 14; i ++)
			System.out.println(topic1[i]);*/
		System.out.println(rule.recommendGrade(userId, 4));
	}
}
