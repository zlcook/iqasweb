package com.cnu.iqas.controller.mobile.ios;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.bean.ios.SuserWork;
import com.cnu.iqas.constant.ResourceConstant;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.service.common.IFileDownloadService;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.service.common.IUserWorkService;
import com.cnu.iqas.service.ios.impl.SUserWorkServiceImpl;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.iqas.vo.mobile.ios.WorkVoManage;

/**
* @author 周亮 
* @version 创建时间：2016年3月2日 下午3:27:48
* 类说明
*/
@Controller
@RequestMapping(value="/mobile/ios/work/")
public class SWorkController implements ServletContextAware{
	private ServletContext servletContext;
	/**
	 * 文件下载服务
	 */
	private IFileDownloadService sfileDownloadService;
	/**
	 * 用户作品服务类
	 */
	private IUserWorkService<SuserWork> suserWorkService;
	/**
	 * 用户服务类
	 */
	private IUserBaseService<Suser> suserBaseService;
	
	private final static Logger logger = LogManager.getLogger(SWorkController.class);
	
	
	@RequestMapping(value="uploadWork")
	public ModelAndView uploadWork(WorkVoManage vo){
		MyStatus status = new MyStatus();
		//校验基础信息是否完善
		if( vo.validateBaseInfo()){
			//1.校验用户
			Suser user =(Suser) suserBaseService.findUser(vo.getUserName(), vo.getPassword());
			if( user !=null){
				//2.上传文件类型
				//获取保存的跟路径
				String relativedir =null;
				//上传正确作品类型
				boolean typeOk = true;
				switch(vo.getType()){
				case ResourceConstant.TYPE_IMAGE://1图片
					relativedir = PropertyUtils.getFileSaveDir(PropertyUtils.IOS_WORK_IMAGE);
					break;
				case ResourceConstant.TYPE_VIDEO://4视频
					relativedir = PropertyUtils.getFileSaveDir(PropertyUtils.IOS_WORK_VIDEO);
					break;
				case ResourceConstant.TYPE_VOICE://3声音
					relativedir = PropertyUtils.getFileSaveDir(PropertyUtils.IOS_WORK_VOICE);
					break;
				default:
						status.setStatus(StatusConstant.WORK_FILE_TYPE);//确认作品类型
						typeOk= false;
				}
				if( relativedir!=null){
					//上传文件
					try {
					  //获取文件保存路径
					  String savePath=BaseForm.saveFile(servletContext, relativedir, vo.getFile());
					  //保存文件
					  SuserWork userWork = new SuserWork();
					  WebUtils.copyBean(userWork, vo);
					  userWork.setSize(vo.getFile().getSize());//文件大小
					  userWork.setExt(BaseForm.getExt(vo.getFile().getOriginalFilename()));//后缀
					  userWork.setSavePath(savePath);//保存路径
					  userWork.setUserId(user.getUserId());//用户id
					  //保存
					  suserWorkService.save(userWork);
					  
					}catch(Exception e){
						e.printStackTrace();
						status.setExecptionStatus(e);
					}
				}else if( typeOk==true){
					status.setStatus(StatusConstant.WORK_SAVEDIR_NOT_CONFIGURE);
					status.setMessage("保存路径未配置！");
				}
			}else{
				status.setStatus(StatusConstant.USER_NAME_OR_PASSWORD_ERROR);
			}
		}else{
			status.setParamsError();
		}
		return JsonTool.generateModelAndView(status);
	}
	
	
	/**
	 *  下载文件
	 * @param savePath
	 * @return
	 */
	@RequestMapping(value="downloadWork")
	public Void getWork(String savePath,HttpServletRequest request,HttpServletResponse response){
		//1.获取文件系统的根路径:D:/Soft/autoiqasweb/
		String fileSystemRoot = PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR);
		//2.生成文件的绝对路径:D:/Soft/autoiqasweb/ifilesystem/ios/works/pictures/24d6d764-e043-473b-a36b-c224a1afd222.jpg
		String fileSavePath = fileSystemRoot+savePath;
		
		//2.1获取文件资源
		Resource fileResource =sfileDownloadService.getFileResource("file:"+fileSavePath);
		
		response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + fileResource.getFilename());
		//3.建立文件
		try {
			//4.建立字节读取流
			InputStream is = fileResource.getInputStream();
			//5.建立缓冲流
			BufferedInputStream bis = new BufferedInputStream(is);
			//6.获取response的输出流
			OutputStream os = response.getOutputStream();
			//7.缓冲器2kb
			byte[] buf= new byte[2048];
			//8.进行传输
			int len=0;
			int count=0;
			while( (len=bis.read(buf))!=-1)
			{
				count+=len;
				os.write(buf, 0, len);
			}
			System.out.println("文件："+count+"byte");
			//9.刷新缓冲器
			os.flush();
			os.close();
			bis.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public IUserWorkService<SuserWork> getSuserWorkService() {
		return suserWorkService;
	}
	@Autowired
	public void setSuserWorkService(IUserWorkService<SuserWork> suserWorkService) {
		this.suserWorkService = suserWorkService;
	}
	public IUserBaseService<Suser> getSuserBaseService() {
		return suserBaseService;
	}
	@Autowired
	public void setSuserBaseService(IUserBaseService<Suser> suserBaseService) {
		this.suserBaseService = suserBaseService;
	}
	public IFileDownloadService getSfileDownloadService() {
		return sfileDownloadService;
	}
	@Autowired
	public void setSfileDownloadService(IFileDownloadService sfileDownloadService) {
		this.sfileDownloadService = sfileDownloadService;
	}

}
