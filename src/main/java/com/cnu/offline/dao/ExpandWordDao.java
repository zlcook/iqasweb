package com.cnu.offline.dao;

import com.cnu.iqas.dao.base.DAO;
import com.cnu.offline.bean.ExpandWord;

/**
* @author 周亮 
* @version 创建时间：2016年10月27日 上午10:29:17
* 类说明
*/
public interface ExpandWordDao extends DAO<ExpandWord> {

	/**
	 * 如果实体不存在就保存，存在就更新
	 * @param entity
	 */
	public void saveOrUpdate(ExpandWord entity);
}
