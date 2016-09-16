package com.user.service.testpreference.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.testpreference.TestPreferenceDao;
import com.user.dao.usertest.UserTestDao;
import com.user.entity.TestPreference;
import com.user.entity.UserTest;
import com.user.service.testpreference.TestPreferenceService;
import com.user.service.usertest.UserTestService;
@Service("testPreferenceService")
public class TestPreferenceServiceImpl implements TestPreferenceService {

	private TestPreferenceDao testPreferenceDao;
	private UserTestService userTestService;
	private UserTestDao userTestDao;
	
	public TestPreferenceDao getTestPreferenceDao() {
		return testPreferenceDao;
	}
	@Resource
	public void setTestPreferenceDao(TestPreferenceDao testPreferenceDao) {
		this.testPreferenceDao = testPreferenceDao;
	}

	public UserTestService getUserTestService() {
		return userTestService;
	}
	@Resource
	public void setUserTestService(UserTestService userTestService) {
		this.userTestService = userTestService;
	}
	
	@Override
	public void updateTestPreference(String userId) {
		// TODO Auto-generated method stub
     //获取当前用户的所有测试数据list
	 List<UserTest> testlist = userTestDao.find(userId, 1);
	 //统计不同题型/考查方面/难度 不同反馈类别的个数
	 int[][] typecount = new int[4][3];
	 int[][] aspectcount = new int[4][3];
	 int[][] difficultycount = new int[4][3];
	 int[] feedbackcount = new int[3];
	 //存储不同题型/考查方面/难度 不同反馈类别的概率
	 double[][] Ftype = new double[4][3];
	 double[][] Faspect = new double[4][3];
	 double[][] Fdifficulty = new double[3][3];
	 double[] Ffeedback = new double[3];
		//遍历进行统计	
	 if(testlist != null && testlist.size() >3)
	 { 
		 for(UserTest ut : testlist)
	      {
			 int f = ut.getTotalTimes();
			 if(ut.getTotalTimes() >= 3) 
	              f = 3;
			 typecount[ut.getTestType()-1][f-1] ++;
	         aspectcount[ut.getTestAspect()-1][f-1] ++;
			 difficultycount[ut.getTestDifficulty()-1][f-1] ++;
			 feedbackcount[f-1] ++;
	      }
		 int count = testlist.size();
		int i,j;
		//确保不出现0
		for(i = 0; i < 4; i ++)
		{
			for(j = 0; j < 3; j ++)
			{
				if(typecount[i][j] == 0){typecount[i][j] ++; count ++;}
				if(aspectcount[i][j] == 0 ){aspectcount[i][j] ++; count ++;}
				if(i < 3 && difficultycount[i][j] == 0){difficultycount[i][j] ++; count ++;}
				System.out.println(typecount[i][j] + "/////" + aspectcount[i][j]);
			}
			if(i < 3 && feedbackcount[i] == 0)
			{feedbackcount[i] ++; count ++;}
		}
		//计算概率向量
		for(i = 0; i < 3; i ++)
			Ffeedback[i] = ((double)feedbackcount[i])/testlist.size();
		for(i = 0; i < 4; i ++)
			for(j = 0; j < 3; j ++)
			{
				Ftype[i][j] = typecount[i][j]*1.0/feedbackcount[j];
				Faspect[i][j] = aspectcount[i][j]*1.0/feedbackcount[j];
				if(i < 3) Fdifficulty[i][j] = difficultycount[i][j]*1.0/feedbackcount[j];
			}
	 }
	 
	 System.out.println("概率向量计算完毕！");
	 TestPreference tp = testPreferenceDao.find(userId,null,0);
		 if(tp != null)
		 {
			 tp.setpFeedback1(Ffeedback[0]);
			 tp.setpFeedback2(Ffeedback[1]);
			 tp.setpFeedback3(Ffeedback[2]);
			 testPreferenceDao.update(tp);
		 }
		 else 
			 testPreferenceDao.save(new TestPreference(userId,"0",0,Ffeedback[0],Ffeedback[1],Ffeedback[2]));
	 for(int i = 0; i < 4; i ++)
	 {
		 int t = i + 1;
		 TestPreference tp1 = testPreferenceDao.find(userId, "testType", t);
		 if(tp1 != null)
		 {
			 tp1.setpFeedback1(Ftype[i][0]);
			 tp1.setpFeedback2(Ftype[i][1]);
			 tp1.setpFeedback3(Ftype[i][2]);
			 testPreferenceDao.update(tp1);
		 }
		 else
			 testPreferenceDao.save(new TestPreference(userId,"testType",t,Ftype[i][0],Ftype[i][1],Ftype[i][2]));
		 TestPreference tp2 = testPreferenceDao.find(userId, "testAspect", t);
		 if(tp2 != null)
		 {
			 tp2.setpFeedback1(Faspect[i][0]);
			 tp2.setpFeedback2(Faspect[i][1]);
			 tp2.setpFeedback3(Faspect[i][2]);
			 testPreferenceDao.update(tp2);
		 }
		 else
			 testPreferenceDao.save(new TestPreference(userId,"testAspect",t,Faspect[i][0],Faspect[i][1],Faspect[i][2]));
		 if(i < 3)
		 {
			 TestPreference tp3 = testPreferenceDao.find(userId, "testDifficulty", t);
			 if(tp3 != null)
			 {
				 tp3.setpFeedback1(Fdifficulty[i][0]);
				 tp3.setpFeedback2(Fdifficulty[i][1]);
				 tp3.setpFeedback3(Fdifficulty[i][2]);
				 testPreferenceDao.update(tp3);
			 }
			 else
				 testPreferenceDao.save(new TestPreference(userId,"testDifficulty",t,Fdifficulty[i][0],Fdifficulty[i][1],Fdifficulty[i][2])); 
		 }
	 }
	 
	}
	
