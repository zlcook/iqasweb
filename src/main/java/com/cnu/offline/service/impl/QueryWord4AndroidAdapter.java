package com.cnu.offline.service.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cnu.iqas.listener.InitializedListener;
import com.cnu.offline.service.QueryWordFromDataBase;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年9月6日 下午10:22:22
* 类说明
*/
public class QueryWord4AndroidAdapter implements QueryWordFromDataBase {
	//private final static Logger log = LogManager.getLogger(InitializedListener.class);
	private final Logger log = LogManager.getLogger(QueryWord4AndroidAdapter.class);
	//操作本题库
	private OntologyManage ontologyManage =null;

	public QueryWord4AndroidAdapter(OntologyManage ontologyManage) {
		super();
		this.ontologyManage = ontologyManage;
	}

	@Override
	public Hashtable<String, PropertyEntity> queryWordByThemeAndGrade(int grade, String theme) {
		if(grade<=0)
			throw new RuntimeException("本体查询的年级不在范围:grade="+grade);

		if( theme==null || theme.trim().equals(""))
			throw new RuntimeException("本体查询的主题为null:"+theme);
		//存放单词word和单词实体,只保留不同的单词，如果有重复会只取一个。
		Hashtable<String,PropertyEntity> wordsMap=null;
		//List<PropertyEntity> listpe = new ArrayList<>();
		List<ResultSet> resultsAllBrother=null;
		try {
			resultsAllBrother = ontologyManage.QueryBrotherIndividual(grade+"", theme);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if( resultsAllBrother!=null &resultsAllBrother.size()>0){
			wordsMap= new Hashtable<>();
			for (int i = 0; i < resultsAllBrother.size(); i++) {
				if (resultsAllBrother.get(i).hasNext()) {
					while (resultsAllBrother.get(i).hasNext()) {
						QuerySolution solutionEachBrother = resultsAllBrother.get(i).next();
						PropertyEntity pe= PropertyEntity.generatePropertyEntity(solutionEachBrother);
						wordsMap.put(pe.getInstanceLabel(), pe);
					
					}
				}
			}
		} else {
			log.error("知识本体库中没有此实例");
			return null;
		}
		
		//如何是旅游交通主题，且实际年级是4年级，一共有28个不同单词，只取其中24中不同单词，排除以下四个单词by_bus,bike_ride,by_school_bus,by_ship
		if(theme.equalsIgnoreCase("17.旅游与交通") && grade==4 && wordsMap!=null)
		{
			wordsMap.remove("by_bus");
			wordsMap.remove("bike_ride");
			wordsMap.remove("by_school_bus");
			wordsMap.remove("by_ship");         
		}
		//如果是12.饮食主题，且实际年级是4年级，一共有47个单词, 不同的单词的单词有：42，而ios端只学习37个，排除以下5个单词push、buffer、crowd、front、safety、thick
		if(theme.equalsIgnoreCase("12.饮食") && grade==4 && wordsMap!=null)
		{//push、buffer、crowd、front、safety、thick
			wordsMap.remove("push");
			wordsMap.remove("buffer");
			wordsMap.remove("crowd");
			wordsMap.remove("front");  
			wordsMap.remove("safety");  
			wordsMap.remove("thick");         
		}
		
		return wordsMap;
	}
	
}
