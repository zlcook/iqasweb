
package com.cnu.iqas.controller.web.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.base.PageView;
import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.formbean.iword.WordThemeForm;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.service.iword.WordThemeService;
import com.cnu.iqas.service.iword.WordThemeWordRelService;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.offline.bean.OffLineWordXml;
import com.cnu.offline.service.OfflineService;

/**
* @author 周亮 
* @version 创建时间：2015年12月8日 上午10:13:13
* 类说明   单词主题控制类，功能有：
* 添加主题、
* 查询所有主题、
* 删除主题、
* 编辑主题、
* 为主题添加单词、
* 查询主题下的所有单词、
* 删除主题下的某个单词
*/
@Controller
@RequestMapping(value="admin/control/wordtheme/")
public class WordThemeController implements ServletContextAware{
	private ServletContext servletContext;
	//主题服务类
	private WordThemeService wordThemeService; 
	//主题和单词关系服务类
	private WordThemeWordRelService wordThemeWordRelService;
	/**
	 * 单词服务类
	 */
	private IwordService iwordService;
	private OfflineService offlineService;
	/**
	 * 根据主题编号下载主题
	 * @param number
	 * @param response
	 */
	@RequestMapping(value="down")
	public void  downOffline(String number,HttpServletResponse response){
		
		WordTheme theme =wordThemeService.findByNumber(number);
		if(theme!=null){
			OffLineWordXml owx= offlineService.findNewByThemeId(theme.getId());
			if( owx!=null){
				//1.获取文件系统的根路径:D:/Soft/nsystem/
				String fileSystemRoot =PropertyUtils.get(PropertyUtils.IQASWEB_FILE_SYSTEM_DIR);
				//2.生成文件的绝对路径:D:/Soft/system/news/files/报名.doc
				String fileSavePath = fileSystemRoot+owx.getSavePath();
				org.springframework.core.io.Resource fileResource =offlineService.getFileResource("file:"+fileSavePath);
				BaseForm.loadFile(response, fileResource,true);
				owx.setCount(owx.getCount()+1);
				offlineService.update(owx);
			}
		}
	
	}
	/**
	 * 创建离线文件
	 * @param number 主题编号
	 * @param parentContent  父类主题名称
	 * @param content  子类主题名称
	 * @return
	 */
	@RequestMapping(value="createOffline")
	public String createOffline(String number,String parentContent,String content){
	
		//String number="2-17-58";
		WordTheme theme =wordThemeService.findByNumber(number);
		String[] numbers= number.split("-");
		//构造 "17.旅游与交通-（58）交通运输方式";
		StringBuffer sb = new StringBuffer();
		sb.append(numbers[1]).append(".").append(parentContent).append("-（").append(numbers[2]).append("）").append(content);
		theme.setContent(sb.toString());
		//根据主题生成xml
		OffLineWordXml owx= offlineService.createByThme(theme);
		offlineService.save(owx);
		
		return "redirect:/admin/control/wordtheme/list.html?id="+theme.getParentId();
	}
	
	@RequestMapping(value="ajaxGetOffline")
	public ModelAndView ajaxOffline(String themeId){
		
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		MyStatus status= new MyStatus();
		List<OffLineWordXml> listowx= offlineService.findByThemeId(themeId);
		
		mv.addObject("json",JsonTool.createJson(listowx, status));
		return mv;
	}

