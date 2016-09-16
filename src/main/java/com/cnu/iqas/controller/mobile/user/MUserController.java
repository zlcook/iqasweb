package com.cnu.iqas.controller.mobile.user;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.DateJsonValueProcessor;
import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.formbean.user.UserForm;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.service.user.StudyDateService;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;
import com.user.service.rules.Rules;
import com.user.service.userlogin.UserLoginService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author 周亮
 * @version 创建时间：2015年11月7日 下午4:45:16 类说明
 */
@Controller
@RequestMapping(value = "/mobile/user/")
public class MUserController  implements ServletContextAware{
	private ServletContext servletContext;
	private Logger logger = LogManager.getLogger(MUserController.class);
	private IUserBaseService<User> userService;
	/**
	 * 用户学习记录服务类
	 */
	private StudyDateService studyDateService;
	
	private UserLoginService userLoginService;

	private Rules rules;
	/**
	 * 检查用户名是否存在
	 * @param userName
	 * @return { "status": 1, "message": "" }
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "validateUserName")
	public ModelAndView register(String userName) {

		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		MyStatus status = new MyStatus();
		// 总的json对象
		JSONObject jsonObejct = new JSONObject();

		// ----以下为业务逻辑，
		try {
			if (userName != null && !userName.trim().equals("")) {
				// 检查用户名是否存在
				if (null == userService.findByName(userName)) {
					status.setMessage("用户名不存在!");
					status.setStatus(StatusConstant.USER_NOT_EXIST);
				}
			} else {
				status.setMessage("请输入用户名");
				status.setStatus(StatusConstant.PARAM_ERROR);
			}

		} catch (Exception e) {
			status.setMessage("未知异常!");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putStatusJson(status, jsonObejct);
			mv.addObject("json", jsonObejct.toString());
			return mv;
		}
	}

	/**
	 * 检查用户名是否存在
	 * 
	 * @param userName
	 * @return { "status": 1, "message": "" }
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "updatePassword")
	public ModelAndView updatePassword(String userName, String password) {
		
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		MyStatus status = new MyStatus();
		// 总的json对象
		JSONObject jsonObejct = new JSONObject();
		
		// ----以下为业务逻辑，
		try {
			if (BaseForm.validate(userName) && BaseForm.validate(password)) {
				User user = (User) userService.findByName(userName);
				// 检查用户名是否存在
				if (null == user) {
					status.setMessage("用户名不存在!");
					status.setStatus(StatusConstant.USER_NOT_EXIST);
				} else {
					user.setPassword(WebUtils.MD5Encode(password));
					userService.update(user);
				}
			} else {
				status.setMessage("请输入完整信息");
				status.setStatus(StatusConstant.PARAM_ERROR);
			}
		} catch (Exception e) {
			status.setMessage("未知异常!");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putStatusJson(status, jsonObejct);
			mv.addObject("json", jsonObejct.toString());
			return mv;
		}
	}
	/***
	 * 注册
	 * @param formbean 用户名，密码，性别，年级
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "register")
	public ModelAndView register(@Valid UserForm formbean, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		MyStatus status = new MyStatus();
		// 总的json对象
		JSONObject jsonObejct = new JSONObject();
		// ------------------以下为业务逻辑，此处可以采用面向切面编程
		try {
			// 参数校验
			if (!bindingResult.hasErrors()) {
				// 检查用户名是否存在
				if (null == userService.findByName(formbean.getUserName())) {
					// 注册
					User u = new User();
					WebUtils.copyBean(u, formbean);
					userService.save(u);
				} else {
					status.setStatus(StatusConstant.USER_EXIST);
				}
			} else {
				status.setStatus(StatusConstant.PARAM_ERROR);
			}
		} catch (Exception e) {
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} 
		// -------------------返回视图
		JSONObject json =JSONObject.fromObject(status);
		mv.addObject("json", json.toString());
		return mv;
		
	}
	/**
	 * 更新用户头像
	 * @param userName 用户名
	 * @param password 密码
	 * @param logo 头像文件
	 * @return
	 */
	@RequestMapping(value="updateLogo")
	public ModelAndView editPicture(String userName,String password,@RequestParam("logo") CommonsMultipartFile  logo)
	{
		MyStatus status = new MyStatus();
		//1.校验用户名和密码和文件
		if( !WebUtils.isNull(userName) &&!WebUtils.isNull(password) && logo!=null && logo.getSize()>0){
			//2.查询用户
			User user = (User)userService.findUser(userName, password);
			if( user !=null){
				//3.判断文件是否是图片格式
				if( BaseForm.validateImageFileType(logo.getOriginalFilename(), logo.getContentType())){
					//4.判断文件大小 2M
					long logSizeMax = 1024*1024*2;
					if( logo.getSize()<=logSizeMax){
						//5.获取保存头像的相对路径
						String relativedir= PropertyUtils.getFileSaveDir(PropertyUtils.FIRST_USER_LOG);
						try {
							//6.保存头像并得到保存的相对路径
							String picturePath=BaseForm.saveFile(servletContext, relativedir, logo);
							//7.更新用户头像路径信息
							user.setPicturePath(picturePath);
							userService.update(user);
							
						} catch (Exception e) {
							e.printStackTrace();
							status.setExecptionStatus(e);
						}
					}else{
						status.setStatus(StatusConstant.USER_LOG_SIZE_OVER);
						status.setMessage("用户上传头像不能大于2M");
					}
				}else{
					status.setStatus(StatusConstant.USER_LOG_NOT_PICTURE);
					status.setMessage("上传用户头像不是图片格式");
				}
			}else{
				status.setStatus(StatusConstant.USER_NOT_EXIST);
				status.setMessage("用户不存在!");
			}
		}else{
			status.setStatus(StatusConstant.PARAM_ERROR);
			status.setMessage("参数有误!");
		}
		return JsonTool.generateModelAndView(status);
	}
	/**
	 *  
	 * @param formbean 
	 * @return { status:1, 1:成功，0失败 message:"ok",原因 result:{ 
	 * 			count:1, data:[ 
	 *         { 
	 *         account:"zhangsan",password:"123",gender:"男",picturePath: 
	 *         "http://172.19.68.77:8080/zhushou/images/logo.jpg" 
	 *         } 
	 * 		      
	 *         ] 
	 *         } 
	 *   
	 */ 
	@Deprecated
	@SuppressWarnings("finally")
	@RequestMapping(value = "login")
	public ModelAndView login(UserForm formbean, HttpServletRequest request) {
		MyStatus status = new MyStatus();
		// 用户对象
		List<JSONObject> userlist = new ArrayList<>();
		
		// 用户对象配置类
		JsonConfig userConfig = new JsonConfig();
		try {
			// 检查账号是否存在
			User user = (User) userService.findUser(formbean.getUserName(), formbean.getPassword());
			if (null == user) {
				status.setMessage("用户名或者密码有误!");
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
			} else {
				//生成登录token
				user.setToken(WebUtils.token(user.getUserName()));
				
				// 过滤一些属性
				userConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				JSONObject userObject = JSONObject.fromObject(user, userConfig);
				// 通关率
				String successRate = getSuccessRate(user.getSuccessRate());
				userObject.put("successRate", successRate);
				// 加入集合中
				userlist.add(userObject);
				//更新token
				userService.update(user);
				//保存登录记录
				userLoginService.addLoginRecord(user.getUserId(), request.getRemoteHost());
				//userService.addLoginRecord(user.getUserId(), formbean.getUserName(), request.getRemoteHost());
			}

		} catch (Exception e) {
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常");
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			// -------------------返回视图
			return JsonTool.generateModelAndView(userlist, status);
		}
	}
	
	
	
