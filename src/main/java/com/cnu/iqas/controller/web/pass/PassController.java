package com.cnu.iqas.controller.web.pass;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.PageView;
import com.cnu.iqas.bean.iword.Iword;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.PageViewConstant;
import com.cnu.iqas.formbean.iword.IwordForm;
import com.cnu.iqas.service.iword.IwordService;
import com.cnu.iqas.service.iword.WordResourceService;

@Controller
@RequestMapping(value="/pass/control/passControl/")
public class PassController {
	String ResourcePath;
	String wordPicture1;
	String wordPicture2;
	private IwordService iwordService;
	private WordResourceService wordResourceService;
	String horse; 
	@RequestMapping(value="list")
	public ModelAndView listWordResource(String content){
	ModelAndView mv = new ModelAndView(PageViewConstant.PASS_LIST);
	Iword iword=iwordService.find("o.content = ?", "horse");
	String wordId=iword.getId();
    System.out.println("backpack单词的ID"+wordId); 
	WordResource wordResource=wordResourceService.find("o.wordId = ?", wordId);
	do
	  {	
		WordResource wr1=wordResourceService.findByContent();
		wordPicture1=wr1.getSavepath();
		WordResource wr2=wordResourceService.findByContent();
		wordPicture2=wr2.getSavepath();		               
		ResourcePath=wordResource.getSavepath();
		
	  }while(wordPicture1.equals(wordPicture2)||wordPicture1.equals(ResourcePath)||wordPicture2.equals(ResourcePath));
		//usersArray.add("wordpicture", wordpicture.getSavepath());
	System.out.println("图片1"+wordPicture1);
	System.out.println("图片2"+wordPicture2); 
	mv.addObject("wordId", wordId);
	mv.addObject("ResourcePath", ResourcePath);
	mv.addObject("wordPicture1", wordPicture1);
	mv.addObject("wordPicture2", wordPicture2);
    return mv;
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
	
}