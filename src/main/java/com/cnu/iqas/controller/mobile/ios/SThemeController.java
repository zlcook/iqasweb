package com.cnu.iqas.controller.mobile.ios;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.ios.Suser;
import com.cnu.iqas.bean.ios.SuserWord;
import com.cnu.iqas.bean.iword.WordTheme;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.service.common.IUserWordService;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.service.iword.WordAttributeResourceService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.service.iword.WordThemeService;
import com.cnu.iqas.utils.JsonTool;
import com.cnu.iqas.utils.WebUtils;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.entity.PropertyEntity;

import net.sf.json.JSONObject;

/**
* @author 周亮 
* @version 创建时间：2016年1月26日 下午10:16:41
* 类说明：苹果端根据服务主题查询单词
*/
@Controller
@RequestMapping(value="/mobile/ios/theme/")
public class SThemeController {
	/**
	 * 单词资源服务接口
	 */
	private WordResourceService wordResourceService;
	/**
	 * 单词属性资源服务接口
	 */
	private WordAttributeResourceService wordAttributeResourceService;
	/**
	 * 本体操作服务接口
	 */
	private OntologyManage ontologyManage;
	/**
	 * 用户学习单词服务类
	 */
	private IUserWordService suserWordService;
	/**
	 * 用户基本服务接口
	 */
	private IUserBaseService suserBaseService;
	/**
	 * 单词服务
	 */
	private IwordService iwordService;
	/**
	 * 单词服务
	 */
	private WordThemeService wordThemeService;
	/**
	 * 根据主题编号和用户id查询用户在该主题下的学习单词
	 * @param themeNumber    主题编号
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="findWordsByTheme")
	public ModelAndView findWordsByTheme(String themeNumber,String userName,String password){
		MyStatus status =new MyStatus();
		//存放所有实体的json对象
		List<JSONObject>  listJson = new ArrayList<>();
		//用户学习单词
		List<SuserWord> userWords=null;
		if( !WebUtils.isNull(themeNumber) &&!WebUtils.isNull(userName) &&!WebUtils.isNull(password) ){
			//1.查询用户
			Suser user = (Suser) suserBaseService.findUser(userName, password);
			if( user !=null){
				//查询该主题
				WordTheme theme = wordThemeService.findByNumber(themeNumber);
				if( theme!=null){
					//2.先在本地查，如果本地没有该学生学习的主题单词再去本题库查，同时保存在本地。
					userWords = suserWordService.getWords(themeNumber, user.getUserId());
					//3.本地不存在，查询本体库
					if( userWords==null || userWords.size()<=0){
						if( userWords==null)
							userWords = new ArrayList<>();
						 try {
							//查询主题下所有单词
							List<ResultSet> resultsAllBrother = ontologyManage.QueryBrotherIndividual("4",theme.getContent());
							//遍历单词
							if(resultsAllBrother!=null)
							for (int i = 0; i < resultsAllBrother.size(); i++) {
								if (resultsAllBrother.get(i).hasNext()) {
									//获取一条实体记录
									QuerySolution solutionEachBrother = resultsAllBrother.get(i).next();
									//单词id 
									String wordId =PropertyEntity.subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[0]).toString());
									 // 单词
									 String wordStr=PropertyEntity.subStringManage(solutionEachBrother.get(PropertyEntity.propertySPARQLValue[1]).toString());
									 
									 SuserWord  uW = new SuserWord();
									 uW.setTheme(themeNumber);
									 uW.setWordId(wordId);
									 uW.setUserId(user.getUserId());
									 uW.setWord(wordStr);
									 //保存用户学习单词
									 suserWordService.save(uW);
									 //收集
									 userWords.add(uW);
								} 
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							status.setExecptionStatus(e);
						}
					}
				}else{
					status.setStatus(StatusConstant.WORD_THEME_NO_EXIST);
					status.setMessage("没有此主题");
				}
			}else{
				status.setStatus(StatusConstant.USER_NOT_EXIST);
				status.setMessage("用户不存在!");
			}
		}else{
			status.setParamsError();
		}
		//封装成json格式
		if(userWords!=null)
			for( SuserWord  uw: userWords){
				JSONObject uwJson = JSONObject.fromObject(uw);
				listJson.add(uwJson);
			}
		
		return JsonTool.generateModelAndView(listJson, status);
	}
	/**
	 * 查询子主题
	 * @param themeNumber 主题id
	 * @return
	 */
	@RequestMapping(value="findChilThemes")
	public ModelAndView findThemeByParent(String themeNumber){
		MyStatus status =new MyStatus();
		//存放所有实体的json对象
		List<JSONObject>  listJson = new ArrayList<>();
		if( !WebUtils.isNull(themeNumber)  ){
			try {
				
				//先查询服务主题
				WordTheme parentTheme=wordThemeService.findByNumber(themeNumber);
				if( parentTheme!=null){
					//查询子主题构造条件
					String sql = "o.parentId = ?";
					List<Object> listParams= new ArrayList<>();
					listParams.add(parentTheme.getId());
					//1.查询子主题
					List<WordTheme> wordThemes= wordThemeService.getWordThemes(sql, listParams.toArray());
					//遍历生成json
					if( wordThemes!=null)
					for(WordTheme theme :wordThemes){
						JSONObject themeJson= generatorThemeJson(theme);
						listJson.add(themeJson);
					}
				}else{
					status.setStatus(StatusConstant.WORD_THEME_NO_EXIST);
					status.setMessage("主题不存在");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				status.setExecptionStatus(e);
			}
		}else{
			status.setParamsError();
		}
		
		return JsonTool.generateModelAndView(listJson, status);
	}
	
	/**
	 * 查询一级主题,根主题
	 * @return
	 */
	@RequestMapping(value="findRootThemes")
	public ModelAndView findThemeByParent(){
		MyStatus status =new MyStatus();
		//存放所有实体的json对象
		List<JSONObject>  listJson = new ArrayList<>();
			try {
				//查询根主题构造条件：可见
				String sql = "o.parentId is Null and o.visible =?";
				List<Object> listParams= new ArrayList<>();
				listParams.add(true);
				
				//1.查询主题
				List<WordTheme> wordThemes= wordThemeService.getWordThemes(sql, listParams.toArray());
				//遍历生成json
				if( wordThemes!=null)
				for(WordTheme theme :wordThemes){
					JSONObject themeJson= generatorThemeJson(theme);
					listJson.add(themeJson);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				status.setExecptionStatus(e);
			}
		
		return JsonTool.generateModelAndView(listJson, status);
	}
	/**
	 * 根据主题生成想要的json
	 * @param theme
	 * @return
	 */
	private JSONObject generatorThemeJson(WordTheme theme) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		json.put("number", theme.getNumber());
		json.put("content", theme.getContent());
		json.put("english", theme.getEnglish());
		json.put("picturePath", theme.getPicturePath());
		return json;
	}
	/**
	 * 发现主题下的所有单词:
	 * @param theme：主题，如“20.自然-(69)山川与河流-山川 ”或是"(69)"
	 * @return
	 */
	/*@RequestMapping(value="findWordsByTheme")
	public ModelAndView findWordsByTheme(String theme){
		MyStatus status =new MyStatus();
		//存放所有实体的json对象
		List<JSONObject>  listJson = new ArrayList<>();
		//存放所有查到的单词，key值为单词内容,value存放不同版本相同的单词
		Map<String,List<PropertyEntity>> words = new HashMap<String,List<PropertyEntity>>();
		try {
			 if( theme ==null || !theme.trim().equals("")){
				 //查询主题下所有单词
				List<ResultSet> resultsAllBrother = ontologyManage.QueryBrotherIndividual(theme);
				//遍历
				for (int i = 0; i < resultsAllBrother.size(); i++) {
					if (resultsAllBrother.get(i).hasNext()) {
						//获取一条实体记录
						QuerySolution solutionEachBrother = resultsAllBrother.get(i).next();
						//封装成实体
						PropertyEntity pe =PropertyEntity.generatePropertyEntity(solutionEachBrother);
						//是否包含了该单词
						if( words.containsKey(pe.getInstanceLabel())){
							//包含了则加入
							words.get(pe.getInstanceLabel()).add(pe);
						}else{
							//不包含则新建
							List<PropertyEntity> listPe = new ArrayList<>();
							listPe.add(pe);
							words.put(pe.getInstanceLabel(), listPe);
						}
					} else {
						status.setStatus(StatusConstant.NOUMENON_NO_THEME);
						status.setMessage("知识本体库中没有此主题");
					}
				}
				//将查询的单词封装成json
				for(String word : words.keySet()){
					//封装成WordVoMange
					  //分隔符
					 String splitChar="@";
					WordVoManage mv = WordVoManage.generateWordVoManage(words.get(word), wordResourceService, wordAttributeResourceService, splitChar);
					//生成json对象
					JSONObject peJson = JSONObject.fromObject(mv);
					listJson.add(peJson);
				}
				
			 }else{
				   status.setStatus(StatusConstant.PARAM_ERROR);
					status.setMessage("请输入查看的主题！");
			   }
		}catch(StringIndexOutOfBoundsException soe){
			soe.printStackTrace();
			status.setStatus(StatusConstant.PARAM_ERROR);
			status.setMessage("参数有误");
		}catch (Exception e) {
			e.printStackTrace();
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常");
		}
		return JsonTool.generateModelAndView(listJson, status);
	}*/
	/**
	 * 根据单词查看所有属性值
	 * @param word
	 * @return
	 *//*
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
	*//**
	 * 获取单词的图片
	 * @param wordid  单词id
	 * @return
	 *//*
	@RequestMapping(value="picturesOfword")
	public ModelAndView getPictures(String wordid){
		MyStatus status =new MyStatus();
		//存放所有实体的json对象
		List<JSONObject>  listJson=null;
		try {
			if( wordid!=null && !wordid.equals("")){
				listJson=getResouces(wordid,ResourceConstant.TYPE_IMAGE);
			}else{
				status.setMessage("输入查询的单词!");
				status.setStatus(StatusConstant.NOUMENON_NO_WORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常！");
		}
		return JsonTool.generateModelAndView(listJson, status);
	}
	
	*//**
	 * 获取单词的绘本
	 * @param wordid
	 * @return
	 *//*
	@RequestMapping(value="booksOfword")
	public ModelAndView getBooks(String wordid){
		MyStatus status =new MyStatus();
		//存放所有实体的json对象
		List<JSONObject>  listJson=null;
		try {
			if( wordid!=null && !wordid.equals("")){
				listJson=getResouces(wordid,ResourceConstant.TYPE_PICTUREBOOK);
			}else{
				status.setMessage("输入查询的单词!");
				status.setStatus(StatusConstant.NOUMENON_NO_WORD);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
			status.setMessage("未知异常！");
		}
		
		return JsonTool.generateModelAndView(listJson, status);
	}
    
	*//**
	 * 获取单词资源和单词属性资源特定类型资源
	 * @param wordid
	 * @param type
	 * @return
	 *//*
	public List<JSONObject> getResouces(String wordid,int type){
		//存放所有实体的json对象
		List<JSONObject>  listJson = new ArrayList<>();
			//获取单词资源的图片
			List<WordResource> wrs = wordResourceService.findByWordId(wordid, ResourceConstant.TYPE_IMAGE);
		    //将单词本身资源图片资源保存到List集合中
			if(wrs!=null)
			for( WordResource wr : wrs ){
				JSONObject wrJson = new JSONObject();
				wrJson.put("resoucePath", wr.getSavepath());
				listJson.add(wrJson);
			}
			//获取单词属性资源的图片
			List<WordAttributeResource> wars= wordAttributeResourceService.findByWordId(wordid, ResourceConstant.TYPE_IMAGE);
			 //将单词属性资源图片资源保存到List集合中
			for( WordAttributeResource war : wars ){
				JSONObject warJson = new JSONObject();
				warJson.put("resoucePath", war.getSavepath());
				listJson.add(warJson);
			}
		
		return listJson;
	}*/
	
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

	public IUserWordService getSuserWordService() {
		return suserWordService;
	}
	@Resource
	public void setSuserWordService(IUserWordService suserWordService) {
		this.suserWordService = suserWordService;
	}

	public IUserBaseService getSuserBaseService() {
		return suserBaseService;
	}
	@Resource
	public void setSuserBaseService(IUserBaseService suserBaseService) {
		this.suserBaseService = suserBaseService;
	}

	public IwordService getIwordService() {
		return iwordService;
	}
	@Resource
	public void setIwordService(IwordService iwordService) {
		this.iwordService = iwordService;
	}
	public WordThemeService getWordThemeService() {
		return wordThemeService;
	}
	@Resource
	public void setWordThemeService(WordThemeService wordThemeService) {
		this.wordThemeService = wordThemeService;
	}
	
}
