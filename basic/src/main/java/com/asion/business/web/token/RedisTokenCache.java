package com.asion.business.web.token;

import java.util.concurrent.TimeUnit;

import org.springframework.transaction.annotation.Transactional;

import com.asion.common.redis.annotation.RedisStorage;
import com.asion.common.redis.storage.value.CatalogRedisValueStorage;

/**
 * Token缓存服务Redis存储接口
 * 
 * @author chenboyang
 *
 * @param <T> Token信息业务类型
 */
@RedisStorage(key = "token:")
public class RedisTokenCache<T> implements TokenCache<T>, CatalogRedisValueStorage<String, Token<T>> {

	@Transactional
	public String saveToken(String id) {
		String token = createToken(id);
		set(token, Token.token(id), timeout(), TimeUnit.MINUTES);
		return token;
	}

	@Transactional
	public String saveToken(String id, T info) {
		String token = createToken(id);
		set(token, Token.token(id, info), timeout(), TimeUnit.MINUTES);
		return token;
	}

	@Transactional(readOnly = true)
	public boolean checkToken(String token) {
		Boolean has = has(token);
		return has != null ? has : false;
	}

	@Transactional
	public void deleteToken(String token) {
		delete(token);
	}

	@Transactional(readOnly = true)
	public T getToken(String token) {
		Token<T> info = get(token);
		if (info != null) {
			return info.getInfo();
		}
		return null;
	}

}
