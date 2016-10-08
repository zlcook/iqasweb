package com.cnu.iqas.controller.web.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnu.offline.bean.OffLineBag;
import com.cnu.offline.service.OffLineBagService;

/**
* @author 周亮 
* @version 创建时间：2016年9月18日 上午11:04:35
* 类说明:主题离线包管理类
*/
@Controller
@RequestMapping(value="/admin/control/offlinebag/")
public class OffLineBagManageController {


	private static final Logger logger= LogManager.getLogger(OffLineBagManageController.class);
	private OffLineBagService offLineBagService;
	/**
	 * 根据主题编号查询该主题下所有离线包
	 * @param themenumber
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(String themenumber,Model model){
		
		List<OffLineBag> list = offLineBagService.listMaster(themenumber);
		model.addAttribute("list", list);
		return "";
	}
	public OffLineBagService getOffLineBagService() {
		return offLineBagService;
	}
	@Resource
	public void setOffLineBagService(OffLineBagService offLineBagService) {
		this.offLineBagService = offLineBagService;
	}
	
}
