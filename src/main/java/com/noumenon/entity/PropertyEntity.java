package com.noumenon.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hp.hpl.jena.query.QuerySolution;

/**
* @author 周亮 
* @version 创建时间：2016年1月26日 下午9:51:59
* 类说明:存放单词属性内容
*/
public class PropertyEntity {
	public static String[] propertySPARQLValue = { "?propertyID",
			"?instanceLabel", "?propertyFunction", "?propertyTopic",
			"?propertyClass", "?propertyPartsOfSpeech",
			"?propertyWordProperty", "?propertyChinese", "?propertyVersion",
			"?propertyBook", "?propertyDifficulty", "?propertyText",
			"?propertyScene", "?propertyAssociate", "?propertyAntonym",
			"?propertySynonyms", "?propertyExpand", "?propertyNcyclopedia",
			"?propertyUse", "?propertyExtend", "?propertyCommonUse" };
	/**
	 * 单词ID
	 */
	private String propertyID;
	/**
	 * 单词
	 */
	private String instanceLabel;
	/**
	 * 主题-功能意念
	 */
	private String propertyFunction;
	/**
	 * 主题-话题
	 */
	private String propertyTopic;
	/**
	 * Hownet中的父类
	 */
	private String propertyClass;
	/**
	 * 词性
	 */
	private String propertyPartsOfSpeech;
	/**
	 * 词性属性
	 */
	private String propertyWordProperty;
	
