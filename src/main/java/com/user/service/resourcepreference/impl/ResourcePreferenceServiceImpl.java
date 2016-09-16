package com.user.service.resourcepreference.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.user.dao.resourcepreference.ResourcePreferenceDao;
import com.user.dao.userresource.UserResourceDao;
import com.user.entity.ResourcePreference;
import com.user.entity.UserResource;
import com.user.service.resourcepreference.ResourcePreferenceService;
import com.user.service.userresource.UserResourceService;
@Service("resourcePreferenceService")
public class ResourcePreferenceServiceImpl implements ResourcePreferenceService {
    public static int MIN_PICLEARN = 1;
    public static int AVE_PICLEARN = 10;
    public static int MIN_TEXTLEARN = 5;
    public static int AVE_TEXTLEARN = 30;
    public static int MIN_AUDIOLEARN = 10;
    public static int AVE_AUDIOLEARN = 60;
    public static int MIN_VIDEOLEARN = 10;
    public static int AVE_VIDEOLEARN = 120;
	private ResourcePreferenceDao resourcePreferenceDao;
	private UserResourceDao userResourceDao;
	private UserResourceService userResourceService;
	@Override
	public void add(String userId, String feature, String featureValue, double pfeedback1, double pfeedback2,
			double pfeedback3) {
		// TODO Auto-generated method stub
	    ResourcePreference rp = resourcePreferenceDao.find(userId, feature, featureValue);
	    if(rp == null)
		    resourcePreferenceDao.save(new ResourcePreference(userId,feature,featureValue,pfeedback1,pfeedback2,pfeedback3));
	    else 
	    {
	    	rp.setPfeedback1(pfeedback1);
	    	rp.setPfeedback2(pfeedback2);
	    	rp.setPfeedback3(pfeedback3);
	    	resourcePreferenceDao.update(rp);
	    }   	
	}

