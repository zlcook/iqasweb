package com.noumenon.OntologyManage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import com.hp.hpl.jena.query.ResultSet;

public interface OntologyManage {
	/**添加一个单词及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param parameter
	 */
	@Deprecated
	public void Add(String[] parameter);

	/**添加一个句子及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param parameter
	 */
	public void AddSentence(String[] parameter);
	
	/**从Excel中批量添加单词：无返回值--------------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	@Deprecated
	public void AddBatch(InputStream yourPath) throws BiffException, IOException;
	
	/**从Excel中批量添加单词：无返回值--------------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void AddWordBatch(InputStream yourPath) throws BiffException, IOException;

	/**从Excel中批量添加句子：无返回值--------------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void AddSentenceBatch(InputStream yourPath) throws BiffException, IOException;

	/**删除一个单词及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param yourInstanceID
	 */
	public void Delete(String yourInstanceID);
	
	/**删除一个句子及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param yourInstanceID
	 */
	public void DeleteSentence(String yourInstanceID);

	/**修改一个单词的某个属性：无返回值----------------------------------------------------------
	 * 
	 * @param yourProperty
	 * @param yourPropertyLabel
	 * @param yourSPARQLProperty
	 * @param yourRelationProperty
	 */
	public void Modify(String yourProperty, String yourPropertyLabel, String yourSPARQLProperty, String yourRelationProperty);
	
	/**修改一个句子及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param yourProperty
	 * @param yourPropertyLabel
	 * @param yourSPARQLProperty
	 * @param yourRelationProperty
	 */
	public void ModifySentence(String yourProperty, String yourPropertyLabel, String yourSPARQLProperty, String yourRelationProperty);
	
	/**根据类查找类下的所有单词Label：返回结果集ResultSet-----------------------------------------
	 * 
	 * @param yourClass
	 * @return
	 */
	public ResultSet QueryWord(String yourClass);
	
	/**根据HowNet父类查看所有单词及其属性：返回结果集ResultSet-----------------------------------------
	 * 
	 * @param yourClass
	 * @return
	 */
	public List<ResultSet> QueryWordAndPropertiesDependOnClass(String yourClass);

	/**根据ID查找该单词及其所有属性：返回结果集ResultSet-------------------------------------------
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet QueryIndividualDependOnId(String yourID);

	/**查一个单词的所有属性：返回结果集ResultSet------------------------------------------------
	 * 
	 * @param yourWord
	 * @return
	 */
	public ResultSet QueryIndividual(String yourWord);
	
	/**查询单词对应的所有ID-----------------------------------------------------------------
	 * 
	 * @param yourWord
	 * @return
	 */
	public ResultSet QueryAWordAllId(String yourWord);
	
	/**查询句子对应的所有ID-----------------------------------------------------------------
	 * 
	 * @param yourSentence
	 * @return
	 */
	public ResultSet QueryASentenceAllId(String yourSentence);
		
	/**根据ID查找该句子及其所有属性：返回结果集ResultSet-------------------------------------------
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet QuerySentenceIndividualDependOnId(String yourID);

	/**查一个句子的所有属性：返回结果集ResultSet-------------------------------------------------
	 * 
	 * @param yourSentence
	 * @return
	 */
	public ResultSet QuerySentenceIndividual(String yourSentence);
	
	/**根据单词查看所有同级单词及其属性：返回结果集ResultSet-----------------------------------------
	 * 
	 * @param yourTheme
	 * @return
	 */
	public List<ResultSet> QueryBrotherIndividual(String yourGrade, String yourTheme);
	
	/**根据版本和单元查询所有单词及其属性
	 * 
	 * @param yourBook
	 * @param yourGrade
	 * @param yourUnit
	 * @return
	 */
	public List<ResultSet> QueryAllWordsOfAUnit(String yourBook, String yourGrade, String yourUnit);
	

	// 测试所需---------------------------------------------------------------------------------
	/**根据年级随机找出5个单词----------------------------------------------------------------
	 * 
	 * @param yourGrade
	 * @return
	 */
	public List<ResultSet> QueryFiveWordsOfThisGrade(String yourGrade);
	
	/**根据年级随机找出2个句子----------------------------------------------------------------
	 * 
	 * @param yourGrade
	 * @return
	 */
	public List<ResultSet> QueryTwoSentencesOfThisGrade(String yourGrade);
	
	/**根据年级随机找出3个句子----------------------------------------------------------------
	 * 
	 * @param yourGrade
	 * @return
	 */
	public List<ResultSet> QueryThreeSentencesOfThisGrade(String yourGrade);
	
	/**根据疑问词找出句子以及ID----------------------------------------------------------------
	 * 
	 * @param yourClass
	 * @return
	 */
	public Map<String, String>QuerySentenceAndId(String yourClass);
	
	/**根据年级查询与该单词主题不同的随机2个单词----------------------------------------------------------
	 * 
	 * @param yourGrade
	 * @param yourWord
	 * @return
	 */
	public List<String> QueryTwoDifferentThemeWordsOfThisGrade(String yourGrade, String yourWord);
	
	/**根据年级、两个单词的难度随机给出两个单词---------------------------------------------------------
	 * 
	 * @param yourGrade
	 * @param yourDifficultyOfWord1
	 * @param yourDifficultyOfWord2
	 * @return
	 */
	public List<String> QueryTwoWordsDependOnDifficulty(String yourGrade, String yourDifficultyOfWord1, String yourDifficultyOfWord2);
	
	/**根据年级随机给出两个单词--------------------------------------------------------------------
	 * 
	 * @param yourGrade
	 * @return
	 */
	public List<ResultSet> TwoRandomWordsOfThisGrade(String yourGrade);
	
	/**判断某单词是否存在于本体库中
	 * 
	 * @return
	 */
	public Boolean IfExistInFuseki(String yourWord);
	
	/**查询与该单词具有一样年级和主题的另外两个单词
	 * 
	 * @param yourWord
	 * @return
	 */
	public List<String> QueryTheTextOFThisWord(String yourWord, String yourGrade, String themeOfThisWord);
	
	
	//-----------------------------------------------------------------------------------------
	/**从Fuseki中写回OWL文件中，且单词和句子分开：无返回值-------------------------------------------
	 * 
	 * @throws IOException
	 */
	@Deprecated
	public void WriteBackToOwl() throws IOException;
	
	/**从Fuseki中写回OWL文件中，且单词和句子分开：无返回值-------------------------------------------
	 * 
	 * @throws IOException
	 */
	public void WriteBackToRespectiveOwl() throws IOException;

	/**从Excel中添加等价关系SameAs：无返回值---------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void InsertRelationSameAs(InputStream yourPath) throws BiffException, IOException;
	
	/**
	 * 推理等价关系
	 * 
	 * @param yourWord
	 * @return
	 */
	public ArrayList<String> ReasonSameAs(String yourSentence);
}
