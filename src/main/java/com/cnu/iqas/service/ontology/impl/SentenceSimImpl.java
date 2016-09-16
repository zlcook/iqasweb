package com.cnu.iqas.service.ontology.impl;
/**
* @author  王文辉
* @version 创建时间：2016年1月15日
* 类说明:单词类。
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.ontology.ISentence;
import com.cnu.iqas.bean.ontology.Word_simalgorithm;
import com.cnu.iqas.listener.InitializedListener;
import com.cnu.iqas.service.ontology.SentenceSim;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.AddDeleteModifyQuery.Query.QueryWithManyWays;
import com.noumenon.AddDeleteModifyQuery.Query.Impl.QueryWithManyWaysImpl;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.OntologyManage.Impl.OntologyManageImpl;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;
import edu.sussex.nlp.jws.JWS;
@Service("sentenceSim")
public class SentenceSimImpl implements SentenceSim  {
	private static JWS jws= InitializedListener.getJws();
	private static  MaxentTagger tagger= InitializedListener.getTagger();
	private static StanfordCoreNLP pipeline= InitializedListener.getPipeline();
	/**
	  * 将两组互异单词合并为一个字符串
	  * @param  str1   字符串
	  * @param  str2  字符串
	  * @return 合并后的数据存放在一个动态数组
	  */
	public ArrayList Word_Combine(String[] str1, String[] str2) {
		ArrayList temp = new ArrayList(); // 合并2个数组后存放的对象
		int i = 0;
		for (String str_temp : str1) {
			temp.add(str_temp);
		}
		for (int h = 0; h < str2.length; h++) {
			int j = 0;
			for (; j < str1.length; j++)
				if (str2[h].toString().equals(str1[j].toString()))
					break;
			if (j == str1.length) {
				temp.add(str2[h]);
			}
		}
		return temp;
	}
	/**
	  * 将英文句子进行词性标注，并返回词性数组
	  * @param str    需要进行标注的语句
	  * @param tagger 模板
	  * @return 数组
	  */
	public String[] Speech_tagging(String str, MaxentTagger tagger) {

		String _tagged = tagger.tagString(str);
		//.replaceAll(",", " ")
		String arr[] = _tagged.replaceAll(","," ").split(" ");
		String[] word_prop = new String[arr.length];
		System.out.println("=============="+_tagged+"这一步是干什么呢");

		for (int j = 0; j < arr.length; j++) {
			String temp[] = arr[j].replaceAll(",","_").split("_");
			word_prop[j] = temp[1];

		}
		System.out.println();
		return word_prop;
	}
	/**
	 * 计算句子相似度
	 * @param s_1  单词数组
	 * @param s_2 单词数组
	 * @return 句子的相似度值
	 */
	public double Sentence_similarity(double[] s_1, double[] s_2) { // 实现
																	// sim(t1,t2)=(s1.s2)/(||s1||
																	// *||s2||)
		double s = 0, Vec_1 = 0, Vec_2 = 0;
		int i = 0;
		for (i = 0; i < s_1.length; i++) {
			s = s + s_1[i] * s_2[i]; // 计算s_1.s_2
			Vec_1 = Vec_1 + Math.pow(s_1[i], 2);
			Vec_2 = Vec_2 + Math.pow(s_2[i], 2);
		}
		s = s / (Math.sqrt(Vec_1) * Math.sqrt(Vec_2));
		return s;
	}
	/**
	  * 计算单词相似度
	  * @param word_prop3
	  * @param str3
	  * @param word_prop
	  * @param str
	  * @return 相似度的值
	  */
	public double[] word_similarity(ArrayList word_prop3, ArrayList str3,
			String[] word_prop, String[] str) {
		int k;
	//	ws = new JWS(dir, "2.1");
		double[] t = new double[str3.size()];
		for (k = 0; k < str3.size(); k++) {
			if (Arrays.asList(str).contains(str3.get(k))) {
				t[k] = 1;
				System.out.print(str3.get(k) + " " + t[k] + " ");
			} else {
				t[k] = Word_Sim(word_prop3.get(k).toString(), str3.get(k)
						.toString(), word_prop, str);// （合并数组词性，合并数组单词，被比较数组词性，比较数组单词，K）
				System.out.print(str3.get(k) + " " + t[k] + " ");
			}
		}
		System.out.println();
		return t;
	}
	private double Word_Sim(String word_prop3, String str3, String[] word_prop,
			String[] str) {
		// TODO Auto-generated method stub
		ArrayList temp = new ArrayList();
		double max = 0;
		if (word_prop3.equals("NNP") || word_prop3.equals("NN")
				|| word_prop3.equals("NNS")) {

			for (int i = 0; i < word_prop.length; i++) {
				if (word_prop[i].equals("NNP") || word_prop[i].equals("NN")
						|| word_prop[i].equals("NNS")) {
					temp.add(str[i]);
				}
			}
			for (int j = 0; j < temp.size(); j++) {
				Word_simalgorithm sm = new Word_simalgorithm(temp.get(j).toString(), str3,jws);
				System.out.println("--------------------"+j+"华丽的分割线--------------");
			    //sm.start();
				double sim_degree = sm.getSimilarity();
				if (sim_degree > max)
					max = sim_degree;
			}
			if (max > 0.2)
				return max;

		}

		else if (word_prop3.equals("VBZ") || word_prop3.equals("VBN")
				|| word_prop3.equals("VB") || word_prop3.equals("VBD")
				|| word_prop3.equals("VBG") || word_prop3.equals("VBP")) {
			for (int i = 0; i < word_prop.length; i++) {
				if (word_prop[i].equals("VBZ") || word_prop[i].equals("VBN")
						|| word_prop[i].equals("VB")
						|| word_prop[i].equals("VBG")
						|| word_prop[i].equals("VBP")) {
					System.out.println(word_prop[i]);
					temp.add(str[i]);
				}
			}
			for (int j = 0; j < temp.size(); j++) {
				System.out.println("--------------------华丽的分割线"+j+"--------------");
				Word_simalgorithm sm = new Word_simalgorithm(temp.get(j).toString(), str3,jws);
				double sim_degree = sm.getSimilarity();
				if (sim_degree > max)
					max = sim_degree;
			}
			if (max > 0.2)
				return max;
		}
		return max;
	}

	/**
	  * 将两组单词进行合并为一个数组
	  * @param word_prop1   数组1
	  * @param word_prop2   数组2
	  * @param str1  字符串1
	  * @param str2 字符串2
	  * @return 合并为一个数组
	  */
	public ArrayList Word_property_Combine(String[] word_prop1,
			String[] word_prop2, String[] str1, String[] str2) {
		// TODO Auto-generated method stub
		ArrayList temp = new ArrayList(); // 合并2个数组后存放的对象
		int i = 0;
		for (String _temp : word_prop1) {
			temp.add(_temp);
		}
		for (int h = 0; h < str2.length; h++) {
			int j = 0;
			for (; j < str1.length; j++)
				if (str2[h].toString().equals(str1[j].toString()))
					break;
			if (j == str1.length) {
				temp.add(word_prop2[h]);
			}
		}
		return temp;
	}

	/**
	  * 将输入的句子进行词性还原
	  * @param  str1      输入的字符串
	  * @param  pipeline  斯坦福大学单词还原模板 
	  * @return 还原后的数组
	  */
	public String[] Sentence_huanyuan(String str, StanfordCoreNLP pipeline) {
		// create an empty Annotation just with the given text
		int i = 0;
		String[] temp = new String[str.trim().split(" ").length];
		Annotation document = new Annotation(str);
		// run all Annotators on this text
		pipeline.annotate(document);
		// these are all the sentences in this document
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String lemma = token.get(LemmaAnnotation.class);
				System.out.print(lemma + " ");
				temp[i++] = lemma;
			}
		}
		System.out.println();
		return temp;
	}

	 /**
	  * 截取一个字符串的首字符
	  * @param s 字符串
	  * @return 首字母
	  */
	public String intercept(String s) {
//		int a = s.indexOf(",");
//		int b = s.lastIndexOf(",");
//		String ab = s.substring(a + 1, b);
		String ab=s;
		String[] sss = ab.split(" ");
		return sss[0];
	}
	/**
	  * 根据疑问词进行查询语句并且保存
	  * @param yourClass 疑问词
	  * @return 一个map集合
	  */
	public Map<String, String> saveInstanceAndId(String yourClass) {
		System.out.println("新方法！！！！");
		Map<String, String> resultsSentenceAndId = new HashMap<String, String>();
		OntologyManage ontologyManage = new OntologyManageImpl();
		resultsSentenceAndId = ontologyManage.QuerySentenceAndId(yourClass);
		// 打印结果
		if (resultsSentenceAndId.isEmpty()) {
			System.out.println("无键值对");
		} else {
			for (Entry<String, String> s : resultsSentenceAndId.entrySet()) {
			System.out.println("ID以及对应的句子是:" + s);
			}
		}
		return resultsSentenceAndId;
	}
	/**
     * 根据值来查找键。及句子的id
     * @param map 容器
     * @param o
     */
	public Object keyString(Map map, Object o) {
		{
	          Iterator<Object> it= map.keySet().iterator();
	          while(it.hasNext())
	          {
	              Object keyString=it.next();
	              if(map.get(keyString).equals(o))
	                   return keyString;
	           }
	           return null;
	    }
	}
	/**
	  * 根据id查询句子的所有属性
	  * @param key 键值
	  * @return 该id下所有的属性
	  */
	public ISentence findPropertyById(String key) {
		ISentence sentence = null;
		//List<String>list=new ArrayList();
		
		//QuerySentenceDependOnId querySentenceDependOnId=new QuerySentenceDependOnIdImpl();
		OntologyManage om=new OntologyManageImpl();
		ResultSet resultsSentenceProperty=om.QuerySentenceIndividualDependOnId(key);
		
	  //  ResultSet resultsSentenceProperty = querySentenceDependOnId.checkSentencePropertyDependOnId(key);
	    while (resultsSentenceProperty.hasNext()) {
			// QuerySolution next()
			// Moves onto the next result.
			// 移动到下个result上
			QuerySolution solutionInstance = resultsSentenceProperty.next();
		   // System.out.println("====================实例==========================");	
			String stringInstance = solutionInstance.get("?instanceLabel").toString();
			String tempStr = stringInstance.replaceAll("_", " ");
			tempStr = tempStr.substring(0, tempStr.length() - 4);
			System.out.println("实例："+tempStr);
			//System.out.println("====================实例==========================");		
			
			//System.out.println("====================回答==========================");	
			String stringAnswer = solutionInstance.get("?propertyAnswer").toString();
		    String tempAnswer=stringAnswer.substring(stringAnswer.lastIndexOf(")")+1, stringAnswer.length() - 4);
			System.out.println("回答："+tempAnswer);
			//System.out.println("====================回答==========================");	
			
		//	System.out.println("====================教材版本==========================");	
			String stringVersion = solutionInstance.get("?propertyVersion").toString();
			String tempVersion=stringVersion.substring(stringVersion.lastIndexOf(")")+1, stringVersion.length() - 3);
			System.out.println("教材版本："+tempVersion);
			//System.out.println("====================教材版本==========================");
			
			//System.out.println("====================册数==========================");	
			String stringBook = solutionInstance.get("?propertyBook").toString();
			String tempBook=stringBook.substring(stringBook.lastIndexOf(")")+1, stringBook.length() - 3);
			System.out.println("册数："+tempBook);
			//System.out.println("====================册数==========================");	
			
		//	System.out.println("====================情境对话==========================");	
			String stringScene = solutionInstance.get("?propertyScene").toString();
			String tempScene=stringScene.substring(stringScene.lastIndexOf(")")+1, stringScene.length() - 3);
			System.out.println("情境对话："+tempScene);
		//	System.out.println("====================情境对话=========================");	
			
		//	System.out.println("====================重要句型=========================");	
			String stringPattern = solutionInstance.get("?propertySentencePattern").toString();
			String tempPattern=stringPattern.substring(stringPattern.lastIndexOf(")")+1, stringPattern.length() - 3);
			System.out.println("重要句型："+tempPattern);
		//	System.out.println("====================重要句型=========================");	
			
		//	System.out.println("====================相关单词=========================");	
			String stringRelatedWords = solutionInstance.get("?propertyRelatedWords").toString();
			String tempRelatedWords=stringRelatedWords.substring(stringRelatedWords.lastIndexOf(")")+1, stringRelatedWords.length() - 3);
			System.out.println("相关单词："+tempRelatedWords);
		//	System.out.println("====================相关单词=========================");	
			
			
			sentence = new ISentence(tempStr, tempAnswer, tempVersion, tempBook, tempScene, tempPattern,tempRelatedWords);
		  /*System.out.println("================第2次打印=============");
			System.out.println("ID：" + key + "\n" + "    ————实例："
					+ solutionInstance.get("?instanceLabel") + "\n"
					+ "    ————回答：" + solutionInstance.get("?propertyAnswer")
					+ "\n" + "    ————教材版本："
					+ solutionInstance.get("?propertyVersion") + "\n"
					+ "    ————册数："
					+ solutionInstance.get("?propertyBook") + "\n"
					+ "    ————情境对话：" + solutionInstance.get("?propertyScene")
					+ "\n" + "    ————重要句型："
					+ solutionInstance.get("?propertySentencePattern"));*/
		}
		return sentence;
	}
	 /**
	 * 用户输入一个单词或词组的时候，执行这个方法，通过对本题库的查询，查询到该单词在本体库中所有的属性。
	 * @param  用户输入一个单词或词组
	 * @return 该单词所对应的所有的属性
	 */
	@Override
	public Iword findWordProperty(String str) {
		Iword word  = null;
		QueryWithManyWays queryIndividualAndProperty = new QueryWithManyWaysImpl();
	    //QueryIndividualAndProperty queryIndividualAndProperty=new QueryIndividualAndPropertyImpl();
		ResultSet resultsWordProperty=queryIndividualAndProperty.checkProperty(str);
		 while (resultsWordProperty.hasNext()){ 
			QuerySolution solutionInstance = resultsWordProperty.next();
			//-----------------------------------how to use-------------------------
			//课文原句 
			String propertyText = solutionInstance.get("?propertyText").toString();
			String temppropertyText =subStringManage(propertyText);
			//String temppropertyText = propertyText.substring(0, propertyText.length() - 3);
			System.out.println("课文原句"+temppropertyText);
			//情景段落
			String propertyScene = solutionInstance.get("?propertyScene").toString();
			String temppropertyScene =subStringManage(propertyScene); 
			System.out.println("情景段落"+temppropertyScene);
			//延伸例句 
			String propertyExtend = solutionInstance.get("?propertyExtend").toString();
			String temppropertyExtend =subStringManage(propertyExtend); 
			System.out.println("延伸例句 "+temppropertyExtend);
			//-----------------------------------how to use-------------------------
			
			//-----------------------------------what else-------------------------
			//联想
			String propertyAssociate = solutionInstance.get("?propertyAssociate").toString();
			String temppropertyAssociate =subStringManage(propertyAssociate);  
			System.out.println("联想 "+temppropertyAssociate);
			//同义词 
			String propertySynonyms = solutionInstance.get("?propertySynonyms").toString();
			String temppropertySynonyms =subStringManage(propertySynonyms);  
			System.out.println("同义词 "+temppropertySynonyms);
			//反义词 
			String propertyAntonym = solutionInstance.get("?propertyAntonym").toString();
			String temppropertyAntonym = subStringManage(propertyAntonym); 
			System.out.println("反义词 "+temppropertyAntonym);
		    //拓展  
			String propertyExpand = solutionInstance.get("?propertyExpand").toString();
			String temppropertyExpand =subStringManage(propertyExpand);  
			System.out.println("拓展 "+temppropertyExpand);
			//常用
			String propertyCommonUse = solutionInstance.get("?propertyCommonUse").toString();
			String temppropertyCommonUse=subStringManage(propertyCommonUse);  
			System.out.println("常用 "+temppropertyCommonUse);
			//-----------------------------------what else-------------------------
			
			//-----------------------------------do you know?-------------------------
			//百科 
			String propertyNcyclopedia = solutionInstance.get("?propertyNcyclopedia").toString();
			String temppropertyNcyclopedia=subStringManage(propertyNcyclopedia);  
			System.out.println("百科 "+temppropertyNcyclopedia);
			//用法 
			String propertyUse = solutionInstance.get("?propertyUse").toString();
			String temppropertyUse=subStringManage(propertyUse); 
			System.out.println("用法 "+temppropertyUse);
			//-----------------------------------do you know?-------------------------
			word = new Iword(null,temppropertyText, temppropertyScene, temppropertyExtend, temppropertyAssociate,
			temppropertySynonyms,temppropertyAntonym,temppropertyExpand,temppropertyCommonUse,temppropertyNcyclopedia,temppropertyUse);
		    }
		    return word;
	}
	  // 处理字符串
	private static String subStringManage(String string) {
		String newString = string.substring(string.indexOf(")") + 1,
				string.lastIndexOf("@"));
		return newString;
	}
	/**
	  * 该方法用于通过传入一个问句，提取问句的疑问词，在本题库中进行查询，将查询结果保存在Map集合，然后将问句与集合中的问句进行对比计算相似度。
	  * 首先将问句与所要对比的问句进行单词还原，然后采用问句模板，寻找有用的词性，然后将问句和所要对比的句子中有用的词合并到一个容器，通过相似度
	  * 算法计算出每对单词的相似度，最后通过算法算出整个句子的相似度，返回相似度最高句子的所有属性。
	  * @param   用户输入的字符串
	  * @return  返回该字符串所对应的所有属性
	  * @author  王文辉
	  */
	@Override
	public ISentence maxSimilar(String str,HttpServletRequest request){
		double similarityDegree=0;
		long time1 = System.currentTimeMillis();
    	System.out.println("================================CheckSimAction开始时间：" + time1);
		String maxCompareString=null;
		String tempAnswer=null;
        double max=0;
      	Map<String,String> map = new HashMap(); 
      	//List<String>list=new ArrayList();
		/*int a=str.indexOf(",");
		int b=str.lastIndexOf(",");
		String string1=str.substring(a+1, b);
		System.out.println("kkkkkkkkkkkkkkkkkk"+string1);*/
      	String string1=str;
	 	String yourClass=intercept(str);
	    long time2 = System.currentTimeMillis();
    	System.out.println("==============================提取疑问词 ：" + yourClass + "," + (time2 - time1));
		//输出所有的句子
	    //--------此处浪费1秒
	    map=saveInstanceAndId(yourClass);  
	    long time3 = System.currentTimeMillis();
    	System.out.println("====================================从数据库提取相关问句：" + (time3 - time2));
	    System.out.println(map);
		/* string 1与Map中的每一句话进行相似度计算，得到相似度值最大的句子以及ID*/
		long time7 = System.currentTimeMillis();
	    System.out.println("================================单词还原：" + (time7 - time3));
		long time4 = System.currentTimeMillis();
	    System.out.println("===================================pipeline时间：" + (time4 - time7));
		String[] str1 = Sentence_huanyuan(string1, pipeline);// str1为句1的所有单词还原后  
		String[] word_prop1 = Speech_tagging(string1, tagger);
		long time5 = System.currentTimeMillis();
	    System.out.println("======================================还原用户问句：" + (time5 - time4));
		for (String string2 : map.values()) {
			String[] str2 = Sentence_huanyuan(string2, pipeline);// str2为句2的所有单词还原后
			// 进行词性标注
//			System.out.println(this.getClass().getClassLoader().getResource("/").getPath());
			String[] word_prop2 = Speech_tagging(string2, tagger);
			// 构成语义向量和联合语义向量,，现在str3[]，包含了2句话的所有互异单词；
	
			ArrayList str3 = Word_Combine(str1, str2);
			for (int i = 0; i < str3.size(); i++)
				System.out.print(str3.get(i).toString()+" ");
			ArrayList word_prop3 = Word_property_Combine(word_prop1,
					word_prop2, str1, str2);

			// 根据概念词相似度，给向量中的每个单词赋与概念词相似度
			
			double[] t1 = word_similarity(word_prop3, str3, word_prop1, str1);
			double[] t2 = word_similarity(word_prop3, str3, word_prop2, str2);
			// 假设
			for (double ss : t1) {
				System.out.print(ss + " ");
			}
			for (double ss : t2) {
				System.out.print(ss + " ");
			}
		    similarityDegree= Sentence_similarity(t1, t2);
			System.out.println("最大的相似度值为"+similarityDegree);
			if(similarityDegree>max)
			{
				max=similarityDegree;
				maxCompareString=string2;
			}
		   }
		HttpSession session = request.getSession();
		session.setAttribute("similarityDegree", similarityDegree);
		long time6 = System.currentTimeMillis();
	 // System.out.println("========================================for循环：句子匹配：" + (time6 - time5)); 
        String key= (String)keyString(map,maxCompareString);
	    System.out.println("查询到相似度最高的句子的ID:"+key);
	    
	    ISentence sentence=findPropertyById(key);
	    //System.out.println(resultsSentenceProperty);		
	    return sentence;
	}

}