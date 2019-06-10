package com.asion.business.handler.security.shiro.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Shiro异常解析器(未完善)
 * 
 * @author chenboyang
 *
 */
public class ShiroExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		/*if (ex instanceof UnauthenticatedException) {
			throw new UnauthenticatedException("未通过认证！");
		} else if (ex instanceof IncorrectCredentialsException) {
			throw new IncorrectCredentialsException("登录密码错误！");
		} else if (ex instanceof ExcessiveAttemptsException) {
			throw new ExcessiveAttemptsException("登录失败次数过多！");
		} else if (ex instanceof LockedAccountException) {
			throw new LockedAccountException("帐号已被锁定！");
		} else if (ex instanceof DisabledAccountException) {
			throw new DisabledAccountException("帐号已被禁用！");
		} else if (ex instanceof ExpiredCredentialsException) {
			throw new ExpiredCredentialsException("帐号已过期！");
		} else if (ex instanceof UnknownAccountException) {
			throw new UnknownAccountException("帐号不存在！");
		} else if (ex instanceof UnauthorizedException) {
			throw new UnauthorizedException("您没有得到相应的授权！");
		} else {
			throw ex;
		}*/
		return null;
	}

}
