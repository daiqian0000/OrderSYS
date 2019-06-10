package com.asion.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.asion.common.log.LogInter;

/**
 * AOP方法拦截范例
 * 
 * @author chenboyang
 *
 */
@Aspect
public class MethodMonitorAspect {

	/**
	 * 切点
	 */
	@Pointcut("@annotation(logInter)")
	private void pointcut() {
	}

	/**
	 * 调用前执行
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
	 * 调用后执行
	 * 
	 * @param point
	 *            连接点
	 * @param logInter
	 *            日志信息
	 */
	@After("@annotation(logInter)")
	public void after(JoinPoint point, LogInter logInter) {

	}

	/**
	 * 返回结果后执行
	 * 
	 * @param returnValue
	 *            返回结果
	 */
	@AfterReturning(value = "@annotation(logInter)", returning = "returnValue")
	public void afterReturin(Object returnValue) {

	}

	/**
	 * 抛出异常后执行
	 * 
	 * @param throwable
	 *            抛出异常
	 * @param logInter
	 *            日志信息
	 */
	@AfterThrowing(value = "@annotation(logInter)", throwing = "throwable")
	public void afterThrowing(Throwable throwable, LogInter logInter) {

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
