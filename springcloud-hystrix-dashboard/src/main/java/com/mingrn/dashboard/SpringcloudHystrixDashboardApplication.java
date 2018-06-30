package com.mingrn.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 加 @EnableHystrixDashboard 注解开启 Hystrix Dashboard(Hystrix 仪表盘)功能
 *
 * @author MinGRn <br > 2018/6/30 15:12
 */
@EnableHystrixDashboard
@SpringBootApplication
public class SpringcloudHystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudHystrixDashboardApplication.class, args);
	}
}
