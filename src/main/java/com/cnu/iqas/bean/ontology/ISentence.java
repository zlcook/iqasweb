package com.cnu.iqas.bean.ontology;
/**
* @author 周亮 
* @version 创建时间：2015年11月9日 下午9:34:32
* 类说明
*/
public class ISentence {
	//实例
	private String instanceLabel;
	//回答
	private String propertyAnswer;
	//教材版本
	private String propertyVersion;
	//版本册数
	private String propertyBook;
	//情境对话
	private String propertyScene;
	//重要句型
	private String propertySentencePattern;
	//相关单词
	private String propertyRelatedWords;
	
	public ISentence(String instanceLabel, String propertyAnswer, String propertyVersion, String propertyBook,
			String propertyScene, String propertySentencePattern,String propertyRelatedWords ) {
		super();
		this.instanceLabel = instanceLabel;
		this.propertyAnswer = propertyAnswer;
		this.propertyVersion = propertyVersion;
		this.propertyBook = propertyBook;
		this.propertyScene = propertyScene;
		this.propertySentencePattern = propertySentencePattern;
		this.propertyRelatedWords=propertyRelatedWords;
	}
	public String getInstanceLabel() {
		return instanceLabel;
	}
	public void setInstanceLabel(String instanceLabel) {
		this.instanceLabel = instanceLabel;
	}
	public String getPropertyAnswer() {
		return propertyAnswer;
	}
	public void setPropertyAnswer(String propertyAnswer) {
		this.propertyAnswer = propertyAnswer;
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
	public String getPropertyScene() {
		return propertyScene;
	}
	public void setPropertyScene(String propertyScene) {
		this.propertyScene = propertyScene;
	}
	public String getPropertySentencePattern() {
		return propertySentencePattern;
	}
	public void setPropertySentencePattern(String propertySentencePattern) {
		this.propertySentencePattern = propertySentencePattern;
	}
	public String getPropertyRelatedWords() {
		return propertyRelatedWords;
	}
	public void setPropertyRelatedWords(String propertyRelatedWords) {
		this.propertyRelatedWords = propertyRelatedWords;
	}
	
}
