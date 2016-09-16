package com.user.service.rules.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.candidatetest.CandidateTestDao;
import com.user.dao.passinfo.PassInfoDao;
import com.user.dao.userinfo.UserInfoDao;
import com.user.dao.userlogin.UserLoginDao;
import com.user.dao.userresource.UserResourceDao;
import com.user.dao.userword.UserWordDao;
import com.user.entity.CandidateTest;
import com.user.entity.PassInfo;
import com.user.entity.UserInfo;
import com.user.entity.UserLogin;
import com.user.entity.UserResource;
import com.user.entity.UserWord;
import com.user.service.candidatetest.CandidateTestService;
import com.user.service.resourcepreference.ResourcePreferenceService;
import com.user.service.rules.Rules;
import com.user.service.userinfo.UserInfoService;
import com.user.service.userword.UserWordService;
@Service("rules")
public class RulesImpl implements Rules {
    private UserLoginDao userLoginDao;
	private UserInfoService userInfoService;
    private UserWordService userWordService;
    private UserWordDao userWordDao;
    private UserResourceDao userResourceDao;
    private UserInfoDao userInfoDao;
    private CandidateTestService candidateTestService;
    private CandidateTestDao candidateTestDao;
    private PassInfoDao passInfoDao;
    private ResourcePreferenceService resourcePreferenceService;




	public ResourcePreferenceService getResourcePreferenceService() {
		return resourcePreferenceService;
	}


    @Resource
	public void setResourcePreferenceService(ResourcePreferenceService resourcePreferenceService) {
		this.resourcePreferenceService = resourcePreferenceService;
	}



	@Override
	public Object[] topicRecommendIOS(String userId) {
		// TODO Auto-generated method stub
		UserInfo ui = userInfoService.find(userId);
	//	System.out.println("userId=" + userId + ",englishScore=" + ui.getEnglishScore());
     Object[] topic = new Object[2];
	 List<UserLogin> loginlist = new ArrayList<UserLogin>();
	 loginlist = userLoginDao.find(userId);
	 if(loginlist == null)  topic[0] = 0;
	 if(loginlist != null)
		 topic[0] = loginlist.size();
	 System.out.println("logincount=" + topic[0]);
	 //登录次数<5则按英语成绩推荐
	 if(loginlist == null || loginlist.size() < 5)
	 {
		 
		 int englishScore = ui.getEnglishScore();
		 int level = 0;
		 if(englishScore < 60) level = 0;
		 else if(englishScore < 70) level = 1;
		 else if(englishScore < 80) level = 2;
		 else if(englishScore < 90) level = 3;
		 else level = 4;
		 System.out.println("level=" + level);
		 String[] topicname = new String[5];
		 //自己假设的
		 topicname[0] = "topic1";
		 topicname[1] = "topic2";
		 topicname[2] = "topic3";
		 topicname[3] = "topic4";
		 topicname[4] = "topic5";
		 topic[1] = topicname[level];
	 }
	 else  //登录次数>=5次
	 {
		 int i;
		 String[] topicname = new String[13]; //13个主题 需要赋值！！！
		 topicname[0] = "topic1";
		 topicname[1] = "topic2";
		 topicname[2] = "topic3";
		 topicname[3] = "topic4";
		 topicname[4] = "topic5";
		 topicname[5] = "topic6";
		 topicname[6] = "topic7";
		 topicname[7] = "topic8";
		 topicname[8] = "topic9";
		 topicname[9] = "topic10";
		 topicname[10] = "topic11";
		 topicname[11] = "topic12";
		 topicname[12] = "topic13";

		 int[] usertopiccount = new int[13];  //当前用户点击13个主题的次数
		 int[] topiccount = new int[13];  //当前13个主题被点击的次数
		 double[] topicrecommend = new double[13];
		 double w1,w2;  //设置两个权重
		 w1 = 0.7;
		 w2 = 0.3;
		 int max = 0;
		 for(i = 0; i < 13; i ++)//循环13个主题
		 {
			 //通过循环去完成13个主题次数的查找
			 List<UserWord> userlist = new ArrayList<UserWord>();
			 userlist = userWordDao.findAll(userId, topicname[i], 1);
			 if(userlist != null)
				 usertopiccount[i] = userlist.size();
			 else usertopiccount[i] = 0;
		//	 for(int j = 0; j < userlist.size(); j ++)
			//	 usertopiccount[i] += userlist.get(j).getWordLearn();
			 List<UserWord> wordlist = new ArrayList<UserWord>();
			 wordlist = userWordDao.findlearncount(topicname[i], 1);
			 if(wordlist != null)
				 topiccount[i] = wordlist.size();
			 else topiccount[i] = 0;
			 //并去做推荐度计算
			 topicrecommend[i] = usertopiccount[i] * w1 + topiccount[i] * w2;
			 System.out.println("第" + i + "个主题的推荐度为：" + topicrecommend[i] + "|||" + usertopiccount[i] + "/" + topiccount[i]);
			 if(i > 0 && topicrecommend[max] < topicrecommend[i])
				 max = i;
		 }
		 topic[1] = topicname[max];
	 }
		return topic;
	}



