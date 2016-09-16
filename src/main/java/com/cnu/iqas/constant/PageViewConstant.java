package com.cnu.iqas.constant;
/**
* @author 周亮 
* @version 创建时间：2015年11月24日 下午8:37:48
* 类说明  :项目中所有跳转页面路径常量，和生成重定向路径的方法、生成带过滤后缀界面路径的方法
*/
public class PageViewConstant {

	/**
	 * 重定向标识
	 */
	public final static String REDIRECT ="redirect:/";
	/**
	 * 链接过滤的后缀
	 */
	public final static String FILTER_SUFFIX =".html";
	
	
	/**
	 * 后台消息提示页面
	 */
	public final static String MESSAGE="share/message"; 
	/**
	 * 添加单词资源界面
	 */
	public final static String WORDRESOURCE_ADD="admincenter/word/addresource"; 
	/**
	 * 添加单词属性资源界面 
	 */
	public final static String WORDATTRIBUTERESOURCE_ADD="admincenter/word/addattributeresource"; 
	/**
	 * 单词资源详情界面
	 */
	public final static String WORD_RESOURCE_DETAILUI="admincenter/word/resourcedetail"; 
	/**
	 * 单词列表界面
	 */
	public final static String WORD_LIST_UI="admincenter/word/wordlist"; 

	/**
	 * 添加一个单词界面
	 */
	public final static String WORD_ADD_UI="admincenter/word/addword";
	
	/**
	 * 从execl中导入单词界面
	 */
	public final static String WORD_IMPORT_WORD_UI="admincenter/word/importword";
	
	/**
	 * 统计每个版本有多少单词界面
	 */
	public final static String WORD_STATISTIC="admincenter/word/statisticsword";
	
	/**
	 * 管理员登录界面
	 */
	public final static String ADMIN_LOGIN_UI="admincenter/login";
	/**
	 * 后台管理中心
	 */
	public final static String CONTROL_CENTER_MAIN="admincenter/main";
	//--------------单词主题涉及界面
	/**
	 * 单词主题添加界面
	 */
	public final static String WORDTHEME_ADD_UI="admincenter/wordtheme/addwordtheme"; 
	/**
	 * 单词主题编辑界面
	 */
	public final static String WORDTHEME_EDIT_UI="admincenter/wordtheme/editwordtheme"; 
	/**
	 * 单词主题列表界面
	 */
	public final static String WORDTHEME_LIST="admincenter/wordtheme/wordthemelist"; 
	
	/**
	 * 单词主题下的单词列表界面
	 */
	public final static String WORDTHEME_WORDS_LIST="admincenter/wordtheme/themewordlist"; 

	/**
	 * 从execl中导入单词主题
	 */
	public final static String WORDTHEME_IMPORT_UI="admincenter/wordtheme/importtheme";
	/*---------商店涉及界面--------------*/

	/**
	 * 添加商品类型界面
	 */
	public final static String COMMODITYTYPE_ADD_UI="admincenter/store/addcommoditytype"; 
	/**
	 * 商品类型列表界面
	 */
	public final static String COMMODITYTYPE_LIST="admincenter/store/commoditytypelist"; 
	/**
	 * 商品类型下的商品列表界面
	 */
	public final static String COMMODITYTYPE_COMMODITY_LIST="admincenter/store/commoditysoftype";
	/**
	 * 添加商品界面
	 */
	public final static String COMMODITY_ADD_UI="admincenter/store/addcommodity";
	/**
	 * 闯关列表界面
	 */
	public final static String PASS_LIST="pass/pass"; 
	/*---------问答界面--------------*/
	public final static String ANSWER_LIST="front/listanswer";
	
	public final static String SUCCESS="front/success";
	
	public final static String UPDATE_MESSAGE="front/updateMessage";
	
	
	
	/**
	 * 显示json的界面
	 */
	public final static String JSON="share/json"; 
	
	
	/**
	 * 生成重定向链接
	 * @param method 要重定向的方法
	 * @return
	 */
	public static String generatorRedirect(String method){
		return PageViewConstant.REDIRECT+method+PageViewConstant.FILTER_SUFFIX;
	}
	/**
	 * 为消息界面下一次跳转生成链接
	 * @param method 要跳转的方法
	 * @return
	 */
	public static String generatorMessageLink(String method){
		return method+PageViewConstant.FILTER_SUFFIX;
	}
	
}
