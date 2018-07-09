# API 网关服务 Spring Cloud Zuul

在之前的模块实例中我们对 Spring Cloud Netflix 下的核心组件已经了解一大半。这些组件基本涵盖了微服务建构中最基本的几个核心设施，利用这些组件我们已经可以构建起一个简单的微服务架构系统。

如：通过使用 Spring Cloud Eureka 实现高可用的服务注册中心以及实现微服务的注册于发现；通过 Spring Cloud Ribbon 或 Feign 实现服务间负载均衡的接口调用；对于依赖的服务调用使用 Spring Cloud Hystrix 来进行包装，实现线程隔离并加入熔断机制，以避免在服务架构中因个别服务出现异常而引起级联故障蔓延。通过上诉思路，可以设计出类似下图的基础系统机构。

![base-system](images/base-system.png)

在该架构中，我们的服务集群包含内部服务 Service A 和 Service B，他们都会向 Eureka Service 集群进行注册与订阅服务，而 Open Service 是一个对外的 RESTFul Api 服务，它通过 F5、Nginx 等网络设备或工具软件实现对各个微服务的路由与负载均衡，并公开给外部的客户端调用。

首先可以肯定的是，这个架构是没有问题的。但是我们可以通过开发与运维的角度思考是否还有些不足。

**运维：**

当客户端应用单机某个功能的时候往往会发出一些对微服务获取资源的请求到后端，这些请求通过 F5，Nginx 等设施的路由和负载均衡分配后被转发到各个不同的服务实例上。为这些实例可能都会在不同的 IP 主机上，运维人员需要手动维护这些路由规则与服务实例列表，当有实例增减或 IP 地址变动时，也需要手动去同步修改这些信息。当系统规模不大的时候运维很容易去维护，但是，当系统庞大后，看似简单的实际上会让运维痛苦不堪。

**开发：**

现在，我们在来看下开发人员的角度。在大多数情况下，为了保证对外服务的安全性，我们在服务端的微服务接口中往往都会增加权限校验机制，比如校验登录状态等；同时为了防止客户端在发起请求时被串改等安全方面的考虑，还会有一些签名校验的机制存在。在这个时候，由于使用微服务架构的理念，我们不得不在各个微服务中增加这样的一套逻辑。随着规模不断扩大，这些校验逻辑变得越来越冗余，突然有一天，我们发现这样的逻辑存在问题或BUG在修复时有需要在每一个我服务应用中做修改，又会引起开发人员的抱怨与测试人员的负担。

为了解决这样的问题 **API 网关** 的概念应运而生。既然 API 网关这么重要那再微服务中是否也有这样的解决方案呢 一一 Spring Cloud Zuul。

----

**构建网关**

构建一个Spring Boot 应用，引入 `spring-cloud-starter-netflix-zuul` 包。

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

在启动类上加上 `@EnableZuulProxy` 注解开启 Zuul Api 网关服务功能。

```java
@EnableZuulProxy
@SpringCloudApplication
public class SpringcloudZuulApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringcloudZuulApplication.class).web(true).run(args);
	}
}
```

> **注意：** 这里使用的是 `@SpringCloudApplication` 注解，如果使用的是 `@SpringBootApplication` 注解还需要加上 `@EnableDiscoveryClient` 注解。另外一点这里并不是直接采用
```
public static void main(String[] args) {
	SpringApplication.run(SpringcloudZuulApplication.class, args);
}
```
>因为使用 `SpringApplicationBuilder`，可以在应用程序启动之前更改其中一些应用程序默认设置，即使这些设置中的大多数都具有合理的默认值。因此，只需几行代码，您就可以为不同目的（嵌入式部署，外部部署，测试等）构建具有不同设置的不同应用程序，同时您的实际底层业务逻辑保持不变。

在配置文件中配置 Zuul 应用的基础信息，如应用名、服务端口等信息：

```
spring.application.name=api-gateway
server.port=5555
```

**请求路由**

为了与 Eureka 整合，我们需要在 pom 文件中引入 eureka 依赖：

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

另外，我们需要在配置文件中指定服务注册中心与具体的路由规则，具体如下：

```
## eureka
eureka.instance.hostname=localhost
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:8888/eureka

## zuul 路由策略
zuul.routes.api-a.path=/api-a/**
zuul.routes.api-a.serviceId=hello-service

zuul.routes.api-b.path=/api-b/**
zuul.routes.api-b.serviceId=feign-consumer
```

- `zuul.routes.api-a.path`：路由规则，所有请求路径中包含 `/api-a/` 都访问 `zuul.routes.api-a.serviceId` 指定的服务。
- `zuul.routes.api-a.serviceId`：指定服务，每个服务都会有一个名称即在配置文件中通过 `spring.application.name` 指定的服务名称。

