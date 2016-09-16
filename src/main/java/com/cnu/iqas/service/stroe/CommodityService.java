package com.cnu.iqas.service.stroe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.Commodity;

/**
* @author 周亮 
* @version 创建时间：2015年12月21日 下午6:35:19
* 类说明
*/
public interface CommodityService {

	/**
	 * 根据条件分页查询，结果根据条件排序
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult 一页的最大记录数
	 * @param wherejpql 查询条件  "o.email=? and o.account like ?"
	 * @param queryParams  查询条件占位符对应的参数值，
	 * @param orderby 排序条件  Key为属性,Value为asc/desc
	 * @return 查询结果类
	 */
	public QueryResult<Commodity> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby);
	
	

	/**
	 * 根据条件查询，结果根据条件排序
	 * @param wherejpql 查询条件  "o.email=? and o.account=?"
	 * @param queryParams 查询条件占位符对应的参数值，
	 * @param orderby 排序条件  Key为属性,Value为asc/desc
	 */
	public List<Commodity> getAllData(  String wherejpql,  Object[] queryParams, LinkedHashMap<String,String> orderby);

	/**
	 * 保存实体
	 * @param entity 实体id
	 */
	public void save(Commodity entity);
	/**
	 * 更新实体
	 * @param entity 实体id
	 */
	public void update(Commodity entity);
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityids 实体id数组
	 */
	public void delete(Serializable...  entityids);
	/**
	 * 获取实体
	 * @param <Commodity>
	 * @param entityClass 实体类
	 * @param entityId 实体id
	 * @return
	 */
	public Commodity find(Serializable entityId);
	/**
	 * 根据商品名称来获取实体
	 * @param <Commodity>
	 * @param name 商品名称 name
	 * @return
	 */
	public Commodity findByName(String name);
	
	/**
	 * 根据查询条件按时间降序排列查询可见商品。
	 * @param key  查询字段，格式：o.typeid=?
	 * @param value 查询字段对应查询值  
	 * @return
	 */
	public List<Commodity> getCommodityByParam(String key,String value);
}
