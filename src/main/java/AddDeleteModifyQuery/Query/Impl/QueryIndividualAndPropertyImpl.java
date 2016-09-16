package AddDeleteModifyQuery.Query.Impl;

import java.util.Scanner;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import AddDeleteModifyQuery.Query.QueryIndividualAndProperty;

public class QueryIndividualAndPropertyImpl implements
		QueryIndividualAndProperty {

	// 定义fuseki数据库连接地址/EnglishLearningDataset query
	public static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";

	public QueryIndividualAndPropertyImpl() {

	}

	public ResultSet checkProperty(String yourWord) {
		// 输入找实例——————————————————————————————————————————————————————————————————————————————————————————————————
		String string1 = "SELECT ?propertyClass ?propertyID ?propertyChinese ?propertyFunction ?propertyTopic ?propertyBook ?propertyAntonym ?propertySynonyms ?propertyCommonUse ?propertyExtend ?propertyScene ?propertyExpand ?propertyVersion ?propertyUse ?propertyNcyclopedia ?propertyAssociate ?propertyPartsOfSpeeche ?propertyWordProperty ?propertyText ?propertyDifficulty WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?class <http://www.w3.org/2000/01/rdf-schema#label> ?propertyClass."
				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"ID\"@zh."
				+ "?instance ?relationID ?propertyID."
				+ "?relationChinese <http://www.w3.org/2000/01/rdf-schema#label> \"中文含义\"@zh."
				+ "?instance ?relationChinese ?propertyChinese."
				+ "?relationFunction <http://www.w3.org/2000/01/rdf-schema#label> \"主题-功能意念\"@zh."
				+ "?instance ?relationFunction ?propertyFunction."
				+ "?relationTopic <http://www.w3.org/2000/01/rdf-schema#label> \"主题-话题\"@zh."
				+ "?instance ?relationTopic ?propertyTopic."
				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"册数\"@zh."
				+ "?instance ?relationBook ?propertyBook."
				+ "?relationAntonym <http://www.w3.org/2000/01/rdf-schema#label> \"反义词\"@zh."
				+ "?instance ?relationAntonym ?propertyAntonym."
				+ "?relationSynonyms <http://www.w3.org/2000/01/rdf-schema#label> \"同义词\"@zh."
				+ "?instance ?relationSynonyms ?propertySynonyms."
				+ "?relationCommonUse <http://www.w3.org/2000/01/rdf-schema#label> \"常用\"@zh."
				+ "?instance ?relationCommonUse ?propertyCommonUse."
				+ "?relationExtend <http://www.w3.org/2000/01/rdf-schema#label> \"延伸例句\"@zh."
				+ "?instance ?relationExtend ?propertyExtend."
				+ "?relationScene <http://www.w3.org/2000/01/rdf-schema#label> \"情境段落\"@zh."
				+ "?instance ?relationScene ?propertyScene."
				+ "?relationExpand <http://www.w3.org/2000/01/rdf-schema#label> \"拓展\"@zh."
				+ "?instance ?relationExpand ?propertyExpand."
				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"教材版本\"@zh."
				+ "?instance ?relationVersion ?propertyVersion."
				+ "?relationUse <http://www.w3.org/2000/01/rdf-schema#label> \"用法\"@zh."
				+ "?instance ?relationUse ?propertyUse."
				+ "?relationNcyclopedia <http://www.w3.org/2000/01/rdf-schema#label> \"百科\"@zh."
				+ "?instance ?relationNcyclopedia ?propertyNcyclopedia."
				+ "?relationAssociate <http://www.w3.org/2000/01/rdf-schema#label> \"联想\"@zh."
				+ "?instance ?relationAssociate ?propertyAssociate."
				+ "?relationPartsOfSpeech <http://www.w3.org/2000/01/rdf-schema#label> \"词性\"@zh."
				+ "?instance ?relationPartsOfSpeech ?propertyPartsOfSpeech."
				+ "?relationWordProperty <http://www.w3.org/2000/01/rdf-schema#label> \"词性属性\"@zh."
				+ "?instance ?relationWordProperty ?propertyWordProperty."
				+ "?relationText <http://www.w3.org/2000/01/rdf-schema#label> \"课文原句\"@zh."
				+ "?instance ?relationText ?propertyText."
				+ "?relationDifficulty <http://www.w3.org/2000/01/rdf-schema#label> \"难度\"@zh."
				+ "?instance ?relationDifficulty ?propertyDifficulty." + "}";
		String sparqlInstance = string1 + yourWord + string2;
		//System.out.println(sparqlInstance);

		// Results from a query in a table-like manner for SELECT queries.
		// 在SELECT查询的类表方法中的查询结果
		// Each row corresponds to a set of bindings which fulfil the conditions
		// of the query.
		// 每一列对应一个绑定集，其中执行查询条件
		// Access to the results is by variable name.
		// 通过变量名访问结果
		ResultSet resultsInstance = Result(sparqlInstance);

		return resultsInstance;
	}

	private static ResultSet Result(String sparql) {
		// public static Query create(String queryString)
		// Create a SPARQL query from the given string.
		// 从给定的string中创建一个SPARQL查询
		Query queryInstance = QueryFactory.create(sparql);
		// public static QueryExecution sparqlService(String service,Query
		// query)
		// Create a QueryExecution that will access a SPARQL service over HTTP
		// 创建一个QueryExecution，它将在HTTP上访问SPARQL服务
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		// ResultSet execSelect()
		// Execute a SELECT query
		// 执行一个SELECT查询
		// Important: The name of this method is somewhat of a misnomer in that
		// depending on the underlying implementation this typically does not
		// execute the SELECT query but rather answers a wrapper over an
		// internal data structure that can be used to answer the query. In
		// essence calling this method only returns a plan for executing this
		// query which only gets evaluated when you actually start iterating
		// over the results.
		ResultSet results = qexecInstance.execSelect();
		return results;
	}

}
