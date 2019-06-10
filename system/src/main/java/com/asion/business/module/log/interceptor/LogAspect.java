package com.asion.business.module.log.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.asion.business.module.log.service.LogService;
import com.asion.common.log.LogInter;

/**
 * 日志拦截切面
 * 
 * @author chenboyang
 *
 */
@Aspect
public class LogAspect {

	/**
	 * 日志服务接口
	 */
	@Autowired
	@Qualifier("logServiceImpl")
	private LogService logService;

	/**
	 * 切入点
	 */
	@Pointcut("@annotation(logInter)")
	private void pointcut() {
	}

	/**
	 * 执行前拦截
	 * 
	 * @param point
	 *            连接点
	 * @param logInter
	 *            日志信息
	 */
	@Before("@annotation(logInter)")
	public void before(JoinPoint point, LogInter logInter) {

	}

	/**
	 * 执行后拦截
	 * 
	 * @param point
	 *            连接点
	 * @param logInter
	 *            日志信息
	 */
	@After("@annotation(logInter)")
	public void after(JoinPoint point, LogInter logInter) {
		logService.saveLog(logInter.info());
	}

	/**
	 * 返回后拦截
	 * 
	 * @param returnValue
	 *            返回值
	 */
	@AfterReturning(value = "@annotation(logInter)", returning = "returnValue")
	public void afterReturin(Object returnValue) {

	}

	/**
	 * 异常抛出时拦截
	 * 
	 * @param throwable
	 *            异常
	 * @param logInter
	 *            日志信息
	 */
	@AfterThrowing(value = "@annotation(logInter)", throwing = "throwable")
	public void afterThrowing(Throwable throwable, LogInter logInter) {
		logService.saveLog(logInter.info(), throwable);
	}

	/**
	 * 方法执行时拦截控制
	 * 
	 * @param proceedingJoinPoint
	 *            执行进程点
	 * @return 返回执行结果
	 * @throws Throwable
	 *             抛出异常
	 */
	@Around("@annotation(logInter)")
	public Object around(ProceedingJoinPoint proceedingJoinPoint, LogInter logInter) throws Throwable {
		return proceedingJoinPoint.proceed();
	}

}
