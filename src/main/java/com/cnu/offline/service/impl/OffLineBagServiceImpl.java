package com.cnu.offline.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.dao.OffLineBagDao;
import com.cnu.offline.service.OffLineBagService;

/**
* @author 周亮 
* @version 创建时间：2016年9月10日 下午3:00:55
* 类说明
*/
@Service("offLineBagService")
public class OffLineBagServiceImpl implements OffLineBagService {

	private OffLineBagDao offLineBagDao;

	public OffLineBag find(String themenumber,int recommendGrade,int realGrade){
		return offLineBagDao.find(themenumber, recommendGrade, realGrade);
	}

	public void add(OffLineBag offLineBag){
		offLineBagDao.save(offLineBag);
	}
	public OffLineBagDao getOffLineBagDao() {
		return offLineBagDao;
	}
	@Resource
	public void setOffLineBagDao(OffLineBagDao offLineBagDao) {
		this.offLineBagDao = offLineBagDao;
	}

	@Override
	public void update(OffLineBag offLineBag) {
		offLineBagDao.update(offLineBag);
	}

	@Override
	public boolean existMasterBag(String themenumber, int realGrade) {
		return offLineBagDao.existMasterBag(themenumber, realGrade);
	}

	@Override
	public List<OffLineBag> listMaster(String themenumber) {
		if( themenumber ==null || themenumber.equals(""))
			return null;
		return offLineBagDao.getAllData("o.themenumber =?", new Object[]{themenumber});
	}

	@Override
	public OffLineBag find(String id) {
		// TODO Auto-generated method stub
		return offLineBagDao.find(id);
	}
	
	
	
}
