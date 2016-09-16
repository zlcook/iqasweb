package com.cnu.iqas.formbean.ios;
/**
* @author 周亮 
* @version 创建时间：2016年3月1日 下午2:11:11
* 类说明：ios端用户注册表单
*/
public class SUserRegisterForm {
	/**
	 * 用户名,必填
	 */
	private String userName;
	/**
	 * 密码 默认000000
	 */
	private String password;
	/**
	 * 性别 0：男  1：女,默认0
	 */
	private Integer sex;
	/**
	 * 年级,必填
	 */
	private Integer grade;
	
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	/**
	 * 校验注册信息是否有效
	 * 注册信息包括：用户名，密码，性别，年级
	 * @return :true表示有效
	 */
	public boolean validateRegisterInfo() {
		if(  !isNull(userName)&& !isNull(password)){
			if( validateSex() && validateGrade())
				return true;
		}
		return false;
	}
	/**
	 * 校验性别是否是0或者1
	 * @return ，true表示有效
	 */
	private boolean validateSex(){
		if(sex!=null && (sex==0 || sex==1))
			return true;
		else
			return false;
	}
	/**
	 * 校验年级是否是0或者1
	 * @return ，true表示有效
	 */
	private boolean validateGrade(){
		if(grade!=null && grade>=1 && grade<=6)
			return true;
		else
			return false;
	}
	/**
	 * 校验字符串是否为空
	 * @param str 字符串
	 * @return 字符串为空时返回true
	 */
	private boolean isNull(String str){
		if(userName!=null && userName.trim()!=null)
			return false;
		else
			return true;
	}
	
}
