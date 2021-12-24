# cache-redis-spring-boot-starter

#### 介绍
redis 缓存服务

#### 安装教程
使用maven进行依赖安装

pom添加依赖

```xml
<dependency>
    <groupId>com.gitee.fubluesky.kernel</groupId>
    <artifactId>cache-redis-spring-boot-starter</artifactId>
    <version>1.5.6</version>
</dependency>
```

#### 使用说明

##### **配置**

正常配置redis使用参数

##### 使用
缓存对象
```java
public class TestRedisCache extends AbstractRedisCacheOperator<String> {

    public TestRedisCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    /**
     * 获取缓存的前缀
     * @return 缓存前缀
     */
    @Override
    public String getKeyPrefix() {
        return "test:";
    }

}

```
自动注入
```java
@Configuration
public class CacheConfiguration {

	@Bean
	public RedisTemplate<String, String> testRedisTemplate(
			RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheUtils.getObjectRedisTemplate(redisConnectionFactory);
	}

	@Autowired
	private RedisTemplate<String, String> testRedisTemplate;

	@Bean
	public TestRedisCache testRedisCache() {
		return new TestRedisCache(testRedisTemplate);
	}

}

```
使用
```java

@RestController
public class TestController {

    @Autowired
    private TestRedisCache testRedisCache;

    @GetMapping("/test/set")
    public String set(@RequestParam("key") String key)  {
        testRedisCache.add(key, "test");
        return "test";
    }

    @GetMapping("/test/get")
    @ResponseBody
    public String get(@RequestParam("key") String key)  {
        return testRedisCache.get(key);
    }
}
```