package com.cnu;

import java.util.ArrayList;
import java.util.List;

/**
* @author 周亮 
* @version 创建时间：2016年9月16日 下午6:11:31
* 类说明
*/
public class MemoryGCTest {
	
	static class OOMObject{
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<OOMObject> list = new ArrayList<>();
		
		while(true){
			list.add(new OOMObject());
		}
	}
	
}
