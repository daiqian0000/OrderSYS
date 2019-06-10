package com.asion.business.module.log.service;

import com.asion.business.module.log.model.Log;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface LogService extends BaseService<Log> {

	/**
	 * 异常日志类型
	 */
	String LOG_TYPE_EXCEPTION = "exception";

	/**
	 * 操作日志类型
	 */
	String LOG_TYPE_OPREATION = "opreation";

	/**
	 * 系统业务操作出现异常保存日志执行方法
	 * 
	 * @param operateInfo
	 *            操作信息
	 * @param throwable
	 *            异常
	 */
	void saveLog(String operateInfo, Throwable throwable);

	/**
	 * 业务操作保存日志执行方法
	 * 
	 * @param operateInfo
	 *            操作信息
	 */
	void saveLog(String operateInfo);

	/**
	 * 保存日志
	 * 
	 * @param log
	 *            日志信息
	 */
	void saveLog(Log log);

}
