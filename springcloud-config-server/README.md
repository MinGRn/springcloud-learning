# Spring Cloud Config Server

在分布式系统中，每一个功能模块都能拆分成一个独立的服务，一次请求的完成，可能会调用很多个服务协
调来完成，为了方便服务配置文件统一管理，更易于部署、维护，所以就需要分布式配置中心组件 一一 spring cloud config。

spring cloud config 是用来为分布式系统中的基础设施和微服务应用提供集中化的外部配置支持，它分为服务端与客户端。

其中服务端也成为分布式配置中心，它是个独立的微服务应用，用来连接配置仓库并为客户端提供获取配置信息、
加密&解密信息等访问接口；而客户端则是微服务架构中的各个微服务应用或基础设施，他们通过指定的配置中心
来管理应用资源与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息。


Spring Cloud Config 支持配置文件放在在配置服务的内存中，也支持放在远程Git仓库里。引入
spring cloud config后，我们的外部配置文件就可以集中放置在一个git仓库里，再新建一个 
config server，用来管理所有的配置文件，维护的时候需要更改配置时，只需要在本地更改后，推送
到远程仓库，所有的服务实例都可以通过config server来获取配置文件，这时每个服务实例就相当于
配置服务的客户端config client,为了保证系统的稳定，配置服务端config server可以进行集群
部署，即使某一个实例，因为某种原因不能提供服务，也还有其他的实例保证服务的继续进行。

如下架构图：

![architecture.png](images/architecture.png)


**本工程只介绍服务端**

> 由于 Spring Cloud Config 实现的配置中心默认采用 Git 来存储配置信息，所以 Config 构建的
配置服务器天然就支持对微服务应用配置信息的版本管理，并且可以通过 Git 客户端工具来方便的管理和访问配置内。

# 创建 Spring Could Config 服务端

在 Idea 工具下，创建工程是直接勾选 Config Server 即可：

![springcloud-config-server.png](images/springcloud-config-server.png)

或者在 pom 文件中直接引入依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

工程创建完成后在启动类上加上 `@EnableConfigServer` 直接开启 Spring Cloud Config **服务端** 功能。

```java
@EnableConfigServer
@SpringBootApplication
public class SpringcloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudConfigServerApplication.class, args);
	}
}
```

接着在配置文件中增加如下配置信息：

```properties
server.port=7001
spring.application.name=config-server
spring.cloud.config.server.git.uri=https://gitee.com/mingrn/springcloud-config-server
spring.cloud.config.server.git.search-paths=spring-cloud-in-action/config-repo
spring.cloud.config.server.git.username=username
spring.cloud.config.server.git.password=******
```

> - `spring.cloud.config.server.git.uri`：仓库地址，这里使用的是gitee
> - `spring.cloud.config.server.git.search-paths`：git仓库下配置文件所在目录,采用相对路径
> - `spring.cloud.config.server.git.username`：git仓库登录账号
> - `spring.cloud.config.server.git.password`：git仓库登录密码

在配置文件中 `spring.cloud.config.server.git.search-paths` 所指的相对路径是基于仓库位置，比如这里的仓库名称为 springcloud-config-server，
而配置文件所处的位置在 `springcloud-config-server/spring-cloud-in-action/config-repo` 目录下，那么相对路径为 `/spring-cloud-in-action/config-repo`,支持多个相对路径,多个相对路径之间需要中逗号做分隔！

如图：

![gitee-repository.png](images/gitee-repository.png)

通过 git 工具创建如下文件（或者直接在网页创建）并推送至远程仓库：

- `config.properties`
- `config-dev.properties`
- `config-prod.properties`
- `config-test.properties`

在三个配置文件中分别设置不同的值：

- `fromf = git-default-1.0`
- `fromf = git-dev-1.0`
- `fromf = git-prod-1.0`
- `fromf = git-test-1.0`

成功推送至远程仓库后就能看到上图所示页面信息，接着在创建一个 `test` 分支

![checkout-test.png](images/checkout-test.png)

并将 test 分支上的三个配置文件内容分别修改为如下内容：

- `from = git-default-test-1.0`
- `from = git-dev-test-1.0`
- `from = git-prod-test-1.0`
- `from = git-test-test-1.0`

提交并推送至远程，如下：

![remote.png](images/remote.png)

为了测试版本控制，笔者在仓库中创建了两个分支分别是 master 和 test，完成上面的工作后启动服务，并
在浏览器中或通过 POSTMan 工具访问我们配置的内容。访问配置信息的 URL 与配置文件的映射关系如下：

- `/{application}/{profile}[/label]`
- `/{application}-{profile}.[yml|properties]`
- `/{label}/{application}-{profile}.[yml|properties]`

其中，`{application}` 指的是配置文件前缀，比如笔者创建的配置文件名称分别是 `config.properties`、`config-dev.properties`,
这里的 `{application}` 指的就是 `config`。
`{profile}` 则指的是后缀 `dev`。`/label` 这指的是分支，比如 master、test 分支。

