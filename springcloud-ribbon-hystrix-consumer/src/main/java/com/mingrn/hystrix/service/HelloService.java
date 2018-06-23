package com.mingrn.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;

@Service
public class HelloService {

	@Resource
	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

	/**
	 * 注解 @HystrixCommand(fallbackMethod) 指定回调方法
	 */
	@HystrixCommand(fallbackMethod = "helloFallBack")
	public String ribbonConsumer() {
		return restTemplate.getForEntity("http://hello-service/index", String.class).getBody();
	}

	/**
	 * 注解 @HystrixCommand(fallbackMethod) 指定回调方法
	 */
	@HystrixCommand(fallbackMethod = "helloFallBack")
	public String serviceBlock() {
		long start = System.currentTimeMillis();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://hello-service/serviceBlock", String.class);
		long end = System.currentTimeMillis();
		LOGGER.info("Speed time : " + (end - start));
		return responseEntity.getBody();

	}

	public String helloFallBack() {
		return "error";
	}
}
