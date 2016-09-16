package com.cnu.iqas.formbean.iword;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.cnu.iqas.formbean.BaseForm;

/**
* @author 周亮 
* @version 创建时间：2015年11月17日 下午3:23:19
* 类说明
*/
public class IwordForm extends BaseForm {
	/**
	 * 单词id
	 */
	private String id;
	//单词内容
	private String content;
	/**
	 * 版本
	 * 1：北师大版
	 * 2：北京版
	 * 3：外研社新标准
	 * 4：外研社一年级起
	 * 5：人教版
	 * 6：朗文版
	 */
	private Integer version;
	//册数
	private Integer book;

	
	//单元
	private Integer unit;
	//序号,单词在本单元的序号
	private Integer rank;
	/*@NotEmpty(message="单词id不能为空")
	@Pattern(regexp="\\S{1,15}",message="单词id长度为1~15")//通过正则表达式进行校验，匹配1~15个非空白的字符
*/	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@NotEmpty(message="单词不能为空")
	@Pattern(regexp="\\S{1,15}",message="单词长度为1~15")//通过正则表达式进行校验，匹配1~15个非空白的字符
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@NotNull(message="单词版本不能为空")
	@Min(value=1) @Max(value=6)
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@NotNull(message="单词册数不能为空")
	public Integer getBook() {
		return book;
	}
	public void setBook(Integer book) {
		this.book = book;
	}
	@NotNull(message="单词所属单元不能为空")
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	
	@NotNull(message="单词序号不能为空")
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
	public void generateid(){
		this.id=this.version+"/"+this.book+"/"+this.unit+"/"+this.rank;
	}
	/**
	 * 根据用户传过来的（版本：册数：单元：序号）信息组成查询的条件，注：四个参数是从属关系，所以如果父类没出现，子类就出现了，说明该查询条件有误
	 * @return
	 */
	public String queryversion(){
		String queryversion="";
		if(this.version!=0){
			queryversion+=this.version+"/";
			if( this.book!=null&&this.book!=0 ){
				queryversion+=this.book+"/";
				if( this.unit!=null &&this.unit!=0){
					queryversion+=this.unit+"/";
					if( this.rank!=null&&this.rank!=0)
						queryversion+=this.rank;
				}
			}
		}
		return queryversion.equals("")?null:queryversion;
	}
	/**
	 * 校验单词在excel中存放的id和内容是否符合规范
	 * @param id   单词id //正确格式 2/3/4/5 ,每个数字最多两位
	 * @param content  单词内容  ：前后不能有空格，单词组中间可以有空格,
	 * @return 匹配返回true
	 */
	public static boolean validateWord(String id,String content){
		if( null==id || null == content)
			return false;
		String idreg ="\\d{1,2}/\\d{1,2}/\\d{1,2}/\\d{1,2}";  
		//String conreg="[A-Za-z'-]+( *[A-Za-z'-]+)*"; //单词前后不能有空格，单词组中间可以有空格,
		String conreg=".+";
		if( id.matches(idreg)&& content.matches(conreg))
			return true;
		return false;
	}
}
