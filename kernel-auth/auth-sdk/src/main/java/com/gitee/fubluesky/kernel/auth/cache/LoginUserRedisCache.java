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
package com.gitee.fubluesky.kernel.auth.cache;

import com.gitee.fubluesky.kernel.auth.api.constants.AuthConstants;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.auth.utils.SpringContextUtils;
import com.gitee.fubluesky.kernel.cache.redis.AbstractRedisCacheOperator;
import com.gitee.fubluesky.kernel.core.constants.CoreConstants;
import com.gitee.fubluesky.kernel.jwt.api.pojo.JwtProperties;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-12 15:03
 */
public class LoginUserRedisCache extends AbstractRedisCacheOperator<LoginUser> {

	public LoginUserRedisCache(RedisTemplate<String, LoginUser> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public void add(String key, LoginUser value) {
		JwtProperties jwtProperties = SpringContextUtils.getBean(JwtProperties.class);
		int day = jwtProperties.getRefresh();
		long exp = 60 * 60 * 24L * day;
		super.add(key, value, exp);
	}

	/**
	 * 获取缓存的前缀
	 * @return 缓存前缀
	 */
	@Override
	public String getKeyPrefix() {
		return AuthConstants.CACHE_KEY_PREFIX + CoreConstants.COLON + AuthConstants.LOGIN_USER_PREFIX
				+ CoreConstants.COLON;
	}

}
