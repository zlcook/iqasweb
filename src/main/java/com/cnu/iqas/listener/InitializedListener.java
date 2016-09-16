package com.cnu.iqas.listener;

import java.net.URISyntaxException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cnu.iqas.test.LogTest;
import com.cnu.iqas.utils.PropertyUtils;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.sussex.nlp.jws.JWS;

/**
 * 初始化wordnet涉及的JWS、MaxentTagger、StanfordCoreNLP类，
 * 该类在工程初始化前调用。
 * @author dell
 *
 */

public class InitializedListener implements ServletContextListener  {
	
	private final static Logger log = LogManager.getLogger(InitializedListener.class);
	private static JWS jws;
	private static MaxentTagger tagger;
	private static StanfordCoreNLP pipeline;
	/**
	 * 方法说明：设置了一个监听的类，将wordnet的启动、模板的加载、词性还原工具放在这个方法中。在程序启动加载这个类，节省了大量的运行时间。
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("--初始化wordnet相关类");
		log.info("加载模板！！！");
		String dir = PropertyUtils.get(PropertyUtils.JWSDIR);
		String version = PropertyUtils.get(PropertyUtils.JWSVERSION);
		jws = new JWS(dir, version);
		log.info("JWs 初始化成功");
		String path="";
		try {
			path = InitializedListener.class.getClassLoader().getResource("english-left3words-distsim.tagger").toURI().getPath();
			path =path.substring(1);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		log.info("------path:"+path);
		tagger = new MaxentTagger(path);//单词属性***

		log.info("MaxentTagger 初始化成功");
		Properties props = new Properties();//单词还原****
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		
		pipeline = new StanfordCoreNLP(props);   //--------------费时	

		log.info("StanfordCoreNLP 初始化成功");
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	public static JWS getJws() {
		return jws;
	}
	public static MaxentTagger getTagger() {
		return tagger;
	}
	public static StanfordCoreNLP getPipeline() {
		return pipeline;
	}
	

}