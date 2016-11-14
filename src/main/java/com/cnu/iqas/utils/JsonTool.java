/**
 * 
 */
package com.cnu.iqas.utils;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;
import com.cnu.iqas.bean.iword.WordResource;
import com.cnu.iqas.constant.PageViewConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * @author zhouliang
 * @2015年4月8日
 */
public class JsonTool {

	/**
	 * 将json数据状态加入到JSONObject中
	 * @param status	json数据状态描述类
	 * @param object	JSONObject
	 */
	public static void putStatusJson(MyStatus status,JSONObject object)
	{
		if(object !=null || status !=null)
		{
			object.put("status", status.getStatus());
			object.put("message", status.getMessage());
		}else{
			throw new RuntimeException("返回操作标识不能为空。");
		}
	}
	/**
	 * 封装json返回视图
	 * @param status
	 * @return
	 */
	public static ModelAndView generateModelAndView(MyStatus status){
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		//整个json
		JSONObject jsonObject = new JSONObject();
		JsonTool.putStatusJson(status, jsonObject);
		
		mv.addObject("json", jsonObject.toString());
		return mv;
	}	
	/**
	 * 将集合元素封装成json格式数据
	 * @param list  要封装的数组集合
	 * @param config  类在转换成json数据时遵循的一些配置规则
	 * @param status  状态标识
	 * @return
	 * 返回数据类似如下格式：data中的内容就是list集合中的内容，count表示data中的条数，也就是list集合中数据数
	 * {
		 *  status:1,
		 *  message:"ok",
		 *  result:{
		 *   count:2,
		 *   data:[
		 *    
		 *		{id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      {id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      
		 *   ]
		 *  }
		 * }
	 */
	@Deprecated
	public static <T> String createJson(List<T> list,JsonConfig config,MyStatus status){
	
		//整个json
		JSONObject jsonObject = new JSONObject();
		//result json
		JSONObject resultObject = new JSONObject();
		//数组json
		JSONArray jsonArray = new JSONArray();
		
		int count =0;
		if( list !=null ){
			for( T entity :list )
			{
				JSONObject entityJson;
				if( config ==null)
					 entityJson = JSONObject.fromObject(entity);
				else
				    entityJson = JSONObject.fromObject(entity, config);
				jsonArray.add(entityJson);
				count++;
			}
		}
		resultObject.put("count", count);
		resultObject.put("data", jsonArray);
		jsonObject.put("result", resultObject);
	 
		JsonTool.putStatusJson(status, jsonObject);
		return jsonObject.toString();
	}
	/**
	 * 将集合元素封装成json对象并返回
	 * @param list  要封装的数组集合
	 * @param config  类在转换成json数据时遵循的一些配置规则
	 * @param status  状态标识
	 * @return  json对象
	 * 返回json对象的内部格式如下：data中的内容就是list集合中的内容，count表示data中的条数，也就是list集合中数据数
	 * {
		 *  status:1,
		 *  message:"ok",
		 *  result:{
		 *   count:2,
		 *   data:[
		 *		{id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      {id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      
		 *   ]
		 *  }
		 * }
	 */
	@Deprecated
	public static <T> JSONObject createJsonObject(List<T> list,JsonConfig config,MyStatus status){
		
		//整个json
		JSONObject jsonObject = new JSONObject();
		//result json
		JSONObject resultObject = new JSONObject();
		//数组json
		JSONArray jsonArray = new JSONArray();
		
		int count =0;
		if( list !=null ){
			for( T entity :list )
			{
				JSONObject entityJson;
				if( config ==null)
					 entityJson = JSONObject.fromObject(entity);
				else
				    entityJson = JSONObject.fromObject(entity, config);
				jsonArray.add(entityJson);
				count++;
			}
		}
		resultObject.put("count", count);
		resultObject.put("data", jsonArray);
		jsonObject.put("result", resultObject);
	 
		JsonTool.putStatusJson(status, jsonObject);
		return jsonObject;
	}	
	
	
	/**
	 * 将集合元素封装成json对象并生成返回视图
	 * @param listJson 要封装的JSONObject集合
	 * @param status 此次操作状态
	 * @return：返回数据格式
	 * {
		 *  status:1,
		 *  message:"ok",
		 *  result:{
		 *   count:2,
		 *   data:[
		 *		{id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      {id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      
		 *   ]
		 *  }
		 * }
	 */
	public static ModelAndView generateModelAndView(List<JSONObject> listJson,MyStatus status){
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		
		//整个json
		JSONObject jsonObject = new JSONObject();
		//result json
		JSONObject resultObject = new JSONObject();
		//数组json
		JSONArray jsonArray = new JSONArray();
		
		if( listJson !=null ){
			for( JSONObject entity :listJson )
			  jsonArray.add(entity);
		}
		resultObject.put("count", jsonArray.size());
		resultObject.put("data", jsonArray);
		
		jsonObject.put("result", resultObject);
	 
		JsonTool.putStatusJson(status, jsonObject);
		mv.addObject("json", jsonObject.toString());
		return mv;
	}	
	
