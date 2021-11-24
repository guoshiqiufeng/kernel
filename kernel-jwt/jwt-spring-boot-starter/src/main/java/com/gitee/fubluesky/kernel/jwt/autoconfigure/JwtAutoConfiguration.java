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

package com.gitee.fubluesky.kernel.jwt.autoconfigure;

import com.gitee.fubluesky.kernel.jwt.JwtTokenOperator;
import com.gitee.fubluesky.kernel.jwt.api.JwtApi;
import com.gitee.fubluesky.kernel.jwt.api.pojo.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-04 16:32
 */
@Configuration
@ConditionalOnProperty(prefix = "kernel.jwt", name = "enabled", matchIfMissing = true)
public class JwtAutoConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "kernel.jwt")
	@ConditionalOnMissingBean(JwtProperties.class)
	public JwtProperties jwtConfig() {
		return new JwtProperties();
	}

	@Autowired
	private JwtProperties jwtProperties;

	@Bean
	@ConditionalOnMissingBean(JwtApi.class)
	public JwtApi jwtApi() {
		return new JwtTokenOperator(jwtProperties);
	}

}
