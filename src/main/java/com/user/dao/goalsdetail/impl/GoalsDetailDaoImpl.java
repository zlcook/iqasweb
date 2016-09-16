package com.user.dao.goalsdetail.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.dao.base.DaoSupport;
import com.user.dao.goalsdetail.GoalsDetailDao;
import com.user.entity.GoalsDetail;
@Repository("goalsDetailDao")
public class GoalsDetailDaoImpl extends DaoSupport<GoalsDetail> implements GoalsDetailDao {

	@Override
	public List<GoalsDetail> find(String userId, int inORout, Date time1, Date time2) {
		// TODO Auto-generated method stub
		List<GoalsDetail> list = (List<GoalsDetail>) getHt().find("From GoalsDetail o where o.userId=? and o.inORout=?", userId,inORout);
		List<GoalsDetail> gdlist = new ArrayList<GoalsDetail>();
		
        for(GoalsDetail gt : list)
        {
        	if(time1 != null && gt.getTime().after(time1) || time1 == null)
        		if(time2 != null && gt.getTime().before(time2) || time2 == null)
        			gdlist.add(gt);       
        }
	    return gdlist;	 
	}

	@Override
	public List<GoalsDetail> find(String userId) {
		// TODO Auto-generated method stub
		List<GoalsDetail> list = (List<GoalsDetail>) getHt().find("From GoalsDetail o where o.userId=?", userId);
		return list;
	}
	
	
}
