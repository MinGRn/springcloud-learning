package com.mingrn.bus.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbit config
 *
 * @author MinGRn <br > 2018/7/24 18:30
 */
@Configuration
public class RabbitConfig {

	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}
}