	/**
	 * 跳转到添加主题界面
	 * @param parentId 父类id
	 * @param model 存放返回给页面端数据
	 * @return
	 */
	@RequestMapping(value="addUI")
	public String addThemeUI(String parentId,Model model){
		//转发到添加界面，构造导航栏
		Map<String,String> urlParams= generatorNavigation(parentId);
		model.addAttribute("urlParams",urlParams);
		model.addAttribute("parentId",parentId);
		
		return PageViewConstant.WORDTHEME_ADD_UI;
	}
	/**
	 * 跳转到导入主题界面
	 * @param model 存放返回给页面端数据
	 * @return
	 */
	@RequestMapping(value="importUI")
	public String addThemeUI(){
		return PageViewConstant.WORDTHEME_IMPORT_UI;
	}
	/**
	 * 该方法为从Excel导入主题
	 * @param file execl文件
	 * @return
	 */
	@RequestMapping(value="importtheme")
	public ModelAndView importword( @RequestParam("themefile") CommonsMultipartFile  themefile){
		//标识导入是否成功
		boolean importSuccess= true;
		ModelAndView mv = new ModelAndView();
		if( !themefile.isEmpty()){	
				HSSFWorkbook hssfWorkbook=null;
				 /** 判断文件的类型，是2003还是2007 */ 
	            boolean isExcel2003 = false;  
	            if (WebUtils.isExcel2003(themefile.getName()))  
	            {  
	                isExcel2003 = true;  
	            }  
				try {
					 /** 根据版本选择创建Workbook的方式 */ 
		            Workbook wb = null;  
		            if (isExcel2003)  
		            {  
		                wb = new HSSFWorkbook(themefile.getInputStream());  
		            }else{  
		                wb = new XSSFWorkbook(themefile.getInputStream());  
		            }  
					// 只取第一页工作表Sheet
					//得到第一页
					Sheet sheet = wb.getSheetAt(0);
					if (sheet != null) {
						//获得当前页的行数
						//循环行Row
						int allrows =sheet.getLastRowNum();
						mv.addObject("message", "录入成功,共录入"+allrows+"条数据!");
						String savePath = PropertyUtils.get(PropertyUtils.LOG);
						for (int rowNum = 1; rowNum <= allrows; rowNum++) {
							System.out.println("第--："+rowNum+"行");
							//一行数据
							Row row = sheet.getRow(rowNum);
							if (row != null) {
								//设置第一列、第二列,3,4列为string类型
								if(row.getCell(0) != null)
									row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
								if(row.getCell(1) != null)
									row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
								if(row.getCell(2) != null)
									row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
								if(row.getCell(3) != null)
									row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
								//读出第一列、第二列,3,4列数据;
								String parentNumber=row.getCell(0).getStringCellValue();
								String number=row.getCell(1).getStringCellValue();
								String content=row.getCell(2).getStringCellValue();
								String english=row.getCell(3).getStringCellValue();
								
								System.out.println(parentNumber+" : "+number+" : "+content+" : "+english);
								WordTheme theme = new WordTheme();
								theme.setContent(content);
								theme.setEnglish(english);
								theme.setNumber(number);
								//如果父类字段为0则该主题为一级主题，没有父类
								if( parentNumber.trim().equals("0"))
								  theme.setParentId(null);
								else{
									//查询该父类主题的id
									WordTheme parentTheme =wordThemeService.findByNumber(parentNumber);
									theme.setParentId(parentTheme.getId());
								}
								theme.setPicturePath(savePath);
								wordThemeService.save(theme);
								
							}
						}
					}else{  //第一页为空
						mv.addObject("error", "没有内容");
						importSuccess=false;
					}
				}catch(Exception e){
					e.printStackTrace();
					mv.addObject("error", e.getMessage());
					importSuccess=false;
				}
		}else{
			mv.addObject("error", "文件不存在!");
			importSuccess=false;
		}
		//导入成功
		if(importSuccess){
			mv.setViewName("redirect:/admin/control/wordtheme/list.html");
		}else{ 
			//导入失败
			mv.setViewName(PageViewConstant.WORDTHEME_IMPORT_UI);
		}
		return mv;
	}
	
