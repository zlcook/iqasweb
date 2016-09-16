package com.user.service.candidatetest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.candidatetest.CandidateTestDao;
import com.user.entity.CandidateTest;
import com.user.service.candidatetest.CandidateTestService;
@Service("candidateTestService")
public class CandidateTestServiceImpl implements CandidateTestService {

	private CandidateTestDao candidateTestDao;
	public CandidateTestDao getCandidateTestDao() {
		return candidateTestDao;
	}
	@Resource
	public void setCandidateTestDao(CandidateTestDao candidateTestDao) {
		this.candidateTestDao = candidateTestDao;
	}

	@Override
	public int GetCandidateDCount(String userId, int difficulty) {
		// TODO Auto-generated method stub
		Object[] queryParams = new Object[3];
		queryParams[0] = userId;
		queryParams[1] = difficulty;
		queryParams[2] = 1;
		
		List<CandidateTest> ctlist = candidateTestDao.getAllData("o.userId=? and o.testDifficulty=? and o.candidate = ?", queryParams);
		if(ctlist == null)
			return 0;	
		return ctlist.size();
	}

	/*@Override
	public CandidateTest SearchCandidateTest(String userId, int testType, int testAspect, int testDifficulty) {
		// TODO Auto-generated method stub
		Object[] queryParams = new Object[4];
	       queryParams[0] = userId;
		   queryParams[1] = testType;
		   queryParams[2] = testAspect;
		   queryParams[3] = testDifficulty;
		   CandidateTest ct = candidateTestDao.find(userId, testType, testAspect, testDifficulty);
		return ct;
	}*/

