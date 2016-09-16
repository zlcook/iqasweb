package com.cnu.iqas.formbean.iword;

import com.cnu.iqas.formbean.BaseForm;

/**
* @author 周亮 
* @version 创建时间：2015年12月8日 上午11:11:36
* 类说明 单词主题表单类，负责接受页面数据
*/
public class WordThemeForm extends BaseForm {
	/**
	 * 主题id
	 */
	private String id;
	/**
	 * 主题名称
	 */
	private String content;
	/**
	 *主题序号
	 */
	private String number;
	/**
	 * 英文意思
	 */
	private String english;
	/**
	 * 父类id
	 */
	private String parentId;
	/**
	 * 主题图片保存路径
	 */
	private String picturePath;
	/**
	 * 查询状态
	 * 0:不要求
	 * 1:已开启
	 * 2:已屏蔽
	 */
	private int qvisible=0;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 校验添加的主题名称是否符合规范，不为空长度在1-20
	 * @return 正确返回true,错误返回false并在errors集合中存放了key值为name的错误原因。
	 */
	public boolean validateThemeContent(){
		String nameReg = "\\S{1,20}";
		if( content!=null && content.matches(nameReg)){
			return true;
		}
		getErrors().put("content", "主题长度为1-20位");
		return false;
	}
	
	/**
	 * 主题的id和名称基本格式是否符合要求，符合返回true,否则返回false;
	 * @return
	 */
	public boolean validateIdAndContent(){
		if( BaseForm.validate(id) &&validateThemeContent())
			return true;
		return false;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public int getQvisible() {
		return qvisible;
	}
	public void setQvisible(int qvisible) {
		this.qvisible = qvisible;
	}
	
}
