##认证和鉴权
> 主要使用spring security + jwt 进行认证和鉴权
### 安装
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>auth-spring-boot-starter</artifactId>
    <version>1.6.8</version>
</dependency>
```
### 配置
| 参数                                 | 名称                                                                      | 备注               |
|------------------------------------|-------------------------------------------------------------------------|------------------|
| kernel.auth.token-prefix           | jwt 生成 token 前缀                                                         | 默认为Bearer        |
| kernel.auth.token-param-name       | 请求中token 参数名称                                                           | 默认为Authorization |
| kernel.auth.token-header-name      | 请求头中token 参数名称                                                          | 默认为Authorization |
| kernel.auth.token-cookie-name      | cookie中token 参数名称                                                       | 默认为Authorization |
| kernel.auth.jwt-app-id-header-name | 请求头中 jwt app id 参数名称 （用于获取jwt模块所使用加密的密钥对应的参数名，若 jwt模块开启多租户则必填，若 未开启可忽略） | 默认为  appId       |
| kernel.auth.security-config-enabled             | 是否开启spring security config                                              | 默认为 true         |
| kernel.auth.security-config-allow-patterns            | spring security 放行路径                                                    |          |
### security
can set `kernel.auth.security-config-enabled: false` to skip this config
can set `kernel.auth.security-config-allow-patterns: -/url` to allow patterns

#### security-config-allow-patterns example
```yaml
kernel:
  auth:
    security-config-allow-patterns:
      - "/core/login"
      - "/message/*"
      - "/core/test/*"
```