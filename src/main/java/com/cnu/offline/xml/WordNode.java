package com.cnu.offline.xml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.cnu.offline.xml.WordNode.SlaveNode;

/**
* @author 周亮 
* @version 创建时间：2016年11月8日 上午9:16:07
* 类说明
* ios端xml文件组织的节点单元
*/
public class WordNode {
	//节点的属性
	private List<AttributeValue> attrs=new ArrayList<>();
	//从节点,一个word节点中包含多个从节点
	private List<SlaveNode> slaves = new ArrayList<>();
	/**
	 * 往wordNode中添加子节点，如果为null则不添加
	 * @param node 子节点
	 * @return 返回当前节点
	 */
	public WordNode addSlaveNode(SlaveNode node){
	  if( node !=null)
		slaves.add(node);
		return this;
	}
	/**
	 * 往wordNode中添加属性，如果为null则不添加
	 * @param attr 属性
	 * @return 返回当前节点
	 */
	public WordNode addAttribute(AttributeValue attr){
	  if(attr!=null)
		attrs.add(attr);
		return this;
	}
	public List<AttributeValue> getAttrs() {
		return attrs;
	}
	public List<SlaveNode> getSlaves() {
		return slaves;
	}



	public static class SlaveNode{
		private List<AttributeValue> attrs=new ArrayList<>();
		private List<SlaveNode> slaves = new ArrayList<>();
		public SlaveNode(AttributeValue attr) {
			attrs.add(attr);
		}
		public SlaveNode() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 * 往wordNode中添加子节点，如果为null则不添加
		 * @param node 子节点
		 * @return 返回当前节点
		 */
		public SlaveNode addSlaveNode(SlaveNode node){
		 if( node !=null)
			slaves.add(node);
			return this;
		}
		/**
		 * 往wordNode中添加属性，如果为null则不添加
		 * @param attr 属性
		 * @return 返回当前节点
		 */
		public SlaveNode addAttribute(AttributeValue attr){
		  if(attr!=null)
			attrs.add(attr);
			return this;
		}
		public List<AttributeValue> getAttrs() {
			return attrs;
		}
		public List<SlaveNode> getSlaves() {
			return slaves;
		}
	}
	/**
	 * 将pe对象的属性和值添加到wn对象中，
	 * @param wn  被填充的wordNode对象
	 * @param pe  要填充的对象
	 * @param excludeFields 过滤掉pe中的属性名集合
	 */
	public static <T> void fillProperty2Wn(WordNode wn,T pe,List<String> excludeFields){
		Class clazz = pe.getClass();
	    Field[] fields =clazz.getDeclaredFields();
	    
	    for(Field field :fields ){
	    	String fieldName =field.getName();
	    	
	    	if(excludeFields==null || excludeFields.size()<=0 || !excludeFields.contains(fieldName)){
	    		try {
	    			field.setAccessible(true);
	    			Object obj =field.get(pe);
	    			
	    			String fieldValue ="";
	    			if( obj!=null )
		    			if(field.getType()== String.class){
							fieldValue =(String) obj;
		    			}else{
		    				fieldValue=obj.toString();
		    			}
	    			//new SlaveNode(new AttributeValue("name", fieldName)).addAttribute(new AttributeValue("value", fieldValue));
					wn.addSlaveNode(new SlaveNode(new AttributeValue("name", fieldName)).addAttribute(new AttributeValue("value", fieldValue)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
	    	}
	    }
	}
}
