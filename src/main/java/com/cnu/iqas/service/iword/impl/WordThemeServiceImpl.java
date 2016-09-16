package com.cnu.iqas.service.iword.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.bean.iword.WordThemeTypeEnum;
import com.cnu.iqas.dao.iword.IwordDao;
import com.cnu.iqas.dao.iword.WordThemeDao;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.service.iword.WordThemeService;

/**
* @author 周亮 
* @version 创建时间：2015年12月7日 下午6:55:14
* 类说明
*/
@Service("wordThemeService")
public class WordThemeServiceImpl implements WordThemeService {
	//单词主题数据访问类
	private WordThemeDao wordThemeDao;
	//单词数据访问类
	private IwordDao iwordDao;
	
	/**
	 * 根据主题id分页查找该主题下单词
	 * @param themeid 主题id
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult  每页最大显示数
	 * @return
	 */
	public QueryResult<Iword> getWords( String themeid, int firstindex,  int maxresult){
		// TODO Auto-generated method stub
		if( !validateId(themeid))
			return null;
		return wordThemeDao.getWords(themeid, firstindex, maxresult);
	}
	@Override
	public void save(WordTheme entity) {
		if( entity==null)
			throw new RuntimeException("主题不能为空！");
		try {
			wordThemeDao.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public WordTheme findByContent(String content) {
		String  sql = "o.content = ?";
		return wordThemeDao.find(sql, content);
	}

	
	public WordThemeDao getWordThemeDao() {
		return wordThemeDao;
	}
	@Resource
	public void setWordThemeDao(WordThemeDao wordThemeDao) {
		this.wordThemeDao = wordThemeDao;
	}

	public IwordDao getIwordDao() {
		return iwordDao;
	}
	@Resource
	public void setIwordDao(IwordDao iwordDao) {
		this.iwordDao = iwordDao;
	}

	@Override
	public QueryResult<WordTheme> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return wordThemeDao.getScrollData(firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void update(WordTheme entity) {
		wordThemeDao.update(entity);
	}

	@Override
	public List<WordTheme> getWordThemes(String wherejpql,Object[] queryParams ){
		
		return wordThemeDao.getAllData(wherejpql, queryParams);
	}
	@Override
	public WordTheme find(String id) {
		if( !validateId(id))
			return null;
		return wordThemeDao.find(id);
	}
	/**
	 * 根据主题id使主题失效
	 * @param id
	 * @return 修改成功返回true
	 */
	@Override
	public boolean disable(String id) {
		return makeVisible(id, false);
	}
	/**
	 * 根据主题id使主题有效
	 * @param id
	 * @return 修改成功返回true
	 */
	@Override
	public boolean enable(String id) {
		return makeVisible(id, true);
	}
	/**
	 * 根据主题id来操作主题的是否有效，如果visible为true,则让主题变的有效
	 * @param id	主题id0  
	 * @param visible
	 * @return
	 */
	private boolean makeVisible(String id,boolean visible){
		// TODO Auto-generated method stub
		WordTheme theme=wordThemeDao.find(id);
		if( theme==null)
			return false;
		theme.setVisible(visible);
		wordThemeDao.update(theme);
		return true;
	}
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		wordThemeDao.delete(id);
	}
	/**
	 * 判断主题id是否为空或者空字符串
	 * @param id
	 * @return 合理返回true
	 */
	private boolean validateId(String id){
		if( id==null || "".equals(id.trim()))
			return false;
		return true;
	}
	@Override
	public WordTheme findByNumber(String number) {
		String  sql = "o.number = ?";
		return wordThemeDao.find(sql, number);
	}
}
