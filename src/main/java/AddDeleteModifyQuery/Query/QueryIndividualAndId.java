package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QueryIndividualAndId {
	//查找一个类下的所有实例名称：返回值ResultSet；参数：类名
		public ResultSet checkInstanceAndId(String yourClass);
}
