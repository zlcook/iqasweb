package com.cnu.iqas.service.iword.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.ResourceConstant;
import com.cnu.iqas.dao.iword.WordResourceDao;
import com.cnu.iqas.exception.word.ResourceTypeNotExisting;
import com.cnu.iqas.exception.word.SaveDirNoExsitingException;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.service.iword.IfetchResource;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.utils.PropertyUtils;

/**
* @author 周亮 
* @version 创建时间：2015年11月23日 上午11:38:23
* 类说明 : 单词资源服务接口的实现类
*/
@Service("wordResourceService")
public class WordResourceServiceImpl implements WordResourceService{

	private WordResourceDao wordResourceDao;

	@Override
	public String saveWordResourceFile(ServletContext servletContext, CommonsMultipartFile file, int filetype) throws Exception {
		//获取单词资源的文件保存的相对路径
		   String relativepath=null;
		   switch(filetype){
			    case ResourceConstant.TYPE_IMAGE: //上传图片类型文件
			    	relativepath = PropertyUtils.getFileSaveDir(PropertyUtils.ANDROID_WORD_IMAGE_DIR);  //获取本地存储路径
			    	break;
			    case ResourceConstant.TYPE_VOICE://声音
			    	relativepath = PropertyUtils.getFileSaveDir(PropertyUtils.ANDROID_WORD_VOICE_DIR);  //获取本地存储路径
			    	break;
			    case ResourceConstant.TYPE_VIDEO://视频
			    	relativepath = PropertyUtils.getFileSaveDir(PropertyUtils.ANDROID_WORD_VIDEO_DIR);  //获取本地存储路径
			    	break;
			    case ResourceConstant.TYPE_PICTUREBOOK://绘本
			    	relativepath = PropertyUtils.getFileSaveDir(PropertyUtils.WORD_PICTUREBOOK_DIR);  //获取本地存储路径
			    	break;
			    default:
			    	throw new ResourceTypeNotExisting("单词资源文件类型未确定!");
		   }
		   if(relativepath==null){
			   throw new SaveDirNoExsitingException(filetype+"类型单词资源保存的相对路径不存在!,请在savepath.properties中设置。");
		   }
		return BaseForm.saveFile(servletContext, relativepath, file);
	}

	@Override
	public WordResource find(String id) {
		// TODO Auto-generated method stub
		
		return wordResourceDao.find(id);
	}

	@Override
	public void update(WordResource wr) {
		// TODO Auto-generated method stub
		wordResourceDao.update(wr);
	}
	@Override
	public void save(WordResource resource) {
		// TODO Auto-generated method stub
		wordResourceDao.save(resource);
	}
	@Override
	public List<WordResource> getAllData(String string, Object[] array) {
		// TODO Auto-generated method stub
		return wordResourceDao.getAllData(string, array);
	}

	public WordResourceDao getWordResourceDao() {
		return wordResourceDao;
	}
	@Resource
	public void setWordResourceDao(WordResourceDao wordResourceDao) {
		this.wordResourceDao = wordResourceDao;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.wordResourceDao.delete(id);
	}

	public List<WordResource> findByWordId(String wordId, int type) {
		// TODO Auto-generated method stub
        if( wordId!=null && !wordId.equals("")){
	    	 if( ResourceConstant.isResouceType(type)){
	    		 return this.wordResourceDao.findByWord(wordId, type);
	    	 }else{
	    		 return null;
	    	 }
	     }
		return null;
	}

	@Override
	public WordResource find(String wherejpql, Object attribute) {
		// TODO Auto-generated method stub
		return wordResourceDao.find(wherejpql, attribute);
	}

	@Override
	public WordResource findByContent() {
		// TODO Auto-generated method stub
		return wordResourceDao.findByContent();
	}

	@Override
	public List<WordResource> findResourceByName(String content, int type) {
		// TODO Auto-generated method stub
		  if( content!=null && !content.equals("")){
		    	 if( ResourceConstant.isResouceType(type)){
		    		 String wherejpql="o.name= ? and type=?";
		    		 List<Object> attribute = new ArrayList<Object>();
		    		 attribute.add(content);
		    		 attribute.add(type);
		    		 return this.wordResourceDao.getAllData(wherejpql, attribute.toArray());
		    	 }else{
		    		 return null;
		    	 }
		     }
			return null;
	}
	
	
}
