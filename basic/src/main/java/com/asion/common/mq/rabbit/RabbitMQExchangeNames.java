package com.asion.common.mq.rabbit;

/**
 * RabbitMQ常用交换机名称类
 * 
 * @author chenboyang
 *
 */
public abstract class RabbitMQExchangeNames {

	/**
	 * 定向
	 */
	public static final String DIRECT = "amq.direct";

	/**
	 * 广播
	 */
	public static final String FANOUT = "amq.fanout";

	/**
	 * 头部信息
	 */
	public static final String HEADERS = "amq.headers";

	/**
	 * 匹配
	 */
	public static final String MATCH = "amq.match";

	/**
	 * 追踪
	 */
	public static final String TRACE = "amq.rabbitmq.trace";

	/**
	 * 主题
	 */
	public static final String TOPIC = "amq.topic";

}
