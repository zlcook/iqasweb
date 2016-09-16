package com.cnu.iqas.formbean.iword;

import com.cnu.iqas.formbean.BaseForm;

/**
* @author 王文辉 
* @version 创建时间：2015年12月2日 下午3:52:42
* 类说明 单词属性资源表单类
*/
public class WordAttributeResourceForm extends BaseForm {

	/**
	 * 资源类型,有1图片、2视频、3声音、4绘本
	 */
	//资源所属单词的uuid

	private String uuid;
	private int type;
	private int attribute;
	private String figure;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	public int getAttribute() {
		return attribute;
	}
	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}
	public String getFigure() {
		return figure;
	}
	public void setFigure(String figure) {
		this.figure = figure;
	}

}
