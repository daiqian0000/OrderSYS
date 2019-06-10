package com.asion.common.redis.storage.value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.asion.common.redis.annotation.RedisStorage;

/**
 * Redis目录结构默认值存储接口
 * 
 * @author chenboyang
 *
 * @param <V>
 *            值类型
 */
@SuppressWarnings("unchecked")
public interface CatalogRedisValueStorage<K, V> extends RedisValueStorage<K, V> {

	/**
	 * 组装存储键
	 * 
	 * @param key
	 *            业务键
	 * @return 存储键
	 */
	default K assembleKey(K key) {
		String keyPrefix = "";
		RedisStorage redisModel = getClass().getAnnotation(RedisStorage.class);
		if (redisModel != null && redisModel.key() != null) {
			keyPrefix = redisModel.key();
		}
		return (K) (keyPrefix + key.toString());
	}

	default Boolean has(K key) {
		return RedisValueStorage.super.has(assembleKey(key));
	}
	
	default void set(K key, V value) {
		RedisValueStorage.super.set(assembleKey(key), value);
	}

	default void set(K key, V value, long timeout, TimeUnit unit) {
		RedisValueStorage.super.set(assembleKey(key), value, timeout, unit);
	}

	default void multiSet(Map<? extends K, ? extends V> m) {
		if (MapUtils.isNotEmpty(m)) {
			try {
				Map<K, V> map = m.getClass().newInstance();
				m.forEach((k, v) -> map.put(assembleKey(k), v));
				RedisValueStorage.super.multiSet(map);
			} catch (InstantiationException | IllegalAccessException e) {
				Logger.getLogger(getClass()).error(e.getMessage(), e);
			}
		}
	}

	default V get(K key) {
		return RedisValueStorage.super.get(assembleKey(key));
	}

	default List<V> multiGet(Collection<K> keys) {
		if (CollectionUtils.isNotEmpty(keys)) {
			keys.stream().map((k) -> assembleKey(k));
		}
		return RedisValueStorage.super.multiGet(keys);
	}

	default Integer append(K key, String value) {
		return RedisValueStorage.super.append(assembleKey(key), value);
	}

	default Long size(K key) {
		return RedisValueStorage.super.size(assembleKey(key));
	}

	default void delete(K key) {
		RedisValueStorage.super.delete(assembleKey(key));
	}

	default void delete(Collection<K> keys) {
		if (CollectionUtils.isNotEmpty(keys)) {
			keys.stream().map((k) -> assembleKey(k));
		}
		RedisValueStorage.super.delete(keys);
	}
	
}
