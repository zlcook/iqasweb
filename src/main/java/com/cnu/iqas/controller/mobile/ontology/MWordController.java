package com.cnu.iqas.controller.mobile.ontology;
/**
* @author 周亮 
* @version 创建时间：2016年1月27日 下午7:21:20
* 类说明
*/

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.utils.JsonTool;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/mobile/ios/word/")
public class MWordController {
	//本体操作服务接口
	private OntologyManage ontologyManage;
	/**
	 * 根据单词查看器所有属性值
	 * @param word
	 * @return
	 */
	@RequestMapping(value="findWordProperty")
	public ModelAndView findWordProperty(String word){
		MyStatus status =new MyStatus();
		//存放所有实体的json对象
		List<JSONObject>  listJson = new ArrayList<>();
		try {
			   if( word !=null && !word.trim().equals("")){
				   ResultSet wordResults = ontologyManage.QueryIndividual(word);
					if (wordResults.hasNext()) {
						//获取一条实体记录
						QuerySolution solutionEach = wordResults.next();
						//封装成实体 
						PropertyEntity pe =PropertyEntity.generatePropertyEntity(solutionEach);
						//生成json对象
						JSONObject peJson = JSONObject.fromObject(pe);
						listJson.add(peJson);
					} 
			   }else{
				   status.setStatus(StatusConstant.PARAM_ERROR);
					status.setMessage("请输入查看的单词！");
			   }
			
		}catch (Exception e) {
			e.printStackTrace();
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常");
		}
		return JsonTool.generateModelAndView(listJson, status);
	}
	
	
	public OntologyManage getOntologyManage() {
		return ontologyManage;
	}
	@Resource
	public void setOntologyManage(OntologyManage ontologyManage) {
		this.ontologyManage = ontologyManage;
	}
	
	
}
