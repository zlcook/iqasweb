package com.user.service.usertest;

import java.util.Date;
import java.util.List;

import com.user.entity.UserTest;

/**用户测试数据
 * @author 刘玉婷
 * @version 创建时间：2016年6月16日 下午16:07:10
 */
public interface UserTestService {


    /**
     * 添加一条用户答题记录	
     * @param ut
     */
	public void addUserTest(UserTest ut);
	
	/**
	 * 添加一条用户答题记录
	 * @param userId
	 * @param word
	 * @param testType
	 * @param testAspect
	 * @param testDifficulty
	 * @param rightTimes
	 * @param wrongTimes
	 * @param totalTimes
	 * @param startTime
	 * @param endTime
	 */
    public void addUserTest1(String userId, String word, int testType, int testAspect, int testDifficulty,
			int rightTimes, int wrongTimes, int totalTimes, Date startTime, Date endTime);
    
    /**
     * 小测四道题目的出题
     * @param userId 
     * @param words 场景中5个单词
     * @param index 第几道题
     * @return 四元组
     */
    public UserTest TestFourGroup(String userId,String[] words,int index);
}
