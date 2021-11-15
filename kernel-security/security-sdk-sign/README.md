# security-sdk-sign

#### 介绍
- 方法签名
- 支持 requestBody 和 requestGet 两种方式
  `启用签名验证后，默认启用requestBody签名验证，可手动开启requestGet`
#### 签名方式

##### 目前采用 uri+请求体+盐 md5生成签名
- requestBody ```uri+json+salt```
- get ```uri+queryString+salt```
#### 配置项