package com.cnu.iqas.controller.mobile.pass;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.bean.ontology.ISentence;
import com.cnu.iqas.bean.user.User;
import com.cnu.iqas.constant.StatusConstant;
import com.cnu.iqas.service.common.IUserBaseService;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.service.iword.WordResourceService;
import com.cnu.iqas.service.ontology.SentenceSim;
import com.cnu.iqas.utils.JsonTool;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.noumenon.OntologyManage.OntologyManage;
import com.noumenon.OntologyManage.Impl.OntologyManageImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 王文辉
 * @version 创建时间：2016年1月22日 下午10:46:28 类说明
 * @version 修改时间：2016年3月8日 下午1:45:28 类说明
 */
@Controller
@RequestMapping(value = "/mobile/pass/")
public class MPassController {
	private SentenceSim sentenceSim;
	String pictruepath;
	String picwrongpath1;
	String picwrongpath2;
	String picwrongcontent1;
	String picwrongcontent2;
	private IwordService iwordService;
	private WordResourceService wordResourceService;
	private IUserBaseService userService;
	private String content;
	private static String[] propertyLabel = new String[] { "单词ID", "单词", "主题-功能意念", "主题-话题", "Hownet中的父类", "词性", "词性属性",
			"中文含义", "单词教材版本", "单词册数", "难度", "课文原句", "情境段落", "联想", "同义词", "反义词", "拓展", "百科", "用法", "延伸例句", "常用" };
	private static String[] propertySPARQLValue = { "?propertyID", "?instanceLabel", "?propertyFunction",
			"?propertyTopic", "?propertyClass", "?propertyPartsOfSpeech", "?propertyWordProperty", "?propertyChinese",
			"?propertyVersion", "?propertyBook", "?propertyDifficulty", "?propertyText", "?propertyScene",
			"?propertyAssociate", "?propertyAntonym", "?propertySynonyms", "?propertyExtend", "?propertyNcyclopedia",
			"?propertyUse", "?propertyExpand", "?propertyCommonUse" };
	private static String[] sentencePropertyLabel = new String[] { "句子ID", "句子教材版本", "句子册数", "问题", "问题句型", "回答", "情境对话",
			"重要句型", "相关单词" };
	private static String[] sentencePropertySPARQLValue = { "?propertyID", "?propertyVersion", "?propertyBook",
			"?instanceLabel", "?propertyClass", "?propertyAnswer", "?propertyScene", "?propertySentencePattern",
			"?propertyRelatedWords" };

