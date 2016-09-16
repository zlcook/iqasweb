package com.cnu.iqas.service.iword;

import java.util.List;

import com.cnu.iqas.bean.iword.WordResource;

/**
* @author 周亮 
* @version 创建时间：2016年1月28日 下午5:59:59
* 类说明：获取资源接口
*/
public interface IfetchResource<T> {

	/**
	 * 根据单词id和资源类型查看单词资源
	 * @param wordId
	 * @param type,资源类型ResourceConstant中的值
	 * @return
	 */
	public List<T> findByWordId(String wordId,int type);
	
	/**
	 * 根据内容和类型查询资源
	 * @param content 查询 内容
	 * @param type 资源类型 1:图片，2：绘本，3：音频 4：视频
	 * @return
	 */
	public List<T> findResourceByName(String content,int type);
	
}
