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

package com.gitee.fubluesky.kernel.file.ftp.autoconfigure;

import com.gitee.fubluesky.kernel.file.api.FileOperatorApi;
import com.gitee.fubluesky.kernel.file.ftp.FtpFileOperator;
import com.gitee.fubluesky.kernel.file.ftp.pojo.FtpFileProperties;
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
@ConditionalOnProperty(prefix = "kernel.file.ftp", name = "enabled", matchIfMissing = true)
public class FtpFileAutoConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "kernel.file.ftp")
	@ConditionalOnMissingBean(FtpFileProperties.class)
	public FtpFileProperties ftpFileProperties() {
		return new FtpFileProperties();
	}

	@Bean
	@ConditionalOnMissingBean(FileOperatorApi.class)
	public FileOperatorApi fileOperatorApi(FtpFileProperties ftpFileProperties) {
		return new FtpFileOperator(ftpFileProperties);
	}

}
