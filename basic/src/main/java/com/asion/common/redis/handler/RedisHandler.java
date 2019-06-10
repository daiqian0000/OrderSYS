package com.asion.common.redis.handler;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.asion.common.redis.operate.HashOperate;
import com.asion.common.redis.operate.ListOperate;
import com.asion.common.redis.operate.ValueOperate;

/**
 * <h1>Redis存储处理类</h1>
 * <p>
 * Redis支持五种数据类型：string（字符串），hash（哈希），list（列表），set（集合）及zset（sorted set：有序集合）。
 * </p>
 * <p>
 * 五种类型分别有对应的处理对象
 * </p>
 * <p>
 * string（字符串）的处理对象为类的opsForValue()方法返回对象
 * </p>
 * <p>
 * hash（哈希）的处理对象为类的opsForHash()方法返回对象
 * </p>
 * <p>
 * list（列表）的处理对象为类的opsForList()方法返回对象
 * </p>
 * <p>
 * set（集合）的处理对象为类的opsForSet()方法返回对象
 * </p>
 * <p>
 * zset（有序集合）的处理对象为类的opsForZSet()方法返回对象
 * </p>
 * 
 * @author chenboyang
 * 
 * @param <K>
 *            键类型
 * @param <V>
 *            值类型
 */
@SuppressWarnings("unchecked")
public class RedisHandler<K, V> extends RedisTemplate<K, V>
		implements ValueOperate<K, V>, HashOperate<K, V>, ListOperate<K, V> {

	/**
	 * 构建处理器
	 */
	public RedisHandler() {

	}

	/**
	 * 构建处理器
	 * 
	 * @param connectionFactory
	 *            连接工厂
	 */
	public RedisHandler(RedisConnectionFactory connectionFactory) {
		setConnectionFactory(connectionFactory);
	}

	// ==============================字符串操作==============================

	public V valueGet(Object key) {
		return opsForValue().get(key);
	}

	public List<V> valueMultiGet(Collection<K> key) {
		return opsForValue().multiGet(key);
	}

	public void valueMultiSet(Map<? extends K, ? extends V> key) {
		opsForValue().multiSet(key);
	}

	public void valueSet(K key, V value) {
		opsForValue().set(key, value);
	}

	public void valueSet(K key, V value, long timeout, TimeUnit unit) {
		opsForValue().set(key, value, timeout, unit);
	}

	// ==============================哈希操作==============================

	public Long hashDelete(K key, K... hashKeys) {
		return opsForHash().delete(key, hashKeys);
	}

	public Boolean hashHasKey(K key, K hashKey) {
		return opsForHash().hasKey(key, hashKey);
	}

	public V hashGet(K key, K hashKey) {
		return (V) opsForHash().get(key, hashKey);
	}

	public List<V> hashMultiGet(K key, Collection<Object> hashKeys) {
		return (List<V>) opsForHash().multiGet(key, hashKeys);
	}

	public void hashPutAll(K key, Map<? extends K, ? extends V> m) {
		opsForHash().putAll(key, m);
	}

	public void hashPut(K key, K hashKey, V value) {
		opsForHash().put(key, hashKey, value);
	}

	public List<V> hashValues(K key) {
		return (List<V>) opsForHash().values(key);
	}

	public Map<K, V> hashEntries(K key) {
		return (Map<K, V>) opsForHash().entries(key);
	}

	// ==============================列表操作==============================

	public List<V> listGet(K key) {
		return listRange(key, 0, opsForList().size(key) - 1);
	}

	public List<V> listRange(K key, long start, long end) {
		return opsForList().range(key, start, end);
	}

	public void listTrim(K key, long start, long end) {
		opsForList().trim(key, start, end);
	}

	public Long listLeftPush(K key, V value) {
		return opsForList().leftPush(key, value);
	}

	public Long listLeftPushAll(K key, V... values) {
		return opsForList().leftPushAll(key, values);
	}

	public Long listLeftPushAll(K key, Collection<V> values) {
		return opsForList().leftPushAll(key, values);
	}

	public Long listLeftPush(K key, V pivot, V value) {
		return opsForList().leftPush(key, pivot, value);
	}

	public Long listRightPush(K key, V value) {
		return opsForList().rightPush(key, value);
	}

	public Long listRightPushAll(K key, V... values) {
		return opsForList().rightPushAll(key, values);
	}

	public Long listRightPushAll(K key, Collection<V> values) {
		return opsForList().rightPushAll(key, values);
	}

	public Long listRightPush(K key, V pivot, V value) {
		return opsForList().rightPush(key, pivot, value);
	}

	public void listSet(K key, long index, V value) {
		opsForList().set(key, index, value);
	}

	public Long listRemove(K key, V value) {
		return opsForList().remove(key, 0, value);
	}

	public Long listRemove(K key, long i, V value) {
		return opsForList().remove(key, i, value);
	}

	public V listIndex(K key, long index) {
		return opsForList().index(key, index);
	}

	public V listLeftPop(K key) {
		return opsForList().leftPop(key);
	}

	public V listRightPop(K key) {
		return opsForList().rightPop(key);
	}

}
