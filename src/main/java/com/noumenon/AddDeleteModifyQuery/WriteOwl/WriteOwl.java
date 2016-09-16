package com.noumenon.AddDeleteModifyQuery.WriteOwl;

import java.io.IOException;

public interface WriteOwl {


	/**把Fuseki数据库中的数据写回.owl文件中，且单词和句子的.owl文件是分开的：无返回值
	 * 
	 * @throws IOException
	 */
	public void writeBackToOwl() throws IOException;
	
	
	/**把Fuseki数据库中的数据写回.owl文件中，且单词和句子的.owl文件是分开的：无返回值
	 * 
	 * @throws IOException
	 */
	public void writeBackToRespectiveOwl() throws IOException;
}
