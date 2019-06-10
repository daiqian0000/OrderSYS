package com.asion.business.web.session;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.transaction.annotation.Transactional;

import com.asion.business.sys.service.SysService;
import com.asion.business.web.service.ApplyService;
import com.asion.common.redis.annotation.RedisStorage;
import com.asion.common.redis.storage.value.CatalogRedisValueStorage;

/**
 * Session信息Redis存储
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@SuppressWarnings("unchecked")
@RedisStorage(key = "session:")
public class SessionRedisStorage implements SessionStorage, CatalogRedisValueStorage<String, Map<String, Object>> {

	/**
	 * 系统应用服务接口
	 */
	@Autowired
	private ApplyService applyService;
	
	/**
	 * 系统服务接口
	 */
	@Autowired
	private SysService sysService;
	
	/**
	 * 获取SessionID信息
	 * 
	 * @return SessionID
	 */
	private String sessionKey() {
		return applyService.session().getId();
	}

	/**
	 * 获取Session属性信息
	 * 
	 * @return 属性
	 */
	@Transactional
	private Map<String, Object> attributes() {
		Map<String, Object> attributes = null;
		String id = sessionKey();
		if (has(id)) {
			attributes = get(id);
		} else {
			attributes = new HashMap<String, Object>();
			long createTime = applyService.session().getCreationTime();
			long nowTime = (new Date()).getTime();
			long difference = (nowTime - createTime) / 1000;
			long timeout = sysService.sysProperties().getTimeout();
			timeout -= difference;
			if (timeout > 0) {
				set(id, attributes, timeout, TimeUnit.MINUTES);
			}
		}
		return attributes;
	}

	/**
	 * 将Session属性信息更新到Redis存储上
	 * 
	 * @param attributes
	 *            Session属性信息
	 */
	@Transactional
	private void updateAttributes(Map<String, Object> attributes) {
		ops().setIfAbsent(sessionKey(), attributes);
	}

	@Transactional(readOnly = true)
	public boolean hasSession(String name) {
		return attributes().containsKey(name);
	}

	@Transactional
	public void setSession(String name, Object value) {
		Map<String, Object> map = attributes();
		map.put(name, value);
		updateAttributes(map);
	}

	@Transactional(readOnly = true)
	public <V> V getSession(String name) {
		return (V) attributes().get(name);
	}

	@Transactional
	public void removeSession(String name) {
		Map<String, Object> map = attributes();
		map.remove(name);
		updateAttributes(map);
	}

}
