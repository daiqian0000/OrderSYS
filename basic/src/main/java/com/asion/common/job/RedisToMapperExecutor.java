package com.asion.common.job;

import com.asion.common.base.BaseMapper;
import com.asion.common.redis.storage.hash.RedisHashStorage;
import com.asion.common.redis.storage.list.RedisListStorage;
import com.asion.common.redis.storage.set.RedisSetStorage;

/**
 * Redis到数据访问层执行器接口
 * 
 * @author chenboyang
 *
 */
public interface RedisToMapperExecutor {

	/**
	 * 从Redis哈希到关系型数据库新增列表
	 * 
	 * @param redisHash
	 *            Redis哈希接口
	 * @param mapper
	 *            数据访问接口
	 */
	<K, V> void addListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper);

	/**
	 * 从Redis哈希到关系型数据库新增列表
	 * 
	 * @param redisHash
	 *            Redis哈希接口
	 * @param mapper
	 *            数据访问接口
	 * @param batchSize
	 *            批次数据操作条数
	 */
	<K, V> void addListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize);

	/**
	 * 从Redis哈希到关系型数据库修改列表
	 * 
	 * @param redisHash
	 *            Redis哈希接口
	 * @param mapper
	 *            数据访问接口
	 */
	<K, V> void modListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper);

	/**
	 * 从Redis哈希到关系型数据库修改列表
	 * 
	 * @param redisHash
	 *            Redis哈希接口
	 * @param mapper
	 *            数据访问接口
	 * @param batchSize
	 *            批次数据操作条数
	 */
	<K, V> void modListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize);

	/**
	 * 从Redis哈希到关系型数据库新增或修改列表
	 * 
	 * @param redisHash
	 *            Redis哈希接口
	 * @param mapper
	 *            数据访问接口
	 */
	<K, V> void addOrModListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper);

	/**
	 * 从Redis哈希到关系型数据库新增或修改列表
	 * 
	 * @param redisHash
	 *            Redis哈希接口
	 * @param mapper
	 *            数据访问接口
	 * @param batchSize
	 *            批次数据操作条数
	 */
	<K, V> void addOrModListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize);

	/**
	 * 从Redis列表到关系型数据库新增列表
	 * 
	 * @param redisList
	 *            Redis列表接口
	 * @param mapper
	 *            数据访问接口
	 */
	<E> void addListRedisListToMapper(RedisListStorage<E> redisList, BaseMapper<E> mapper);

	/**
	 * 从Redis列表到关系型数据库新增列表
	 * 
	 * @param redisList
	 *            Redis列表接口
	 * @param mapper
	 *            数据访问接口
	 * @param batchSize
	 *            批次数据操作条数
	 */
	<E> void addListRedisListToMapper(RedisListStorage<E> redisList, BaseMapper<E> mapper, int batchSize);

	/**
	 * 从Redis列表到关系型数据库修改列表
	 * 
	 * @param redisList
	 *            Redis列表接口
	 * @param mapper
	 *            数据访问接口
	 */
	<E> void modListRedisListToMapper(RedisListStorage<E> redisList, BaseMapper<E> mapper);

	/**
	 * 从Redis列表到关系型数据库修改列表
	 * 
	 * @param redisList
	 *            Redis列表接口
	 * @param mapper
	 *            数据访问接口
	 * @param batchSize
	 *            批次数据操作条数
	 */
	<E> void modListRedisListToMapper(RedisListStorage<E> redisList, BaseMapper<E> mapper, int batchSize);

	/**
	 * 从Redis集合到关系型数据库新增列表
	 * 
	 * @param redisSet
	 *            Redis集合接口
	 * @param mapper
	 *            数据访问接口
	 */
	<E> void addListRedisSetToMapper(RedisSetStorage<E> redisSet, BaseMapper<E> mapper);

	/**
	 * 从Redis集合到关系型数据库新增列表
	 * 
	 * @param redisSet
	 *            Redis集合接口
	 * @param mapper
	 *            数据访问接口
	 * @param batchSize
	 *            批次数据操作条数
	 */
	<E> void addListRedisSetToMapper(RedisSetStorage<E> redisSet, BaseMapper<E> mapper, int batchSize);

	/**
	 * 从Redis集合到关系型数据库修改列表
	 * 
	 * @param redisSet
	 *            Redis集合接口
	 * @param mapper
	 *            数据访问接口
	 */
	<E> void modListRedisSetToMapper(RedisSetStorage<E> redisSet, BaseMapper<E> mapper);

	/**
	 * 从Redis集合到关系型数据库修改列表
	 * 
	 * @param redisSet
	 *            Redis集合接口
	 * @param mapper
	 *            数据访问接口
	 * @param batchSize
	 *            批次数据操作条数
	 */
	<E> void modListRedisSetToMapper(RedisSetStorage<E> redisSet, BaseMapper<E> mapper, int batchSize);

}
