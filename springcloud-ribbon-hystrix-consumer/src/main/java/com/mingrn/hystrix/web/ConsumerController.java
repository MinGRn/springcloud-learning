package com.mingrn.hystrix.web;

import com.mingrn.hystrix.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class ConsumerController {

	@Resource
	private HelloService helloService;

	@GetMapping("ribbon-consumer")
	public String ribbonConsumer() {
		return helloService.ribbonConsumer();
	}

	@GetMapping("serviceBlock")
	public String serviceBlock() {
		return helloService.serviceBlock();
	}
}
