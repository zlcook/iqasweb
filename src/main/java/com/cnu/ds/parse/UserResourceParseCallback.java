package com.cnu.ds.parse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

import com.cnu.ds.ParseXmlCallback;
import com.cnu.ds.untils.ParseUntils;
import com.user.entity.UserBehaviour;
import com.user.entity.UserResource;

/**
* @author 周亮 
* @version 创建时间：2016年10月25日 下午8:48:55
* 类说明
*/
public class UserResourceParseCallback implements ParseXmlCallback<UserResource> {
	private SimpleDateFormat sdf;
	
	public UserResourceParseCallback(SimpleDateFormat sdf) {
		super();
		this.sdf = sdf;
	}
	public UserResourceParseCallback() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public List<UserResource> doParse(Document document) throws Exception {
		 List<UserResource> urs = new ArrayList<>();
			//解析t_userword表数据
		       //选取拥有名为 name 的属性的 table 元素,且name属性值为t_userword。
		       Node table = document.selectSingleNode("/database/table[@name='user_resource']");
		       if( table !=null){
		    		//选取table的row子节点
		        	List<Node> rows = table.selectNodes("row");
		        	if( sdf ==null)
			        	 sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		        	for( Node row : rows ){
		        		String userId =row.valueOf("@userId");
		        		String word =row.valueOf("@word");
		        		String resourceId =row.valueOf("@resourceId");
		        		String mediaType =row.valueOf("@mediaType");
		        		String doWhere =row.valueOf("@mediaType");
		        		String learnStartTime =row.valueOf("@learnStartTime");
		        		String learnEndTime =row.valueOf("@learnEndTime");
		        		String learnState =row.valueOf("@learnState");
		        		
		        		UserResource ur = new UserResource(userId, word, resourceId, ParseUntils.parseInt(mediaType), ParseUntils.parseDate(learnStartTime, sdf), ParseUntils.parseDate(learnEndTime, sdf) , 0L,0);
		        		urs.add(ur);
		        	}
		       }
		     return urs;
	}

}
