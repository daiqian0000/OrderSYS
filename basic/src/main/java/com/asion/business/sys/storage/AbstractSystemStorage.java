package com.asion.business.sys.storage;

import java.util.Map;

/**
 * 系统存储接口抽象实现类
 * 
 * @author chenboyang
 * 
 */
@SuppressWarnings("unchecked")
public abstract class AbstractSystemStorage implements SystemStorage {

	/**
	 * 获取系统存储容器
	 * @return 系统存储
	 */
	protected abstract <K, V> Map<K, V> getStorage();

	@Override
	public <K> boolean has(K key) {
		return getStorage().containsKey(key);
	}

	@Override
	public <K, V> V get(K key) {
		return (V) getStorage().get(key);
	}

	@Override
	public <K, V> V set(K key, V value) {
		return (V) getStorage().put(key, value);
	}

	@Override
	public <K, V> void setMap(Map<? extends K, ? extends V> map) {
		getStorage().putAll(map);
	}

	@Override
	public <K, V> V replace(K key, V value) {
		return (V) getStorage().replace(key, value);
	}

	@Override
	public <K, V> V remove(K key) {
		return (V) getStorage().remove(key);
	}

}
