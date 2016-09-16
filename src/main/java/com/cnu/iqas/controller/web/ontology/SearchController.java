package com.cnu.iqas.controller.web.ontology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.bean.Recommend.Question;
import com.cnu.iqas.bean.ontology.ISentence;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.service.Recommend.AnswerService;
import com.cnu.iqas.service.Recommend.QuestionService;
import com.cnu.iqas.service.ontology.SentenceSim;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
	private SentenceSim sentenceSim;
	private QuestionService questionService;
	private AnswerService answerService;
	/**
	 * 方法说明：在查询不到资源的情况可以采用人工添加的方式
	 * 
	 * @return
	 */
	@RequestMapping(value = "/input")
	public ModelAndView input() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/base/myforward.html?page=front/search/wordresult/InputQuestions");
		return mv;
	}
	/**
	 * 用户输入问句后首先进行判断，如果是单词跳转到查询单词的页面，如果是查询句子，跳转到查询句子的页面。
	 * @param queryParams
	 * @param request
	 * @param response
	 * @return 相关的页面
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/sentence")
	public ModelAndView searchSentence(HttpSession httpSession, String text) {
		httpSession.setAttribute("text", text);
		ModelAndView mv = new ModelAndView();
		User user=(User) httpSession.getAttribute("user");
		int userRank=user.getUserRank();
		if(userRank==1)
		{
			if (text.trim().length() < 25) {
				Question question = questionService.find("o.content = ?", text);
				    if (question != null) {
					System.out.println(question.getContent());
					// 获取了问题ID
					int questionId = question.getQuestionId();
					List<Object> list1 = new ArrayList<Object>();
					list1.add(questionId);
					// 查询答案信息列表
					List<Answer> Answer = answerService.getAllData("o.questionId = ?", list1.toArray());
					// 创建一个map集合来存放数据
					Map<String, String> tempMap = new HashMap<String, String>();
					// 保存课文原句的text到Map中
					List<Object> propertyText = new ArrayList<Object>();
					propertyText.add(questionId);
					propertyText.add("1");
					propertyText.add("课文原句");
					List<Answer> listpropertyText = answerService
							.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyText.toArray());
					if(listpropertyText!=null && listpropertyText.size()>0){
						System.out.println("课文原句"+listpropertyText.get(0).getContent());
						tempMap.put("propertyText", listpropertyText.get(0).getContent());
					}
					// 保存情景段落的text到Map中
					List<Object> propertyScene = new ArrayList<Object>();
					propertyScene.add(questionId);
					propertyScene.add("1");
					propertyScene.add("情境段落");
					List<Answer> listpropertyScene = answerService
							.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyScene.toArray());
					if(listpropertyScene!=null && listpropertyScene.size()>0){
						tempMap.put("propertyScene", listpropertyScene.get(0).getContent());
						System.out.println(listpropertyScene.get(0).getContent());
					}				
					// 保存延伸例句的text到Map中
					List<Object> propertyExtend = new ArrayList<Object>();
					propertyExtend.add(questionId);
					propertyExtend.add("1");
					propertyExtend.add("延伸例句");
					List<Answer> listpropertyExtend = answerService
							.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyExtend.toArray());
					if(listpropertyExtend!=null &&listpropertyExtend.size()>0){
						tempMap.put("propertyExtend", listpropertyExtend.get(0).getContent());
						System.out.println(listpropertyExtend.get(0).getContent());
					}
					mv.addObject("listResource", tempMap);
					mv.setViewName("/front/search/wordresult/lowrankresourceshow");
				    } else{
					 // 在二维数据表中查询不到的情况下在本体库中查询,假如本题库有，跳转到本体库进行查询，如果两个数据库都没有就跳转查询失败。				
					 mv.setViewName("/front/search/wordresult/findfail");
				          }

			   } else {
				     System.out.println("进入句子数据库查询");
				     Map<String, String> sentenceMap = new HashMap<String, String>();
				     Question question = questionService.find("o.content = ?", text);
				     if (question != null) {
					 System.out.println(question.getContent());
					 // 获取了问题ID
				 	int questionId = question.getQuestionId();
					List<Object> list1 = new ArrayList<Object>();
					list1.add(questionId);
					// 查询答案信息列表
					List<Answer> Answer = answerService.getAllData("o.questionId = ?", list1.toArray());
					// 答案列表的长度
					int TT = Answer.size();
					System.out.println("长度是" + TT);
					// 保存句子问题到Map中
					List<Object> sentencequestion = new ArrayList<Object>();
					sentencequestion.add(questionId);
					sentencequestion.add("1");
					sentencequestion.add("问题");
					List<Answer> listsentencequestion = answerService
							.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencequestion.toArray());
					if(listsentencequestion!=null &listsentencequestion.size()>0)
					{
					System.out.println(listsentencequestion.get(0).getContent());
					sentenceMap.put("sentencequestion", listsentencequestion.get(0).getContent());
					}			
					// 保存句子回答到Map中
					List<Object> sentenceanswer = new ArrayList<Object>();
					sentenceanswer.add(questionId);
					sentenceanswer.add("1");
					sentenceanswer.add("回答");
					List<Answer> listsentenceanswer = answerService
							.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentenceanswer.toArray());
					if(listsentenceanswer!=null && listsentenceanswer.size()>0){
						System.out.println(listsentenceanswer.get(0).getContent());
						sentenceMap.put("sentenceanswer", listsentenceanswer.get(0).getContent());
					}				
					// 保存句子情景对话到Map中
					List<Object> sentencedialogue = new ArrayList<Object>();
					sentencedialogue.add(questionId);
					sentencedialogue.add("1");
					sentencedialogue.add("情景对话");
					List<Answer> listsentencedialogue = answerService
							.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencedialogue.toArray());			
					if(listsentencedialogue!=null && listsentencedialogue.size()>0){
						System.out.println(listsentencedialogue.get(0).getContent());
						sentenceMap.put("sentencedialogue", listsentencedialogue.get(0).getContent());
						}
					// 保存句子本课重要句型对话到Map中
					List<Object> sentencepattern = new ArrayList<Object>();
					sentencepattern.add(questionId);
					sentencepattern.add("1");
					sentencepattern.add("本课重要句型");
					List<Answer> listsentencepattern = answerService
							.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencepattern.toArray());
					if(listsentencepattern!=null & listsentencepattern.size()>0){
					System.out.println(listsentencepattern.get(0).getContent());
					sentenceMap.put("sentencepattern", listsentencepattern.get(0).getContent());
					}			
					// 保存句子本课课后单词对话到Map中
					List<Object> sentencerelatedwords = new ArrayList<Object>();
					sentencerelatedwords.add(questionId);
					sentencerelatedwords.add("1");
					sentencerelatedwords.add("本课课后单词");
					List<Answer> listsentencerelatedwords = answerService
							.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencerelatedwords.toArray());
					if(listsentencerelatedwords!=null && listsentencerelatedwords.size()>0)
					{
					System.out.println(listsentencerelatedwords.get(0).getContent());
					sentenceMap.put("sentencerelatedwords", listsentencerelatedwords.get(0).getContent());
					}
					
					mv.addObject("listsentence", sentenceMap);
					mv.setViewName("/front/search/wordresult/sentenceshow");
				   } else {
					// 在二维数据表中无法查询的时候，在本体库中进行查询。根据句子进行查询
					System.out.println("根据句子本体库查询");
					ISentence sentence = sentenceSim.maxSimilar(text, null);
					if (sentence != null) {
						System.out.println("进入本体查询！");
						sentenceMap.put("sentencequestion",sentence.getInstanceLabel() );
						sentenceMap.put("sentenceanswer",sentence.getPropertyAnswer() );
						sentenceMap.put("sentencedialogue",sentence.getPropertyScene() );
						sentenceMap.put("sentencepattern",sentence.getPropertySentencePattern() );
						sentenceMap.put("sentencerelatedwords",sentence.getPropertyRelatedWords());
						mv.addObject("listsentence", sentenceMap);
						mv.setViewName("/front/search/wordresult/sentenceshow");
					    } else {
						mv.setViewName("/front/search/help");
					      }
				       }
			     }		
			}		
	            if(userRank==2)
                 {
	            	if (text.trim().length() < 25) {
	    			    Question question = questionService.find("o.content = ?", text);
	    			    if (question != null) {
	    				System.out.println(question.getContent());
	    				// 获取了问题ID
	    				int questionId = question.getQuestionId();
	    				List<Object> list1 = new ArrayList<Object>();
	    				list1.add(questionId);
	    				// 查询答案信息列表
	    				List<Answer> Answer = answerService.getAllData("o.questionId = ?", list1.toArray());
	    				// 答案列表的长度
	    				int TT = Answer.size();
	    				System.out.println("长度是" + TT);
	    				// 创建一个list集合来存储数据
	    				/* List<Object> listnum=new ArrayList<Object>(); */
	    				// 创建一个map集合来存放数据
	    				Map<String, String> tempMap = new HashMap<String, String>();
	    				// 保存课文原句的text到Map中
	    				List<Object> propertyText = new ArrayList<Object>();
	    				propertyText.add(questionId);
	    				propertyText.add("1");
	    				propertyText.add("课文原句");
	    				List<Answer> listpropertyText = answerService
	    						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyText.toArray());
	    				if (listpropertyText != null && listpropertyText.size() > 0) {
	    					System.out.println(listpropertyText.get(0).getContent());
	    					tempMap.put("propertyText", listpropertyText.get(0).getContent());
	    				}
	    				// 保存情景段落的text到Map中
	    				List<Object> propertyScene = new ArrayList<Object>();
	    				propertyScene.add(questionId);
	    				propertyScene.add("1");
	    				propertyScene.add("情境段落");
	    				List<Answer> listpropertyScene = answerService
	    						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyScene.toArray());
	    				if (listpropertyScene != null && listpropertyScene.size() > 0) {
	    					tempMap.put("propertyScene", listpropertyScene.get(0).getContent());
	    					System.out.println(listpropertyScene.get(0).getContent());
	    				}
	    				// 保存延伸例句的text到Map中
	    				List<Object> propertyExtend = new ArrayList<Object>();
	    				propertyExtend.add(questionId);
	    				propertyExtend.add("1");
	    				propertyExtend.add("延伸例句");
	    				List<Answer> listpropertyExtend = answerService
	    						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyExtend.toArray());
	    				if (listpropertyExtend != null && listpropertyExtend.size() > 0) {
	    					tempMap.put("propertyExtend", listpropertyExtend.get(0).getContent());
	    					System.out.println(listpropertyExtend.get(0).getContent());
	    				}
	    				// 保存百科的text到Map中
	    				List<Object> propertyNcyclopedia = new ArrayList<Object>();
	    				propertyNcyclopedia.add(questionId);
	    				propertyNcyclopedia.add("1");
	    				propertyNcyclopedia.add("百科");
	    				List<Answer> listpropertyNcyclopedia = answerService.getAllData(
	    						"o.questionId = ? and o.checked= ? and o.attributes=?", propertyNcyclopedia.toArray());
	    				if (listpropertyNcyclopedia != null && listpropertyNcyclopedia.size() > 0) {
	    					tempMap.put("propertyNcyclopedia", listpropertyNcyclopedia.get(0).getContent());
	    					System.out.println(listpropertyNcyclopedia.get(0).getContent());
	    				}
	    				// 保存用法的text到Map中
	    				List<Object> propertyUse = new ArrayList<Object>();
	    				propertyUse.add(questionId);
	    				propertyUse.add("1");
	    				propertyUse.add("用法");
	    				List<Answer> listpropertyUse = answerService
	    						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyUse.toArray());
	    				if (listpropertyUse != null && listpropertyUse.size() > 0) {
	    					tempMap.put("propertyUse", listpropertyUse.get(0).getContent());
	    					System.out.println(listpropertyUse.get(0).getContent());
	    				}
	    				mv.addObject("listResource", tempMap);
	    				mv.setViewName("/front/search/wordresult/middlerankresourceshow");
	    			   } else {
	    				// 在数据表中查询不到的情况下在本体库中查询
	    				mv.setViewName("/front/search/wordresult/findfail");
	    			          }
	    		    } else {
	    			System.out.println("进入句子数据库查询");
	    			Map<String, String> sentenceMap = new HashMap<String, String>();
	    			Question question = questionService.find("o.content = ?", text);
	    			if (question != null) {
	    				System.out.println(question.getContent());
	    				// 获取了问题ID
	    				int questionId = question.getQuestionId();
	    				List<Object> list1 = new ArrayList<Object>();
	    				list1.add(questionId);
	    				// 查询答案信息列表
	    				List<Answer> Answer = answerService.getAllData("o.questionId = ?", list1.toArray());
	    				// 答案列表的长度
	    				int TT = Answer.size();
	    				System.out.println("长度是" + TT);
	    				// 保存句子问题到Map中
	    				List<Object> sentencequestion = new ArrayList<Object>();
	    				sentencequestion.add(questionId);
	    				sentencequestion.add("1");
	    				sentencequestion.add("问题");
	    				List<Answer> listsentencequestion = answerService
	    						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencequestion.toArray());
	    				if (listsentencequestion != null & listsentencequestion.size() > 0) {
	    					System.out.println(listsentencequestion.get(0).getContent());
	    					sentenceMap.put("sentencequestion", listsentencequestion.get(0).getContent());
	    				}
	    				// 保存句子回答到Map中
	    				List<Object> sentenceanswer = new ArrayList<Object>();
	    				sentenceanswer.add(questionId);
	    				sentenceanswer.add("1");
	    				sentenceanswer.add("回答");
	    				List<Answer> listsentenceanswer = answerService
	    						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentenceanswer.toArray());
	    				if (listsentenceanswer != null && listsentenceanswer.size() > 0) {
	    					System.out.println(listsentenceanswer.get(0).getContent());
	    					sentenceMap.put("sentenceanswer", listsentenceanswer.get(0).getContent());
	    				}
	    				// 保存句子情景对话到Map中
	    				List<Object> sentencedialogue = new ArrayList<Object>();
	    				sentencedialogue.add(questionId);
	    				sentencedialogue.add("1");
	    				sentencedialogue.add("情景对话");
	    				List<Answer> listsentencedialogue = answerService
	    						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencedialogue.toArray());
	    				if (listsentencedialogue != null && listsentencedialogue.size() > 0) {
	    					System.out.println(listsentencedialogue.get(0).getContent());
	    					sentenceMap.put("sentencedialogue", listsentencedialogue.get(0).getContent());
	    				}
	    				// 保存句子本课重要句型对话到Map中
	    				List<Object> sentencepattern = new ArrayList<Object>();
	    				sentencepattern.add(questionId);
	    				sentencepattern.add("1");
	    				sentencepattern.add("本课重要句型");
	    				List<Answer> listsentencepattern = answerService
	    						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencepattern.toArray());
	    				if (listsentencepattern != null & listsentencepattern.size() > 0) {
	    					System.out.println(listsentencepattern.get(0).getContent());
	    					sentenceMap.put("sentencepattern", listsentencepattern.get(0).getContent());
	    				}
	    				// 保存句子本课课后单词对话到Map中
	    				List<Object> sentencerelatedwords = new ArrayList<Object>();
	    				sentencerelatedwords.add(questionId);
	    				sentencerelatedwords.add("1");
	    				sentencerelatedwords.add("本课课后单词");
	    				List<Answer> listsentencerelatedwords = answerService.getAllData(
	    						"o.questionId = ? and o.checked= ? and o.attributes=?", sentencerelatedwords.toArray());
	    				if (listsentencerelatedwords != null && listsentencerelatedwords.size() > 0) {
	    					System.out.println(listsentencerelatedwords.get(0).getContent());
	    					sentenceMap.put("sentencerelatedwords", listsentencerelatedwords.get(0).getContent());
	    				}

	    				mv.addObject("listsentence", sentenceMap);
	    				mv.setViewName("/front/search/wordresult/sentenceshow");
	    			} else {
	    				// 在二维数据表中无法查询的时候，在本体库中进行查询。根据句子进行查询
	    				System.out.println("根据句子本体库查询");
	    				ISentence sentence = sentenceSim.maxSimilar(text, null);
	    				if (sentence != null) {
	    					System.out.println("进入本体查询！");
	    					sentenceMap.put("sentencequestion", sentence.getInstanceLabel());
	    					sentenceMap.put("sentenceanswer", sentence.getPropertyAnswer());
	    					sentenceMap.put("sentencedialogue", sentence.getPropertyScene());
	    					sentenceMap.put("sentencepattern", sentence.getPropertySentencePattern());
	    					sentenceMap.put("sentencerelatedwords", sentence.getPropertyRelatedWords());
	    					mv.addObject("listsentence", sentenceMap);
	    					mv.setViewName("/front/search/wordresult/sentenceshow");
	    				} else {
	    					mv.setViewName("/front/search/help");
	    				       }
	    			      }
	    		      }
                 }
	            if(userRank==3)
	            {
		        if (text.trim().length() < 25) {
			    Question question = questionService.find("o.content = ?", text);
			    if (question != null) {
				System.out.println(question.getContent());
				// 获取了问题ID
				int questionId = question.getQuestionId();
				List<Object> list1 = new ArrayList<Object>();
				list1.add(questionId);
				// 查询答案信息列表
				List<Answer> Answer = answerService.getAllData("o.questionId = ?", list1.toArray());
				// 答案列表的长度
				int TT = Answer.size();
				System.out.println("长度是" + TT);
				// 创建一个list集合来存储数据
				/* List<Object> listnum=new ArrayList<Object>(); */
				// 创建一个map集合来存放数据
				Map<String, String> tempMap = new HashMap<String, String>();
				// 保存课文原句的text到Map中
				List<Object> propertyText = new ArrayList<Object>();
				propertyText.add(questionId);
				propertyText.add("1");
				propertyText.add("课文原句");
				List<Answer> listpropertyText = answerService
						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyText.toArray());
				if (listpropertyText != null && listpropertyText.size() > 0) {
					System.out.println(listpropertyText.get(0).getContent());
					tempMap.put("propertyText", listpropertyText.get(0).getContent());
				}
				// 保存情景段落的text到Map中
				List<Object> propertyScene = new ArrayList<Object>();
				propertyScene.add(questionId);
				propertyScene.add("1");
				propertyScene.add("情境段落");
				List<Answer> listpropertyScene = answerService
						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyScene.toArray());
				if (listpropertyScene != null && listpropertyScene.size() > 0) {
					tempMap.put("propertyScene", listpropertyScene.get(0).getContent());
					System.out.println(listpropertyScene.get(0).getContent());
				}
				// 保存延伸例句的text到Map中
				List<Object> propertyExtend = new ArrayList<Object>();
				propertyExtend.add(questionId);
				propertyExtend.add("1");
				propertyExtend.add("延伸例句");
				List<Answer> listpropertyExtend = answerService
						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyExtend.toArray());
				if (listpropertyExtend != null && listpropertyExtend.size() > 0) {
					tempMap.put("propertyExtend", listpropertyExtend.get(0).getContent());
					System.out.println(listpropertyExtend.get(0).getContent());
				}
				// 保存百科的text到Map中
				List<Object> propertyNcyclopedia = new ArrayList<Object>();
				propertyNcyclopedia.add(questionId);
				propertyNcyclopedia.add("1");
				propertyNcyclopedia.add("百科");
				List<Answer> listpropertyNcyclopedia = answerService.getAllData(
						"o.questionId = ? and o.checked= ? and o.attributes=?", propertyNcyclopedia.toArray());
				if (listpropertyNcyclopedia != null && listpropertyNcyclopedia.size() > 0) {
					tempMap.put("propertyNcyclopedia", listpropertyNcyclopedia.get(0).getContent());
					System.out.println(listpropertyNcyclopedia.get(0).getContent());
				}
				// 保存用法的text到Map中
				List<Object> propertyUse = new ArrayList<Object>();
				propertyUse.add(questionId);
				propertyUse.add("1");
				propertyUse.add("用法");
				List<Answer> listpropertyUse = answerService
						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", propertyUse.toArray());
				if (listpropertyUse != null && listpropertyUse.size() > 0) {
					tempMap.put("propertyUse", listpropertyUse.get(0).getContent());
					System.out.println(listpropertyUse.get(0).getContent());
				}
				mv.addObject("listResource", tempMap);
				mv.setViewName("/front/search/wordresult/highrankresourceshow");
			   } else {
				// 在数据表中查询不到的情况下在本体库中查询
				mv.setViewName("/front/search/wordresult/findfail");
			          }
		    } else {
			System.out.println("进入句子数据库查询");
			Map<String, String> sentenceMap = new HashMap<String, String>();
			Question question = questionService.find("o.content = ?", text);
			if (question != null) {
				System.out.println(question.getContent());
				// 获取了问题ID
				int questionId = question.getQuestionId();
				List<Object> list1 = new ArrayList<Object>();
				list1.add(questionId);
				// 查询答案信息列表
				List<Answer> Answer = answerService.getAllData("o.questionId = ?", list1.toArray());
				// 答案列表的长度
				int TT = Answer.size();
				System.out.println("长度是" + TT);
				// 保存句子问题到Map中
				List<Object> sentencequestion = new ArrayList<Object>();
				sentencequestion.add(questionId);
				sentencequestion.add("1");
				sentencequestion.add("问题");
				List<Answer> listsentencequestion = answerService
						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencequestion.toArray());
				if (listsentencequestion != null & listsentencequestion.size() > 0) {
					System.out.println(listsentencequestion.get(0).getContent());
					sentenceMap.put("sentencequestion", listsentencequestion.get(0).getContent());
				}
				// 保存句子回答到Map中
				List<Object> sentenceanswer = new ArrayList<Object>();
				sentenceanswer.add(questionId);
				sentenceanswer.add("1");
				sentenceanswer.add("回答");
				List<Answer> listsentenceanswer = answerService
						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentenceanswer.toArray());
				if (listsentenceanswer != null && listsentenceanswer.size() > 0) {
					System.out.println(listsentenceanswer.get(0).getContent());
					sentenceMap.put("sentenceanswer", listsentenceanswer.get(0).getContent());
				}
				// 保存句子情景对话到Map中
				List<Object> sentencedialogue = new ArrayList<Object>();
				sentencedialogue.add(questionId);
				sentencedialogue.add("1");
				sentencedialogue.add("情景对话");
				List<Answer> listsentencedialogue = answerService
						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencedialogue.toArray());
				if (listsentencedialogue != null && listsentencedialogue.size() > 0) {
					System.out.println(listsentencedialogue.get(0).getContent());
					sentenceMap.put("sentencedialogue", listsentencedialogue.get(0).getContent());
				}
				// 保存句子本课重要句型对话到Map中
				List<Object> sentencepattern = new ArrayList<Object>();
				sentencepattern.add(questionId);
				sentencepattern.add("1");
				sentencepattern.add("本课重要句型");
				List<Answer> listsentencepattern = answerService
						.getAllData("o.questionId = ? and o.checked= ? and o.attributes=?", sentencepattern.toArray());
				if (listsentencepattern != null & listsentencepattern.size() > 0) {
					System.out.println(listsentencepattern.get(0).getContent());
					sentenceMap.put("sentencepattern", listsentencepattern.get(0).getContent());
				}
				// 保存句子本课课后单词对话到Map中
				List<Object> sentencerelatedwords = new ArrayList<Object>();
				sentencerelatedwords.add(questionId);
				sentencerelatedwords.add("1");
				sentencerelatedwords.add("本课课后单词");
				List<Answer> listsentencerelatedwords = answerService.getAllData(
						"o.questionId = ? and o.checked= ? and o.attributes=?", sentencerelatedwords.toArray());
				if (listsentencerelatedwords != null && listsentencerelatedwords.size() > 0) {
					System.out.println(listsentencerelatedwords.get(0).getContent());
					sentenceMap.put("sentencerelatedwords", listsentencerelatedwords.get(0).getContent());
				}

				mv.addObject("listsentence", sentenceMap);
				mv.setViewName("/front/search/wordresult/sentenceshow");
			} else {
				// 在二维数据表中无法查询的时候，在本体库中进行查询。根据句子进行查询
				System.out.println("根据句子本体库查询");
				ISentence sentence = sentenceSim.maxSimilar(text, null);
				if (sentence != null) {
					System.out.println("进入本体查询！");
					sentenceMap.put("sentencequestion", sentence.getInstanceLabel());
					sentenceMap.put("sentenceanswer", sentence.getPropertyAnswer());
					sentenceMap.put("sentencedialogue", sentence.getPropertyScene());
					sentenceMap.put("sentencepattern", sentence.getPropertySentencePattern());
					sentenceMap.put("sentencerelatedwords", sentence.getPropertyRelatedWords());
					mv.addObject("listsentence", sentenceMap);
					mv.setViewName("/front/search/wordresult/sentenceshow");
				} else {
					mv.setViewName("/front/search/help");
				       }
			      }
		  }
	  } 
	            return mv;
	}
	public SentenceSim getSentenceSim() {
		return sentenceSim;
	}
	@Resource
	public void setSentenceSim(SentenceSim sentenceSim) {
		this.sentenceSim = sentenceSim;
	}
	public QuestionService getQuestionService() {
		return questionService;
	}
	@Resource
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public AnswerService getAnswerService() {
		return answerService;
	}

	@Resource
	public void setAnswerService(AnswerService answerService) {
		this.answerService = answerService;
	}

}