package com.cnu.iqas.service.ios.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.ios.SuserWork;
import com.cnu.iqas.dao.common.IUserWorkDao;
import com.cnu.iqas.service.common.IFileDownloadService;
import com.cnu.iqas.service.common.IUserWorkService;

/**
* @author 周亮 
* @version 创建时间：2016年3月9日 下午4:06:12
* 类说明
*/
@Service("suserWorkService")
public class SUserWorkServiceImpl implements IUserWorkService<SuserWork>{

	/**
	 * 用户作品服务
	 */
	private IUserWorkDao<SuserWork> suserWorkDao;
	/**
	 * 保存学生作品
	 * @param work
	 */
	public void save(SuserWork work){
		suserWorkDao.save(work);
	}

	public IUserWorkDao<SuserWork> getSuserWorkDao() {
		return suserWorkDao;
	}
	@Autowired
	public void setSuserWorkDao(IUserWorkDao<SuserWork> suserWorkDao) {
		this.suserWorkDao = suserWorkDao;
	}
	
}
