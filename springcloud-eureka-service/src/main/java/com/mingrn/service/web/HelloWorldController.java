package com.mingrn.service.web;

import com.mingrn.service.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping
public class HelloWorldController {



	@GetMapping("/hello1")
	public String hello1(@RequestParam String name) {
		return "Hello" + name;
	}

	@GetMapping("/hello2")
	public User hello2(@RequestHeader String name, @RequestHeader Integer age) {
		return new User(name, age);
	}

	@PostMapping("/hello3")
	public String hello3(@RequestBody User user) {
		StringBuffer sb = new StringBuffer("Hello")
				.append(user.getName())
				.append(",")
				.append(user.getAge());
		return sb.toString();
	}
}