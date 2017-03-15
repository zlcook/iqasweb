package com.cnu.offline.template.method;
/**
* @author 周亮 
* @version 创建时间：2016年11月21日 上午10:11:55
* 类说明
*/
public abstract class AbstractBuild {
	/**
	 * 项目构建
	 */
	public void build(){
		initialize();
		compile();
		test();
		packagee();
		integrationTest();
		deploy();
	}

	protected abstract void initialize();
	protected abstract void compile();
	protected abstract void test() ;
	protected abstract void packagee();
	protected abstract void integrationTest();
	protected abstract void deploy();
}
