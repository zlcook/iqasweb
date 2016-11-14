package com.cnu.offline.xml;
/**
* @author 周亮 
* @version 创建时间：2016年11月8日 上午9:12:04
* 类说明
* xml文件每个标签的属性
* 属性有属性名和属性值组成
*/
public class AttributeValue {

	private String name;
	private String value;
	public AttributeValue(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	
}
