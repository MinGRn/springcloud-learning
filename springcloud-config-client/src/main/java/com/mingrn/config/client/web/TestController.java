package com.mingrn.config.client.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注解 @RefreshScope 用于微服务客户端配置自动更新,解决资源文件与javaConfig的同步问题
 *
 * @author MinGRn <br > 2018/7/18 19:37
 */
@RefreshScope
@RestController
@RequestMapping
public class TestController {

	@Value("${from}")
	private String from;

	@RequestMapping("/from")
	public String from() {
		return this.from;
	}
}