	/**
	 * 跳转到编辑主题界面
	 * @param id    编辑主题id
	 * @param model 存放返回给页面端数据
	 * @return
	 */
	@RequestMapping(value="editUI")
	public String editThemeUI(String id,Model model){
		WordTheme theme=wordThemeService.find(id);
		if( theme!=null){
			//转发到添加界面，构造导航栏
			Map<String,String> urlParams= generatorNavigation(theme.getParentId());
			model.addAttribute("urlParams",urlParams);
			
			WordThemeForm formbean = new WordThemeForm();
			WebUtils.copyBean(formbean, theme);
			model.addAttribute("formbean",formbean);
		}else{
			return "redirect:/admin/control/wordtheme/list.html";
		}
		
		return PageViewConstant.WORDTHEME_EDIT_UI;
	}
	/**
	 * 跳转到编辑主题界面
	 * @param id    编辑主题id
	 * @param model 存放返回给页面端数据
	 * @return
	 */
	@RequestMapping(value="edit")
	public ModelAndView editTheme(WordThemeForm formbean,@RequestParam("logo") CommonsMultipartFile  logo){
		//标识，true更新数据有效，false:无效
		boolean dataValid = true;
		ModelAndView mv = new ModelAndView();
		//1.校验主题名称信息是否完善
			//2.查询主题
			WordTheme theme=wordThemeService.find(formbean.getId());
			if( theme!=null){
				//3.保存图片，获取图片保存路径,默认还是直接的图片
				String savePath =theme.getPicturePath();
				//如果上传图片，标识上传图片成功
				boolean uploadOk = true;
				//如果上传图片，怎将图片保存路径换成新的图片
				if( logo!=null && logo.getSize()>0 ){
					//4.图片存在，校验图片格式
					if( BaseForm.validateImageFileType(logo.getOriginalFilename(), logo.getContentType())){
						long imageSizeMax= 1024*500;//500KB
						if( logo.getSize()<=imageSizeMax){
							//5.保存图片,获取主题图片保存的跟路径
							String relativedir = PropertyUtils.getFileSaveDir(PropertyUtils.IOS_WORD_THEME_LOG);
							try {
								//6.获取保存路径
								savePath=BaseForm.saveFile(servletContext, relativedir, logo);
							} catch (Exception e) {
								e.printStackTrace();
								formbean.getErrors().put("error","上传图片失败");
								uploadOk =false;
							}
						}else{
							formbean.getErrors().put("error", "上传图片不能大于500KB");
							uploadOk =false;
						}
					}else{
						formbean.getErrors().put("error", "上传图片格式不对！");
						uploadOk =false;
					}
				}
				//如果上传图片成功或者没上传图片怎更改图片路径
				if(uploadOk){
					theme.setPicturePath(savePath);
					//5.判断新的编号是否重复
					if( !formbean.getNumber().trim().equals(theme.getNumber().trim())){
						if(wordThemeService.findByNumber(formbean.getNumber().trim())!=null){
							formbean.getErrors().put("error", "主题编号已经存在");
							dataValid=false;//更新数据无效
						}else{
							theme.setNumber(formbean.getNumber().trim());
						}
					}
					//6.新的主题内容
					theme.setContent(formbean.getContent().trim());
					//7.设置新的英文意思
					theme.setEnglish(formbean.getEnglish());
				}else{
					dataValid=false;//更新数据无效
				}
			}else{
				formbean.getErrors().put("error", "主题不存在");
				dataValid=false;//更新数据无效
			}
			//更新数据有效
		if( dataValid){
			//更新
			wordThemeService.update(theme);
			//成功，返回列表     redirect
			if(WebUtils.isNull(theme.getParentId()))
			   mv.setViewName("redirect:/admin/control/wordtheme/list.html");
			else
			  mv.setViewName("redirect:/admin/control/wordtheme/list.html?id="+theme.getParentId());
		}else{
			//更新失败，返回编辑界面
			mv.setViewName(PageViewConstant.WORDTHEME_EDIT_UI);
			//图片换成之前的图片
			formbean.setPicturePath(theme.getPicturePath());
			mv.addObject("formbean",formbean);
			//转发到编辑界面，构造导航栏
			Map<String,String> urlParams= generatorNavigation(formbean.getId());
			mv.addObject("urlParams",urlParams);
		}
		return mv;
	}
	/**
	 * 添加单词主题，添加失败会在错误map集合中存入key值为name的错误原因
	 * @return
	 */
	@RequestMapping(value="add")
	public ModelAndView addTheme(WordThemeForm formbean,@RequestParam("logo") CommonsMultipartFile  logo){
		//标识，true添加成功，false:添加失败
		boolean flage = false;
		ModelAndView mv = new ModelAndView();
		//1.校验主题名称信息是否完善
		if( formbean.validateThemeContent()){
			//2.查询主题是否添加过
				//内容不存在，查询编号是否已存在
				WordTheme theme = wordThemeService.findByNumber(formbean.getNumber());
				if( theme==null){

					//3.保存图片，获取图片保存路径,默认系统logo
					String savePath =PropertyUtils.get(PropertyUtils.LOG);
					//如果上传图片，标识上传图片成功
					boolean uploadOk = true;
					if( logo!=null && logo.getSize()>0 ){
						//4.图片存在，校验图片格式
						if( BaseForm.validateImageFileType(logo.getOriginalFilename(), logo.getContentType())){
							long imageSizeMax= 1024*500;//500KB
							if( logo.getSize()<=imageSizeMax){
								//5.保存图片,获取主题图片保存的跟路径
								String relativedir = PropertyUtils.getFileSaveDir(PropertyUtils.IOS_WORD_THEME_LOG);
								try {
									//6.获取保存路径
									savePath=BaseForm.saveFile(servletContext, relativedir, logo);
								} catch (Exception e) {
									e.printStackTrace();
									formbean.getErrors().put("error","上传图片失败");
									uploadOk =false;
								}
							}else{
								formbean.getErrors().put("error", "上传图片不能大于500KB");
								uploadOk =false;
							}
						}else{
							formbean.getErrors().put("error", "上传图片格式不对！");
							uploadOk =false;
						}
					}
					//如果上传图片成功，或者未添加图片都保存
					if(uploadOk){
						theme= new WordTheme();
						//7.将表单数据复制到类中
						WebUtils.copyBean(theme, formbean);
						theme.setPicturePath(savePath);
						if( formbean.getParentId()==null || formbean.getParentId().trim().equals("")){
							theme.setParentId(null);
						}
						//8.保存主题
						wordThemeService.save(theme);
						//.9.添加成功
						flage=true;
					}
				}else{
					formbean.getErrors().put("error", "主题编号已经存在");
				}
		}else{
			formbean.getErrors().put("error", "信息不完善");
		}
		if( flage){
			//上传成功，返回列表     redirect
			if(WebUtils.isNull(formbean.getParentId()))
			   mv.setViewName("redirect:/admin/control/wordtheme/list.html");
			else
			  mv.setViewName("redirect:/admin/control/wordtheme/list.html?id="+formbean.getParentId());
		}else{
			//上传失败，返回添加界面
			mv.setViewName(PageViewConstant.WORDTHEME_ADD_UI);
			mv.addObject("formbean",formbean);
			//转发到添加界面，构造导航栏
			Map<String,String> urlParams= generatorNavigation(formbean.getParentId());
			mv.addObject("urlParams",urlParams);
			mv.addObject("parentId",formbean.getParentId());
		}
		return mv;
	}
	/**
	 * 为主题添加单词
	 * 输入：单词uuid
	 *      主题id
	 * @return
	 *//*
	@RequestMapping(value="addword")
	public ModelAndView addWord4Theme(WordThemeForm formbean){

		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		boolean success = false;
		if( BaseForm.validate(formbean.getId())&& BaseForm.validate(formbean.getUuid())){
			Iword word =iwordService.find(formbean.getUuid());
			WordTheme theme = wordThemeService.find(formbean.getId());
			if( word !=null&& theme!=null){

				WordThemeWordRel entity = new WordThemeWordRel(word.getUuid(), theme.getId());
				wordThemeWordRelService.save(entity);
				success = true;
			}
		}
		if( success){
			mv.addObject("message", "添加成功！");
		}else{
			mv.addObject("message", "添加失败！");
		}
		mv.addObject("urladdress",PageViewConstant.generatorMessageLink("admin/control/wordtheme/addUI"));
		return mv;
	}*/
	/**
	 * 分页获取主题下的单词
	 * 输入：第几页:page
	 * 		主题id:id
	 * @return
	 */
	@RequestMapping(value="wordlist")
	public ModelAndView getWords4Theme(WordThemeForm formbean){

		ModelAndView mv = new ModelAndView(PageViewConstant.WORDTHEME_WORDS_LIST);
		QueryResult<Iword> queryResult=null;
		//分页
		PageView<Iword> pageView = new PageView<Iword>(10, formbean.getPage());
		if( formbean.getId()!=null){
			
		    WordTheme theme=	wordThemeService.find(formbean.getId());
		    if( theme!=null){
			 queryResult= wordThemeService.getWords(formbean.getId(), pageView.getFirstResult(), pageView.getMaxresult());    
			 mv.addObject("theme",theme);
		    }
		}
		//将查询结果存到页面
		pageView.setQueryResult(queryResult);
		mv.addObject("pageView", pageView);
		return mv;
	}
	
	
	
