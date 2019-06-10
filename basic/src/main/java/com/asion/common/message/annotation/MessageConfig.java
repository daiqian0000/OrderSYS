package com.asion.common.message.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.asion.business.sys.storage.StorageType;

/**
 * 消息发送配置注解类
 * 
 * @author chenboyang
 *
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageConfig {

	/**
	 * 验证存储类型
	 * 
	 * @return 验证存储类型
	 */
	StorageType verifyStorageType() default StorageType.SYSTEM;

}