	@Override
	public Object[] secondTopicRecommend(String userId, String topic) {
		// TODO Auto-generated method stub
		Object[] secondtopic = new Object[30];
		String[] topicname = new String[30];
		//去word表中获取当前一级主题下有哪些二级主题放到topicname中
		//......自己假设的当前1级主题下有如下5个二级主题
		topicname[0] = "topic11";
		topicname[1] = "topic12";
		topicname[2] = "topic13";
		topicname[3] = "topic14";
		topicname[4] = "topic15";
		
		 List<UserLogin> loginlist = userLoginDao.find(userId);
		 if(loginlist == null)  secondtopic[0] = 0;
		 if(loginlist != null)
			 secondtopic[0] = loginlist.size();
		 int count = 0;
		 //登录次数<5则按英语成绩推荐
		 if(loginlist == null || loginlist.size() < 5)
		 {
		     //什么也不做按序返回
			 System.out.println("登录小于5次");
		 }
		 else  //登录次数>=5次
		 {
			 int i; 
			 int[] usertopiccount = new int[30];  //当前用户点击这n个二级主题的次数
			 int[] topiccount = new int[30];  //当前13个主题被点击的次数
			 double[] topicrecommend = new double[30];
			 double w1,w2;  //设置两个权重
			 w1 = 0.7;
			 w2 = 0.3;
			 int max = 0;
			 for(i = 0; i < 30 && topicname[i] != null; i ++)//循环n个二级主题
			 {
				 //通过循环去完成n个二级主题次数的查找
				 List<UserWord> userlist = userWordDao.findAll(userId, topicname[i], 2);
				 if(userlist != null)
					 usertopiccount[i] = userlist.size();
				 else usertopiccount[i] = 0;
				// for(int j = 0; j < userlist.size(); j ++)
				//	 usertopiccount[i] += userlist.get(j).getWordLearn();
				 List<UserWord> wordlist = userWordDao.findlearncount(topicname[i], 2);
				 if(wordlist != null)
					 topiccount[i] = wordlist.size();
				 else topiccount[i] = 0;
				 //并去做推荐度计算
				 topicrecommend[i] = usertopiccount[i] * w1 + topiccount[i] * w2;
				 System.out.println(topicname[i] + "：-------------" + usertopiccount[i] + "/" + topiccount[i] + "/" + topicrecommend[i]);
				 count ++;
			 }
			 for(i = 0; i < count; i ++)
				 for(int j = i; j < count; j ++)
					 if(topicrecommend[i] < topicrecommend[j])
					 {
						 double temp;
						 String tempstr;
						 temp = topicrecommend[i];
						 topicrecommend[i] = topicrecommend[j];
						 topicrecommend[j] = temp;
						 tempstr = topicname[i];
						 topicname[i] = topicname[j];
						 topicname[j] = tempstr;
					 }
		 }
		 for(int i = 0; i < count ; i ++)
		 {
				 secondtopic[i+1] = topicname[i];
		 }
			return secondtopic;
	}

