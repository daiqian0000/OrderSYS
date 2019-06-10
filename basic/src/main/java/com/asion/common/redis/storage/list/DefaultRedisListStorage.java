package com.asion.common.redis.storage.list;

import com.asion.common.redis.annotation.RedisStorage;

/**
 * Redis列表存储接口默认实现类
 * 
 * @author chenboyang
 *
 * @param <E>
 *            列表元素类型
 */
@RedisStorage(key = "default:list")
public class DefaultRedisListStorage<E> implements RedisListStorage<E> {

}
