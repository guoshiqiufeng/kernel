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

package com.gitee.fubluesky.kernel.file.local.autoconfigure;

import com.gitee.fubluesky.kernel.file.api.FileOperatorApi;
import com.gitee.fubluesky.kernel.file.local.LocalFileOperator;
import com.gitee.fubluesky.kernel.file.local.pojo.LocalFileProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring mvc 映射访问本地文件 配置
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-02 17:07
 */
@ConditionalOnProperty(prefix = "kernel.file.local", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(LocalFileSpringProperties.class)
@Configuration
public class FileStaticConfig implements WebMvcConfigurer {

	private final LocalFileProperties localFileProperties;

	public FileStaticConfig(LocalFileSpringProperties localFileProperties) {
		this.localFileProperties = localFileProperties;
	}

	@Bean
	@ConditionalOnMissingBean(FileOperatorApi.class)
	public FileOperatorApi fileOperatorApi() {
		return new LocalFileOperator(localFileProperties);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/" + localFileProperties.getMvcPath() + "/**")
				.addResourceLocations("file:" + localFileProperties.getSavePath());
	}

}
