package com.asion.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asion.business.web.constants.StatusCode;
import com.asion.business.web.model.Result;

/**
 * 控制器返回统一处理切面
 * 
 * @author chenboyang
 *
 */
@Aspect
public class ControllerReturnAspect {

	/**
	 * 方法执行时拦截控制
	 * 
	 * @param proceedingJoinPoint
	 *            执行进程点
	 * @return 返回执行结果
	 * @throws Throwable
	 *             抛出异常
	 */
	@Around("@annotation(requestMapping)")
	public Object around(ProceedingJoinPoint proceedingJoinPoint, RequestMapping requestMapping) throws Throwable {
		Object result = proceedingJoinPoint.proceed();
		if (result != null && result instanceof Boolean) {
			if ((boolean) result) {
				return Result.result(StatusCode.SUCCESS, "操作成功！", result);
			}
			return Result.result(StatusCode.FAIL, "操作失败！", result);
		}
		return Result.result(result);
	}

}
