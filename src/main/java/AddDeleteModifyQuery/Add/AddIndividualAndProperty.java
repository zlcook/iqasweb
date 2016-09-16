package AddDeleteModifyQuery.Add;

public interface AddIndividualAndProperty {

	// 添加单词实例名称：无返回值；参数：要添加到的类，实例名（单词名）
	void addInstance(String yourClass, String yourInstance);
	//void addInstance(String yourInstance);

	// 添加单词实例属性（单词属性）：无返回值；参数：属性名，实例名称，属性值
	void addProperty(String propertyLabel, String yourInstance,
			String yourProperty);

	// 添加句子实例名称：无返回值；参数：要添加到的类，实例名（单词名）
	void addSentenceInstance(String yourClass, String yourInstance);

	// 添加句子实例属性（单词属性）：无返回值；参数：属性名，实例名称，属性值
	void addSentenceProperty(String propertyLabel, String yourInstance,
			String yourProperty);
	
}
