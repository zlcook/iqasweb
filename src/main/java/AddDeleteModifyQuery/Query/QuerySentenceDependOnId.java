package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QuerySentenceDependOnId {
	//根据Id查找实例及其属性：返回值ResultSet；参数：Id
		public ResultSet checkSentencePropertyDependOnId(String yourId);
}
