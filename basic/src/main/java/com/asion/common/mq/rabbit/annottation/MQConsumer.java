package com.asion.common.mq.rabbit.annottation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.asion.common.spring.SpringBean;

/**
 * MQ信息消费注解
 * 
 * @author chenboyang
 *
 */
@Inherited
@SpringBean
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MQConsumer {

	/**
	 * SpringBean名称
	 * 
	 * @return SpringBean名称
	 */
	String value() default "";

}
