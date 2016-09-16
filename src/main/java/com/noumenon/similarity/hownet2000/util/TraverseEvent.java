package com.noumenon.similarity.hownet2000.util;

/**
 * 遍历接口, 对于需要遍历的东西，通过传入该接口，可以实现实际的访问处理
 * 
 * @author 于欢
 * 
 * @param <T>
 */
public interface TraverseEvent<T> {
	
	/** 
	 * 遍历时访问其中的一个条目
	 * @param item
	 * @return
	 */
	public boolean visit(T item);
}
