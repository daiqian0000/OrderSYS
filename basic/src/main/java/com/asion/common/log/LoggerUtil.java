package com.asion.common.log;

/**
 * 日志记录器帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class LoggerUtil {

	/**
	 * 错误日志记录
	 * 
	 * @param logger
	 *            日志记录器
	 * @param throwable
	 *            异常信息
	 */
	public static void errorLogging(org.apache.log4j.Logger logger, Throwable throwable) {
		logger.error(throwable.getMessage(), throwable);
	}

	/**
	 * 错误日志记录
	 * 
	 * @param logger
	 *            日志记录器
	 * @param throwable
	 *            异常信息
	 */
	public static void errorLogging(org.slf4j.Logger logger, Throwable throwable) {
		logger.error(throwable.getMessage(), throwable);
	}

}
