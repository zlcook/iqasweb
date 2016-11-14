package com.cnu.offline.dao;

import com.cnu.iqas.dao.base.DAO;
import com.cnu.offline.bean.Word;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午10:30:09
* 类说明
*/
public interface WordDao extends DAO<Word> {

	/**
	 * 更新或添加
	 * @param word
	 */
	public void saveOrUpdate(Word word);
}
