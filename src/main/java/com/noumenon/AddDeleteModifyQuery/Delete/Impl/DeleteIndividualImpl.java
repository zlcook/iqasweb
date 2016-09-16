package com.noumenon.AddDeleteModifyQuery.Delete.Impl;

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
import com.noumenon.AddDeleteModifyQuery.Delete.DeleteIndividual;

public class DeleteIndividualImpl implements DeleteIndividual {

	private static String SERVER = "http://localhost:3030/EnglishLearningDataset/query";
	private static String UPDATE_SERVER = "http://localhost:3030/EnglishLearningDataset/update";

	public DeleteIndividualImpl() {

	}

	@Override
	public void deleteInstanceProperty(String yourInstanceLabel,
			String property, String propertyValue) {

		if (yourInstanceLabel.contains("@")) {
			yourInstanceLabel = subStringManag(yourInstanceLabel);
		}
		if (propertyValue.contains("@")) {
			propertyValue = subStringManag(propertyValue);
		}

		deleteProperty("6", "31", yourInstanceLabel, property, propertyValue);

	}

	@Override
	public void deleteClass(String yourInstanceLabel,
			String yourInstanceClass) {

		if (yourInstanceLabel.contains("@")) {
			yourInstanceLabel = subStringManag(yourInstanceLabel);
		}
		if (yourInstanceClass.contains("@")) {
			yourInstanceClass = subStringManag(yourInstanceClass);
		}
		
		QuerySolution solutionClass = findClassURL(yourInstanceClass);

		deleteClass("6", "31", yourInstanceLabel, solutionClass);

	}
	
	@Override
	public void deleteLabel(String yourInstanceLabel) {

		if (yourInstanceLabel.contains("@")) {
			yourInstanceLabel = subStringManag(yourInstanceLabel);
		}

		deleteLabel("6", "31", yourInstanceLabel);

	}
	
	

	@Override
	public void deleteSentenceInstanceProperty(String yourInstanceLabel,
			String property, String propertyValue) {

		if (yourInstanceLabel.contains("@")) {
			yourInstanceLabel = subStringManag(yourInstanceLabel);
		}
		if (propertyValue.contains("@")) {
			propertyValue = subStringManag(propertyValue);
		}

		deleteProperty("8", "85", yourInstanceLabel, property, propertyValue);

	}

	@Override
	public void deleteSentenceClass(String yourInstanceLabel,
			String yourInstanceClass) {
		if (yourInstanceLabel.contains("@")) {
			yourInstanceLabel = subStringManag(yourInstanceLabel);
		}
		if (yourInstanceClass.contains("@")) {
			yourInstanceClass = subStringManag(yourInstanceClass);
		}
		
		QuerySolution solutionClass = findClassURL(yourInstanceClass);

		deleteClass("8", "85", yourInstanceLabel, solutionClass);

	}
	
	@Override
	public void deleteSentenceLabel(String yourInstanceLabel) {
		if (yourInstanceLabel.contains("@")) {
			yourInstanceLabel = subStringManag(yourInstanceLabel);
		}
		
		deleteLabel("8", "85", yourInstanceLabel);

	}

	// 静态方法---------------------------------------------------------------------------------------------------------------------------------------

	// 查找类的url
	private static QuerySolution findClassURL(String yourClass) {
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext() == true)
			solutionClass = resultsClass.next();// Moves onto the next result.
		return solutionClass;
	}
	
	// 根据SPARQL语言查询，并返回结果集
		private static ResultSet Result(String sparql) {
			Query queryInstance = QueryFactory.create(sparql);
			QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
					SERVER, queryInstance);
			ResultSet results = qexecInstance.execSelect();
			return results;
		}

	//处理字符串
	public static String subStringManag(String string) {
		string = string.substring(0, string.indexOf("@"));
		return string;
	}

	// 删除所有属性
	public static void deleteProperty(String flag1, String flag2,
			String yourInstanceLabel, String property, String propertyValue) {
		String sparqlDeleteInstance = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/"
				+ flag1
				+ "/untitled-ontology-"
				+ flag2
				+ "#'"
				+ yourInstanceLabel
				+ "'@zh> <"
				+ property
				+ "> \""
				+ propertyValue + "\"@zh}";
		executeSPARQL(sparqlDeleteInstance);
	}

	// 删除类
	public static void deleteClass(String flag1, String flag2,
			String yourInstanceLabel, QuerySolution solutionClass) {
		String sparqlDeleteInstanceClass = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/"
				+ flag1
				+ "/untitled-ontology-"
				+ flag2
				+ "#'"
				+ yourInstanceLabel
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class") 
				+ ">}";
		executeSPARQL(sparqlDeleteInstanceClass);
	}

	// 删除Label
	public static void deleteLabel(String flag1, String flag2,
			String yourInstanceLabel) {
		String sparqlDeleteInstanceLabel = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/"
				+ flag1
				+ "/untitled-ontology-"
				+ flag2
				+ "#'"
				+ yourInstanceLabel
				+ "'@zh> <"
				+ "http://www.w3.org/2000/01/rdf-schema#label"
				+ "> \""
				+ yourInstanceLabel + "\"@zh}";
		executeSPARQL(sparqlDeleteInstanceLabel);

	}

	// 执行SPARQL语言
	private static void executeSPARQL(String sparql) {
		UpdateRequest updateInstance = UpdateFactory.create(sparql);
		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
				updateInstance, UPDATE_SERVER);
		qexecInstance.execute();
	}

}
