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

package com.gitee.fubluesky.kernel.auth.autoconfigure;

import com.gitee.fubluesky.kernel.auth.LoginImpl;
import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.auth.cache.LoginUserRedisCache;
import com.gitee.fubluesky.kernel.auth.cache.LoginUserTokenRedisCache;
import com.gitee.fubluesky.kernel.cache.api.CacheOperatorApi;
import com.gitee.fubluesky.kernel.cache.redis.utils.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-12 16:57
 */
@Configuration
@AutoConfigureBefore(AuthAutoConfiguration.class)
public class LoginAutoConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "kernel.auth")
	@ConditionalOnMissingBean(AuthProperties.class)
	public AuthProperties authProperties() {
		return new AuthProperties();
	}

	@Autowired
	private AuthProperties authProperties;

	@Bean
	public RedisTemplate<String, LoginUser> loginUserRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheUtils.getObjectRedisTemplate(redisConnectionFactory);
	}

	@Autowired
	private RedisTemplate<String, LoginUser> loginUserRedisTemplate;

	@Bean
	@ConditionalOnMissingBean(name = "loginUserTokenCache")
	public CacheOperatorApi<LoginUser> loginUserTokenCache() {
		return new LoginUserTokenRedisCache(loginUserRedisTemplate);
	}

	@Bean
	@ConditionalOnMissingBean(name = "loginUserCache")
	public CacheOperatorApi<LoginUser> loginUserCache() {
		return new LoginUserRedisCache(loginUserRedisTemplate);
	}

	@Autowired
	private CacheOperatorApi<LoginUser> loginUserCache;

	@Bean
	@ConditionalOnMissingBean(LoginApi.class)
	public LoginApi loginApi() {
		return new LoginImpl(authProperties, loginUserCache);
	}

}
