package AddDeleteModifyQuery.Modify.Impl;

import AddDeleteModifyQuery.Modify.ModifyPropertyValue;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;

public class ModifyPropertyValueImpl implements ModifyPropertyValue {
	private static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";
	private static String UPDATE_SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/update";

	public ModifyPropertyValueImpl() {

	}

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
		
		System.out.println("修改成功");

	}

	private static ResultSet Result(String sparql) {
		Query queryInstance = QueryFactory.create(sparql);
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		ResultSet results = qexecInstance.execSelect();
		return results;
	}

}
