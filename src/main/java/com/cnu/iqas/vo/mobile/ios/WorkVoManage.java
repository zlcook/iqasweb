package com.cnu.iqas.vo.mobile.ios;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cnu.iqas.utils.WebUtils;

/**
* @author 周亮 
* @version 创建时间：2016年3月9日 下午4:58:55
* 类说明：ios端上传作品交互类
*/
public class WorkVoManage {
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 作品所属单词
	 */
	private String word;
	/**
	 * 上传的作品
	 */
	private CommonsMultipartFile  file;
	/**
	 * 上传作品类型
	 */
	private Integer type;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 确认基础信息是否完善
	 * @return 完善返回true
	 */
	public boolean validateBaseInfo(){
		//确认基础信息是否完善
		if(isNull(userName)||isNull(password)||isNull(word)||type==null||file==null||file.getSize()<=0)
			return false;
		return true;
	}
	
	 /* 校验字符串是否为空或者空字符串
	 * @param str
	 * @return 空或者空字符串返回true
	 */
	private  boolean isNull(String str){
		if( str!=null && !str.trim().equals(""))
			return false;
		else
			return true;
	}
}
