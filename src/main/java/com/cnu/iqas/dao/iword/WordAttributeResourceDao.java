package com.cnu.iqas.dao.iword;

import java.util.List;

import com.cnu.iqas.bean.iword.WordAttributeResource;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2016年1月8日 下午4:29:45
* 类说明
*/
public interface WordAttributeResourceDao extends DAO<WordAttributeResource> {
	
	/**
	 * 根据单词id和资源类型查看单词资源
	 * @param wordId
	 * @param type
	 * @return
	 */
	List<WordAttributeResource> findByWord(String wordId, int type);

}
