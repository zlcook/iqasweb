package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QueryIndividualAndProperty {
	
	//查找类下的所有实例及其属性：返回值ResultSet；参数：类名
	public ResultSet checkProperty(String yourWord);
	
}
