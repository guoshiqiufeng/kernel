# file-local-spring-boot-starter

#### 介绍
本地 文件服务

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>file-local-spring-boot-starter</artifactId>
    <version>1.6.0</version>
</dependency>
```

#### 使用说明

##### **配置**

```application.yml```或```application.properties```添加开启配置，开启自动装配

```yml
kernel:
   file:
	 local:
        enabled: false
        domain: http://127.0.0.1:8888
        date-path-enabled: true
        local-file-save-path-linux: /file/
        local-file-save-path-win: D:\file\
        prefix: dev
        mvc-path: source
```

配置文件可配置参数

| 参数                           | 名称                           | 备注                                     |
| ------------------------------ | ------------------------------ | ---------------------------------------- |
| kernel.file.local.enabled | 是否启用自动装配               | 默认为false                              |
| kernel.file.local.domain | 外网访问地址                   | 必填 |
| kernel.file.local.prefix | 文件前缀文件夹              |  |
| kernel.file.local.date-path-enabled | 是否使用日期+uuid 生成文件名 | 默认为true |
| kernel.file.local.local-file-save-path-linux | linux 环境存储目录 | 需要以/结尾 |
| kernel.file.local.local-file-save-path-win | windows 环境存储目录 | 需要以\结尾 |
| kernel.file.local.mvc-path | mvc 映射路径 | 配置后，可映射到文件所在目录 |

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

