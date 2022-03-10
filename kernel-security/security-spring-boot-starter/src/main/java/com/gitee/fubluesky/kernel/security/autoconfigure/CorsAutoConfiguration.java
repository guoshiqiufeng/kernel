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

import com.gitee.fubluesky.kernel.security.cors.pojo.CorsProperties;
import com.gitee.fubluesky.kernel.security.cors.utils.CorsUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-26 15:12
 */
@Configuration
@ConditionalOnProperty(prefix = "kernel.cors", name = "enabled", matchIfMissing = true)
public class CorsAutoConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "kernel.cors")
	@ConditionalOnMissingBean(CorsProperties.class)
	public CorsProperties corsProperties() {
		return new CorsProperties();
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(CorsProperties corsProperties) {
		return new CorsUtils(corsProperties).getCorsFilter();
	}

}
