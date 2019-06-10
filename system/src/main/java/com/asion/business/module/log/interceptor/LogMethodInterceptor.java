package com.asion.business.module.log.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.asion.business.module.log.service.LogService;
import com.asion.common.log.LogInter;

/**
 * 日志方法拦截器(未完成版本)
 * 
 * @author chenboyang
 *
 */
public class LogMethodInterceptor implements MethodInterceptor {

	/**
	 * 日志服务接口
	 */
	@Autowired
	@Qualifier("logServiceImpl")
	private LogService logService;
	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();
		if (method.isAnnotationPresent(LogInter.class)) {
			LogInter logInter = method.getAnnotation(LogInter.class);
			if (logInter != null) {
				Object result = null;
				Exception exception = null;
				try {
					result = methodInvocation.proceed();
				} catch (Exception e) {
					exception = e;
					throw e;
				} finally {
					if (exception != null) {
						logService.saveLog(logInter.info(), exception);
					} else {
						logService.saveLog(logInter.info());
					}
				}
				return result;
			}
		}
		return methodInvocation.proceed();
	}

}
