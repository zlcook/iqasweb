package com.cnu.iqas.formbean.admin;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.cnu.iqas.formbean.BaseForm;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 上午10:54:42
* 类说明  管理员表单
*/
public class AdminForm  extends BaseForm{
	private String account;
	private String password;
	
	@NotEmpty(message="账号不能为空")
	@Pattern(regexp="\\w{5,20}",message="账号长度为5~20")//通过正则表达式进行校验，匹配5~15个数字和字母以及下划线的字符
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@NotEmpty(message="密码不能为空")
	@Pattern(regexp="\\S{5,20}",message="密码长度为5~20")//通过正则表达式进行校验，匹配5~15个非空白的字符
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
