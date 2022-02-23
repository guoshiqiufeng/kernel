## 数据库相关

> 主要对`mybatis plus`相关进行了封装和配置

#### 安装
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>db-spring-boot-starter</artifactId>
    <version>1.6.5</version>
</dependency>
```

### 实体

| 实体类             | 备注                                                   | 所在包                                         |
| ------------------ | ------------------------------------------------------ | ---------------------------------------------- |
| BaseDomain         | 通用领域模型；数据库相关模型类要继承此类               | com.gitee.fubluesky.kernel.db.api.pojo.domain  |
| IBaseService       | 通用业务逻辑接口；所有数据库相关业务接口要继承此类     | com.gitee.fubluesky.kernel.db.mybatisplus.pojo |
| BaseServiceImpl    | 通用业务逻辑实现；所有数据库相关业务实现类要继承此类   | com.gitee.fubluesky.kernel.db.mybatisplus.pojo |
| BaseRestController | 通用控制层 rest 实现；所有数据库控制层相关类要继承此类 | com.gitee.fubluesky.kernel.db.mybatisplus.pojo |
| PageResult         | 分页返回对象                                           | com.gitee.fubluesky.kernel.db.api.pojo.page    |

### 配置

- 分页插件

  默认启用了`mybatis plus`的分页插件 `PaginationInnerInterceptor`

- 自动填充

  默认启用了`mybatis plus`的添加、更新方法的自动填充

- 其他

  - 关闭了`mybatis plus` 的 `banner`
  - 设置数据库主键类型默认为自增 `IdType.AUTO`
  - 设置逻辑删除字段 `isDeleted`

  可通过修改`mybatis plus`下的配置覆盖以上相关配置

### 配置项

> ```application.yml```或```application.properties```添加开启配置，会在`mybatis plus` 加载前进行加载，覆盖配置的参数可在此修改，也可以在`mybatis plus`下进行修改

| 参数                           | 名称                           | 备注                                     |
| ------------------------------ | ------------------------------ | ---------------------------------------- |
| kernel.db.enabled  | 是否启用自动装配               | 默认为true                          |
| kernel.db.interceptor-enabled | 是否启用分页插件                                             | 默认为true |
| kernel.db.meta-enabled | 是否启用数据填充 | 默认为true |
| kernel.db.create-date | 创建时间字段；开启数据填充后会对包含本字段的数据库实体填充当前时间 | createDate |
| kernel.db.modify-date | 修改时间字段；开启数据填充后会对包含本字段的数据库实体填充当前时间 | modifyDate |
| kernel.db.is-deleted | 逻辑删除字段；开启数据填充后会对包含本字段的数据库实体填充0或false | isDeleted |