package com.asion.config.properties;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

/**
 * Redis配置属性辅助类
 * 
 * @author chenboyang
 *
 */
public class RedisSupportProperties extends RedisProperties {

	/**
	 * 是否启用事务支持
	 */
	private boolean enableTransactionSupport = false;

	public boolean isEnableTransactionSupport() {
		return enableTransactionSupport;
	}

	public void setEnableTransactionSupport(boolean enableTransactionSupport) {
		this.enableTransactionSupport = enableTransactionSupport;
	}

}
