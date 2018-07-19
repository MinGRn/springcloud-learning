package com.mingrn.config.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringcloudConfigClientApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringcloudConfigClientApplication.class).web(WebApplicationType.SERVLET).run(args);
	}
}
