package com.asion.common.message;

/**
 * 消息处理程序接口
 * 
 * @author chenboyang
 *
 * @param <T>
 *            消息数据类型
 */
public interface Handler<T> {

	/**
	 * 进行消息数据处理
	 * 
	 * @param message
	 *            消息
	 */
	void handle(T message);

}
