package com.asion.business.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class WebExceptionUtil {

	/**
	 * 在请求信息中获取异常
	 * 
	 * @param request
	 *            请求信息
	 * @return 异常
	 */
	public static Throwable getThrowable(HttpServletRequest request) {
		Throwable exception = null;
		if (request.getAttribute("exception") != null) {
			exception = (Throwable) request.getAttribute("exception");
		} else if (request.getAttribute("javax.servlet.error.exception") != null) {
			exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}
		return exception;
	}
	
}
