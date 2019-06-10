package com.asion.common.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.asion.common.util.SpringContextUtil;

/**
 * Redis数据库操作模板构建工厂接口实现类
 * 
 * @author chenboyang
 *
 */
public class RedisTemplateFactoryImpl implements RedisTemplateFactory {
	
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
	 * 构建Redis处理模板工厂
	 */
	public RedisTemplateFactoryImpl() {
		this(SpringContextUtil.getBean(RedisConnectionFactory.class));
	}

	/**
	 * 构建Redis处理模板工厂
	 * 
	 * @param connectionFactory
	 *            连接工厂
	 */
	public RedisTemplateFactoryImpl(RedisConnectionFactory connectionFactory) {
		this(connectionFactory, SpringContextUtil.getBean(StringRedisSerializer.class), SpringContextUtil.getBean(GenericJackson2JsonRedisSerializer.class));
	}
	
	public RedisTemplateFactoryImpl(RedisConnectionFactory connectionFactory, StringRedisSerializer stringRedisSerializer, GenericJackson2JsonRedisSerializer jsonRedisSerializer) {
		this.connectionFactory = connectionFactory;
		this.stringRedisSerializer = stringRedisSerializer;
		this.jsonRedisSerializer = jsonRedisSerializer;
	}
	
	
	@Override
	public <K, V> RedisTemplate<K, V> buildTemplate() {
		return buildTemplate(connectionFactory);
	}

	@Override
	public <K, V> RedisTemplate<K, V> buildTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<K, V> redisTemplate = new RedisTemplate<K, V>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(jsonRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(jsonRedisSerializer);
		return redisTemplate;
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
