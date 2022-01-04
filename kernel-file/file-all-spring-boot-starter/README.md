# file-ali-spring-boot-starter

#### 介绍
阿里云oss 文件服务

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>file-all-spring-boot-starter</artifactId>
    <version>1.6.2</version>
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
     local:
       enabled: false
       domain: http://127.0.0.1:8888
       date-path-enabled: true
       local-file-save-path-linux: /file/
       local-file-save-path-win: D:\file\
       prefix: develop
       mvc-path: source
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

    @GetMapping("/test/upload")
    public String uploadTest(@RequestParam("file") MultipartFile multipartFile) {
        String path = UploadUtils.upload(multipartFile.getBytes(), multipartFile.getOriginalFilename());
        return path;
    }
}
```

##### 注

`ali`(阿里云OSS文件存储)、`ftp`(FTP文件存储)、`local`（本地文件存储）可以同时开启，也可以只开启其中的一个或多个；开启后会同时调用开启的存储。

同时开启多个存储后，返回文件的访问前缀会按照 `ali`(阿里云OSS文件存储)、`ftp`(FTP文件存储)、`local`（本地文件存储）顺序进行返回。

> 例如：
>
> 开启 `ali`(阿里云OSS文件存储) 后默认 返回前缀 为 `kernel.file.ali.domain`中配置的内容
>
> 未开启 `ali`(阿里云OSS文件存储) ，开启`ftp`(FTP文件存储) 后默认 返回前缀 为 `kernel.file.ftp.domain`中配置的内容
>
> 只开启`local`（本地文件存储）后默认 返回前缀 为 `kernel.file.local.domain`中配置的内容
>
> 想要调整输出顺序 可以使用只返回相对地址上传方法，文件地址前缀获取方法自定义 进行两者拼接即可

