package com.cnu.offline.service.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.cnu.offline.service.QueryWordFromOntology;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
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
		//存放单词word和单词实体,只保留不同的单词，如果有重复会只取一个。
		Hashtable<String,PropertyEntity> wordsMap=null;
		
		//List<PropertyEntity> listpe = new ArrayList<>();
		List<ResultSet> resultsAllBrother = ontologyManage.QueryBrotherIndividual(grade+"", theme);
		if( resultsAllBrother!=null &resultsAllBrother.size()>0){
			wordsMap= new Hashtable<>();
			for (int i = 0; i < resultsAllBrother.size(); i++) {
				if (resultsAllBrother.get(i).hasNext()) {
					while (resultsAllBrother.get(i).hasNext()) {
						QuerySolution solutionEachBrother = resultsAllBrother.get(i).next();
						PropertyEntity pe= PropertyEntity.generatePropertyEntity(solutionEachBrother);
						wordsMap.put(pe.getInstanceLabel(), pe);
						//listpe.add(pe);
					}
				}
			}
		} else {
			System.out.println("知识本体库中没有此实例");
			return null;
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
