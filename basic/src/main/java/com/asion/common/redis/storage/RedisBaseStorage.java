package com.asion.common.redis.storage;

import org.springframework.data.redis.core.RedisOperations;

import com.asion.common.redis.handler.RedisHandler;
import com.asion.common.util.SpringContextUtil;

/**
 * Redis容器存储接口
 * 
 * @author chenboyang
 *
 * @param <K>
 *            键类型
 * @param <V>
 *            值类型
 */
@SuppressWarnings("unchecked")
public interface RedisBaseStorage<K, V> {

	/**
	 * 获取操作处理器
	 * 
	 * @return 操作处理器
	 */
	default RedisHandler<K, V> handler() {
		return SpringContextUtil.getBean(RedisHandler.class);
	}

	/**
	 * 获取原始操作器
	 * 
	 * @return 操作器
	 */
	RedisOperations<K, V> operations();

}
