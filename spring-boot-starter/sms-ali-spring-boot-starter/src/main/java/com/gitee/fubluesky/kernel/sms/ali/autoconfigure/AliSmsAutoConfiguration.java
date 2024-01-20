/*
 * Copyright (c) 2021-2024, fubluesky (fubluesky@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitee.fubluesky.kernel.sms.ali.autoconfigure;

import com.gitee.fubluesky.kernel.sms.ali.AliSmsSender;
import com.gitee.fubluesky.kernel.sms.ali.pojo.AliSmsProperties;
import com.gitee.fubluesky.kernel.sms.api.SmsSenderApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-04 15:15
 */
@Configuration
@ConditionalOnProperty(prefix = "kernel.sms.ali", name = "enabled", matchIfMissing = true)
public class AliSmsAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "kernel.sms.ali")
    @ConditionalOnMissingBean(AliSmsProperties.class)
    public AliSmsProperties aliSmsProperties() {
        return new AliSmsProperties();
    }

    @Bean
    @ConditionalOnMissingBean(SmsSenderApi.class)
    public SmsSenderApi smsSenderApi(AliSmsProperties aliSmsProperties) {
        return new AliSmsSender(aliSmsProperties);
    }

}
