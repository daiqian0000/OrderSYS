package com.asion.common.mq.rabbit.exchange;

import com.asion.common.mq.rabbit.RabbitMQExchangeNames;
import com.asion.common.mq.rabbit.RabbitMQProducer;
import com.asion.common.mq.rabbit.annottation.MQProducer;

/**
 * 定向交换机
 * 
 * @author chenboyang
 *
 */
@MQProducer(exchange = RabbitMQExchangeNames.DIRECT)
public class DirectExchange implements RabbitMQProducer {

}
