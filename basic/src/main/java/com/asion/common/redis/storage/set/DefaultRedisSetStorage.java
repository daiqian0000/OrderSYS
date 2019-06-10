package com.asion.common.redis.storage.set;

import com.asion.common.redis.annotation.RedisStorage;

/**
 * Redis集合存储接口默认实现类
 * 
 * @author chenboyang
 *
 * @param <E>
 *            集合元素类型
 */
@RedisStorage(key = "default:set")
public class DefaultRedisSetStorage<E> implements RedisSetStorage<E> {

}
