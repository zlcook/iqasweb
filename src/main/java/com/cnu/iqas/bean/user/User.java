package com.cnu.iqas.bean.user;

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
 * @author dell
 *
 */
@Entity
@Table(name="t_user")
@GenericGenerator(name="uuidGenderator",strategy="uuid")
public class User {
	//主键
	private String userId;     
	//用户名
	private String userName;
	//密码
	private String password;
	//姓名
	private String realName;
	//性别
	private int sex;
	//年级
	private String grade;
	//班级
	private int classNum;
	//出生年份
	private Date birthYear;
	//学校
	private String school;
	//身份
	private int role;
	/**
	 * 用户在自适应学习系统中可查看的商品类型等级，默认值0'
	 * 该值也表示用户的勋章个数及隐藏关闯关个数
	 */
	private Integer storeGrade=0;

	/**
	 * 用户在当前可查看商品类型中已购买的商品种数，该属性用于判断用户是否已购买完当前可查看的所有商品
	 */
	private int spieces =0;
	/**
	 * 用户在自适应学习系统中的获得的金币总数，初始值为0
	 */
	private Integer allCoins=0;
	/**
	 * 用户当前已经开启的天数，默认第一天
	 */
	private int gameDay=1;
	/**
	 * 用户在当前游戏天数下的第几个场景，默认第一个场景
	 */
	private int gameScene=1;
	/**
	 * 用户通过率，默认1,即100%
	 */
	private Double successRate=1.0;
	/**
	 * 头像保存路径，默认系统头像
	 */
	private String picturePath=PropertyUtils.get(PropertyUtils.LOG);
	/**
	 * 用户的等级（有三种类型分别）
	 */
	private int userRank;
	/**
	 * 令牌唯一，每次登陆都会变化，用户提供的和服务器端存的不一样就要重新登录。令牌由随机数和用户账号组成。
	 */
	private String token;
	//测试一下
	@Id @GeneratedValue(generator="uuidGenderator")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 令牌唯一，每次登陆都会变化，用户提供的和服务器端存的不一样就要重新登录。令牌由随机数和用户账号组成。
	 */
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Column(length=15,nullable=false,unique=true)
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
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	@Temporal(TemporalType.DATE)
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
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public Integer getStoreGrade() {
		return storeGrade;
	}
	public void setStoreGrade(Integer storeGrade) {
		this.storeGrade = storeGrade;
	}
	public Integer getAllCoins() {
		return allCoins;
	}
	public void setAllCoins(Integer allCoins) {
		this.allCoins = allCoins;
	}
	public int getSpieces() {
		return spieces;
	}
	public void setSpieces(int spieces) {
		this.spieces = spieces;
	}
	public int getGameDay() {
		return gameDay;
	}
	public void setGameDay(int gameDay) {
		this.gameDay = gameDay;
	}
	public int getGameScene() {
		return gameScene;
	}
	public void setGameScene(int gameScene) {
		this.gameScene = gameScene;
	}
	public Double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(Double successRate) {
		this.successRate = successRate;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public int getUserRank() {
		return userRank;
	}
	public void setUserRank(int userRank) {
		this.userRank = userRank;
	}
}
