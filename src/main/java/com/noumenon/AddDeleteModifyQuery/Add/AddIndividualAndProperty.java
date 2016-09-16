package com.noumenon.AddDeleteModifyQuery.Add;

public interface AddIndividualAndProperty {
	/** 添加单词的类：无返回值-------------------------------------------------------
	 * 
	 * @param yourClass：你要添加的单词的类
	 * @param yourInstance：你要添加的单词
	 */
	@Deprecated
	void addClass(String yourClass, String yourInstance);
	
	/** 添加单词的类：无返回值-------------------------------------------------------
	 * 
	 * @param yourClass：你要添加的单词的类
	 * @param yourInstance：你要添加的单词
	 */
	void addWordClass(String yourClass, String yourInstance);
	
	/**添加单词的标签Label：无返回值
	 * 
	 * @param yourInstance：你要添加的单词
	 */
	@Deprecated
	void addLabel(String yourInstance);
	
	/**添加单词的标签Label：无返回值
	 * 
	 * @param yourInstance：你要添加的单词
	 */
	void addWordLabel(String yourInstance);

	/**添加单词的属性（单词属性）：无返回值-----------------------------------------------
	 * 
	 * @param propertyLabel：你要添加的单词属性（如“单词ID”）
	 * @param yourInstance：你要添加的单词
	 * @param yourProperty：你要添加的单词属性值（如“单词ID”对应的属性值为：1/1/1/1）
	 */
	@Deprecated
	void addProperty(String propertyLabel, String yourInstance,
			String yourProperty);
	
	/**添加单词的属性（单词属性）：无返回值-----------------------------------------------
	 * 
	 * @param propertyLabel：你要添加的单词属性（如“单词ID”）
	 * @param yourInstance：你要添加的单词
	 * @param yourProperty：你要添加的单词属性值（如“单词ID”对应的属性值为：1/1/1/1）
	 */
	void addWordProperty(String propertyLabel, String yourInstance,
			String yourProperty);


	/**添加句子的类：无返回值--------------------------------------------------------
	 * 
	 * @param yourClass：你要添加的句子的类
	 * @param yourInstance：你要添加的句子
	 */
	void addSentenceClass(String yourClass, String yourInstance);
	
	/**添加句子的标签Label：无返回值--------------------------------------------------
	 * 
	 * @param yourInstance：你要添加的句子
	 */
	void addSentenceLabel(String yourInstance);

	/**添加句子的属性：无返回值-------------------------------------------------------
	 * 
	 * @param propertyLabel：你要添加的句子属性（如“句子ID”）
	 * @param yourInstance：你要添加的句子
	 * @param yourProperty：你要添加的句子属性值（如“句子ID”对应的属性值为：2/2/2/2）
	 */
	void addSentenceProperty(String propertyLabel, String yourInstance,
			String yourProperty);
	
	/**为修改单词设计的添加方法：无返回值------------------------------------------------
	 * 
	 * @param propertyLabel：你要添加的单词数据属性(DataProperty)
	 * @param yourInstance：你要添加的单词实例
	 * @param yourProperty：你要添加的单词属性值
	 * @param yourID：改实例对应的单词ID（因为有重名实例，所以需要以单词ID值区别）
	 */
	void addPropertyForModify(String propertyLabel, String yourInstance,
			String yourProperty, String yourID);

	/**为修改句子设计的添加方法：无返回值------------------------------------------------
	 * 
	 * @param propertyLabel：你要添加的句子数据属性(DataProperty)
	 * @param yourInstance：你要添加的句子实例
	 * @param yourProperty：你要添加的句子属性值
	 * @param yourID：改实例对应的单词ID（因为有重名实例，所以需要以句子ID值区别）
	 */
	void addSentencePropertyForModify(String propertyLabel, String yourInstance,
			String yourProperty, String yourID);
	
	/**添加等价关系sameAs
	 * 
	 * @param question1
	 * @param question2
	 */
	void addRelationSameAs(String question1, String question2);
}
