package com.cnu.ds.parse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

import com.cnu.ds.ParseXmlCallback;
import com.user.entity.UserWord;

/**
* @author 周亮 
* @version 创建时间：2016年10月25日 下午7:20:39
* 类说明
*/
public class UserWordParseCallback implements ParseXmlCallback<UserWord> {
	
	private SimpleDateFormat sdf;
	
	public UserWordParseCallback(SimpleDateFormat sdf) {
		super();
		this.sdf = sdf;
	}
	public UserWordParseCallback() {
		super();
	}

	@Override
	public List<UserWord> doParse(Document document) throws Exception {
		List<UserWord> userWords = new ArrayList<>();
		//解析t_userword表数据
	       //选取拥有名为 name 的属性的 table 元素,且name属性值为t_userword。
	       Node table = document.selectSingleNode("/database/table[@name='user_word']");
	       if( table !=null){
	    		//选取table的row子节点
	        	List<Node> rows = table.selectNodes("row");
	        	if( sdf ==null)
	        	 sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	        	for( Node row : rows ){
	        		String userId =row.valueOf("@userId");
	        		String word =row.valueOf("@word");
	        		String topicLevel =row.valueOf("@topicLevel");
	        		String time =row.valueOf("@time");
	        		String test =row.valueOf("@test");
	        		String wordLearn =row.valueOf("@wordLearn");
	        		UserWord userWord = new UserWord(userId, word, Integer.parseInt(topicLevel),Integer.parseInt(wordLearn), Integer.parseInt(test), sdf.parse(time));
	        		userWords.add(userWord);
	        	}
	       }
	       return userWords;
	}

}
