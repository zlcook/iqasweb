package com.noumenon.MyReasoner.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.noumenon.MyReasoner.MyReasoner;

public class MyReasonerImpl implements MyReasoner {
	
	public MyReasonerImpl(){
		
	}

	@Override
	public InfModel ReasonSameAs() {
		Model model = ModelFactory.createDefaultModel();
		OntModel ontModel = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_MEM, model);
		File file = new File("OnlyClassSentence.owl");

		// 单词本体读入到模型中
		ReadToModel(file, ontModel);

		String rules = "[Rule: (?x1 ?p ?y),(?x1 owl:sameAs ?x2) ->(?x2 ?p ?y)]";
		GenericRuleReasoner reasoner = new GenericRuleReasoner(
				Rule.parseRules(rules)); // 把编写的规则加入Jena2 的推理规则中去
		reasoner.setMode(GenericRuleReasoner.FORWARD_RETE);// 设置基于Rete算法的前向链推理模式
		InfModel inf = ModelFactory.createInfModel(reasoner, ontModel);

		return inf;
	}

	// 单词本体读入到模型中
	private static void ReadToModel(File file, OntModel ontModel) {
		FileInputStream in;
		try {
			in = new FileInputStream(file);

			ontModel.read(
					in,
					"http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85",
					"RDF/XML-ABBREV");

			in.close();
			in = null;// 回收

		} catch (FileNotFoundException e1) {// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