	/**
	 * 中文含义
	 */
	private String propertyChinese;
	/**
	 * 单词教材版本
	 */
	private String propertyVersion;
	/**
	 * 单词册数
	 */
	private String propertyBook;
	/**
	 * 难度
	 */
	private String propertyDifficulty;
	/**
	 * 课文原句
	 */
	private String propertyText;
	/**
	 * 情境段落
	 */
	private String propertyScene;
	/**
	 * 联想
	 */
	private String propertyAssociate;
	/**
	 * 同义词
	 */
	private String propertyAntonym;
	/**
	 * 反义词
	 */
	private String propertySynonyms;
	/**
	 * 拓展
	 */
	private String propertyExtend;
	/**
	 * 百科
	 */
	private String propertyNcyclopedia;
	/**
	 * 用法
	 */
	private String propertyUse;
	/**
	 * 延伸例句
	 */
	private String propertyExpand;
	/**
	 * 常用
	 */
	private String propertyCommonUse;
	
	
	public PropertyEntity(String instanceLabel) {
		super();
		this.instanceLabel = instanceLabel;
	}
	public PropertyEntity(String propertyID, String instanceLabel, String propertyFunction, String propertyTopic,
			String propertyClass, String propertyPartsOfSpeech, String propertyWordProperty, String propertyChinese,
			String propertyVersion, String propertyBook, String propertyDifficulty, String propertyText,
			String propertyScene, String propertyAssociate, String propertyAntonym, String propertySynonyms,
			String propertyExtend, String propertyNcyclopedia, String propertyUse, String propertyExpand,
			String propertyCommonUse) {
		super();
		this.propertyID = propertyID;
		this.instanceLabel = instanceLabel;
		this.propertyFunction = propertyFunction;
		this.propertyTopic = propertyTopic;
		this.propertyClass = propertyClass;
		this.propertyPartsOfSpeech = propertyPartsOfSpeech;
		this.propertyWordProperty = propertyWordProperty;
		this.propertyChinese = propertyChinese;
		this.propertyVersion = propertyVersion;
		this.propertyBook = propertyBook;
		this.propertyDifficulty = propertyDifficulty;
		this.propertyText = propertyText;
		this.propertyScene = propertyScene;
		this.propertyAssociate = propertyAssociate;
		this.propertyAntonym = propertyAntonym;
		this.propertySynonyms = propertySynonyms;
		this.propertyExtend = propertyExtend;
		this.propertyNcyclopedia = propertyNcyclopedia;
		this.propertyUse = propertyUse;
		this.propertyExpand = propertyExpand;
		this.propertyCommonUse = propertyCommonUse;
	}
	public PropertyEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPropertyID() {
		return propertyID;
	}
	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}
	public String getInstanceLabel() {
		return instanceLabel;
	}
	public void setInstanceLabel(String instanceLabel) {
		this.instanceLabel = instanceLabel;
	}
	public String getPropertyFunction() {
		return propertyFunction;
	}
	public void setPropertyFunction(String propertyFunction) {
		this.propertyFunction = propertyFunction;
	}
	public String getPropertyTopic() {
		return propertyTopic;
	}
	public void setPropertyTopic(String propertyTopic) {
		this.propertyTopic = propertyTopic;
	}
	public String getPropertyClass() {
		return propertyClass;
	}
	public void setPropertyClass(String propertyClass) {
		this.propertyClass = propertyClass;
	}
	public String getPropertyPartsOfSpeech() {
		return propertyPartsOfSpeech;
	}
	public void setPropertyPartsOfSpeech(String propertyPartsOfSpeech) {
		this.propertyPartsOfSpeech = propertyPartsOfSpeech;
	}
	public String getPropertyWordProperty() {
		return propertyWordProperty;
	}
	public void setPropertyWordProperty(String propertyWordProperty) {
		this.propertyWordProperty = propertyWordProperty;
	}
	public String getPropertyChinese() {
		return propertyChinese;
	}
	public void setPropertyChinese(String propertyChinese) {
		this.propertyChinese = propertyChinese;
	}
	public String getPropertyVersion() {
		return propertyVersion;
	}
	public void setPropertyVersion(String propertyVersion) {
		this.propertyVersion = propertyVersion;
	}
	public String getPropertyBook() {
		return propertyBook;
	}
	public void setPropertyBook(String propertyBook) {
		this.propertyBook = propertyBook;
	}
	public String getPropertyDifficulty() {
		return propertyDifficulty;
	}
	public void setPropertyDifficulty(String propertyDifficulty) {
		this.propertyDifficulty = propertyDifficulty;
	}
	public String getPropertyText() {
		return propertyText;
	}
	public void setPropertyText(String propertyText) {
		this.propertyText = propertyText;
	}
	public String getPropertyScene() {
		return propertyScene;
	}
	public void setPropertyScene(String propertyScene) {
		this.propertyScene = propertyScene;
	}
	public String getPropertyAssociate() {
		return propertyAssociate;
	}
	public void setPropertyAssociate(String propertyAssociate) {
		this.propertyAssociate = propertyAssociate;
	}
	public String getPropertyAntonym() {
		return propertyAntonym;
	}
	public void setPropertyAntonym(String propertyAntonym) {
		this.propertyAntonym = propertyAntonym;
	}
	public String getPropertySynonyms() {
		return propertySynonyms;
	}
	public void setPropertySynonyms(String propertySynonyms) {
		this.propertySynonyms = propertySynonyms;
	}
	public String getPropertyExtend() {
		return propertyExtend;
	}
	public void setPropertyExtend(String propertyExtend) {
		this.propertyExtend = propertyExtend;
	}
	public String getPropertyNcyclopedia() {
		return propertyNcyclopedia;
	}
	public void setPropertyNcyclopedia(String propertyNcyclopedia) {
		this.propertyNcyclopedia = propertyNcyclopedia;
	}
	public String getPropertyUse() {
		return propertyUse;
	}
	public void setPropertyUse(String propertyUse) {
		this.propertyUse = propertyUse;
	}
	public String getPropertyExpand() {
		return propertyExpand;
	}
	public void setPropertyExpand(String propertyExpand) {
		this.propertyExpand = propertyExpand;
	}
	public String getPropertyCommonUse() {
		return propertyCommonUse;
	}
	public void setPropertyCommonUse(String propertyCommonUse) {
		this.propertyCommonUse = propertyCommonUse;
	}
	
	/**
	 * 从查询中构造一个单词属性实体
	 * @param solutionEachBrother 查询的值
	 * @return
	 */
	public static PropertyEntity generatePropertyEntity(QuerySolution solutionEachBrother){
		
			 String propertyID =subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[0]).toString());
			
			/**
			 * 单词
			 */
			 String instanceLabel=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[1]).toString());
			/**
			 * 主题-功能意念
			 */
			 String propertyFunction=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[2]).toString());
			/**
			 * 主题-话题
			 */
			 String propertyTopic=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[3]).toString());
			/**
			 * Hownet中的父类
			 */
			 String propertyClass=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[4]).toString());
			/**
			 * 词性
			 */
			 String propertyPartsOfSpeech=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[5]).toString());
			/**
			 * 词性属性
			 */
			 String propertyWordProperty=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[6]).toString());
			/**
			 * 中文含义
			 */
			 String propertyChinese=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[7]).toString());
			/**
			 * 单词教材版本
			 */
			 String propertyVersion=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[8]).toString());
			/**
			 * 单词册数
			 */
			 String propertyBook=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[9]).toString());
			/**
			 * 难度
			 */
			 String propertyDifficulty=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[10]).toString());
			/**
			 * 课文原句
			 */
			 String propertyText=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[11]).toString());
			/**
			 * 情境段落
			 */
			 String propertyScene=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[12]).toString());
			/**
			 * 联想
			 */
			 String propertyAssociate=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[13]).toString());
			/**
			 * 同义词
			 */
			 String propertyAntonym=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[14]).toString());
			/**
			 * 反义词
			 */
			 String propertySynonyms=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[15]).toString());
			/**
			 * 拓展
			 */
			 String propertyExtend=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[16]).toString());
			/**
			 * 百科
			 */
			 String propertyNcyclopedia=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[17]).toString());
			/**
			 * 用法
			 */
			 String propertyUse=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[18]).toString());
			/**
			 * 延伸例句
			 */
			 String propertyExpand=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[19]).toString());
			/**
			 * 常用
			 */
			 String propertyCommonUse=subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[20]).toString());
		
			 PropertyEntity propertyEntity = new PropertyEntity(propertyID, instanceLabel, propertyFunction, propertyTopic, propertyClass, propertyPartsOfSpeech, propertyWordProperty, propertyChinese, propertyVersion, propertyBook, propertyDifficulty, propertyText, propertyScene, propertyAssociate, propertyAntonym, propertySynonyms, propertyExtend, propertyNcyclopedia, propertyUse, propertyExpand, propertyCommonUse);
			 
		return propertyEntity;
	}
	/**
	 * 将本体库中单词的属性值截取出来
	 * @param string
	 * @return
	 */
	public static String subStringManage(String string) {
		String newString = string.substring(string.indexOf(")") + 1,
				string.lastIndexOf("@"));
		return newString;
	}
	@Override
	public String toString() {
		return "PropertyEntity [propertyID=" + propertyID + ", instanceLabel=" + instanceLabel + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instanceLabel == null) ? 0 : instanceLabel.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyEntity other = (PropertyEntity) obj;
		if (instanceLabel == null) {
			if (other.instanceLabel != null)
				return false;
		} else if (!instanceLabel.equals(other.instanceLabel))
			return false;
		return true;
	}
	/**
	 * 获取该单词中的从单词，即“联想propertyAssociate”、“同义词propertyAntonym”、“反义词propertySynonyms”、“拓展propertyExtend”、“常用propertyCommonUse”属性中的单词
	 * @return 如果没有从单词则返回的hashset中内容数量为0
	 */
	public HashSet<PropertyEntity> getSub() {
		//this.propertyAssociate
		HashSet<String> all = new HashSet<>();
		//String str = "burn one's boats(破釜沉舟)/in the same boat(处境相同)";
		HashSet<String> assWords=getSubWord(this.propertyAssociate);
		HashSet<String> synWords=getSubWord(this.propertySynonyms);
		HashSet<String> extWords=getSubWord(this.propertyExtend);
		HashSet<String> comWords=getSubWord(this.propertyCommonUse);
		if( assWords!=null)
			all.addAll(assWords);
		if(synWords!=null)
			all.addAll(synWords);
		if(extWords!=null)
			all.addAll(extWords);
		if(comWords!=null)
			all.addAll(comWords);
		HashSet<PropertyEntity> hashSet = new HashSet<>();
		for( String str :all){
			hashSet.add(new PropertyEntity(str));
		}
		return hashSet;
	}
	
	private HashSet<String> getSubWord(String property){
		if( property!=null && !property.equalsIgnoreCase("无")){
			String[] words =property.split("/");
			HashSet<String> listWord =new HashSet<>();
			for( String wordm : words){
				int lastIndex = 0;
					lastIndex=wordm.lastIndexOf("(");
				if( lastIndex ==-1 )
					lastIndex=wordm.lastIndexOf("（");
				String word= wordm.substring(0, lastIndex);
				listWord.add(word.trim());
			}
			if(listWord.size()>0)
				return listWord;
		}
		return null;
	}
	
	
}
