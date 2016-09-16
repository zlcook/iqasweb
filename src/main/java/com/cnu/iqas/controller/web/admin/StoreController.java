package com.cnu.iqas.controller.web.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.base.PageView;
import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.Commodity;
import com.cnu.iqas.bean.store.CommodityType;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.formbean.BaseForm;
import com.cnu.iqas.formbean.store.StoreForm;
import com.cnu.iqas.service.stroe.CommodityService;
import com.cnu.iqas.service.stroe.CommodityTypeService;
import com.cnu.iqas.service.stroe.StoreService;
import com.cnu.iqas.utils.PropertyUtils;
import com.cnu.iqas.utils.WebUtils;

import net.sf.json.JSONObject;

/**
* @author 周亮 
* @version 创建时间：2015年12月18日 下午2:17:04
* 类说明 商店控制类，包含功能：
* 1.添加商品类型
* 2.设置商品类型不可见
* 3.设置商品类型可见
* 4.修改商品类型名称
* 5.分页查询所有商品类型
* 6.分页查询某个商品类型下的商品
* 7.为某个商品类型添加商品
* 8.设置商品不可见
* 9.设置商品可见
* 10.修改商品名称
* 11.修改商品价格
*/
@Controller
@RequestMapping(value="/admin/control/store/")
public class StoreController   implements ServletContextAware{
	
	private Logger log = LogManager.getLogger(StoreController.class);
	 //应用对象
	 private ServletContext servletContext;
	/**
	 * 商品类型服务接口
	 */
	private CommodityTypeService commodityTypeService;
	/**
	 * 商品服务接口
	 */
	private CommodityService commodityService;
	/**
	 * 商店服务类，用于同时操作商品和商品类型
	 */
	private StoreService storeService;

	
	/**
	 * 获取某个商品类型下的所有商品
	 * @param grade 商品类型id
	 * @return
	 */
	@RequestMapping(value="getCommoditysOfType")
	public ModelAndView getCommoditysOfType(StoreForm formbean){

		ModelAndView mv = new ModelAndView(PageViewConstant.COMMODITYTYPE_COMMODITY_LIST);
		//构造页面类
		PageView<Commodity> pv = new PageView<Commodity>(formbean.getMaxresult(), formbean.getPage());
		//构造查询条件
		String wherejpql = "o.typeid=?";
		List<Object> queryParams  = new ArrayList<Object>();
		queryParams.add(formbean.getId());
		//构造排序条件
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createTime", "desc");
		//查询
		List<Commodity> query= commodityService.getAllData(wherejpql, queryParams.toArray(), orderby);
		//设置到页面类中
		pv.setRecords(query);
		
		mv.addObject("pageView", pv);

		CommodityType type = commodityTypeService.find(formbean.getId());
		mv.addObject("type", type);
		return mv;
	}
	
