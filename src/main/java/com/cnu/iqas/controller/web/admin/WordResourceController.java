package com.cnu.iqas.controller.web.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.exception.word.ResourceTypeNotExisting;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.formbean.iword.WordResourceForm;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.utils.JsonTool;

import net.sf.json.JsonConfig;

/**
* @author 周亮 
* @version 创建时间：2015年11月23日 上午11:30:45
* 类说明  单词资源控制类，负责单词自身资源的增、删、查、改
*/
@Controller
@RequestMapping(value="/admin/control/wordresource/")
public class WordResourceController  implements ServletContextAware{
	//日志类
	private final static Logger logger = LogManager.getLogger(WordResourceController.class);
	 //单词资源服务类
	 private WordResourceService wordResourceService;
	 //应用对象
	 private ServletContext servletContext;
	 //单词服务类
	 private IwordService iwordService;
	 
	 //测试一下000
	 //添加资源界面
	 @RequestMapping(value="addUI")
	 public ModelAndView addResourceUI(String uuid){
		 ModelAndView mv = new ModelAndView(PageViewConstant.WORDRESOURCE_ADD);
		 Iword word = iwordService.find(uuid);
		 if( word == null){
			 mv.setViewName(PageViewConstant.MESSAGE);
		      mv.addObject("message","该单词不存在！");
		 }else{
			 mv.addObject("word", word);}
		 
		 return mv;
	 }
	 
	 /**
	  * 根据资源id删除某个资源
	  * @return
	  */
	 @RequestMapping(value="delete")
	 public String delete(String id){
		 ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		 if( BaseForm.validate(id)){
			 WordResource wr =wordResourceService.find(id);
			 if( wr !=null){
				 //设置不可见
				 wr.setVisible(false);
				 //更新
				 wordResourceService.update(wr);
			 }else{
				 mv.addObject("message","删除资源不存在!");
			 }
		 }else{
			 mv.addObject("message","删除资源不存在!");
		 }
		 mv.addObject("urladdress", "");
		 return PageViewConstant.MESSAGE;
	 }
	 /**
	  * ajax根据资源id删除某个资源
	  * @return
	  */
	 @RequestMapping(value="ajaxDelete")
	 public ModelAndView ajaxDelete(String id){
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		 MyStatus status = new MyStatus();
		 
		 if( null !=id && !"".equals(id.trim())){
			 WordResource wr =wordResourceService.find(id);
			 if( wr !=null){
				 //设置不可见
				 wr.setVisible(false);
				 //更新记录为删除状态
				 wordResourceService.update(wr);
				 //删除记录
				 //wordResourceService.delete(wr.getId());
			 }else{
				 status.setStatus(StatusConstant.WORD_RESOURCE_NO_EXIST);
				 status.setMessage("删除资源不存在!");
			 }
		 }else {
			 status.setMessage("删除单词资源id不能为空！");
			 status.setStatus(StatusConstant.PARAM_ERROR);
		 }
		 mv.addObject("json", JsonTool.createJson(null, null, status));
		 return mv;
	 }
	
	 //进入资源详情界面
	 @RequestMapping(value="resourceDetailUI")
	 public ModelAndView resourceDetailUI(String uuid){
		 ModelAndView mv = new ModelAndView(PageViewConstant.WORD_RESOURCE_DETAILUI);
		 Iword word = iwordService.find(uuid);
		 if( word == null){
			 mv.setViewName(PageViewConstant.MESSAGE);
		      mv.addObject("message","该单词不存在！");
		 }else{
			 mv.addObject("word", word);
		}
		 return mv;
	 }
	 