	@Override
	public void addTestPreference(String userId) {
		// TODO Auto-generated method stub
        int i;
        for(i = 1; i <= 4;i ++)
        {
        	//题型特征:4个取值
        	if(testPreferenceDao.find(userId, "testType", i) == null)
        	{
        		TestPreference newtp = new TestPreference(userId,"testType",i,0,0,0);
        		testPreferenceDao.save(newtp);	
        		System.out.println("添加成功！");
        	}
        	else
        		System.out.println("该用户的该特征下取该值的记录已存在！");
        	//考查方面特征:4个取值
        	if(testPreferenceDao.find(userId, "testAspect", i) == null)
        	{
        		TestPreference newtp = new TestPreference(userId,"testAspect",i,0,0,0);
        		testPreferenceDao.save(newtp);
        		System.out.println("添加成功！");
        	}
        	else
        		System.out.println("该用户的该特征下取该值的记录已存在！");
        	//难度特征:3个取值
        	if(i <= 3)
        	{
        		if(testPreferenceDao.find(userId, "testDifficulty", i) == null)
            	{
            		TestPreference newtp = new TestPreference(userId,"testDifficulty",i,0,0,0);
            		testPreferenceDao.save(newtp);	
            		System.out.println("添加成功！");
            	}
            	else
            		System.out.println("该用户的该特征下取该值的记录已存在！");
        	}
        }    	
	}

	@Override
	public double recommendByPreference(String userId, int testType, int testAspect, int testDifficulty) {
		// TODO Auto-generated method stub
		TestPreference tp = testPreferenceDao.find(userId, "0", 0);
		TestPreference type = testPreferenceDao.find(userId, "testType", testType);
		TestPreference aspect = testPreferenceDao.find(userId, "testAspect", testAspect);
		TestPreference difficulty = testPreferenceDao.find(userId, "testDifficulty", testDifficulty);
		if(tp != null && type != null && aspect != null && difficulty != null)
		{
		    return (tp.getpFeedback1()*type.getpFeedback1()*aspect.getpFeedback1()*difficulty.getpFeedback1())*2 + (tp.getpFeedback2()*type.getpFeedback2()*aspect.getpFeedback2()*difficulty.getpFeedback2()) - (tp.getpFeedback3()*type.getpFeedback3()*aspect.getpFeedback3()*difficulty.getpFeedback3());
		}
		return 0;
	}
	public UserTestDao getUserTestDao() {
		return userTestDao;
	}
	@Resource
	public void setUserTestDao(UserTestDao userTestDao) {
		this.userTestDao = userTestDao;
	}

}
