package com.cnu.iqas.controller.mobile.ios;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordAttributeResource;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.ResourceConstant;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.enumtype.WordAttributeEnum;
import com.cnu.iqas.service.common.IFindWordInNoumenon;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.service.iword.WordAttributeResourceService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.WebUtils;
import com.cnu.iqas.vo.mobile.ios.WordVoManage;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

import net.sf.json.JSONObject;

/**
* @author 周亮 
* @version 创建时间：2016年3月2日 下午7:48:51
* 类说明
*/
@Controller
@RequestMapping(value="/mobile/ios/word/")
public class SWordController {

	//本体操作服务接口
	private OntologyManage ontologyManage;
	/**
	 * 查询单词本身资源服务
	 */
	private WordResourceService wordResourceService;
	/**
	 * 查询单词相关属性资源服务接口
	 */
	private WordAttributeResourceService wordAttributeResourceService;
/**
 *    
 * @param text  查询的单词
 * @return
{
  "result": {
    "word": {
      "wordId": "5/3/5/4",
      "word": "boat",
      "meaning": "ship(船)@1.There is a boat on the lake.@1.Look! There is a boat on the lake. 2.I like boats.@A:How are you going to the island?(你们怎么去岛上？) B:We're going by boat.(我们坐船去。)",
      "picture": "ifilesystem/noumenon/wordresource/images/1ea15867-e6c4-4b44-9f3c-0c54d46f424b.jpg",
      "dialogue": "ifilesystem/noumenon/wordresource/images/574bc948-cccb-4052-8681-3985739f289c.jpg@ifilesystem/noumenon/wordresource/images/c5dba7f9-5c50-4472-bda2-fe4ddf9bebb5.jpg@ifilesystem/noumenon/wordresource/images/bb81a8c4-6812-4c16-92bc-4f4857eaddca.jpg"
    }
  },
  "status": 1,
  "message": "ok"
}
 */
	@RequestMapping(value="findWord")
	public ModelAndView findWord(String text){
		//分隔符
		 String SPLIT_CHAR="@";
		//单词json
		JSONObject wordJson=null;
		MyStatus status = new MyStatus();
		
		if( !WebUtils.isNull(text)){
				//存放不同版本的单词
				List<PropertyEntity> listPes=null;
				try {
					//从本体中查询出不同版本的单词
					// 查该单词对应所有ID的结果集
					ResultSet resultsIdofOneWord= ontologyManage.QueryAWordAllId(text);
					//QueryIndividualAllId(text);
					listPes = new ArrayList<PropertyEntity>();
					if (resultsIdofOneWord.hasNext()) {
						//将多个版本的相同单词封装成json
						while (resultsIdofOneWord.hasNext()) {
							QuerySolution solutionInstance = resultsIdofOneWord.next();
							//找出该单词的对应的所有ID
							ResultSet resultsAllPropertyOfThisId = ontologyManage.QueryIndividualDependOnId(solutionInstance.get("?propertyID").toString());
							QuerySolution solutionAllPropertyOfThisId = resultsAllPropertyOfThisId.next();
							//从本体中解析出一个包含单词的23个属性的实体
							PropertyEntity pe =PropertyEntity.generatePropertyEntity(solutionAllPropertyOfThisId);
							listPes.add(pe);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					status.setExecptionStatus(e);
				}
				//将多个版本的单词整合成一个单词里
				WordVoManage wv =WordVoManage.generateWordVoManage(listPes,wordResourceService, wordAttributeResourceService,SPLIT_CHAR);
				//封装成json格式数据
				wordJson = JSONObject.fromObject(wv);
			
		}else{
			status.setStatus(StatusConstant.PARAM_ERROR);
			status.setMessage("参数有误!");
		}
		
		List<JSONObject> list = new ArrayList<>();
		if( wordJson!=null)
		list.add(wordJson);
		return JsonTool.generateModelAndView(list, status);
	}
	
	
	public WordResourceService getWordResourceService() {
		return wordResourceService;
	}
	@Resource
	public void setWordResourceService(WordResourceService wordResourceService) {
		this.wordResourceService = wordResourceService;
	}

	public WordAttributeResourceService getWordAttributeResourceService() {
		return wordAttributeResourceService;
	}
	@Resource
	public void setWordAttributeResourceService(WordAttributeResourceService wordAttributeResourceService) {
		this.wordAttributeResourceService = wordAttributeResourceService;
	}

	public OntologyManage getOntologyManage() {
		return ontologyManage;
	}
	@Resource
	public void setOntologyManage(OntologyManage ontologyManage) {
		this.ontologyManage = ontologyManage;
	}

	
}
