package com.cnu.iqas.dao.ios.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.bean.ios.SuserWord;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.ios.SUserDao;
import com.cnu.iqas.dao.ios.SUserWordDao;

/**
* @author 周亮 
* @version 创建时间：2016年3月6日 下午10:10:04
* 类说明
*/
@Repository("SUserWordDao")
public class SUserWordDaoImpl extends DaoSupport<SuserWord>implements SUserWordDao {
}