	@Override
	public Object[] wordRecommend(String userId, String secondTopic) {
		// TODO Auto-generated method stub
		Object[] wordlist = new Object[100];
		String[] word = new String[100];
		//根据二级主题secondTopic查找到所有的单词放到word中
		//......假设当前二级主题下的所有单词如下
		word[0] = "boat";
		word[1] = "ship";
		word[2] = "river";
		word[3] = "sun";
		word[4] = "rain";
		
		List<UserLogin> loginlist = userLoginDao.find(userId);
		if(loginlist == null)  wordlist[0] = 0;
		if(loginlist != null)
			 wordlist[0] = loginlist.size();
		if(loginlist == null || loginlist.size() < 5)
		{
			//按序返回
			
		}
		else  //登录次数>=5次
		{
		//去用户单词表中查询是否有学习过，学习过的往后排，没有学习过往前排
		//往前排的单词中，
		int i;
		double[] wordrecommend = new double[100];   //存储单词的推荐度
		for(i = 0; i < 100 && word[i] != null; i ++)
		{
			List<UserWord> userlist = userWordDao.findAll(userId, word[i], 3);
			if(userlist == null)   //如果该单词尚未被该用户学习过则推荐度剧增
				wordrecommend[i] += 100000;
			int wordlearncount = new Random().nextInt(100);   //获取单词被IOS用户学习的次数,暂设为随机
			wordrecommend[i] += wordlearncount;
		}
		for(i = 0; i < 100 && word[i] != null; i ++)
		  for(int j = i; j < 100 && word[j] != null; j ++)
			  if(wordrecommend[i] < wordrecommend[j])
			  {
				  double temp;
				  String tempstr;
				  temp = wordrecommend[i];
				  wordrecommend[i] = wordrecommend[j];
				  wordrecommend[j] = temp;
				  tempstr = word[i];
				  word[i] = word[j];
				  word[j] = tempstr;
			  }
		for(i = 0; i < 100 && word[i] != null; i ++)
		    wordlist[i+1] = word[i];
		}
		return wordlist;
	}

	@Override
	public int[] wordLearnIOS(String userId) {
		// TODO Auto-generated method stub
		int[] media = new int[5];
		List<UserLogin> loginlist = userLoginDao.find(userId);
		if(loginlist == null)  media[0] = 0;
		if(loginlist != null)
			 media[0] = loginlist.size();
		//1:图片2：音频3：视频4：文本
		media[1] = 1;
		media[2] = 2;
		media[3] = 3;
		media[4] = 4;
		if(loginlist == null || loginlist.size() < 5)
		{
			int learningstyle = 0;
            UserInfo ui = userInfoService.find(userId);
			if(ui != null)  learningstyle = ui.getLearningStyle3();
			if(learningstyle == 1)  
			{
				media[1] = 3;
				media[2] = 1;
				media[3] = 4;
				media[4] = 2;
			}
			else if(learningstyle == 2)
			{
				media[1] = 4;
				media[2] = 2;
				media[3] = 3;
				media[4] = 1;
			}
		}
		else
		{
			int[] usercount = new int[4];   //用户对4种媒体类型点击次数
			int[] count = new int[4];   //4种媒体类型被点击次数
			double[] mediarecommend = new double[4];  //4种媒体类型对当前用户的推荐度
			int[] mediacount = new int[4];  //4种媒体类型的总数分布(目前为假设？？？)
			mediacount[0] = 150;
			mediacount[1] = 200;
			mediacount[2] = 50;
			mediacount[3] = 260;
			int i;
			double w1,w2;
			w1 = 0.7;
			w2 = 0.3;
			for(i = 1 ; i <= 4; i ++)
			{
			   List<UserResource> urlist = 	userResourceDao.findByUserMedia(userId, i);
			   if(urlist != null)
				   usercount[i-1] = urlist.size();
			   count[i-1] = 0;
			   List<UserResource> list = userResourceDao.findByMedia(i);
			   if(list != null)
				   for(UserResource ur : list)
					   if(userInfoDao.find(ur.getUserId()).getSystem() == 2)  //判定是IOS用户的数据
				              count[i-1] ++;
			   mediarecommend[i-1] = (usercount[i-1] * w1 + count[i-1] * w2) / mediacount[i-1];
			   System.out.println(usercount[i-1] + "/" + count[i-1] + "/" + mediarecommend[i-1]);
			 }
			for(i = 0; i < 4; i ++)
				for(int j = i; j < 4; j ++)
					if(mediarecommend[i] < mediarecommend[j])
					{
						double temp;
						int mediatemp;
						temp = mediarecommend[i];
						mediarecommend[i] = mediarecommend[j];
						mediarecommend[j] = temp;
						mediatemp =  media[i+1];
						media[i+1] = media[j+1];
						media[j+1] = mediatemp;
					}
		}
		return media;
	}

