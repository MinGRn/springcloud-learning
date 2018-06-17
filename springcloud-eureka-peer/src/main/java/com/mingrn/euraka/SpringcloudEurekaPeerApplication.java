package com.mingrn.euraka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringcloudEurekaPeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudEurekaPeerApplication.class, args);
	}
}
