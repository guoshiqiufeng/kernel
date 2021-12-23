# file-ali-spring-boot-starter

#### 介绍
阿里云oss 文件服务

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>file-ali-spring-boot-starter</artifactId>
    <version>1.5.4</version>
</dependency>
```

#### 使用说明

##### **配置**

```application.yml```或```application.properties```添加开启配置，开启自动装配

```yml
kernel:
   file:
     ali:
       enabled: true
       domain: http://develop.oss-cn-shenzhen.aliyuncs.com
       prefix: develop
       date-path-enabled: true
       bucket-name: bucket
       access-key: access
       secret-key: secret
       end-point: https://oss-cn-shenzhen.aliyuncs.com
```

配置文件可配置参数

| 参数                           | 名称                           | 备注                                     |
| ------------------------------ | ------------------------------ | ---------------------------------------- |
| kernel.file.ali.enabled     | 是否启用自动装配               | 默认为false                              |
| kernel.file.ali.domain | 外网访问地址                   | 必填 |
| kernel.file.ali.prefix | 文件前缀文件夹              |  |
| kernel.file.ali.date-path-enabled | 是否使用日期+uuid 生成文件名 | 默认为true |
| kernel.file.ali.bucket-name | bucket                 | 必填 |
| kernel.file.ali.access-key | access     | 必填 |
| kernel.file.ali.secret-key | secret           | 必填 |
| kernel.file.ali.end-point | end-point        | 必填                   |

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

