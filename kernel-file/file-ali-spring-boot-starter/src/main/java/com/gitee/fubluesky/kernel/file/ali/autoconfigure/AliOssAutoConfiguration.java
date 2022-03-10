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

package com.gitee.fubluesky.kernel.file.ali.autoconfigure;

import com.gitee.fubluesky.kernel.file.ali.AliFileOperator;
import com.gitee.fubluesky.kernel.file.ali.pojo.AliOssProperties;
import com.gitee.fubluesky.kernel.file.api.FileOperatorApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-02 14:09
 */
@Configuration
@ConditionalOnProperty(prefix = "kernel.file.ali", name = "enabled", matchIfMissing = true)
public class AliOssAutoConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "kernel.file.ali")
	@ConditionalOnMissingBean(AliOssProperties.class)
	public AliOssProperties aliOssProperties() {
		return new AliOssProperties();
	}

	@Bean
	@ConditionalOnMissingBean(FileOperatorApi.class)
	public FileOperatorApi fileOperatorApi(AliOssProperties aliOssProperties) {
		return new AliFileOperator(aliOssProperties);
	}

}
