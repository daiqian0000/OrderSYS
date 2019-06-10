package com.asion.common.mq.rabbit.exchange;

import com.asion.common.mq.rabbit.RabbitMQExchangeNames;
import com.asion.common.mq.rabbit.RabbitMQProducer;
import com.asion.common.mq.rabbit.annottation.MQProducer;

/**
 * 匹配交换机
 * 
 * @author chenboyang
 *
 */
@MQProducer(exchange = RabbitMQExchangeNames.MATCH)
public class MatchExchange implements RabbitMQProducer {

}
