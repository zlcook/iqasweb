package com.cnu.offline.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.util.ArrayUtil;

import com.cnu.offline.service.QueryWordFromDataBase;
import com.cnu.offline.utils.ResourceConfigParseUntils;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.mysql.fabric.xmlrpc.base.Array;
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
		//获取配置文件中，需要学习的单词
		 List<String> studyWords =null;
		try {
			//android端要学习的单词，在studyword.properties中配置
			 studyWords =ResourceConfigParseUntils.getProsByKey("a-2-17-4",",");
		} catch (IOException e1) {
			throw new RuntimeException("从配置文件"+ResourceConfigParseUntils.PROFILEPATH+" 中读取学习单词信息失败",e1);
		}
		if(studyWords==null || studyWords.size()<=0)
			return null;
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
						//添加需要学习的单词
						if( studyWords.contains(pe.getInstanceLabel()) )
						   wordsMap.put(pe.getInstanceLabel(), pe);
					}
				}
			}
		} else {
			log.error("知识本体库中没有此实例");
			return null;
		}
		
		//如何是旅游交通主题，且实际年级是4年级，只取其中24中不同单词
		/**wheel,by_train,West_Lake,way,Disneyland,travel,on_foot,transportation,row_a_boat,bicycle,airport,by_boat,by_subway,
		 * map,trip,train_station,bus_stop,by_plane,boat,by_taxi,go_on_a_trip,visit_the_Mogao_Caves,by_bike,by_car
		 * 
		 */
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
