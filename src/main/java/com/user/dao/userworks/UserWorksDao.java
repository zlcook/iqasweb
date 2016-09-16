package com.user.dao.userworks;

import java.util.List;

import com.cnu.iqas.dao.base.DAO;
import com.user.entity.UserWorks;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 下午8:52:35 
 * 类说明 
*/
public interface UserWorksDao extends DAO<UserWorks> {

	public UserWorks findUserWorks(String wherejpql, Object[] attribute);
	
	/**
	 * 根据作品名和用户找到其相关上传记录
	 * @param worksName
	 * @param userId
	 * @return
	 */
	public List<UserWorks> find(String worksName,String userId);
	
}
