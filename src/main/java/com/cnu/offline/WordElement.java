package com.cnu.offline;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnu.offline.bean.Word;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年9月8日 上午11:06:17
* 类说明:words.xml文件中一个<word></word>节点
* 
*/
public class WordElement {

	/**
	 * 单词
	 */
	private String name;
	/**
	 * 单词所属主题
	 */
	private String themenumber;
	/**
	 * 实际年级
	 */
	private int grade;
	/**
	 * 单词的属性
	 */
	private List<Property> propertys=new ArrayList<>();
	
	/**
	 * 根据pe的属性生成WordElement
	 * @param pe 
	 * @param excludeFields ,排除pe中的属性
	 * @return
	 */
	public static WordElement instance(PropertyEntity pe,List<String> excludeFields,String themenumber,int realGrade){
		WordElement we = new WordElement(pe.getInstanceLabel(),themenumber,realGrade);
		List<Property> pros = Property.createFromObject(pe, excludeFields);
		we.setPropertys(pros);
		return we;
	}
	/**
	 * 根据pe生成WordElement，但是排除了pe中的"propertyScene"、"propertyText"、"instanceLabel"三个属性
	 * @param pe
	 * @return
	 * 返回的WordElement对象中，没有pe的"propertyScene"、"propertyText"、"instanceLabel"三个属性信息
	 */
	public static WordElement instance(PropertyEntity pe,String themenumber,int realGrade){
		List<String> excludeFields = new ArrayList<>();
		excludeFields.add("propertyScene");
		excludeFields.add("propertyText");
		excludeFields.add("instanceLabel");
		excludeFields.add("propertySPARQLValue");
		return instance(pe,excludeFields,themenumber,realGrade);
	}
	
	
	public WordElement(String name, String themenumber,int realGrade) {
		super();
		this.name = name;
		this.themenumber = themenumber;
		this.grade = realGrade;
	}
	
	public int getGrade() {
		return grade;
	}
	public WordElement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Property> getPropertys() {
		return propertys;
	}
	public void setPropertys(List<Property> propertys) {
		this.propertys = propertys;
	}
	
	public String getThemenumber() {
		return themenumber;
	}
	public void setThemenumber(String themenumber) {
		this.themenumber = themenumber;
	}
	/**
	 * 根据word内容创建一个WordElement对象，但是word中有资源文件的属性会被过滤掉，比如englishmeaning、textsentence等
	 * @param wr
	 * @return
	 */
	public static WordElement createInstance(Word wr) {
		// TODO Auto-generated method stub
		WordElement we = new WordElement();
		List<Property> pros = Property.createFromObject(wr, null);
		we.setPropertys(pros);
		return we;
	}
	
	public static class Property{
		
		private String name;
		private String value;
		private String difficulty;
		private List<Pro> pros=new ArrayList<>();
		
		public static <T> List<Property> createFromObject(T pe,List<String> excludeFields){
			List<Property> pros = new ArrayList<>();
			Class clazz = pe.getClass();
		    Field[] fields =clazz.getDeclaredFields();
		    
		    for(Field field :fields ){
		    	String fieldName =field.getName();
		    	
		    	if(excludeFields==null || excludeFields.size()<=0 || !excludeFields.contains(fieldName)){
		    		try {
		    			field.setAccessible(true);
		    			Object obj =field.get(pe);
						String fieldValue =(String) obj;
						if( fieldValue!=null){
							Property pro = new Property(fieldName, fieldValue);
							pros.add(pro);
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
		    	}
		    }
			return pros;
		}
		public Property(String name) {
			super();
			this.name = name;
		}
		public Property(String name, String value) {
			this(name);
			this.value = value;
		}
		public Property(String name, String difficulty, String value) {
			this.name=name;
			this.difficulty = difficulty;
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDifficulty() {
			return difficulty;
		}
		public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}
		public List<Pro> getPros() {
			return pros;
		}
		public void setPros(List<Pro> pros) {
			this.pros = pros;
		}
		


		public static class Pro{
			private String grade;
			private String value;
			private String path;
			public Pro(String grade) {
				super();
				this.grade = grade;
			}
			public String getValue() {
				return value;
			}
			public void setValue(String value) {
				this.value = value;
			}
			public String getPath() {
				return path;
			}
			public void setPath(String path) {
				this.path = path;
			}
			public String getGrade() {
				return grade;
			}
			
		}
	}
	
	
}
/*
 * 
 * <word  name="boat">
			<!--单词ID-->
			<property name="propertyID"  value="2/8/17/2" />
			<!--主题-功能意念-->
			<property name="propertyFunction"  value="无" />
			<!--主题-话题-->
			<property name="propertyTopic"  value="17.旅游与交通-（58）交通运输方式" />
			<!--Hownet中的父类-->
			<property name="propertyClass"  value="ship|船" />
			<!--词性-->
			<property name="propertyPartsOfSpeech"  value="n." />
			<!--词性属性-->
			<property name="propertyWordProperty"  value="boats" />
			<!--中文含义-->
			<property name="propertyChinese"  value="小船；艇" />
			<!--单词教材版本-->
			<property name="propertyVersion"  value="北京版" />
			<!--单词册数-->
			<property name="propertyBook"  value="小7" />
			<!--难度-->
			<property name="propertyDifficulty"  value="2" />
			<!--百科-->
			<property name="propertyNcyclopedia"  value="船是重要的水上交通工具。在石器时代，就出现了最早的船——独木舟。" />
			<!--用法-->
			<property name="propertyUse"  value="一般boat指小船，ship指大船。" />
			
			<!--以下属性中的值有可能是单词（叫做从单词），多个单词是以斜杠(/)分隔符隔开的。从单词的资源通过单词在本文件中需找，从单词的资源至少包含单词发音和图片-->
			
			<!--联想-->
			<property name="propertyAssociate"  value="river (河)/lake (湖)" />
			<!--同义词-->
			<property name="propertyAntonym"  value="无" />
			<!--反义词-->
			<property name="propertySynonyms"  value="无" />
			<!--拓展-->
			<property name="propertyExtend"  value="A:How are you going to the island?(你们怎么去岛上？)B:We're going by boat.(我们坐船去。)" />
			<!--常用-->
			<property name="propertyCommonUse"  value="burn one's boats(破釜沉舟)/in the same boat(处境相同)" />

			<!-- 以下属性会带有音频文件 -->

			<!--课文原句，集合属性，因为推荐年级和实际年级可能会不同-->
			<property name="propertyText">
				<pro  grade="4" value="1.They also have dragon boat races.It sounds fun." path="audio/1472913787276.mp3" />
				<pro  grade="3" value="1.They're dragon boats." path="audio/1472913772950.mp3"/>

			</property>
		    <!--情境段落，集合属性，因为推荐年级和实际年级可能会不同-->
			<property name="propertyScene">
				<pro  grade="4" value="1.A:What's special about this day?B:In many places people eat zongzi.They also have dragon boat races.A:It sounds fun." path="audio/1472913787285.mp3" />
				<pro  grade="3" value="1.A:Look!What are those?B:They're dragon boats." path="audio/1472913772954.mp3"/>
			</property>

		    <!-- 以下属性在本体中不存在，但是属于单词的资源-->

			<!--单词发音-->
			<property name="pronunciation"  path="pronunciation/1472913815056.mp3" />
			<!--单词图片-->
			<property name="picture"  path="picture/1472913815051.png" />
			<!--单词视频-->
			<property name="video" difficulty="1"  path="video/1472913879535.avi" />
   </word>
 */
