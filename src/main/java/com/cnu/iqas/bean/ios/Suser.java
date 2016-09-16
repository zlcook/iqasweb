package com.cnu.iqas.bean.ios;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.cnu.iqas.utils.PropertyUtils;

/**
* @author 周亮 
* @version 创建时间：2016年3月1日 上午9:41:06
* 类说明：ios端用户
* 
*/
@Entity
@Table(name="t_suser")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class Suser {
	   /**
	    * 主键
	    */
		private String userId;     
		/**
		 * 用户名,必填
		 */
		private String userName;
		/**
		 * 密码 默认000000
		 */
		private String password="000000";
		/**
		 * 姓名,必填
		 */
		private String realName;
		/**
		 * 性别 0：男  1：女,默认0

		 */
		private Integer sex=0;
		/**
		 * 年级,必填
		 */
		private Integer grade;
		/**
		 * 班级，必填
		 */
		private Integer classNumber;
		/**
		 * 出生年份，默认当前日期
		 */
		private Date birthYear=new Date();
		/**
		 * 金币数	默认都置0	
		 */
		private Integer golden=0;
		/**
		 * 排行	默认都置0	
		 */
		private Integer rank=0;
		/**
		 * 勋章	默认都置0	
		 */
		private Integer medal=0;
		/**
		 * 登录次数	默认0
		 */
		private Integer loginTimes=0;
		/**
		 * 已学习单词个数	默认0
		 */
		private Integer wordCount=0;	
		/**
		 * 作品个数	默认0	
		 */
		private Integer workCount=0;
		/**
		 * 学校
		 */
		private String school;
		/**
		 * 身份，1：学生
		 */
		private Integer role=1;
		/**
		 * 注册时间，默认当前日期
		 */
		private Date createTime = new Date();
		/**
		 * 头像保存路径，默认系统头像
		 */
		private String picturePath=PropertyUtils.get(PropertyUtils.LOG);
		
		@Id @GeneratedValue(generator="uuidGenderator")
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		@Column(nullable=false,unique=true)
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		@Column(length=32,nullable=false)
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
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
		public Integer getClassNumber() {
			return classNumber;
		}
		public void setClassNumber(Integer classNumber) {
			this.classNumber = classNumber;
		}
		@Temporal(TemporalType.DATE)
		public Date getBirthYear() {
			return birthYear;
		}
		public void setBirthYear(Date birthYear) {
			this.birthYear = birthYear;
		}
		public Integer getGolden() {
			return golden;
		}
		public void setGolden(Integer golden) {
			this.golden = golden;
		}
		public Integer getRank() {
			return rank;
		}
		public void setRank(Integer rank) {
			this.rank = rank;
		}
		public Integer getMedal() {
			return medal;
		}
		public void setMedal(Integer medal) {
			this.medal = medal;
		}
		public Integer getLoginTimes() {
			return loginTimes;
		}
		public void setLoginTimes(Integer loginTimes) {
			this.loginTimes = loginTimes;
		}
		public Integer getWordCount() {
			return wordCount;
		}
		public void setWordCount(Integer wordCount) {
			this.wordCount = wordCount;
		}
		public Integer getWorkCount() {
			return workCount;
		}
		public void setWorkCount(Integer workCount) {
			this.workCount = workCount;
		}
		public String getSchool() {
			return school;
		}
		public void setSchool(String school) {
			this.school = school;
		}
		public Integer getRole() {
			return role;
		}
		public void setRole(Integer role) {
			this.role = role;
		}
		@Temporal(TemporalType.TIME)
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public String getPicturePath() {
			return picturePath;
		}
		public void setPicturePath(String picturePath) {
			this.picturePath = picturePath;
		}
		
}
