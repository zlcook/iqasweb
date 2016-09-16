package com.cnu.iqas.dao.ios;

import java.util.List;

import com.cnu.iqas.bean.ios.SuserLogin;
import com.cnu.iqas.dao.base.DAO;
import com.cnu.iqas.dao.common.IFindUserLoginDao;

/**
* @author 周亮 
* @version 创建时间：2016年3月1日 下午4:46:23
* 类说明:ios登录记录表
*/
public interface SUserLoginDao extends DAO<SuserLogin>,IFindUserLoginDao<SuserLogin>{

	
}
