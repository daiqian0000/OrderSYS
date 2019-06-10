package com.asion.common.redis.storage.value;

import com.asion.common.redis.annotation.RedisStorage;

/**
 * Redis默认值存储接口默认实现类
 * 
 * @author chenboyang
 *
 * @param <K>
 *            键类型
 * @param <V>
 *            值类型
 */
@RedisStorage(key = "")
public class DefaultRedisValueStorage<K, V> implements RedisValueStorage<K, V> {

}
