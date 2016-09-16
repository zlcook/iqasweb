package com.cnu.iqas.dao.user.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.store.CommodityType;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.dao.base.DaoSupport;
import com.cnu.iqas.dao.user.UserDao;
import com.cnu.iqas.utils.WebUtils;

/**
* @author 周亮 
* @version 创建时间：2015年11月9日 下午8:41:46
* 类说明
*/
@Repository("userDao")
public class UserDaoImpl  extends DaoSupport<User> implements UserDao {
	
	
	@Override
	public User findByName(final String userName) {
		User user = getHt().execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session)
					throws HibernateException, SQLException {
				User user = (User) session.createQuery("from User o where  o.userName=:userName ")
								.setParameter("userName", userName)
								.uniqueResult();
				return user;
			}
		});
		return user;
	}
	@Override
	public User findByNameAndPas(final String userName,final  String password) {
		User user = getHt().execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session)
					throws HibernateException, SQLException {
				User user = (User) session.createQuery("from User o where  o.userName=:userName and  o.password=:password")
								.setParameter("userName", userName)
								.setParameter("password", password)
								.uniqueResult();
				return user;
			}
		});
		return user;
	}

}
