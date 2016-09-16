package AddDeleteModifyQuery.Delete;

public interface DeleteIndividual {
	
	//删除实例：无返回值；参数：类名，实例名称
	void deleteInstance(String yourClass, String yourInstance);
	
	//删除实例某个属性值：无返回值；参数：实例名称，属性名
	void deleteProperty(String yourInstance, String yourProperty);
}
