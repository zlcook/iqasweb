package com.cnu.iqas.controller.mobile.ios;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;

/**
* @author 周亮 
* @version 创建时间：2016年3月6日 下午7:00:23
* 类说明:用户信息修改控制类
*/
@Controller
@RequestMapping(value="/mobile/ios/user/update/")
public class SUserManagerController  implements ServletContextAware{
	private ServletContext servletContext;
	/**
	 * 用户基本服务接口
	 */
	private IUserBaseService suserBaseService;
	
	/**
	 * 更新用户头像
	 * @param userName 用户名
	 * @param password 密码
	 * @param logo 头像文件
	 * @return
	 */
	@RequestMapping(value="logo")
	public ModelAndView editPicture(String userName,String password,@RequestParam("logo") CommonsMultipartFile  logo)
	{
		MyStatus status = new MyStatus();
		//1.校验用户名和密码和文件
		if( !WebUtils.isNull(userName) &&!WebUtils.isNull(password) && logo!=null && logo.getSize()>0){
			//2.查询用户
			Suser user = (Suser) suserBaseService.findUser(userName, password);
			if( user !=null){
				//3.判断文件是否是图片格式
				if( BaseForm.validateImageFileType(logo.getOriginalFilename(), logo.getContentType())){
					//4.判断文件大小 2M
					long logoSizeMax = 1024*1024*2;
					if( logo.getSize()<=logoSizeMax){
						//5.获取保存头像的相对路径
						String relativedir= PropertyUtils.getFileSaveDir(PropertyUtils.IOS_USER_LOG);
						try {
							//6.保存头像并得到保存的相对路径
							String picturePath=BaseForm.saveFile(servletContext, relativedir, logo);
							//7.更新用户头像路径信息
							user.setPicturePath(picturePath);
							suserBaseService.update(user);
							
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
	public IUserBaseService getSuserBaseService() {
		return suserBaseService;
	}
	@Resource
	public void setSuserBaseService(IUserBaseService suserBaseService) {
		this.suserBaseService = suserBaseService;
	}
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
	
}
