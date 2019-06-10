package com.asion.business.sys.storage;

/**
 * Session存储类型枚举
 * 
 * @author chenboyang
 *
 */
public enum StorageType {

	/**
	 * 系统存储
	 */
	SYSTEM,
	
	/**
	 * Http请求WEB容器存储
	 */
	HTTP,
	
	/**
	 * Redis存储
	 */
	REDIS
	
}
