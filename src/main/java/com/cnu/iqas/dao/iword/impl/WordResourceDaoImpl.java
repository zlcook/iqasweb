package com.cnu.iqas.dao.iword.impl;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.iword.WordResourceDao;

/**
* @author 周亮 
* @version 创建时间：2015年11月26日 下午7:23:14
* @author 王文辉
* @version 修改时间：2016年1月29号
* 类说明
*/
@Repository("wordResourceDao")
public class WordResourceDaoImpl extends DaoSupport<WordResource> implements WordResourceDao{

	@Override
	public List<WordResource> findByWord(String wordId, int type) {
		return (List<WordResource>) getHt().find("From WordResource o where o.wordId=? and type=?", wordId,type);
	}

	@Override
	public WordResource findByContent() {

		// TODO Auto-generated method stub  SELECT * FROM  t_resource  ORDER BY RAND ( )  limit 1
        //SELECT * FROM  t_resource  ORDER BY RAND ( )  limit 1
		    Random rand = new Random();
			List <WordResource>list =(List<WordResource>) getHt().find("From WordResource");
			int i = rand.nextInt(list.size()); //生成0-list.size()以内的随机数    
			if(list.size()!=0)
				return  list.get(i);
			else
				return null;
		}	
  			
	}


