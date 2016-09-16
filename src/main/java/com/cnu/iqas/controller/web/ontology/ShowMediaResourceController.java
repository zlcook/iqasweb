package com.cnu.iqas.controller.web.ontology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping(value = "/show")
public class ShowMediaResourceController {
	private QuestionService questionService;
	private AnswerService answerService;
	@RequestMapping(value = "/MultiMediaResource")
	public ModelAndView MultiMediaResource(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		String text = (String) httpSession.getAttribute("text");
		System.out.println("传过来的数据是" + text);
		Question question = questionService.find("o.content = ?", text);
		if (question != null) {
			System.out.println(question.getContent());
			// 获取了问题ID
			int questionId = question.getQuestionId();
			Map<String, String> mediaMap = new HashMap<String, String>();
			// 保存联想图片到Map中
			List<Object> associationpic = new ArrayList<Object>();
			associationpic.add(questionId);
			associationpic.add("1");
			associationpic.add("联想");
			associationpic.add("图片");
			List<Answer> listassociationpic = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", associationpic.toArray());
			if (listassociationpic != null && listassociationpic.size() > 0) {
				System.out.println(listassociationpic.get(0).getMediaUrl());
				mediaMap.put("associationpic", listassociationpic.get(0).getMediaUrl());
			}
			// 保存联想音频到Map中
			List<Object> associationaud = new ArrayList<Object>();
			associationaud.add(questionId);
			associationaud.add("1");
			associationaud.add("联想");
			associationaud.add("音频");
			List<Answer> listassociationaud = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", associationaud.toArray());
			if (listassociationaud != null && listassociationaud.size() > 0) {
				System.out.println(listassociationaud.get(0).getMediaUrl());
				mediaMap.put("associationaud", listassociationaud.get(0).getMediaUrl());
			}
			// 保存联想文本到Map中
			List<Object> associationtxt = new ArrayList<Object>();
			associationtxt.add(questionId);
			associationtxt.add("1");
			associationtxt.add("联想");
			associationtxt.add("文本");
			List<Answer> listassociationtxt = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", associationtxt.toArray());
			if (listassociationtxt != null && listassociationtxt.size() > 0) {
				System.out.println(listassociationtxt.get(0).getContent());
				mediaMap.put("associationtxt", listassociationtxt.get(0).getContent());
			}
			// 保存同义词图片到Map中
			List<Object> Synonymspic = new ArrayList<Object>();
			Synonymspic.add(questionId);
			Synonymspic.add("1");
			Synonymspic.add("同义词");
			Synonymspic.add("图片");
			List<Answer> listSynonymspic = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Synonymspic.toArray());
			if (listSynonymspic != null && listSynonymspic.size() > 0) {
				System.out.println(listSynonymspic.get(0).getMediaUrl());
				mediaMap.put("Synonymspic", listSynonymspic.get(0).getMediaUrl());
			}
			// 保存同义词音频到Map中
			List<Object> Synonymsaud = new ArrayList<Object>();
			Synonymsaud.add(questionId);
			Synonymsaud.add("1");
			Synonymsaud.add("同义词");
			Synonymsaud.add("音频");
			List<Answer> listSynonymsaud = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Synonymsaud.toArray());
			if (listSynonymsaud != null && listSynonymsaud.size() > 0) {
				System.out.println(listSynonymsaud.get(0).getMediaUrl());
				mediaMap.put("Synonymsaud", listSynonymsaud.get(0).getMediaUrl());
			}
			// 保存同义词文本到Map中
			List<Object> Synonymstxt = new ArrayList<Object>();
			Synonymstxt.add(questionId);
			Synonymstxt.add("1");
			Synonymstxt.add("同义词");
			Synonymstxt.add("文本");
			List<Answer> listSynonymstxt = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Synonymstxt.toArray());
			if (listSynonymstxt != null && listSynonymstxt.size() > 0) {
				System.out.println(listSynonymstxt.get(0).getContent());
				mediaMap.put("Synonymstxt", listSynonymstxt.get(0).getContent());
			}
			// 保存反义词图片到Map中
			List<Object> Antonympic = new ArrayList<Object>();
			Antonympic.add(questionId);
			Antonympic.add("1");
			Antonympic.add("反义词");
			Antonympic.add("图片");
			List<Answer> listAntonympic = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Antonympic.toArray());
			if (listAntonympic != null && listAntonympic.size() > 0) {
				System.out.println(listAntonympic.get(0).getMediaUrl());
				mediaMap.put("Antonympic", listAntonympic.get(0).getMediaUrl());
			}
			// 保存反义词音频到Map中
			List<Object> Antonymaud = new ArrayList<Object>();
			Antonymaud.add(questionId);
			Antonymaud.add("1");
			Antonymaud.add("反义词");
			Antonymaud.add("音频");
			List<Answer> listAntonymaud = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Antonymaud.toArray());
			if (listAntonymaud != null && listAntonymaud.size() > 0) {
				System.out.println(listAntonymaud.get(0).getMediaUrl());
				mediaMap.put("Antonymaud", listAntonymaud.get(0).getMediaUrl());
			}
			// 保存反义词文本到Map中
			List<Object> Antonymtxt = new ArrayList<Object>();
			Antonymtxt.add(questionId);
			Antonymtxt.add("1");
			Antonymtxt.add("反义词");
			Antonymtxt.add("文本");
			List<Answer> listAntonymtxt = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Antonymtxt.toArray());
			if (listAntonymtxt != null && listAntonymtxt.size() > 0) {
				System.out.println(listAntonymtxt.get(0).getContent());
				mediaMap.put("Antonymtxt", listAntonymtxt.get(0).getContent());
			}
			// 保存拓展图片到Map中
			List<Object> Expandpic = new ArrayList<Object>();
			Expandpic.add(questionId);
			Expandpic.add("1");
			Expandpic.add("拓展");
			Expandpic.add("图片");
			List<Answer> listExpandpic = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Expandpic.toArray());
			if (listExpandpic != null && listExpandpic.size() > 0) {
				System.out.println(listExpandpic.get(0).getMediaUrl());
				mediaMap.put("Expandpic", listExpandpic.get(0).getMediaUrl());
			}
			// 保存拓展音频到Map中
			List<Object> Expandaud = new ArrayList<Object>();
			Expandaud.add(questionId);
			Expandaud.add("1");
			Expandaud.add("拓展");
			Expandaud.add("音频");
			List<Answer> listExpandaud = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Expandaud.toArray());
			if (listExpandaud != null && listExpandaud.size() > 0) {
				System.out.println(listExpandaud.get(0).getMediaUrl());
				mediaMap.put("Expandaud", listExpandaud.get(0).getMediaUrl());
			}
			// 保存拓展文本到Map中
			List<Object> Expandtxt = new ArrayList<Object>();
			Expandtxt.add(questionId);
			Expandtxt.add("1");
			Expandtxt.add("拓展");
			Expandtxt.add("文本");
			List<Answer> listExpandtxt = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", Expandtxt.toArray());
			if (listExpandtxt != null && listExpandtxt.size() > 0) {
				System.out.println(listExpandtxt.get(0).getContent());
				mediaMap.put("Expandtxt", listExpandtxt.get(0).getContent());
			}
			// 保存常用图片到Map中
			List<Object> CommonUsepic = new ArrayList<Object>();
			CommonUsepic.add(questionId);
			CommonUsepic.add("1");
			CommonUsepic.add("常用");
			CommonUsepic.add("图片");
			List<Answer> listCommonUsepic = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", CommonUsepic.toArray());
			if (listCommonUsepic != null && listCommonUsepic.size() > 0) {
				System.out.println(listCommonUsepic.get(0).getMediaUrl());
				mediaMap.put("CommonUsepic", listCommonUsepic.get(0).getMediaUrl());
			}
			// 保存常用音频到Map中
			List<Object> CommonUseaud = new ArrayList<Object>();
			CommonUseaud.add(questionId);
			CommonUseaud.add("1");
			CommonUseaud.add("常用");
			CommonUseaud.add("音频");
			List<Answer> listCommonUseaud = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", CommonUseaud.toArray());
			if (listCommonUseaud != null && listCommonUseaud.size() > 0) {
				System.out.println(listCommonUseaud.get(0).getMediaUrl());
				mediaMap.put("CommonUseaud", listCommonUseaud.get(0).getMediaUrl());
			}
			// 保存常用文本到Map中
			List<Object> CommonUsetxt = new ArrayList<Object>();
			CommonUsetxt.add(questionId);
			CommonUsetxt.add("1");
			CommonUsetxt.add("常用");
			CommonUsetxt.add("文本");
			List<Answer> listCommonUsetxt = answerService.getAllData(
					"o.questionId = ? and o.checked= ? and o.attributes=? and o.mediaType=?", CommonUsetxt.toArray());
			if (listCommonUsetxt != null && listCommonUsetxt.size() > 0) {
				System.out.println(listCommonUsetxt.get(0).getContent());
				mediaMap.put("CommonUsetxt", listCommonUsetxt.get(0).getContent());
			}
			mv.addObject("media", mediaMap);
			User user = (User) httpSession.getAttribute("user");
			int userRank = user.getUserRank();
			if (userRank == 2) {
				mv.setViewName("/front/search/wordresult/middleresourceshowexpand");
			}
			if (userRank == 3) {
				mv.setViewName("/front/search/wordresult/highresourceshowexpand");
			}
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
}
