package com.cnu.iqas.service.Recommend;
import com.cnu.iqas.bean.Recommend.Question;
/**
* @author  王文辉
* @version 创建时间：2016年4月20日 下午10:41:18
* 类说明
**/

public interface QuestionService {
	
	/**
	 * 根据属性来获取实体
	 * @param <T>
	 * @param wherejpql 查询条件  "o.content=? "
	 * @param attribute 实体的属性值
	 * @return
	 */
	public Question find(String wherejpql, Object attribute);
	
	/**
	 * 保存在问题表
	 * @param questiuon
	 */
	public void save(Question questiuon);
	
}