	@Override
	public Object[] topicRecommendAndroid(String userId) {
		// TODO Auto-generated method stub
		Object[] topic = new Object[14];
		List<UserLogin> loginList = userLoginDao.find(userId);
		String[] topicName = new String[13]; //13个主题 需要赋值！！
		topicName[0] = "topic1";
		topicName[1] = "topic2";
		topicName[2] = "topic3";
		topicName[3] = "topic4";
		topicName[4] = "topic5";
		topicName[5] = "topic6";
		topicName[6] = "topic7";
		topicName[7] = "topic8";
		topicName[8] = "topic9";
		topicName[9] = "topic10";
		topicName[10] = "topic11";
		topicName[11] = "topic12";
		topicName[12] = "topic13";
		
		//获取登录次数
		if(loginList == null){
			topic[0] = 0;
		}else{
			 topic[0] = loginList.size();
		}
		
		if(loginList != null && loginList.size()>=5){
			 int i;		
			 double[] topicRecommend = new double[13];
			 double w1,w2,w3;  //设置两个权重
			 w1 = 0.6;
			 w2 = 0.3;
			 w3 = 0.1;
			 int max = 0;
			 for(i = 0; i<13; i++){
			 //用户一级主题学习的场景数
			 PassInfo passInfo = passInfoDao.find(userId, topicName[i]);
			 if(passInfo != null)
			 { 
				 System.out.println("--" + passInfo.getOnScene() + "/" + passInfo.getGoals() + "/" + passInfo.getMedals());
				 int onScene = passInfo.getOnScene();
				 int goals = passInfo.getGoals();
				 int medals = passInfo.getMedals();
				 topicRecommend[i] = onScene*w1 + goals*w2 + medals*w3;
			 }
			 else topicRecommend[i] = 0;
			 System.out.println(topicRecommend[i]);
			 if(i > 0 && topicRecommend[max] < topicRecommend[i])
				 max = i;
			}
			 topic[1] = topicName[max];
			 for(i = 2; i <= 13; i ++)
				 topic[i] = -1;
		}else{
			for(int i = 1;i <= 13;i++){
				topic[i] = topicName[i-1];
			}
		}
			return topic;	
	}

