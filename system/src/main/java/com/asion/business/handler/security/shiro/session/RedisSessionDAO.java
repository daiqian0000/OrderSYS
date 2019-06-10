package com.asion.business.handler.security.shiro.session;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import com.asion.business.handler.security.shiro.cache.RedisCache;

/**
 * Redis会话存储DAO
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("unchecked")
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

	protected Serializable doCreate(Session session) {
		Serializable sessionId = super.doCreate(session);
		redisCache().put(sessionId, session);
		return sessionId;
	}

	protected Session doReadSession(Serializable sessionId) {
		Session session = super.doReadSession(sessionId);
		if (session == null) {
			session = super.getCachedSession(sessionId);
		}
		return session;
	}

	protected void doUpdate(Session session) {
		super.doUpdate(session);
		if (redisCache().has(session.getId())) {
			redisCache().put(session.getId(), session);
		}
	}

	protected void doDelete(Session session) {
		super.doDelete(session);
		redisCache().remove(session.getId());
	}

	/**
	 * 获取Redis缓存
	 * 
	 * @return Redis缓存
	 */
	private <K, V> RedisCache<K, V> redisCache() {
		return (RedisCache<K, V>) getCacheManager().getCache(getActiveSessionsCacheName());
	}

}
