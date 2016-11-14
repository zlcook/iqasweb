package com.cnu.offline.service;

import java.util.List;

import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.service.common.IFileDownloadService;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.bean.OffLineWordXml;
import com.cnu.offline.exception.ThemeWordNotExistException;

/**
* @author 周亮 
* @version 创建时间：2016年7月8日 上午10:31:31
* 类说明
* 过时，请使用OffLineBagService
*/
@Deprecated
public interface OfflineService extends IFileDownloadService{
	/**
	 * 过时
	 * @param theme
	 * @return
	 */
	@Deprecated
	public OffLineWordXml createByThme(WordTheme theme);
	
	@Deprecated
	public void save(OffLineWordXml olx);
	/**
	 * 查询该主题的离线文件
	 * @param themeId
	 * @return
	 */
	@Deprecated
	public List<OffLineWordXml> findByThemeId(String themeId);
	
	/**
	 * 查询最新版的离线文件
	 * @param id
	 * @return
	 */
	@Deprecated
	public OffLineWordXml findNewByThemeId(String id);
	@Deprecated
	public void update(OffLineWordXml owx);
	
}
