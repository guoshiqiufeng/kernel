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

package com.gitee.fubluesky.kernel.push.autoconfigure;

import com.gitee.fubluesky.kernel.push.api.PushApi;
import com.gitee.fubluesky.kernel.push.jpush.JpushService;
import com.gitee.fubluesky.kernel.push.jpush.pojo.JpushProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-17 10:00
 */
@Configuration
@ConditionalOnProperty(prefix = "kernel.push.jpush", name = "enabled", havingValue = "true")
public class JpushAutoConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "kernel.push.jpush")
	@ConditionalOnMissingBean(JpushProperties.class)
	public JpushProperties jpushProperties() {
		return new JpushProperties();
	}

	@Autowired
	private JpushProperties jpushProperties;

	@Bean
	@ConditionalOnMissingBean(PushApi.class)
	public PushApi pushApi() {
		return new JpushService(jpushProperties);
	}

}
