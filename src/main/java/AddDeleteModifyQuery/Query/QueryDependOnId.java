package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QueryDependOnId {

	//根据Id查找实例及其属性：返回值ResultSet；参数：Id
	public ResultSet checkPropertyDependOnId(String yourId);
}