> `[]` 符号同正则表达式

现在笔者样访问 `test` 分支, `config` 应用的 `prod` 环境，就可以访问URL：`http://127.0.0.1:7001/config/prod/test`,就会返回如下信息：

```json
{
    "name": "config",
    "profiles": [
        "prod"
    ],
    "label": "test",
    "version": "ed4c5b1c976664a5e25b17f9d7ceb5da38813f5a",
    "state": null,
    "propertySources": [
        {
            "name": "https://gitee.com/mingrn/springcloud-config-server/spring-cloud-in-action/config-repo/config-prod.properties",
            "source": {
                "from": "git-prod-test-1.0"
            }
        },
        {
            "name": "https://gitee.com/mingrn/springcloud-config-server/spring-cloud-in-action/config-repo/config.properties",
            "source": {
                "from": "git-default-test-1.0"
            }
        }
    ]
}
```

可以看到其中返回的信息包含了应用名称：`config`,分支：`test`,版本号等信息。

**注意：** URL 映射路径 label 默认值为 master,所以当将之前的访问 URL 最后的 `/test` 去除访问的就会是 `/master`。

如下：`http://127.0.0.1:7001/config/prod`

```json
{
    "name": "config",
    "profiles": [
        "prod"
    ],
    "label": null,
    "version": "94c2d548363b05668694f093f1313d814ca86491",
    "state": null,
    "propertySources": [
        {
            "name": "https://gitee.com/mingrn/springcloud-config-server/spring-cloud-in-action/config-repo/config-prod.properties",
            "source": {
                "from": "git-prod-1.0"
            }
        },
        {
            "name": "https://gitee.com/mingrn/springcloud-config-server/spring-cloud-in-action/config-repo/config.properties",
            "source": {
                "from": "git-default-1.0"
            }
        }
    ]
}
```

另外一点，每次访问时，配置服务器都会 Git 中获取配置信息后，都会存储一份在 config-server 的文件系统中，实质上 config-server 是通过 `git clone` 命令将配置内容克隆一份在本地存储，然后回去这些内容并
返回给微服务应用进行加载，日志如下：

![download-config.png](images/download-config.png)

现在访问这个目录就会看到本地克隆的文件

![local-clone.png](images/local-clone.png)

通过 Git 在本地仓库暂存，可以有效访问当 Git 仓库出现故障而已引起无法加载配置信息的情况。现在断开网络在重新请求一次，会看到控制台输出日下信息：

![break-net.png](images/break-net.png)

虽然提示无法从远程获取该分支内容的报错信息：

```log
Could not fetch remote for master remote:
```

但是依然会为该请求返回配置信息，这些内容源于之前访问存在于 config-server 本地文件的拷贝。

---

在之前的配置文件中，笔者是直接使用的远程仓库地址，其实在测试或者开发中也可以直接使用本地仓库，配置如下：

```properties
server.port=7001
spring.application.name=config-server
spring.cloud.config.server.git.uri = file:///${user.home}/AppData/Local/Temp/config-repo-5452474400930930259
spring.cloud.config.server.git.search-paths=spring-cloud-in-action/config-repo
```

这里使用的是之前远程的一份拷贝，启动程序会看到能够正常启动，并且控制台会打印提示信息：

![local-clone-tip.png](images/local-clone-tip.png)

访问也是能正常访问的,但是需要注意的是这仅仅只能用于开发和测试无法再线上使用：

```json
{
    "name": "config",
    "profiles": [
        "prod"
    ],
    "label": null,
    "version": "94c2d548363b05668694f093f1313d814ca86491",
    "state": null,
    "propertySources": [
        {
            "name": "file:///C:\\Users\\MinGR/AppData/Local/Temp/config-repo-5452474400930930259/spring-cloud-in-action/config-repo/config-prod.properties",
            "source": {
                "from": "git-prod-1.0"
            }
        },
        {
            "name": "file:///C:\\Users\\MinGR/AppData/Local/Temp/config-repo-5452474400930930259/spring-cloud-in-action/config-repo/config.properties",
            "source": {
                "from": "git-default-1.0"
            }
        }
    ]
}
```

---

**本地仓库配置与采坑**

在配置本地仓库时，使用的参数如下：

```properties
spring.cloud.config.server.git.uri = file:///${user.home}/AppData/Local/Temp/config-repo-5452474400930930259

```

在 windows 下 `file:///` 这里必须跟三个斜杠，Linux则双斜杠即可。另外，`${user.home}` 环境变量指的是当前用户环境目录，如果你不知道在哪，那就直接在 CMD 终端命令
中输入：`start.` 指令就会直接弹出当前用户目录，另外路径什么的都设置好以后并没有完事，你需要确定你所指定的本地仓库是否有指向远程仓库的信息，即 `.git` 目录。你使用远程
仓库时，每次克隆本地都会有 .git 目录。这里要注意下！