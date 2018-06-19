package com.mingrn.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 加上注解 @EnableDiscoveryClient 让该应用注册为 Eureka 客户端应用
 *
 * @author MinGRn <br > 2018/6/19 18:39
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudRibbonConsumerApplication {


	/**
	 * 创建 RestTemplate 的 Spring Bean 实例,并通过 @LoadBalanced 注解开启客户端负载均衡
	 */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudRibbonConsumerApplication.class, args);
	}
}
