package com.cnu.iqas.controller.web.ontology;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.service.Recommend.AnswerService;
import com.cnu.iqas.service.Recommend.QuestionService;
@Controller
@RequestMapping(value = "/check")
public class TeacherCheckController {
	
	private QuestionService questionService;
	private AnswerService answerService;
	private List<Answer> answerList;
	
	@RequestMapping(value = "/addMessage")
	public ModelAndView addResourceMessage(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/base/myforward.html?page=front/search/wordresult/checkAddMessage");
		return mv;	
	}
	@RequestMapping(value = "/showMessage")
	public ModelAndView showMessage()
	{
		System.out.println("showMessage!!!");
		ModelAndView mv = new ModelAndView();
		List<Object> checkMessage = new ArrayList<Object>();
		checkMessage.add("2");
		List<Answer> listcheckMessage = answerService.getAllData(
				"o.checked= ?", checkMessage.toArray());
		if(listcheckMessage!=null && listcheckMessage.size()>0)
		{
			System.out.println(listcheckMessage.get(0).getContent());		
		}
		mv.addObject("listcheckMessage",listcheckMessage);
		mv.setViewName("/front/search/wordresult/checkAddMessage");
		return mv;
		
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
	public List<Answer> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}
	
}
