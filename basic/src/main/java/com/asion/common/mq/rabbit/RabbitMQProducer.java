package com.asion.common.mq.rabbit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.asion.common.mq.rabbit.annottation.MQProducer;
import com.asion.common.util.JsonUtil;
import com.asion.common.util.SpringContextUtil;

/**
 * RabbitMQ消息生产接口
 * 
 * @author chenboyang
 *
 */
public interface RabbitMQProducer {

	/**
	 * 获取MQ发送模板
	 * 
	 * @return 模板
	 */
	default RabbitTemplate template() {
		if (StringUtils.isNotBlank(channelInfo().rabbitTemplate())) {
			return SpringContextUtil.getBean(channelInfo().rabbitTemplate());
		} else {
			return SpringContextUtil.getBean(RabbitTemplate.class);
		}
	}

	/**
	 * 获取通道信息
	 * 
	 * @return 信息
	 */
	default MQProducer channelInfo() {
		return getClass().getAnnotation(MQProducer.class);
	}

	/**
	 * 发送消息
	 * 
	 * @param obj
	 *            消息体
	 */
	default void send(Object obj) {
		template().convertAndSend(channelInfo().exchange(), channelInfo().routingKey(),
				obj instanceof String ? obj : JsonUtil.toJson(obj));
	}

	/**
	 * 发送消息
	 * 
	 * @param key
	 *            业务key值
	 * @param obj
	 *            消息体
	 */
	default void send(String key, Object obj) {
		if (StringUtils.isNotBlank(channelInfo().routingKey())) {
			if (channelInfo().routingKey().contains("#")) {
				key = channelInfo().routingKey().replace("#", key);
			} else if (channelInfo().routingKey().contains("*")) {
				key = channelInfo().routingKey().replace("*", key);
			}
		}
		template().convertAndSend(channelInfo().exchange(), key,
				obj instanceof String ? obj : JsonUtil.toJson(obj));
	}

}
