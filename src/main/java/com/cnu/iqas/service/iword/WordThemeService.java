package com.cnu.iqas.service.iword;

import java.util.LinkedHashMap;
import java.util.List;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordTheme;

/**
* @author 周亮 
* @version 创建时间：2015年12月7日 下午6:49:00
* 类说明  单词主题服务类接口
*/
public interface WordThemeService {
	/**
	 * 根据主题id分页查找该主题下单词
	 * @param themeid 主题id
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult  每页最大显示数
	 * @return
	 */
	public QueryResult<Iword> getWords(final String themeid,final int firstindex,final  int maxresult);
	/**
	 * 根据主题id关闭主题
	 * @param id 主题id
	 * @return
	 */
	public boolean disable(String id);
	/**
	 * 开启主题
	 * @param id 主题id
	 * @return
	 */
	public boolean enable(String id);

	/**
	 * 保存一个实体
	 * @param entity
	 */
	public void save(WordTheme entity);
	/**
	 * 根据主题内容查找主题
	 * @param content
	 * @return
	 */
	public WordTheme findByContent(String content);
	/**
	 * 根据主题内容编号查找主题
	 * @param number
	 * @return
	 */
	public WordTheme findByNumber(String number);
	/**
	 * 更新主题
	 * @param entity
	 */
	public void update(WordTheme entity);
	/**
	 * 根据id查找主题
	 * @param id
	 * @return
	 */
	public WordTheme find(String id);
	/**
	 * 根据条件分页查询，结果根据条件排序
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult 一页的最大记录数
	 * @param wherejpql 查询条件  "o.email=? and o.account like ?"
	 * @param queryParams  查询条件占位符对应的参数值，
	 * @param orderby 排序条件  Key为属性,Value为asc/desc
	 * @return 查询结果类
	 */
	public QueryResult<WordTheme> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby);
	
	
	
	/**
	 * 删除主题
	 * @param id 主题id
	 */
	public void delete(String id);
	/**
	 * 根据条件查询数据
	 * @param wherejpql 构造查询语句,如：“o.parentId =?"
	 * @param queryParams,查询语句对应的查询参数，如“1234”；
	 * @return
	 */
	List<WordTheme> getWordThemes(String wherejpql, Object[] queryParams);

}
