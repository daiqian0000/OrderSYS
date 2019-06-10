package com.asion.common.redis.storage.zset;

import com.asion.common.redis.annotation.RedisStorage;

/**
 * Redis有序集合存储接口默认实现类
 * 
 * @author chenboyang
 *
 * @param <E>
 *            有序集合元素类型
 */
@RedisStorage(key = "default:zset")
public class DefaultRedisZSetStorage<E> implements RedisZSetStorage<E> {

}
