package com.noumenon.AddDeleteModifyQuery.WriteOwl.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.noumenon.AddDeleteModifyQuery.Query.QueryWithManyWays;
import com.noumenon.AddDeleteModifyQuery.Query.Impl.QueryWithManyWaysImpl;
import com.noumenon.AddDeleteModifyQuery.WriteOwl.WriteOwl;

public class WriteOwlImpl implements WriteOwl {
	private static String SERVER = "http://localhost:3030/EnglishLearningDataset/query";
	private static String[] propertySPARQLValue = { "?propertyID",
			"?propertyChinese", "?propertyFunction", "?propertyTopic",
			"?propertyBook", "?propertyAntonym", "?propertySynonyms",
			"?propertyCommonUse", "?propertyExtend", "?propertyScene",
			"?propertyExpand", "?propertyVersion", "?propertyUse",
			"?propertyNcyclopedia", "?propertyAssociate",
			"?propertyPartsOfSpeech", "?propertyWordProperty", "?propertyText",
			"?propertyDifficulty" };
	private static String[] propertySPARQLName = { "?relationID",
			"?relationChinese", "?relationFunction", "?relationTopic",
			"?relationBook", "?relationAntonym", "?relationSynonyms",
			"?relationCommonUse", "?relationExtend", "?relationScene",
			"?relationExpand", "?relationVersion", "?relationUse",
			"?relationNcyclopedia", "?relationAssociate",
			"?relationPartsOfSpeech", "?relationWordProperty", "?relationText",
			"?relationDifficulty" };
	private static String[] sentencePropertySPARQLValue = { "?propertyAnswer",
			"?propertyID", "?propertyVersion", "?propertyBook",
			"?propertyScene", "?propertySentencePattern",
			"?propertyRelatedWords" };
	private static String[] sentencePropertySPARQLName = { "?relationAnswer",
			"?relationID", "?relationVersion", "?relationBook",
			"?relationScene", "?relationSentencePattern",
			"?relationRelatedWords" };

	public WriteOwlImpl() {

	}

	public void writeBackToOwl() throws IOException {

		String[] allFile = new String[] { "OnlyClass.owl",
				"OnlyClassSentence.owl" };
		Model model = ModelFactory.createDefaultModel();
		OntModel ontModel = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_MEM, model);