	/**
	 * 将一个对象封装成json对象并生成返回视图
	 * @param key ，JSONObject对象对应的key值
	 * @param singleObject 要封装的JSONObject对象
	 * @param status 此次操作状态
	 * @return：返回数据格式
	 * {
		 *  status:1,
		 *  message:"ok",
		 *  result:{
		 *	  "key":{id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"}
		 *      
		 *  }
		 * }
	 */
	public static ModelAndView generateModelAndView(String key,JSONObject singleObject,MyStatus status){
		ModelAndView mv = new ModelAndView(PageViewConstant.JSON);
		
		//整个json
		JSONObject jsonObject = new JSONObject();
		//result json
		JSONObject resultObject = new JSONObject();
		//将单条记录存进result中
		resultObject.put(key, singleObject);
		jsonObject.put("result", resultObject);
	 
		JsonTool.putStatusJson(status, jsonObject);
		mv.addObject("json", jsonObject.toString());
		return mv;
	}	
	/**
	 * 将jsonArray和status对象按照格式封装到jsonObject中
	 * @param jsonObject 要封装到的json对象
	 * @param jsonArray json数组对象，对应data内容
	 * @param status  此次操作的描述
	 * @return
	 * {
	 *  status:1,
	 *  message:"ok",
	 *  result:{
	 *   count:1,
	 *   data:{
	 *     ....
	 *   }
	 *  }
	 *  
	 * }
	 * 
	 */
  public static void createJsonObject( JSONObject jsonObject,JSONArray jsonArray,MyStatus status){
	
		//result json
		JSONObject resultObject = new JSONObject();
		if(jsonArray!=null){
		resultObject.put("count", jsonArray.size());
		resultObject.put("data", jsonArray);
		jsonObject.put("result", resultObject);
		}
	 
		JsonTool.putStatusJson(status, jsonObject);
	}
 

  
	/**
	 * 将jsonArray和status对象按照格式封装到jsonObject中
	 * @param jsonObject 要封装到的json对象
	 * @param resultJson result对象
	 * @param status  此次操作的描述
	 * @return
	 * {
	 *  status:1,
	 *  message:"ok",
	 *  result:{
	 *   count:1,
	 *   data:{
	 *     ....
	 *   }
	 *  }
	 *  
	 * }
	 * 
	 */
  @Deprecated
  public static void createJsonObject( JSONObject jsonObject,JSONObject resultJson,MyStatus status){
		//result json
		jsonObject.put("result", resultJson);
	 
		JsonTool.putStatusJson(status, jsonObject);
	}
    //已经被删除的方法
  /**
	 * 将jsonArray和status对象按照格式封装到jsonObject中
	 * @param jsonObject 要封装到的json对象
	 * @param jsonArray json数组对象，对应data内容
	 * @param status  此次操作的描述
	 * @return
	 * {
	 *  status:1,
	 *  message:"ok",
	 *  result:{
	 *   count:1,
	 *   data:{
	 *     ....
	 *   }
	 *  }
	 *  
	 * }
	 * 
	 */
 @Deprecated
public static void putJsonObject( JSONObject jsonObject,JSONArray jsonArray,MyStatus status){
	
		//result json
		JSONObject resultObject = new JSONObject();
		resultObject.put("count", jsonArray.size());
		resultObject.put("data", jsonArray);
		jsonObject.put("result", resultObject);
	 
		JsonTool.putStatusJson(status, jsonObject);
	}
	/**
	 * 将jsonArray和status对象按照格式封装到jsonObject中
	 * @param jsonObject 要封装到的json对象
	 * @param resultJson result对象
	 * @param status  此次操作的描述
	 * @return
	 * {
	 *  status:1,
	 *  message:"ok",
	 *  result:{
	 *   count:1,
	 *   data:{
	 *     ....
	 *   }
	 *  }
	 *  
	 * }
	 * 
	 */
 @Deprecated
public static void putJsonObject( JSONObject jsonObject,JSONObject resultJson,MyStatus status){
		//result json
		jsonObject.put("result", resultJson);
	 
		JsonTool.putStatusJson(status, jsonObject);
	}
	/**
	 * 
	  * 将集合元素封装成json格式数据
	 * @param list  要封装的数组集合
	 * @param status  状态标识
	 * @return
	 * 返回数据类似如下格式：data中的内容就是list集合中的内容，count表示data中的条数，也就是list集合中数据数
	 * {
		 *  status:1,
		 *  message:"ok",
		 *  result:{
		 *   count:2,
		 *   data:[
		 *    
		 *		{id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      {id:"2353sdkfhosdf",name:boat.jpg,type=1,savepath:"http://172.19.68.77:8080/zhushou/images/logo.jpg"},
		 *      
		 *   ]
		 *  }
		 * }
	 */
    @Deprecated
	public static <T> String createJson(List<T> list,MyStatus status){
		return JsonTool.createJson( list,null,status);
	}
    
