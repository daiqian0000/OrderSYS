package com.asion.common.redis.storage.value;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;

import com.asion.common.redis.storage.RedisBaseStorage;

/**
 * Redis值存储接口
 * 
 * @author chenboyang
 *
 * @param <K>
 *            键类型
 * @param <V>
 *            值类型
 */
public interface RedisValueStorage<K, V> extends RedisBaseStorage<K, V> {

	default RedisOperations<K, V> operations() {
		return ops().getOperations();
	}

	/**
	 * 获取哈希操作处理器
	 * 
	 * @return 哈希操作器
	 */
	default ValueOperations<K, V> ops() {
		return handler().opsForValue();
	}
	
	/**
	 * 根据键判断是否存在
	 * 
	 * @param key
	 *            键
	 * @return 是否存在
	 */
	default Boolean has(K key) {
		return operations().hasKey(key);
	}
	
	/**
	 * 设置键值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	default void set(K key, V value) {
		ops().set(key, value);
	}

	/**
	 * 设置拥有过期时间的键值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param timeout
	 *            时间数值
	 * @param unit
	 *            时间单位
	 */
	default void set(K key, V value, long timeout, TimeUnit unit) {
		ops().set(key, value, timeout, unit);
	}

	/**
	 * 设置一组键值
	 * 
	 * @param m
	 *            键值容器
	 */
	default void multiSet(Map<? extends K, ? extends V> m) {
		ops().multiSet(m);
	}

	/**
	 * 获取值
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	default V get(K key) {
		return ops().get(key);
	}

	/**
	 * 获取一组值
	 * 
	 * @param keys
	 *            键集合
	 * @return 值集合
	 */
	default List<V> multiGet(Collection<K> keys) {
		return ops().multiGet(keys);
	}

	/**
	 * 追加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return 追加后的值长度
	 */
	default Integer append(K key, String value) {
		return ops().append(key, value);
	}

	/**
	 * 获取值长度
	 * 
	 * @param key
	 *            键
	 * @return 长度
	 */
	default Long size(K key) {
		return ops().size(key);
	}

	/**
	 * 根据键删除对象
	 * 
	 * @param key
	 *            键
	 */
	default void delete(K key) {
		operations().delete(key);
	}
	
	/**
	 * 根据键集删除对象
	 * 
	 * @param keys
	 *            键集
	 */
	default void delete(Collection<K> keys) {
		operations().delete(keys);
	}

}
