package com.noumenon.AddDeleteModifyQuery.Query.Impl;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.vocabulary.OWL;
import com.noumenon.AddDeleteModifyQuery.Query.QueryWithManyWays;

public class QueryWithManyWaysImpl implements QueryWithManyWays {

	// 定义fuseki数据库连接地址/EnglishLearningDataset query
	// private static String SERVER =
	// "http://localhost:3030/EnglishLearningDataset/query";
	private static String SERVER = "http://localhost:3030/EnglishLearningDataset_FourthGrade/query";

	// 根据类查找所有实例Label
	@Override
	public ResultSet checkInstance(String yourClass) {
		String string1 = "SELECT ?instanceLabel WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel}";
		String sparqlInstance = string1 + yourClass + string2;
		// System.out.println(sparqlInstance);

		ResultSet resultsInstance = Result(sparqlInstance);
		return resultsInstance;
	}

	// 查找该父类下的所有ID
	public ResultSet checkIDDependOnClass(String yourClass) {
		String string1 = "SELECT ?propertyID WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"单词ID\"@zh." 
				+ "?instance ?relationID ?propertyID.}";
		String sparqlIDDependOnClass = string1 + yourClass + string2;
		// System.out.println(sparqlInstance);

		ResultSet resultsIDDependOnClass = Result(sparqlIDDependOnClass);
		return resultsIDDependOnClass;
	}

