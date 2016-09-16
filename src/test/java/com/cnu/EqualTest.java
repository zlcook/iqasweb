package com.cnu;

import java.util.HashSet;
import java.util.Hashtable;

/**
* @author 周亮 
* @version 创建时间：2016年9月7日 上午9:57:43
* 类说明
*/
public class EqualTest {

	private String name;
	private String nicke;
	public static void main(String[] args){
		
		int x[] = new int[15];
		final int y[] = x;
		System.out.println(y[5]);
		
		int it = 65;
		float f1 =65.0f;
		char ch = 'A';
		//都将输出true
		System.out.println("65和65.0f是否相等？"+(it==f1)+" 65.0f和 字母\'A\'是否相等?"+(f1==ch) );
		
		String str1=new String("hello");
		String str2 = new String("hello");
		//将输出false
		System.out.println("str和str2是否相等?"+(str1==str2));
		
		
		 	Integer i = 127;
		    Integer j = 127;
		    System.out.println(i == j);
		    //提示：对象存在常量池,常量池中的数值类型大小只有-127到127

		    Integer m = 128;
		    Integer n = 128;
		    System.out.println(m == n);
		    //提示：对象存在堆内存,
		    
		    //s1，s2,s3直接饮用常量池中的"hello"、"he"、"llo"
		    String s1="hello";
		    
		    String s2="he";
		    String s3="llo";
		    //s4后面的字符串值可以在编译时就确定下来，所以s4引用常量池中的"hello"
		    String s4="he"+"llo";
		    
		    //s5、s6后面的字符串值不能在编译时就确定下来，不能引用常量池中的"hello".
		    String s5=s2+s3;
		    String s6="he"+new String("llo");
		    //s7引用堆内存中新创建的String对象
		    String s7= new String("hello");
		    
		    System.out.println(s1==s4);//输出true
		    System.out.println(s1==s5);//输出false
		    System.out.println(s1==s5);//输出false
		    System.out.println(s1==s6);//输出false
		    System.out.println(s1==s7);//输出false
		    
		    
		    Hashtable<String, EqualTest> maps = new Hashtable<>();
		    EqualTest t1 = new EqualTest("oo", "t1");
		    EqualTest t2 = new EqualTest("oo", "t2");
		    maps.put(t1.getName(),t1);
		    maps.put(t2.getName(), t2);
		    
		    System.out.println(maps.toString());
		    
		    HashSet<EqualTest> set = new HashSet<>();
		    set.add(t1);
		    set.add(t2);
		    System.out.println(set.toString());
	}  
		    
	
	
	
	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getNicke() {
		
		return nicke;
	}




	public void setNicke(String nicke) {
		this.nicke = nicke;
	}




	@Override
	public String toString() {
		return "EqualTest [name=" + name + ", nicke=" + nicke + "]";
	}




	public EqualTest(String name, String nicke) {
		super();
		this.name = name;
		this.nicke = nicke;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EqualTest other = (EqualTest) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}