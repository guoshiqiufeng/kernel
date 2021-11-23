## 核心层

>  定义异常、异常分类；统一返回对象；工具类
>

### 异常

所有异常都需要继承自`ServiceException`

### 异常分类：`ErrorType`

```java

/**
	 * 用户操作异常
	 */
	USER_OPERATION_ERROR("1", "用户操作异常"),

	/**
	 * 业务异常
	 */
	BUSINESS_ERROR("2", "业务异常"),

	/**
	 * 第三方异常
	 */
	THIRD_ERROR("3", "第三方异常"),

	/**
	 * 空
	 */
	EMPTY("", "")
```

### 工具类

- `ListUtils`

  List对象工具类：提供判断是否为空`isEmpty`、不为空`isNotEmpty`、对象转换`castList`

- `IpUtils`

  ip地址工具类：提供获取ip地址`getIpAddress`

- `HttpServletUtil `

   `HttpServlet`工具类：提供获取request对象`getRequest`、提供获取response对象`getResponse`

- `RandomUtil` 

  随机数工具类：产生随机数字

