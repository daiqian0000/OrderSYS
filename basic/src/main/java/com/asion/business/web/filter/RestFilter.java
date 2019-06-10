package com.asion.business.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前后端分离RESTful接口过滤器
 * 
 * @author chenboyang
 *
 */
public class RestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = null;
		if (request instanceof HttpServletRequest) {
			req = (HttpServletRequest) request;
		}
		HttpServletResponse res = null;
		if (response instanceof HttpServletResponse) {
			res = (HttpServletResponse) response;
		}
		if (req != null && res != null) {
			res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
			res.setHeader("Access-Control-Allow-Credentials", "true");
			String origin = req.getHeader("Origin");
			if (origin == null) {
				origin = req.getHeader("Referer");
			}
			res.setHeader("Access-Control-Allow-Origin", origin);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
