package com.cnu.iqas.bean.ontology;
import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Lin;


public class Word_simalgorithm{
	private String str1;
	  private String str2;
	  private String dir = "C:/Program Files(x86)/WordNet";
	  private JWS    ws ;
	  private Lin lin;
	  public Word_simalgorithm(String str1,String str2, JWS ws){
	        this.str1=str1;
	        this.str2=str2;
	        this.ws=ws;
	        lin = ws.getLin();
	    }
	  /**
	   * 获取两个单词的相似度
	   * @return 两个单词的最高相似度
	   */
	    public double getSimilarity(){
	       String[] strs1 = splitString(str1);
	       String[] strs2 = splitString(str2);
	         double sum = 0.0,sc=0.0; 
	      for(String s1 :strs1){
	           for(String s2: strs2){
	               sc= maxScoreOfLin(str1,str2);
	                sum+= sc;
	               System.out.println("当前计算: "+str1+" VS "+str2+" 的相似度为:"+sc);
	            }
	      }
	      return sc;           
	     //double Similarity = sum /(strs1.length * strs2.length);
	       //  return Similarity;
	     }  
	    /**
	     * 方法：去除空格
	     * @param str 字符串
	     * @return 去除空格后的字符串
	     */
	   private String[] splitString(String str){
	         String[] ret = str.split(" ");
	        return ret;
	   }
	   /**
	    * 通过区分名词、动词区分两单词的相似度。
	    * @param str1 字符串
	    * @param str2 字符串
	    * @return 计算出的两个单词的相似度
	    */
	   private double maxScoreOfLin(String str1,String str2){
	         
	        double sc = lin.max(str1, str2, "n");
	        if(sc==0){
	             sc = lin.max(str1, str2, "v");
	        }
	       return sc;    
	    }

 }