	@Override
	public int[] wordLearnMedia(String userId, String aspect) {
		// TODO Auto-generated method stub
		int[] media = new int[5];
		List<UserLogin> loginList = userLoginDao.find(userId);

		//获取用户的登录次数
		if(loginList == null){
			media[0] = 0;
		}
		else{
			media[0] = loginList.size();
		}
		System.out.println("-------------------登录次数：" + media[0]);
		if(aspect == "wordProperty" || aspect == "wordAttribute" || aspect == "textSentence" || aspect == "sceneParagraph" || aspect == "extendSentence" || aspect == "encyclopedia" || aspect == "usage")
		{
			System.out.println("_________________" + aspect);
			media[1] = 4;
			media[2] = -1;
			media[3] = -1;
			media[4] = -1;
			return media;
		}
			//1:图片2：音频3：视频
		media[1] = 1;
		media[2] = 2;
		media[3] = 3;
		media[4] = -1;
		if(loginList.size() < 5 || loginList ==null){
			int learningsStyle = 0;
			UserInfo userInfo = userInfoService.find(userId);
			if(userInfo != null){
				learningsStyle = userInfo.getLearningStyle3();
			}
			if(learningsStyle == 1){
				media[1] = 3;
				media[2] = 1;
				media[3] = 2;
			}else if(learningsStyle == 2){
				media[1] = 2;
				media[2] = 1;
				media[3] = 3;
			}
		}else{
			//获取用户对各种媒体资源的点击次数
			int[] userCount = new int[3];	//用户对3种媒体类型点击次数
			int[] count = new int[3];	//3种媒体类型被点击次数
			double[] mediaRecommend = new double[3];  //4种媒体类型对当前用户的推荐度
			int[] mediaCount = new int[3];  //3种媒体类型的总数分布(目前为假设？？？)
			mediaCount[0] = 150;
			mediaCount[1] = 200;
			mediaCount[2] = 50;
			
			int i;
			double w1, w2;
			w1 = 0.7;
			w2 = 0.3;
			for(i = 1; i<4; i++){
				//当前用户对不同媒体资源的点击次数
				List<UserResource> userList = userResourceDao.findByUserMedia(userId, i);
				if(userList != null){
					userCount[i-1] = userList.size();
					System.out.println("当前用户对第" + i + "种媒体类型的学习次数：" + userCount[i-1]);
				}
				//所有用户对不同媒体类型的点击次数
				List<UserResource> list = userResourceDao.findByMedia(i);
				if(list != null){
					count[i-1] = 0;
					for(UserResource ur : list)
						if(userInfoDao.find(ur.getUserId()).getSystem() == 1)  //当前用户为安卓用户
				         	count[i-1] ++;
					System.out.println("第" + i + "种媒体类型的被学习次数：" + count[i-1]);
				}
				mediaRecommend[i-1] = (userCount[i-1] * w1 + count[i-1] * w2) / mediaCount[i-1];
				System.out.println("第" + i + "种媒体类型的推荐度：" + mediaRecommend[i-1]);
			}
			for(i = 0; i < 3; i ++){
				for(int j = i; j < 3; j ++){
					if(mediaRecommend[i] < mediaRecommend[j]){
						double temp;
						int mediaTemp;
						temp = mediaRecommend[i];
						mediaRecommend[i] = mediaRecommend[j];
						mediaRecommend[j] = temp;
						mediaTemp =  media[i+1];
						media[i+1] = media[j+1];
						media[j+1] = mediaTemp;
					}
				}
			}
		}					
		return media;
	}

