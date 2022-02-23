# email-spring-boot-starter

#### 介绍
邮件发送

#### 安装教程
使用maven进行依赖安装，163邮箱测试通过

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>email-spring-boot-starter</artifactId>
    <version>1.6.5</version>
</dependency>
```

#### 使用说明

##### **配置**

```application.yml```或```application.properties```添加开启配置，开启自动装配

```yml
kernel:
   mail:
      enabled: true
      host: smtp.163.com
      port:
      auth: true
      user: user@163.com
      pass: pass
      from: user@163.com
      debug: true
```

配置文件可配置参数

| 参数                           | 名称                           | 备注                                     |
| ------------------------------ | ------------------------------ | ---------------------------------------- |
| kernel.mail.enabled             | 是否启用自动装配               | 默认为false                              |
| kernel.mail.host             | 服务器主机名                       | 必填 |
| kernel.mail.port        | 服务器端口                     | 默认 465 |
| kernel.mail.auth        | 是否开启认证                       | 默认为false |
| kernel.mail.user     | 认证用户名           | 开启认证后，必填 |
| kernel.mail.pass          | 认证密码                   | 开启认证后，必填 |
| kernel.mail.from     | 发送方                  | 必填，需为邮件地址                   |
| kernel.mail.debug         | 是否开启调试模式               | 默认false                                |

##### 使用

spring boot 使用

```java
@RestController
public class TestController {

    @Autowired
    private MailSenderApi mailSenderApi;

    @GetMapping("/test/send")
    public String sendTest() {
        SendMailParam param= new SendMailParam();
        param.setTo("user@mail.com");
        param.setTitle("test");
        param.setContent("<html><head></head><body><h1>test content</h1></body></html>");

        mailSenderApi.sendMailHtml(param);
        return  "success";
    }
}
```

