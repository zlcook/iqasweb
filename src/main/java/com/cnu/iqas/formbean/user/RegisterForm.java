package com.cnu.iqas.formbean.user;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.cnu.iqas.formbean.BaseForm;

/**
* @author 周亮 
* @version 创建时间：2016年10月18日 下午3:14:19
* 类说明:用户注册表单
*/
public class RegisterForm {
	
	@NotEmpty(message="年级不能为空")
	@Pattern(regexp="\\S{3,15}",message="用户名长度为3~15")
	private String userName;
	@NotEmpty
	@Pattern(regexp="\\S{3,15}",message="密码长度为3~15")
	private String password;
	//姓名
	@NotEmpty
	@Pattern (regexp="\\S{1,15}",message="真实姓名长度为1~15")
	private String realName;
	//性别
	@Min(value=0,message="性别0或1")
	@Max(value=1,message="性别0或1")
	private int sex;
	//班级
	@NotEmpty(message="班级不能为空")
	private String classNum;
	//年级
	@Min(value=1,message="年级范围选1-6")
	@Max(value=6,message="年级范围选1-6")
	private int grade;
	//出生年份
	@DateTimeFormat(pattern = "yyyy") 
	private Date birthYear;
	//学校
	@NotEmpty(message="学校不能为空")
	private String school;
	
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
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Date getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Date birthYear) {
		this.birthYear = birthYear;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	
}
