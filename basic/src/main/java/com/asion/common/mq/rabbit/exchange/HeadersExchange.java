package com.asion.common.mq.rabbit.exchange;

import com.asion.common.mq.rabbit.RabbitMQExchangeNames;
import com.asion.common.mq.rabbit.RabbitMQProducer;
import com.asion.common.mq.rabbit.annottation.MQProducer;

/**
 * 头部信息交换机
 * 
 * @author chenboyang
 *
 */
@MQProducer(exchange = RabbitMQExchangeNames.HEADERS)
public class HeadersExchange implements RabbitMQProducer {

}