	/**
	 * 登录，新的登录方法
	 * @param userName
	 * @param password
	 * @param request
	 * @return
	 * {
	 *  status:
	 *  message:
	 *  token:
	 *  
	 * }
	 */
	@RequestMapping(value = "signup")
	public ModelAndView signup(String userName,String password, HttpServletRequest request) {
		MyStatus status = new MyStatus();
		String token = null;
		try {
			// 检查账号是否存在
			User user = (User) userService.findUser(userName, password);
			if (null == user) {
				status.setMessage("用户名或者密码有误!");
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
			} else {
				//生成登录token
				token = WebUtils.token(user.getUserName());
				user.setToken(token);
				//更新token
				userService.update(user);
				//保存登录记录
				userLoginService.addLoginRecord(user.getUserId(), request.getRemoteHost());
				//userService.addLoginRecord(user.getUserId(), formbean.getUserName(), request.getRemoteHost());
			}
			rules.topicRecommendAndroid(user.getUserId());
		} catch (Exception e) {
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常");
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		
		// -------------------返回视图
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		
		mv.addObject("json", JsonTool.singleAttribute("token", token, status));
		return mv;
	}
	
	/**
	 * 退出 用户名，密码
	 * 
	 * @param formbean
	 * @return
	 */
	@RequestMapping(value = "exit")
	public ModelAndView exit(String userName, String password, HttpServletRequest request) {

		MyStatus status = new MyStatus();
		// 校验用户名，密码
		if (!WebUtils.isNull(userName) && !WebUtils.isNull(password)) {
			userService.logout(userName, password, request.getRemoteHost());
		} else {
			status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
			status.setMessage("用户名或者密码有误");
		}
		// 返回结果
		return JsonTool.generateModelAndView(status);
	}

	/**
	 * 将double类型通过率转换成带%号的通关率
	 * 
	 * @param successRate
	 *            ，通过率，如：0.6
	 * @return 60%
	 */
	private String getSuccessRate(Double successRate) {
		String strSuccessRate = "100%";
		if (successRate != null) {
			// 四舍五入只保留2位小数
			Double d = successRate * 100;
			
			DecimalFormat df = new DecimalFormat("#");
			// 生成字符66%
			strSuccessRate = df.format(d) + "%";
		}
		return strSuccessRate;
	}

	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "getUser")
	public ModelAndView getUser(String userName, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);

