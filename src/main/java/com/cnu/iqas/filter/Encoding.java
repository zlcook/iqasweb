package com.cnu.iqas.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
* @author 周亮 
* @version 创建时间：2015年12月16日 下午1:47:30
* 类说明；编码过滤器
*/
public class Encoding implements Filter {
	private String charset = "UTF-8";
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	/*	request.setCharacterEncoding(charset);//只处理了post请求
		response.setContentType("text/html;charset="+charset);//处理响应编码
		//放行时把request调包
		chain.doFilter(request, response);//在目标中使用的request是调包后的request
*/	
	HttpServletRequest req = (HttpServletRequest) request;
		if(req.getMethod().equalsIgnoreCase("GET")) {
			if(!(req instanceof CharSetRequest)) {
				req = new CharSetRequest(req, charset);//处理get请求编码
			}
		} else {
			req.setCharacterEncoding(charset);//处理post请求编码
		}
		chain.doFilter(req, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		String charset = fConfig.getInitParameter("charset");
		if(charset != null && !charset.isEmpty()) {
			this.charset = charset;
		}
	}
}
