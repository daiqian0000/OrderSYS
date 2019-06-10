package com.asion.business.handler.security.dataPermission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPermission {

	/**
	 * 数据权限验证
	 * 
	 * @return 验证内容
	 */
	DataPermissionVerify[] value() default { DataPermissionVerify.ORG };

}