	// 根据年级查询5个单词
	@SuppressWarnings("finally")
	@RequestMapping(value = "wordByGrade")
	public ModelAndView wordByGrade(String grade) {
		System.out.println("进入wordByGrade");
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的Json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		JSONObject wordJson = new JSONObject();
		try {
			OntologyManage ontologyManage = new OntologyManageImpl();
			// 查询该年级随机5个单词
			List<ResultSet> resultsFiveWordsOfThisGrade = ontologyManage.QueryFiveWordsOfThisGrade("4");
			// 打印
			System.out.println("4" + "年级的5个单词:" + "\n");
			for (int wordIndex = 0; wordIndex < resultsFiveWordsOfThisGrade.size(); wordIndex++) {
				while (resultsFiveWordsOfThisGrade.get(wordIndex).hasNext()) {
					QuerySolution solutionFiveWordsOfThisGrade = resultsFiveWordsOfThisGrade.get(wordIndex).next();
					String word = subStringManage(solutionFiveWordsOfThisGrade.get(propertySPARQLValue[1]).toString());
					if (word.indexOf("_") > 0) {
						word = word.replaceAll("_", " ");
						System.out.println(word);
					}
					System.out.println("    ————" + propertyLabel[1] + "：" + word);
					System.out.println("\n");
					wordJson.put("word", word);
					usersArray.add(wordJson);
				}
			}
		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	// 根据年级查询2条句子
	@SuppressWarnings("finally")
	@RequestMapping(value = "SentencesByGrade")
	public ModelAndView SentencesByGrade(String grade) {
		System.out.println("进入SentencesByGrade");
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的Json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		JSONObject SentencesJson = new JSONObject();
		try {
			OntologyManage ontologyManage = new OntologyManageImpl();
			// 查询该年级随机2个句子
			List<ResultSet> resultsTwoSentencesOfThisGrade = ontologyManage.QueryTwoSentencesOfThisGrade(grade);
			if (resultsTwoSentencesOfThisGrade.size() == 0) {
				System.out.println("该年级的句子结果集为空！不打印！");
			} else {
				// 打印
				System.out.println(grade + "年级的2个句子:" + "\n");
				for (int sentenceIndex = 0; sentenceIndex < resultsTwoSentencesOfThisGrade.size(); sentenceIndex++) {
					while (resultsTwoSentencesOfThisGrade.get(sentenceIndex).hasNext()) {
						QuerySolution solutionTwoSentencesOfThisGrade = resultsTwoSentencesOfThisGrade
								.get(sentenceIndex).next();
						String Sentences = subStringManage(
								solutionTwoSentencesOfThisGrade.get(sentencePropertySPARQLValue[5]).toString());
						if (Sentences.indexOf("_") > 0) {
							Sentences = Sentences.replaceAll("_", " ");
							System.out.println(Sentences);
						}
						System.out.println("    ————" + sentencePropertyLabel[5] + "：" + Sentences);
						System.out.println("\n");
						SentencesJson.put("Sentences", Sentences);
						usersArray.add(SentencesJson);
					}
				}
			}
		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	// 根据传回的问句来计算相似度
	@SuppressWarnings("finally")
	@RequestMapping(value = "simanswer")
	public ModelAndView simanswer(String question, HttpServletRequest request) {
		System.out.println("进入simanswer");
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的Json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		JSONObject simvalue = new JSONObject();
		try {

			System.out.println("问句查询");
			ISentence sentence = sentenceSim.maxSimilar(question, request);
			HttpSession session = request.getSession();
			System.out.println("+++++++++++++" + session.getAttribute("similarityDegree"));
			simvalue.put("similarityDegree", session.getAttribute("similarityDegree"));
			usersArray.add(simvalue);
			System.out.println(sentence);
		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "listWordResource")
	// 根据单词查询，展示要显示图片信息内容
	public ModelAndView listWordResource(String content) {
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		try {
			Iword iword = iwordService.find("o.content = ?", content);
			if (iword != null) {
				String wordId = iword.getId();
				do { // 根据单词id来查询单词的对象
					WordResource wordpictrue = wordResourceService.find("o.wordId = ?", wordId);
					if (wordpictrue != null) {
						// 随机查询两个图片对象
						WordResource wordpicwrong1 = wordResourceService.findByContent();
						WordResource wordpicwrong2 = wordResourceService.findByContent();
						if (wordpicwrong1 != null && wordpicwrong2 != null) {
							// 获取单词图片的保存路径
							pictruepath = wordpictrue.getSavepath();
							picwrongpath1 = wordpicwrong1.getSavepath();
							picwrongpath2 = wordpicwrong2.getSavepath(); 
						} else {
							break;
						}
					} else {
						break;
					}
				} while (pictruepath.equals(picwrongpath1) || picwrongpath1.equals(picwrongpath2)
						|| pictruepath.equals(picwrongpath2));
				// 根据单词资源的保存路径来查询单词
				WordResource wordpicwrongpath1 = wordResourceService.find("o.savepath = ?", picwrongpath1);
				WordResource wordpicwrongpath2 = wordResourceService.find("o.savepath = ?", picwrongpath2);
				// 根据单词资源表中的资源路径来查询单词content属性
				if (wordpicwrongpath1 != null && wordpicwrongpath2 != null) {
					Iword word1 = iwordService.find("o.id=?", wordpicwrongpath1.getWordId());
					Iword word2 = iwordService.find("o.id=?", wordpicwrongpath2.getWordId());
					if (word1 != null && word2 != null) {
						picwrongcontent1 = word1.getContent();
						picwrongcontent2 = word2.getContent();
						System.out.println("原来的单词" + content);
						System.out.println("第一个错误图片对应的单词" + picwrongcontent1);
						System.out.println("第二个错误图片对应的单词" + picwrongcontent2);
					}
				}
				JSONObject picJson = new JSONObject();
				// 单词内容存放在容器
				picJson.put("content", content);
				picJson.put("wordpicture", pictruepath);
				picJson.put("picwrongcontent1", picwrongcontent1);
				picJson.put("wordpicture1", picwrongpath1);
				picJson.put("picwrongcontent2", picwrongcontent2);
				picJson.put("wordpicture2", picwrongpath2);
				usersArray.add(picJson);
			}

		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	// 该方法用于前测模块的返回数据，数据包括金币数量和场景。方法的参数是用户名、金币数、场景
	@SuppressWarnings("finally")
	@RequestMapping(value = "coinAndScene")
	public ModelAndView savecoinAndScene(String userName, int coin, int scene) {
		System.out.println("进入savecoinAndScene");
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的Json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		JSONObject coinAndScene = new JSONObject();
		try {
			User user = (User) userService.findByName(userName);
			System.out.println(user.getPassword());
			System.out.println(user.getUserId());
			user.setAllCoins(coin);
			user.setGameScene(scene);
			System.out.println("金币数" + user.getAllCoins());
			System.out.println("场景" + user.getGameScene());
			userService.SaveCoinAndScene(user);
			coinAndScene.put("coin", user.getAllCoins());
			coinAndScene.put("scene", user.getGameScene());
			usersArray.add(coinAndScene);
			System.out.println(userService);
		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}
/*
	@SuppressWarnings("finally")
	@RequestMapping(value = "listTestWordattribute")
	public ModelAndView listTestWordattribute(int num) {
		System.out.println("listTestWordattribute");
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		JSONObject picJson = new JSONObject();
		try {
			
			 * 1、根据前台传回数据，获得此时的学习场景，一共五个关卡（1，2，3，4，5） 2、根据前台传回数据，获得此时登录人的学号。
			 
			// System.out.println("try..........."+num);
			TestFourGroup tests = new TestFourGroupImpl();
			String[] knowledges = new String[5];
			knowledges[0] = "boat";
			knowledges[1] = "horse";
			knowledges[2] = "fly";
			knowledges[3] = "sleep";
			knowledges[4] = "family";
			LearnTest lt = new LearnTest();
			System.out.println("进入方法前。。。。。。。。。");
			lt = tests.TestFourGroup("2141003034", knowledges, num);
			System.out.println("生成的测试四元组为：" + lt.getTestKnowledgeId() + "/" + lt.getTestType() + "/"
					+ lt.getTestAspect() + "/" + lt.getTestDifficulty());
			picJson.put("testKnowledgeId", lt.getTestKnowledgeId());
			picJson.put("testType", lt.getTestType());
			picJson.put("testAspect", lt.getTestAspect());
			picJson.put("difficulty", lt.getTestDifficulty());
			usersArray.add(picJson);
		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "middletest")
	public ModelAndView saveMiddleTest(String UserId, String userName, String TestKnowledgeId, int TestType,
			int TestAspect, int TestDifficulty, int Pass, int tempcoin) {
		System.out.println("进入saveMiddleTest");
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的Json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		JSONObject MiddleTest = new JSONObject();
		try {
			User user = (User) userService.findByName(userName);
			int coin = user.getAllCoins();
			System.out.println(user.getPassword());
			System.out.println(user.getUserId());
			if (Pass == 1) {
				coin = coin + tempcoin;
			}
			user.setAllCoins(coin);
			System.out.println("金币数" + user.getAllCoins());
			userService.SaveCoinAndScene(user);
			usersArray.add(MiddleTest);
			System.out.println(userService);
			// 获取当前日期
			java.util.Date d = new java.util.Date();
			long dtime = d.getTime();
			Date date = new Date(dtime);
			UpdateLearnAction ula = new UpdateLearnActionImpl();
			ula.UpdateTest(UserId, TestKnowledgeId, TestType, TestAspect, TestDifficulty, Pass, date);
			MiddleTest.put("UserId", UserId);
			MiddleTest.put("TestKnowledgeId", TestKnowledgeId);
			MiddleTest.put("TestType", TestType);
			MiddleTest.put("TestAspect", TestAspect);
			MiddleTest.put("TestDifficulty", TestDifficulty);
			MiddleTest.put("coin", user.getAllCoins());
			usersArray.add(MiddleTest);
		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}
*/
	// 该方法用于大测模块的返回数据，数据包括场景增加。方法的参数是用户名、判断值（1代表准确，0 代表错误）、场景
	@SuppressWarnings("finally")
	@RequestMapping(value = "finalScene")
	public ModelAndView savefinalScene(String userName, int num) {
		System.out.println("进入savefinalScene");
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的Json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		JSONObject finalScene = new JSONObject();
		try {
			User user = (User) userService.findByName(userName);
			int scene = user.getGameScene();
			System.out.println(user.getPassword());
			System.out.println(user.getUserId());
			if (num == 1) {
				scene += 1;
			}
			user.setGameScene(scene);
			System.out.println("场景" + user.getGameScene());
			userService.SaveCoinAndScene(user);
			finalScene.put("scene", user.getGameScene());
			usersArray.add(finalScene);
			System.out.println(userService);
		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	// 该方法用于隐藏关模块的返回数据，数据包括勋章。方法的参数是用户名、判断值（1代表准确，0 代表错误）、勋章
	@SuppressWarnings("finally")
	@RequestMapping(value = "hideGrade")
	public ModelAndView savehideGrade(String userName, int num) {
		System.out.println("进入savehideGrade");
		ModelAndView mv = new ModelAndView("share/json");
		MyStatus status = new MyStatus();
		// 总的Json对象
		JSONObject jsonObject = new JSONObject();
		JSONArray usersArray = new JSONArray();
		JSONObject hideGrade = new JSONObject();
		try {
			User user = (User) userService.findByName(userName);
			int grade = user.getStoreGrade();
			System.out.println(user.getPassword());
			System.out.println(user.getUserId());
			System.out.println(user.getStoreGrade());
			if (num == 1) {
				grade += 1;
			}
			user.setStoreGrade(grade);
			System.out.println("勋章" + user.getStoreGrade());
			userService.SaveCoinAndScene(user);
			hideGrade.put("grade", user.getStoreGrade());
			usersArray.add(hideGrade);
			System.out.println(userService);
		} catch (Exception e) {
			status.setMessage("未知异常");
			status.setStatus(StatusConstant.UNKONWN_EXECPTION);
		} finally {
			// -------------------返回视图
			JsonTool.putJsonObject(jsonObject, usersArray, status);
			mv.addObject("json", jsonObject.toString());
			return mv;
		}
	}

	public IwordService getIwordService() {
		return iwordService;
	}

	@Resource
	public void setIwordService(IwordService iwordService) {
		this.iwordService = iwordService;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public WordResourceService getWordResourceService() {
		return wordResourceService;
	}

	@Resource
	public void setWordResourceService(WordResourceService wordResourceService) {
		this.wordResourceService = wordResourceService;
	}

	public IUserBaseService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserBaseService userService) {
		this.userService = userService;
	}

	public SentenceSim getSentenceSim() {
		return sentenceSim;
	}

	@Resource
	public void setSentenceSim(SentenceSim sentenceSim) {
		this.sentenceSim = sentenceSim;
	}

	// 处理字符串
	private static String subStringManage(String string) {
		String newString = string.substring(string.indexOf(")") + 1, string.lastIndexOf("@"));
		return newString;
	}
}