package AddDeleteModifyQuery.Delete.Impl;

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

import AddDeleteModifyQuery.Delete.DeleteIndividual;

public class DeleteIndividualImpl implements DeleteIndividual{
	private static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";
	private static String UPDATE_SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/update";

	public DeleteIndividualImpl() {
		
	}

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
		
		System.out.println("删除实例父类成功！");
		
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
		
		System.out.println("删除实例Label成功！");
	}
	
	public void deleteProperty(String yourInstance, String yourProperty) {

		// 查找属性URI
		String sparqlProperty = "SELECT ?property WHERE{?property <http://www.w3.org/2000/01/rdf-schema#label> '"
				+ yourProperty + "'@zh.}";
		ResultSet resultsProperty = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (resultsProperty.hasNext())
			solutionProperty = resultsProperty.next();
		//System.out.println(sparqlProperty);
		//System.out.println(solutionProperty.get("?property"));
		
		// 查找属性值
		String sparqlPropertyValue = "SELECT ?propertyValue WHERE{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"+ yourInstance+ "'@zh> <" + solutionProperty.get("?property")+ "> " + "?propertyValue}";
		ResultSet resultsPropertyValue = Result(sparqlPropertyValue);
		QuerySolution solutionPropertyValue = null;
		while (resultsPropertyValue.hasNext())
			solutionPropertyValue = resultsPropertyValue.next();
		//System.out.println(sparqlPropertyValue);
		//System.out.println(solutionPropertyValue.get("?propertyValue"));
		
		String string1=solutionPropertyValue.toString();
		int i=string1.length();
		i=i-2;
		String string2=(String) string1.subSequence(19, i);
		System.out.println(string2);
		
		// 删除实例属性
		String sparqlDeleteProperty = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + string2 + "}";
		//System.out.println(sparqlDeleteProperty);
	
		UpdateRequest updateProperty = UpdateFactory
				.create(sparqlDeleteProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();

	}

	    private static ResultSet Result(String sparql) {
		Query queryInstance = QueryFactory.create(sparql);
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		ResultSet results = qexecInstance.execSelect();
		return results;
	}
}
