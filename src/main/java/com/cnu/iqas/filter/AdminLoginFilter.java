package com.cnu.iqas.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnu.iqas.bean.admin.Admin;

/**
* @author 周亮 
* @version 创建时间：2015年11月16日 下午6:49:51
* 类说明 管理员登录过滤器，如果管理员未登录则不可以访问后台界面
*/
public class AdminLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*
		 * 1. 获取session中的admin
		 * 2. 判断是否为null
		 *   > 如果不为null：放行
		 */
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Admin admin = (Admin) req.getSession().getAttribute("admin");
		
		if(admin == null) {
			String path = req.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			resp.sendRedirect(basePath+"admin/loginUI.html");
			//req.getRequestDispatcher("/index.jsp").forward(req, response);
		} else {
			chain.doFilter(request, response);//放行
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
