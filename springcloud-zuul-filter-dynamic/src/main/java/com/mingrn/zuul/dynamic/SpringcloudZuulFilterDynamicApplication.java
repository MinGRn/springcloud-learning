package com.mingrn.zuul.dynamic;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class SpringcloudZuulFilterDynamicApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringcloudZuulFilterDynamicApplication.class).web(WebApplicationType.SERVLET).run(args);
	}
}
