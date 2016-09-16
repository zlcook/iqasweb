package com.cnu.iqas.controller.web.ontology;
import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.Recommend.Answer;
import com.cnu.iqas.bean.Recommend.Question;
import com.cnu.iqas.service.Recommend.AnswerService;
import com.cnu.iqas.service.Recommend.QuestionService;

@Controller
@RequestMapping(value = "/add")
public class AddResourceController implements ServletContextAware {
	private ServletContext servletContext;
	private QuestionService questionService;
	private AnswerService answerService;

	@RequestMapping(value = "/addResourceMessage")
	public ModelAndView addResourceMessage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/base/myforward.html?page=front/search/wordresult/addResourceMessage");
		return mv;
	}
	@RequestMapping(value = "/addResource")
	public ModelAndView addResource(HttpSession httpSession, Question question, Answer answer, String name,
			@RequestParam("file") CommonsMultipartFile file) {
		ModelAndView mv = new ModelAndView();
		String text = (String) httpSession.getAttribute("text");
		System.out.println("传过来的数据是" + text);
		// 将数据保存在问题表
		question.setContent(text);
		question.setCreateDate(new Date());
		question.setUserId("2141002068");
		questionService.save(question);
		Question tempquestion = questionService.find("o.content = ?", text);
		System.out.println("tempquestion" + tempquestion.getQuestionId());
		// 在answer表中添加信息
		answer.setAddType(question.getType());
		answer.setChecked("1");
		answer.setDifficulty("1");
		answer.setCreateDate(new Date());
		answer.setQuestionId(tempquestion.getQuestionId());
		// 上传文件
		if (!file.isEmpty()) {
			String path = this.servletContext.getRealPath("/upload/Resource"); // 获取本地存储路径
			System.out.println(path);
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			System.out.println(fileType);
			File file2 = new File(path, new Date().getTime() + fileType); // 新建一个文件
			try {
				file.getFileItem().write(file2); // 将上传的文件写入新建的文件中
				System.out.println(file2.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			answer.setMediaUrl(file2.getPath());
			mv.setViewName("/front/search/wordresult/successd");
			/* return "redirect:upload_ok.jsp"; */
		} else {
			mv.setViewName("/front/search/wordresult/fail");
			/* return "redirect:upload_error.jsp"; */
		}
		answerService.save(answer);
		mv.setViewName("/front/search/wordresult/successd");
		return mv;
	}
	@Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		this.servletContext = context;
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