	@Override
	public void AddCandidateTest(String userId, int testType, int testAspect, int testDifficulty, int candidate) {
		// TODO Auto-generated method stub
	   CandidateTest ct = candidateTestDao.find(userId, testType, testAspect, testDifficulty);
       if(ct == null)
	   {
		   CandidateTest newct = new CandidateTest(userId,testType,testAspect,testDifficulty,0,0,0,candidate);
		   candidateTestDao.save(newct);
		   System.out.println("添加用户测试类型信息记录成功！");
	   }
	   else  System.out.println("该用户该测试类型信息已存在！"); 
	}
	@Override
	public void UpdateCandidateTestCount(String userId, int testType, int testAspect, int testDifficulty,
			int passtime) {
		CandidateTest ct = candidateTestDao.find(userId, testType, testAspect, testDifficulty);
		int[] pass = new int[3];
		if(ct != null)
		{
			int passCount = 0;
		    
			if(passtime == 1)
			{
		//		pass[0] = 1;
				passCount = ct.getPass1Count();
				System.out.println("1:" + passCount);
				ct.setPass1Count(++passCount);
				candidateTestDao.update(ct);
			}
			else if(passtime == 2)
			{
		//		pass[1] = 1;
				passCount = ct.getPass2Count();
				System.out.println("2:" + passCount);
				ct.setPass2Count(++passCount);
				candidateTestDao.update(ct);
			}
			else 
			{
		//		pass[2] = 1;
				passCount = ct.getPass3Count();
				System.out.println("3:" + passCount);
				ct.setPass3Count(++passCount);
				candidateTestDao.update(ct);
			}
		}
		else 
		{
			System.out.println("该测试类型信息不存在，新增！");
			if(passtime == 1) {pass[0] = 1; pass[1] = 0; pass[2] = 0;}
			else if(passtime == 2) {pass[0] = 0; pass[1] = 1; pass[2] = 0;}
			else {pass[0] = 0; pass[1] = 0; pass[2] = 1;}
			int candidate = 0;
			CandidateTest newct = new CandidateTest(userId,testType,testAspect,testDifficulty,pass[0],pass[1],pass[2],candidate);
		    candidateTestDao.save(newct);
		}
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void UpdateCandidateStatus(String userId, int testType, int testAspect, int testDifficulty) {
		// TODO Auto-generated method stub
		//当前级别是否满足要更新备选状态的条件
	    List<CandidateTest> ctList  = new ArrayList<CandidateTest>();
		//3种难度15种测试类型，其中0—4,5—9,10—14构成升降级的直接对应关系（属于同一测试类型不同难度），如0对应5对应10,1对应6,对应11
		//难度为1的测试类型
		ctList.add(new CandidateTest(2,1,1));
		ctList.add(new CandidateTest(2,2,1));
		ctList.add(new CandidateTest(3,2,1));
		ctList.add(new CandidateTest(1,3,1));
		ctList.add(new CandidateTest(4,4,1));
		//难度为2的测试类型
		ctList.add(new CandidateTest(1,1,2));
		ctList.add(new CandidateTest(2,3,2));
		ctList.add(new CandidateTest(4,3,2));
		ctList.add(new CandidateTest(1,3,2));
		ctList.add(new CandidateTest(4,4,2));
		//难度为3的测试类型
		ctList.add(new CandidateTest(1,4,3));
		ctList.add(new CandidateTest(2,2,3));
		ctList.add(new CandidateTest(1,2,3));
		ctList.add(new CandidateTest(1,3,3));
		ctList.add(new CandidateTest(2,4,3));

		//判定当前测试类型的位置
		int i,location=0;
		for(i = 0; i < 15; i ++)
			if(ctList.get(i).getTestType() == testType && ctList.get(i).getTestAspect() == testAspect && ctList.get(i).getTestDifficulty() == testDifficulty)
			{
				location = i;
				break;
			}
		//查询到当前用户当前测试类型的考察信息
		CandidateTest ct = candidateTestDao.find(userId, testType, testAspect, testDifficulty);
		if(ct != null)
		{
			//如果当前测试类型测试个数大于等于4个且当前为备选状态则去判断是否需要升降级
			if(ct.getPass1Count() + ct.getPass2Count() + ct.getPass3Count() >= 4 && ct.getCandidate() == 1)
			{
				double referenceScore = 0;
				double realScore = 0;
				int[] dCount = new int[3];
				//判断该测试类型对应的三种难度的测试正确率
				int[] loc = new int[3];  //对应三种难度的测试类型在ctList的位置
				loc[0] = 4;
				loc[1] = 9;
				loc[2] = 14;
				int r = (location + 1 ) % 5;
				if(r != 0)
				{
					loc[0] = r - 1;
					loc[1] = 5 + r - 1;
					loc[2] = 10 + r - 1;
				}
			    System.out.println("三个测试类型的位置对应分别是：" + loc[0] + "," + loc[1] + "," + loc[2]);
				for(i = 0; i < 3; i ++)
				{
				    CandidateTest ctInfo = candidateTestDao.find(userId, ctList.get(loc[i]).getTestType(), ctList.get(loc[i]).getTestAspect(), ctList.get(loc[i]).getTestDifficulty());
				    if(ctInfo != null)
				    {
				    	referenceScore += (ctInfo.getPass1Count() + ctInfo.getPass2Count() + ctInfo.getPass3Count()) * ctInfo.getTestDifficulty();
				        realScore += (ctInfo.getPass1Count() + ctInfo.getPass2Count() * 0.5 + ctInfo.getPass3Count() * 1.0/3) * ctInfo.getTestDifficulty();
				   }
				}
				System.out.println("参考分值：" + referenceScore + ",实际分值：" + realScore);
				//该测试类型三种难度的正确率高于80%，升级
				if(realScore/referenceScore > 0.8)
				{
					if(testDifficulty == 3)
					{
						System.out.println("已经是最高级！");
						//难度为3的测试类型不再升级
					}
					else 
					{
						CandidateTest ctInfo = candidateTestDao.find(userId, testType, testAspect, testDifficulty);
						if(ctInfo != null && ctInfo.getCandidate() == 1)
						{
							ctInfo.setCandidate(0);
							candidateTestDao.update(ctInfo);
							CandidateTest ctUpInfo = candidateTestDao.find(userId, ctList.get(location+5).getTestType(), ctList.get(location+5).getTestAspect(), testDifficulty+1);
						    if(ctUpInfo != null)
						    {
						    	ctUpInfo.setCandidate(1);
						    	candidateTestDao.update(ctUpInfo);
						    	System.out.println("升级成功！");
						    }
						    else
						    {
						        CandidateTest newct = new CandidateTest(userId,ctList.get(location+5).getTestType(),ctList.get(location+5).getTestAspect(),testDifficulty+1,0,0,0,1);
						    	candidateTestDao.save(newct);
						        System.out.println("新建升级测试类型记录！");
                             }    	
						}
						else System.out.println("当前类型不存在或者不为备选状态！");
					}			
				} //升级结束
				//该测试类型三种难度的正确率低于60%，降级
				if(realScore/referenceScore < 0.6)
				{
					if(testDifficulty == 1)
					{
						System.out.println("已经是最低级！");
						//难度为1的测试类型不再降级
					}
					else 
					{
						CandidateTest ctInfo = candidateTestDao.find(userId, testType, testAspect, testDifficulty);
						if(ctInfo != null && ctInfo.getCandidate() == 1)
						{
							ctInfo.setCandidate(0);
							candidateTestDao.update(ctInfo);
							CandidateTest ctDownInfo = candidateTestDao.find(userId, ctList.get(location-5).getTestType(), ctList.get(location-5).getTestAspect(), testDifficulty-1);
						    if(ctDownInfo != null)
						    {
						    	ctDownInfo.setCandidate(1);
						    	candidateTestDao.update(ctDownInfo);
						        System.out.println("降级成功！");
						    }
						    else
						    {
						    	CandidateTest newct = new CandidateTest(userId,ctList.get(location-5).getTestType(),ctList.get(location-5).getTestAspect(),testDifficulty-1,0,0,0,1);
						    	candidateTestDao.save(newct);
						    	System.out.println("新建降级测试类型记录！");		
						    }    	
						}
						else System.out.println("当前类型不存在或者不为备选状态！");
					}	
				} //降级结束
		}  //满足升降级的个数&当前为备选状态的条件
		} //备选测试类型中有此用户关于当前测试类型的记录
		else  System.out.println("该用户当前的备选测试类型记录尚未在备选测试类型表中，请查看！");
	}//该方法代码描述结束
	@Override
	public void AddBatchCandidateTest(String userId) {
		// TODO Auto-generated method stub
		this.AddCandidateTest(userId, 1, 1, 2, 1);
		this.AddCandidateTest(userId, 2, 3, 2, 1);
		this.AddCandidateTest(userId, 4, 3, 2, 1);
		this.AddCandidateTest(userId, 1, 3, 2, 1);
		this.AddCandidateTest(userId, 4, 4, 2, 1);
	}

}
