package com.asion.config;

import com.asion.common.redis.annotation.RedisStorage;
import com.asion.common.redis.handler.RedisHandler;
import com.asion.common.spring.BeanNameGenerator;
import com.asion.config.properties.RedisSupportProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * 
 * @author chenboyang
 *
 */
@Configuration
@ComponentScan(includeFilters = @Filter(RedisStorage.class) , basePackages = "com.asion", useDefaultFilters = false, nameGenerator = BeanNameGenerator.class)
public class RedisConfig {

	/**
	 * 连接属性配置
	 * 
	 * @return 属性配置
	 */
	@Bean
	@Primary
	@ConfigurationProperties("spring.redis")
	public RedisSupportProperties redisSupportProperties() {
		return new RedisSupportProperties();
	}

	/**
	 * Redis字符串序列化器
	 * 
	 * @return 序列化器
	 */
	@Bean
	public StringRedisSerializer stringRedisSerializer() {
		return new StringRedisSerializer();
	}

	/**
	 * Redis-JSON序列化器
	 * 
	 * @return 序列化器
	 */
	@Bean
	public GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
		return new GenericJackson2JsonRedisSerializer(mapper);
	}

	/**
	 * Redis字符串操作模板
	 * 
	 * @param connectionFactory
	 *            连接工厂
	 * @return 操作模板
	 */
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
		stringRedisTemplate.setDefaultSerializer(stringRedisSerializer());
		stringRedisTemplate.setEnableTransactionSupport(redisSupportProperties().isEnableTransactionSupport());
		stringRedisTemplate.afterPropertiesSet();
		return stringRedisTemplate;
	}

	/**
	 * Redis普通操作模板
	 * 
	 * @param connectionFactory
	 *            连接工厂
	 * @return 操作模板
	 */
	@Bean
	public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<K, V> redisTemplate = new RedisTemplate<K, V>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(jsonRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setHashValueSerializer(jsonRedisSerializer());
		redisTemplate.setEnableTransactionSupport(redisSupportProperties().isEnableTransactionSupport());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * Redis操作处理器
	 * 
	 * @param connectionFactory
	 *            连接工厂
	 * @return 操作处理器
	 */
	@Bean
	public <K, V> RedisHandler<K, V> redisHandler(RedisConnectionFactory connectionFactory) {
		RedisHandler<K, V> redisHandler = new RedisHandler<K, V>();
		redisHandler.setConnectionFactory(connectionFactory);
		redisHandler.setKeySerializer(stringRedisSerializer());
		redisHandler.setValueSerializer(jsonRedisSerializer());
		redisHandler.setHashKeySerializer(stringRedisSerializer());
		redisHandler.setHashValueSerializer(jsonRedisSerializer());
		redisHandler.setEnableTransactionSupport(redisSupportProperties().isEnableTransactionSupport());
		redisHandler.afterPropertiesSet();
		return redisHandler;
	}

}
