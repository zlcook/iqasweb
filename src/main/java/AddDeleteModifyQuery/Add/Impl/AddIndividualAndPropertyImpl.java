package AddDeleteModifyQuery.Add.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import AddDeleteModifyQuery.Add.AddIndividualAndProperty;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.function.library.substring;

import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.vocabulary.RDF;

public class AddIndividualAndPropertyImpl implements AddIndividualAndProperty {
	
	//private static String UPDATE_SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/update";

	private static String SERVER = "http://localhost:3030/EnglishLearningDataset/query";
	private static String UPDATE_SERVER = "http://localhost:3030/EnglishLearningDataset/update";

	public void addInstance(String yourClass, String yourInstance) {
		// 查找类的url
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext() == true)
			solutionClass = resultsClass.next();// Moves onto the next result.

		// 添加实例父类
		String sparqlAddInstance = null;

		sparqlAddInstance = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class") + ">}";

		System.out.println(sparqlAddInstance);

		// 添加实例Label
		String sparqlAddInstanceLabel = null;

		sparqlAddInstanceLabel = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
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
		System.out.println("单词Label添加成功");
	}

//	@Override
//	public void addInstance(String yourInstance) {
//		
//		String yourClass = findDEF(yourInstance);
//		System.out.println("你选择的父类（返回）：" + yourClass);
//
//		// 查找类的url
//		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
//				+ yourClass + "\"@zh.}";
//
//		ResultSet resultsClass = Result(sparqlClass);
//		QuerySolution solutionClass = null;
//		while (resultsClass.hasNext() == true)
//			solutionClass = resultsClass.next();// Moves onto the next result.
//
//		// 添加实例父类
//		String sparqlAddInstance = null;
//
//		sparqlAddInstance = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
//				+ yourInstance
//				+ "'@zh> <"
//				+ RDF.type
//				+ "> <"
//				+ solutionClass.get("?class") + ">}";
//
//		System.out.println(sparqlAddInstance);
//
//		// 添加实例Label
//		String sparqlAddInstanceLabel = null;
//
//		sparqlAddInstanceLabel = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
//				+ yourInstance
//				+ "'@zh>"
//				+ "<http://www.w3.org/2000/01/rdf-schema#label> \""
//				+ yourInstance + "\"@zh.}";
//		UpdateRequest updateInstance = UpdateFactory.create(sparqlAddInstance);
//		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
//				updateInstance, UPDATE_SERVER);
//		qexecInstance.execute();
//
//		UpdateRequest updateInstanceLabel = UpdateFactory
//				.create(sparqlAddInstanceLabel);
//		UpdateProcessor qexecInstanceLabel = UpdateExecutionFactory
//				.createRemote(updateInstanceLabel, UPDATE_SERVER);
//		qexecInstanceLabel.execute();
//		System.out.println("单词Label添加成功");
//
//	}

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
		String stringProperty = null;

		stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?Property")
				+ "> \"" + yourProperty + "\"@zh.}";

		UpdateRequest updateProperty = UpdateFactory.create(stringProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();
		System.out.println(yourInstance + propertyLabel + "属性添加成功");
	}

	public void addSentenceInstance(String yourClass, String yourInstance) {
		// 查找类的url
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext() == true)
			solutionClass = resultsClass.next();// Moves onto the next result.

		// 添加实例父类
		String sparqlAddInstance = null;

		sparqlAddInstance = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85#'"
				+ yourInstance
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class") + ">}";

		System.out.println(sparqlAddInstance);

		// 添加实例Label
		String sparqlAddInstanceLabel = null;

		sparqlAddInstanceLabel = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85#'"
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
		System.out.println("句子Label添加成功");
	}

	public void addSentenceProperty(String propertyLabel, String yourInstance,
			String yourProperty) {
		// 查找属性url
		String sparqlProperty = "SELECT ?Property WHERE{?Property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel + "\"@zh.}";
		ResultSet results = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (results.hasNext())
			solutionProperty = results.next();

		// 添加属性
		String stringProperty = null;

		stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?Property")
				+ "> \"" + yourProperty + "\"@zh.}";

		UpdateRequest updateProperty = UpdateFactory.create(stringProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();
		System.out.println(yourInstance + propertyLabel + "属性添加成功");
	}

	private static ResultSet Result(String sparql) {
		Query queryInstance = QueryFactory.create(sparql);
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		ResultSet results = qexecInstance.execSelect(); 
		return results;
	}
}