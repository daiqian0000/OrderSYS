package com.asion.common.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WEB程序日志信息注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfo {

	/**
	 * 操作信息
	 * 
	 * @return 操作信息
	 */
	String info();
	
}
