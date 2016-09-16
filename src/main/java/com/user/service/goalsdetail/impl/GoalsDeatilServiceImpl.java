package com.user.service.goalsdetail.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.goalsdetail.GoalsDetailDao;
import com.user.entity.GoalsDetail;
import com.user.service.goalsdetail.GoalsDetailService;
@Service("goalsDetailService")
public class GoalsDeatilServiceImpl implements GoalsDetailService {
    private GoalsDetailDao goalsDetailDao;
    
	public GoalsDetailDao getGoalsDetailDao() {
		return goalsDetailDao;
	}
	@Resource
	public void setGoalsDetailDao(GoalsDetailDao goalsDetailDao) {
		this.goalsDetailDao = goalsDetailDao;
	}



	@Override
	public List<GoalsDetail> find(String userId, int inORout, Date time1, Date time2) {
		// TODO Auto-generated method stub
		return goalsDetailDao.find(userId,inORout,time1,time2);
	}
	@Override
	public void add(String userId, int inORout, int goals, int content, Date time) {
		// TODO Auto-generated method stub
		int goalsBefore = this.getGoals(userId);
		int goalsAfter = 0;
		if(inORout == 1) goalsAfter = goalsBefore + goals;
		else if(inORout == 2) goalsAfter = goalsBefore - goals;
		if(goalsAfter >= 0)
		   {
			 GoalsDetail gd = new GoalsDetail(userId,goalsBefore,inORout,content,goalsAfter,time);
			 goalsDetailDao.save(gd);
		   }
		else System.out.println("金币数不足！");
	}
	@Override
	public int getGoals(String userId) {
		// TODO Auto-generated method stub
        List<GoalsDetail> list = goalsDetailDao.find(userId);
        if(list != null && list.size() > 0)
        	return list.get(list.size() - 1).getGoalsAfter();
		return 0;
	}

}
