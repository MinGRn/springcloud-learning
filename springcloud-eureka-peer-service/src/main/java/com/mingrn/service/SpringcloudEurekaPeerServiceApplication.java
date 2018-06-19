package com.mingrn.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudEurekaPeerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudEurekaPeerServiceApplication.class, args);
	}
}
