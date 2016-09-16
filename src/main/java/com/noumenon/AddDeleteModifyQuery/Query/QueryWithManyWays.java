package com.noumenon.AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QueryWithManyWays {

	/**
	 * 根据Id查找单词及其属性：返回ResultSet----------------------------------
	 * 
	 * @param yourId
	 *            ：单词ID
	 * @return
	 */
	public ResultSet checkPropertyDependOnId(String yourId);

	/**
	 * 根据Id查找句子及其属性：返回值ResultSet---------------------------------
	 * 
	 * @param yourId
	 *            ：句子ID
	 * @return
	 */
	public ResultSet checkSentencePropertyDependOnId(String yourId);

	/**
	 * 查找一个类下的所有实例标签Label：返回值ResultSet--------------------------
	 * 
	 * @param yourClass
	 *            ：单词的类
	 * @return
	 */
	public ResultSet checkInstance(String yourClass);
	
	/**
	 * 查找该父类下的所有ID：返回值ResultSet-----------------------------------
	 * 
	 * @param yourClass
	 * @return
	 */
	public ResultSet checkIDDependOnClass(String yourClass);

	/**
	 * 查找该实例的所有实例及其属性：返回值ResultSet-------------------------------
	 * 
	 * @param yourWord
	 *            ：单词
	 * @return
	 */
	public ResultSet checkProperty(String yourWord);
	
	/**
	 * 查询一个单词对应的所有ID
	 * 
	 * @param yourWord
	 *            ：单词
	 * @return
	 */
	public ResultSet checkAllIdOfAnWord(String yourWord);
	
	/**
	 * 查询一个句子对应的所有ID
	 * 
	 * @param yourSentence
	 *                ：句子
	 * @return
	 */
	public ResultSet checkAllIdOfAnSentence(String yourSentence);

	/**
	 * 根据实例名称查找句子所有属性值：返回值ResultSet-----------------------------
	 * 
	 * @param yourSentence
	 * @return
	 */
	public ResultSet checkSentenceProperty(String yourSentence);

	/**
	 * 判断数据库中是否存在此实例：返回值boolean--------------------------------
	 * 
	 * @param Instance
	 * @return
	 */
	public boolean checkIfInDB(String Instance);

	/**
	 * 查找所有三元组：返回值ResultSet--------------------------------------
	 * 
	 * @return
	 */
	public ResultSet checkAllTriple();
	
	/**
	 * 查找等价sameAs关系
	 * 
	 * @param yourInstance
	 * @return
	 */
	public ResultSet checkPropertySameAs(String yourInstance);
	
	/**
	 * 只找实例的Label
	 * 
	 * @param yourInstance
	 * @return
	 */
	public ResultSet checkOnlyInstanceURI(String yourInstance);
	
	/**
	 * 只找属性名称
	 * 
	 * @param yourPropertyURI
	 * @return
	 */
	public ResultSet checkOnlyPropertyLabel(String yourPropertyURI);
	
	/**
	 * 根据ID查找其父类+标签Label+ID的三元组(为修改特例设计的)
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet checkClass_Label(String yourID);
	
	/**
	 * 根据单词查找它的主题属性值
	 * @param yourWord
	 * @return
	 */
	public ResultSet checkTopicValue(String yourWord);
	
	/**
	 * 根据主题属性标记，找出所有包含该标记的属性值，该属性值中包含单词ID
	 * @param yourThemeValueFlag1
	 * @param yourThemeValueFlag2
	 * @return
	 */
	public ResultSet checkBrotherID(String yourThemeValueFlag1, String yourThemeValueFlag2);
	
	/**
	 * 根据主题属性标记，找出所有包含该标记的属性值，该属性值中包含单词ID(主题为自己定义的)
	 * @param yourTheme
	 * @return
	 */
	public ResultSet checkBrotherID2(String yourTheme);
	
	/**
	 * 根据年级随机找出5个单词和2个句子
	 * @param yourGrade
	 * @return
	 */
	public ResultSet checkAllWordsOfThisGrade(String yourGrade);
	
	/**
	 * 查询所有的单词的ID
	 * @return
	 */
	public ResultSet checkIdOfAllWords();
	
	/**
	 * 查询所有的句子的ID
	 * @return
	 */
	public ResultSet checkIdOfAllSentences();
	
	/**
	 * 查询某年级所有单词的ID
	 * @return
	 */
	public ResultSet checkAllIdOfThisGrade(String yourGrade);
	
	/**
	 * 查询某主题-功能意念的所有单词的ID
	 * @param yourPropertyFunction
	 * @return
	 */
	public ResultSet checkAllIdOfThisPropertyFunction(String yourPropertyFunction);
	
	/**
	 * 根据版本查找该版本所有单词：返回值ResultSet-------------------------------
	 * @param yourVersion
	 * @return
	 */
	public ResultSet checkAllWordsOfAVersion(String yourVersion);
	
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 根据实例+某属性+属性值，查找该三元组：返回值ResultSet------------------------
	 * 
	 * @param yourInstance
	 *            ：实例标签
	 * @param propertyLabel
	 *            ：属性名
	 * @param flag
	 *            ：标志（单词为“31”， 句子为“85”）
	 * @return
	 */
	public ResultSet checkThisTriple(String yourInstance, String propertyLabel,
			String flag);

	/**
	 * 根据实例Label查其所有ID：返回值ResultSet-------------------------------
	 * 
	 * @param yourInstance
	 *            ：实例标签
	 * @param flag
	 * @return：标志（单词为“31”， 句子为“85”）
	 */
	public ResultSet checkAllID(String yourInstance, String flag);
	

}
