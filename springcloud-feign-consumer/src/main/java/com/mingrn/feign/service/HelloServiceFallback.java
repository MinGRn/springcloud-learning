package com.mingrn.feign.service;

import com.mingrn.feign.interfase.HelloService;
import com.mingrn.feign.model.User;
import org.springframework.stereotype.Component;

/**
 * 服务降级
 *
 * @author MinGRn <br > 2018/7/8 20:32
 */
@Component
public class HelloServiceFallback implements HelloService {
	@Override
	public String serviceBlock() {
		return "error";
	}

	@Override
	public String index() {
		return "error";
	}

	@Override
	public String hello1(String name) {
		return "error";
	}

	@Override
	public User hello2(String name, Integer age) {
		return new User("未知", 0);
	}

	@Override
	public String hello3(User user) {
		return "error";
	}
}
