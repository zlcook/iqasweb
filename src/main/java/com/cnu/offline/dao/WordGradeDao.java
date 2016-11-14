package com.cnu.offline.dao;

import com.cnu.iqas.dao.base.DAO;
import com.cnu.offline.bean.Word;
import com.cnu.offline.bean.WordGrade;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 下午2:21:21
* 类说明
*/
public interface WordGradeDao extends DAO<WordGrade> {

	/**
	 * 更新或添加
	 * @param wordGrade
	 */
	public void saveOrUpdate(WordGrade wordGrade);
}
