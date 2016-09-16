package com.cnu.iqas.service.Recommend;
import java.util.List;

import com.cnu.iqas.bean.Recommend.QuestionAnswer;
/**
* @author  王文辉
* @version 创建时间：2016年4月20日 下午10:41:18
* 类说明
**/

public interface QuestionAnswerService {
	
	/**
	 * 根据条件查询所有数据
	 * @param wherejpql 查询条件  "o.email=? and o.account=?"
	 * @param queryParams 查询条件占位符对应的参数值，
	 */
	public List<QuestionAnswer> getAllData(String wherejpql, Object[] queryParams);
	/**
	 * 根据属性来获取实体
	 * @param <T>
	 * @param wherejpql 查询条件  "o.content=? "
	 * @param attribute 实体的属性值
	 * @return
	 */
	public QuestionAnswer find(String wherejpql, Object attribute);
	public void save(QuestionAnswer questionAnswer);

}