	/**
	 * 根据条件查询主题
	 * @return
	 */
	@RequestMapping(value="list")
	public ModelAndView list(WordThemeForm formbean,HttpServletRequest request){
		ModelAndView mv = new ModelAndView(PageViewConstant.WORDTHEME_LIST);
		//分页
		PageView<WordTheme> pageView = new PageView<WordTheme>(formbean.getMaxresult(), formbean.getPage());
		//查询结果根据时间排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("createTime", "asc");
		//构造查询语句
		StringBuilder wheresql = new StringBuilder();
		//查询条件
		List<Object> params = new ArrayList<Object>();
		//状态查询
		if( formbean.getQvisible()!=0){
			int qvisible = formbean.getQvisible();
			if( qvisible==1){
				//查询“1:已开启”
				wheresql.append(" o.visible = ? ");
				params.add(true);
			}else if( qvisible==2){
				//“2:已屏蔽”
				wheresql.append(" o.visible = ? ");
				params.add(false);
			}
		}
		//非状态查询
		//主题id不为空，则查询该主题下的孩子
		if( !WebUtils.isNull(formbean.getId())){
			if( params.size()>0)
			  wheresql.append(" and ");
			wheresql.append(" o.parentId = ? ");
			params.add(formbean.getId());
		}else{
			if( params.size()>0)
				wheresql.append(" and ");
			//查询一级父类
			wheresql.append(" o.parentId is Null ");
		}
		
		//查询
		QueryResult<WordTheme> qr= wordThemeService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(),wheresql.toString(), params.toArray(), orderby);
		//将查询结果存到页面
		pageView.setQueryResult(qr);
		mv.addObject("pageView", pageView);
		//如果其根据状态查询了，则将状态返回去，翻页是才能保证也是按照该状态来查询的
		mv.addObject("formbean", formbean);
		//构造导航栏
		Map<String,String> urlParams =generatorNavigation(formbean.getId());
		
