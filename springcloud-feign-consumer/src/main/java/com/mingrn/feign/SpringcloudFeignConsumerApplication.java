package com.mingrn.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 注解 @EnableFeignClients 开启Spring Cloud Feign的支持功能
 *
 * @author MinGRn <br > 2018/7/3 20:27
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudFeignConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudFeignConsumerApplication.class, args);
	}
}