	@Override
	public List<String> wordLearnAspect(String userId) {
		// TODO Auto-generated method stub
		System.out.println(userId);
		//System.out.println(userInfoDao.find(userId).getBeforeLevel());
		List<String> aspectList = new ArrayList<String>();
		int loginCount = 0;
		List<UserLogin> loginList = userLoginDao.find(userId);
		
		//获取用户的登录次数
		if(loginList != null){
			loginCount = loginList.size();
		}
		
		int level = 2;
		int i;
		int[] candidateD = new int[3];   //备选测试类型三个难度的个数分布
		for(i = 0; i < 3; i ++)
			candidateD[i] = candidateTestService.GetCandidateDCount(userId, i+1);
		double[] score = new double[6];
		List<CandidateTest> ctList = new ArrayList<CandidateTest>();
		ctList.add(new CandidateTest(1,1,2));
		ctList.add(new CandidateTest(2,3,2));
		ctList.add(new CandidateTest(4,3,2));
		ctList.add(new CandidateTest(1,3,2));
		ctList.add(new CandidateTest(4,4,2));
		double score1 = 0;   //5个测试类型的参考分
		double score2 = 0;   //5个测试类型的实际得分
		int count = 0;
		for(i = 0; i < 5; i ++)
		{
			CandidateTest ct = candidateTestDao.find(userId, ctList.get(i).getTestType(), ctList.get(i).getTestAspect(), ctList.get(i).getTestDifficulty());
		    if(ct != null && (ct.getPass1Count() + ct.getPass2Count() + ct.getPass3Count() > 0))
			{
		    	count ++;
		    	double referenceScore = ct.getPass1Count() + ct.getPass2Count() + ct.getPass3Count();
			    double realScore = ct.getPass1Count() + ct.getPass2Count() * 0.5 + ct.getPass3Count() * 1.0/3;
			    score1 += referenceScore;
			    score2 += realScore;
			    score[i] = realScore/referenceScore;
			    System.out.println("-------------------" + score[i]);
			}
		}
		if(count > 0)
	    	{
			   score[5] = score2/score1;
			   System.out.println("----------------" + score[5]);
	    	}
		if(loginCount < 5 && count < 5){
			//前测
			UserInfo userInfo = userInfoService.find(userId);
			if(userInfo != null){
				level = userInfo.getBeforeLevel();
			}
		}else{
			//根据测试成绩判定等级
			if(candidateD[0] > 1){
				level = 1;				
			}else if(candidateD[0] == 0 || score[0] < 0.6 || score[1] < 0.6 || score[2] < 0.6 || score[3] < 0.6 || score[4] < 0.6){
				level = 2;
			}else if(score[5] <= 0.7){
				level = 3;
			}else if(score[0] < 0.7 || score[1] < 0.7 || score[2] < 0.7	|| score[3] < 0.7 || score[4] < 0.7){
				level = 4;
			}else if(score[5] <= 0.8){
				level = 5;
			}else{
				level = 6;
			}	
			UserInfo ui = userInfoDao.find(userId);
			ui.setBeforeLevel(level);
			userInfoDao.update(ui);
			System.out.println("后期成绩估算得到级别!");
		}
		System.out.println("登录次数=" + loginCount + ",count=" + count + ",level=" + level);
			if(level == 1){
				aspectList.add("wordD2");
				aspectList.add("wordProperty");	//词性
				aspectList.add("wordAttribute");	//词性属性
				aspectList.add("textSentence");	//课文原句
				aspectList.add("sceneParagraph");	//情景段落
				aspectList.add("extendSentence");	//延伸例句				
			}else if(level == 3){
				aspectList.add("wordD2");
				aspectList.add("wordProperty");	//词性
				aspectList.add("wordAttribute");	//词性属性
				aspectList.add("textSentence");	//课文原句
				aspectList.add("sceneParagraph");	//情景段落
				aspectList.add("extendSentence");	//延伸例句	
				aspectList.add("associate");	//联想
				aspectList.add("synonyms");		//同义词
				aspectList.add("antonym");	//反义词
			}else if(level == 5){
				aspectList.add("wordD2");
				aspectList.add("wordProperty");	//词性
				aspectList.add("wordAttribute");	//词性属性
				aspectList.add("textSentence");	//课文原句
				aspectList.add("sceneParagraph");	//情景段落
				aspectList.add("extendSentence");	//延伸例句	
				aspectList.add("associate");	//联想
				aspectList.add("synonyms");		//同义词
				aspectList.add("antonym");	//反义词
				aspectList.add("expand");	//拓展
				aspectList.add("encyclopedia");	//百科
				aspectList.add("usage");	//用法
				aspectList.add("commonlyUsed");	//常用
			}else if(level == 2){
				aspectList.add("wordD5");
				aspectList.add("wordProperty");	//词性
				aspectList.add("wordAttribute");	//词性属性
				aspectList.add("textSentence");	//课文原句
				aspectList.add("sceneParagraph");	//情景段落
				aspectList.add("extendSentence");	//延伸例句				
			}else if(level == 4){
				aspectList.add("wordD5");
				aspectList.add("wordProperty");	//词性
				aspectList.add("wordAttribute");	//词性属性
				aspectList.add("textSentence");	//课文原句
				aspectList.add("sceneParagraph");	//情景段落
				aspectList.add("extendSentence");	//延伸例句	
				aspectList.add("associate");	//联想
				aspectList.add("synonyms");		//同义词
				aspectList.add("antonym");	//反义词
			}else if(level == 6){
				aspectList.add("wordD5");
				aspectList.add("wordProperty");	//词性
				aspectList.add("wordAttribute");	//词性属性
				aspectList.add("textSentence");	//课文原句
				aspectList.add("sceneParagraph");	//情景段落
				aspectList.add("extendSentence");	//延伸例句	
				aspectList.add("associate");	//联想
				aspectList.add("synonyms");		//同义词
				aspectList.add("antonym");	//反义词
				aspectList.add("expand");	//拓展
				aspectList.add("encyclopedia");	//百科
				aspectList.add("usage");	//用法
				aspectList.add("commonlyUsed");	//常用
			}
		
		return aspectList;
	}
	public UserLoginDao getUserLoginDao() {
		return userLoginDao;
	}
	@Resource
	public void setUserLoginDao(UserLoginDao userLoginDao) {
		this.userLoginDao = userLoginDao;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}
	@Resource
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public UserWordService getUserWordService() {
		return userWordService;
	}
	@Resource
	public void setUserWordService(UserWordService userWordService) {
		this.userWordService = userWordService;
	}

