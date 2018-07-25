package com.mingrn.config.client.eureka;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudConfigClientEurekaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringcloudConfigClientEurekaApplication.class).web(WebApplicationType.SERVLET).run(args);
	}
}
