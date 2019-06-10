package com.asion.common.redis.storage.hash;

import com.asion.common.redis.annotation.RedisStorage;

/**
 * Redis哈希存储接口默认实现类
 * 
 * @author chenboyang
 *
 * @param <K>
 *            键类型
 * @param <V>
 *            值类型
 */
@RedisStorage(key = "default:hash")
public class DefaultRedisHashStorage<K, V> implements RedisHashStorage<K, V> {

}
