package com.cnu.iqas.controller.mobile.ontology;

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
import com.cnu.iqas.bean.ontology.ISentence;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.service.iword.WordAttributeResourceService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.service.ontology.SentenceSim;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.WebUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author 周亮 
* @version 创建时间：2015年11月7日 下午10:46:28
* @author  王文辉
* @version 创建时间：2016年1月29日 上午10:46:28
* 类说明
*/
@Controller
@RequestMapping(value="/mobile/search/")
public class MOntologyController {
	private SentenceSim sentenceSim ;
	private IwordService iwordService;
	private WordResourceService wordResourceService;
	private WordAttributeResourceService wordAttributeResourceService;
	String pictruepath;
	/**
	 * 用户输入问句后首先进行判断，如果是单词跳转到查询单词的页面，如果是查询句子，跳转到查询句子的页面。
	 * @param text 用户搜索的句子
	 * @return json格式数据
	 * {
	 *  status:1,    1:成功，0失败
	 *  message:"ok",原因
	 *  result:{
	 *  count:1,
	 *     data:[
	 *     	{account:"zhangsan",password:"123",gender:"男",picturePath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"}
	 * 	]
	 * 	}
	 */
	//Excuse me,Where are you from,ok?  park
	@SuppressWarnings("finally")
	@RequestMapping(value="/sentence")
	public ModelAndView searchSentence(String text){
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		int scode =StatusConstant.OK;//结果
		String message ="ok";//结果说明
		//总的json对象
		//JSONObject jsonObejct = new JSONObject();
		//result对象
		//JSONObject resultObject = new JSONObject();
		//对象
		//JSONObject senObject ;
		//总的json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray(); 
    	try {
    		
    		if( text!=null && !text.trim().equals("")){
    			if(text.trim().length()<15)
    			{
    				//根据单词进行查询
    				Iword iword=sentenceSim.findWordProperty(text);
    				
    				if( iword !=null ){
    					JSONObject reswordJson = new JSONObject();  
        				//课文原句+相关的图片
        				reswordJson.put("label", text);
        				reswordJson.put("propertyText", iword.getPropertyText());
        				Iword iwordtext=iwordService.find("o.content = ?",text);
        				String wordId=iwordtext.getId();
        				//根据单词id来查询单词的对象
        				WordResource wordpictrue=wordResourceService.find("o.wordId = ?", wordId);
        				if( wordpictrue !=null){
        					//获取单词图片的保存路径
            				pictruepath=wordpictrue.getSavepath();
            				System.out.println("单词的保存路径"+pictruepath);
            				System.out.println("课文原句"+iword.getPropertyText());
        				}
        				
        				reswordJson.put("pictruepath", pictruepath);
        				//情景段落
        				reswordJson.put("propertyScene", iword.getPropertyScene());
        				//延伸例句
        				reswordJson.put("propertyExtend", iword.getPropertyExtend());
        				//联想+相关图片 
        				reswordJson.put("propertyAssociate", iword.getPropertyAssociate());
        				List<Object>list=new ArrayList<Object>();
        				list.add(wordId);
        				list.add(13);
        				list.add(1);
        				List<WordAttributeResource>listwordAttributeAssociate=wordAttributeResourceService.getAllData("o.wordId = ? and o.attribute=? and o.type=?", list.toArray());
        				
        				
        				String associatesavePath=null;
        				if(listwordAttributeAssociate!=null && listwordAttributeAssociate.size()>0){
        					System.out.println("-----------"+listwordAttributeAssociate.get(0).getName());
        					StringBuilder str=new StringBuilder() ;
	        				for(WordAttributeResource wa : listwordAttributeAssociate)
	        				{
	        					str.append(wa.getSavepath()+",");
	        				   //a=wa.getSavepath();
	        				}
	        				 associatesavePath=str.substring(0, str.toString().length()-1);
	        				System.out.println("++++++++++"+associatesavePath);
        				}
        				reswordJson.put("AssociatesavePath", associatesavePath);
        				//同义词
        				reswordJson.put("propertySynonyms", iword.getPropertySynonyms());
        				//反义词   
        				reswordJson.put("propertyAntonym", iword.getPropertyAntonym());   
        				//拓展
        				reswordJson.put("propertyExpand",iword.getPropertyExpand());
        				//常用  
        				reswordJson.put("propertyCommonUse",iword.getPropertyCommonUse());
        				List<Object>listCommonUse=new ArrayList<Object>();
        				listCommonUse.add(wordId);
        				listCommonUse.add(20);
        				listCommonUse.add(1);
        				List<WordAttributeResource> listwordAttributeCommonUse=wordAttributeResourceService.getAllData("o.wordId = ? and o.attribute=? and o.type=?", list.toArray());
        				
        				String CommonUsesavePath = null;
        				if( listwordAttributeCommonUse!=null&&listwordAttributeCommonUse.size()>0 ){
        				System.out.println("-----------"+listwordAttributeCommonUse.get(0).getName());
        				CommonUsesavePath = listwordAttributeCommonUse.get(0).getSavepath();
        				}
        				reswordJson.put("CommonUsesavePath", CommonUsesavePath);
        				//百科
        				reswordJson.put("propertyNcyclopedia", iword.getPropertyNcyclopedia());
        				//用法
        				reswordJson.put("propertyUse", iword.getPropertyUse());
        				usersArray.add(reswordJson);
    				}
    				
    			}else{	
    				//根据句子进行查询
    			    System.out.println("根据句子进行查询");
    			    ISentence sentence=sentenceSim.maxSimilar(text, null);
    				//senObject= JSONObject.fromObject(sentence);
    			    JSONObject restextJson = new JSONObject();  
    			    restextJson.put("text", text);
    			    restextJson.put("answer", sentence.getPropertyAnswer());
    				//reswordJson.put("pictruepath", pictruepath);
    			    usersArray.add(restextJson);
    			}
    		}else{
    			status.setMessage("请填写查询单词!");
    			status.setStatus(StatusConstant.PARAM_ERROR);
    		}
				
    	}catch(Exception e ){
    		status.setMessage("未知异常");
    		status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		}finally{
			//-------------------返回视图
			//return WebUtils.beforeReturn(scode, message, jsonObejct, resultObject, mv);
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	public SentenceSim getSentenceSim() {
		return sentenceSim;
	}
	@Resource
	public void setSentenceSim(SentenceSim sentenceSim) {
		this.sentenceSim = sentenceSim;
	}

	public IwordService getIwordService() {
		return iwordService;
	}
	@Resource
	public void setIwordService(IwordService iwordService) {
		this.iwordService = iwordService;
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
	
	

}
