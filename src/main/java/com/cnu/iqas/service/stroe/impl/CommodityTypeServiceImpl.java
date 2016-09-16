package com.cnu.iqas.service.stroe.impl;

import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.CommodityType;
import com.cnu.iqas.dao.store.CommodityTypeDao;
import com.cnu.iqas.service.stroe.CommodityTypeService;

/**
* @author 周亮 
* @version 创建时间：2015年12月21日 下午6:33:26
* 类说明 商品类型服务接口实现类
*/
@Service("commodityTypeService")
public class CommodityTypeServiceImpl implements CommodityTypeService {

	private CommodityTypeDao commodityTypeDao;
	@Override
	public void save(CommodityType type) {
		commodityTypeDao.save(type);
	}
	
	
	public CommodityTypeDao getCommodityTypeDao() {
		return commodityTypeDao;
	}
	@Resource
	public void setCommodityTypeDao(CommodityTypeDao commodityTypeDao) {
		this.commodityTypeDao = commodityTypeDao;
	}


	@Override
	public CommodityType find(String id) {
		CommodityType ty = commodityTypeDao.find(id);
		return ty;
	}


	@Override
	public CommodityType findByName(String name) {
		
		String wherejpql ="o.name=?";
		CommodityType ty = commodityTypeDao.find(wherejpql, name);
		return ty;
	}

	/**
	 * 分页查询，结果根据条件排序
	 * @param firstindex 开始查询位置从0开始
	 * @param maxresult 一页的最大记录数
	 * @param orderby 排序条件  Key为属性,Value为asc/desc
	 * @return 查询结果类
	 */
	public QueryResult<CommodityType> getScrollData(int firstindex, int maxresult, LinkedHashMap<String, String> orderby){
		
		return commodityTypeDao.getScrollData(firstindex, maxresult, orderby);
	}


	@Override
	public void makeVisible(String id, boolean visible) {
		CommodityType type =commodityTypeDao.find(id);
		if( type !=null)
		{
			type.setVisible(visible);
			commodityTypeDao.update(type);
		}
		
	}


	@Override
	public CommodityType findByGrade(Integer grade) {
		String wherejpql ="o.grade=?";
		CommodityType ty = commodityTypeDao.find(wherejpql, grade);
		return ty;
	}
	
}
