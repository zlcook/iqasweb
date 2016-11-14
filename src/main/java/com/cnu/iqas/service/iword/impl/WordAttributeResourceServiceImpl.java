package com.cnu.iqas.service.iword.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.WordAttributeResource;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.ResourceConstant;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.iword.WordAttributeResourceDao;
import com.cnu.iqas.enumtype.WordAttributeEnum;
import com.cnu.iqas.exception.word.ResourceTypeNotExisting;
import com.cnu.iqas.exception.word.SaveDirNoExsitingException;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.service.iword.IfetchResource;
import com.cnu.iqas.service.iword.WordAttributeResourceService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.utils.PropertyUtils;

/**
* @author 王文辉
* @version 创建时间：2015年12月2日 上午16:21:23
* @version 修改时间：2016年1月29号
* 类说明 : 单词属性资源服务接口的实现类
*/
@Service("wordAttributeResourceService")
public class WordAttributeResourceServiceImpl implements WordAttributeResourceService {
	
	private WordAttributeResourceDao wordAttributeResourceDao;
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
			    	throw new ResourceTypeNotExisting("单词属性资源文件类型未确定!");
		   }
		   if(relativepath==null){
			   throw new SaveDirNoExsitingException(filetype+"类型单词属性资源保存的相对路径不存在!,请在savepath.properties中设置。");
		   }
		return BaseForm.saveFile(servletContext, relativepath, file);
	}

	@Override
	public void save(WordAttributeResource resourceattribute) {
		// TODO Auto-generated method stub
		wordAttributeResourceDao.save(resourceattribute);
	}

	public WordAttributeResourceDao getWordAttributeResourceDao() {
		return wordAttributeResourceDao;
	}
	@Resource
	public void setWordAttributeResourceDao(WordAttributeResourceDao wordAttributeResourceDao) {
		this.wordAttributeResourceDao = wordAttributeResourceDao;
	}

	@Override
	public List<WordAttributeResource> findByWordId(String wordId, int type) {
		// TODO Auto-generated method stub
		 if( wordId!=null && !wordId.equals("")){
	    	 if( ResourceConstant.isResouceType(type)){
	    		 return this.wordAttributeResourceDao.findByWord(wordId, type);
	    	 }else{
	    		 return null;
	    	 }
	     }
		return null;
	}

	@Override
	public WordAttributeResource find(String wherejpql, Object attribute) {
		// TODO Auto-generated method stub
		return wordAttributeResourceDao.find(wherejpql, attribute);
	}

	@Override
	public List<WordAttributeResource> getAllData(String wherejpql, Object[] queryParams) {
		// TODO Auto-generated method stub
		return wordAttributeResourceDao.getAllData(wherejpql, queryParams);
	}

	@Override
	public List<WordAttributeResource> find(String wordId, WordAttributeEnum attributeType, int resourceType) {
		
		List<Object> params = new ArrayList<>();
		params.add(wordId);
		params.add(attributeType.ordinal()+1);
		params.add(resourceType);
		return wordAttributeResourceDao.getAllData("o.wordId=? and o.attribute=? and type=?", params.toArray());
		 
	}

	@Override
	public List<WordAttributeResource> findResourceByName(String content, int type) {
		// TODO Auto-generated method stub
				 if( content!=null && !content.equals("")){
			    	 if( ResourceConstant.isResouceType(type)){
			    		 String wherejpql="o.name= ? and type=?";
			    		 List<Object> attribute = new ArrayList<Object>();
			    		 attribute.add(content);
			    		 attribute.add(type);
			    		 return this.wordAttributeResourceDao.getAllData(wherejpql, attribute.toArray());
			    	 }else{
			    		 return null;
			    	 }
			     }
				return null;
	}


}
