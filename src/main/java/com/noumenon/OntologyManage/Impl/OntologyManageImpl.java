package com.noumenon.OntologyManage.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.noumenon.AddDeleteModifyQuery.Add.AddIndividualAndProperty;
import com.noumenon.AddDeleteModifyQuery.Add.Impl.AddIndividualAndPropertyImpl;
import com.noumenon.AddDeleteModifyQuery.Delete.DeleteIndividual;
import com.noumenon.AddDeleteModifyQuery.Delete.Impl.DeleteIndividualImpl;
import com.noumenon.AddDeleteModifyQuery.Query.QueryWithManyWays;
import com.noumenon.AddDeleteModifyQuery.Query.Impl.QueryWithManyWaysImpl;
import com.noumenon.AddDeleteModifyQuery.WriteOwl.WriteOwl;
import com.noumenon.AddDeleteModifyQuery.WriteOwl.Impl.WriteOwlImpl;
import com.noumenon.MyReasoner.MyReasoner;
import com.noumenon.MyReasoner.Impl.MyReasonerImpl;
import com.noumenon.OntologyManage.OntologyManage;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Service("ontologyManage")
public class OntologyManageImpl implements OntologyManage {
	private static String[] propertyLabel = new String[] { "单词ID", "单词",
			"主题-功能意念", "主题-话题", "Hownet中的父类", "词性", "词性属性", "中文含义", "单词教材版本",
			"单词册数", "难度", "课文原句", "情境段落", "联想", "同义词", "反义词", "拓展", "百科", "用法",
			"延伸例句", "常用" };
	private static String[] propertySPARQLValue = { "?propertyID",
			"?instanceLabel", "?propertyFunction", "?propertyTopic",
			"?propertyClass", "?propertyPartsOfSpeech",
			"?propertyWordProperty", "?propertyChinese", "?property",
			"?propertyBook", "?propertyDifficulty", "?propertyText",
			"?propertyScene", "?propertyAssociate", "?propertyAntonym",
			"?propertySynonyms", "?propertyExtend", "?propertyNcyclopedia",
			"?propertyUse", "?propertyExpand", "?propertyCommonUse" };
	private static String[] propertyRelation = { "?relationID",
			"?relationInstance", "?relationFunction", "?relationTopic",
			"?relationClass", "?relationPartsOfSpeech",
			"?relationWordProperty", "?relationChinese", "?relationVersion",
			"?relationBook", "?relationDifficulty", "?relationText",
			"?relationScene", "?relationAssociate", "?relationAntonym",
			"?relationSynonyms", "?relationExtend", "?relationNcyclopedia",
			"?relationUse", "?relationExpand", "?relationCommonUse" };
	private static String[] sentencePropertyLabel = new String[] { "句子ID",
			"句子教材版本", "句子册数", "问题", "问题句型", "回答", "情境对话", "重要句型", "相关单词" };
	private static String[] sentencePropertySPARQLValue = { "?propertyID",
			"?propertyVersion", "?propertyBook", "?propertyClass",
			"?instanceLabel", "?propertyAnswer", "?propertyScene",
			"?propertySentencePattern", "?propertyRelatedWords" };
	private static String[] sentencePropertyRelation = { "?relationID",
			"?relationVersion", "?relationBook", "?relationClass",
			"?instanceLabel", "?relationAnswer", "?relationScene",
			"?relationSentencePattern", "?relationRelatedWords" };

	// 构造函数
	public OntologyManageImpl() {

	}

	@SuppressWarnings("rawtypes")
	public static HashMap<String, Vector> ALLWORDS;
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Vector> ALLMEANS;

	private static AddIndividualAndProperty addIndividualAndProperty = new AddIndividualAndPropertyImpl();
	private static DeleteIndividual deleteIndividual = new DeleteIndividualImpl();;
	private static QueryWithManyWays queryWithManyWays = new QueryWithManyWaysImpl();
	private static WriteOwl writeOwl = new WriteOwlImpl();
	private static MyReasoner myReasoner = new MyReasonerImpl();

	@Override
	public void Add(String[] parameter) {

		// 添加单词父类和Label
		addIndividualAndProperty.addClass(parameter[4], parameter[1]);
		addIndividualAndProperty.addLabel(parameter[1]);

		// 添加单词属性
		int i = 0;
		for (i = 0; i < parameter.length; i++) {
			System.out.println(i + parameter[i]);
			if (i == 1 || i == 4) {

			} else {
				// 调用public void addProperty(String propertyLabel, String
				// yourInstance,String yourProperty)
				addIndividualAndProperty.addProperty(propertyLabel[i],
						parameter[1], parameter[i]);
			}
		}

	}

	@Override
	public void AddSentence(String[] parameter) {

		// 添加单词父类和Label
		addIndividualAndProperty.addSentenceClass(parameter[4], parameter[3]);
		addIndividualAndProperty.addSentenceLabel(parameter[3]);

		// 添加单词属性
		int i = 0;
		for (i = 0; i < parameter.length; i++) {
			System.out.println(i + parameter[i]);
			if (i == 3 || i == 4) {

			} else {
				// 调用public void addProperty(String propertyLabel, String
				// yourInstance,String yourProperty)
				addIndividualAndProperty.addSentenceProperty(
						sentencePropertyLabel[i], parameter[3], parameter[i]);
			}
		}
	}

	@Override
	public void AddBatch(InputStream yourPath) throws BiffException,
			IOException {
		List<String[]> list = readExcel(yourPath);

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("要添加的Excel中的" + propertyLabel[j] + "内容:"
						+ str[j]);
			}

			// 我自己的excel不要注释掉以下三句
			String yourClass = null;
			yourClass = str[4];
			System.out.println("你想添加的类:" + yourClass);

			String yourInstance = null;
			yourInstance = str[1];
			System.out.println("你想添加的实例:" + yourInstance);

			// 我自己的excel不要注释掉以下
			if (yourClass.equals("（先不在Protégé中填写）")) {

			} else {
				addIndividualAndProperty.addClass(yourClass, yourInstance);
				addIndividualAndProperty.addLabel(yourInstance);
				String yourProperty;
				for (int k = 0; k < 21; k++) {
					if (propertyLabel[k].equals("单词")
							|| propertyLabel[k].equals("Hownet中的父类")) {
						// 当为“单词”或“Hownet中的父类”时，什么都不做
					} else {
						yourProperty = null;
						yourProperty = str[k];
						addIndividualAndProperty.addProperty(propertyLabel[k],
								yourInstance, yourProperty);
					}
				}
			}
			// 到这里

			// 我自己的excel要注释掉以下
			// String yourClass = findDEF(yourInstance);
			// System.out.println("你选择的父类（返回）：" + yourClass);