		String ip = request.getRemoteHost() + ":" + request.getRemoteAddr();
		System.out.println("host:address:  " + ip);
		int scode = StatusConstant.OK;// 结果
		String message = "ok";// 结果说明
		// 总的json对象
		JSONObject jsonObejct = new JSONObject();
		// result对象
		JSONObject resultObject = new JSONObject();
		// 用户对象
		JSONObject userObject;
		// 用户对象配置类
		JsonConfig userConfig = new JsonConfig();
		// ------------------以下为业务逻辑，此处可以采用面向切面编程
		try {
			// System.out.println(bindingResult.hasErrors()+":"+bindingResult.getFieldValue("username")+"："+bindingResult.getFieldValue("password"));
			// 检查账号是否存在
			// User user = userService.find(formbean.getUsername());
			User user = (User) userService.findByName(userName);
			if (null == user) {
				scode = StatusConstant.USER_NAME_OR_PASSWORD_ERROR;
				message = "用户名不存在!";
			} else {
				// 过滤一些属性
				userConfig.setExcludes(new String[] {});
				userConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

				userObject = JSONObject.fromObject(user, userConfig);
				// 通关率
				String successRate = "100%";
				if (user.getSuccessRate() != null) {
					// 四舍五入只保留2位小数
					Double d = user.getSuccessRate() * 100;
					
					DecimalFormat df = new DecimalFormat("#");
					// 生成字符66%
					successRate = df.format(d) + "%";
				}
				userObject.put("successRate", successRate);
				JSONArray usersArray = new JSONArray();
				usersArray.add(userObject);

				resultObject.put("count", 1);
				resultObject.put("data", usersArray);
			}

		} catch (Exception e) {
			scode = StatusConstant.UNKONWN_EXECPTION;
			message = "未知异常";
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			// -------------------返回视图
			return WebUtils.beforeReturn(scode, message, jsonObejct, resultObject, mv);
		}
	}

	/**
	 * 学习记录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "studyRecord")
	public ModelAndView studyRecord(String userName, String password) {
		return getStudyData(userName, password, 1);
	}

	/**
	 * 闯关类型对比
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "gameTypeContrast")
	public ModelAndView gameTypeContrast(String userName, String password) {
		return getStudyData(userName, password, 2);
	}

	/**
	 * 通过率对比
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "successRate")
	public ModelAndView successRate(String userName, String password) {
		return getStudyData(userName, password, 3);
	}

	/**
	 * 最爱的闯关
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "favoriteGameType")
	public ModelAndView favoriteGameType(String userName, String password) {
		return getStudyData(userName, password, 4);
	}

	/**
	 * 玩游戏时间
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "timeOfGame")
	public ModelAndView timeOfGame(String userName, String password) {
		return getStudyData(userName, password, 5);
	}

	private ModelAndView getStudyData(String userName, String password, int type) {
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		MyStatus status = new MyStatus();
		// 总的json对象
		JSONObject jsonObejct = new JSONObject();
		// result对象
		JSONObject resultObject = new JSONObject();
		// ------------------以下为业务逻辑，此处可以采用面向切面编程
		try {
			User user = (User) userService.findUser(userName, password);
			if (null == user) {
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
				status.setMessage("用户名或者密码有误!");
			} else {
				JSONArray picArray = new JSONArray();
				// 存放一张图片的路径
				// JSONObject pJson = new JSONObject();

				switch (type) {
				case 1:// 学习记录
					String picPath = studyDateService.studyRecord(user);
					// pJson.put("picturePath",picPath);
					picArray.add(picPath);
					break;
				case 2:// 闯关类型对比
					List<String> picPaths = studyDateService.gameTypeContrast(user);
					for (String str : picPaths) {
						// JSONObject pJson2 = new JSONObject();
						// pJson2.put("picturePath",str);
						picArray.add(str);
					}
					break;
				case 3:// 通过率对比
					String sp = studyDateService.successRate(user);
					// pJson.put("picturePath",sp);
					picArray.add(sp);
					break;
				case 4:// 最爱的闯关
					String fp = studyDateService.favoriteGameType(user);
					// pJson.put("picturePath",fp);
					picArray.add(fp);
					break;
				case 5:// 玩游戏时间
					String tp = studyDateService.timeOfGame(user);
					// pJson.put("picturePath",tp);
					picArray.add(tp);
					break;
				}

				resultObject.put("count", picArray.size());
				resultObject.put("data", picArray);
			}

		} catch (Exception e) {
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("出现未知异常！");
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			// -------------------返回视图
			JsonTool.createJsonObject(jsonObejct, resultObject, status);
			mv.addObject("json", jsonObejct.toString());
			return mv;
		}
	}

	public StudyDateService getStudyDateService() {
		return studyDateService;
	}

	@Resource
	public void setStudyDateService(StudyDateService studyDateService) {
		this.studyDateService = studyDateService;
	}

	public IUserBaseService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserBaseService userService) {
		this.userService = userService;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

	public UserLoginService getUserLoginService() {
		return userLoginService;
	}
	@Resource
	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	public Rules getRules() {
		return rules;
	}
	@Resource
	public void setRules(Rules rules) {
		this.rules = rules;
	}

}
