package com.asion.common.mq.rabbit.annottation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.asion.common.spring.SpringBean;

/**
 * MQ消息生产通道注解
 * 
 * @author chenboyang
 *
 */
@Inherited
@SpringBean
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MQProducer {

	/**
	 * SpringBean名称
	 * 
	 * @return SpringBean名称
	 */
	String value() default "";

	/**
	 * 交换机名称
	 * 
	 * @return 交换机名称
	 */
	String exchange();

	/**
	 * 路由key
	 * 
	 * @return 路由key
	 */
	String routingKey() default "";

	/**
	 * MQ操作模板
	 * 
	 * @return 操作模板
	 */
	String rabbitTemplate() default "";

}
