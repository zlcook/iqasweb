package com.cnu.offline.service.impl;

import java.util.Hashtable;
import java.util.Map;

import com.cnu.offline.service.QueryWordFromOntology;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年9月6日 下午10:32:17
* 类说明
*/
public class QueryWord4IosAdapter implements QueryWordFromOntology {
	//操作本题库
	private OntologyManage ontologyManage =null;

	public QueryWord4IosAdapter(OntologyManage ontologyManage) {
		super();
		this.ontologyManage = ontologyManage;
	}
	
	@Override
	public Hashtable<String, PropertyEntity> queryWordByThemeAndGrade(int grade, String theme) {
		// TODO Auto-generated method stub
		return null;
	}

}
