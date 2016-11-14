package com.cnu.ds.parse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

import com.cnu.ds.ParseXmlCallback;
import com.user.entity.UserBehaviour;

/**
* @author 周亮 
* @version 创建时间：2016年10月25日 下午7:28:14
* 类说明
*/
public class UserBehaviourParseCallback implements ParseXmlCallback<UserBehaviour> {
	
	private SimpleDateFormat sdf;
	
	public UserBehaviourParseCallback(SimpleDateFormat sdf) {
		super();
		this.sdf = sdf;
	}
	public UserBehaviourParseCallback() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<UserBehaviour> doParse(Document document) throws Exception {
		 List<UserBehaviour> ubs = new ArrayList<>();
			//解析t_userword表数据
		       //选取拥有名为 name 的属性的 table 元素,且name属性值为t_userword。
		       Node table = document.selectSingleNode("/database/table[@name='user_behaviour']");
		       if( table !=null){
		    		//选取table的row子节点
		        	List<Node> rows = table.selectNodes("row");

		        	if( sdf ==null)
		        	 sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		        	for( Node row : rows ){
		        		String userId =row.valueOf("@userId");
		        		String doWhat =row.valueOf("@doWhat");
		        		String doWhen =row.valueOf("@doWhen");
		        		String doWhere =row.valueOf("@doWhere");
		        		UserBehaviour ub = new UserBehaviour(userId, doWhere, doWhat, sdf.parse(doWhen));
		        		ubs.add(ub);
		        	}
		       }
		     return ubs;
	}

}
