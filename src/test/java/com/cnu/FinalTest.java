package com.cnu;

import java.util.Scanner;

import org.junit.Test;

/**
* @author 周亮 
* @version 创建时间：2016年9月14日 下午4:02:48
* 类说明
*/
public class FinalTest {
	//类变量。宏变量
	private final static  int age = 20;
	
	//实例变量
	private String name="zhouzhou";
	//宏变量
	private final String name1 = "zhou";
	//宏变量
	private final String name2 = "zhou";
	private  String name3="zh"+"ou";
	//会执行宏替换
	private  String name4 = name1+name2;
	
	//不会宏替换，因为name3不是宏变量
	private String name5=name1+name3;
	
	@Test
	public void testObjectVar(){
		/**===实例变量======**/
		//false
		System.out.println(name==name5);
		//true
		System.out.println(name==name4);
		
	}
	
	public static void main(String[] args)
	{
		/*final修饰的变量,不管是类变量（static定义的变量)、实例变量（成员变量）、还是局部变量，满足3个条件1.final修饰;2.定义时指定初始值;3.初始值在编译时可以被确定;
		它就相当于一个”宏变量“，在编译时所有用到该变量的地方直接替换成该变量的值（宏替换）*/
		
		/*==========以下都是局部变量的演示==========*/
		
		//book1、book2都是“宏变量”
		final String book1 = "java"+99.0;
		final String book2 = "ja"+"va"+99.0;
		
		//book3不是“宏变量”，第2个条件不满足
		final String book3 ;
		book3= "java"+99.0;
		
		//book4是“宏变量"，因为book1,book2都是宏变量，在编译时直接替换成”java“，所以值会存放在常量池中（方法区中）
		final String book4 = book1+book2;
		//book5不是“宏变量",第3个条件不满足。因为book3不是宏变量，编译时不确定，所以指向的地址在堆中，
		final String book5=book1+book3;
		//book6是宏变量，并且地址都是指向常量池中的地址
		final String book6=book1+"java"+99.0;
		
		//true,因为常量池的存在，book1和book2的引用地址相同
		System.out.println(book1==book2);
		//true,因为常量池的存在，book1和book3的引用地址相同
		System.out.println(book1==book3);
		//false,因为book4指向常量池中存放”javajava“内存的地址,book5指向堆中存放"javajava"值内存的地址
		System.out.println(book4==book5);
		//true,book4和book6指向同一个地址
		System.out.println(book4==book6);
		
		//a和b都是宏变量，在编译时都可以确定值
		final int a = 5+2;
		final double b = 1.2/3;
		
		//常量池存放hello字符串，同时让t1指向存放的地址
		String t1="hello";
		//t1执行后常量池中已经存在hello了，所以t2会直接指向存放hello的地址。所以t1==t2
		String t2="he"+"llo";
		//t1和t2不是宏变量，所以他们的值在编译时不会确定，所以t3在执行时会在堆中申请内存来存放，t3则存放该内存地址
		String t3 = t1+t2;
		//true,指向相同地址（常量池中存放hello的地址）
		System.out.println(t1==t2);
		//false,
		System.out.println(t1==t3);
		
		//常量池中存放"wo"字符串，同时在堆中申请内存存放String对象，t4指向堆中的内存地址
		String t4= new String("wo");
		//t4执行完后，常量池中有了wo，所以t5直接存放常量池中存放wo内存的地址。
		String t5="wo";
		
		//任然是main函数的局部变量
		
		final FinalTest f1= new FinalTest();
		 FinalTest f2= new FinalTest();
		/**
		 * 两个变量存放的地址不一样，但是好像没有地方需要
		 * com.cnu.FinalTest@774e1f2b
		   com.cnu.FinalTest@29e07d3e
		 */

		System.out.println(f1);
		System.out.println(f2);
		
		//f3是引用类型存放的是地址，因为f1是宏变量，所以在编译时期f3的值就确定了
		FinalTest f3=f1;
		//因为f2不是宏变量，f4的值在编译时期不会确定
		FinalTest f4=f2;
	}
	
	@Test
	public void dengyao2(){
		
		//第一个*位置
		int start =0;
		//最后一个*位置
		int end =0;
		//腰长
		int n =10;
		
		/*假设n腰
		 *思路：第i(从0开始)行的start=n-i;end=n+i 
		 */
		System.out.println("输入等腰三角形的腰长:");
		Scanner sc =new Scanner(System.in);
		n =sc.nextInt();
		for(int i =0;i <n;i++ ){
			start = n-i;
			end = n+i;
			for( int j = 1;j<=n+i;j++){
				if( j>=start && j<=end){
					System.out.print("*");
					continue;
				}
				System.out.print(" ");
			}
			//换行
			System.out.println("");
		}
	}
	
	@Test
	public void dengyao(){
		
		int sum =10;
		//1.开始*号位置
		int start ;
		//结束*号位置
		int end ;
		final String flchar ="&";
		final String black =" ";
		
		System.out.println("输入等腰三角形的腰长:");
		Scanner sc =new Scanner(System.in);
		sum =sc.nextInt();
		/**
		 * sum腰，
		 * 
		 */
		
		//外循环
		for( int i =0;i< sum;i++){
			//第一行单独处理
			if( i==0 )
			{
				for( int k =0;k<sum-1;k++)
					System.out.print(black);
				System.out.print(flchar);
			}else if( i== sum-1){  //最后一行单独处理
				for(int k =0;k<sum*2-1;k++)
					System.out.print(flchar);
			}else{
				//中间行处理
				
				//1.开始*号位置
				 start = sum-i;
				//结束*号位置
				 end =sum+i;
				for( int m =1;m<=end;m++)
				{
					if( m>= start && m<=end){
						System.out.print(flchar);
						continue;
					}
					System.out.print(black);
				}
			}
			//换行
			System.out.println("");
		}
		
	}
}
