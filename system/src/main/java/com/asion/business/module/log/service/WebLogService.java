package com.asion.business.module.log.service;

import javax.servlet.http.HttpServletRequest;

/**
 * WEB日志服务接口
 * 
 * @author chenboayng
 *
 */
public interface WebLogService extends LogService {

	/**
	 * 请求拦截器保存日志执行方法
	 * 
	 * @param request
	 *            请求信息
	 * @param handler
	 *            业务处理器
	 * @param ex
	 *            异常
	 */
	void saveWebLog(HttpServletRequest request, Object handler, Exception ex);

	/**
	 * 业务请求保存日志执行方法
	 * 
	 * @param operateInfo
	 *            操作信息
	 * @param request
	 *            请求信息
	 */
	void saveWebLog(String operateInfo, HttpServletRequest request);
	
	/**
	 * 业务操作保存日志执行方法
	 * 
	 * @param operateInfo
	 *            操作信息
	 */
	void saveWebLog(String operateInfo);

}
