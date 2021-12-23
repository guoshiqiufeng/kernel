# file-ftp-spring-boot-starter

#### 介绍
FTP 文件服务

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>file-ftp-spring-boot-starter</artifactId>
    <version>1.5.5</version>
</dependency>
```

#### 使用说明

##### **配置**

```application.yml```或```application.properties```添加开启配置，开启自动装配

```yml
kernel:
   file:
     ftp:
       enabled: true
       domain: http://127.0.0.1:88
       prefix: develop
       date-path-enabled: true
       host: 127.0.0.1
       port: 21
       auth: true
       user-name: ftpuser
       password: ftpuser
       is-passive: true
       temp-dir: /temp
```

配置文件可配置参数

| 参数                           | 名称                           | 备注                                     |
| ------------------------------ | ------------------------------ | ---------------------------------------- |
| kernel.file.ftp.enabled  | 是否启用自动装配               | 默认为false                              |
| kernel.file.ftp.domain | 外网访问地址                   | 必填 |
| kernel.file.ftp.prefix | 文件前缀文件夹              |  |
| kernel.file.ali.date-path-enabled | 是否使用日期+uuid 生成文件名 | 默认为true |
| kernel.file.ftp.host | ftp 服务ip         | 必填 |
| kernel.file.ftp.port | ftp 服务端口 | 必填 |
| kernel.file.ftp.auth | 是否开启认证     | 默认为false |
| kernel.file.ftp.user-name | 用户名        | 开启认证，必填              |
| kernel.file.ftp.password | 密码 | 开启认证，必填 |
| kernel.file.ftp.is-passive | 是否开启私有模式 | 默认为false |
| kernel.file.ftp.temp-dir | 临时文件夹目录 |  |

##### 使用

spring boot 使用

```java
@RestController
public class TestController {

    @Autowired
    private FileOperatorApi fileOperatorApi;

    @GetMapping("/test/upload")
    public String uploadTest(@RequestParam("file") MultipartFile multipartFile) {
        String path = fileOperatorApi.upload(multipartFile.getBytes(), multipartFile.getOriginalFilename());
        return fileOperatorApi.getHttpPrefix() + path;
    }
}
```

