package com.asion.common.mongo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.asion.common.spring.SpringBean;

/**
 * MongoDB存储集合注解
 * 
 * @author chenboyang
 *
 */
@Inherited
@Documented
@SpringBean
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoStorage {

	/**
	 * SpringBean名称
	 * 
	 * @return SpringBean名称
	 */
	String value() default "";
	
	/**
	 * 存储集合名称
	 * @return 名称
	 */
	String name();
	
	/**
	 * 存储对象类型
	 * 
	 * @return 类型
	 */
	Class<?> type();
	
}
