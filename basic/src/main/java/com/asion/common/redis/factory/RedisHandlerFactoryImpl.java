package com.asion.common.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.asion.common.redis.handler.RedisHandler;
import com.asion.common.util.SpringContextUtil;

/**
 * Redis数据库处理器构建工厂接口实现类
 * 
 * @author chenboyang
 *
 */
public class RedisHandlerFactoryImpl implements RedisHandlerFactory {

	/**
	 * 连接工厂
	 */
	private RedisConnectionFactory connectionFactory;

	/**
	 * 字符串序列化器
	 */
	private StringRedisSerializer stringRedisSerializer;

	/**
	 * JSON序列化器
	 */
	private GenericJackson2JsonRedisSerializer jsonRedisSerializer;

	/**
	 * 构建Redis处理器工厂
	 */
	public RedisHandlerFactoryImpl() {
		this(SpringContextUtil.getBean(RedisConnectionFactory.class));
	}

	/**
	 * 构建Redis处理器工厂
	 * 
	 * @param connectionFactory
	 *            连接工厂
	 */
	public RedisHandlerFactoryImpl(RedisConnectionFactory connectionFactory) {
		this(connectionFactory, SpringContextUtil.getBean(StringRedisSerializer.class),
				SpringContextUtil.getBean(GenericJackson2JsonRedisSerializer.class));
	}

	public RedisHandlerFactoryImpl(RedisConnectionFactory connectionFactory,
			StringRedisSerializer stringRedisSerializer, GenericJackson2JsonRedisSerializer jsonRedisSerializer) {
		this.connectionFactory = connectionFactory;
		this.stringRedisSerializer = stringRedisSerializer;
		this.jsonRedisSerializer = jsonRedisSerializer;
	}

	@Override
	public <K, V> RedisHandler<K, V> buildHandler() {
		return buildHandler(connectionFactory);
	}

	@Override
	public <K, V> RedisHandler<K, V> buildHandler(RedisConnectionFactory connectionFactory) {
		RedisHandler<K, V> redisHandler = new RedisHandler<K, V>(connectionFactory);
		redisHandler.setKeySerializer(stringRedisSerializer);
		redisHandler.setValueSerializer(jsonRedisSerializer);
		redisHandler.setHashKeySerializer(stringRedisSerializer);
		redisHandler.setHashValueSerializer(jsonRedisSerializer);
		return redisHandler;
	}

	public RedisConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public StringRedisSerializer getStringRedisSerializer() {
		return stringRedisSerializer;
	}

	public void setStringRedisSerializer(StringRedisSerializer stringRedisSerializer) {
		this.stringRedisSerializer = stringRedisSerializer;
	}

	public GenericJackson2JsonRedisSerializer getJsonRedisSerializer() {
		return jsonRedisSerializer;
	}

	public void setJsonRedisSerializer(GenericJackson2JsonRedisSerializer jsonRedisSerializer) {
		this.jsonRedisSerializer = jsonRedisSerializer;
	}

}
