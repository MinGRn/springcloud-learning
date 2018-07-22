package com.mingrn.zuul.dynamic;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringCloudApplication
public class SpringcloudZuulDynamicApplication {

	@Bean
	@Primary
	@RefreshScope
	public ZuulProperties zuulProperties() {
		return new ZuulProperties();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringcloudZuulDynamicApplication.class).web(WebApplicationType.SERVLET).run(args);
	}
}
