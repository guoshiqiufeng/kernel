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

import com.gitee.fubluesky.kernel.security.sign.api.SignApi;
import com.gitee.fubluesky.kernel.security.sign.api.impl.Md5SignImpl;
import com.gitee.fubluesky.kernel.security.sign.pojo.SignProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-11 13:39
 */
@Configuration
@ComponentScan("com.gitee.fubluesky.kernel.security.sign")
@ConditionalOnProperty(prefix = "kernel.sign", name = "enabled", havingValue = "true")
public class SignAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SignApi.class)
	public SignApi signApi() {
		return new Md5SignImpl();
	}

	@Bean
	@ConfigurationProperties(prefix = "kernel.sign")
	@ConditionalOnMissingBean(SignProperties.class)
	public SignProperties signProperties() {
		return new SignProperties();
	}

}
