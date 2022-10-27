# kernel
[![Maven central](https://img.shields.io/maven-central/v/com.gitee.fubluesky.kernel/kernel.svg?style=flat-square)](https://search.maven.org/search?q=g:com.gitee.fubluesky.kernel%20AND%20a:kernel)
[![License](https://img.shields.io/:license-apache-brightgreen.svg?style=flat-square)](http://www.apache.org/licenses/LICENSE-2.0.html)
#### 介绍
内核框架
#### 依赖
- jdk 17
- spring-boot 2.7.4
- spring-cloud 2021.0.3
- spring-cloud-alibaba 2021.0.4.0
- kernel-dependencies 1.7.0
#### 代码
采用阿里巴巴代码规范，使用`spring-javaformat`格式化代码
#### 模块
- [x] kernel-core [01] [使用说明](kernel-core/README.md)
- [x] kernel-db [02] [使用说明](kernel-db/README.md)
- [x] kernel-jwt [03] [使用说明](kernel-jwt/README.md)

- [x] kernel-cache [10] [使用说明](kernel-cache/README.md)
- [x] kernel-validator [11]
- [x] kernel-security [12]
- [x] kernel-auth [13] [使用说明](kernel-auth/README.md)

- [x] kernel-file [20] [使用说明](kernel-file/README.md)
- [x] kernel-email [21] [使用说明](kernel-email/README.md)
- [x] kernel-sms [22] [使用说明](kernel-sms/README.md)
- [x] kernel-push [23]

- [x] kernel-excel [30]

#### ToDo
- [ ] kernel-sms 添加多签名切换
- [x] kernel-jwt 添加刷新token机制
- [x] auth jwt 添加多租户校验
- [x] jackson localDateTime、localDate、localTime支持 通过request header设置转换格式
- [ ] 模块拆分
- [ ] 文档
- [ ] demo
  - [vea](https://github.com/guoshiqiufeng/vea) 微服务demo
#### 安装教程
使用maven进行依赖安装

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
