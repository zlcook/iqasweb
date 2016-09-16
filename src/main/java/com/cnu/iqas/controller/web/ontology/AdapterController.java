/*package com.cnu.iqas.controller.web.ontology;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.bean.Recommend.Question;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.service.Recommend.AnswerService;
import com.cnu.iqas.service.Recommend.QuestionService;

@Controller
@RequestMapping(value = "/adapter")
public class AdapterController {
	private QuestionService questionService;
	private AnswerService answerService;
	@RequestMapping(value = "/recommendmedia")
	public ModelAndView recommendmedia(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		int questionId=0;
		User user = (User) httpSession.getAttribute("user");
		String userid = user.getUserId();
		Question question = questionService.find("o.userid = ?", userid);
		if (question != null) {
			// 获取了问题ID
		    questionId = question.getQuestionId();
			System.out.println(questionId);
		}
		Answer answer=answerService.find("o.questionId = ?", questionId);
		if (answer != null) {
			// 获取了答案属性
			System.out.println(answer.getAttributes());
		}
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

}*/