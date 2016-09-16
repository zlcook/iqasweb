package com.cnu.iqas.service.Recommend;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.bean.base.QueryResult;
/**
* @author  王文辉
* @version 创建时间：2016年4月20日 下午10:41:18
* 类说明
**/
public interface AnswerService {
	
	/**
	 * 根据属性来获取实体
	 * @param <T>
	 * @param wherejpql 查询条件  "o.content=? "
	 * @param attribute 实体的属性值
	 * @return
	 */
	public Answer find(String wherejpql, Object attribute);
	public void save(Answer answer);
	/**
	 * 根据条件查询所有数据
	 * @param wherejpql 查询条件  "o.email=? and o.account=?"
	 * @param queryParams 查询条件占位符对应的参数值，
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<Answer> getAllData(String wherejpql, Object[] queryParams);
	/**
	 * 跟新实体
	 * 
	 */
	public int updateAnswer(Answer answer);
	/**
	 * 根据条件分页查询
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult 一页的最大记录数
	 * @param wherejpql 查询条件  "o.email=? and o.account like ?"
	 * @param queryParams  查询条件占位符对应的参数值，
	 * @return 查询结果类
	 */
	public QueryResult<Answer> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams);

}
