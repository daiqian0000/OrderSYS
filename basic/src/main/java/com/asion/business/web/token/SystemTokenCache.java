package com.asion.business.web.token;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import com.asion.common.model.time.TimeContainerFactory;
import com.asion.common.model.time.TimeMap;

/**
 * Token缓存服务系统存储接口
 * 
 * @author chenboyang
 *
 * @param <T> Token信息业务类型
 */
@ConditionalOnWebApplication
@Component
public class SystemTokenCache<T> implements TokenCache<T> {

	/**
	 * 获取Token存储容器
	 * 
	 * @return 存储容器
	 */
	private TimeMap<String, Token<T>> tokenMap() {
		if (!sysService().systemStorage().has(TokenCache.TOKEN)) {
			long timeout = timeout() * 60;
			TimeMap<String, Token<T>> map = TimeContainerFactory.createTimeHashMap(
					timeout - 1, 1, timeout, TimeUnit.SECONDS);
			sysService().systemStorage().set(TokenCache.TOKEN, map);
		}
		return sysService().systemStorage().get(TokenCache.TOKEN);
	}

	public String saveToken(String id) {
		String token = createToken(id);
		tokenMap().put(token, Token.token(id));
		return token;
	}

	public String saveToken(String id, T info) {
		String token = createToken(id);
		tokenMap().put(token, Token.token(id, info));
		return token;
	}

	public boolean checkToken(String token) {
		return tokenMap().containsKey(token);
	}
	
	public void deleteToken(String token) {
		tokenMap().remove(token);
	}
	
	public T getToken(String token) {
		Token<T> info = tokenMap().get(token);
		if (info != null) {
			return info.getInfo();
		}
		return null;
	}

}
