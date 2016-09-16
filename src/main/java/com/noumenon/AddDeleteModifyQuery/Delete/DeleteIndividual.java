package com.noumenon.AddDeleteModifyQuery.Delete;

public interface DeleteIndividual {

	/**删除单词的属性：无返回值-------------------------------------------------------------
	 * 
	 * @param yourInstanceLabel：你要删除的单词
	 * @param property：你要删除的单词属性
	 * @param propertyValue：你要删除的单词属性值
	 */
	void deleteInstanceProperty(String yourInstanceLabel, String property,
			String propertyValue);

	/**删除单词的类：无返回值--------------------------------------------------------------
	 * 
	 * @param yourInstanceLabel：你要删除的单词
	 * @param yourInstanceClass：你要删除的单词的类
	 */
	void deleteClass(String yourInstanceLabel,String yourInstanceClass);
	
	/**删除单词的标签Label：无返回值--------------------------------------------------------
	 * 
	 * @param yourInstanceLabel：你要删除的单词
	 */
	void deleteLabel(String yourInstanceLabel);

	
	/**删除句子的属性：无返回值-------------------------------------------------------------
	 * 
	 * @param yourInstanceLabel：你要删除的句子
	 * @param property：你要删除的句子的属性
	 * @param propertyValue：你要删除的句子的属性值
	 */
	void deleteSentenceInstanceProperty(String yourInstanceLabel, String property,
			String propertyValue);

	/**删除句子的类：无返回值--------------------------------------------------------------
	 * 
	 * @param yourInstanceLabel：你要删除的句子
	 * @param yourInstanceClass：你要删除的句子的类
	 */
	void deleteSentenceClass(String yourInstanceLabel, String yourInstanceClass);
	
	/**删除句子的实例Label：无返回值--------------------------------------------------------
	 * 
	 * @param yourInstanceLabel：你要删除的句子
	 */
	void deleteSentenceLabel(String yourInstanceLabel);

}
