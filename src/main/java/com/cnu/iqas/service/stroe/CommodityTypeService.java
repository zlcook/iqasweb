package com.cnu.iqas.service.stroe;

import java.util.LinkedHashMap;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.CommodityType;

/**
* @author 周亮 
* @version 创建时间：2015年12月21日 下午6:32:53
* 类说明 商品类型服务接口
*/
public interface CommodityTypeService {

	/**
	 * 保存商品
	 * @param type
	 */
	public void save(CommodityType type);
	/**
	 * 根据id查看商品类型
	 * @param id
	 * @return
	 */
	public CommodityType find(String id);
	/**
	 * 根据商品类型名查看某个商品类型
	 * @param name  商品类型名
	 * @return
	 */
	public CommodityType findByName(String name);
	/**
	 * 根据商品类型等级查看某个商品类型
	 * @param grade  商品类型等级
	 * @return
	 */
	public CommodityType findByGrade(Integer grade);
	/**
	 * 分页查询，结果根据条件排序
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult 一页的最大记录数
	 * @param orderby 排序条件  Key为属性,Value为asc/desc
	 * @return 查询结果类
	 */
	public QueryResult<CommodityType> getScrollData(int firstindex, int maxresult, LinkedHashMap<String, String> orderby);
	/**
	 * 根据商品类型id，使商品类型无效或者有效
	 * @param id 商品类型id
	 * @param visible ：true表示有效，false:无效
	 */
	public void makeVisible(String id, boolean visible);
}
