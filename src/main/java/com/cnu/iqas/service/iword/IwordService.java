package com.cnu.iqas.service.iword;

import java.util.LinkedHashMap;
import java.util.List;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.VersionWordCount;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 下午10:41:18
* 类说明
*/
public interface IwordService {
	/**
	 * 批量插入单词
	 * @param iwords 需要一次性插入数据库的单词，建议一次性20个
	 */
	public void batchSave(List<Iword> iwords);
	/**
	 * 统计每个版本教材的单词数
	 * @return
	 */
	public List<VersionWordCount> statisticsVersionAndWordCount();
	/**
	 * 保存单词
	 * @param word
	 */
	public void save(Iword word);
	/**
	 * 根据条件分页查询，结果根据条件排序
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult 一页的最大记录数
	 * @param wherejpql 查询条件  "o.email=? and o.account=?"
	 * @param queryParams 查询条件占位符对应的参数值，
	 * @param orderby 排序条件  Key为属性,Value为asc/desc
	 */
	public QueryResult<Iword> getScrollData(int firstResult, int maxresult, String string, Object[] array,
			LinkedHashMap<String, String> orderby);
	/**
	 * 根据单词uuid查询单词
	 * @param uuid
	 * @return
	 */
	public Iword find(String uuid);
	/**
	 * 分页查询
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult 一页的最大记录数
	 * @return 查询结果类
	 */
	public QueryResult<Iword> getScrollData(int firstindex, int maxresult);
	/**
	 * 根据属性来获取实体
	 * @param <T>
	 * @param wherejpql 查询条件  "o.email=? "
	 * @param attribute 实体的属性值
	 * @return
	 */
	public Iword find(String wherejpql, Object attribute);
	/**
	 * 根据单词内容查单词
	 * @param wordStr
	 * @return
	 */
	public Iword findWord(String wordStr);
}

	
	

