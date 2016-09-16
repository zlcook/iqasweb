package com.cnu.iqas.bean.Recommend;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author  王文辉
 * @version 创建时间：2016年5月25日  自适应信息表
 */
@Entity
@Table(name = "t_adaptive")
public class Adaptive {
	 /**
	  * Id，答案标识
	  */
	 private int Id;
     /**
	  * userID 用户Id
	  */
	 private String userId;
	 /**
	   * answerId 答案标识
	   */
	 private String answerId;
	 /**
	   *  呈现形式
	   */    
	private String Presentedform;
	@Id
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getPresentedform() {
		return Presentedform;
	}
	public void setPresentedform(String presentedform) {
		Presentedform = presentedform;
	}
	 
	 
}
