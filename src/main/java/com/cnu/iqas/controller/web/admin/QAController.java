package com.cnu.iqas.controller.web.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.bean.base.PageView;
import com.cnu.iqas.bean.base.QueryResult;
import com.cnu.iqas.bean.store.CommodityType;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.formbean.Recommend.AnswerForm;
import com.cnu.iqas.service.Recommend.AnswerService;

@Controller
@RequestMapping(value = "/admin/control/qa/")
public class QAController implements ServletContextAware {
	private Logger log = LogManager.getLogger(QAController.class);
	// 应用对象
	private ServletContext servletContext;
	private AnswerService answerService;
	private List<Answer> listcheckMessage;

	@RequestMapping(value = "listanswermessage")
	public ModelAndView listanswermessage(AnswerForm formbean) {

		System.out.println("listanswermessage");
		ModelAndView mv = new ModelAndView(PageViewConstant.ANSWER_LIST);
		//建立页面类，并初始化每页最大页数
		PageView<Answer> pv = new PageView<Answer>(formbean.getMaxresult(),formbean.getPage());
		//查询结果按时间降序排列
		LinkedHashMap<String, String> order = new LinkedHashMap<String, String>();
		order.put("createTime", "desc");
		List<Object> checkMessage = new ArrayList<Object>();
		checkMessage.add("1");
		//查询
		QueryResult<Answer> query= answerService.getScrollData(pv.getFirstResult(), pv.getMaxresult(), "o.checked= ?", checkMessage.toArray());
		/*listcheckMessage = answerService.getAllData("o.checked= ?", checkMessage.toArray());*/
		//查询结果存到页面类中
	    pv.setQueryResult(query);
	    //页面类存放到request中
	  	mv.addObject("pageView", pv);
		return mv;
	}
	@RequestMapping(value = "loadMessage")
	public ModelAndView loadMessage(HttpSession httpSession, int id) {
		ModelAndView mv = new ModelAndView(PageViewConstant.UPDATE_MESSAGE);
		Answer answer = answerService.find("o.answerId=?", id);
		if (answer != null) {
			httpSession.setAttribute("answer", answer);
			mv.addObject("answer", answer);
			System.out.println("loadMessage");
			System.out.println(id);
		}
		return mv;
	}

	@RequestMapping(value = "updatemessage")
	public ModelAndView updatemessage(Answer answer, HttpSession httpSession) {
		System.out.println(answer.getAnswerId());
		ModelAndView mv = new ModelAndView(PageViewConstant.SUCCESS);
		Answer answersql = (Answer) httpSession.getAttribute("answer");
		if (answersql != null) {
			System.out.println(answersql.getAnswerId());
			answersql.setAttributes(answer.getAttributes());
			answersql.setContent(answer.getContent());
			answersql.setDifficulty(answer.getDifficulty());
			answersql.setMediaType(answer.getMediaType());
			answersql.setChecked(answer.getChecked());
			answerService.updateAnswer(answersql);
			System.out.println(answer.getContent());
		}
		return mv;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
	}

	public AnswerService getAnswerService() {
		return answerService;
	}

	@Resource
	public void setAnswerService(AnswerService answerService) {
		this.answerService = answerService;
	}
}
