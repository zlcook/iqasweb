package com.cnu.iqas.service.iword.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.iword.WordThemeWordRel;
import com.cnu.iqas.dao.iword.WordThemeWordRelDao;
import com.cnu.iqas.service.iword.WordThemeWordRelService;

/**
* @author 周亮 
* @version 创建时间：2015年12月7日 下午8:05:17
* 类说明
*/
@Service("wordThemeWordRelService")
public class WordThemeWordRelServiceImpl implements WordThemeWordRelService {
	
	private WordThemeWordRelDao wordThemeWordRelDao;
	/**
	 * 保存一个“单词主题关系”实体
	 * @param rel 单词主题关系实体
	 */
	public void save(WordThemeWordRel entity){
		wordThemeWordRelDao.save(entity);
	}
	public WordThemeWordRelDao getWordThemeWordRelDao() {
		return wordThemeWordRelDao;
	}
	@Resource
	public void setWordThemeWordRelDao(WordThemeWordRelDao wordThemeWordRelDao) {
		this.wordThemeWordRelDao = wordThemeWordRelDao;
	}
	
}
