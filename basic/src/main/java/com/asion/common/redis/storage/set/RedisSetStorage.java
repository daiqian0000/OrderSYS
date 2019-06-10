package com.asion.common.redis.storage.set;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

import com.asion.common.redis.storage.RedisContainerStorage;

/**
 * Redis集合存储接口
 * 
 * @author chenboyang
 *
 * @param <E>
 *            元素类型
 */
@SuppressWarnings("unchecked")
public interface RedisSetStorage<E> extends RedisContainerStorage<Object, E> {

	default RedisOperations<Object, E> operations() {
		return ops().getOperations();
	}

	/**
	 * 获取列表操作器
	 * 
	 * @return 哈希操作器
	 */
	default SetOperations<Object, E> ops() {
		return handler().opsForSet();
	}

	/**
	 * 获取当前集合与其它集合的差集
	 * 
	 * @param otherKey
	 *            其它集合键
	 * @return 差集
	 */
	default Set<E> difference(Object otherKey) {
		return ops().difference(key(), otherKey);
	}

	/**
	 * 获取当前集合与其它多个集合的差集
	 * 
	 * @param otherKeys
	 *            其它集合键集
	 * @return 差集
	 */
	default Set<E> difference(Collection<Object> otherKeys) {
		return ops().difference(key(), otherKeys);
	}

	/**
	 * 将当前集合与其它集合的差集存入目标集合
	 * 
	 * @param otherKey
	 *            其它集合键
	 * @param destKey
	 *            目标集合键
	 * @return 存入集合长度
	 */
	default Long differenceAndStore(Object otherKey, Object destKey) {
		return ops().differenceAndStore(key(), otherKey, destKey);
	}

	/**
	 * 将当前集合与其它多个集合的差集存入目标集合
	 * 
	 * @param otherKeys
	 *            其它集合键集
	 * @param destKey
	 *            目标集合键
	 * @return 存入集合长度
	 */
	default Long differenceAndStore(Collection<Object> otherKeys, Object destKey) {
		return ops().differenceAndStore(key(), otherKeys, destKey);
	}

	/**
	 * 获取当前集合与其它集合的交集
	 * 
	 * @param otherKey
	 *            其它集合键
	 * @return 交集
	 */
	default Set<E> intersect(Object otherKey) {
		return ops().intersect(key(), otherKey);
	}

	/**
	 * 获取当前集合与其它多个集合的交集
	 * 
	 * @param otherKeys
	 *            其它集合键集
	 * @return 交集
	 */
	default Set<E> intersect(Collection<Object> otherKeys) {
		return ops().intersect(key(), otherKeys);
	}

	/**
	 * 将当前集合与其它集合的交集存入目标集合
	 * 
	 * @param otherKey
	 *            其它集合键
	 * @param destKey
	 *            目标集合键
	 * @return 存入集合长度
	 */
	default Long intersectAndStore(Object otherKey, Object destKey) {
		return ops().intersectAndStore(key(), otherKey, destKey);
	}

	/**
	 * 将当前集合与其它多个集合的交集存入目标集合
	 * 
	 * @param otherKeys
	 *            其它集合键集
	 * @param destKey
	 *            目标集合键
	 * @return 存入集合长度
	 */
	default Long intersectAndStore(Collection<Object> otherKeys, Object destKey) {
		return ops().intersectAndStore(key(), otherKeys, destKey);
	}

	/**
	 * 获取当前集合与其它集合的并集
	 * 
	 * @param otherKey
	 *            其它集合键
	 * @return 并集
	 */
	default Set<E> union(Object otherKey) {
		return ops().union(key(), otherKey);
	}

	/**
	 * 获取当前集合与其它多个集合的并集
	 * 
	 * @param otherKeys
	 *            其它集合键集
	 * @return 并集
	 */
	default Set<E> union(Collection<Object> otherKeys) {
		return ops().union(key(), otherKeys);
	}

	/**
	 * 将当前集合与其它集合的并集存入目标集合
	 * 
	 * @param otherKey
	 *            其它集合键
	 * @param destKey
	 *            目标集合键
	 * @return 存入集合长度
	 */
	default Long unionAndStore(Object otherKey, Object destKey) {
		return ops().unionAndStore(key(), otherKey, destKey);
	}

	/**
	 * 将当前集合与其它多个集合的并集存入目标集合
	 * 
	 * @param otherKeys
	 *            其它集合键集
	 * @param destKey
	 *            目标集合键
	 * @return 存入集合长度
	 */
	default Long unionAndStore(Collection<Object> otherKeys, Object destKey) {
		return ops().unionAndStore(key(), otherKeys, destKey);
	}

	/**
	 * 新增元素
	 * 
	 * @param values
	 *            元素集合
	 * @return 新增元素数量
	 */
	default Long add(E... values) {
		return ops().add(key(), values);
	}

	/**
	 * 新增一组元素
	 * 
	 * @param values
	 *            元素集合
	 * @return 新增元素数量
	 */
	default Long addAll(Collection<? extends E> values) {
		return add((E[]) values.toArray());
	}

	/**
	 * 判断元素是否是集合的成员
	 * 
	 * @param o
	 *            元素
	 * @return 是否是集合的成员
	 */
	default Boolean isMember(E o) {
		return ops().isMember(key(), o);
	}

	/**
	 * 获取集合中的所有元素
	 * 
	 * @return 元素集合
	 */
	default Set<E> members() {
		return ops().members(key());
	}

	/**
	 * 将元素从当前集合移动到目标集合
	 * 
	 * @param value
	 *            元素
	 * @param destKey
	 *            目标集合键
	 * @return 是否移动成功
	 */
	default Boolean move(E value, Object destKey) {
		return ops().move(key(), value, destKey);
	}

	/**
	 * 随机获取集合中一个元素
	 * 
	 * @return 元素
	 */
	default E randomMember() {
		return ops().randomMember(key());
	}

	/**
	 * 随机获取集合中多个不重复元素
	 * 
	 * @param count
	 *            获取元素数量
	 * @return 元素集合
	 */
	default Set<E> distinctRandomMembers(long count) {
		return ops().distinctRandomMembers(key(), count);
	}

	/**
	 * 随机获取集合中多个元素
	 * 
	 * @param count
	 *            获取元素数量
	 * @return 元素集合
	 */
	default List<E> randomMembers(long count) {
		return ops().randomMembers(key(), count);
	}

	/**
	 * 移除集合中多个元素
	 * 
	 * @param values
	 *            元素集合
	 * @return 移除元素数量
	 */
	default Long remove(Collection<? extends E> values) {
		return ops().remove(key(), values.toArray());
	}

	/**
	 * 在集合中随机拉出一个元素
	 * 
	 * @return 元素
	 */
	default E pop() {
		return ops().pop(key());
	}

	/**
	 * 获取集合长度
	 * 
	 * @return 长度
	 */
	default Long size() {
		return ops().size(key());
	}

	/**
	 * 扫描元素
	 * 
	 * @param options
	 *            扫描配置项
	 * @return 元素光标
	 */
	default Cursor<E> scan(ScanOptions options) {
		return ops().scan(key(), options);
	}

}
