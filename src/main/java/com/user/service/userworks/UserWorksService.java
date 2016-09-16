package com.user.service.userworks;

import java.util.Date;

import com.user.entity.UserWorks;

/**
 * @author 郭明丽 
 * @version 创建时间：2016年6月30日 下午9:29:05 
 * 类说明 
*/
public interface UserWorksService {

	/**
	 * 添加一条用户作品上传信息
	 * @param worksName
	 * @param userId
	 * @param word
	 * @param worksType 1：文本 2：图片3：音频 4：视频
	 * @param worksUrl
	 * @param location   0:本地  1：服务器
	 */
	public void add(String worksName,String userId,String word,int worksType,String worksUrl,int location);
	
	public void delectUserWorks(String worksId);
	
	
	public UserWorks findUserWorks(String worksId,String userId);
}
