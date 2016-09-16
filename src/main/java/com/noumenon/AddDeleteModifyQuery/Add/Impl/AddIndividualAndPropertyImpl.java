package com.noumenon.AddDeleteModifyQuery.Add.Impl;

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
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.noumenon.AddDeleteModifyQuery.Add.AddIndividualAndProperty;

public class AddIndividualAndPropertyImpl implements AddIndividualAndProperty {
	// private static String UPDATE_SERVER =
		// "http://127.0.0.1:3030/EnglishLearningDataset/update";

		private static String SERVER = "http://localhost:3030/EnglishLearningDataset_FourthGrade/query";
		private static String UPDATE_SERVER = "http://localhost:3030/EnglishLearningDataset_FourthGrade/update";
		private static String ID = null;

		// 添加单词父类-----------------------------------------------------------------------------------------------------------------------------
		@Deprecated
		public void addClass(String yourClass, String yourInstance) {
			if (yourClass.contains("@")) {
				yourClass = subStringManag(yourClass);
			}
			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}

			QuerySolution solutionClass = findClassURL(yourClass);

			// 添加单词实例父类
			//addInstanceClass(yourInstance, solutionClass, "6", "31");
			addInstanceClass(yourInstance, solutionClass, "word");

			System.out.println("单词类添加成功");
		}

		// 添加单词父类-----------------------------------------------------------------------------------------------------------------------------
		public void addWordClass(String yourClass, String yourInstance) {
			if (yourClass.contains("@")) {
				yourClass = subStringManag(yourClass);
			}
			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}

			QuerySolution solutionClass = findClassURL(yourClass);

			// 添加单词实例父类
			//addInstanceClass(yourInstance, solutionClass, "6", "31");
			addInstanceClass(yourInstance, solutionClass, "word");

			System.out.println("单词类添加成功");
		}

		// 添加单词Label-----------------------------------------------------------------------------------------------------------------------------
		@Deprecated
		public void addLabel(String yourInstance) {

			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}

			// 添加单词实例Label
			//addInstanceLable(yourInstance, "6", "31");
			addInstanceLable(yourInstance, "word");

			System.out.println("单词Label添加成功");
		}

		// 添加单词Label-----------------------------------------------------------------------------------------------------------------------------
		public void addWordLabel(String yourInstance) {

			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}

			// 添加单词实例Label
			//addInstanceLable(yourInstance, "6", "31");
			addInstanceLable(yourInstance, "word");

			System.out.println("单词Label添加成功");
		}

		// 添加单词属性-----------------------------------------------------------------------------------------------------------------------------------
		@Deprecated
		public void addProperty(String propertyLabel, String yourInstance,
				String yourProperty) {
			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}
			if (yourProperty.contains("@")) {
				yourProperty = subStringManag(yourProperty);
				if (yourProperty.contains("(" + ID + ")")) {
					yourProperty = yourProperty
							.substring(yourProperty.indexOf(")"));
				}
			}

			if (propertyLabel.equals("单词ID")) {
				ID = yourProperty;
				// 查找单词属性url
				QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

				// 添加单词属性
//				addIDProperty(yourInstance, solutionProperty, yourProperty, "6",
//						"31");
				addIDProperty(yourInstance, solutionProperty, yourProperty, "word");

				System.out.println(yourInstance + propertyLabel + "属性添加成功");
			} else {
				// 查找单词属性url
				QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

				// 添加单词属性
//				addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
//						"6", "31");
				addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
						"word");

				System.out.println(yourInstance + propertyLabel + "属性添加成功");
			}
		}

		// 添加单词属性-----------------------------------------------------------------------------------------------------------------------------------
		public void addWordProperty(String propertyLabel, String yourInstance,
				String yourProperty) {
			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}
			if (yourProperty.contains("@")) {
				yourProperty = subStringManag(yourProperty);
				if (yourProperty.contains("(" + ID + ")")) {
					yourProperty = yourProperty
							.substring(yourProperty.indexOf(")"));
				}
			}

			if (propertyLabel.equals("单词ID")) {
				ID = yourProperty;
				// 查找单词属性url
				QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

				// 添加单词属性
//				addIDProperty(yourInstance, solutionProperty, yourProperty, "6",
//						"31");
				addIDProperty(yourInstance, solutionProperty, yourProperty, "word");

				System.out.println(yourInstance + propertyLabel + "属性添加成功");
			} else {
				// 查找单词属性url
				QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

				// 添加单词属性
//				addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
//						"6", "31");
				addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
						"word");

				System.out.println(yourInstance + propertyLabel + "属性添加成功");
			}
		}

		// 为修改单词设计的添加方法-----------------------------------------------------------------------------------------------------------------------------------
		@Override
		public void addPropertyForModify(String propertyLabel, String yourInstance,
				String yourProperty, String yourID) {
			ID = yourID;
			addProperty(propertyLabel, yourInstance, yourProperty);
		}

		// 添加句子类---------------------------------------------------------------------------------------------------------------------------------
		public void addSentenceClass(String yourClass, String yourInstance) {

			if (yourClass.contains("@")) {
				yourClass = subStringManag(yourClass);
			}
			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}

			// 查找句子类的url
			QuerySolution solutionClass = findClassURL(yourClass);

			// 添加句子实例父类
			//addInstanceClass(yourInstance, solutionClass, "8", "85");
			addInstanceClass(yourInstance, solutionClass, "sentence");

			System.out.println("句子父类添加成功");
		}

		// 添加句子Label---------------------------------------------------------------------------------------------------------------------------------
		public void addSentenceLabel(String yourInstance) {

			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}

			// 添加句子实例Label
			//addInstanceLable(yourInstance, "8", "85");
			addInstanceLable(yourInstance, "sentence");

			System.out.println("句子Label添加成功");
		}

		// 添加句子属性-----------------------------------------------------------------------------------------------------------------------------------
		public void addSentenceProperty(String propertyLabel, String yourInstance,
				String yourProperty) {

			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}
			if (yourProperty.contains("@")) {
				yourProperty = subStringManag(yourProperty);
				if (yourProperty.contains("(" + ID + ")")) {
					yourProperty = yourProperty
							.substring(yourProperty.indexOf(")"));
				}
			}

			// 查找句子属性url
			QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

			if (propertyLabel.equals("句子ID")) {

				ID = yourProperty;
				// 添加句子属性
//				addIDProperty(yourInstance, solutionProperty, yourProperty, "8",
//						"85");
				addIDProperty(yourInstance, solutionProperty, yourProperty, "sentence");

				System.out.println(yourInstance + propertyLabel + "属性添加成功");
			} else {
				// 添加句子属性
//				addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
//						"8", "85");
				addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
						"sentence");

				System.out.println(yourInstance + propertyLabel + "属性添加成功");
			}
		}

		// 为修改句子设计的添加方法-----------------------------------------------------------------------------------------------------------------------------------
		@Override
		public void addSentencePropertyForModify(String propertyLabel,
				String yourInstance, String yourProperty, String yourID) {
			if (yourInstance.contains("@")) {
				yourInstance = subStringManag(yourInstance);
			}
			ID = yourID;
			addSentenceProperty(propertyLabel, yourInstance, yourProperty);
		}

		// 添加等价关系sameAs
		@Override
		public void addRelationSameAs(String question1, String question2) {
			addSameAs(question1, question2);
		}

		// 静态方法****************************************************************************************************************************************
		// 处理字符串
		public static String subStringManag(String string) {
			string = string.substring(0, string.indexOf("@"));
			return string;
		}

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

		// 添加实例父类
		private static void addInstanceClass(String yourInstance,
				QuerySolution solutionClass, String flag) {
			String sparqlAddInstance = "insert data{<http://www.semanticweb.org/primaryEnglishOntology/fourthGrade/"
					+ flag
					+ "#'"
					+ yourInstance
					+ "'@zh> <"
					+ RDF.type
					+ "> <"
					+ solutionClass.get("?class")
					+ ">}";
			executeSPARQL(sparqlAddInstance);
		}

		// 添加实例Label
		private static void addInstanceLable(String yourInstance, String flag) {
			String sparqlAddInstanceLabel = "insert data{<http://www.semanticweb.org/primaryEnglishOntology/fourthGrade/"
					+ flag
					+ "#'"
					+ yourInstance
					+ "'@zh>"
					+ "<http://www.w3.org/2000/01/rdf-schema#label> \""
					+ yourInstance + "\"@zh.}";
			executeSPARQL(sparqlAddInstanceLabel);
		}

		// 查找属性url
		private static QuerySolution queryPropertyURL(String propertyLabel) {
			String sparqlProperty = "SELECT ?Property WHERE{?Property <http://www.w3.org/2000/01/rdf-schema#label> \""
					+ propertyLabel + "\"@zh.}";
			ResultSet results = Result(sparqlProperty);
			QuerySolution solutionProperty = null;
			while (results.hasNext()) {
				solutionProperty = results.next();
			}
			return solutionProperty;
		}

		// 添加ID属性
		private static void addIDProperty(String yourInstance,
				QuerySolution solutionProperty, String yourProperty, String flag) {
			String stringProperty = "insert data{<http://www.semanticweb.org/primaryEnglishOntology/fourthGrade/"
					+ flag
					+ "#'"
					+ yourInstance
					+ "'@zh> <"
					+ solutionProperty.get("?Property")
					+ "> \""
					+ yourProperty + "\"@zh.}";

			executeSPARQL(stringProperty);
		}

		// 添加除ID的其他属性
		private static void addOthersProperty(String yourInstance,
				QuerySolution solutionProperty, String yourProperty, String wordID,
				String flag) {
			String stringProperty = "insert data{<http://www.semanticweb.org/primaryEnglishOntology/fourthGrade/"
					+ flag
					+ "#'"
					+ yourInstance
					+ "'@zh> <"
					+ solutionProperty.get("?Property")
					+ "> \"("
					+ wordID + ")" + yourProperty + "\"@zh.}";

			executeSPARQL(stringProperty);
		}

		// 添加等价关系
		private static void addSameAs(String question1, String question2) {
			String sparqlAddSameAs = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85#'"
					+ question1
					+ "'@zh> <"
					+ OWL.sameAs
					+ "> <http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85#'"
					+ question2 + "'@zh>.}";
			executeSPARQL(sparqlAddSameAs);
		}

		// 根据SPARQL语言查询，并返回结果集
		private static ResultSet Result(String sparql) {
			Query queryInstance = QueryFactory.create(sparql);
			QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
					SERVER, queryInstance);
			ResultSet results = qexecInstance.execSelect();
			return results;
		}

		// 执行SPARQL语言
		private static void executeSPARQL(String sparql) {
			UpdateRequest updateInstance = UpdateFactory.create(sparql);
			UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
					updateInstance, UPDATE_SERVER);
			qexecInstance.execute();
		}
}
