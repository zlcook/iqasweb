package com.cnu.iqas.bean.iword;
/**
* @author 周亮 
* @version 创建时间：2015年11月22日 下午10:08:13
* 类说明,该表记录不同版本教材，和教材对应的单词数
*/
public class VersionWordCount {
	
	/**
	 * 版本序号
	 */
	private int version;
	/**
	 * 该版本下的单词数
	 */
	private long sum;
	public VersionWordCount(int version, long sum) {
		super();
		this.version = version;
		this.sum = sum;
	}
	
	public VersionWordCount(int version) {
		super();
		this.version = version;
	}

	public VersionWordCount() {
		super();
	}

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public long getSum() {
		return sum;
	}
	public void setSum(long sum) {
		this.sum = sum;
	}
	@Override
	public String toString() {
		return "VersionWordCount [version=" + version + ", sum=" + sum + "]";
	}
	
}
