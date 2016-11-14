package com.cnu.ds.service.impl;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import com.cnu.ds.ParseXmlCallback;

/**
* @author 周亮 
* @version 创建时间：2016年10月25日 下午4:44:53
* 类说明，解析xml文件服务接口
*/
@Service("parseXmlService")
public class ParseXmlService {
	
	public <T> List<T> parse(ParseXmlCallback<T> parseExecute,final Document document) throws Exception {
		return parseExecute.doParse(document);
	}
}
