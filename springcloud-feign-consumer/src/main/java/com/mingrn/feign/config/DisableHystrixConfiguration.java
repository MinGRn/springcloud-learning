package com.mingrn.feign.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 关闭 Hystrix 支持配置类
 *
 * @author MinGRn <br > 2018/7/8 20:53
 */
@Configuration
public class DisableHystrixConfiguration {

	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {
		return new Feign.Builder();
	}
}
