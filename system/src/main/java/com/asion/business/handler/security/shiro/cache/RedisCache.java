package com.asion.business.handler.security.shiro.cache;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.asion.common.redis.handler.RedisHandler;
import com.asion.common.redis.storage.value.CatalogRedisValueStorage;
import com.asion.common.util.SpringContextUtil;
import com.asion.config.properties.SysProperties;

/**
 * Redis缓存类
 * 
 * @author chenboyang
 *
 * @param <K>
 *            键类型
 * @param <V>
 *            值类型
 */
@SuppressWarnings("unchecked")
public class RedisCache<K, V> implements Cache<K, V>, CatalogRedisValueStorage<K, V> {

	/**
	 * 缓存容器前缀
	 */
	private String prefix = "";

	/**
	 * Redis操作器
	 */
	private RedisHandler<K, V> redisHandler;

	/**
	 * 获取系统配置属性
	 * 
	 * @return 系统配置属性
	 */
	private SysProperties sysProperties() {
		return SpringContextUtil.getBean(SysProperties.class);
	}

	/**
	 * 构建Redis缓存
	 */
	public RedisCache() {

	}

	/**
	 * 构建Redis缓存
	 * 
	 * @param name
	 *            名称
	 */
	public RedisCache(String name) {
		this(name, null);
	}

	/**
	 * 构建Redis缓存
	 * 
	 * @param name
	 *            名称
	 * @param redisHandler
	 *            Redis操作器
	 */
	public RedisCache(String name, RedisHandler<K, V> redisHandler) {
		prefix += name + ":";
		this.redisHandler = redisHandler;
	}

	@Override
	public RedisHandler<K, V> handler() {
		if (redisHandler != null) {
			return redisHandler;
		} else {
			return CatalogRedisValueStorage.super.handler();
		}
	}

	@Override
	public K assembleKey(K key) {
		return (K) (prefix + key.toString());
	}

	@Override
	public void clear() throws CacheException {
		delete(keys());
	}

	@Override
	public V get(K key) throws CacheException {
		operations().expire(key, timeout(), TimeUnit.MINUTES);
		return CatalogRedisValueStorage.super.get(key);
	}

	@Override
	public Set<K> keys() {
		return operations().keys(assembleKey((K) ("*")));
	}

	@Override
	public V put(K key, V value) throws CacheException {
		V old = get(key);
		set(key, value, timeout(), TimeUnit.MINUTES);
		return old;
	}

	@Override
	public V remove(K key) throws CacheException {
		V old = get(key);
		delete(key);
		return old;
	}

	@Override
	public int size() {
		return keys().size();
	}

	@Override
	public Collection<V> values() {
		return multiGet(keys());
	}

	private long timeout() {
		if (sysProperties() != null) {
			return sysProperties().getTimeout();
		}
		return 30;
	}

}
