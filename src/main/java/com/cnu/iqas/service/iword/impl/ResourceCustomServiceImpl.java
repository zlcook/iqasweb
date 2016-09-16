package com.cnu.iqas.service.iword.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.iword.WordResouceCustom;
import com.cnu.iqas.dao.iword.ResourceCustomDao;
import com.cnu.iqas.dao.iword.WordResourceDao;
import com.cnu.iqas.service.iword.ResourceCustomService;

/**
* @author 周亮 
* @version 创建时间：2016年5月10日 下午1:31:54
* 类说明
*/
@Service("resourceCustomService")
public class ResourceCustomServiceImpl implements ResourceCustomService {

	private ResourceCustomDao resourceCustomDao;
	@Override
	public void save(WordResouceCustom wordResouceCustom) {
		// TODO Auto-generated method stub
		resourceCustomDao.save(wordResouceCustom);
	}
	public ResourceCustomDao getResourceCustomDao() {
		return resourceCustomDao;
	}
	@Resource
	public void setResourceCustomDao(ResourceCustomDao resourceCustomDao) {
		this.resourceCustomDao = resourceCustomDao;
	}

}
