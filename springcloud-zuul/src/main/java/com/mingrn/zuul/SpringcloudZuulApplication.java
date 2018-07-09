package com.mingrn.zuul;

import com.mingrn.zuul.web.AccessFilter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;


@EnableZuulProxy
@SpringCloudApplication
public class SpringcloudZuulApplication {

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}

	/**
	 * 将路由规则进行模式转换,比如服务实例为 userService-v1 则被映射的路径为 v1/userService
	 */
	/*@Bean
	public PatternServiceRouteMapper patternServiceRouteMapper() {
		return new PatternServiceRouteMapper("(?<name>.*)-(?<version>v.*$)", "${version}/${name}");
	}*/

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringcloudZuulApplication.class).web(true).run(args);
	}
}
