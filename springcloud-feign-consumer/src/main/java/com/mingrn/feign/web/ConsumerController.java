package com.mingrn.feign.web;

import com.mingrn.feign.interfase.HelloService;
import com.mingrn.feign.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class ConsumerController {

	@Resource
	private HelloService helloService;

	@GetMapping("serviceBlock")
	public String serviceBlock() {
		return helloService.serviceBlock();
	}

	@GetMapping("feign-consumer")
	public String helloConsumer() {
		return helloService.index();
	}

	@GetMapping("feign-consumer2")
	public String helloConsumer2() {
		StringBuilder sb = new StringBuilder();
		sb.append(helloService.index()).append("\n")
				.append(helloService.hello1("MinGRn")).append("\n")
				.append(helloService.hello2("MinGRn", 18)).append("\n")
				.append(helloService.hello3(new User("MinGRn", 18))).append("\n");
		return sb.toString();
	}
}
