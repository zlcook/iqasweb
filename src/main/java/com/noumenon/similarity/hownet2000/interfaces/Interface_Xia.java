/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noumenon.similarity.hownet2000.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Vector;

import com.noumenon.similarity.hownet2000.concept.MyConceptParser;

/**
 * 
 * @author Administrator
 */
public class Interface_Xia {

	public String Keyword1;
	public ArrayList<String> Keyword1_AllMeans = new ArrayList<String>();
	public String Keyword2;
	public ArrayList<String> Keyword2_AllMeans = new ArrayList<String>();
	public double a_Value;
	public double b1_Value;
	public double b2_Value;
	public double b3_Value;
	public double b4_Value;
	public int Sense_flag1;// 用于标识Keyword1是否为未登录词，flag1=0：Keyword1为未登录词，flag1=1：Keyword1为已登录词
	public int Sense_flag2;// 用于标识Keyword2是否为未登录词，flag2=0：Keyword2为未登录词，flag2=1：Keyword2为已登录词
	public int Sememe_flag1;
	public int Sememe_flag2;
	public String Keyword1_SelectMean;
	public String Keyword2_SelectMean;
	public int type;

	public void findFather() {// 查看义项1
		boolean isNewWord = true;

		// 创建输入对象
		Scanner sc = new Scanner(System.in);

		// 获取用户输入的字符串
		System.out.print("请输入任意字符:");
		Keyword1 = sc.nextLine();
		System.out.println("你输入的字符为:" + Keyword1);

		// for (int i = 0; i < Keyword1_ComboBox.getItemCount(); i++) {
		// if (Keyword1_ComboBox.getItemAt(i).equals(Keyword1)) {
		// isNewWord = false;
		// break;
		// }
		// }
		// if (isNewWord) {
		// Keyword1_ComboBox.addItem(Keyword1);//将输入的内容加入组合框的下拉菜单中
		// }
		// HowNet myHowNet = new HowNet();
		Vector<String> v = new Vector<String>();
		if (MyConceptParser.getInstance().isConcept(Keyword1)) {
			Sense_flag1 = 1;
			Collection<String> result1 = MyConceptParser.getInstance()
					.getDefinitions(Keyword1);
			for (String temp : result1) {
				System.out.println("-------" + temp);
				v.add(temp);
			}

		} else {
			Sense_flag1 = 0;
			v.add("未登录词，需要计算组合概念!");
			// System.out.println("Not Found!");
		}
	}

	public static void main(String[] args) {
		Interface_Xia myInterface = new Interface_Xia();
		myInterface.findFather();
	}
}