	 /**
		 * 通过ajax获取单词的某个类型的所有可见资源,
		 * @param formbean  接收单词的uuid和获取资源的类型
		 * @return
		 * json数据
		 * 返回内容：
		 *{
		 *  status:1,
		 *  message:"ok",
		 *  result:{
		 *   count:2,
		 *   data:[
		 *    
		 *		{id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      {id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      
		 *   ]
		 *  }
		 * }
		 */
	 @RequestMapping(value="ajaxFetchResources")
		public ModelAndView ajaxFetchResources(WordResourceForm  formbean){
			ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
			//状态
			MyStatus status = new MyStatus();
			
			Iword word =null;
			//单词uuid
			String uuid = formbean.getUuid();
			//资源类型，图片、声音。。。
			int type = formbean.getType();
			//资源类型是否有效
			if( formbean.validateType()){
				 if(BaseForm.validate(uuid)){
					   word = iwordService.find(uuid);
				 }
					 if( word == null){
						 status.setStatus(0);
					      status.setMessage("该单词不存在！");
					 }else{
						 //根据单词uuid获取单词资源
						 //构造查询条件和查询值
						 StringBuilder wherejpql = new StringBuilder();
						 List<Object> queryParams = new ArrayList<Object>();
						 //uuid
						 if( BaseForm.validate(uuid)){
							 queryParams.add(uuid);
							 wherejpql.append(" o.iword.uuid = ? ");
						 }
						 //资源类型 type
						 queryParams.add(type);
						 if(!queryParams.isEmpty())
							 wherejpql.append(" and ");
						 wherejpql.append(" o.type = ? ");
						 
						 //查询可见的
						 queryParams.add(true);
						 if(!queryParams.isEmpty())
							 wherejpql.append(" and ");
						 wherejpql.append(" o.visible = ? ");
						 
						 //调用查询函数获取查询结果 
						 List<WordResource> wordResources  =wordResourceService.getAllData(wherejpql.toString(), queryParams.toArray());
						
					
						//配置
						JsonConfig wrConfig = new JsonConfig();
						//groupConfig.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
						//排除属性
						wrConfig.setExcludes(new String[]{"iword","visible"});
						//生成最终的json语句
						 String jsonString= JsonTool.createJson(wordResources,wrConfig, status);
						
							//添加到request中
						 mv.addObject("json", jsonString);
						 mv.addObject("word", word);
					}
			}else{
				status.setStatus(0);
			    status.setMessage("资源类型无效！");
			    logger.error("查找的单词资源类型 "+type+" 无效!");
			}
			
			 return mv;
		}
		
	 /**
	  * 添加单词资源，该方法应该具有事务属性，暂时还未加
	  * 
	  * @return
	  */
	 @RequestMapping(value="add",method=RequestMethod.POST)
	 public ModelAndView addResource(WordResourceForm formbean,@RequestParam(value="file")CommonsMultipartFile  file){
		
		 //默认保存失败
		 boolean flage = false;
		 ModelAndView mv = new ModelAndView();
		 //1.根据单词id,获取单词
		 Iword word= null;
		 if( BaseForm.validate(formbean.getUuid())){
			word= iwordService.find(formbean.getUuid());
		 }
		 if( null !=word ){
			//1.对文件的格式和大小进行判断
			 if(formbean.validateResourceTypeAndSize(file, formbean.getType())){
				//2.保存文件，返回文件保存路径
				 //单词原名称
				 String fileName = file.getOriginalFilename();
				 //3.资源的保存相对路径
				 String filesavepath=null;
				 try {
					 filesavepath=wordResourceService.saveWordResourceFile(servletContext, file, formbean.getType());
				}catch(ResourceTypeNotExisting se){
					logger.error("上传单词资源：单词资源文件类型未确定!");
				}
				 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					formbean.getErrors().put("error", "上传文件有误！");
					logger.error("上传单词资源：出现错误");
				}
				 
				 if( filesavepath !=null){
					//4.建立资源类保存信息
					 WordResource resource = new WordResource();
					 //5.设置资源所属的单词的id
				     resource.setWordId(word.getId());
					 resource.setName(fileName);//单词原名称
					 resource.setSavepath(filesavepath);//包含文件名的相对保存路径
					 resource.setType(formbean.getType());//资源类型
					 //保存到数据库
					 wordResourceService.save(resource);
					 flage = true;//保存成功
				 }else{
					 formbean.getErrors().put("error", "保存资源文件路径有误!");
				 }
			 }
		 }else{
			 formbean.getErrors().put("error", "该资源对应的单词不存在!");
		 }
		 
		 mv.setViewName(PageViewConstant.MESSAGE);
		 if( flage ){
			 mv.addObject("message","保存成功");
		 }
		 else{
			 mv.addObject("message", formbean.getErrors().get("error"));
		 }
		 //mv.addObject("uuid", word.getUuid());
		 mv.addObject("urladdress",PageViewConstant.generatorMessageLink("admin/control/wordresource/addUI")+"?uuid="+word.getUuid());
		 return mv;
	 }
	 
	public WordResourceService getWordResourceService() {
		return wordResourceService;
	}
	@Resource
	public void setWordResourceService(WordResourceService wordResourceService) {
		this.wordResourceService = wordResourceService;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

	public IwordService getIwordService() {
		return iwordService;
	}
	@Resource
	public void setIwordService(IwordService iwordService) {
		this.iwordService = iwordService;
	}
	
}
