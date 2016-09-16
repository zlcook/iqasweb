package com.cnu.iqas.dao.store.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.CommodityType;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.store.CommodityTypeDao;

/**
* @author 周亮 
* @version 创建时间：2015年12月18日 下午2:42:12
* 类说明：操作商品类型表的数据访问接口的实现类
*/
@Repository("commodityTypeDao")
public class CommodityTypeDaoImpl extends DaoSupport<CommodityType>implements CommodityTypeDao {


}
