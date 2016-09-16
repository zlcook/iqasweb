package com.user.service.usertest.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.OntologyManage.Impl.OntologyManageImpl;
import com.user.dao.candidatetest.CandidateTestDao;
import com.user.dao.candidatetestwords.CandidateTestWordsDao;
import com.user.dao.testpreference.TestPreferenceDao;
import com.user.dao.userinfo.UserInfoDao;
import com.user.dao.usertest.UserTestDao;
import com.user.entity.CandidateTest;
import com.user.entity.CandidateTestWords;
import com.user.entity.UserInfo;
import com.user.entity.UserTest;
import com.user.service.candidatetest.CandidateTestService;
import com.user.service.candidatetest.impl.CandidateTestServiceImpl;
import com.user.service.testpreference.TestPreferenceService;
import com.user.service.usertest.UserTestService;
@Service("userTestService")
public class UserTestServiceImpl implements UserTestService {
    private UserTestDao userTestDao;
    private CandidateTestWordsDao candidateTestWordsDao;
    private CandidateTestDao candidateTestDao;
	private CandidateTestService candidateTestService;
	private TestPreferenceDao testPreferenceDao;
	private TestPreferenceService testPreferenceService;
	private OntologyManage ontologyManage;
	
	public OntologyManage getOntologyManage() {
		return ontologyManage;
	}
	@Resource
	public void setOntologyManage(OntologyManage ontologyManage) {
		this.ontologyManage = ontologyManage;
	}
	public TestPreferenceService getTestPreferenceService() {
		return testPreferenceService;
	}
	@Resource
	public void setTestPreferenceService(TestPreferenceService testPreferenceService) {
		this.testPreferenceService = testPreferenceService;
	}
	private UserInfoDao userInfoDao;
	public CandidateTestWordsDao getCandidateTestWordsDao() {
		return candidateTestWordsDao;
	}
	@Resource
	public void setCandidateTestWordsDao(CandidateTestWordsDao candidateTestWordsDao) {
		this.candidateTestWordsDao = candidateTestWordsDao;
	}
	public UserTestDao getUserTestDao() {
		return userTestDao;
	}
	@Resource
	public void setUserTestDao(UserTestDao userTestDao) {
		this.userTestDao = userTestDao;
	}

	
	
	@Override
	public void addUserTest(UserTest ut) {
		// TODO Auto-generated method stub
		userTestDao.save(ut);	
	//	CandidateTestService cts = new CandidateTestServiceImpl();
		if(ut.getWrongTimes() > 0)
			{
			   CandidateTestWords ctw = new CandidateTestWords(ut.getUserId(),ut.getWord(),2);
			   if(candidateTestWordsDao.find(ctw.getUserId(),ctw.getWord()) == null)
			      candidateTestWordsDao.save(ctw);
			}
		if(ut.getTotalTimes() > 0)
		{
			//更新测试类型答题情况
			candidateTestService.UpdateCandidateTestCount(ut.getUserId(), ut.getTestType(), ut.getTestAspect(), ut.getTestDifficulty(), ut.getTotalTimes());
			//更新测试类型升降级
			candidateTestService.UpdateCandidateStatus(ut.getUserId(),ut.getTestType(), ut.getTestAspect(), ut.getTestDifficulty());
		}
	}
	
