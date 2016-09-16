package com.cnu.iqas.service.iword;

import com.cnu.iqas.bean.iword.WordThemeWordRel;

/**
* @author 周亮 
* @version 创建时间：2015年12月7日 下午8:04:42
* 类说明
*/
public interface WordThemeWordRelService {
	/**
	 * 保存一个“单词主题关系”实体
	 * @param entity 单词主题关系实体
	 */
	public void save(WordThemeWordRel entity);
}
