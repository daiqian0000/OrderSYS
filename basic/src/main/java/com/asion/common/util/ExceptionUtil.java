package com.asion.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理帮助类
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("unchecked")
public abstract class ExceptionUtil {

	/**
	 * 将异常转为运行时异常
	 * 
	 * @param exception
	 *            异常
	 * @return 运行时异常
	 */
	public static RuntimeException revRunExc(Exception exception) {
		if (exception instanceof RuntimeException) {
			return (RuntimeException) exception;
		} else {
			return new RuntimeException(exception);
		}
	}

	/**
	 * 获取异常堆栈信息字符串
	 * 
	 * @param throwable
	 *            异常
	 * @return 堆栈信息
	 */
	public static String getStackTraceAsString(Throwable throwable) {
		if (throwable != null) {
			StringWriter stringWriter = new StringWriter();
			throwable.printStackTrace(new PrintWriter(stringWriter));
			return stringWriter.toString();
		}
		return null;
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 * 
	 * @param exception
	 *            异常
	 * @param causeExceptionClasses
	 *            异常类型集
	 * @return 判断结果
	 */
	public static boolean isCausedBy(Exception exception, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = exception.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}

}