	@Override
	public void addUserTest1(String userId, String word, int testType, int testAspect, int testDifficulty,
			int rightTimes, int wrongTimes, int totalTimes, Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		userTestDao.save(new UserTest(userId,word,testType,testAspect,testDifficulty,rightTimes,wrongTimes,totalTimes,startTime,endTime));
		if(wrongTimes > 0)
		{
		   int wordd = 2;
		   ResultSet resultsInstance = ontologyManage.QueryAWordAllId(word);
		   if (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// 移动到下个result上
					QuerySolution solutionInstance = resultsInstance.next();

					// 找出该单词的对应的所有ID
					ResultSet resultsAllPropertyOfThisId = ontologyManage
							.QueryIndividualDependOnId(solutionInstance.get(
									"?propertyID").toString());
					QuerySolution solutionAllPropertyOfThisId = resultsAllPropertyOfThisId
							.next();
					// 打印出每个ID对应的句子及其属性
					System.out.println("实例：" + word + "\n");
					wordd=Integer.parseInt(subStringManage(solutionAllPropertyOfThisId.get("?propertyDifficulty").toString()));
				    System.out.println("难度为：" + wordd);	
			} else {
				System.out.println("知识本体库中没有此实例");
			}
		   CandidateTestWords ctw = new CandidateTestWords(userId,word,wordd);
		   if(candidateTestWordsDao.find(ctw.getUserId(),ctw.getWord()) == null)
		      candidateTestWordsDao.save(ctw);
		}
	if(totalTimes > 0)
	{
		//更新测试类型答题情况
		candidateTestService.UpdateCandidateTestCount(userId, testType, testAspect, testDifficulty, totalTimes);
		//更新测试类型升降级
		candidateTestService.UpdateCandidateStatus(userId,testType, testAspect, testDifficulty);
	}
	}
	// 处理字符串
		private static String subStringManage(String string) {
			String newString = string.substring(string.indexOf(")") + 1,
					string.lastIndexOf("@"));
			return newString;
		}
	@Override
	public UserTest TestFourGroup(String userId, String[] words, int index) {
		// TODO Auto-generated method stub
		//如果是获取第2，3,4道题目，则直接去用户测试表中获取
		for(int i = 0; i < 5; i ++)
			System.out.println("要考的单词：" + words[i]);
		
		if(index > 1)
		{
			List<UserTest> list = userTestDao.find(userId, 0);
			if(list != null && list.size() >= 5 - index)
			{
				userTestDao.delete(list.get(0).getId());  //拿走一道题目就删掉出题记录
			    return list.get(list.size() - (5-index));
			}
			else return null;
		}
		List<CandidateTest> ctlist = candidateTestDao.getCandidate(userId);
		//初始化难度为2的备选测试类型
		if(ctlist == null) 
			{
			   candidateTestService.AddBatchCandidateTest(userId);
			   ctlist = candidateTestDao.getCandidate(userId);
			}
		double[] Recommend = new double[5];   //5个备选测试类型的擅长度
		int max = 0;
		int min = 0;
		int i = 0;
        for(CandidateTest ct : ctlist)
        {
       // 	Recommend[i] = testPreferenceDao.find(userId, "testType", ct.getTestType()).getpFeedback1() * testPreferenceDao.find(userId, "testAspect", ct.getTestAspect()).getpFeedback1() * testPreferenceDao.find(userId, "testDifficulty", ct.getTestDifficulty()).getpFeedback1() * 2;
       //     Recommend[i] += testPreferenceDao.find(userId, "testType", ct.getTestType()).getpFeedback2() * testPreferenceDao.find(userId, "testAspect", ct.getTestAspect()).getpFeedback2() * testPreferenceDao.find(userId, "testDifficulty", ct.getTestDifficulty()).getpFeedback2();
       //     Recommend[i] -= testPreferenceDao.find(userId, "testType", ct.getTestType()).getpFeedback3() * testPreferenceDao.find(userId, "testAspect", ct.getTestAspect()).getpFeedback3() * testPreferenceDao.find(userId, "testDifficulty", ct.getTestDifficulty()).getpFeedback3();
            Recommend[i] = testPreferenceService.recommendByPreference(ct.getUserId(), ct.getTestType(), ct.getTestAspect(), ct.getTestDifficulty());
            System.out.println(ct.getTestType() + "/" + ct.getTestAspect() + "/" + ct.getTestDifficulty() + "的推荐度为：" + Recommend[i]);
            if(i > 0 && Recommend[max] < Recommend[i])
                max = i;
            if(i > 0 && Recommend[min] > Recommend[i])
            	min = i;
        }
        Object[][] fourGroup = new Object[4][4];  //存储四道题目
        if(max == min)
        	min = (max + 1) % 5;
        int[] testindex = new int[4];
        testindex[0] = max;
        testindex[1] = min;
	    int tindex = 0;  //随机去掉一个备选测试类型    
	    while(tindex == max || tindex == min)
	       tindex = new Random().nextInt(5);
	    int j = 2;
        for(i = 0; i < 5; i ++)
        	if(i != max && i != min && i != tindex && j < 4)
        		   testindex[j++] = i;
        for(i = 0; i < 4; i ++)
        {
        	fourGroup[i][1] = ctlist.get(testindex[i]).getTestType();
        	fourGroup[i][2] = ctlist.get(testindex[i]).getTestAspect();
        	fourGroup[i][3] = ctlist.get(testindex[i]).getTestDifficulty();
        }
        //从场景五个单词中随机抽取两个单词
        for(i = 0 ; i < 2; i ++)
        {
        	int testword = new Random().nextInt(5);
        	while(words[testword] == null)	  //当随机选中的单词下标为已选过的单词时接着随机选择
        		testword = new Random().nextInt(5);
        	fourGroup[i][0] = words[testword];
        	System.out.println("随机抽中的单词有：" + fourGroup[i][0]);
        	words[testword] = null;
        }
        //从备考单词中抽取两个单词
        UserInfo ui = userInfoDao.find(userId);
        int[] wd = new int[2];
        if(ui.getBeforeLevel() <= 2) 
        {wd[0] = 2; wd[1] = 2;}
        else if(ui.getBeforeLevel() <= 4)
        {wd[0] = 2; wd[1] = 5;}
        else {wd[0] = 5; wd[1] = 5;}
        System.out.println(wd[0] + "/" + wd[1]);
        CandidateTestWords ctw1 = candidateTestWordsDao.find(userId, wd[0]);
        if(ctw1 != null)
            fourGroup[2][0] = ctw1.getWord();
        else fourGroup[2][0] = "extraword1";
        CandidateTestWords ctw2 = candidateTestWordsDao.find(userId, wd[1]);
        if(ctw2 != null)
            fourGroup[3][0] = ctw2.getWord();
        else fourGroup[3][0] = "extraword2";
        
        //写入用户测试表中
        for(i = 0; i < 4; i ++)
        	userTestDao.save(new UserTest(userId,(String)fourGroup[i][0],(int)fourGroup[i][1],(int)fourGroup[i][2],(int)fourGroup[i][3],0,0,0,new Date(),null));
        List<UserTest> utlist = userTestDao.find(userId, 0);
        userTestDao.delete(utlist.get(utlist.size() - 4).getId());  //返回一道题目就删掉出题记录
        return utlist.get(0);
	}
	public CandidateTestService getCandidateTestService() {
		return candidateTestService;
	}
	@Resource
	public void setCandidateTestService(CandidateTestService candidateTestService) {
		this.candidateTestService = candidateTestService;
	}
	public TestPreferenceDao getTestPreferenceDao() {
		return testPreferenceDao;
	}
	@Resource
	public void setTestPreferenceDao(TestPreferenceDao testPreferenceDao) {
		this.testPreferenceDao = testPreferenceDao;
	}
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	@Resource
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	public CandidateTestDao getCandidateTestDao() {
		return candidateTestDao;
	}
	@Resource
	public void setCandidateTestDao(CandidateTestDao candidateTestDao) {
		this.candidateTestDao = candidateTestDao;
	}
	

}
