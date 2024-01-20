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

package com.gitee.fubluesky.kernel.security.autoconfigure;

import com.gitee.fubluesky.kernel.security.api.CaptchaApi;
import com.gitee.fubluesky.kernel.security.api.pojo.CaptchaProperties;
import com.gitee.fubluesky.kernel.security.captcha.CaptchaService;
import com.gitee.fubluesky.kernel.security.captcha.cache.CaptchaRedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-11 14:59
 */
@Configuration
public class CaptchaAutoConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "kernel.captcha")
	@ConditionalOnMissingBean(CaptchaProperties.class)
	public CaptchaProperties captchaProperties() {
		return new CaptchaProperties();
	}

	@Autowired
	private RedisTemplate<String, String> defaultStringRedisTemplate;

	@Bean
	@ConditionalOnMissingBean(CaptchaApi.class)
	public CaptchaApi captchaApi() {
		CaptchaRedisCache redisCache = new CaptchaRedisCache(defaultStringRedisTemplate);
		return new CaptchaService(redisCache);
	}

}
