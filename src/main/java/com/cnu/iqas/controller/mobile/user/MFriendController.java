package com.cnu.iqas.controller.mobile.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.xmlbeans.impl.jam.JSourcePosition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.user.Friend;
import com.cnu.iqas.bean.user.FriendRequest;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.service.user.FriendService;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.iqas.vo.mobile.FriendVoManage;
import com.hp.hpl.jena.sparql.pfunction.library.listIndex;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
* @author 周亮 
* @version 创建时间：2016年1月25日 下午1:29:19
* 类说明:处理好友关系
* 1.根据账号查找好友 searchFriend
* 2.发送好友添加请求sendRequest
* 3.查看好友发过来的添加请求lookRequests
* 4.接受好友请求receiveRequest
* 5.拒绝好友请求rejectRequest  
* 6.删除好友deleteFriend
* 7.查看自己的好友myFriends
*/
@Controller
@RequestMapping(value="mobile/friend/")
public class MFriendController {
  
	private FriendService friendService;
	/**
	 * 用户服务类
	 */
	private IUserBaseService userService;
	/**
	 * 根据账号查找好友
	 * @param userName
	 * @return
	 * {
	 *  "status":1,
	 *  "message":"ok",
	 *   "result":{
	 *    "count":1,
	 *    "data":[
	 *       {"userName":"zhangsan"}
	 *       ...
	 *    ]
	 *    
	 *   }
	 * }
	 */
	@RequestMapping(value="searchFriend")
	public ModelAndView searchFriend(String userName){
		//返回的所有好友
		List<JSONObject> listJson = new ArrayList<JSONObject>();
		//返回状态
		MyStatus status = new MyStatus();
		
		try {
			//查找到的好友
			JSONObject userJson=null;
			//用户名查找用户
			User user= (User) userService.findByName(userName);
			if( user !=null){
				userJson =new JSONObject();
				userJson.put("userName", user.getUserName());
				userJson.put("picturePath",user.getPicturePath());
				userJson.put("sex", user.getSex());
				listJson.add(userJson);
			}else{
				//用户不存在
				status.setStatus(StatusConstant.USER_NOT_EXIST);
				status.setMessage("用户不存在");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			 status.setMessage("未知异常");
		}
		//根据结果封装成json并返回视图
		return  JsonTool.generateModelAndView(listJson, status);
	}
	
	
	/**
	 * 发送好友添加请求
	 * @param userName 自己的用户名
	 * @param password ,密码
	 * @param friendUserName 好友的用户名
	 * @param content, 备注
	 * @return
	 */
	@RequestMapping(value="sendRequest")
	public ModelAndView sendRequest(FriendVoManage formbean){
		MyStatus status = new MyStatus();
		try {
			//校验用户密码和用户名是否有效
			User own = (User) userService.findUser(formbean.getUserName(), formbean.getPassword());
			if( own !=null){
				//对方是否已经是好友
				boolean isExist =friendService.isExistFriend(formbean.getUserName(), formbean.getFriendUserName());
				if( !isExist){
					//检验请求是否发送过
					//如果之前已经发送过请求，怎直接将原来请求的处理状态改为”未处理“|false
					FriendRequest frequest= friendService.findRequest(formbean.getUserName(),formbean.getFriendUserName());
					
					if( frequest!=null){
						//改为未处理状态
						frequest.setIsHandle(false);
						if( !WebUtils.isNull(formbean.getContent()))
						   frequest.setContent(formbean.getContent());
						//更新请求
						friendService.updateRequest(frequest);
					}else{
						//之前未发起过请求
						
						//校验好友用户名是否有效
						User friend= (User) userService.findByName(formbean.getFriendUserName());
						
						if( friend!=null){
							//添加请求
							friendService.sendAddFriendRequest(formbean.getUserName(), formbean.getFriendUserName(), formbean.getContent());
							
						}else{
							//好友不存在
							status.setStatus(StatusConstant.FRIEND_NOT_EXIST);
							status.setMessage("好友不存在！");
						}
					}
					
				}else{
					//好友已经存在
					status.setStatus(StatusConstant.FRIEND_HAVE_EXIST);
					status.setMessage("好友已经存在！");
				}
			}else{
				//用户密码或账户错误
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
				status.setMessage("用户名或密码错误");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			 status.setMessage("未知异常");
		}
		//返回结果
		return JsonTool.generateModelAndView(status);
	}
	/**
	 * 查看好友发过来的添加请求
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value="lookRequests")
	public ModelAndView lookRequests(FriendVoManage formbean){
		//此处请求状态
		MyStatus status = new MyStatus();
		//返回的所有好友请求
		List<JSONObject> requestJsonList = new ArrayList<>();
		try {
			//校验用户密码和用户名是否有效
			User own = (User) userService.findUser(formbean.getUserName(), formbean.getPassword());
			if( own !=null){
				//查看别人发送来的添加请求,自己在表中应该是被请求方，所以对应表中friendUserName字段
				List<FriendRequest> requests= friendService.fetchRequests(formbean.getUserName());
				
				if( requests !=null){
					JSONObject requestJson = null;
					JsonConfig config = new JsonConfig();
					config.setExcludes(new String[]{"createTime"});
					for( FriendRequest request : requests){
						requestJson = new JSONObject();
						requestJson.put("id", request.getId());
						requestJson.put("content", request.getContent());
						requestJson.put("picturePath", request.getPicturePath());
						//因为我查看的请求时别人发来的，所以在FriendRequest类中friendUserName字段存放的是我的用户名，而ownUserName是好友的用户名
						requestJson.put("friendUserName", request.getOwnUserName());
						//requestJson = JSONObject.fromObject(request,config);
						requestJsonList.add(requestJson);
					}
				}
			}else{
				//用户密码或账户错误
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
				status.setMessage("用户名或密码错误");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			 status.setMessage("未知异常");
		}
		return JsonTool.generateModelAndView(requestJsonList, status);
	}
	
	
	/**
	 * 接受好友请求
	 * @param userName
	 * @param password
	 * @param requestId 请求id
	 * @return
	 */
	@RequestMapping(value="receiveRequest")
	public ModelAndView receiveRequest(FriendVoManage formbean){
		//此处请求状态
		MyStatus status = new MyStatus();
		
		try {
			//校验用户密码和用户名是否有效
			User own = (User) userService.findUser(formbean.getUserName(), formbean.getPassword());
			if( own !=null){
				
			  //1.查看请求 
			  FriendRequest request=friendService.findRequest(formbean.getRequestId());
			 //请求存在并且未处理过
			  if( request !=null && request.getIsHandle()==false){
				   //2.判断是否已经是好友关系
				       ////接受别人发送来的添加请求,自己在表中应该是被请求方，所以对应表中friendUserName字段，而对方应该对应表中的ownUserName字段
					boolean isExist =friendService.isExistFriend(formbean.getUserName(), request.getOwnUserName());
					//如果是好友关系则直接更新请求状态
					if(isExist){
						//更新请求状态为“处理过”|true
						request.setIsHandle(true);
						friendService.updateRequest(request);
					}else{
					 //添加好友关系,同时会更新请求状态为“处理过”|true
						request.setIsHandle(true);
						 //接受别人发送来的添加请求,自己在表中应该是被请求方，所以对应表中friendUserName字段，而对方应该对应表中的ownUserName字段
					 boolean result= friendService.addFriendUpdateRequest(formbean.getUserName(), request.getOwnUserName(),request);
					}
				 
			  }else{
				  status.setStatus(StatusConstant.FRIEND_REQUEST_INVAIN);
				  status.setMessage("好友请求无效");
			  }
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			 status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			 status.setMessage("未知异常");
		}
		
		return JsonTool.generateModelAndView(status);
	}

	/**
	 * 拒绝好友请求rejectRequest  
	 * @param userName
	 * @param password
	 * @param requestId 请求id
	 * @return
	 */
	@RequestMapping(value="rejectRequest")
	public ModelAndView rejectRequest(FriendVoManage formbean){
		//此处请求状态
		MyStatus status = new MyStatus();
		
		try {
			//校验用户密码和用户名是否有效
			User own = (User) userService.findUser(formbean.getUserName(), formbean.getPassword());
			if( own !=null){
				
			  //1.查看请求 
			  FriendRequest request=friendService.findRequest(formbean.getRequestId());
			  //请求存在并且未处理过
			  if( request !=null && request.getIsHandle()==false){
				//更新请求状态为“处理过”|true
				request.setIsHandle(true);
				friendService.updateRequest(request);
			  }else{
				  status.setStatus(StatusConstant.FRIEND_REQUEST_INVAIN);
				  status.setMessage("好友请求无效");
			  }
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			 status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			 status.setMessage("未知异常");
		}
		return JsonTool.generateModelAndView(status);
	}
	/**
	 * 删除好友
	 *  @param userName 自己的用户名
	 * @param password ,密码
	 * @param friendRelId 好友关系id
	 * @return
	 */
	@RequestMapping(value="deleteFriend")
	public ModelAndView deleteFriend(FriendVoManage formbean){
		
		//此处请求状态
		MyStatus status = new MyStatus();
		try {
			//校验用户密码和用户名是否有效
			User own = (User) userService.findUser(formbean.getUserName(), formbean.getPassword());
			if( own !=null){
				//删除好友
				friendService.deleteFriend(formbean.getFriendRelId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			 status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			 status.setMessage("未知异常");
		}
		
		return JsonTool.generateModelAndView(status);
	}
	
	
	/**
	 * 查看自己的好友
	 *  @param userName 自己的用户名
	 * @param password ,密码
	 * @return
	 */
	@RequestMapping(value="myFriends")
	public ModelAndView myFriends(FriendVoManage formbean){
		//此处请求状态
		MyStatus status = new MyStatus();
		//存放所有好友数据
		List<JSONObject> friendsJson = new ArrayList<JSONObject>();
		
		try {
			String userName = formbean.getUserName();
			//校验用户密码和用户名是否有效
			User own = (User) userService.findUser(userName, formbean.getPassword());
			if( own !=null){
				//查看好友关系记录
				List<Friend> friends= friendService.myFriends(userName);
				if( friends !=null)
				for( Friend friend :friends ){
					
					JSONObject fJson = new JSONObject();
					
					String friendUserName = getFriendUserName(friend,userName);
					if( friendUserName!=null){
						//好友用户名
						fJson.put("friendUserName", friendUserName);
						//好友关系id
						fJson.put("friendRelId", friend.getId());
						
						//获取好友头像,金币
						String picturecPath = null;
						User fu = (User) userService.findByName(friendUserName);
						
						if( fu !=null ){
							//目前存放项目logo图片
							picturecPath = PropertyUtils.get(PropertyUtils.LOG);
							//头像
							fJson.put("picturePath",picturecPath);
							//好友金币
							fJson.put("coins",fu.getAllCoins());
							//勋章数
							fJson.put("medals",fu.getStoreGrade());
						}else{
							status.setStatus(StatusConstant.FRIEND_NOT_EXIST);
							status.setMessage("好友不存在!");
						}
						//添加到集合中
						friendsJson.add(fJson);
						//排序，将数据按金币、勋章数降序排序
						sort(friendsJson,false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			 status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			 status.setMessage("未知异常");
		}
		
		return JsonTool.generateModelAndView(friendsJson, status);
	}
	
	/**
	 * 将存放好友的集合按照金币和勋章进行排序，先进行金币数排，金币数相等再按勋章数排
	 * @param friendsJson
	 * @param flage
	 */
	private void sort(List<JSONObject> friendsJson, boolean flage) {
		// TODO Auto-generated method stub
		
		
		for(int i =0;i < friendsJson.size()-1;i++){
			JSONObject a =friendsJson.get(i);
			//金币数
			int coins=a.getInt("coins");
			//勋章数
			int medals =a.getInt("medals");
			
			for( int j=i+1;j< friendsJson.size();j++){
				JSONObject b =friendsJson.get(j);
				//金币数
				int bcoins=b.getInt("coins");
				//勋章数
				int bmedals =b.getInt("medals");
				//比较,升序
				if( flage==true){
					//金币数大，或者金币数相等但是勋章数大
					if(coins>bcoins  ||( coins==bcoins && medals>bmedals)){
						friendsJson.set(i, b);
						friendsJson.set(j,a);
					}
				}else{//降序
					//金币数大，或者金币数相等但是勋章数大
					if(coins<bcoins  ||( coins==bcoins && medals<bmedals)){
						friendsJson.set(i, b);
						friendsJson.set(j,a);
					}
				}
			}
		}
	}


	/**
	 * 从好友关系记录中获取好友的用户名
	 * @param friend 好友关系
	 * @param userName 用户的名称
	 * @return 返回好友的名称
	 */
	
	private String getFriendUserName(Friend friend,String userName){
		if( friend!=null){
			if( friend.getOneUserName().equals(userName))
				return friend.getOtherUserName();
			else
				return friend.getOneUserName();
		}
		return null;
	}
	public IUserBaseService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserBaseService userService) {
		this.userService = userService;
	}


	public FriendService getFriendService() {
		return friendService;
	}

	@Resource
	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}
	
	
}