	/**
	 * 为某个商品类型添加商品
	 * @param formbean 存放商品信息表单
	 * @param file  商品图片文件
	 * @return
	 */
	@RequestMapping(value="addCommodity")
	public ModelAndView addCommodityForType(StoreForm formbean,@RequestParam(value="file")CommonsMultipartFile  file){
		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		mv.addObject("urladdress", PageViewConstant.generatorMessageLink("admin/control/store/addCommodityUI")+"?typeid="+formbean.getTypeid());
		Commodity commodity = new Commodity();
		//保存商品，并获取商品的保存路径
		String relativepath =null;
		//商品图片的文件名和格式
		String fileName = file.getOriginalFilename();
		String fileContentType = file.getContentType();
		//商品类型是否存在
		CommodityType type = commodityTypeService.find(formbean.getTypeid());
		if( type ==null){
			mv.addObject("message", "商品类型不存在!");
			return  mv;
		}
		//判断上传图片类型
		if(!BaseForm.validateImageFileType(fileName, fileContentType)){
			mv.addObject("message", "上传图片格式有误!");
			return  mv;
		}
		//判断商品名称是否已经存在
		if( commodityService.findByName(formbean.getName())!=null){
			mv.addObject("message", "商品名称已存在!");
			return  mv;
		}
		//获取文件保存的相对路径
		String relativedir= PropertyUtils.getFileSaveDir(PropertyUtils.FIRST_COMMODITY_PIC);
		if( !BaseForm.validate(relativedir)){
			mv.addObject("message", "商品图片存放的相对路径有误!");
			log.error("商品图片存放的相对路径有误!");
			//使用项目log图片
			relativedir=PropertyUtils.getFileSaveDir(PropertyUtils.LOG);
		}
		try {
			relativepath=formbean.saveFile(servletContext, relativedir, file);
			
			if( relativepath!=null){
				//设置商品参数
				WebUtils.copyBean(commodity, formbean);
				commodity.setExt(BaseForm.getExt(fileName));
				commodity.setSavePath(relativepath);
				commodity.setPicSize(file.getSize());
				//商品类型的商品数加一
				type.setCount(type.getCount()+1);
				//这样可以保持事务性
				storeService.saveCommodityAndUpdateType(commodity, type);
				mv.addObject("message", "保存商品成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存商品图片失败:"+e.getMessage());
			mv.addObject("message", "保存商品失败!");
		}
		return mv;
	}
	
	/**
	 * 添加商品界面
	 * @param typeid  商品类型id
	 * @return
	 */
	@RequestMapping(value="addCommodityUI")
	public ModelAndView addCommodityUI(String typeid){
		ModelAndView mv = new ModelAndView(PageViewConstant.COMMODITY_ADD_UI);
		CommodityType type = commodityTypeService.find(typeid);
		mv.addObject("type", type);
		return mv;
	}
	
	/**
	 * 添加商品类型界面
	 * @return
	 */
	@RequestMapping(value="addTypeUI")
	public String addCommodityTypeUI(){
		
		return PageViewConstant.COMMODITYTYPE_ADD_UI;
	}
	
	/**
	 * 添加商品类型 
	 * @param name 商品类型名
	 * @return
	 */
	@RequestMapping(value="addType")
	public ModelAndView addCommodityType(StoreForm formbean){
		
		ModelAndView mv = new ModelAndView(PageViewConstant.MESSAGE);
		
		if( BaseForm.validate(formbean.getName())){
			if( commodityTypeService.findByName(formbean.getName())==null){
				CommodityType type = new CommodityType();
				type.setName(formbean.getName());
				type.setGrade(formbean.getGrade());
				commodityTypeService.save(type);
				mv.addObject("message", "添加成功");
				
			}else{
				mv.addObject("message", "商品类型已存在!");
			}
		}else{
			mv.addObject("message", "请填写商品类型名称");
		}
		
		mv.addObject("urladdress",PageViewConstant.generatorMessageLink("admin/control/store/addTypeUI"));
		return mv;
	}
	/**
	 * 分页查看所有商品类型
	 * @return 
	 */
	@RequestMapping(value="listtype")
	public ModelAndView listType(StoreForm formbean){
		ModelAndView mv = new ModelAndView(PageViewConstant.COMMODITYTYPE_LIST);
		//建立页面类，并初始化每页最大页数
		PageView<CommodityType> pv = new PageView<CommodityType>(formbean.getMaxresult(),formbean.getPage());
		//查询结果按时间降序排列
		LinkedHashMap<String, String> order = new LinkedHashMap<String, String>();
		order.put("createTime", "desc");
		//查询
		QueryResult<CommodityType> query= commodityTypeService.getScrollData(pv.getFirstResult(), pv.getMaxresult(),order);
		//查询结果存到页面类中
		pv.setQueryResult(query);
		//页面类存放到request中
		mv.addObject("pageView", pv);
		return mv;
	}
	/**
	 * 根据商品类型id使商品类型无效
	 * @param id
	 * @return
	 */
	@RequestMapping(value="disableType")
	public String disableType(String id){

		commodityTypeService.makeVisible(id,false);
	
		return PageViewConstant.generatorRedirect("admin/control/store/listtype") ;
	}
	
	/**
	 * 根据商品类型id使商品类型有效
	 * @param id
	 * @return
	 */   
	@RequestMapping(value="enableType")
	public String enableType(String id){
		commodityTypeService.makeVisible(id,true);
		
		return PageViewConstant.generatorRedirect("admin/control/store/listtype");
	}

	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	public String disableCommodity(String id){
		
		return "";
	}
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	public String deleteCommodity(String id){
		
		MyStatus status = new MyStatus();
		JSONObject json = new JSONObject();
		
		boolean flage = true;
		//1.校验id是否正确，
		if( BaseForm.validate(id)){
			//2.调用服务方法删除商品，同时商品类型包含商品数减1.
			flage =storeService.deleteCommodity(id);
		}
		
		if( !flage ){
			status.setStatus(0);
			status.setMessage("删除失败!");
		}
			
		
		
		//3.删除成功返回正确
		
		
		
		return "";
	}
	/**
	 * 恢复商品
	 * @param id
	 * @return
	 */
	public String enableCommodity(String id){
		
		return "";
	}
	public CommodityTypeService getCommodityTypeService() {
		return commodityTypeService;
	}
	@Resource
	public void setCommodityTypeService(CommodityTypeService commodityTypeService) {
		this.commodityTypeService = commodityTypeService;
	}

	public CommodityService getCommodityService() {
		return commodityService;
	}

	@Resource
	public void setCommodityService(CommodityService commodityService) {
		this.commodityService = commodityService;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
		
	}

	public StoreService getStoreService() {
		return storeService;
	}
	@Resource
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	
	
}
