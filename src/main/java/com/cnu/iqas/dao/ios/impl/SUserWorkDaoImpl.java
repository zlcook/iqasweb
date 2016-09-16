package com.cnu.iqas.dao.ios.impl;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.ios.SuserWord;
import com.cnu.iqas.bean.ios.SuserWork;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.common.IUserWorkDao;

/**
* @author 周亮 
* @version 创建时间：2016年3月9日 下午1:19:01
* 类说明:用户作品数据访问层
*/
@Repository("suserWorkDao")
public class SUserWorkDaoImpl extends DaoSupport<SuserWork> implements IUserWorkDao<SuserWork> {

}
