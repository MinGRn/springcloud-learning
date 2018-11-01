# SpringCloud-Learning

本仓库主要用于学习 Spring Cloud ，在阅读本工程之前请先保证对 Spring Boot 有一定的了解，这样够益于阅读和理解！

本仓库主要参考至 [SpringCloud中文官网](https://springcloud.cc/)、[Spring Cloud 官网](http://projects.spring.io/spring-cloud/) 与 [翟永超](http://blog.didispace.com/) 的 《Spring Cloud 微服务实战》 书籍。

本仓库在提交后在 GitHub 中的目录会有些错乱，下面将阅读顺序进行一个罗列：

+ 单节点服务注册中心
  - [springcloud-eureka](springcloud-eureka)
  - [springcloud-eureka-service](springcloud-eureka-service)

+ 高可用服务注册中心(多节点)
  - [springcloud-eureka-peer](springcloud-eureka-peer)
  - [springcloud-eureka-peer-service](springcloud-eureka-peer-service)

+ 服务发现与消费者
  - [springcloud-ribbon-consumer](springcloud-ribbon-consumer)

+ Hystrix 容错保护
  - [springcloud-ribbon-hystrix-consumer](springcloud-ribbon-hystrix-consumer)

+ Hystrix Dashboard仪表盘
  - [springcloud-hystrix-dashboard](springcloud-hystrix-dashboard)

+ Turbine 集群监控
  - [springcloud-turbine](springcloud-turbine)

+ Feign 负载均衡与容错保护
  - [springcloud-feign-consumer](springcloud-feign-consumer)

+ Zuul API 网关、动态更新路由与动态过滤器
  - [springcloud-zuul](springcloud-zuul)
  - [springcloud-zuul-api-gateway-dynamic](springcloud-zuul-api-gateway-dynamic)
  - [springcloud-zuul-filter-dynamic](springcloud-zuul-filter-dynamic)
  
+ Config 分布式配置中心服务端与客户端
  - [springcloud-config-server](springcloud-config-server)
  - [springcloud-config-client](springcloud-config-client)
  
+ Config 分布式配置中心服务端与客户端高可用
  - [springcloud-config-server-eureka](springcloud-config-server-eureka)
  - [springcloud-config-client-eureka](springcloud-config-client-eureka)
  
* Bus 消息中间件 rabbitMQ、ActiveMQ
  - [springcloud-rabbitmq](springcloud-rabbitmq)
  - [springcloud-config-server-eureka-bus](springcloud-config-server-eureka-bus)
  - [springcloud-config-client-eureka-bus](springcloud-config-client-eureka-bus)

---
  未完待续~
