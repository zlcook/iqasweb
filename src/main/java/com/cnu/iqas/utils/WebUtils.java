package com.cnu.iqas.utils;

import java.security.MessageDigest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cnu.iqas.bean.base.MyStatus;

import net.sf.json.JSONObject;

public class WebUtils {
	//16进制字符
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	/**
	 * 将表单类中的信息拷贝到对应的类中
	 * @param dest  类
	 * @param src  表单信息
	 */
	public static void copyBean(Object dest,Object src){
		try {
			// 通过BeanUtils类调用方法将表单类中的信息拷贝到对应的类中 ，拷贝过程中只支持8中基本数据类型
			BeanUtils.copyProperties(dest, src);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据信息返回json数据
	 * @param scode  状态码
	 * @param message 状态码说明
	 * @param jsonObejct 返回总的json类
	 * @param resultObject jsonObejct中的result数据
	 * @param mv  视图
	 * @return
	 */
	public static ModelAndView beforeReturn(int scode,String message,JSONObject jsonObejct,JSONObject resultObject,ModelAndView mv){
		//状态对象
		MyStatus status =new MyStatus(scode,message);
		JsonTool.putStatusJson(status, jsonObejct);
		jsonObejct.put("result", resultObject);
		mv.addObject("json", jsonObejct.toString());
		return mv;
	}
	
	/**
	 * url路径中的MD5编码，以防出现“=”号
	 * @param origin 源数据
	 * @return MD5数据，不含有"="可以放在连接路径中以防产生错误参数
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}
	/**
	 * 转换字节数组为16进制字串
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	 /** 
     *  
     * @描述：是否是2003的excel，返回true是2003 
     * @时间：2012-08-29 下午16:29:11 
     *  
     * @参数：@param filePath　文件完整路径 
     *  
     * @参数：@return 
     *  
     * @返回值：boolean 
     */  
    public static boolean isExcel2003(String filePath)  
    {  
        return filePath.matches("^.+\\.(?i)(xls)$");  
    }  
    /** 
     *  
     * @描述：是否是2007的excel，返回true是2007 
     *  
     * @时间：2012-08-29 下午16:28:20 
     *  
     * @参数：@param filePath　文件完整路径 
     *  
     * @参数：@return 
     *  
     * @返回值：boolean 
     */  
    public static boolean isExcel2007(String filePath)  
    {  
        return filePath.matches("^.+\\.(?i)(xlsx)$");  
    }  
    // 根据字节大小转换成 G M K B
    public static String convertStorage(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
    
    /**
	 * 校验字符串是否为空或者空字符串
	 * @param str
	 * @return 空或者空字符串返回true
	 */
	public static boolean isNull(String str){
		if( str!=null && !str.trim().equals(""))
			return false;
		else
			return true;
	}
	public static String token(String userName) {
		// TODO Auto-generated method stub
		if( userName==null)
			throw new RuntimeException("用户名为空");
		return System.currentTimeMillis()+"@"+userName;
	}
	public static String getUserNameFromToken(String token) {
		// TODO Auto-generated method stub
		if( token==null)
			return null;
		if(token.split("@").length!=2)
			return null;
		return token.split("@")[1];
	}
}