			// if (yourClass == null) {
			// System.out.println(yourInstance + "的父类不在HowNet中");
			// } else {
			// addIndividualAndProperty.addInstance(yourClass, yourInstance);
			// }
			// 到这里
		}
		System.out.println("批量单词插入成功！");
	}

	@Override
	public void AddWordBatch(InputStream yourPath) throws BiffException,
			IOException {
		List<String[]> list = readExcel(yourPath);

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("要添加的Excel中的内容" + str[j]);
			}

			// 我自己的excel不要注释掉以下三句
			String yourClass = null;
			yourClass = str[4];
			System.out.println("你想添加的类:" + yourClass);

			String yourInstance = null;
			yourInstance = str[1];
			yourInstance = yourInstance.replace(" ", "_");// 如果实例中有空格，则将空格替换成下划线
			System.out.println("你想添加的实例:" + yourInstance);

			// 我自己的excel不要注释掉以下
			// if (yourClass.equals("（先不在Protégé中填写）")) {
			if (yourClass.equals("无")) {
				// 如果HowNet的父类为“无”，则什么都不做
			} else {
				// addIndividualAndProperty.addClass(yourClass, yourInstance);
				// addIndividualAndProperty.addLabel(yourInstance);
				addIndividualAndProperty.addWordClass(yourClass, yourInstance);
				addIndividualAndProperty.addWordLabel(yourInstance);

				String yourProperty;
				for (int k = 0; k < 21; k++) {
					if (propertyLabel[k].equals("单词")
							|| propertyLabel[k].equals("Hownet中的父类")) {
						// 当为“单词”或“Hownet中的父类”时，什么都不做
					} else {
						yourProperty = null;
						yourProperty = str[k];
						yourProperty = yourProperty.replace("\n", "");// 如果属性中存在回车，则将回车删除
						addIndividualAndProperty.addProperty(propertyLabel[k],
								yourInstance, yourProperty);
					}
				}
			}
			// 到这里

			// 我自己的excel要注释掉以下
			// String yourClass = findDEF(yourInstance);
			// System.out.println("你选择的父类（返回）：" + yourClass);

			// if (yourClass == null) {
			// System.out.println(yourInstance + "的父类不在HowNet中");
			// } else {
			// addIndividualAndProperty.addInstance(yourClass, yourInstance);
			// }
			// 到这里
		}
		System.out.println("批量单词插入成功！");
	}

	@Override
	public void AddSentenceBatch(InputStream yourPath) throws BiffException,
			IOException {

		List<String[]> list = readExcel(yourPath);

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("要添加的Excel中的内容" + str[j]);
			}

			String yourClass = null;
			yourClass = str[3];
			System.out.println("你想添加的类:" + yourClass + "\n");

			String yourInstance = null;
			yourInstance = str[4];
			System.out.println("你想添加的实例:" + yourInstance + "\n");

			if (yourClass.equals("（先不在Protégé中填写）")) {

			} else {
				addIndividualAndProperty.addSentenceClass(yourClass,
						yourInstance);
				addIndividualAndProperty.addSentenceLabel(yourInstance);
			}

			String yourProperty;
			for (int k = 0; k < sentencePropertyLabel.length; k++) {
				if (sentencePropertyLabel[k].equals("问题句型")
						|| sentencePropertyLabel[k].equals("问题")) {
					// 当为“问题句型”或“问题”时，什么都不做
				} else {
					yourProperty = null;
					yourProperty = str[k];
					addIndividualAndProperty.addSentenceProperty(
							sentencePropertyLabel[k], yourInstance,
							yourProperty);
				}
			}
		}
		System.out.println("批量句子插入成功！");
	}

	@Override
	public void Delete(String yourInstanceID) {

		ResultSet resultsProperty = queryWithManyWays
				.checkPropertyDependOnId(yourInstanceID);

		if (resultsProperty.hasNext()) {
			while (resultsProperty.hasNext()) {

				QuerySolution solutionInstance = resultsProperty.next();

				if (solutionInstance.get("?relationID").toString()
						.contains("31")) {// 如果是单词则执行

					int i = 0;
					for (i = 0; i < propertyLabel.length; i++) {
						System.out.println("    ————"
								+ propertyLabel[i]
								+ "："
								+ substringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));

						if (propertyLabel[i].equals("单词")
								|| propertyLabel[i].equals("Hownet中的父类")) {

						} else {
							deleteIndividual.deleteInstanceProperty(
									solutionInstance.get("?instanceLabel")
											.toString(),
									solutionInstance.get(propertyRelation[i])
											.toString(),
									solutionInstance
											.get(propertySPARQLValue[i])
											.toString());

						}

					}

					// 删除父类和Label
					deleteIndividual.deleteClass(
							solutionInstance.get("?instanceLabel").toString(),
							solutionInstance.get("?propertyClass").toString());
					deleteIndividual.deleteLabel(solutionInstance.get(
							"?instanceLabel").toString());

					System.out.println("单词删除成功\n");
				} else {
					// 如果是句子则什么都不做
				}
			}
		}

		else {
			System.out.println("知识本体库中没有此实例");
		}
	}

	@Override
	public void DeleteSentence(String yourInstanceID) {

		ResultSet resultsProperty = queryWithManyWays
				.checkSentencePropertyDependOnId(yourInstanceID);

		if (resultsProperty.hasNext()) {
			while (resultsProperty.hasNext()) {

				QuerySolution solutionInstance = resultsProperty.next();

				if (solutionInstance.get("?relationID").toString()
						.contains("85")) {// 如果是句子则执行
					int i = 0;
					for (i = 0; i < sentencePropertyLabel.length; i++) {
						System.out.println("    ————"
								+ sentencePropertyLabel[i]
								+ "："
								+ substringManage(solutionInstance.get(
										sentencePropertySPARQLValue[i])
										.toString()));

						if (sentencePropertyLabel[i].equals("问题")
								|| sentencePropertyLabel[i].equals("问题句型")) {

						} else {
							deleteIndividual.deleteSentenceInstanceProperty(
									solutionInstance.get("?instanceLabel")
											.toString(),
									solutionInstance.get(
											sentencePropertyRelation[i])
											.toString(),
									solutionInstance.get(
											sentencePropertySPARQLValue[i])
											.toString());

						}

					}

					// 删除父类和Label
					deleteIndividual.deleteSentenceClass(
							solutionInstance.get("?instanceLabel").toString(),
							solutionInstance.get("?propertyClass").toString());
					deleteIndividual.deleteSentenceLabel(solutionInstance.get(
							"?instanceLabel").toString());

					System.out.println("句子删除成功\n");
				} else {
					// 如果是单词则什么都不做
				}

			}

		} else {
			System.out.println("知识本体库中没有此实例");
		}
	}

	@Override
	public void Modify(String yourID, String yourPropertyLabel,
			String yourSPARQLProperty, String yourRelationProperty) {

		// 查找对应的三元组
		// ResultSet result = queryWithManyWays.checkThisTriple(yourID,
		// yourPropertyLabel, "单词");
		ResultSet result = queryWithManyWays.checkPropertyDependOnId(yourID);
		QuerySolution solution = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				solution = result.next();
				if (yourPropertyLabel.equals("单词ID")) {

					modifyID(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel);

				} else if (yourPropertyLabel.equals("Hownet中的父类")) {

					modifyClass(solution, yourSPARQLProperty);

				} else if (yourPropertyLabel.equals("单词")) {

					modifyLabel(solution, yourID);
				} else {
					modifyPropertyValue(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel, yourID);
				}

			}
		} else {
			System.out.println("无此三元组");
		}

		System.out.println("修改成功");
	}

	@Override
	public void ModifySentence(String yourID, String yourPropertyLabel,
			String yourSPARQLProperty, String yourRelationProperty) {

		// 查找对应的三元组
		// ResultSet result = queryWithManyWays.checkThisTriple(yourID,
		// yourPropertyLabel, "单词");
		ResultSet result = queryWithManyWays
				.checkSentencePropertyDependOnId(yourID);
		QuerySolution solution = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				solution = result.next();
				if (yourPropertyLabel.equals("句子ID")) {

					modifySentenceID(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel);

				} else if (yourPropertyLabel.equals("问题句型")) {

					modifySentenceClass(solution, yourSPARQLProperty);

				} else if (yourPropertyLabel.equals("问题")) {

					modifySentenceLabel(solution, yourID);

				} else {
					modifySentencePropertyValue(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel, yourID);
				}

			}
			System.out.println("修改成功");
		} else {
			System.out.println("无此三元组");
		}
	}

	// 根据类查找类下的所有单词Label
	public ResultSet QueryWord(String yourClass) {

		ResultSet resultsInstance = queryWithManyWays.checkInstance(yourClass);

		return resultsInstance;

	}

	// 根据类查找类下的所有单词Label
	public List<ResultSet> QueryWordAndPropertiesDependOnClass(String yourClass) {

		// 1.查找该父类下的所有ID
		ResultSet resultsIDDependOnClass = queryWithManyWays
				.checkIDDependOnClass(yourClass);

		// 2.根据ID查找属性
		List<ResultSet> resultsWordsAndProperties = new ArrayList<ResultSet>();
		if (resultsIDDependOnClass.hasNext()) {
			while (resultsIDDependOnClass.hasNext()) {
				QuerySolution solutionIDDependOnClass = resultsIDDependOnClass
						.next();

				resultsWordsAndProperties
						.add(QueryIndividualDependOnId(solutionIDDependOnClass
								.get("?propertyID").toString()));
			}
		} else {
			System.out.println("该HowNet父类下没有单词！");
		}

		return resultsWordsAndProperties;

	}

	// 根据ID查找该单词及其所有属性
	@Override
	public ResultSet QueryIndividualDependOnId(String yourID) {

		ResultSet resultsInstance = queryWithManyWays
				.checkPropertyDependOnId(yourID);

		return resultsInstance;
	}

	// 查一个单词的所有属性
	@Override
	public ResultSet QueryIndividual(String yourWord) {

		ResultSet resultsInstance = queryWithManyWays.checkProperty(yourWord);

		return resultsInstance;
	}

	// 查询单词对应的所有ID
	@Override
	public ResultSet QueryAWordAllId(String yourWord) {

		ResultSet resultsInstance = queryWithManyWays
				.checkAllIdOfAnWord(yourWord);

		return resultsInstance;
	}

	// 查询句子对应的所有ID
	@Override
	public ResultSet QueryASentenceAllId(String yourSentence) {

		ResultSet resultsInstance = queryWithManyWays
				.checkAllIdOfAnSentence(yourSentence);

		return resultsInstance;
	}

	// 根据ID查找该句子及其所有属性
	@Override
	public ResultSet QuerySentenceIndividualDependOnId(String yourID) {

		ResultSet resultsInstance = queryWithManyWays
				.checkSentencePropertyDependOnId(yourID);

		return resultsInstance;
	}

	@Override
	public ResultSet QuerySentenceIndividual(String yourSentence) {

		ResultSet resultsInstance = queryWithManyWays
				.checkSentenceProperty(yourSentence);

		return resultsInstance;
	}

	// 根据年级随机找出5个单词
	@Override
	public List<ResultSet> QueryFiveWordsOfThisGrade(String yourGrade) {

		// 用动态数组保存该年级单词ID
		List<String> allIdOfThisGrade = QueryAllWordsIdOfThisGrade(yourGrade);

		// 随机取5个单词ID，之后查找每个单词的属性
		List<ResultSet> resultsFiveWordsOfThisGrade = new ArrayList<ResultSet>();
		if (allIdOfThisGrade.isEmpty()) {
			System.out.println("该年纪没有单词！");
		} else {// 该年级有单词
			System.out.println("该年级所有单词的个数：" + allIdOfThisGrade.size());

			if (allIdOfThisGrade.size() < 5) {// 如果单词个数<5，保存所有单词
				for (int i = 0; i < allIdOfThisGrade.size(); i++) {
					resultsFiveWordsOfThisGrade.add(queryWithManyWays
							.checkPropertyDependOnId(allIdOfThisGrade.get(i)
									.toString()));
				}
			} else {// 如果单词个数>=5，从中选择不重复的5个单词
					// 用数组保持随机ID，来判断是否有重复
				List<String> randomIdList = new ArrayList<String>();
				// 随机取5个单词ID，之后查找每个单词的属性
				do {
					Random ra = new Random();
					int randomNum = ra.nextInt(allIdOfThisGrade.size() - 1);
					System.out.println("随机索引为：" + randomNum);
					String randomId = allIdOfThisGrade.get(randomNum)
							.toString();
					System.out.println("随机ID为：" + randomId);

					int j;
					// 遍历随机ID数组中的每一个元素
					for (j = 0; j < randomIdList.size(); j++) {
						// 判断是否有重复的
						if (randomIdList.get(j).equals(randomId)) {
							break;
						}
					}
					if (j == randomIdList.size()) {// 无重复
						// 把随机ID保存好
						randomIdList.add(randomId);
						resultsFiveWordsOfThisGrade.add(queryWithManyWays
								.checkPropertyDependOnId(randomId));
					} else {
						continue;
					}
				} while (resultsFiveWordsOfThisGrade.size() < 5);
			}
		}
		return resultsFiveWordsOfThisGrade;
	}

	@Override
	public Map<String, String> QuerySentenceAndId(String yourClass) {
		// 创建空Map以保存键值对<ID, instanceLabel>
		Map<String, String> idAndInstancelabelOfAllInstance = new HashMap<String, String>();

		// 查找类下所有句子
		ResultSet resultsInstance = queryWithManyWays.checkInstance(yourClass);

		// 以键值对保存每一个
		if (resultsInstance.hasNext()) {
			while (resultsInstance.hasNext()) {
				QuerySolution solutionInstance = resultsInstance.next();

				// 提取出当前句子
				String thisSentence = solutionInstance
						.get("?instanceLabel")
						.toString()
						.substring(
								0,
								solutionInstance.get("?instanceLabel")
										.toString().indexOf("@"));
				// 根据当前句子查找属性，取出所有ID
				ResultSet resultsAllPropertiesOfAInstance = queryWithManyWays
						.checkAllIdOfAnSentence(thisSentence);

				// 保存键值对<ID, instanceLabel>
				while (resultsAllPropertiesOfAInstance.hasNext()) {
					QuerySolution solutionAllPropertiesOfAInstance = resultsAllPropertiesOfAInstance
							.next();

					// 提取出当前句子的ID
					String idOfThisSentence = solutionAllPropertiesOfAInstance
							.get("?propertyID").toString();
					// 去除“@zh”
					idOfThisSentence = idOfThisSentence.substring(0,
							idOfThisSentence.indexOf("@"));

					// 保存到Map中
					idAndInstancelabelOfAllInstance.put(idOfThisSentence,
							thisSentence);

				}
			}
		} else {
			System.out.println("知识本体库中没有此实例");
		}
		return idAndInstancelabelOfAllInstance;
	}

	@Override
	public List<ResultSet> QueryTwoSentencesOfThisGrade(String yourGrade) {

		// 用动态数组保存该年级句子ID
		List<String> allSentencesIdOfThisGrade = QueryAllSentencesIdOfThisGrade(yourGrade);

		// 随机取2个句子ID，之后查找每个单词的属性
		List<ResultSet> resultsTwoSentencesOfThisGrade = new ArrayList<ResultSet>();
		if (allSentencesIdOfThisGrade.isEmpty()) {
			System.out.println("该年纪没有句子！");
		} else {// 该年级有句子
			System.out
					.println("该年级所有句子的个数：" + allSentencesIdOfThisGrade.size());

			if (allSentencesIdOfThisGrade.size() < 2) {// 如果句子个数<2，即只有一个句子，则保存这1个句子并返回
				resultsTwoSentencesOfThisGrade
						.add(queryWithManyWays
								.checkSentencePropertyDependOnId(allSentencesIdOfThisGrade
										.get(0).toString()));
			} else {// 如果句子个数>=2，选择不重复的2个句子
				// 用数组保持随机ID，来判断是否有重复
				List<String> randomIdList = new ArrayList<String>();

				// 随机取2个句子ID，之后查找每个单词的属性
				do {
					Random ra = new Random();
					int randomNum = ra
							.nextInt(allSentencesIdOfThisGrade.size() - 1);
					System.out.println("随机索引为：" + randomNum);
					String randomId = allSentencesIdOfThisGrade.get(randomNum)
							.toString();
					System.out.println("随机ID为：" + randomId);

					int j;
					// 遍历随机ID数组中的每一个元素
					for (j = 0; j < randomIdList.size(); j++) {
						// 判断是否有重复的
						if (randomIdList.get(j).equals(randomId)) {
							break;
						}
					}
					if (j == randomIdList.size()) {// 无重复
						// 把随机ID保存好
						randomIdList.add(randomId);

						resultsTwoSentencesOfThisGrade.add(queryWithManyWays
								.checkSentencePropertyDependOnId(randomId));
					} else {
						continue;
					}
				} while (resultsTwoSentencesOfThisGrade.size() < 2);
			}
		}
		return resultsTwoSentencesOfThisGrade;
	}

	@Override
	public List<ResultSet> QueryThreeSentencesOfThisGrade(String yourGrade) {

		// 用动态数组保存该年级句子ID
		List<String> allSentencesIdOfThisGrade = QueryAllSentencesIdOfThisGrade(yourGrade);

		// 随机取3个句子ID，之后查找每个单词的属性
		List<ResultSet> resultsTwoSentencesOfThisGrade = new ArrayList<ResultSet>();
		if (allSentencesIdOfThisGrade.isEmpty()) {
			System.out.println("该年纪没有句子！");
		} else {// 该年级有句子
			System.out
					.println("该年级所有句子的个数：" + allSentencesIdOfThisGrade.size());

			if (allSentencesIdOfThisGrade.size() < 3) {// 如果句子个数<3，即只有一个句子，则保存这1个句子并返回
				resultsTwoSentencesOfThisGrade
						.add(queryWithManyWays
								.checkSentencePropertyDependOnId(allSentencesIdOfThisGrade
										.get(0).toString()));
			} else {// 如果句子个数>=3，选择不重复的3个句子
				// 用数组保持随机ID，来判断是否有重复
				List<String> randomIdList = new ArrayList<String>();

				// 随机取3个句子ID，之后查找每个单词的属性
				do {
					Random ra = new Random();
					int randomNum = ra
							.nextInt(allSentencesIdOfThisGrade.size() - 1);
					System.out.println("随机索引为：" + randomNum);
					String randomId = allSentencesIdOfThisGrade.get(randomNum)
							.toString();
					System.out.println("随机ID为：" + randomId);

					int j;
					// 遍历随机ID数组中的每一个元素
					for (j = 0; j < randomIdList.size(); j++) {
						// 判断是否有重复的
						if (randomIdList.get(j).equals(randomId)) {
							break;
						}
					}
					if (j == randomIdList.size()) {// 无重复
						// 把随机ID保存好
						randomIdList.add(randomId);

						resultsTwoSentencesOfThisGrade.add(queryWithManyWays
								.checkSentencePropertyDependOnId(randomId));
					} else {
						continue;
					}
				} while (resultsTwoSentencesOfThisGrade.size() < 3);
			}
		}
		return resultsTwoSentencesOfThisGrade;
	}

	// 根据年级查询两个类别中的3个单词
	@Override
	public List<String> QueryTwoDifferentThemeWordsOfThisGrade(
			String yourGrade, String yourWord) {
		List<String> saveThreeWords = new ArrayList<String>(); // 保存最终的3个单词
		saveThreeWords.add(yourWord);

		// 1.根据单词查找对应的所有ID
		ResultSet resultAllIdOfThisWord = QueryAWordAllId(yourWord);

		// 2.根据ID判断年级
		Map<String, List<String>> allThemeOfThisWordMap = new HashMap<String, List<String>>(); // 用于该单词对应所有ID及其所有主题
		if (resultAllIdOfThisWord.hasNext()) {
			while (resultAllIdOfThisWord.hasNext()) {

				QuerySolution solutionAllIdOfThisWord = resultAllIdOfThisWord
						.next();

				// 3.找出该单词的册数属性
				String bookOfThisWord = interceptBook(solutionAllIdOfThisWord
						.get("?propertyID").toString());
				String gradeOfThisWord = bookToGrade(bookOfThisWord);

				// 4.判断这个单词的年级是否等于所给的年级,若相等，则保存该单词的主题-功能意念和主题-话题属性
				if (gradeOfThisWord.equals(yourGrade)) {

					// 根据ID查找该单词的所有主题
					ResultSet resultAllPropertiesOfThisId = queryWithManyWays
							.checkPropertyDependOnId((solutionAllIdOfThisWord
									.get("?propertyID").toString()));
					List<String> themeOfThisWord = new ArrayList<String>(); // 用于保存该年级中该单词的所有主题
					if (resultAllPropertiesOfThisId.hasNext()) {
						while (resultAllPropertiesOfThisId.hasNext()) {

							QuerySolution solutionAllPropertiesOfThisId = resultAllPropertiesOfThisId
									.next();

							// 保存主题
							themeOfThisWord.add(solutionAllPropertiesOfThisId
									.get("?propertyFunction").toString());
							themeOfThisWord.add(solutionAllPropertiesOfThisId
									.get("?propertyTopic").toString());

							// 保存Map
							allThemeOfThisWordMap.put(
									solutionAllPropertiesOfThisId.get(
											"?propertyID").toString(),
									themeOfThisWord);
						}
					}

				} else { // 若不相等，则continue
					continue;
				}
			}
		} else {
			System.out.println("该单词不存在于本体库中");
		}

		// 5.根据年级查找所有单词ID
		List<String> allIdOfThisGradeList = QueryAllWordsIdOfThisGrade(yourGrade);

		while (saveThreeWords.size() <= 3) { // 需要再随机找两个别的主题的单词
			int circleTimes = 0;

			// 6.随机抽取一个ID
			Random ra = new Random();
			int randomNum = ra.nextInt(allIdOfThisGradeList.size() - 1);
			System.out.println("随机索引为：" + randomNum);
			String randomId = allIdOfThisGradeList.get(randomNum).toString();
			System.out.println("随机单词ID为：" + randomId);

			// 7.对比ID和主题是否一致，若一致则重新抽取，若不一致则保存
			for (String key : allThemeOfThisWordMap.keySet()) { // 遍历Map
				// 判断ID是否一致
				if (randomId.equals(key)) { // 若一致，则跳出继续循环
					continue;
				} else {
					circleTimes++;
				}
			}

			if (circleTimes < allThemeOfThisWordMap.size()) { // ID有重复，需跳出，进行下一次循环
				continue;
			} else { // ID无重复，需再对比主题是否有重复
				List<String> randomThemeList = new ArrayList<String>(); // 用于临时存储两个主题

				// 查询该随机ID的所有属性
				ResultSet resultsAllPropertisOfAWord = queryWithManyWays
						.checkPropertyDependOnId(randomId);
				if (resultsAllPropertisOfAWord.hasNext()) {
					while (resultsAllPropertisOfAWord.hasNext()) {

						QuerySolution solutionAllPropertisOfAWord = resultsAllPropertisOfAWord
								.next();
						String functionOfThisWord = substringManage(solutionAllPropertisOfAWord
								.get("?propertyFunction").toString()); // 该ID对应的主题-功能意念属性
						String topicOfThisWord = substringManage(solutionAllPropertisOfAWord
								.get("?propertyTopic").toString()); // 该ID对应的主题-话题属性

						int repeatTimes = 0;
						// 遍历Map做主题属性对比
						for (String key : allThemeOfThisWordMap.keySet()) {

							List<String> oneInfoOfMap = allThemeOfThisWordMap
									.get(key);

							// 处理字符串：Map中的属性和随机属性都抽取出来
							String functionOfMapString = substringManage(oneInfoOfMap
									.get(0));
							String topicOfMapString = substringManage(oneInfoOfMap
									.get(1));

							if (functionOfMapString.equals(functionOfThisWord)
									&& !functionOfThisWord.equals("无")) { // 与propertyFunction对比若一致，则跳出继续循环
								randomThemeList.add("无");
								repeatTimes++;
								continue;
							} else { // 否则，再与propertyTopic对比若一致，则跳出继续循环
								if (topicOfMapString.equals(topicOfThisWord)
										&& !topicOfThisWord.equals("无")) { // 与propertyTopic对比若一致，则跳出继续循环
									randomThemeList.add("无");
									repeatTimes++;
									continue;
								} else { // 否则，临时保存
									// 什么都不做
								}
							}
						}

						// 遍历Map之后，判断重复次数
						if (repeatTimes == 0) { // 如果重复次数为零，则保存
							// 最终保存
							saveThreeWords
									.add(subStringManage(solutionAllPropertisOfAWord
											.get("?instanceLabel").toString()));
						} else {
							System.out.println("与所给单词的主题相同");
						}
					}
				} else {
					System.out.println("不存在该ID");
				}
			}
		}

		return saveThreeWords;
	}

	@Override
	public List<String> QueryTwoWordsDependOnDifficulty(String yourGrade,
			String yourDifficultyOfWord1, String yourDifficultyOfWord2) {
		List<String> twoWordsDependOnDifficultyList = new ArrayList<String>(); // 用于保存这两个单词

		// 1.查找该年级所有单词的ID
		List<String> allIdOfThisGradeList = QueryAllWordsIdOfThisGrade(yourGrade);

		while (twoWordsDependOnDifficultyList.size() < 2) { // 需要再随机找两个对应所给难度的单词{
			// 2.随机抽取一个ID
			Random ra = new Random();
			int randomNum = ra.nextInt(allIdOfThisGradeList.size() - 1);
			System.out.println("随机索引为：" + randomNum);
			String randomId = allIdOfThisGradeList.get(randomNum).toString();
			System.out.println("随机单词ID为：" + randomId);

			// 3.对比难度是否一致，若一致则重新抽取，若不一致则保存
			ResultSet resultsAllPropertisOfAWord = queryWithManyWays
					.checkPropertyDependOnId(randomId);
			if (resultsAllPropertisOfAWord.hasNext()) {
				while (resultsAllPropertisOfAWord.hasNext()) {

					QuerySolution solutionAllPropertisOfAWord = resultsAllPropertisOfAWord
							.next();
					String difficultyOfThisWord = substringManage(solutionAllPropertisOfAWord
							.get("?propertyDifficulty").toString()); // 该ID对应的难度

					if (twoWordsDependOnDifficultyList.size() == 0) { // 难度1的单词没找到
						if (difficultyOfThisWord.equals(yourDifficultyOfWord1)) { // 若随机单词难度与所给难度1相等，则保存该单词
							twoWordsDependOnDifficultyList
									.add(substringManage(solutionAllPropertisOfAWord
											.get("?instanceLabel").toString()));
						} else {
							continue;
						}
					} else { // 难度1的单词找到了，难度2的单词没找到
						if (difficultyOfThisWord.equals(yourDifficultyOfWord2)) { // 若随机单词难度与所给难度1相等，则保存该单词
							twoWordsDependOnDifficultyList
									.add(substringManage(solutionAllPropertisOfAWord
											.get("?instanceLabel").toString()));
						} else {
							continue;
						}
					}
				}
			}
		}

		return twoWordsDependOnDifficultyList;
	}

	@Override
	public List<ResultSet> TwoRandomWordsOfThisGrade(String yourGrade) {
		List<ResultSet> twoRandomWordsOfThisGradeList = new ArrayList<ResultSet>(); // 用于保存这两个单词

		// 1.查找该年级所有单词的ID
		List<String> allIdOfThisGradeList = QueryAllWordsIdOfThisGrade(yourGrade);
		String ID1 = new String();

		while (twoRandomWordsOfThisGradeList.size() < 2) { // 需要再随机找两个对应所给难度的单词{
			// 2.随机抽取一个ID
			Random ra = new Random();
			int randomNum = ra.nextInt(allIdOfThisGradeList.size() - 1);
			System.out.println("随机索引为：" + randomNum);
			String randomId = allIdOfThisGradeList.get(randomNum).toString();
			System.out.println("随机单词ID为：" + randomId);

			ResultSet resultsAllPropertisOfAWord = queryWithManyWays
					.checkPropertyDependOnId(randomId);
			if (twoRandomWordsOfThisGradeList.size() == 0) { // 如果一个单词还没找到，就添加进List<ResultSet>中
				ID1 = randomId;
				twoRandomWordsOfThisGradeList.add(resultsAllPropertisOfAWord);
			} else { // 如果找到了一个，需要检查找到的第二个是否与第一个重复
				if (randomId.equals(ID1)) { // 如果相等，则重复，需跳出循环重新查找
					continue;
				} else { // 如果不相等，则保存
					twoRandomWordsOfThisGradeList
							.add(resultsAllPropertisOfAWord);
				}
			}
		}

		return twoRandomWordsOfThisGradeList;
	}

	@Override
	public Boolean IfExistInFuseki(String yourWord) {
		Boolean ifExistInFuseki = false;
		ResultSet resultOfyourWord = QueryAWordAllId(yourWord);
		if (resultOfyourWord.hasNext()) {
			ifExistInFuseki = true;
		} else {
			ifExistInFuseki = false;
		}

		return ifExistInFuseki;
	}

	@Override
	public List<String> QueryTheTextOFThisWord(String yourWord,
			String yourGrade, String themeOfThisWord) {
		// 用动态数组保存该年级单词ID
		List<String> allIdOfThisGrade = QueryAllWordsIdOfThisGrade(yourGrade);
		// 用动态数组保存该年级同主题的随机单词
		List<String> twoWordsWithSameGradeAndSameThemeList = new ArrayList<String>();

		// 根据allIdOfThisGrade找该主题的所有ID，之后随机抽取2个
		List<String> wordsOfThisGradeAndThisTheme = new ArrayList<String>();
		if (allIdOfThisGrade.isEmpty()) {
			System.out.println("该年级没有单词！");
		} else {// 该年级所有单词
			System.out.println("该年级所有单词的个数：" + allIdOfThisGrade.size());

			for (int i = 0; i < allIdOfThisGrade.size(); i++) {

				ResultSet thisWordResultSet = queryWithManyWays
						.checkPropertyDependOnId(allIdOfThisGrade.get(i)
								.toString());
				// 判断是否在同一主题下
				if (thisWordResultSet.hasNext()) {
					while (thisWordResultSet.hasNext()) {
						QuerySolution thisWordSolution = thisWordResultSet
								.next();
						String topicOfThisWord = substringManage(thisWordSolution
								.get("?propertyTopic").toString());
						String functionOfThisWord = substringManage(thisWordSolution
								.get("?propertyFunction").toString());
						if (themeOfThisWord.equals(topicOfThisWord)) {
							wordsOfThisGradeAndThisTheme
									.add(subStringManage(thisWordSolution.get(
											"?instanceLabel").toString()));
						} else if (themeOfThisWord.equals(functionOfThisWord)) {
							wordsOfThisGradeAndThisTheme
									.add(subStringManage(thisWordSolution.get(
											"?instanceLabel").toString()));
						} else {
							// 跟所给主题不是同一内容，则什么都不做
						}
					}
				}
			}

			// 随机抽取2个单词
			do {
				Random ra = new Random();
				int randomNum = ra
						.nextInt(wordsOfThisGradeAndThisTheme.size() - 1);
				System.out.println("随机索引为：" + randomNum);
				String randomWord = wordsOfThisGradeAndThisTheme.get(randomNum)
						.toString();
				System.out.println("随机单词为：" + randomWord);

				List<String> randomWordsList = new ArrayList<String>();
				int j;
				// 遍历随机ID数组中的每一个元素
				for (j = 0; j < randomWordsList.size(); j++) {
					// 判断是否有重复的
					if (randomWordsList.get(j).equals(yourWord)) {
						break;
					}
					if (randomWordsList.get(j).equals(randomWord)) {
						break;
					}
				}
				if (j == randomWordsList.size()) {// 无重复
					// 把随机ID保存好
					randomWordsList.add(randomWord);
					twoWordsWithSameGradeAndSameThemeList.add(randomWord);
				} else {
					continue;
				}

			} while (twoWordsWithSameGradeAndSameThemeList.size() < 2);
		}

		return twoWordsWithSameGradeAndSameThemeList;
	}

	@Override
	public List<ResultSet> QueryAllWordsOfAUnit(String yourVersion,
			String yourBook, String yourUnit) {
		// 1.根据版本查找该版本所有单词
		ResultSet allWordsOfAUnitResultSet = queryWithManyWays
				.checkAllWordsOfAVersion(yourVersion);

		// 2.根据propertyVersion找到第2位（册数）和第3位（单元），并与给定册数和单元进行对比，若对比成功则保存ID
		// 用List<String>保存相应的ID
		List<String> allIDOfAUnitList = new ArrayList<String>();

		if (allWordsOfAUnitResultSet.hasNext()) {
			while (allWordsOfAUnitResultSet.hasNext()) {
				QuerySolution allWordsOfAUnitSolution = allWordsOfAUnitResultSet
						.next();
				// 抽取出ID
				String ID = substringManage4(allWordsOfAUnitSolution.get(
						"?propertyVersion").toString());
				// 抽取出第2位（册数）和第3位（单元）
				String stringExcept1 = substringManage5(ID);
				String book = substringManage6(stringExcept1);
				String stringExcept2 = substringManage5(stringExcept1);
				String unit = substringManage6(stringExcept2);

				if (book.equals(yourBook)) {
					if (unit.equals(yourUnit)) {
						// 是我要的册数和单元，则保存该ID
						allIDOfAUnitList.add(ID);
					} else {
						// 什么都不做
					}
				} else {
					// 什么都不做
				}
			}
		} else {
			System.out.println("该版本没有单词");
		}

		// 3.根据ID找该单词的所有属性
		// 用List<ResultSet>保存最后的结果集
		List<ResultSet> resultOfAllWordsOfAUnit = new ArrayList<ResultSet>();
		for (int i = 0; i < allIDOfAUnitList.size(); i++) {
			resultOfAllWordsOfAUnit.add(queryWithManyWays
					.checkPropertyDependOnId(allIDOfAUnitList.get(i)));
		}

		return resultOfAllWordsOfAUnit;
	}

	@Override
	public void WriteBackToOwl() throws IOException {

		writeOwl.writeBackToOwl();

	}

	@Override
	public void WriteBackToRespectiveOwl() throws IOException {

		writeOwl.writeBackToRespectiveOwl();

	}

	@Override
	public void InsertRelationSameAs(InputStream yourPath)
			throws BiffException, IOException {
		List<String[]> list = readExcel(yourPath);

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			System.out.println("i = " + i);
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("要添加的Excel中的内容" + str[j]);
			}

			String question1 = null;
			question1 = str[0];

			String question2 = null;
			question2 = str[1];
			// 用“_”代替“ ”
			question1 = question1.replace(" ", "_");
			question2 = question2.replace(" ", "_");
			System.out.println("question1:" + question1);
			System.out.println("question2:" + question2);

			// 判断question2是否在数据库中
			boolean ifInDB = queryWithManyWays.checkIfInDB(question2);
			String ID = null;
			if (ifInDB == false) {
				// 抽取question2父类(可调用王文辉接口找父类)在这里用我自己的笨方法
				String[] sentenceAllClass = { "Where", "What", "How", "When",
						"Which", "Would", "Shall", "Have", "Why", "Whose",
						"Is", "May", "Could", "Can", "Did", "Will", "Do",
						"Dose", "Was", "Are" };
				String[] sentenceAllClass2 = { "where", "what", "how", "when",
						"which", "would", "shall", "have", "why", "whose",
						"Is", "may", "could", "can", "did", "will", "do",
						"dose", "was", "are" };
				String question2Class = null;
				int j = 0;
				for (j = 0; j < sentenceAllClass.length; j++) {
					if (question2.contains(sentenceAllClass[j])) {
						question2Class = sentenceAllClass[j];
						System.out.println("question2Class: " + question2Class);
						break;
					} else if (question2.contains(sentenceAllClass2[j])) {
						question2Class = sentenceAllClass[j];
						System.out.println("question2Class: " + question2Class);
						break;
					}
				}
				if (j >= sentenceAllClass.length) {
					System.out.println("没有找到该句子的父类");
				}

				// 若有缩写，如：What's
				// if (question2Class.contains("'")) {
				// question2Class = question2Class.substring(0,
				// question2Class.indexOf("'"));
				// }

				// 添加question2父类
				addIndividualAndProperty.addSentenceClass(question2Class,
						question2);

				// 添加标签Label
				addIndividualAndProperty.addSentenceLabel(question2);

				// 添加句子ID
				System.out.println("句子的版本");
				// Scanner sc = new Scanner(System.in);
				// String sentenceVersion = sc.nextLine();
				String sentenceVersion = str[2];
				System.out.println("句子的册数（1-12）");
				// sc = new Scanner(System.in);
				// String sentenceBook = sc.nextLine();
				String sentenceBook = str[3];
				System.out.println("句子的单元数");
				// sc = new Scanner(System.in);
				// String sentenceUnit = sc.nextLine();
				String sentenceUnit = str[4];
				ID = sentenceVersion + "/" + sentenceBook + "/" + sentenceUnit
						+ "/";

				int countID = 1;
				// 查找出所有三元组
				ResultSet resultAllTriples = queryWithManyWays.checkAllTriple();
				ResultSet resultInstanceAllProperty = null;
				QuerySolution solutionAllTriples = null;
				if (resultAllTriples.hasNext()) {
					while (resultAllTriples.hasNext()) {
						// QuerySolution next()
						// Moves onto the next result.
						// 移动到下个result上
						solutionAllTriples = resultAllTriples.next();
						if (solutionAllTriples.get("?s").toString()
								.contains("'")) {
							resultInstanceAllProperty = queryWithManyWays
									.checkAllID(
											solutionAllTriples
													.get("?s")
													.toString()
													.substring(
															solutionAllTriples
																	.get("?s")
																	.toString()
																	.indexOf(
																			"'") + 1,
															solutionAllTriples
																	.get("?s")
																	.toString()
																	.lastIndexOf(
																			"'")),
											"85");// 查找该实例的所有ID
							if (resultInstanceAllProperty.hasNext()) {// 算出同版本、同册数、同单元的单词个数，计算ID
								while (resultInstanceAllProperty.hasNext()) {
									QuerySolution solution = resultInstanceAllProperty
											.next();
									if (solution.get("?allID").toString()
											.contains(ID)) {// 若同版本、同册数、同单元
										countID++;// 则countID加1
									} else {
										continue;
									}
								}
							} else {
								System.out
										.println("无同版本、同册数、同单元的单词，countID不变 = 1");
							}
							System.out.println("countID: " + countID);
						} else {
							continue;
						}

					}
				} else {
					System.out.println("无三元组");
				}

				System.out.println("最后的countID: " + countID);
				ID = ID + String.valueOf(countID);
				// 添加ID
				addIndividualAndProperty.addSentencePropertyForModify("句子ID",
						question2, ID, ID);

			} else {
				// 什么都不做
			}

			// 添加等价关系
			addIndividualAndProperty.addRelationSameAs(question1, question2);
			// addIndividualAndProperty.addRelationSameAs(question2, question1);
		}
		System.out.println("批量插入等价关系成功！");

	}

	@Override
	public ArrayList<String> ReasonSameAs(String yourSentence) {

		yourSentence = yourSentence.replace(" ", "_");

		InfModel inf = myReasoner.ReasonSameAs();

		// 通过标签Label找URI
		ResultSet result = queryWithManyWays.checkOnlyInstanceURI(yourSentence);

		System.out.println(yourSentence + " * * =>\n");
		// Iterator list = null;
		StmtIterator list = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// 移动到下个result上
				QuerySolution solution = result.next();
				Resource yourSentenceSubject = solution.get("?instance")
						.asResource();
				// listStatements(Resource subject, Property predicate, RDFNode
				// object, Model posit)
				// Find all the statements matching a pattern.
				list = inf.listStatements(yourSentenceSubject, null,
						(RDFNode) null);
			}
		}

		ArrayList<String> allString = new ArrayList<String>();
		int index = 0;
		// while (list.hasNext()) {
		// String[] listStringArray = list.next().toString().split(",");
		//
		// for (String ss : listStringArray) {
		// // System.out.println(ss);
		// allString.add(ss);
		// index++;
		// }
		// }

		while (list.hasNext()) {
			String[] listStringArray = list.next().toString().split("]");

			for (String ss : listStringArray) {// 分解出每一个三元组
				// System.out.println(ss);

				String str1 = ss.substring(0, ss.indexOf(","));
				System.out.println("str1————" + str1);
				allString.add(str1);
				index++;

				String str2 = ss.substring(ss.indexOf(",") + 1, ss.length());
				// System.out.println("str2————" + str2);

				String str3 = str2.substring(0, str2.indexOf(","));
				System.out.println("str3————" + str3);
				allString.add(str3);
				index++;

				if (str2.substring((str2.indexOf(",") + 1)).contains("\"")) {
					String str4 = str2.substring(str2.indexOf("\"") + 1,
							str2.lastIndexOf("\""));
					if (str4.contains(")")) {
						str4 = str4.substring(str4.indexOf(")") + 1);
					}
					System.out.println("str4————" + str4 + "\n");
					allString.add(str4);
					index++;
				} else if (str2.substring((str2.indexOf(",") + 1))
						.contains("'")) {
					String str4 = str2.substring(str2.indexOf("\'") + 1,
							str2.lastIndexOf("\'"));
					System.out.println("str4————" + str4 + "\n");
					allString.add(str4);
					index++;
				} else {
					String str4 = str2.substring(str2.indexOf(",") + 1);
					if (str4.contains(",")) {
						str4 = str2.substring(str2.indexOf(",") + 1,
								str2.indexOf(","));
					}
					System.out.println("str4————" + str4 + "\n");
					allString.add(str4);
					index++;
				}
			}
		}

		ArrayList<String> returnString = new ArrayList<String>();
		for (int i = 0; i < index; i++) {
			String everyString = allString.subList(i, i + 1).toString();
			if (i % 3 == 1) {

				// 删除第一个空格
				if (everyString.contains("]")) {
					everyString = everyString.substring(
							everyString.indexOf(" ") + 1,
							everyString.indexOf("]"));
				}
				if (everyString.equals(RDFS.label.toString())) {
					returnString.add("句子Label");
				} else if (everyString.equals(RDF.type.toString())) {
					returnString.add("问题父类");

					everyString = allString.subList(i + 1, i + 2).toString();
					everyString = everyString.substring(
							everyString.indexOf(" ") + 1,
							everyString.indexOf("]"));
					everyString = QueryLabelAndReturn(everyString);
					returnString.add(everyString);
					i++;

				} else if (everyString.equals(OWL.sameAs.toString())) {
					returnString.add("等价");

					everyString = allString.subList(i + 1, i + 2).toString();
					everyString = everyString.substring(
							everyString.indexOf("[") + 1,
							everyString.indexOf("]"));
					returnString.add(everyString);
					i++;
				} else {
					everyString = QueryLabelAndReturn(everyString);
					returnString.add(everyString);
				}

			} else if (i % 3 == 2) {
				everyString = everyString.substring(
						everyString.indexOf("[") + 1, everyString.indexOf("]"));
				returnString.add(everyString);
			}

		}
		return returnString;
	}

	// 根据单词查看所有同级单词及其属性
	@Override
	public List<ResultSet> QueryBrotherIndividual(String yourGrade, String yourTheme) {
		// // 查找该单词的的主题属性值
		// String[] theme = { "?propertyTopic", "?propertyFunction"};
		// ResultSet resultsTopicValue = queryWithManyWays
		// .checkTopicValue(yourWord);
		// String yourThemeValue = null;
		// String yourThemeValueFlag1 = null;
		// String yourThemeValueFlag2 = null;
		// String themeSPARQL = null;
		// if (resultsTopicValue.hasNext()) {
		// while (resultsTopicValue.hasNext()) {
		// // QuerySolution next()
		// // Moves onto the next result.
		// // 移动到下个result上
		// QuerySolution solutionPropertyValue = resultsTopicValue.next();
		// for (int i = 0; i < theme.length; i++) {
		// yourThemeValue = solutionPropertyValue.get(theme[i])
		// .toString();
		// if (yourThemeValue.contains("无")) {
		// continue;
		// } else {
		// yourThemeValueFlag1 = substringManage3(yourThemeValue);
		// yourThemeValueFlag2 = substringManage2(yourThemeValue);
		// themeSPARQL = theme[i];
		// }
		// }
		// System.out.println(yourWord + "的主题是： " + yourThemeValue);
		// }
		// } else {
		// System.out.println("该单词无主题");
		// }

		String yourThemeValueFlag1 = null;
		String yourThemeValueFlag2 = null;
		ResultSet resultsAllBrotherID = null;
		if (yourTheme.contains("-")) {
			yourThemeValueFlag1 = substringManage2(yourTheme);
			yourThemeValueFlag2 = substringManage3(yourTheme);

			// 根据主题属性标记，找出所有包含该标记的属性值，该属性值中包含单词ID
			resultsAllBrotherID = queryWithManyWays.checkBrotherID(
					yourThemeValueFlag1, yourThemeValueFlag2);
		} else {
			// 根据主题属性标记，找出所有包含该标记的属性值，该属性值中包含单词ID（非课标定义主题）
			resultsAllBrotherID = queryWithManyWays.checkBrotherID2(yourTheme);
		}

		List<ResultSet> brotherAllResultSet = new ArrayList<ResultSet>();
		if (resultsAllBrotherID.hasNext()) {
			while (resultsAllBrotherID.hasNext()) {
				QuerySolution solutionBrotherID = resultsAllBrotherID.next();
				if (solutionBrotherID.get("?propertyTheme").toString()
						.contains("|")) {
					// 跳过，什么都不做
				} else {
					String brotherTheme = solutionBrotherID.get(
							"?propertyTheme").toString();

					// 提取ID
					String brotherID = substringManage4(brotherTheme);
					// 提取册数
					String bookOfThisWord = interceptBook(brotherID);
					String gradeOfThisWord = bookToGrade(bookOfThisWord);

					// 判断ID的第二位，即册数，是否等于yourGrade
					if(gradeOfThisWord.equals(yourGrade)){
						// 若等于，则查找每个单词ID对应的所有属性
						ResultSet resultsBrother = queryWithManyWays
								.checkPropertyDependOnId(brotherID);
						brotherAllResultSet.add(resultsBrother);
					}else{
						// 若不等于，则什么也不做
					}
					
				}

			}
		} else {
			System.out.println("该主题无单词");
		}

		return brotherAllResultSet;
	}

	// 静态方法-------------------------------------------------------------------------------------------------------------------------------------
	// 处理字符串：取@之前的字符
	private static String subStringManage(String string) {
		string = string.substring(0, string.indexOf("@"));
		return string;
	}

	// 处理字符串：读取“)”和“@”之间的字符串
	private static String substringManage(String string) {
		String newString = string.substring(string.indexOf(")") + 1,
				string.lastIndexOf("@"));
		return newString;
	}

	// 处理字符串：读取主题属性值中“.”和“-”之间的字符串
	private static String substringManage2(String string) {
		String newString = string.substring(string.indexOf(".") + 1,
				string.lastIndexOf("-"));
		return newString;
	}

	// 处理字符串：读取主题属性值中“)”之后，或者“)”和“-”之间的字符串
	private static String substringManage3(String string) {
		String newString = string.substring(string.indexOf("）") + 1,
				string.length());
		if (newString.contains("-")) {
			newString = newString.substring(0, newString.indexOf("-"));
		}
		return newString;
	}

	// 处理字符串：读取主题属性值中第一个“(”和第一个“)”之间的字符串
	private static String substringManage4(String string) {
		String newString = string.substring(string.indexOf("(") + 1,
				string.indexOf(")"));
		return newString;
	}

	// 处理字符串：读取第一个“/”到最后的字符串
	private static String substringManage5(String string) {
		String newString = string.substring(string.indexOf("/") + 1,
				string.length());
		return newString;
	}

	// 处理字符串：读取从开始到第一个“/”的字符串
	private static String substringManage6(String string) {
		String newString = string.substring(0, string.indexOf("/"));
		return newString;
	}

	// 处理字符串：读取从第一个“/”到最后的字符串
	private static String substringManage7(String string) {
		String newString = string.substring(string.indexOf("/") + 1,
				string.length());
		return newString;
	}

	private static void Init() {
		try {
			read();
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void read() throws FileNotFoundException, IOException {
		ALLWORDS = new HashMap<String, Vector>();
		ALLMEANS = new HashMap<String, Vector>();
		BufferedReader in = new BufferedReader(new FileReader(new File(
				"Data/HowNet.txt")));
		String temp = in.readLine();
		while (temp != null) {
			Vector<String> DEFS;
			Vector<String> W_C;

			// 读取一个No的txt内容
			temp = in.readLine();
			String W_c = temp.substring(4);// 读取中文
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			String W_e = temp.substring(temp.indexOf("=") + 1);// 读取英文
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			String DEF = temp.substring(temp.indexOf("=") + 1);// 读取DEF

			//
			if (ALLWORDS.containsKey(W_e) && ALLMEANS.containsKey(W_e)) {
				W_C = ALLWORDS.get(W_e);
				DEFS = ALLMEANS.get(W_e);
			} else {
				W_C = new Vector<String>();
				DEFS = new Vector<String>();
			}

			/* 判断之前是否出现过同样的W_C和DEF */
			Iterator<String> It_1 = W_C.iterator();
			boolean judge_1 = false;
			while (It_1.hasNext()) {
				String m = It_1.next();
				if (m.equals(W_c)) {
					judge_1 = true;
					break;
				}
			}
			/* 判断之前是否出现过同样的DEF */
			Iterator<String> It_2 = DEFS.iterator();
			boolean judge_2 = false;
			while (It_2.hasNext()) {
				String m = It_2.next();
				if (m.equals(DEF)) {
					judge_2 = true;
					break;
				}
			}
			if (!judge_1) {
				W_C.add(W_c);
			}
			if (!judge_2) {
				DEFS.add(DEF);
			}
			ALLWORDS.put(W_e, W_C);
			ALLMEANS.put(W_e, DEFS);
			temp = in.readLine();
			temp = in.readLine();
		}
		in.close();
		System.out.println("HowNet Load Succeed!");
		// findDEF("man");
	}

	// 查找Hownet中的父类
	private static String findDEF(String W_e) {
		if (W_e.contains("@")) {
			W_e = subStringManage(W_e);
		}
		String yourClass = null;
		@SuppressWarnings("unchecked")
		Vector<String> DEFS = ALLMEANS.get(W_e);
		ArrayList<String> result = new ArrayList<String>();
		if (DEFS == null || DEFS.size() <= 0) {
			System.out.println(W_e + "的父类不在HowNet中");
		} else {
			for (int i = 0; i < DEFS.size(); i++) {
				// System.out.println(m.get(i)[1]);
				result.add(DEFS.get(i));
			}
			System.out.println("请选择你需要的父类（仅输入整数）");
			int num = 0;
			for (String temp : result) {
				num++;
				System.out.println(num + temp);
			}

			Scanner sc = new Scanner(System.in);
			int yourClassNum = 0;
			yourClassNum = sc.nextInt();
			num = 0;
			for (String temp : result) {
				num++;
				if (num == yourClassNum) {
					System.out.println(num + temp);
					yourClass = temp;
				}
			}
			int index = 0;
			index = yourClass.indexOf(":");
			System.out.println("index: " + index);
			if (index == -1) {
				yourClass = yourClass.substring(1, yourClass.length() - 1);
			} else {
				yourClass = yourClass.substring(1, index);
			}
			System.out.println("你选择的父类：" + yourClass);
		}
		return yourClass;
	}

	// 修改单词ID
	private static void modifyID(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel) {
		// 删除该三元组
		deleteIndividual.deleteInstanceProperty(solution.get("?instanceLabel")
				.toString(), solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的属性值:");
		String yourModifyValue = sc.nextLine();
		String yourID = yourModifyValue;
		addIndividualAndProperty.addPropertyForModify(yourPropertyLabel,
				solution.get("?instanceLabel").toString(), yourModifyValue,
				yourID);

		for (int i = 0; i < propertyRelation.length; i++) {
			if (propertyLabel[i].equals("单词")
					|| propertyLabel[i].equals("Hownet中的父类")
					|| propertyLabel[i].equals("单词ID")) {
				// 什么都不做
			} else {

				// 保存之前的属性值
				String prePropertyValue = solution.get(propertySPARQLValue[i])
						.toString();
				System.out.println("prePropertyValue:" + prePropertyValue);
				String newPropertyValue = prePropertyValue
						.substring(prePropertyValue.indexOf(")") + 1);
				System.out.println("newPropertyValue:" + newPropertyValue);

				// 删除该三元组
				deleteIndividual.deleteInstanceProperty(
						solution.get("?instanceLabel").toString(), solution
								.get(propertyRelation[i]).toString(), solution
								.get(propertySPARQLValue[i]).toString());

				// 添加新的三元组
				addIndividualAndProperty.addPropertyForModify(propertyLabel[i],
						solution.get("?instanceLabel").toString(),
						newPropertyValue, yourID);

			}
		}
	}

	// 修改单词父类
	private static void modifyClass(QuerySolution solution,
			String yourSPARQLProperty) {
		Init();
		// 删除该三元组
		deleteIndividual.deleteClass(solution.get("?instanceLabel").toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		System.out.println("请选择你想修改的父类:");
		// 查找要添加的实例的Hownet父类
		String yourClass = findDEF(solution.get("?instanceLabel").toString());

		addIndividualAndProperty.addClass(yourClass,
				solution.get("?instanceLabel").toString());
	}

	// 修改单词Label
	private static void modifyLabel(QuerySolution solution, String yourID) {
		// 修改Label
		// 删除该三元组
		deleteIndividual.deleteLabel(solution.get("?instanceLabel").toString());
		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的属性值:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addLabel(yourModifyValue);

		// 修改类
		// 删除该三元组
		deleteIndividual.deleteClass(solution.get("?instanceLabel").toString(),
				solution.get("?propertyClass").toString());
		// 添加新的三元组
		addIndividualAndProperty.addClass(solution.get("?propertyClass")
				.toString(), yourModifyValue);

		for (int i = 0; i < propertyRelation.length; i++) {
			if (propertyLabel[i].equals("单词")
					|| propertyLabel[i].equals("Hownet中的父类")) {
				// 什么都不做
			} else {

				// 删除相应属性的三元组
				deleteIndividual.deleteInstanceProperty(
						solution.get("?instanceLabel").toString(), solution
								.get(propertyRelation[i]).toString(), solution
								.get(propertySPARQLValue[i]).toString());

				// 添加相应属性的三元组
				addIndividualAndProperty.addPropertyForModify(propertyLabel[i],
						yourModifyValue, solution.get(propertySPARQLValue[i])
								.toString(), yourID);

			}
		}
	}

	// 修改单词属性
	private static void modifyPropertyValue(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel, String yourID) {
		// 删除该三元组
		deleteIndividual.deleteInstanceProperty(solution.get("?instanceLabel")
				.toString(), solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的单词属性:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addPropertyForModify(yourPropertyLabel,
				solution.get("?instanceLabel").toString(), yourModifyValue,
				yourID);
	}

	// 修改句子ID
	private static void modifySentenceID(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel) {
		// 删除该三元组
		deleteIndividual.deleteSentenceInstanceProperty(
				solution.get("?instanceLabel").toString(),
				solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的属性值:");
		String yourModifyValue = sc.nextLine();
		String yourID = yourModifyValue;
		addIndividualAndProperty.addSentencePropertyForModify(
				yourPropertyLabel, solution.get("?instanceLabel").toString(),
				yourModifyValue, yourID);

		for (int i = 0; i < sentencePropertyRelation.length; i++) {
			if (sentencePropertyLabel[i].equals("问题")
					|| sentencePropertyLabel[i].equals("问题句型")
					|| sentencePropertyLabel[i].equals("句子ID")) {
				// 什么都不做
			} else {

				// 保存之前的属性值
				String prePropertyValue = solution.get(
						sentencePropertySPARQLValue[i]).toString();
				System.out.println("prePropertyValue:" + prePropertyValue);
				String newPropertyValue = prePropertyValue
						.substring(prePropertyValue.indexOf(")") + 1);
				System.out.println("newPropertyValue:" + newPropertyValue);

				// 删除该三元组
				deleteIndividual
						.deleteSentenceInstanceProperty(
								solution.get("?instanceLabel").toString(),
								solution.get(sentencePropertyRelation[i])
										.toString(),
								solution.get(sentencePropertySPARQLValue[i])
										.toString());

				// 添加新的三元组
				addIndividualAndProperty.addSentencePropertyForModify(
						sentencePropertyLabel[i], solution
								.get("?instanceLabel").toString(),
						newPropertyValue, yourID);

			}
		}
	}

	// 修改句子父类
	private static void modifySentenceClass(QuerySolution solution,
			String yourSPARQLProperty) {
		// 删除该三元组
		deleteIndividual.deleteSentenceClass(solution.get("?instanceLabel")
				.toString(), solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		System.out.println("请选择你想修改的问题句型:");
		// 查找要添加的实例的Hownet父类
		String yourClass = solution
				.get("?instanceLabel")
				.toString()
				.substring(0,
						solution.get("?instanceLabel").toString().indexOf("_"));
		// 若有缩写，如：What's
		if (yourClass.contains("'")) {
			yourClass = yourClass.substring(0, yourClass.indexOf("'"));
		}

		addIndividualAndProperty.addSentenceClass(yourClass,
				solution.get("?instanceLabel").toString());
	}

	// 修改句子Label
	private static void modifySentenceLabel(QuerySolution solution,
			String yourID) {
		// 修改Label
		// 删除该三元组
		deleteIndividual.deleteSentenceLabel(solution.get("?instanceLabel")
				.toString());
		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的问题:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addSentenceLabel(yourModifyValue);

		// 修改类
		// 删除该三元组
		deleteIndividual.deleteSentenceClass(solution.get("?instanceLabel")
				.toString(), solution.get("?propertyClass").toString());
		// 添加新的三元组
		addIndividualAndProperty.addSentenceClass(solution
				.get("?propertyClass").toString(), yourModifyValue);

		for (int i = 0; i < sentencePropertyRelation.length; i++) {
			if (sentencePropertyLabel[i].equals("问题")
					|| sentencePropertyLabel[i].equals("问题句型")) {
				// 什么都不做
			} else {

				// 删除相应属性的三元组
				deleteIndividual
						.deleteSentenceInstanceProperty(
								solution.get("?instanceLabel").toString(),
								solution.get(sentencePropertyRelation[i])
										.toString(),
								solution.get(sentencePropertySPARQLValue[i])
										.toString());

				// 添加相应属性的三元组
				addIndividualAndProperty
						.addSentencePropertyForModify(sentencePropertyLabel[i],
								yourModifyValue,
								solution.get(sentencePropertySPARQLValue[i])
										.toString(), yourID);

			}
		}
	}

	// 修改句子属性
	private static void modifySentencePropertyValue(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel, String yourID) {
		// 删除该三元组
		deleteIndividual.deleteSentenceInstanceProperty(
				solution.get("?instanceLabel").toString(),
				solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的句子属性:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addSentencePropertyForModify(
				yourPropertyLabel, solution.get("?instanceLabel").toString(),
				yourModifyValue, yourID);
	}

	// 读取Excel，返回字符串数组
	private static List<String[]> readExcel(InputStream yourPath)
			throws BiffException, IOException {
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;
		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(yourPath);

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);

		// 行数(表头的目录不需要，从1开始)
		for (int i = 0; i < sheet.getRows(); i++) {

			// 创建一个数组 用来存储每一列的值
			String[] str = new String[sheet.getColumns()];

			// 列数
			for (int j = 0; j < sheet.getColumns(); j++) {

				// 获取第i行，第j列的值
				cell = sheet.getCell(j, i);
				str[j] = cell.getContents();

			}
			// 把刚获取的列存入list
			list.add(str);
		}
		return list;
	}

	// 查询Label，并返回完整无杂质Label
	private static String QueryLabelAndReturn(String URI) {
		ResultSet resultPropertyLabel = queryWithManyWays
				.checkOnlyPropertyLabel(URI);
		String label = null;
		if (resultPropertyLabel.hasNext()) {
			while (resultPropertyLabel.hasNext()) {

				QuerySolution solutionPropertyLabel = resultPropertyLabel
						.next();
				label = solutionPropertyLabel.get("propertyLabel").toString();

				if (label.contains("@")) {
					label = label.substring(0, label.indexOf("@"));
				}
			}
		}
		return label;
	}

	// 查询某年级所有单词ID
	private static List<String> QueryAllWordsIdOfThisGrade(String yourGrade) {
		// 根据年级计算册数
		int yourGradeInt = Integer.parseInt(yourGrade);
		yourGradeInt = yourGradeInt * 2;

		// 查找所有单词的ID
		ResultSet resultsIdOfAllWords = queryWithManyWays.checkIdOfAllWords();

		// 用动态数组保存该年级单词ID
		List<String> allIdOfThisGrade = new ArrayList<String>();

		// 筛选该年级的ID
		if (resultsIdOfAllWords.hasNext()) {
			while (resultsIdOfAllWords.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// 移动到下个result上
				QuerySolution solutionIdOfAllWords = resultsIdOfAllWords.next();
				String propertyId = solutionIdOfAllWords.get("?propertyID")
						.toString();

				// 截取掉册数之前的信息
				String propertyBook = interceptBook(propertyId);
				if (String.valueOf(yourGradeInt).equals(propertyBook)
						|| String.valueOf(yourGradeInt - 1)
								.equals(propertyBook)) {
					allIdOfThisGrade.add(solutionIdOfAllWords
							.get("?propertyID").toString());
				}
			}
		}
		return allIdOfThisGrade;
	}

	// 查询某年级所有单词ID
	private static List<String> QueryAllSentencesIdOfThisGrade(String yourGrade) {
		// 查找所有句子的ID
		ResultSet resultsIDOfAllWords = queryWithManyWays
				.checkIdOfAllSentences();

		// 用动态数组保存该年级句子ID
		List<String> allSentencesIdOfThisGrade = new ArrayList<String>();

		// 筛选该年级的ID
		if (resultsIDOfAllWords.hasNext()) {
			while (resultsIDOfAllWords.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// 移动到下个result上
				QuerySolution solutionIdOfAllWords = resultsIDOfAllWords.next();
				String propertyId = solutionIdOfAllWords.get("?propertyID")
						.toString();
				// 截取掉册数之前的信息
				String stringOutOfVersion = propertyId.substring(propertyId
						.indexOf("/") + 1);
				String propertyBook = stringOutOfVersion.substring(0,
						stringOutOfVersion.indexOf("/"));
				// System.out.println("该单词的册数：" + propertyBook);
				if (yourGrade.equals(propertyBook)) {
					allSentencesIdOfThisGrade.add(solutionIdOfAllWords.get(
							"?propertyID").toString());
				}
			}
		} else {
			System.out.println("该ID不对应任何三元组");
		}

		return allSentencesIdOfThisGrade;
	}

	// 通过ID截取出册数信息
	private static String interceptBook(String yourId) {
		// 截取掉册数之前的信息
		String stringOutOfVersion = yourId.substring(yourId.indexOf("/") + 1);
		String propertyBook = stringOutOfVersion.substring(0,
				stringOutOfVersion.indexOf("/"));
		// System.out.println("该单词的册数：" + propertyBook);

		return propertyBook;
	}

	// 根据册数计算出所在年级
	private static String bookToGrade(String yourBook) {

		int bookOfThisWordInt = Integer.valueOf(yourBook);
		int gradeOfThisWord = (int) Math.ceil((float) bookOfThisWordInt / 2);
		String yourBookString = String.valueOf(gradeOfThisWord);

		return yourBookString;
	}

	// 根据年级计算册数
	private static List<String> gradeToBook(String yourGrade) {
		List<String> book = new ArrayList<String>();
		int gradeOfThisWordInt = Integer.valueOf(yourGrade);
		for (int i = 0; i < 2; i++) {
			int bookOfThisWord = gradeOfThisWordInt * 2 - i;
			book.add(String.valueOf(bookOfThisWord));
		}
		return book;
	}
}
