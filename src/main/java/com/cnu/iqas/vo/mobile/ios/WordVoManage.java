package com.cnu.iqas.vo.mobile.ios;

import java.util.ArrayList;
import java.util.List;

import com.cnu.iqas.bean.iword.WordAttributeResource;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.ResourceConstant;
import com.cnu.iqas.enumtype.WordAttributeEnum;
import com.cnu.iqas.service.iword.WordAttributeResourceService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.mysql.fabric.xmlrpc.base.Array;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

/**
* @author 周亮 
* @version 创建时间：2016年3月6日 上午11:30:12
* 类说明:ios端查询的单词。
*/
public class WordVoManage {
	/**
	 * 单词id
	 */
	String wordId;
	/**
	 * 单词
	 */
	String word;
	/**
	 * 存放多个版本的词义
	 */
	String meanings;
	/**
	 * 2:单词本身的图片（图片）
	 */
	String pictures;
	/**
	 * 3:与单词有关的句子
	 */
	String sentences;
	/**
	 * 4:与单词有关的课文段落（图片）
	 */
	String dialogues;
	/**
	 * 5:与单词有关的动画（视频）
	 */
	String videos;
	/**
	 * 6:与单词有关的绘本（图片）
	 */
	String picturebooks;
	
	
	public WordVoManage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WordVoManage(String wordId, String word, String meanings, String pictures, String sentences,
			String dialogues, String videos, String picturebooks) {
		super();
		this.wordId = wordId;
		this.word = word;
		this.meanings = meanings;
		this.pictures = pictures;
		this.sentences = sentences;
		this.dialogues = dialogues;
		this.videos = videos;
		this.picturebooks = picturebooks;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	public String getMeanings() {
		return meanings;
	}
	public void setMeanings(String meanings) {
		this.meanings = meanings;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public String getSentences() {
		return sentences;
	}
	public void setSentences(String sentences) {
		this.sentences = sentences;
	}
	public String getDialogues() {
		return dialogues;
	}
	public void setDialogues(String dialogues) {
		this.dialogues = dialogues;
	}
	public String getVideos() {
		return videos;
	}
	public void setVideos(String videos) {
		this.videos = videos;
	}
	public String getPicturebooks() {
		return picturebooks;
	}
	public void setPicturebooks(String picturebooks) {
		this.picturebooks = picturebooks;
	}
	
	
	/**
	 * 根据相同单词不同版本的单词集合生成一个WordVoManage类
	 * @param listPes 单词相同单身版本不同的单词属性集合
	 * @param wordResourceService
	 * @param wordAttributeResourceService
	 * @param splitChar 一词多版本时用于分隔不同单词间内容的分隔符
	 * @return
	 */
	public static WordVoManage generateWordVoManage(List<PropertyEntity> listPes,WordResourceService wordResourceService,WordAttributeResourceService wordAttributeResourceService,String splitChar){
		
		//返回给ios的单词
		WordVoManage wv = null;
		//单词id
		StringBuffer wordId= new StringBuffer();
		//存放多个版本的词义
		StringBuffer meanings= new StringBuffer();
		//按钮2:单词本身的图片（图片）
		StringBuffer pictures= new StringBuffer();
		//按钮3:与单词有关的句子
		StringBuffer sentences= new StringBuffer();
		//按钮4:与单词有关的课文段落（图片）
		StringBuffer dialogues= new StringBuffer();
		//按钮5:与单词有关的动画（视频）
		StringBuffer videos= new StringBuffer();
		//按钮6:与单词有关的绘本（图片）
		StringBuffer picturebooks= new StringBuffer();
		if( listPes !=null)
		for( PropertyEntity pe : listPes){
			if( pe !=null){
				wordId.append(pe.getPropertyID()+splitChar);
				//1.词义
				//中文意思
				String mean = pe.getPropertySynonyms();

				meanings.append(mean).append(splitChar);
				//2.与单词有关的句子
				//课文原句
				String sentence = pe.getPropertyText();
				//情景段落
				String scene = pe.getPropertyScene();
				//延伸例句
				String extend = pe.getPropertyExtend();
				
				sentences.append(sentence)
				        .append(splitChar).append(scene).append(splitChar)
						.append(extend).append(splitChar);
				
				//3.单词本身的图片（图片）
				//List<WordResource> wResouces= wordResourceService.findByWordId(pe.getPropertyID(), ResourceConstant.TYPE_IMAGE);
				List<WordResource> wResouces= wordResourceService.findResourceByName(pe.getInstanceLabel(), ResourceConstant.TYPE_IMAGE);
				
				for( WordResource wr : wResouces)
					pictures.append(wr.getSavepath()+splitChar);
				
				//4.单词有关的课文段落（图片）
				//List<WordAttributeResource> wAtResouces =wordAttributeResourceService.find(pe.getPropertyID(), WordAttributeEnum.PROPERTYSCENE, ResourceConstant.TYPE_IMAGE);
				
				//4.单词有关的绘本
				List<WordResource> boResouces= wordResourceService.findResourceByName(pe.getInstanceLabel(), ResourceConstant.TYPE_PICTUREBOOK);
				
				for( WordResource wr : boResouces)
					dialogues.append(wr.getSavepath()+splitChar);
				
				//5:与单词有关的动画（视频）
				   //5.1自身的相关视频
				List<WordResource> ownVideos= wordResourceService.findResourceByName(pe.getInstanceLabel(), ResourceConstant.TYPE_VIDEO);
				   //5.2属性相关的视频
				//List<WordAttributeResource> attrVideos =wordAttributeResourceService.findByWordId(pe.getPropertyID(), ResourceConstant.TYPE_VIDEO);
				
				for( WordResource wr : ownVideos)
					videos.append(wr.getSavepath()+splitChar);
				
				/*for( WordAttributeResource wr : attrVideos)
					videos.append(wr.getSavepath()+splitChar);*/
				
				//6:与单词有关的绘本（图片）
					//6.1自身的相关绘本
				List<WordResource> ownBooks= wordResourceService.findByWordId(pe.getPropertyID(), ResourceConstant.TYPE_PICTUREBOOK);
				   //6.2属性相关的绘本
				List<WordAttributeResource> attrBooks =wordAttributeResourceService.findByWordId(pe.getPropertyID(), ResourceConstant.TYPE_PICTUREBOOK);
				
				for( WordResource wr : ownBooks)
					picturebooks.append(wr.getSavepath()+splitChar);
				
				for( WordAttributeResource wr : attrBooks)
					picturebooks.append(wr.getSavepath()+splitChar);
			}
		}
		//去掉最后的逗号
		String wordIdstr= wordId.length()>0?wordId.toString().substring(0, wordId.length()-1):null;
		String wordstr=null;
		if( listPes !=null&& listPes.size()>0)
		wordstr = listPes.get(0).getInstanceLabel();
		String meaning= meanings.length()>0?meanings.toString().substring(0, meanings.length()-1):null;
		String picture= pictures.length()>0?pictures.toString().substring(0, pictures.length()-1):null;
		String sentence= sentences.length()>0?sentences.toString().substring(0, sentences.length()-1):null;
		String dialogue= dialogues.length()>0?dialogues.toString().substring(0, dialogues.length()-1):null;
		String video= videos.length()>0?videos.toString().substring(0, videos.length()-1):null;
		String picturebook=picturebooks.length()>0? picturebooks.toString().substring(0, picturebooks.length()-1):null;
		wv = new WordVoManage(wordIdstr, wordstr,meaning, picture, sentence, dialogue, video, picturebook);
		return wv;
	}
}
