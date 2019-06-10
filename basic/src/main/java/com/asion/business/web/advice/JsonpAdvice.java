package com.asion.business.web.advice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * 增加JSONP跨域请求支持
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@ControllerAdvice(annotations = { Controller.class, RestController.class })
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

	/**
	 * JSONP处理器构造函数
	 */
	public JsonpAdvice() {
		super("callback", "jsonp");
	}
	
}
