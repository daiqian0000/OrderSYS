package com.asion.common.redis.storage.list;

import java.util.Collection;
import java.util.List;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;

import com.asion.common.redis.storage.RedisContainerStorage;

/**
 * Redis列表存储接口
 * 
 * @author chenboyang
 * 
 * @param <E>
 *            元素类型
 */
@SuppressWarnings("unchecked")
public interface RedisListStorage<E> extends RedisContainerStorage<Object, E> {

	default RedisOperations<Object, E> operations() {
		return ops().getOperations();
	}

	/**
	 * 获取列表操作器
	 * 
	 * @return 哈希操作器
	 */
	default ListOperations<Object, E> ops() {
		return handler().opsForList();
	}

	/**
	 * 获取指定范围内的元素
	 * 
	 * @param start
	 *            开始下标
	 * @param end
	 *            结束下标
	 * @return 元素列表
	 */
	default List<E> range(long start, long end) {
		return ops().range(key(), start, end);
	}

	
	/**
	 * 根据开始下标和结束下载截取范围
	 * 
	 * @param start
	 *            开始下标
	 * @param end
	 *            结束下载
	 */
	default void trim(long start, long end) {
		ops().trim(key(), start, end);
	}

	/**
	 * 获取列表长度
	 * 
	 * @return 长度
	 */
	default Long size() {
		return ops().size(key());
	}

	/**
	 * 从列表左侧插入元素
	 * 
	 * @param value
	 *            元素
	 * @return 列表长度
	 */
	default Long leftPush(E value) {
		return ops().leftPush(key(), value);
	}

	/**
	 * 从列表左侧插入多个元素
	 * 
	 * @param values
	 *            多个元素
	 * @return 列表长度
	 */
	default Long leftPushAll(E... values) {
		return ops().leftPushAll(key(), values);
	}

	/**
	 * 从列表左侧插入一组元素
	 * 
	 * @param values
	 *            元素集合
	 * @return 列表长度
	 */
	default Long leftPushAll(Collection<? extends E> values) {
		return ops().leftPushAll(key(), (Collection<E>) values);
	}

	/**
	 * 从列表右侧插入元素
	 * 
	 * @param value
	 *            元素
	 * @return 列表长度
	 */
	default Long rightPush(E value) {
		return ops().rightPush(key(), value);
	}

	/**
	 * 从列表右侧插入多个元素
	 * 
	 * @param values
	 *            多个元素
	 * @return 列表长度
	 */
	default Long rightPushAll(E... values) {
		return ops().rightPushAll(key(), values);
	}

	/**
	 * 从列表右侧插入一组元素
	 * 
	 * @param values
	 *            元素集合
	 * @return 列表长度
	 */
	default Long rightPushAll(Collection<? extends E> values) {
		return ops().rightPushAll(key(), (Collection<E>) values);
	}

	/**
	 * 在列表下标位置放置一个元素
	 * 
	 * @param index
	 *            下标
	 * @param value
	 *            元素
	 */
	default void set(long index, E value) {
		ops().set(key(), index, value);
	}

	/**
	 * 在列表中删除一个元素
	 * 
	 * @param i
	 *            下标
	 * @param value
	 *            元素
	 * @return 列表长度
	 */
	default Long remove(long i, E value) {
		return ops().remove(key(), i, value);
	}

	/**
	 * 获取列表下标位置的元素
	 * 
	 * @param index
	 *            下标
	 * @return 元素
	 */
	default E index(long index) {
		return ops().index(key(), index);
	}

	/**
	 * 从列表左侧抽出一个元素
	 * 
	 * @return 元素
	 */
	default E leftPop() {
		return ops().leftPop(key());
	}

	/**
	 * 从列表右侧抽出一个元素
	 * 
	 * @return 元素
	 */
	default E rightPop() {
		return ops().rightPop(key());
	}

}
