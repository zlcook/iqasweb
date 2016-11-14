package com.cnu.offline.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnu.iqas.service.common.IFileDownloadService;
import com.cnu.offline.MobileStyleEnum;
import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.exception.ThemeWordNotExistException;

/**
* @author 周亮 
* @version 创建时间：2016年9月10日 下午3:00:27
* 类说明
* 离线包接口，有查找离线包，生成离线包，更新离线包接口
*/
public interface OffLineBagService extends IFileDownloadService{
	/**
	 * 根据主题、推荐年级、实际年级、移动端查询离线包记录
	 * @param themenumber
	 * @param recommendGrade
	 * @param realGrade
	 * @param mobile
	 * @return
	 */
	public OffLineBag find(String themenumber,int recommendGrade,int realGrade,MobileStyleEnum moblie);
	
	
	public OffLineBag find(String id);
	/**
	 * 判断主题的主离线包是否存在
	 * @param themenumber 主题
	 * @param realGrade 实际年级
	 * @return true:存在,false不存在
	 */
	public boolean existMasterBag(String themenumber, int realGrade);
	/**
	 * 添加离线包
	 * @param offLineBag
	 */
	public void add(OffLineBag offLineBag);
	
	/**
	 * 更新
	 * @param offLineBag
	 */
	public void update(OffLineBag offLineBag);
	
	/**
	 * 查询主题下所有主离线包
	 * @param themenumber 主题编号
	 * @return
	 */
	public List<OffLineBag> listMaster(String themenumber);
	/**
	 * 下载
	 * @param bagId
	 * @param response
	 */
	public void downOffLineBag(String bagId,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 创建主离线包
	 * @param createService 具体的实现类，ios和android会通过不同的实现从生成不同的离线包
	 * @param realGrade 实际年级
	 * @param themenumber 主题编号，如：“1-1-1”
	 * @return
	 */
	public OffLineBag createMasterOfflineBag(int realGrade, String themenumber,OffLineAdapter adapter) throws ThemeWordNotExistException ;

	/**
	 * 创建从离线包，从离线包和其主离线包的参数只有recommendGrade值是不同的。
	 * @param createService 具体的实现类，ios和android会通过不同的实现从生成不同的离线包
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
	public OffLineBag createSlaveOfflineBag(int recommendGrade, int realGrade, String themenumber,OffLineAdapter adapter) throws ThemeWordNotExistException;
	
	
}
