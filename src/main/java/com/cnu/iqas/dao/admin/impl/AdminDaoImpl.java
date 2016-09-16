package com.cnu.iqas.dao.admin.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.admin.Admin;
import com.cnu.iqas.dao.admin.AdminDao;
import com.cnu.iqas.dao.base.DaoSupport;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 上午11:09:52
* 类说明
*/
@Repository("adminDao")
public class AdminDaoImpl extends DaoSupport<Admin> implements AdminDao {

	
	@Override
	public Admin validate(final String account,final String password) {
		Admin admin = ht.execute(new HibernateCallback<Admin>() {
			@Override
			public Admin doInHibernate(Session session)
					throws HibernateException, SQLException {
				Admin admin = (Admin) session.createQuery("from Admin o where  o.account=:account and  o.password=:password")
								.setParameter("account", account)
								.setParameter("password", password)
								.uniqueResult();
				return admin;
			}
		});
		return admin;
	}

}
