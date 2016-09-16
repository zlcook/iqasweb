package AddDeleteModifyQuery.Query.Impl;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

import AddDeleteModifyQuery.Query.QuerySentenceDependOnId;

public class QuerySentenceDependOnIdImpl implements QuerySentenceDependOnId {

	// 定义fuseki数据库连接地址/EnglishLearningDataset query
	public static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";

	public QuerySentenceDependOnIdImpl() {

	}

	public ResultSet checkSentencePropertyDependOnId(String yourId) {
		// 输入找实例——————————————————————————————————————————————————————————————————————————————————————————————————
		String string1 = "SELECT ?instanceLabel ?propertyAnswer ?propertyID ?propertyVersion ?propertyBook ?propertyScene ?propertySentencePattern ?propertyRelatedWords WHERE{ ?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"ID\"@zh. ?instance ?relationID \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."

				+ "?relationAnswer <http://www.w3.org/2000/01/rdf-schema#label> \"回答\"@zh."
				+ "?instance ?relationAnswer ?propertyAnswer."

				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"ID\"@zh."
				+ "?instance ?relationID ?propertyID."

				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"教材版本\"@zh."
				+ "?instance ?relationVersion ?propertyVersion."

				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"册数\"@zh."
				+ "?instance ?relationBook ?propertyBook."

				+ "?relationScene <http://www.w3.org/2000/01/rdf-schema#label> \"情境对话\"@zh."
				+ "?instance ?relationScene ?propertyScene."

				+ "?relationSentencePattern <http://www.w3.org/2000/01/rdf-schema#label> \"重要句型\"@zh."
				+ "?instance ?relationSentencePattern ?propertySentencePattern."

				+ "?relationRelatedWords <http://www.w3.org/2000/01/rdf-schema#label> \"相关单词\"@zh."
				+ "?instance ?relationRelatedWords ?propertyRelatedWords."
				+ "}";

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
