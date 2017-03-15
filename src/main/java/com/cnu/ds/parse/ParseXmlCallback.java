package com.cnu.ds.parse;

import java.util.List;

import org.dom4j.Document;

/**
* @author 周亮 
* @version 创建时间：2016年10月25日 下午2:43:20
* 类说明
*  Gets called by {@code XmlParseService.execute} 
*/
public interface ParseXmlCallback<T> {
	
	List<T> doParse(Document document)throws Exception;

}
