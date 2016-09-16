package com.noumenon.Integration.Impl;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.vocabulary.RDF;
import com.noumenon.Integration.AddDeleteModifyQueryInterface;

public class AddDeleteModifyQueryImpl implements AddDeleteModifyQueryInterface{

	private static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";
	private static String UPDATE_SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/update";

	public AddDeleteModifyQueryImpl() {

	}

	// 增加——————————————————————————————————————————————————————————————————————————————————————————————————
	public void addInstance(String yourClass, String yourInstance) {
		// 查找属性值url
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext())
			solutionClass = resultsClass.next();

		// 添加实例父类
		String sparqlAddInstance = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class") + ">}";
		System.out.println(sparqlAddInstance);
		// 添加实例Label
		String sparqlAddInstanceLabel = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh>"
				+ "<http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourInstance + "\"@zh.}";
		UpdateRequest updateInstance = UpdateFactory.create(sparqlAddInstance);
		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
				updateInstance, UPDATE_SERVER);
		qexecInstance.execute();

		UpdateRequest updateInstanceLabel = UpdateFactory
				.create(sparqlAddInstanceLabel);
		UpdateProcessor qexecInstanceLabel = UpdateExecutionFactory
				.createRemote(updateInstanceLabel, UPDATE_SERVER);
		qexecInstanceLabel.execute();
	}

	public void addProperty(String propertyLabel, String yourInstance,
			String yourProperty) {
		// 查找属性url
		String sparqlProperty = "SELECT ?Property WHERE{?Property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel + "\"@zh.}";
		ResultSet results = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (results.hasNext())
			solutionProperty = results.next();

		// 添加属性
		String stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?Property")
				+ "> \"" + yourProperty + "\"@zh.}";
		UpdateRequest updateProperty = UpdateFactory.create(stringProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();
	}

	// 删除实例——————————————————————————————————————————————————————————————————————————————————————————————————
	public void deleteInstance(String yourClass, String yourInstance) {
		// 查找属性url
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext())
			solutionClass = resultsClass.next();

		// 删除实例父类
		String sparqlDeleteInstance = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class") + ">}";
		UpdateRequest updateInstance = UpdateFactory
				.create(sparqlDeleteInstance);
		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
				updateInstance, UPDATE_SERVER);
		qexecInstance.execute();
		// 删除实例Label
		String sparqlDeleteInstanceLabel = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh>"
				+ "<http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourInstance + "\"@zh.}";
		UpdateRequest updateInstanceLabel = UpdateFactory
				.create(sparqlDeleteInstanceLabel);
		UpdateProcessor qexecInstanceLabel = UpdateExecutionFactory
				.createRemote(updateInstanceLabel, UPDATE_SERVER);
		qexecInstanceLabel.execute();
	}

	public void deleteProperty(String yourInstance, String yourProperty) {

		// 查找属性URI
		String sparqlProperty = "SELECT ?property WHERE{?property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourProperty + "\"@zh.}";
		ResultSet resultsProperty = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (resultsProperty.hasNext())
			solutionProperty = resultsProperty.next();
		System.out.println(sparqlProperty);
		System.out.println(solutionProperty.get("?property"));
		// 查找属性值
		// String sparqlPropertyValue = "SELECT ?propertyValue WHERE{?property"
		// + solutionProperty.get("?property")+ "?propertyValue}";
		String sparqlPropertyValue = "SELECT ?propertyValue WHERE{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + "?propertyValue}";
		ResultSet resultsPropertyValue = Result(sparqlPropertyValue);
		QuerySolution solutionPropertyValue = null;
		while (resultsPropertyValue.hasNext())
			solutionPropertyValue = resultsPropertyValue.next();
		System.out.println(sparqlPropertyValue);
		System.out.println(solutionPropertyValue.get("?propertyValue"));

		String string1 = solutionPropertyValue.toString();
		int i = string1.length();
		i = i - 2;
		String string2 = (String) string1.subSequence(19, i);
		System.out.println(string2);

		// 删除实例属性
		String sparqlDeleteProperty = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + string2 + "}";
		System.out.println(sparqlDeleteProperty);

		UpdateRequest updateProperty = UpdateFactory
				.create(sparqlDeleteProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();

	}

	// 修改——————————————————————————————————————————————————————————————————————————————————————————————————
	public void ModifyInstance(String yourProperty, String yourInstance,
			String propertyLabel) {

		// 查找属性URI
		String sparqlProperty = "SELECT ?property WHERE{?property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel + "\"@zh.}";
		ResultSet resultsProperty = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (resultsProperty.hasNext())
			solutionProperty = resultsProperty.next();
		System.out.println(sparqlProperty);
		System.out.println(solutionProperty.get("?property"));
		// 查找属性值
		// String sparqlPropertyValue = "SELECT ?propertyValue WHERE{?property"
		// + solutionProperty.get("?property")+ "?propertyValue}";
		String sparqlPropertyValue = "SELECT ?propertyValue WHERE{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + "?propertyValue}";
		ResultSet resultsPropertyValue = Result(sparqlPropertyValue);
		QuerySolution solutionPropertyValue = null;
		while (resultsPropertyValue.hasNext())
			solutionPropertyValue = resultsPropertyValue.next();
		System.out.println(sparqlPropertyValue);
		System.out.println(solutionPropertyValue.get("?propertyValue"));

		String string1 = solutionPropertyValue.toString();
		int i = string1.length();
		i = i - 2;
		String string2 = (String) string1.subSequence(19, i);
		System.out.println(string2);

		// 删除实例属性
		String sparqlDeleteProperty = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + string2 + "}";
		System.out.println(sparqlDeleteProperty);

		UpdateRequest updateProperty = UpdateFactory
				.create(sparqlDeleteProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();

		String sparqlProperty1 = "SELECT ?Property WHERE{?Property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel + "\"@zh.}";
		ResultSet results = Result(sparqlProperty1);
		QuerySolution solutionProperty1 = null;
		while (results.hasNext())
			solutionProperty1 = results.next();

		// 添加属性
		String stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty1.get("?Property")
				+ "> \"" + yourProperty + "\"@zh}";
		System.out.println(stringProperty);
		UpdateRequest updateProperty1 = UpdateFactory.create(stringProperty);
		UpdateProcessor qexecProperty1 = UpdateExecutionFactory.createRemote(
				updateProperty1, UPDATE_SERVER);
		qexecProperty1.execute();
	}

	// 查找——————————————————————————————————————————————————————————————————————————————————————————————————
	public ResultSet checkInstance(String yourClass) {

		String string1 = "SELECT ?instanceLabel WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel}";
		String sparqlInstance = string1 + yourClass + string2;
		System.out.println(sparqlInstance);

		ResultSet resultsInstance = Result(sparqlInstance);
		return resultsInstance;
//		while (resultsInstance.hasNext()) {
//			QuerySolution solutionInstance = resultsInstance.next();
//			System.out.println("类：" + yourClass + "\n" + "    ————实例："
//					+ solutionInstance.get("?instanceLabel") + "\n");
//		}
	}

	public void checkProperty(String yourClass) {
		// 输入找实例
		String string1 = "SELECT ?instanceLabel ?propertyID ?propertyChinese ?propertyFunction ?propertyTopic ?propertyBook ?propertyAntonym ?propertySynonyms ?propertyCommonUse ?propertyExtend ?propertyScene ?propertyExpand ?propertyVersion ?propertyUse ?propertyNcyclopedia ?propertyAssociat ?propertyPartsOfSpeeche ?propertyWordProperty ?propertyText ?propertyDifficulty WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."
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
		String sparqlInstance = string1 + yourClass + string2;
		// System.out.println(sparqlInstance);

		// Results from a query in a table-like manner for SELECT queries.
		// 在SELECT查询的类表方法中的查询结果
		// Each row corresponds to a set of bindings which fulfil the conditions
		// of the query.
		// 每一列对应一个绑定集，其中执行查询条件
		// Access to the results is by variable name.
		// 通过变量名访问结果
		ResultSet resultsInstance = Result(sparqlInstance);

		while (resultsInstance.hasNext()) {
			// QuerySolution next()
			// Moves onto the next result.
			// 移动到下个result上
			QuerySolution solutionInstance = resultsInstance.next();

			System.out.println("类：" + yourClass + "\n" + "    ————实例："
					+ solutionInstance.get("?instanceLabel") + "\n"
					+ "    ————ID：" + solutionInstance.get("?propertyID")
					+ "\n" + "    ————中文含义："
					+ solutionInstance.get("?propertyChinese") + "\n"
					+ "    ————主题-功能意念："
					+ solutionInstance.get("?propertyFunction") + "\n"
					+ "    ————主题-话题：" + solutionInstance.get("?propertyTopic")
					+ "\n" + "    ————册数："
					+ solutionInstance.get("?propertyBook") + "\n"
					+ "    ————反义词：" + solutionInstance.get("?propertyAntonym")
					+ "\n" + "    ————同义词："
					+ solutionInstance.get("?propertySynonyms") + "\n"
					+ "    ————常用："
					+ solutionInstance.get("?propertyCommonUse") + "\n"
					+ "    ————延伸例句：" + solutionInstance.get("?propertyExtend")
					+ "\n" + "    ————情境段落："
					+ solutionInstance.get("?propertyScene") + "\n"
					+ "    ————拓展：" + solutionInstance.get("?propertyExpand")
					+ "\n" + "    ————教材版本："
					+ solutionInstance.get("?propertyVersion") + "\n"
					+ "    ————用法：" + solutionInstance.get("?propertyUse")
					+ "\n" + "    ————百科："
					+ solutionInstance.get("?propertyNcyclopedia") + "\n"
					+ "    ————联想："
					+ solutionInstance.get("?propertyAssociate") + "\n"
					+ "    ————词性："
					+ solutionInstance.get("?propertyPartsOfSpeech") + "\n"
					+ "    ————词性属性："
					+ solutionInstance.get("?propertyWordProperty") + "\n"
					+ "    ————课文原句：" + solutionInstance.get("?propertyText")
					+ "\n" + "    ————难度："
					+ solutionInstance.get("?propertyDifficulty"));
		}
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