    /**
     * 生成带状态的单个对象的json值
     * @param obj 单个对象
     * @param config json配置
     * @param status 状态
     * @return
     * 
     * {
     *  status:"1",
     *  message:"ok",
     *  objName:{ obj的json值，受config影响 } 
     *  
     * }
     */
  	public static <T> String singleObj(T obj,JsonConfig config,MyStatus status){
  		JSONObject json = new JSONObject();
  		JsonTool.putStatusJson(status, json);
  		//获取obj的类名
  		String className =obj.getClass().getName();
  		JSONObject jsonObj=JSONObject.fromObject(obj, config);
  		json.put(className, jsonObj);
  		return json.toString();
  	}
  	 /**
     * 生成带状态的单个对象的json值
     * @param obj 单个对象
     * @param status 状态
     * @return
     * 
     * {
     *  status:"1",
     *  message:"ok",
     *  objName:{ obj的json值 } 
     *  
     * }
     */
  	public static <T> String singleObj(T obj,MyStatus status){
  		JSONObject json = new JSONObject();
  		JsonTool.putStatusJson(status, json);
  		//获取obj的类名
  		String className =obj.getClass().getName();
  		JSONObject jsonObj=JSONObject.fromObject(obj);
  		json.put(className, jsonObj);
  		return json.toString();
  	}
  	
  	/**
  	 * 返回状态和一个属性值
  	 * @param attrName 属性名
  	 * @param attrValue 属性值
  	 * @param status 状态
  	 * @return
  	 * 
  	 * {
  	 *   status:"1",
  	 *   message:"ok",
  	 *   attrName:attrValue
  	 * }
  	 */
  	public static String singleAttribute(String attrName,Object attrValue,MyStatus status){
  		JSONObject json = new JSONObject();
  		JsonTool.putStatusJson(status, json);
  		json.put(attrName, attrValue);
  		return json.toString();
  	}
    

}
