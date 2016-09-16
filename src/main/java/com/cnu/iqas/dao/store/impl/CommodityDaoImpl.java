package com.cnu.iqas.dao.store.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.Commodity;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.store.CommodityDao;

/**
* @author 周亮 
* @version 创建时间：2015年12月18日 下午2:38:55
* 类说明 ：操作商品表的的数据访问接口的实现类
*/
@Repository("commodityDao")
public class CommodityDaoImpl extends DaoSupport<Commodity>implements CommodityDao {

}