		//2.传回页面端，导航连接
		mv.addObject("urlParams", urlParams);
		//System.out.println(urlParams+"----------------------------");
		return mv;
	}
	/**
	 * 从该主题开始，生成其父类导航。
	 * @param id 主题id
	 * @return Map，里面存放了父类的id和内容，并且最大的父类在第一位
	 */
	private LinkedHashMap<String ,String> generatorNavigation(String id){
		
		//存放主题id
		List<String>  listIds = new ArrayList<>();
		//存放主题内容
		List<String> listContents= new ArrayList<>();
		if( !WebUtils.isNull(id)){
			//寻找父类
			WordTheme th =wordThemeService.find(id);
			if(th!=null){
				listIds.add(id);
				listContents.add(th.getContent());
			}
			while( th!=null){
				//将主题id，和内容存放导航中
			  String parentId= th.getParentId();
			  if( !WebUtils.isNull(parentId)){
				  //获取父类
				  th = wordThemeService.find(parentId);
				  listIds.add(parentId);
				  listContents.add(th.getContent());
			  }else{
				  th = null;
			  }
			}
		}
		
		//将存储顺序反过来，第一个存放最大的父类
		LinkedHashMap<String ,String> orderurlParams=new LinkedHashMap<>();
		for(int i = listIds.size()-1;i>=0;i--){
			orderurlParams.put(listIds.get(i), listContents.get(i));
		}
		return orderurlParams;
	}
	
	 /**
	  * 通过ajax根据主题id编辑主题的名称
	  * @return
	  */
	 @SuppressWarnings("finally")
	@RequestMapping(value="ajaxeditContent")
	 public ModelAndView ajaxeditName(WordThemeForm formbean){
		 ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		 MyStatus status = new MyStatus();
		 try {
			if(formbean.validateIdAndContent()){
				  WordTheme wt= wordThemeService.find(formbean.getId());
				 wt.setContent(formbean.getContent().trim());
				 //更新
				 wordThemeService.update(wt);
			 }else{
				 status.setMessage("修改失败，注意检查内容长度哦!");
				 status.setStatus(0);
			 }
		} catch (Exception e) {
			e.printStackTrace(); 
			status.setMessage("哎呀，修改失败了!");
			status.setStatus(0);
		}finally{
			 mv.addObject("json", JsonTool.createJson(null, null, status));
			 return mv;
		}
	 }
	 /**
	 * 根据主题id删除主题
	 * @param id 主题id
	 * @return
	 */
	@RequestMapping(value="ajaxDelete")
	public ModelAndView ajaxDeleteTheme(String id){
		 ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		 MyStatus status = new MyStatus();
		 try {
			if(!WebUtils.isNull(id)){
				//判断该主题下是否还存在孩子，如果存在则不能删除
				String wherejpql="o.parentId=?";
				List<Object> listParams= new ArrayList<>();
				listParams.add(id);
				//查询孩子
				List<WordTheme> listThemes = wordThemeService.getWordThemes(wherejpql, listParams.toArray());
				//不存在孩子则删除
				if( listThemes ==null || listThemes.size()<=0)
				 wordThemeService.delete(id);
				else{
					//提示
					status.setMessage("该主题包含子主题不能删除!");
					status.setStatus(0);
				}
			 }else{
				 status.setMessage("确定删除主题的id");
				 status.setStatus(0);
			 }
		} catch (Exception e) {
			e.printStackTrace(); 
			status.setMessage(e.getMessage());
			status.setStatus(0);
		}
		 return JsonTool.generateModelAndView(status);
	}
	

	 /**
		 * 根据主题id屏蔽主题
		 * @param id 主题id
		 * @return
		 */
		@RequestMapping(value="ajaxDisable")
		public ModelAndView ajaxDisableTheme(String id){
			 ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
			 MyStatus status = new MyStatus();
			 try {
				if(!WebUtils.isNull(id)){
					//查询该主题
					WordTheme theme= wordThemeService.find(id);
					if( theme!=null){
						theme.setVisible(false);
						wordThemeService.update(theme);
					}else{
						status.setMessage("该主题不存在!");
						status.setStatus(0);
					}
					
				 }else{
					 status.setMessage("确定屏蔽主题的id");
					 status.setStatus(0);
				 }
			} catch (Exception e) {
				e.printStackTrace(); 
				status.setMessage(e.getMessage());
				status.setStatus(0);
			}
			 return JsonTool.generateModelAndView(status);
		}
		 /**
		 * 根据主题id开启主题
		 * @param id 主题id
		 * @return
		 */
		@RequestMapping(value="ajaxEnable")
		public ModelAndView ajaxEnableTheme(String id){
			 ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
			 MyStatus status = new MyStatus();
			 try {
				 if(!WebUtils.isNull(id)){
						//查询该主题
						WordTheme theme= wordThemeService.find(id);
						if( theme!=null){
							theme.setVisible(true);
							wordThemeService.update(theme);
						}else{
							status.setMessage("该主题不存在!");
							status.setStatus(0);
						}
						
					 }else{
						 status.setMessage("确定开启主题的id");
						 status.setStatus(0);
					 }
			} catch (Exception e) {
				e.printStackTrace(); 
				status.setMessage(e.getMessage());
				status.setStatus(0);
			}
			 return JsonTool.generateModelAndView(status);
		}

	public WordThemeService getWordThemeService() {
		return wordThemeService;
	}
	@Resource
	public void setWordThemeService(WordThemeService wordThemeService) {
		this.wordThemeService = wordThemeService;
	}

	public WordThemeWordRelService getWordThemeWordRelService() {
		return wordThemeWordRelService;
	}

	@Resource
	public void setWordThemeWordRelService(WordThemeWordRelService wordThemeWordRelService) {
		this.wordThemeWordRelService = wordThemeWordRelService;
	}

	public IwordService getIwordService() {
		return iwordService;
	}
	@Resource
	public void setIwordService(IwordService iwordService) {
		this.iwordService = iwordService;
	}
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
	public OfflineService getOfflineService() {
		return offlineService;
	}
	@Resource
	public void setOfflineService(OfflineService offlineService) {
		this.offlineService = offlineService;
	}
	
}
