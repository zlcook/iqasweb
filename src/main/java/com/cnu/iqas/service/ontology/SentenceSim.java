package com.cnu.iqas.service.ontology;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.ontology.ISentence;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public interface SentenceSim {
	
	 /**
	  * 截取一个字符串的首字符
	  * @param s 字符串
	  */
	 public String intercept(String s);//截取一个字符串的首字符
	 /**
	  * 根据疑问词进行查询语句并且保存
	  * @param yourClass 疑问词
	  */
	 public Map<String, String> saveInstanceAndId(String yourClass);
	 /**
	  * 将输入的句子进行词性还原
	  * @param  str1      输入的字符串
	  * @param  pipeline  斯坦福大学单词还原模板 
	  */
	 public abstract String[] Sentence_huanyuan(String str1,StanfordCoreNLP pipeline);//单词还原
	 /**
	  * 将两组互异单词合并为一个字符串
	  * @param  str1   字符串
	  * @param  str2  字符串
	  */
	 public abstract ArrayList Word_Combine(String[] str1,String str2[]); //将2组单词合并成一组（互异单词）
	 /**
	  * 将两组单词进行合并为一个数组
	  * @param word_prop1   数组1
	  * @param word_prop2   数组2
	  * @param str1  字符串1
	  * @param str2 字符串2
	  */
	 public ArrayList Word_property_Combine(String[] word_prop1,String[] word_prop2,String[] str1, String[] str2);
	 /**
	  * 将英文句子进行词性标注，并返回词性数组
	  * @param str    需要进行标注的语句
	  * @param tagger 模板
	  */
	 public abstract String[] Speech_tagging(String str,MaxentTagger tagger);//将英文句子进行词性标注，并返回词性数组；
	 /**
	  * 计算单词相似度
	  * @param word_prop3
	  * @param str3
	  * @param word_prop
	  * @param str
	  */
	 public abstract double[] word_similarity(ArrayList word_prop3, ArrayList str3, String[] word_prop,String[] str);
	/**
	 * 计算句子相似度
	 * @param s_1  单词数组
	 * @param s_2 单词数组
	 */
	 public abstract double Sentence_similarity(double s_1[],double s_2[]);//根据2组单词向量，计算2句话的相似度
    /**
     * 根据值来查找键。及句子的id
     * @param map 容器
     * @param o
     */
	 public Object keyString(Map map,Object o);
	 /**
	  * 根据id查询句子的所有属性
	  * @param key 键值
	  */
	 public ISentence findPropertyById(String key);	
	 /**
		 * 用户输入一个单词或词组的时候，执行这个方法，通过对本题库的查询，查询到该单词在本体库中所有的属性。
		 * @param  用户输入一个单词或词组
		 * @return 该单词所对应的所有的属性
	 */
	 public Iword findWordProperty(String str);
	 /**
	  * 该方法用于通过传入一个问句，提取问句的疑问词，在本题库中进行查询，将查询结果保存在Map集合，然后将问句与集合中的问句进行对比计算相似度。
	  * 首先将问句与所要对比的问句进行单词还原，然后采用问句模板，寻找有用的词性，然后将问句和所要对比的句子中有用的词合并到一个容器，通过相似度
	  * 算法计算出每对单词的相似度，最后通过算法算出整个句子的相似度，返回相似度最高句子的所有属性。
	  * @param   用户输入的字符串
	  * @return  返回该字符串所对应的所有属性
	  * @author  王文辉
	  */
	 public ISentence maxSimilar(String str,HttpServletRequest request);
	 
	 
}
