package com.asion.business.module.log.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asion.business.module.log.service.WebLogService;

/**
 * 系统请求统一日志记录拦截器
 * 
 * @author chenboyang
 *
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

	/**
	 * WEB日志服务接口
	 */
	private WebLogService webLogService;

	/**
	 * 构建日志拦截器
	 * 
	 * @param webLogService
	 *            WEB日志服务接口
	 */
	public LogInterceptor(WebLogService webLogService) {
		this.webLogService = webLogService;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		webLogService.saveWebLog(request, handler, ex);
	}

}
