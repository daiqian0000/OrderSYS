package com.asion.common.mq.rabbit.exchange;

import com.asion.common.mq.rabbit.RabbitMQExchangeNames;
import com.asion.common.mq.rabbit.RabbitMQProducer;
import com.asion.common.mq.rabbit.annottation.MQProducer;

/**
 * MQ主题交换机
 * 
 * @author chenboyang
 *
 */
@MQProducer(exchange = RabbitMQExchangeNames.TOPIC)
public class TopicExchange implements RabbitMQProducer {

}
