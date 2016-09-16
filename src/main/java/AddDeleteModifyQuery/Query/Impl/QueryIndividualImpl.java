package AddDeleteModifyQuery.Query.Impl;

import AddDeleteModifyQuery.Query.QueryIndividual;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;


public class QueryIndividualImpl implements QueryIndividual {
	public static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";
	
	public QueryIndividualImpl(){
		
	}
	
	public ResultSet checkInstance(String yourClass){
		
		String string1 = "SELECT ?instanceLabel WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel}";
		String sparqlInstance = string1 + yourClass + string2;
		System.out.println(sparqlInstance);
		
		ResultSet resultsInstance = Result(sparqlInstance);
//		while (resultsInstance.hasNext()){
//			QuerySolution solutionInstance = resultsInstance.next();
//			System.out.println("类：" + yourClass + "\n" + "    ————实例："
//					+ solutionInstance.get("?instanceLabel") + "\n");
//		}	
		return resultsInstance;
	}
	
	private static ResultSet Result(String sparql) {
		//public static Query create(String queryString)
		//Create a SPARQL query from the given string.
		//从给定的string中创建一个SPARQL查询
		Query queryInstance = QueryFactory.create(sparql);
		//public static QueryExecution sparqlService(String service,Query query)
        //Create a QueryExecution that will access a SPARQL service over HTTP
		//创建一个QueryExecution，它将在HTTP上访问SPARQL服务
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		//ResultSet execSelect()
		//Execute a SELECT query
		//执行一个SELECT查询
		//Important: The name of this method is somewhat of a misnomer in that depending on the underlying implementation this typically does not execute the SELECT query but rather answers a wrapper over an internal data structure that can be used to answer the query. In essence calling this method only returns a plan for executing this query which only gets evaluated when you actually start iterating over the results.
		ResultSet results = qexecInstance.execSelect();
		return results;
	}
}
