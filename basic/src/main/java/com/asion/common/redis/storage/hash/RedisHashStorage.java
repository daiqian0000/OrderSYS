package com.asion.common.redis.storage.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;

import com.asion.common.redis.storage.RedisContainerStorage;

/**
 * Redis哈希存储接口
 * 
 * @author chenboyang
 * 
 * @param <K>
 *            哈希键类型
 * @param <V>
 *            哈希值类型
 */
@SuppressWarnings("unchecked")
public interface RedisHashStorage<K, V> extends RedisContainerStorage<Object, V> {

	default RedisOperations<Object, V> operations() {
		return (RedisOperations<Object, V>) ops().getOperations();
	}

	/**
	 * 获取哈希操作器
	 * 
	 * @return 哈希操作器
	 */
	default HashOperations<Object, K, V> ops() {
		return handler().opsForHash();
	}

	/**
	 * 哈希键是否存在
	 * 
	 * @param hashKey
	 *            哈希键
	 * @return 是否存在
	 */
	default Boolean has(K hashKey) {
		return ops().hasKey(key(), hashKey);
	}

	/**
	 * 放入哈希键值
	 * 
	 * @param hashKey
	 *            哈希键
	 * @param value
	 *            哈希值
	 */
	default void put(K hashKey, V value) {
		ops().put(key(), hashKey, value);
	}

	/**
	 * 放入一组哈希键值
	 * 
	 * @param m
	 *            哈希容器
	 */
	default void putAll(Map<? extends K, ? extends V> m) {
		ops().putAll(key(), m);
	}

	/**
	 * 删除一个哈希
	 * 
	 * @param hashKey
	 *            哈希键
	 */
	default Long delete(K hashKey) {
		return ops().delete(key(), hashKey);
	}

	/**
	 * 删除多个哈希
	 * 
	 * @param hashKeys
	 *            哈希键集合
	 */
	default Long delete(Collection<K> hashKeys) {
		return ops().delete(key(), hashKeys.toArray());
	}

	/**
	 * 获取一个哈希值
	 * 
	 * @param hashKey
	 *            哈希键
	 * @return 哈希值
	 */
	default V get(K hashKey) {
		return ops().get(key(), hashKey);
	}

	/**
	 * 获取一组哈希值
	 * 
	 * @param hashKeys
	 *            哈希键集合
	 * @return 哈希值集合
	 */
	default List<V> multiGet(Collection<K> hashKeys) {
		return ops().multiGet(key(), hashKeys);
	}

	/**
	 * 获取容器哈希数量
	 * 
	 * @return 数量
	 */
	default Long size() {
		return ops().size(key());
	}

	/**
	 * 获取整个哈希容器
	 * 
	 * @return 哈希容器
	 */
	default Map<K, V> getMap() {
		return ops().entries(key());
	}

	/**
	 * 获取全部键
	 * 
	 * @return 哈希键集合
	 */
	default Set<K> keys() {
		return ops().keys(key());
	}

	/**
	 * 获取全部值
	 * 
	 * @return 哈希值集合
	 */
	default List<V> values() {
		return ops().values(key());
	}

}
