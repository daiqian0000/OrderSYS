package com.asion.common.mq.rabbit.exchange;

import com.asion.common.mq.rabbit.RabbitMQExchangeNames;
import com.asion.common.mq.rabbit.RabbitMQProducer;
import com.asion.common.mq.rabbit.annottation.MQProducer;

/**
 * 跟踪交换机
 * 
 * @author chenboyang
 *
 */
@MQProducer(exchange = RabbitMQExchangeNames.TRACE)
public class TraceExchange implements RabbitMQProducer {

}
