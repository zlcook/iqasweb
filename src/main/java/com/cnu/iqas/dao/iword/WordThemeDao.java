package com.cnu.iqas.dao.iword;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.bean.iword.WordThemeTypeEnum;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2015年12月7日 下午6:40:20
* 类说明
*/
public interface WordThemeDao extends DAO<WordTheme> {

	/**
	 * 根据主题id分页查找该主题下单词
	 * @param themeid 主题id
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult  每页最大显示数
	 * @return
	 */
	public QueryResult<Iword> getWords(final String themeid,final int firstindex,final  int maxresult);
	
	/**
	 * 根据主题名称获取主题的在本体库中的查询名称
	 * @param number 主题编号：2-17-58
	 * @return "17.旅游与交通-（58）交通运输方式"
	 */
	public String findByNumber(String number);
	
}
