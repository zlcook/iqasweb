package com.cnu.iqas.dao.iword;

import java.util.List;

import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.VersionWordCount;
import com.cnu.iqas.dao.base.DAO;

/**
* @author 周亮 
* @version 创建时间：2015年11月22日 下午6:40:43
* 类说明
*/
public interface IwordDao extends DAO<Iword>{
	
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
}
