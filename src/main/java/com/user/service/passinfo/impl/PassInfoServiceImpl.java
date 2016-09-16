package com.user.service.passinfo.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.passinfo.PassInfoDao;
import com.user.entity.PassInfo;
import com.user.service.passinfo.PassInfoService;
@Service("passInfoService")
public class PassInfoServiceImpl implements PassInfoService {
    private PassInfoDao passInfoDao;
    
	public PassInfoDao getPassInfoDao() {
		return passInfoDao;
	}
	@Resource
	public void setPassInfoDao(PassInfoDao passInfoDao) {
		this.passInfoDao = passInfoDao;
	}

	@Override
	public int delete(String userId, String topic) {
		// TODO Auto-generated method stub
		PassInfo pi = passInfoDao.find(userId, topic);
    	if(pi != null)  {
    		passInfoDao.delete(pi.getId());
    		return 1;
    	}
    	else return 0;
	}

	@Override
	public PassInfo find(String userId, String topic) {
		// TODO Auto-generated method stub
		return passInfoDao.find(userId,topic);
	}
	@Override
	public void addonScene(String userId, String topic) {
		// TODO Auto-generated method stub
		PassInfo pi = this.find(userId, topic);
		if(pi != null)
		{
			pi.setOnScene(pi.getOnScene() + 1);
		    passInfoDao.update(pi);
		}
		else
		{
			passInfoDao.save(new PassInfo(userId,topic,1,0,0));
		}
	}
	@Override
	public void addGoals(String userId, String topic, int goals) {
		// TODO Auto-generated method stub
		PassInfo pi = this.find(userId, topic);
		if(pi != null)
		{
			pi.setGoals(pi.getGoals() + goals);
			passInfoDao.update(pi);
		}
		else
		{
			System.out.println("插入有误！");
		}
	}
	@Override
	public void addMedals(String userId, String topic, int medals) {
		// TODO Auto-generated method stub
		PassInfo pi = this.find(userId, topic);
		if(pi != null)
		{
			pi.setMedals(pi.getMedals() + medals);
			passInfoDao.update(pi);
		}
		else
		{
			System.out.println("插入有误！");
		}
	}

}
