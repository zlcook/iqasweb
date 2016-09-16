package com.cnu.iqas.service.ios.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.ios.SuserWord;
import com.cnu.iqas.dao.ios.SUserWordDao;
import com.cnu.iqas.service.common.IUserWordService;
import com.cnu.iqas.utils.WebUtils;

/**
* @author 周亮 
* @version 创建时间：2016年3月6日 下午10:20:12
* 类说明:
*/
@Service("suserWordService")
public class SUserWordServiceImpl implements IUserWordService<SuserWord> {
	/**
	 * 用户学习单词
	 */
	private SUserWordDao suserWordDao;
	/**
	 * 根据主题和用户id查询用户在该主题下的学习单词
	 * @param theme  主题
	 * @param userId 用户id
	 * @return
	 */
	public List<SuserWord> getWords(String theme,String userId){
		
		if(!isNull(theme)&&!isNull(userId)){
			String sql ="o.theme=? and userId=? ";
			List<Object> params = new ArrayList<>();
			params.add(theme);
			params.add(userId);
			return suserWordDao.getAllData(sql, params.toArray());
		}else
			return null;
	}
	public SUserWordDao getSuserWordDao() {
		return suserWordDao;
	}
	@Resource
	public void setSuserWordDao(SUserWordDao suserWordDao) {
		this.suserWordDao = suserWordDao;
	}
	 /**
		 * 校验字符串是否为空或者空字符串
		 * @param str
		 * @return 空或者空字符串返回true
		 */
		private  boolean isNull(String str){
			if( str!=null && str.trim()!="")
				return false;
			else
				return true;
		}
	@Override
	public void save(SuserWord entity) {
		// TODO Auto-generated method stub
		if( entity!=null)
		suserWordDao.save(entity);
	}
	
}
