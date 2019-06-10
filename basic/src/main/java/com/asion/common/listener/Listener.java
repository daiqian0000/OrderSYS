package com.asion.common.listener;

/**
 * 监听器接口
 * 
 * @author chenboyang
 *
 * @param <T>
 *            结果类型
 */
@FunctionalInterface
public interface Listener<T> {

	/**
	 * 回调函数
	 * 
	 * @param t
	 *            回调结果
	 */
	void callback(T t);

}
