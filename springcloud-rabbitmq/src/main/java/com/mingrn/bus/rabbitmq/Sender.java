package com.mingrn.bus.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 消息生成者
 *
 * @author MinGRn <br > 2018/7/24 18:16
 */
@Component
public class Sender {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	@Resource
	private AmqpTemplate amqpTemplate;

	public void send() {
		String context = "Hello " + new Date();
		LOGGER.info("<<== The Producer Generates a Message: {}", context);
		amqpTemplate.convertAndSend("hello", context);
	}
}
