package com.mingrn.feign.interfase;

import com.mingrn.feign.config.DisableHystrixConfiguration;
import com.mingrn.feign.model.User;
import com.mingrn.feign.service.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "hello-service", fallback = HelloServiceFallback.class,configuration = DisableHystrixConfiguration.class)
public interface HelloService {

	@GetMapping("/serviceBlock")
	String serviceBlock();

	@GetMapping("/index")
	String index();

	@GetMapping("/hello1")
	String hello1(@RequestParam("name") String name);

	@GetMapping("/hello2")
	User hello2(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

	@PostMapping("/hello3")
	String hello3(@RequestBody User user);
}
