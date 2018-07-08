package com.mingrn.zuul;

import com.mingrn.zuul.web.AccessFilter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@EnableZuulProxy
@SpringCloudApplication
public class SpringcloudZuulApplication {

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringcloudZuulApplication.class).web(true).run(args);
	}
}
