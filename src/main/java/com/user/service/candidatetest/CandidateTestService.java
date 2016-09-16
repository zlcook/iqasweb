package com.user.service.candidatetest;

import java.util.List;

import com.user.entity.CandidateTest;

/**
 * @author 刘玉婷
 * @version 创建时间：2016年6月16日晚上20:13:00
 */
public interface CandidateTestService {

	/**
	 * 查询指定用户指定难度的备选测试类型个数
	 * @param userId
	 * @param difficulty 取值1,2,3
	 * @return 指定难度的备选测试类型个数
	 */
	public int GetCandidateDCount(String userId,int difficulty);
	
	/**
	 * 查询指定用户指定测试类型的信息
	 * @param userId
	 * @param testType
	 * @param testAspect
	 * @param testDifficulty
	 * @return 指定用户指定测试类型信息的对象
	 *//*
	public CandidateTest SearchCandidateTest(String userId,int testType,int testAspect,int testDifficulty);
*/
	/**
	 * 新增一条用户测试类型信息
	 * @param userId
	 * @param testType
	 * @param testAspect
	 * @param testDifficulty
	 * @param candidate
	 */
	public void AddCandidateTest(String userId,int testType,int testAspect,int testDifficulty,int candidate);

	/**
	 * 初始化用户备选测试类型
	 * @param userId
	 */
	public void AddBatchCandidateTest(String userId);
	/**
	 * 指定测试类型信息更改：一次通过/两次通过/三次及三次以上通过的数量加1
	 * @param userId
	 * @param testType
	 * @param testAspect
	 * @param testDifficulty
	 * @param passtime 1：一次通过，2:两次通过，3：三次及三次以上通过
	 */
	public void UpdateCandidateTestCount(String userId,int testType,int testAspect,int testDifficulty,int passtime);
    
	
	public void UpdateCandidateStatus(String userId,int testType,int testAspect,int testDifficulty);
	

}