		for (int whichOwl = 0; whichOwl < 2; whichOwl++) {

			File file = new File(allFile[whichOwl]);

			// 单词本体读入到模型中
			ReadToModel(file, ontModel, whichOwl);

			// 得到Fuseki中一共有多少条数据
			// int totalFusekiNum = TotalFusekiNum();

			// 构建单词本体
			ontModel = WriteModel(ontModel, whichOwl);

			// 写回单词owl文件中
			OutputStreamToOwl(file, ontModel);

			ontModel.removeAll();

		}
		model = null;// 回收
		ontModel = null;// 回收

	}

	public void writeBackToRespectiveOwl() throws IOException {

		String[] allFile = new String[] { "OnlyWordClass_FourthGrade.owl" };
		Model model = ModelFactory.createDefaultModel();
		OntModel ontModel = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

		for (int whichOwl = 0; whichOwl < 2; whichOwl++) {

			File file = new File(allFile[whichOwl]);

			// 单词本体读入到模型中
			ReadToModel(file, ontModel, whichOwl);

			// 得到Fuseki中一共有多少条数据
			// int totalFusekiNum = TotalFusekiNum();

			// 构建单词本体
			ontModel = WriteModel(ontModel, whichOwl);

			// 写回单词owl文件中
			OutputStreamToOwl(file, ontModel);

			ontModel.removeAll();

		}
		model = null;// 回收
		ontModel = null;// 回收

	}

	// 静态方法--------------------------------------------------------------------------------------------------------------------------------
	// 单词本体读入到模型中
	private static void ReadToModel(File file, OntModel ontModel, int i) {
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			if (i == 0) {
				ontModel.read(
						in,
						"http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31",
						"RDF/XML-ABBREV");
			} else {
				ontModel.read(
						in,
						"http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85",
						"RDF/XML-ABBREV");
			}
			in.close();
			in = null;// 回收

		} catch (FileNotFoundException e1) {// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 得到Fuseki中一共有多少条数据
	private static int TotalFusekiNum() {
		// String sparql =
		// "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}";
		String sparql = "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}";// 找出有父类属性的三元组
		ResultSet resultsInstance = Result(sparql);// 把查询到的所有实例?s和ID?o放在结果集里
		// System.out.println(resultsInstance);
		int totalNum = 0;
		// System.out.print(totalNum);
		// if (resultsInstance.hasNext()) {
		while (resultsInstance.hasNext()) {
			// System.out.print(totalNum);
			resultsInstance.next();
			totalNum++;
		}
		System.out.print(totalNum);
		return totalNum;
	}

	// 构建单词本体
	private static OntModel WriteModel(OntModel ontModel, int whichOwl) {

		// String sparql1 =
		// "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}limit 50 offset ";
		String sparql = "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}";
		// String sparql2 = String.valueOf(haveWriteToOwlNum);
		// String sparql = sparql1 + sparql2;
		// Statement wordStatement = null;
		// Statement sentenceStatement = null;
		ResultSet resultsInstance = Result(sparql);// 把查询到的所有实例?s和类?o放在结果集里

		Statement wordStatement = null;
		Statement sentenceStatement = null;
		// if (resultsInstance.hasNext()) {
		while (resultsInstance.hasNext()) {

			QuerySolution solutionInstance = resultsInstance.next();// 所有实例?s和类?o

			System.out.println("实例：" + solutionInstance.get("?s") + "\n" + "类："
					+ solutionInstance.get("?o"));

			String instanceURI = solutionInstance.get("?s").toString();
			String URINum = null;
			String yourInstance = null;
			if (instanceURI.contains("#")) {
				URINum = instanceURI.substring(instanceURI.indexOf("#") - 2,
						instanceURI.indexOf("#"));// 处理出要存储到哪一个owl文件中（得到31或85）
				if (instanceURI.contains("\'")) {
					yourInstance = solutionInstance
							.get("?s")
							.toString()
							.substring(
									solutionInstance.get("?s").toString()
											.indexOf("\'") + 1,
									solutionInstance.get("?s").toString()
											.lastIndexOf("\'"));// 处理出实例label
				} else {
					continue;
				}
			} else {
				continue;
			}
			instanceURI = null;// 回收

			// while (resultsFindHowMaySame.hasNext()) {// 有几个同名，则循环几次
			// howManySame++;

			// QuerySolution solutionSameInstance =
			// resultsFindHowMaySame.next();

			ArrayList<OntProperty> propertyURI = new ArrayList<OntProperty>();
			if (whichOwl == 0) {// 读入的是单词owl，所以要存到单词owl文件中
				if (URINum.equals("31")) {

					// 找出与该实例有几个同label的实例
					// int howManySame = 0;
					String findHowMaySame1 = "SELECT ?propertyID ?instance WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";
					String findHowMaySame2 = "\"@zh. ?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"单词ID\"@zh. ?instance ?relationID ?propertyID.}";
					String findHowMaySame = findHowMaySame1 + yourInstance
							+ findHowMaySame2;
					ResultSet resultsFindHowMaySame = Result(findHowMaySame);// 存放同Label的不同ID
					findHowMaySame = null;// 回收

					int i = 0;
					// 得到Property类型的的URI
					Iterator alldatapry = ontModel.listDatatypeProperties();
					while (alldatapry.hasNext()) {
						OntProperty datapry = (OntProperty) alldatapry.next();
						// 属性URI
						String dataprystr = datapry.toString();
						// System.out.print("属性URI：" + dataprystr + "\n");

						propertyURI.add(datapry);
						i++;
						datapry = null;// 回收
					}
					alldatapry = null;// 回收

					// System.out.println(propertyURI);

					while (resultsFindHowMaySame.hasNext()) {// 有几个同名，则循环几次
						// howManySame++;

						QuerySolution solutionSameInstance = resultsFindHowMaySame
								.next();
						System.out.println("ID————"
								+ solutionSameInstance.get("?propertyID")
										.toString());

						// 根据ID调用查找，得到结果集
						QueryWithManyWays queryWithManyWays = new QueryWithManyWaysImpl();

						ResultSet resultsInstanceProperty = queryWithManyWays
								.checkPropertyDependOnId(solutionSameInstance
										.get("?propertyID").toString());

						while (resultsInstanceProperty.hasNext()) {
							QuerySolution solutionInstanceProperty = resultsInstanceProperty
									.next();

							// System.out.println(propertyURI.size());
							// System.out.println(solutionInstance.get("?s"));
							for (int propertyNum = 0; propertyNum < propertySPARQLValue.length; propertyNum++) {// 添加单词属性
								for (i = 0; i < propertyURI.size(); i++) {
									// System.out
									// .println("SPARQL读取的property————"
									// + solutionInstanceProperty
									// .get(propertySPARQLName[propertyNum])
									// .toString() + "\n真正的property————" +
									// propertyURI.get(i).toString());
									if (solutionInstanceProperty
											.get(propertySPARQLName[propertyNum])
											.toString()
											.equals(propertyURI.get(i)
													.toString())) {// 与所有property的URI进行对比，若一样，得到相应的property
										Statement wordPropertyStatement = ontModel
												.createStatement(
														(Resource) solutionInstance
																.get("?s"),
														propertyURI.get(i),
														solutionInstanceProperty
																.get(propertySPARQLValue[propertyNum]));// 生成三元组

										ontModel.add(wordPropertyStatement);// 三元组添加到模型中
										break;
									}
								}
							}
							// 添加父类三元组

							wordStatement = ontModel.createStatement(
									(Resource) solutionInstance.get("?s"),
									RDF.type, solutionInstance.get("?o"));
							ontModel.add(wordStatement);

							// 添加标签Label三元组
							wordStatement = ontModel.createStatement(
									(Resource) solutionInstance.get("?s"),
									RDFS.label, solutionInstanceProperty
											.get("?instanceLabel"));
							ontModel.add(wordStatement);

							// 判断是否存在等价SameAs关系
							ResultSet resultsPropertySameAs = queryWithManyWays
									.checkPropertySameAs(solutionInstance.get(
											"?s").toString());
							if (resultsPropertySameAs.hasNext()) {
								QuerySolution solutionPropertySameAs = resultsPropertySameAs
										.next();
								wordStatement = ontModel.createStatement(
										(Resource) solutionInstance.get("?s"),
										OWL.sameAs, solutionPropertySameAs
												.get("?objectSameAs"));
								ontModel.add(wordStatement);
							}
							System.out.println("创建" + yourInstance + "三元组成功"
									+ "\n");
						}
						queryWithManyWays = null;// 回收
						resultsInstanceProperty = null;// 回收
					}

				}
				wordStatement = null;// 回收
				yourInstance = null;// 回收
				propertyURI = null;// 回收
				System.gc();

			} else {// 读入的是句子owl，所以要存储到句子owl文件中
				if (URINum.equals("85")) {

					// 找出与该实例有几个同label的实例
					// int howManySame = 0;
					String findHowMaySame1 = "SELECT ?propertyID ?instance WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";
					String findHowMaySame2 = "\"@zh. ?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"句子ID\"@zh. ?instance ?relationID ?propertyID.}";
					String findHowMaySame = findHowMaySame1 + yourInstance
							+ findHowMaySame2;
					ResultSet resultsFindHowMaySame = Result(findHowMaySame);
					findHowMaySame = null;// 回收

					int j = 0;
					// 得到Property类型的的URI

					// 列出所有的数据属性
					Iterator alldatapry = ontModel.listDatatypeProperties();
					while (alldatapry.hasNext()) {
						OntProperty datapry = (OntProperty) alldatapry.next();
						// 属性URI
						String dataprystr = datapry.toString();
						// System.out.print("属性URI：" + dataprystr +
						// "\n");

						propertyURI.add(datapry);
						j++;
						datapry = null;// 回收
					}
					alldatapry = null;// 回收

					while (resultsFindHowMaySame.hasNext()) {// 有几个同名，则循环几次
						// howManySame++;

						QuerySolution solutionSameInstance = resultsFindHowMaySame
								.next();
						System.out.println("ID————"
								+ solutionSameInstance.get("?propertyID")
										.toString());

						if (solutionSameInstance.get("?propertyID").toString()
								.contains("X")) {
							// 根据ID调用查找，得到结果集
							QueryWithManyWays queryWithManyWays = new QueryWithManyWaysImpl();
							ResultSet resultsClass_Label = queryWithManyWays
									.checkClass_Label(solutionSameInstance.get(
											"?propertyID").toString());

							while (resultsClass_Label.hasNext()) {
								QuerySolution solutionClass_Label = resultsClass_Label
										.next();
								// 添加父类三元组
								sentenceStatement = ontModel.createStatement(
										(Resource) solutionInstance.get("?s"),
										RDF.type, solutionInstance.get("?o"));
								ontModel.add(sentenceStatement);

								// 添加标签Label三元组
								sentenceStatement = ontModel.createStatement(
										(Resource) solutionInstance.get("?s"),
										RDFS.label, solutionClass_Label
												.get("?instanceLabel"));
								ontModel.add(sentenceStatement);

								// 判断是否存在等价SameAs关系
								ResultSet resultsPropertySameAs = queryWithManyWays
										.checkPropertySameAs(solutionClass_Label
												.get("?instanceLabel")
												.toString());
								if (resultsPropertySameAs.hasNext()) {
									QuerySolution solutionPropertySameAs = resultsPropertySameAs
											.next();
									sentenceStatement = ontModel
											.createStatement(
													(Resource) solutionInstance
															.get("?s"),
													OWL.sameAs,
													solutionPropertySameAs
															.get("?objectSameAs"));
									ontModel.add(sentenceStatement);
								}

								System.out.println("创建" + yourInstance
										+ "三元组成功" + "\n");
							}

						} else {
							// 根据ID调用查找，得到结果集
							QueryWithManyWays queryWithManyWays = new QueryWithManyWaysImpl();
							ResultSet resultsInstanceProperty = queryWithManyWays
									.checkSentencePropertyDependOnId(solutionSameInstance
											.get("?propertyID").toString());

							while (resultsInstanceProperty.hasNext()) {
								QuerySolution solutionInstanceProperty = resultsInstanceProperty
										.next();

								// System.out.println(propertyURI.size());
								// System.out.println(solutionInstance.get("?s"));
								for (int propertyNum = 0; propertyNum < sentencePropertySPARQLValue.length; propertyNum++) {
									for (j = 0; j < propertyURI.size(); j++) {
										if (solutionInstanceProperty
												.get(sentencePropertySPARQLName[propertyNum])
												.toString()
												.equals(propertyURI.get(j)
														.toString())) {
											Statement sentencePropertyStatement = ontModel
													.createStatement(
															(Resource) solutionInstance
																	.get("?s"),
															propertyURI.get(j),
															solutionInstanceProperty
																	.get(sentencePropertySPARQLValue[propertyNum]));

											ontModel.add(sentencePropertyStatement);
											break;
										}
									}
								}

								// 添加父类三元组
								sentenceStatement = ontModel.createStatement(
										(Resource) solutionInstance.get("?s"),
										RDF.type, solutionInstance.get("?o"));
								ontModel.add(sentenceStatement);

								// 添加标签Label三元组
								sentenceStatement = ontModel.createStatement(
										(Resource) solutionInstance.get("?s"),
										RDFS.label, solutionInstanceProperty
												.get("?instanceLabel"));
								ontModel.add(sentenceStatement);

								// 判断是否存在等价SameAs关系
								ResultSet resultsPropertySameAs = queryWithManyWays
										.checkPropertySameAs(solutionInstanceProperty
												.get("?instanceLabel")
												.toString());
								if (resultsPropertySameAs.hasNext()) {
									QuerySolution solutionPropertySameAs = resultsPropertySameAs
											.next();
									sentenceStatement = ontModel
											.createStatement(
													(Resource) solutionInstance
															.get("?s"),
													OWL.sameAs,
													solutionPropertySameAs
															.get("?objectSameAs"));
									ontModel.add(sentenceStatement);
								}

								System.out.println("创建" + yourInstance
										+ "三元组成功" + "\n");
							}
							queryWithManyWays = null;// 回收
							resultsInstanceProperty = null;// 回收
						}
					}
				}

				sentenceStatement = null;// 回收
				yourInstance = null;// 回收
				propertyURI = null;// 回收
				System.gc();

			}
		}

		// }
		// } else {
		// System.out.println("知识本体库中没有此实例");
		// }
		// resultsInstance = null;// 回收
		// System.gc();

		return ontModel;
	}

	// 写回单词owl文件中
	private static void OutputStreamToOwl(File file, OntModel ontModel) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			ontModel.writeAll(out, "RDF/XML");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out
				.println("###################Fuseki数据库写入完毕！！！###################");
	}

	private static ResultSet Result(String sparql) {
		Query queryInstance = QueryFactory.create(sparql);
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		ResultSet results = qexecInstance.execSelect();
		return results;
	}
}
