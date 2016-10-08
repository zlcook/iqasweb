package com.cnu.offline.service;

import java.util.List;

import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.service.common.IFileDownloadService;
import com.cnu.offline.ThemeWordNotExistException;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.bean.OffLineWordXml;

/**
* @author 周亮 
* @version 创建时间：2016年7月8日 上午10:31:31
* 类说明
*/
public interface OfflineService extends IFileDownloadService{
	
	public OffLineWordXml createByThme(WordTheme theme);
	
	/**
	 * 创建主离线包
	 * @param realGrade 实际年级
	 * @param themenumber 主题编号，如：“1-1-1”
	 * @return
	 */
	public OffLineBag createMasterOfflineBag(int realGrade, String themenumber) throws ThemeWordNotExistException ;

	/**
	 * 创建从离线包，从离线包和其主离线包的参数只有recommendGrade值是不同的。
	 * @param recommendGrade 推荐年级
	 * @param realGrade 实际年级
	 * @param themenumber 主题
	 * @return
	 * 返回离线包，离线包的内容是对主离线包的补充，而且补充的内容只有推荐年级的“情景段落”、“课文原句”、“视频”
	 * 主离线包的生成参数和从离线包的生成参数只有“recommendGrade”属性值不同。
	 * 比如：
	 * 第一次下载的离线包参数为:themenumber=2-17,realGrade=4,recommendGrade=3;这次会调用createOfflineBag方法生成主离线包。
	 * 第二次下载的离线包参数为:themenumber=2-17,realGrade=4,recommendGrade=3;这次会调用该方法生成从离线包。
	 * 从离线包中对于每一个主单词只会生成对应推荐年级的“情景段落”、“课文原句”以及对应难度的“视频”
	 * @throws ThemeWordNotExistException 
	 */
	public OffLineBag createSlaveOfflineBag(int recommendGrade, int realGrade, String themenumber) throws ThemeWordNotExistException;
	/**
	 * 下载离线包
	 * @param recommendGrade 推荐年级，有测试得出
	 * @param themenumber 主题编号，如：“1-1-1”
	 * @param videoRecommendDegree，视频推荐难度
	 */
	public void downOffLineBag(String recommendGrade,String themenumber,int videoRecommendDegree);
	
	public void save(OffLineWordXml olx);
	/**
	 * 查询该主题的离线文件
	 * @param themeId
	 * @return
	 */
	public List<OffLineWordXml> findByThemeId(String themeId);
	
	/**
	 * 查询最新版的离线文件
	 * @param id
	 * @return
	 */
	public OffLineWordXml findNewByThemeId(String id);

	public void update(OffLineWordXml owx);
	
}
