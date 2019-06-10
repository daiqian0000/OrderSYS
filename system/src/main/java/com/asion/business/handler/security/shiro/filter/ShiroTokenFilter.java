package com.asion.business.handler.security.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

/**
 * Shiro登录指令验证过滤器
 * 
 * @author chenboyang
 *
 */
public class ShiroTokenFilter extends PathMatchingFilter {

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
        return SecurityUtils.getSubject().isAuthenticated();
	}
	
}
