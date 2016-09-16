package com.cnu.iqas.bean.iword;
/**
* @author 周亮 
* @version 创建时间：2015年12月7日 上午9:04:45
* 类说明 :单词主题类型枚举类
*/
public enum WordThemeTypeEnum {
	SPACE{
		@Override
		public String getName() {
			return "太空主题";
		}
	},PRAIRIE{
		@Override
		public String getName() {
			return "草原主题";
		}
	};
	
	/**
	 * 为枚举类定义一个抽象方法
	 * 这个抽象方法由不同的枚举值提供不同的实现
	 * @return
	 */
	public abstract String getName();
}
