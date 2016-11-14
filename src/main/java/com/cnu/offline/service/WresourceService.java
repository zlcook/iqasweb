package com.cnu.offline.service;

import java.util.List;

import com.cnu.offline.bean.WordGrade;
import com.cnu.offline.bean.Wresource;
import com.cnu.offline.dao.WresourceDao;

/**
* @author 周亮 
* @version 创建时间：2016年11月6日 下午7:33:38
* 类说明
*/
public interface WresourceService {
	/**
	 * 查询单词的资源信息
	 * @param word
	 * @return
	 */
	public Wresource find(String word);
	/**
	 * 获取相同单词其它年级对应的课文原句和情景段落资源信息
	 * @param word 单词
	 * @return
	 * 返回所有年级中相同单词的课文原句和情景段落
	 */
	public List<WordGrade> findWordGrade(String word);
}
