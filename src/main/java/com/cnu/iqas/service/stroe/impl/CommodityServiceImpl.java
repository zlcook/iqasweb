package com.cnu.iqas.service.stroe.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.Commodity;
import com.cnu.iqas.dao.store.CommodityDao;
import com.cnu.iqas.service.stroe.CommodityService;

/**
* @author 周亮 
* @version 创建时间：2015年12月21日 下午6:35:32
* 类说明
*/
@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

	private CommodityDao commodityDao;
	
	public CommodityDao getCommodityDao() {
		return commodityDao;
	}
	@Resource
	public void setCommodityDao(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}
	@Override
	public void save(Commodity entity) {
		commodityDao.save(entity);
		
	}
	@Override
	public void update(Commodity entity) {
		// TODO Auto-generated method stub
		commodityDao.update(entity);
	}
	@Override
	public void delete(Serializable... entityids) {
		// TODO Auto-generated method stub
		commodityDao.delete(entityids);
	}
	@Override
	public Commodity find(Serializable entityId) {
		// TODO Auto-generated method stub
		return commodityDao.find(entityId);
	}
	@Override
	public Commodity findByName(String name) {
		// TODO Auto-generated method stub
		return commodityDao.find("o.name=?", name);
	}
	@Override
	public QueryResult<Commodity> getScrollData(int firstindex, int maxresult, String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		// TODO Auto-generated method stub
		return commodityDao.getScrollData(firstindex, maxresult, wherejpql, queryParams,orderby);
	}
	@Override
	public List<Commodity> getAllData(String wherejpql, Object[] queryParams, LinkedHashMap<String, String> orderby) {
		// TODO Auto-generated method stub
		return commodityDao.getAllData(wherejpql, queryParams, orderby);
	}
	/**
	 * 根据查询条件按时间降序排列查询可见商品。
	 * @param key  查询字段，格式：o.typeid=?
	 * @param value 查询字段对应查询值  
	 * @return
	 */
	public List<Commodity> getCommodityByParam(String key,String value){
		
		StringBuilder sb = new StringBuilder("o.visible=?");
		List<Object> queryParams  = new ArrayList<Object>();
		queryParams.add(true);
		//构造查询条件
		if( key!=null && !key.equals("")){
			sb.append(" and "+key);
			queryParams.add(value);
		}
		
		//5.构造排序条件
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createTime", "desc");
		//6.根据条件查询商品
		List<Commodity> commoditys=getAllData(sb.toString(), queryParams.toArray(), orderby);

		return commoditys;
	}

}
