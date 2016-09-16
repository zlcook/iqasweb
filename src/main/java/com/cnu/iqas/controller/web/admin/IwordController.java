package com.cnu.iqas.controller.web.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.PageView;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.VersionWordCount;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.controller.web.ontology.FileController;
import com.cnu.iqas.exception.IwordExistException;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.formbean.iword.IwordForm;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.utils.WebUtils;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 下午10:39:54
* 类说明 单词控制类，包含功能：
* 1.跳转到单词添加界面
* 2.添加单个单词
* 3.跳转到单词列表界面
* 4.分页查询并显示单词
* 5.从execl中导入单词
* 6.不同教材的单词个数统计
*/
@Controller
@RequestMapping(value="/admin/control/word/")
public class IwordController {
	//日志类
	private final static Logger logger = LogManager.getLogger(IwordController.class);
	/**
	 * 操作单词的服务类
	 */
	private IwordService iwordService;
	
	/**
	 * 单词列表界面
	 * @return
	 */
	@RequestMapping(value="listUI")
	public String listUI(){
		return PageViewConstant.WORD_LIST_UI;
	}
	/**
	 * 添加一个单词界面
	 * @return
	 */
	@RequestMapping(value="addUI")
	public String addUI(){
		return PageViewConstant.WORD_ADD_UI;
	}
	/**
	 * 从execl中导入单词界面
	 * @return
	 */
	@RequestMapping(value="importUI")
	public String importUI(){
		
		return PageViewConstant.WORD_IMPORT_WORD_UI;
	}
	/**
	 * 统计每个版本有多少单词
	 * @return
	 */
	@RequestMapping(value="statistics")
	public ModelAndView statisticsVersion(){
		ModelAndView mv = new ModelAndView(PageViewConstant.WORD_STATISTIC);
		List<VersionWordCount> list =iwordService.statisticsVersionAndWordCount();
		mv.addObject("list",list);
		return mv;
	}
	/**
	 * 该方法为完成读取Excel中的数据并将数据插入到对应的数据库表中的操作
	 * @param file execl文件
	 * @return
	 */
	@RequestMapping(value="importword")
	public ModelAndView importword( @RequestParam("wordfile") CommonsMultipartFile  file){
		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		
		if( !file.isEmpty()){	
				HSSFWorkbook hssfWorkbook=null;
				 /** 判断文件的类型，是2003还是2007 */  
				  
	            boolean isExcel2003 = false;  
	            if (WebUtils.isExcel2003(file.getName()))  
	            {  
	                isExcel2003 = true;  
	            }  
				try {
					 /** 根据版本选择创建Workbook的方式 */  
					  
		            Workbook wb = null;  
		            if (isExcel2003)  
		            {  
		                wb = new HSSFWorkbook(file.getInputStream());  
		            }else{  
		                wb = new XSSFWorkbook(file.getInputStream());  
		            }  
						//hssfWorkbook = new HSSFWorkbook(file.getInputStream());
						// 只取第一页工作表Sheet
						//得到第一页
						Sheet sheet = wb.getSheetAt(0);
						if (sheet != null) {
							//批量保存，每次保存数据放在iwords中
							List<Iword> iwords = new ArrayList<Iword>();
							//获得当前页的行数
							// 循环行Row
							int allrows =sheet.getLastRowNum();
							System.out.println("共："+allrows+"行");
							mv.addObject("message", "录入成功,共录入"+allrows+"条数据!");
							for (int rowNum = 2; rowNum <= allrows; rowNum++) {
								System.out.println("第--："+rowNum+"行");
								//一行数据
								Row row = sheet.getRow(rowNum);
								if (row != null) {
									//设置第一列、第二列为string类型
									if(row.getCell(0) != null)
										row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
									if(row.getCell(1) != null)
										row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
									//读出第一列、第二列数据
									String id=row.getCell(0).getStringCellValue();
									String content=row.getCell(1).getStringCellValue();
									
									//进行基本的校验
									if(!IwordForm.validateWord(id,content)){
										int errorline= rowNum+1;
										logger.error("第"+errorline+"行内容格式有误!");
										mv.addObject("message", "第"+errorline+"行内容格式有误!");
										break;
									}
									
									//保存---------------此处应该批量保存
									Iword word = new Iword(id, content);
									iwords.add(word);
									try {
										if( 20<allrows){
											//总行数大于20行，则每20行保存一次
											if( rowNum%20==0){
												//批量保存
												iwordService.batchSave(iwords);
												iwords.clear();  //清空集合
											}else{
												if( allrows==rowNum ){
													//批量保存
													iwordService.batchSave(iwords);
													iwords.clear();  //清空集合
												}
											}
										}else{
											//总行数等于小于20行一次保存
											if( rowNum==allrows ){
												iwordService.batchSave(iwords);
												iwords.clear();  //清空集合
											}
										}
									}catch (DataIntegrityViolationException e) {
										// TODO Auto-generated catch block
										//e.printStackTrace();
										logger.error("单词"+content+"，"+"id："+id+"已经存在!");
										mv.addObject("message", "单词"+content+"，"+"id："+id+"已经存在!");
										break;
									}
								}
							}
						}else{  //第一页为空
							logger.error("导入单词：没有内容");
							mv.addObject("message", "没有内容");
						}
					
				} catch (IOException e1) {
					logger.error("导入单词：读取上传的单词execl文件有误!");
					e1.printStackTrace();
					mv.addObject("message", "读取上传的单词execl文件有误!");
					
				}catch(NumberFormatException ne){
					logger.error("导入单词：内容格式有误!");
					mv.addObject("message", "内容格式有误!");
					ne.printStackTrace();
				}catch(IllegalArgumentException ie){
					logger.error("导入单词：请上传execl文件!");
					mv.addObject("message", "请上传execl文件!");
					ie.printStackTrace();
				}catch (Exception e) {
					logger.error("导入单词：未知异常");
					mv.addObject("message", "未知异常!");
					e.printStackTrace();
				}
		}else{
			mv.addObject("message", "文件不存在!");
		}
		mv.addObject("urladdress",PageViewConstant.generatorMessageLink("admin/control/word/importUI"));
		return mv;
	}
	/**
	 * 手动添加一个单词
	 * @param formbean
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="add")
	public ModelAndView addSingle(@Valid IwordForm formbean,BindingResult bindingResult){
		ModelAndView mv = new ModelAndView(PageViewConstant.WORD_ADD_UI);
		if( !bindingResult.hasErrors()){
			Iword word = new Iword();
			formbean.generateid();
			//获取最新
			WebUtils.copyBean(word,formbean);
			System.out.println(word.toString());
			iwordService.save(word);
		}else{
			formbean.getErrors().put("result", "信息有误");
			mv.addObject("formbean", formbean);
			System.out.println("有错误");
		}
		return mv;
	}
	
	/**
	 * 通过单词内容来查询单词
	 * @return
	 */
	/*@RequestMapping(value="/findbycontent")
	public ModelAndView findByWord(IwordForm formbean){
		ModelAndView mv = new ModelAndView("share/json");
		if(BaseForm.validate(formbean.getContent())){
			PageView<Iword> pageView = new PageView<Iword>(5, formbean.getPage());
			//根据创建日期降序
			LinkedHashMap<String,String> orderby= new LinkedHashMap<String, String>();
			orderby.put("createtime", "desc");
			//查询条件
			StringBuilder sb = new StringBuilder();
			List<Object> params = new ArrayList<Object>();
			params.add(formbean.getContent());
			sb.append(" o.content = ?");
			
			pageView.setQueryResult(iwordService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(), sb.toString(),params.toArray(),orderby));
			
			mv.addObject("pageView", pageView);
			List<Iword> list =pageView.getRecords();
			for(Iword word :list)
				System.out.println(word.getContent()+":"+word.getId());
		}else{
			System.out.println("查询单词不能为空!");
		}
		return mv;
	}*/
	/**
	 * 分页查询单词，根据时间降序排序
	 * @param formbean
	 * @return
	 */
	@RequestMapping(value="list")
	public ModelAndView list(IwordForm formbean){
		ModelAndView mv = new ModelAndView(PageViewConstant.WORD_LIST_UI);
		PageView<Iword> pageView = new PageView<Iword>(formbean.getMaxresult(), formbean.getPage());
		//根据创建日期降序
		LinkedHashMap<String,String> orderby= new LinkedHashMap<String, String>();
		orderby.put("createtime", "desc");
		//查询条件
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		//根据单词内容查询
		if( BaseForm.validate(formbean.getContent())){
			params.add(formbean.getContent());
			sb.append(" o.content = ?");
		}else{
			//根据版本信息查询
			String queryversion=formbean.queryversion();
			//System.out.println(queryversion+"    版本信息");
			if( BaseForm.validate(queryversion)){
				params.add(queryversion+"%");
				sb.append(" o.id like ?");
				//System.out.println(queryversion);
			}
		}
		
		pageView.setQueryResult(iwordService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(), sb.toString(),params.toArray(),orderby));
		mv.addObject("formbean",formbean);
		mv.addObject("pageView", pageView);
		
		return mv;
	}
	
	public IwordService getIwordService() {
		return iwordService;
	}
	@Resource
	public void setIwordService(IwordService iwordService) {
		this.iwordService = iwordService;
	}
	
	
}
