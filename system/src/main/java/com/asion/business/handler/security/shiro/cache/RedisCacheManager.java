package com.asion.business.handler.security.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.asion.common.redis.handler.RedisHandler;
import com.asion.common.util.SpringContextUtil;

/**
 * Redis缓存管理器
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RedisCacheManager implements CacheManager {

	/**
	 * 会话Redis操作器
	 */
	private RedisHandler sessionRedisHandler;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new RedisCache<K, V>(name, getSessionRedisHandler());
	}

	/**
	 * 获取会话Redis操作器
	 * 
	 * @return 会话Redis操作器
	 */
	private <K, V> RedisHandler<K, V> getSessionRedisHandler() {
		if (sessionRedisHandler == null) {
//			sessionRedisHandler = SpringContextUtil.getBean(RedisHandler.class);
			RedisHandler<K, V> redisHandler = SpringContextUtil.getBean(RedisHandler.class);
			if (redisHandler != null) {
				sessionRedisHandler = new RedisHandler<K, V>(redisHandler.getConnectionFactory());
				sessionRedisHandler.setKeySerializer(new StringRedisSerializer());
				sessionRedisHandler.setValueSerializer(new JdkSerializationRedisSerializer());
				sessionRedisHandler.afterPropertiesSet();
			}
		}
		return sessionRedisHandler;
	}

}
