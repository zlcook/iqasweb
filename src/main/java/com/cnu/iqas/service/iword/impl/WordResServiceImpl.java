package com.cnu.iqas.service.iword.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.bean.iword.WordRes;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.dao.iword.WordResDao;
import com.cnu.iqas.service.iword.WordResService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.utils.PropertyUtils;

/**
* @author 周亮 
* @version 创建时间：2016年7月21日 上午10:13:16
* 类说明:
*/
@Service("wordResService")
public class WordResServiceImpl implements WordResService {

	private WordResDao wordResDao;
	
	@Override
	public WordRes find(String word) {
		if( word==null )
			throw new RuntimeException("查询word为null");
		return wordResDao.find(word);
	}

	@Override
	public void update(WordRes wordRes) {
		if( wordRes==null )
			throw new RuntimeException("更新wordRes为null");
		wordResDao.update(wordRes);
	}

	public WordResDao getWordResDao() {
		return wordResDao;
	}
	@Autowired
	public void setWordResDao(WordResDao wordResDao) {
		this.wordResDao = wordResDao;
	}

	@Override
	public void save(WordRes res) {
		// TODO Auto-generated method stub
		wordResDao.save(res);
	}

	@Override
	public void saveOrUpdate(WordRes res) {
		// TODO Auto-generated method stub
		if( res==null )
			throw new RuntimeException("操作的wordRes为null");
		WordRes wr =find(res.getWord());
		if(wr!=null)
			update(res);
		else
			save(res);
	}

}
