package com.cnu.iqas.bean.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * JSon输出的日期格式
 * @author Administrator
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";   
    private DateFormat dateFormat;   

    
    
    /**  
     * 构造方法.  
     *  
     * @param datePattern 日期格式  为null则默认为yyyy-MM-dd
     */  
    public DateJsonValueProcessor(String datePattern) {   
          
        if( null == datePattern )
            dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);  
        else
            dateFormat = new SimpleDateFormat(datePattern); 
        
    }   

    
    
    /* （非 Javadoc）
     * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    public Object processArrayValue(Object arg0, JsonConfig arg1) {
        // TODO 自动生成方法存根
        return process(arg0);   
    }

    /* （非 Javadoc）
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
     */
    public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
        // TODO 自动生成方法存根
        return process(arg1);   
    }
    
    private Object process(Object value) {   
    	try {
			if( value instanceof Date )
			 return dateFormat.format((Date) value);
			else
				return value == null ? null : value.toString();
		} catch (Exception e) {
			return "";
		}
    	
    }   

}