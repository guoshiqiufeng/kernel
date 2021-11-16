# jwt-spring-boot-starter

#### 介绍
jwt token服务

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>jwt-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```

#### 使用说明

##### **配置**

```application.yml```或```application.properties```添加开启配置，开启自动装配

```yml
kernel:
  jwt:
    enabled: true
    secret: secret
    expire: 1800
    enable-multi-expire: true
    expire-map:
      web: 1800
      app: 0
```

配置文件可配置参数

| 参数                           | 名称                           | 备注                                     |
| ------------------------------ | ------------------------------ | ---------------------------------------- |
| kernel.jwt.enabled  | 是否启用自动装配               | 默认为false                              |
| kernel.jwt.secret | 加密密钥         | 必填 |
| kernel.jwt.expire | 过期时间。单位秒 |  |
| kernel.jwt.refresh | 缓存刷新时间（单位天） |  |
| kernel.jwt.enable-multi-expire | 是否启用多租户 |  |
| kernel.jwt.expire-map | 多租户 token有效时长集合，单位秒。格式 appId:expire |  |
##### 使用

spring boot 使用

```java
@RestController
public class TestController {

    @Autowired
    private JwtApi jwtApi;

    @GetMapping("/test/generate")
    public String generate()  {
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(1L, "934999", false);
        return jwtApi.generateToken(defaultJwtPayload);
    }

    @GetMapping("/test/get")
    @ResponseBody
    public DefaultJwtPayload getDefaultPayload(@RequestParam("token") String token)  {
        return jwtApi.getDefaultPayload(token);
    }
}
```

