package com.mingrn.consumer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping
public class ConsumerController {

	@Autowired
	RestTemplate restTemplate;

	/**
	 * 注意: 这里的访问地址名不是ip,而是服务应用名称,在服务治理框架中,这是一个非常主要的特性
	 */
	@GetMapping("/ribbon-consumer")
	public String helloConsumer() {
		return restTemplate.getForEntity("http://hello-service/index", String.class).getBody();
	}
}
