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

import com.gitee.fubluesky.kernel.auth.AuthImpl;
import com.gitee.fubluesky.kernel.auth.api.AuthApi;
import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.password.PasswordEncryptApi;
import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.auth.password.BcryptPasswordEncrypt;
import com.gitee.fubluesky.kernel.cache.api.CacheOperatorApi;
import com.gitee.fubluesky.kernel.jwt.api.JwtApi;
import com.gitee.fubluesky.kernel.jwt.autoconfigure.JwtAutoConfiguration;
import com.gitee.fubluesky.kernel.security.api.CaptchaApi;
import com.gitee.fubluesky.kernel.security.api.pojo.CaptchaProperties;
import com.gitee.fubluesky.kernel.security.autoconfigure.CaptchaAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-16 16:44
 */
@Configuration
@AutoConfigureAfter(value = { JwtAutoConfiguration.class, CaptchaAutoConfiguration.class })
public class AuthAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(PasswordEncryptApi.class)
	public PasswordEncryptApi passwordEncryptApi() {
		return new BcryptPasswordEncrypt();
	}

	@Bean
	@ConditionalOnMissingBean(AuthApi.class)
	public AuthApi authApi(AuthProperties authProperties, LoginApi loginApi, PasswordEncryptApi passwordEncryptApi,
			CacheOperatorApi<LoginUser> loginUserTokenCache, CacheOperatorApi<LoginUser> loginUserCache,
			CaptchaApi captchaApi, CaptchaProperties captchaProperties, JwtApi jwtApi) {
		return new AuthImpl(authProperties, loginApi, passwordEncryptApi, loginUserTokenCache, loginUserCache,
				captchaApi, captchaProperties, jwtApi);
	}

}