	// 根据实例名称查找单词所有属性值
	@Override
	public ResultSet checkProperty(String yourWord) {
		if (yourWord.contains("@")) {
			yourWord = yourWord.substring(0, yourWord.indexOf("@"));
		}

		// 输入找实例
		String string1 = "SELECT ?instanceLabel ?propertyClass ?propertyID ?propertyChinese ?propertyFunction ?propertyTopic ?propertyBook ?propertyAntonym ?propertySynonyms ?propertyCommonUse ?propertyExtend ?propertyScene ?propertyExpand ?propertyVersion ?propertyUse ?propertyNcyclopedia ?propertyAssociate ?propertyPartsOfSpeech ?propertyWordProperty ?propertyText ?propertyDifficulty "
				+ "WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";// 注意不要忘了空格！
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."

				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?class <http://www.w3.org/2000/01/rdf-schema#label> ?propertyClass."

				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"单词ID\"@zh."
				+ "?instance ?relationID ?propertyID."

				+ "?relationChinese <http://www.w3.org/2000/01/rdf-schema#label> \"中文含义\"@zh."
				+ "?instance ?relationChinese ?propertyChinese."

				+ "?relationFunction <http://www.w3.org/2000/01/rdf-schema#label> \"主题-功能意念\"@zh."
				+ "?instance ?relationFunction ?propertyFunction."

				+ "?relationTopic <http://www.w3.org/2000/01/rdf-schema#label> \"主题-话题\"@zh."
				+ "?instance ?relationTopic ?propertyTopic."

				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"单词册数\"@zh."
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

				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"单词教材版本\"@zh."
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
		// System.out.println(sparqlInstance);

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

	// 查询一个单词对应的所有ID
	@Override
	public ResultSet checkAllIdOfAnWord(String yourWord) {
		String string1 = "SELECT ?propertyID WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"单词ID\"@zh."
				+ "?instance ?relationID ?propertyID.}";
		String sparqlInstance = string1 + yourWord + string2;
		System.out.println(sparqlInstance);

		ResultSet resultsAllIdOfAInstance = Result(sparqlInstance);
		return resultsAllIdOfAInstance;
	}

	// 查询一个单词对应的所有ID
	@Override
	public ResultSet checkAllIdOfAnSentence(String yourSentence) {
		String string1 = "SELECT ?propertyID WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"句子ID\"@zh."
				+ "?instance ?relationID ?propertyID.}";
		String sparqlInstance = string1 + yourSentence + string2;
		// System.out.println(sparqlInstance);

		ResultSet resultsAllIdOfAnSentence = Result(sparqlInstance);
		return resultsAllIdOfAnSentence;
	}

	// 根据实例名称查找句子所有属性值
	@Override
	public ResultSet checkSentenceProperty(String yourSentence) {
		if (yourSentence.contains("@")) {
			yourSentence = yourSentence.substring(0, yourSentence.indexOf("@"));
		}

		// 输入找实例——————————————————————————————————————————————————————————————————————————————————————————————————
		String string1 = "SELECT ?instance ?instanceLabel ?propertyClass ?propertyAnswer ?propertyID ?propertyVersion ?propertyBook ?propertyScene ?propertySentencePattern ?propertyRelatedWords ?relationAnswer ?relationID ?relationVersion ?relationBook ?relationScene ?relationSentencePattern ?relationRelatedWords WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."

				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?class <http://www.w3.org/2000/01/rdf-schema#label> ?propertyClass."

				+ "?relationAnswer <http://www.w3.org/2000/01/rdf-schema#label> \"回答\"@zh."
				+ "?instance ?relationAnswer ?propertyAnswer."

				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"句子ID\"@zh."
				+ "?instance ?relationID ?propertyID."

				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"句子教材版本\"@zh."
				+ "?instance ?relationVersion ?propertyVersio."

				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"句子册数\"@zh."
				+ "?instance ?relationBook ?propertyBook."

				+ "?relationScene <http://www.w3.org/2000/01/rdf-schema#label> \"情境对话\"@zh."
				+ "?instance ?relationScene ?propertyScene."

				+ "?relationSentencePattern <http://www.w3.org/2000/01/rdf-schema#label> \"重要句型\"@zh."
				+ "?instance ?relationSentencePattern ?propertySentencePattern."

				+ "?relationRelatedWords <http://www.w3.org/2000/01/rdf-schema#label> \"相关单词\"@zh."
				+ "?instance ?relationRelatedWords ?propertyRelatedWords.}";

		String sparqlInstance = string1 + yourSentence + string2;
		// System.out.println(sparqlInstance);

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

	// 根据ID查询句子及其属性URI+属性值
	@Override
	public ResultSet checkSentencePropertyDependOnId(String yourId) {
		if (yourId.contains("@")) {
			yourId = yourId.substring(0, yourId.indexOf("@"));
		}

		// 输入找实例——————————————————————————————————————————————————————————————————————————————————————————————————
		String string1 = "SELECT ?instanceLabel ?propertyClass ?propertyAnswer ?propertyID ?propertyVersion ?propertyBook ?propertyScene ?propertySentencePattern ?propertyRelatedWords ?relationAnswer ?relationID ?relationVersion ?relationBook ?relationScene ?relationSentencePattern ?relationRelatedWords WHERE{ ?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"句子ID\"@zh. ?instance ?relationID \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."

				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?class <http://www.w3.org/2000/01/rdf-schema#label> ?propertyClass."

				+ "?relationAnswer <http://www.w3.org/2000/01/rdf-schema#label> \"回答\"@zh."
				+ "?instance ?relationAnswer ?propertyAnswer FILTER regex(?propertyAnswer, \""
				+ yourId
				+ "\")."

				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"句子ID\"@zh."
				+ "?instance ?relationID ?propertyID FILTER regex(?propertyID, \""
				+ yourId
				+ "\")."

				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"句子教材版本\"@zh."
				+ "?instance ?relationVersion ?propertyVersion FILTER regex(?propertyVersion, \""
				+ yourId
				+ "\")."

				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"句子册数\"@zh."
				+ "?instance ?relationBook ?propertyBook FILTER regex(?propertyBook, \""
				+ yourId
				+ "\")."

				+ "?relationScene <http://www.w3.org/2000/01/rdf-schema#label> \"情境对话\"@zh."
				+ "?instance ?relationScene ?propertyScene FILTER regex(?propertyScene, \""
				+ yourId
				+ "\")."

				+ "?relationSentencePattern <http://www.w3.org/2000/01/rdf-schema#label> \"重要句型\"@zh."
				+ "?instance ?relationSentencePattern ?propertySentencePattern FILTER regex(?propertySentencePattern, \""
				+ yourId
				+ "\")."

				+ "?relationRelatedWords <http://www.w3.org/2000/01/rdf-schema#label> \"相关单词\"@zh."
				+ "?instance ?relationRelatedWords ?propertyRelatedWords FILTER regex(?propertyRelatedWords, \""
				+ yourId + "\")." + "}";

		String sparqlInstance = string1 + yourId + string2;
		// System.out.println(sparqlInstance);

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

	// 根据ID查询单词及其属性URI+属性值
	@Override
	public ResultSet checkPropertyDependOnId(String yourId) {

		if (yourId.contains("@")) {
			yourId = yourId.substring(0, yourId.indexOf("@"));
		}

		String string1 = "SELECT ?instance ?instanceLabel ?propertyClass ?propertyID ?propertyChinese ?propertyFunction ?propertyTopic ?propertyBook ?propertyAntonym ?propertySynonyms ?propertyCommonUse ?propertyExtend ?propertyScene ?propertyExpand ?propertyVersion ?propertyUse ?propertyNcyclopedia ?propertyAssociate ?propertyPartsOfSpeech ?propertyWordProperty ?propertyText ?propertyDifficulty ?relationID ?relationChinese ?relationFunction ?relationTopic ?relationBook ?relationAntonym ?relationSynonyms ?relationCommonUse ?relationExtend ?relationScene ?relationExpand ?relationVersion ?relationUse ?relationNcyclopedia ?relationAssociate ?relationPartsOfSpeech ?relationWordProperty ?relationText ?relationDifficulty WHERE{?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"单词ID\"@zh. ?instance ?relationID \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."

				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?class <http://www.w3.org/2000/01/rdf-schema#label> ?propertyClass."

				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"单词ID\"@zh."
				+ "?instance ?relationID ?propertyID FILTER regex(?propertyID, \""
				+ yourId
				+ "\")."

				+ "?relationChinese <http://www.w3.org/2000/01/rdf-schema#label> \"中文含义\"@zh."
				+ "?instance ?relationChinese ?propertyChinese FILTER regex(?propertyChinese, \""
				+ yourId
				+ "\")."

				+ "?relationFunction <http://www.w3.org/2000/01/rdf-schema#label> \"主题-功能意念\"@zh."
				+ "?instance ?relationFunction ?propertyFunction FILTER regex(?propertyFunction, \""
				+ yourId
				+ "\")."

				+ "?relationTopic <http://www.w3.org/2000/01/rdf-schema#label> \"主题-话题\"@zh."
				+ "?instance ?relationTopic ?propertyTopic FILTER regex(?propertyTopic, \""
				+ yourId
				+ "\")."

				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"单词册数\"@zh."
				+ "?instance ?relationBook ?propertyBook FILTER regex(?propertyBook, \""
				+ yourId
				+ "\")."

				+ "?relationAntonym <http://www.w3.org/2000/01/rdf-schema#label> \"反义词\"@zh."
				+ "?instance ?relationAntonym ?propertyAntonym FILTER regex(?propertyAntonym, \""
				+ yourId
				+ "\")."

				+ "?relationSynonyms <http://www.w3.org/2000/01/rdf-schema#label> \"同义词\"@zh."
				+ "?instance ?relationSynonyms ?propertySynonyms FILTER regex(?propertySynonyms, \""
				+ yourId
				+ "\")."

				+ "?relationCommonUse <http://www.w3.org/2000/01/rdf-schema#label> \"常用\"@zh."
				+ "?instance ?relationCommonUse ?propertyCommonUse FILTER regex(?propertyCommonUse, \""
				+ yourId
				+ "\")."

				+ "?relationExtend <http://www.w3.org/2000/01/rdf-schema#label> \"延伸例句\"@zh."
				+ "?instance ?relationExtend ?propertyExtend FILTER regex(?propertyExtend, \""
				+ yourId
				+ "\")."

				+ "?relationScene <http://www.w3.org/2000/01/rdf-schema#label> \"情境段落\"@zh."
				+ "?instance ?relationScene ?propertyScene FILTER regex(?propertyScene, \""
				+ yourId
				+ "\")."

				+ "?relationExpand <http://www.w3.org/2000/01/rdf-schema#label> \"拓展\"@zh."
				+ "?instance ?relationExpand ?propertyExpand FILTER regex(?propertyExpand, \""
				+ yourId
				+ "\")."

				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"单词教材版本\"@zh."
				+ "?instance ?relationVersion ?propertyVersion FILTER regex(?propertyVersion, \""
				+ yourId
				+ "\")."

				+ "?relationUse <http://www.w3.org/2000/01/rdf-schema#label> \"用法\"@zh."
				+ "?instance ?relationUse ?propertyUse FILTER regex(?propertyUse, \""
				+ yourId
				+ "\")."

				+ "?relationNcyclopedia <http://www.w3.org/2000/01/rdf-schema#label> \"百科\"@zh."
				+ "?instance ?relationNcyclopedia ?propertyNcyclopedia FILTER regex(?propertyNcyclopedia, \""
				+ yourId
				+ "\")."

				+ "?relationAssociate <http://www.w3.org/2000/01/rdf-schema#label> \"联想\"@zh."
				+ "?instance ?relationAssociate ?propertyAssociate FILTER regex(?propertyAssociate, \""
				+ yourId
				+ "\")."

				+ "?relationPartsOfSpeech <http://www.w3.org/2000/01/rdf-schema#label> \"词性\"@zh."
				+ "?instance ?relationPartsOfSpeech ?propertyPartsOfSpeech FILTER regex(?propertyPartsOfSpeech, \""
				+ yourId
				+ "\")."

				+ "?relationWordProperty <http://www.w3.org/2000/01/rdf-schema#label> \"词性属性\"@zh."
				+ "?instance ?relationWordProperty ?propertyWordProperty FILTER regex(?propertyWordProperty, \""
				+ yourId
				+ "\")."

				+ "?relationText <http://www.w3.org/2000/01/rdf-schema#label> \"课文原句\"@zh."
				+ "?instance ?relationText ?propertyText FILTER regex(?propertyText, \""
				+ yourId
				+ "\")."

				+ "?relationDifficulty <http://www.w3.org/2000/01/rdf-schema#label> \"难度\"@zh."
				+ "?instance ?relationDifficulty ?propertyDifficulty FILTER regex(?propertyDifficulty, \""
				+ yourId + "\")." + "}";

		String sparqlInstance = string1 + yourId + string2;
		// System.out.println(sparqlInstance);

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

	// 判断数据库中是否存在此实例
	@Override
	public boolean checkIfInDB(String Instance) {
		String sqarqlCheckIfInDB = "SELECT ?instance WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ Instance + "\"@zh.}";
		ResultSet result = Result(sqarqlCheckIfInDB);

		if (result.hasNext()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ResultSet checkAllTriple() {

		String sparql = "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}";
		ResultSet resultsAllTriple = Result(sparql);// 把查询到的所有实例?s和类?o放在结果集里

		return resultsAllTriple;
	}

	public ResultSet checkAllSameVersion_SameBook_SameUnit_ID(
			String yourInstance) {
		String sparql = "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}";
		ResultSet resultsAllTriple = Result(sparql);// 把查询到的所有实例?s和类?o放在结果集里

		return resultsAllTriple;
	}

	public ResultSet checkPropertySameAs(String yourInstance) {

		if (yourInstance.contains("@")) {
			yourInstance = yourInstance.substring(0, yourInstance.indexOf("@"));
		}

		String sparql = "SELECT ?objectSameAs WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourInstance
				+ "\"@zh. ?instance <"
				+ OWL.sameAs
				+ "> ?objectSameAs}";
		ResultSet resultsAllTriple = Result(sparql);// 把查询到的所有实例?s和类?o放在结果集里

		return resultsAllTriple;

	}

	// 只找属性名称
	@Override
	public ResultSet checkOnlyPropertyLabel(String yourPropertyURI) {

		String sparql = "SELECT ?propertyLabel WHERE{<"
				+ yourPropertyURI
				+ "> <http://www.w3.org/2000/01/rdf-schema#label> ?propertyLabel}";
		ResultSet resultsPropertyLabel = Result(sparql);// 把查询到的所有实例?s和类?o放在结果集里

		return resultsPropertyLabel;
	}

	// 根据ID查找其父类+标签Label+ID的三元组(为修改特例设计的)
	@Override
	public ResultSet checkClass_Label(String yourID) {

		if (yourID.contains("@")) {
			yourID = yourID.substring(0, yourID.indexOf("@"));
		}

		String string1 = "SELECT ?instance ?instanceLabel ?propertyClass ?relationID ?propertyID WHERE{ ?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"句子ID\"@zh. ?instance ?relationID \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."

				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?class <http://www.w3.org/2000/01/rdf-schema#label> ?propertyClass."

				+ "?instance ?relationID ?propertyID.}";

		String sparqlClass_Label = string1 + yourID + string2;

		ResultSet resultsClass_Label = Result(sparqlClass_Label);

		return resultsClass_Label;
	}

	// 根据年级所有单词
	@Override
	public ResultSet checkAllWordsOfThisGrade(String yourGrade) {
		String string1 = "SELECT ?propertyBook ?instanceLabel WHERE{?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"单词册数\"@zh. ?instance ?relationBook ?propertyBook FILTER regex(?propertyBook, \"";

		String string2 = "\"\"). ?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel.}";

		String sparqlAllWordsOfThisGrade = string1 + yourGrade + string2;

		ResultSet resultsAllWordsOfThisGrade = Result(sparqlAllWordsOfThisGrade);

		return resultsAllWordsOfThisGrade;
	}

	// 查询所有的单词的ID
	public ResultSet checkIdOfAllWords() {
		String sparqlIdOfAllWords = "SELECT ?propertyID WHERE{?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"单词ID\"@zh. ?instance ?relationID ?propertyID.}";

		ResultSet resultsAllWordsOfThisGrade = Result(sparqlIdOfAllWords);

		return resultsAllWordsOfThisGrade;
	}

	// 查询所有的句子的ID
	public ResultSet checkIdOfAllSentences() {
		String sparqlIdOfAllWords = "SELECT ?propertyID WHERE{?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"句子ID\"@zh. ?instance ?relationID ?propertyID.}";

		ResultSet resultsAllWordsOfThisGrade = Result(sparqlIdOfAllWords);

		return resultsAllWordsOfThisGrade;
	}

	// 查询某年级的所有单词ID
	public ResultSet checkAllIdOfThisGrade(String yourGrade) {
		int yourGradeInt = Integer.parseInt(yourGrade);
		yourGradeInt = yourGradeInt * 2;
		String sparqlIdOfAllIdOfThisGrade = "SELECT ?propertyBook"
				+ "WHERE{?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"单词册数\"@zh."
				+ "?instance ?relationBook ?propertyBook.FILTER regex(?propertyBook,\""
				+ Integer.valueOf(yourGradeInt - 1)
				+ "\").FILTER regex(?propertyBook,\""
				+ Integer.valueOf(yourGradeInt) + "\").}";

		ResultSet resultsIdOfAllIdOfThisGrade = Result(sparqlIdOfAllIdOfThisGrade);

		return resultsIdOfAllIdOfThisGrade;
	}

	// 查询某主题-功能意念的所有单词的ID
	public ResultSet checkAllIdOfThisPropertyFunction(
			String yourPropertyFunction) {
		String sparqlAllIdOfThisPropertyFunction = "SELECT ?instance ?propertyFunction"
				+ "WHERE{?relationFunction <http://www.w3.org/2000/01/rdf-schema#label> \"主题-功能意念\"@zh."
				+ "?instance ?relationFunction ?propertyFunction.FILTER regex(?propertyFunction,\""
				+ yourPropertyFunction + "\").}";
		ResultSet resultAllIdOfThisPropertyFunction = Result(sparqlAllIdOfThisPropertyFunction);

		return resultAllIdOfThisPropertyFunction;
	}

	// 查询某主题-话题的所有单词的ID
	public ResultSet checkAllIdOfThisPropertyTopic(String yourPropertyTopic) {
		String sparqlAllIdOfThisPropertyTopic = "SELECT ?instance ?propertyTopic"
				+ "WHERE{?relationTopic <http://www.w3.org/2000/01/rdf-schema#label> \"主题-话题\"@zh."
				+ "?instance ?relationTopic ?propertyTopic.FILTER regex(?propertyTopic,\""
				+ yourPropertyTopic + "\").}";
		ResultSet resultAllIdOfThisPropertyTopic = Result(sparqlAllIdOfThisPropertyTopic);

		return resultAllIdOfThisPropertyTopic;
	}

	// 根据版本查找该版本所有单词
	public ResultSet checkAllWordsOfAVersion(String yourVersion) {
		String sparqlAllWordsOfAVersion = "SELECT ?propertyVersion"
				+ " WHERE{?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"单词教材版本\"@zh."
				+ "?instance ?relationVersion ?propertyVersion FILTER regex(?propertyVersion, \""
				+ yourVersion + "\").}";
		ResultSet resultAllWordsOfAVersion = Result(sparqlAllWordsOfAVersion);

		return resultAllWordsOfAVersion;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// 根据实例+某属性+属性值，查找该三元组
	@Override
	public ResultSet checkThisTriple(String yourID, String propertyLabel,
			String flag) {
		String sparql = "SELECT ?propertyLabel ?instance ?relation ?instanceLabel WHERE{"
				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ flag
				+ "ID\"@zh."
				+ "?instance ?relationID \""
				+ yourID
				+ "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?propertyLabel."
				+ "?relation <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel
				+ "\"@zh."
				+ "?instance ?relation ?instanceLabel.}";

		ResultSet result = Result(sparql);

		return result;
	}

	// 根据实例Label查其所有ID
	@Override
	public ResultSet checkAllID(String yourInstance, String flag) {

		String sparql = null;
		if (flag.equals("31")) {
			sparql = "SELECT ?allID WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \""
					+ yourInstance
					+ "\"@zh."
					+ "?relation <http://www.w3.org/2000/01/rdf-schema#label> \"单词ID\"@zh."
					+ "?instance ?relation ?allID.}";
		} else {
			sparql = "SELECT ?allID WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \""
					+ yourInstance
					+ "\"@zh."
					+ "?relation <http://www.w3.org/2000/01/rdf-schema#label> \"句子ID\"@zh."
					+ "?instance ?relation ?allID.}";
		}

		ResultSet result = Result(sparql);

		return result;
	}

	@Override
	public ResultSet checkOnlyInstanceURI(String yourInstance) {

		String sparql = "SELECT ?instance WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourInstance + "\"@zh.}";
		ResultSet result = Result(sparql);

		return result;
	}

	@Override
	public ResultSet checkTopicValue(String yourWord) {

		String sparqlTopicValue = "SELECT ?propertyTopic ?propertyFunction ?relationTopic ?relationFunction WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourWord
				+ "\"@zh. ?relationTopic <http://www.w3.org/2000/01/rdf-schema#label> \"主题-话题\"@zh. ?instance ?relationTopic ?propertyTopic."
				+ "?relationFunction <http://www.w3.org/2000/01/rdf-schema#label> \"主题-功能意念\"@zh."
				+ "?instance ?relationFunction ?propertyFunction.}";
		ResultSet resultTopicValue = Result(sparqlTopicValue);
		return resultTopicValue;
	}

	@Override
	public ResultSet checkBrotherID(String yourThemeValueFlag1,
			String yourThemeValueFlag2) {
		String sparqlBrotherIndividual = "SELECT ?propertyTheme WHERE{?instance ?relationTheme ?propertyTheme.FILTER regex(?propertyTheme,\""
				+ yourThemeValueFlag1
				+ "\") FILTER regex(?propertyTheme,\""
				+ yourThemeValueFlag2 + "\").}";

		ResultSet resultBrotherIndividual = Result(sparqlBrotherIndividual);
		return resultBrotherIndividual;
	}

	@Override
	public ResultSet checkBrotherID2(String yourTheme) {
		String sparqlBrotherIndividual = "SELECT ?propertyTheme WHERE{?instance ?relationTheme ?propertyTheme.FILTER regex(?propertyTheme,\""
				+ yourTheme + "\").}";

		ResultSet resultBrotherIndividual = Result(sparqlBrotherIndividual);
		return resultBrotherIndividual;
	}

	// 静态方法：根据SPARQL语句查询，并返回相应结果集-----------------------------------------------------------------------------------------------------------
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