	public UserWordDao getUserWordDao() {
		return userWordDao;
	}
	@Resource
	public void setUserWordDao(UserWordDao userWordDao) {
		this.userWordDao = userWordDao;
	}

	public UserResourceDao getUserResourceDao() {
		return userResourceDao;
	}
	@Resource
	public void setUserResourceDao(UserResourceDao userResourceDao) {
		this.userResourceDao = userResourceDao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	@Resource
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public CandidateTestService getCandidateTestService() {
		return candidateTestService;
	}
	@Resource
	public void setCandidateTestService(CandidateTestService candidateTestService) {
		this.candidateTestService = candidateTestService;
	}
	public PassInfoDao getPassInfoDao() {
		return passInfoDao;
	}
	@Resource
	public void setPassInfoDao(PassInfoDao passInfoDao) {
		this.passInfoDao = passInfoDao;
	}
	public CandidateTestDao getCandidateTestDao() {
		return candidateTestDao;
	}
	@Resource
	public void setCandidateTestDao(CandidateTestDao candidateTestDao) {
		this.candidateTestDao = candidateTestDao;
	}



	@Override
	public int recommendGrade(String userId, int grade) {
		// TODO Auto-generated method stub
	    UserInfo ui = userInfoDao.find(userId);
	    if(ui != null)
	    {
	    	int loginCount = 0;
		    List<UserLogin> loginList = userLoginDao.find(userId);
		    //获取用户的登录次数
		    if(loginList != null)
			     loginCount = loginList.size();
		    //目前均按照登录次数<5的规则来计算，只考虑实际年级和前测等级/初始成绩
	//	    if(loginList != null && loginList.size() >= 5)
		    {
	        	if(ui.getSystem() == 1)  //安卓用户
	        	{
	    		   int bl = ui.getBeforeLevel();
	    		   if(bl == 1 || bl == 2)  
	    			  return grade - 1;
	    	       else 
	    	 		   if(bl == 3 || bl == 4)  
	    				  return grade;
	    			   else  
	    				return grade + 1;
	    	    }
	    	    else   //IOS用户
	    	    {
	    		   int es = ui.getEnglishScore();
	    		   if(es < 70) 
	    			   return grade - 1;
	    		   else 
	    		    	if(es >= 70 && es < 90) 
	    				   return grade;
	    		        else  
	    		    	   return grade + 1;
	    	    }
		    }
	    }
	    else 
	    {
	    	System.out.println("无此用户的信息，返回年级为-1");
	    	return -1;
	    }
	}



	@Override
	public Object[][] recommendSequence(String userId) {
		// TODO Auto-generated method stub
		UserInfo ui = userInfoDao.find(userId);
		
		Object[][] ads = new Object[11][3];   //0单元存储内容，1单元表示mediatype,2单元表示推荐度
		ads[0][0] = "baseinfo"; ads[0][1] = 0;
		ads[1][0] = "textsentence+phase"; ads[1][1] = 0;
		ads[2][0] = "expandsentence"; ads[2][1] = 0;
		ads[3][0] = "video"; ads[3][1] = 3;
		ads[4][0] = "synonym"; ads[4][1] = 1;
		ads[5][0] = "antonym"; ads[5][1] = 1;
		ads[6][0] = "imageword"; ads[6][1] = 1;
		ads[7][0] = "expandword"; ads[7][1] = 1;
		ads[8][0] = "usage"; ads[8][1] = 1;
		ads[9][0] = "baike"; ads[9][1] = 1;
		ads[10][0] = "common"; ads[10][1] = 1;
		for(int i = 0; i < 11; i ++)
			ads[i][2] = 0;
		Object[][] ids = new Object[9][3];
		ids[0][0] = "baseinfo"; ids[0][1] = 0;
		ids[1][0] = "textsentence"; ids[1][1] = 0;
		ids[2][0] = "phase"; ids[2][1] = 0;
		ids[3][0] = "expandsentence"; ids[3][1] = 1;
		ids[4][0] = "imageword"; ids[4][1] = 1;
		ids[5][0] = "wordgroup"; ids[5][1] = 1;
		ids[6][0] = "expandword"; ids[6][1] = 1;
		ids[7][0] = "baike"; ids[7][1] = 1;
		ids[8][0] = "video"; ids[8][1] = 3;
		for(int i = 0; i < 9; i ++)
			ids[i][2] = 0;
		
		if(ui != null)
		{
			int loginCount = 0;
		    List<UserLogin> loginList = userLoginDao.find(userId);
		    //获取用户的登录次数
		    if(loginList != null)
			     loginCount = loginList.size();
		    if(loginCount < 5)
		    {
		    	if(ui.getSystem() == 1) {
		    		if(ui.getLearningStyle3() == 2)
		    		{
		    		   Object[] temp = new Object[3];
	    			   temp[0] = ads[3][0];
	    			   temp[1] = ads[3][1];
	    			   temp[2] = ads[3][2];
		    		   for(int i = 3; i < 11; i ++)
		    			  ads[i] = ads[i+1];
		    		   ads[10] = temp;
		    		}
		    		return ads;
		    	}
		    	else{
		    		if(ui.getLearningStyle3() == 1)
		    		{
		    			Object[] temp = new Object[3];
		    			temp[0] = ids[8][0];
		    			temp[1] = ids[8][1];
		    			temp[2] = ids[8][2];
		    			for(int i = 8; i >= 3; i --)
		    				ids[i] = ids[i-1];
		    			ids[3] = temp;
		    		}
		    		return ids;
		    	} 
		    }
		    if(loginList != null && loginList.size() >= 5)
		    {
		       if(ui.getSystem() == 1)  //安卓用户
		       {
		    	   for(int i = 3; i < 11; i ++)
		    		   ads[i][2] = resourcePreferenceService.recommendByPreference(userId, (String)ads[i][0], (int)ads[i][1]);
		    	   for(int i = 3; i < 11; i ++)
		    		   for(int j = i + 1; j < 11; j ++)
		    		   {
		    			   if(i != j && (double)ads[i][2] < (double)ads[j][2])
		    			   {
		    				   Object[] temp = new Object[3];
		    				   temp[0] = ads[i][0];
		    				   temp[1] = ads[i][1];
		    				   temp[2] = ads[i][2];
		    				   ads[i][0] = ads[j][0];
		    				   ads[i][1] = ads[j][1];
		    				   ads[i][2] = ads[j][2];
		    				   ads[j][0] = temp[0];
		    				   ads[j][1] = temp[1];
		    				   ads[j][2] = temp[2];
		    			   }
		    		   }
		    	   return ads;
		       }
		       else 
		       {
		    	   for(int i = 3; i < 9; i ++)
		    		   ids[i][2] = resourcePreferenceService.recommendByPreference(userId, (String)ids[i][0], (int)ids[i][1]);
		           for(int i = 3; i < 9; i ++)
		        	   for(int j = i + 1; j < 9; j ++)
		        	      if(i != j && (double)ids[i][2] < (double)ids[j][2])
		        	      {
		        	    	   Object[] temp = new Object[3];
		        	    	   temp[0] = ids[i][0];
		    				   temp[1] = ids[i][1];
		    				   temp[2] = ids[i][2];
		    				   ids[i][0] = ids[j][0];
		    				   ids[i][1] = ids[j][1];
		    				   ids[i][2] = ids[j][2];
		    				   ids[j][0] = temp[0];
		    				   ids[j][1] = temp[1];
		    				   ids[j][2] = temp[2];
		        	      }
		           return ids;
		       }
		    }
		}
		return null;
	}


}
