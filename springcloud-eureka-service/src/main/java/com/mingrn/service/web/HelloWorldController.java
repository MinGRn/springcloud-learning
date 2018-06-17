package com.mingrn.service.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@RestController
@RequestMapping
public class HelloWorldController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandlerExceptionResolver.class);

	/**
	 * 服务发现客户端
	 */
	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/index")
	public String index() {
		ServiceInstance serviceInstance = serviceInstance();
		LOGGER.info("provider service, host：{}，service_id：{}", serviceInstance.getHost(), serviceInstance.getServiceId());
		return "provider service, host："+serviceInstance.getHost()+"，service_id："+serviceInstance.getServiceId();
	}


	/**
	 * 获取当前服务的服务实例
	 *
	 * @author MinGRn <br > 2018/6/16 15:43
	 */
	private ServiceInstance serviceInstance() {
		List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("hello-service");
		return (serviceInstanceList != null && serviceInstanceList.size() > 0) ? serviceInstanceList.get(0) : null;
	}
}