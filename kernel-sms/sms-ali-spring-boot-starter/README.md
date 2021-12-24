# sms-ali-spring-boot-starter

#### 介绍
阿里云 短信服务

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>sms-ali-spring-boot-starter</artifactId>
    <version>1.5.6</version>
</dependency>
```

#### 使用说明

##### **配置**

```application.yml```或```application.properties```添加开启配置，开启自动装配

```yml
kernel:
   sms:
     ali:
       enabled: true
       access-key-id: access-key
       access-key-secret: secret
       sign-name: sign
```

配置文件可配置参数

| 参数                           | 名称                           | 备注                                     |
| ------------------------------ | ------------------------------ | ---------------------------------------- |
| kernel.sms.ali.enabled  | 是否启用自动装配               | 默认为false                              |
| kernel.sms.ali. access-key-id | access-key         | 必填 |
| kernel.sms.ali.access-key-secret | access-key-secret |  |
| kernel.sms.ali.sign-name | 签名名称 |  |
| kernel.sms.ali.region-id | 地域id | 默认为 cn-hangzhou |

##### 使用

spring boot 使用

```java
@RestController
public class TestController {

    @Autowired
    private SmsSenderApi smsSenderApi;

    @GetMapping("/test/send")
    public String send() {
        String phone = "15555556575";
        String templateCode = "SMS_184830099";
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("code", "566875");
        smsSenderApi.sendSms(phone, templateCode, params);
        return "";
    }
}
```

