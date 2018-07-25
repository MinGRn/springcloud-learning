package com.mingrn.bus.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 *
 * @author MinGRn <br > 2018/7/24 18:17
 */
@Component
@RabbitListener(queues = "hello")
public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@RabbitHandler
	public void process(String context) {
		LOGGER.info("Receiver: {}", context);
	}
}
