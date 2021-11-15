/*
 *
 *   Copyright 2021 fubluesky.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.gitee.fubluesky.kernel.cache.redis.autoconfigure;

import com.gitee.fubluesky.kernel.cache.redis.operator.RedisCacheOperator;
import com.gitee.fubluesky.kernel.cache.redis.operator.StringRedisCacheOperator;
import com.gitee.fubluesky.kernel.cache.redis.utils.RedisCacheUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-06 10:15
 */
@Configuration
public class RedisCacheAutoConfiguration {

	/**
	 * value是object类型的redis操作类
	 */
	@Bean
	public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheUtils.getObjectRedisTemplate(redisConnectionFactory);
	}

	/**
	 * value是string类型的redis操作类
	 */
	@Bean
	public RedisTemplate<String, String> defaultStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 创建默认的value是string类型的redis缓存
	 */
	@Bean
	public StringRedisCacheOperator stringRedisCacheOperator(RedisTemplate<String, String> stringRedisTemplate) {
		return new StringRedisCacheOperator(stringRedisTemplate);
	}

	/**
	 * 创建默认的value是object类型的redis缓存
	 */
	@Bean
	public RedisCacheOperator redisCacheOperator(RedisTemplate<String, Object> objectRedisTemplate) {
		return new RedisCacheOperator(objectRedisTemplate);
	}

}
