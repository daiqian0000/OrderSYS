package com.asion.common.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.asion.common.spring.SpringBean;

/**
 * Redis存储模型注解
 * 
 * @author chenboyang
 *
 */
@Inherited
@SpringBean
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisStorage {

	/**
	 * SpringBean名称
	 * 
	 * @return SpringBean名称
	 */
	String value() default "";
	
	/**
	 * Redis存储键或目录键前缀
	 * 
	 * @return 键
	 */
	String key();

}
