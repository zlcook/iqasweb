package com.cnu.offline.service;

import java.util.List;

import org.dom4j.Document;

import com.cnu.offline.xml.WordNode;

/**
* @author 周亮 
* @version 创建时间：2016年11月9日 下午7:58:07
* 类说明
* 创建Document文件接口，
* ios、和android端的适配器实现该接口，就可以根据自己的需求实现xml的具体生成格式。
* 
*/
public interface ICreateDocument<E> {

	/**
	 * 将listWes内容填充到document中
	 * @param document xml文档
	 * @param listWordNodes  填充到xml文档中的内容
	 * @param themenumber 主题编号
	 * @param realGrade		 实际年级
	 * @param recommendGrade 推荐年级
	 */
	public void fillDocument(Document document, List<E> listWordNodes,String themenumber,int realGrade,int recommendGrade);
}
