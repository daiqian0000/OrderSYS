package com.asion.business.web.session;

/**
 * Session信息存储接口
 * 
 * @author chenboyang
 *
 */
public interface SessionStorage {

	/**
	 * 默认Session过期时间
	 */
	long TIMEOUT = 30L;

	/**
	 * 对象是否存在
	 * 
	 * @param name
	 *            名称
	 * @return 值
	 */
	boolean hasSession(String name);

	/**
	 * 将对象保存到Session信息中
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 */
	void setSession(String name, Object value);

	/**
	 * 从Session信息中获取对象
	 * 
	 * @param name
	 *            名称
	 * @return 值
	 */
	<V> V getSession(String name);

	/**
	 * 从Session信息中移除对象
	 * 
	 * @param name
	 *            名称
	 */
	void removeSession(String name);

}