	@Override
	public void update(String userId) {
		// TODO Auto-generated method stub
      //统计用户资源学习数据，并根据结束时间-开始时间与上述界定时间比较，确定类别。
      List<UserResource> urlist = userResourceService.findByuserId(userId);
      int feedbackCount[] = new int[3];//存储三种反馈类别的个数
      int aspectCount[][] = new int[20][3];  //存储不同资源对应不同反馈类别的个数
      int mediaCount[][] = new int[4][3];   //存储不同媒体类型对应不同反馈类别的个数
      //存放上述对应的概率值
      double feedback[] = new double[3];
      double aspect[][] = new double[20][3];
      double media[][] = new double[4][3];
      String resourceAspect[] = new String[20];
      int aspectKind = 0;
      if(urlist == null) 
      {
    	  System.out.println("no data!");
    	  return ;
      }
      for(UserResource ur : urlist)
      {
    	  if(ur != null) 
    	  {
    		  //资源类型为0（呈现顺序固定的），资源类型为5（发音）的不参与统计
    		  if(ur.getMediaType() != 0 && ur.getMediaType() != 5)
    		  {
    			  String resourceId = ur.getResourceId();
    			  if(resourceId.substring(0, 5).equals("video"))
    				  resourceId = "video";
    			  int mediaType = ur.getMediaType();
    			  long learnDuration = ur.getLearnDuration();
                  int fb = 0; 
    			  if(mediaType == 1)
    				  fb = learnDuration >= AVE_TEXTLEARN*1000 ? 0 : (learnDuration >= MIN_TEXTLEARN*1000 ? 1 : 2);
    				  else 
    					  if(mediaType == 2)
    						  fb = learnDuration >= AVE_PICLEARN*1000 ? 0 : (learnDuration >= MIN_PICLEARN*1000 ? 1 : 2);
    						  else if(mediaType == 3)
    							fb = learnDuration >= AVE_VIDEOLEARN*1000 ? 0 : (learnDuration >= MIN_VIDEOLEARN*1000 ? 1 : 2);
    							else if(mediaType == 4)
    								fb = learnDuration >= AVE_AUDIOLEARN*1000 ? 0 : (learnDuration >= AVE_AUDIOLEARN*1000 ? 1 : 2);
    		     feedbackCount[fb] ++;
    		     int findexist = 0;  //之前是否已统计过这个属性的标志位
    		     for(int i = 0 ; i < aspectKind; i ++)
    		    	 if(resourceId == resourceAspect[i])
    		    	 {
    		    		 findexist = 1;
    		    		 aspectCount[i][fb] ++;
    		    		 break;
    		    	 }
    		     if(findexist == 0)
    		    	 {
    		    	     System.out.println("aspectkind=" + aspectKind);
    		    	     resourceAspect[aspectKind] = resourceId;
    		    	     aspectCount[aspectKind][fb] ++;
    		    	     aspectKind ++;
                         
    		    	 }
    		    mediaCount[mediaType - 1][fb] ++; 
    		  }
    	  }	  
      }
      int totalCount = feedbackCount[0] + feedbackCount[1] + feedbackCount[2];
      if(totalCount > 0)
      {
    	  feedback[0] = feedbackCount[0] * 1.0 / totalCount;
          feedback[1] = feedbackCount[1] * 1.0 / totalCount;
          feedback[2] = feedbackCount[2] * 1.0 / totalCount; 
          this.add(userId, "0", "0", feedback[0], feedback[1], feedback[2]);
      }
      for(int i = 0 ; i < 3 ; i ++)
      {
    	  if(feedbackCount[i] == 0)
    			  feedbackCount[i] ++;
    	  System.out.println("i = " + i + ",aspectkind=" + aspectKind);
    	  for(int j = 0 ; j < aspectKind ; j ++)
    		{
    		  System.out.println(aspectCount[j][i]);
    			aspect[j][i] = aspectCount[j][i] * 1.0 / feedbackCount[i];
    		}
    //	  this.add(userId, "aspect", resourceAspect[j], aspect[0][i], aspect[1][i], aspect[2][i]);
    	  for(int k = 0 ; k < 4 ; k ++)
    		  media[k][i] = mediaCount[k][i] * 1.0 / feedbackCount[i];
      }
      for(int j = 0 ; j < aspectKind ; j ++)
    	  this.add(userId, "aspect", resourceAspect[j], aspect[j][0], aspect[j][1], aspect[j][2]);
      for(int k = 0 ; k < 4 ; k ++)
      {
    	  int newk = k + 1;
    	  String nk = String.valueOf(newk);   //媒体类型作为特征值为String类型
    	  this.add(userId, "media", nk, aspect[k][0], aspect[k][1], aspect[k][2]);
      }
    	 
	}

	public ResourcePreferenceDao getResourcePreferenceDao() {
		return resourcePreferenceDao;
	}
    @Resource
	public void setResourcePreferenceDao(ResourcePreferenceDao resourcePreferenceDao) {
		this.resourcePreferenceDao = resourcePreferenceDao;
	}

	public UserResourceDao getUserResourceDao() {
		return userResourceDao;
	}
    @Resource
	public void setUserResourceDao(UserResourceDao userResourceDao) {
		this.userResourceDao = userResourceDao;
	}

	public UserResourceService getUserResourceService() {
		return userResourceService;
	}
    @Resource
	public void setUserResourceService(UserResourceService userResourceService) {
		this.userResourceService = userResourceService;
	}

	@Override
	public double recommendByPreference(String userId, String aspect, int mediaType) {
		// TODO Auto-generated method stub
		String media = Integer.toString(mediaType);
		ResourcePreference rp = resourcePreferenceDao.find(userId, "0", "0");
		ResourcePreference arp = resourcePreferenceDao.find(userId, "aspect", aspect);
		ResourcePreference mrp = resourcePreferenceDao.find(userId, "media", media);
		if(rp != null && arp != null && mrp != null)
		{
			return (rp.getPfeedback1()*arp.getPfeedback1()*mrp.getPfeedback1())*2 + (rp.getPfeedback2()*arp.getPfeedback2()*mrp.getPfeedback2()) - (rp.getPfeedback3()*arp.getPfeedback3()*mrp.getPfeedback3());
		}
		return 0;
	}

}