其中 `api-a` 与 `api-b` 都是自己定义的，比如如果要请求的服务是 hello-service 为了可读性可以写成：

```
zuul.routes.hello-service.path=/api-a/**
zuul.routes.hello-service.serviceId=hello-service
```

但是，每一对路由 path 与 serviceId 一定要对应。

启动服务注册中心（[springcloud-eureka](../springcloud-eureka)）、服务提供者（[springcloud-eureka-service](../springcloud-eureka-service)） 与 服务消费者（[springcloud-feign-consumer](../springcloud-feign-consumer) 和该应用。

>**注意：** 这里的服务提供者与服务消费者不要搞混，因为都是注册到服务注册中心所以这两个应用是否服务提供者与服务消费者，因为 springcloud-feign-consumer 功能调用的 springcloud-eureka-service ，所以对于 springcloud-eureka-service 来说 springcloud-feign-consumer 就是它的消费者。

如下界面：

![gateway-center.png](images/gateway-center.png)

在 hello-service 这个服务中，有 `/index` 这个接口。而我们在配置路由规则的时候是 `/api-a/**`，所以当我们访问 `127.0.0.1:5555/api-a/index` 的时候就会被 zuul 路由转发访问 `127.0.0.1:8080/index` 这个接口。为了确定是否这样我们先访问一下 `127.0.0.1:8080/index` 。会看到页面打印如下信息：

```
provider service, host：localhost，service_id：HELLO-SERVICE
```

现在再来通过网关路由访问：`127.0.0.1:5555/api-a/index` 会看到打印同样的信息，说明路由成功。

在 feign-consumer 这个服务中有个接口 `/feign-consumer2` 我们通过路由访问： `127.0.0.1:5555/api-b/feign-consumer2` 会看到打印如下信息：

```
provider service, host：localhost，service_id：HELLO-SERVICE HelloMinGRn name=MinGRn,age=18 HelloMinGRn,18
```

说明路由同样成功。

这样就是 API 网关的里面，**通过访问一个主机通过不同的路由规则而做到访问不同的主机。**

现在，解决了运维人员的问题。

不过，实现了请求路由功能之后，我们的微服务应用其功能的接口就通过统一的 API 网关入口被客户端访问到了，但是，每个客户端用户请求服务接口时往往都会做一定的限制，这就是开发人员的问题！

现在来看在路由中是如何做到这点的！我们只需要定义一个类 `AccessFilter` 用来继承 `ZuulFilter` 抽象类并实现它定义的四个抽象函数就能实现请求的拦截与过滤了。

```java
public class AccessFilter extends ZuulFilter {

	private static Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();

		LOGGER.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

		Object token = request.getParameter("token");
		if (token == null) {
			LOGGER.warn("token is empty");
			context.setSendZuulResponse(false);
			context.setResponseStatusCode(401);
		}
		return null;
	}
}
```

+ `filterType`：过滤器的类型，决定过滤器在请求的哪个周期中执行。
	- `pre`：请求被路由之前调用
	- `route`：请求被路由时调用
	- `post`：在 "route" 和 "error" 之后调用
	- `error`：处理请求时发生错误是调用
+ `filterOrder`：过滤器执行顺序,值越小优先级越高
+ `shouldFilter`：该过滤器是否被执行,可以通过此方法来指定过滤器的有效范围
+ `run`：具体的业务逻辑

这里我们在 `run()` 函数中增加了 request 请求中需要包含 `token` 参数信息。如果不包含则不对其进行路由并返回错误码 401。

现在来访问下 `127.0.0.1:5555/api-b/feign-consumer2` 会发现请求错误：

![err-page.png](images/err-page.png)

当我们在请求中增加 `token` 参数信息：`127.0.0.1:5555/api-b/feign-consumer2?token=1` 就能正常请求！

>**注意：** 在实际中不可能直接采用这种不安全的方式，这里只是测试使用。

关于 `shouldFilter()` 函数，我们可以直接看下 [官网Zuul](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html) 的一个 PRE 栗子：

```java
public class QueryParamPreFilter extends ZuulFilter {
	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER - 1; // run before PreDecoration
	}

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return !ctx.containsKey(FORWARD_TO_KEY) // a filter has already forwarded
				&& !ctx.containsKey(SERVICE_ID_KEY); // a filter has already determined serviceId
	}
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if (request.getParameter("sample") != null) {
		    // put the serviceId in `RequestContext`
    		ctx.put(SERVICE_ID_KEY, request.getParameter("foo"));
    	}
        return null;
    }
}
```

可以看到，在这个栗子中并不是直接在 `run()` 函数中做处理，只是在判断当 "sample" 参数部位 NULL 时，在  RequestContext 中增加 SERVICE_ID_KEY，在 `shouldFilter()` 函数中决定是否进行路由。

更多的路由讲解可以查看 [官网 Zuul](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html) 说明文档