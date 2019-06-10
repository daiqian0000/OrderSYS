package com.asion.business.web.advice;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.asion.business.web.constants.StatusCode;
import com.asion.business.web.model.Result;
import com.asion.common.exception.BusinessException;

/**
 * MVC接口统一异常处理
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@RestControllerAdvice
public class ExceptionAdvice {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 普通异常处理
	 * 
	 * @param e
	 *            异常信息
	 * @return 结果数据
	 */
	@ExceptionHandler(Exception.class)
    public Result<String> handle(Exception e) {
		logger.error(e.getMessage(), e);
        return Result.result(StatusCode.SERVER_ERROR, "服务器错误!", e.getMessage());
    }
	
	/**
	 * 业务异常处理
	 * 
	 * @param e
	 *            异常信息
	 * @return 结果数据
	 */
	@ExceptionHandler(BusinessException.class)
    public Result<String> handle(BusinessException e) {
		logger.error(e.getMessage(), e);
        return Result.result(StatusCode.BUSINESS_ERROR, "业务错误!", e.getMessage());
    } 
	
}
