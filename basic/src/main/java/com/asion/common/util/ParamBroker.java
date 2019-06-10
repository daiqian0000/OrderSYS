package com.asion.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Map容器代理
 * 
 * @author chenboyang
 *
 * @param <K>
 *            键类型
 * @param <V>
 *            值类型
 */
public class ParamBroker<K, V> {

	/**
	 * Map容器
	 */
	private Map<K, V> param = new HashMap<K, V>();

	public void setParam(Map<K, V> param) {
		this.param = param;
	}

	public Map<K, V> getParam() {
		return param;
	}

}
