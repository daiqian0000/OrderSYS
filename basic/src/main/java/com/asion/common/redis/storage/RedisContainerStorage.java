package com.asion.common.redis.storage;

import com.asion.common.redis.annotation.RedisStorage;

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
public interface RedisContainerStorage<K, V> extends RedisBaseStorage<K, V> {

	/**
	 * 获取容器键
	 * 
	 * @return 容器键
	 */
	default K key() {
		String key = "";
		RedisStorage redisModel = this.getClass().getAnnotation(RedisStorage.class);
		if (redisModel != null) {
			key = redisModel.key();
		}
		return (K) key;
	}

	/**
	 * 清除整个容器
	 */
	default void clear() {
		operations().delete(key());
	}

}
