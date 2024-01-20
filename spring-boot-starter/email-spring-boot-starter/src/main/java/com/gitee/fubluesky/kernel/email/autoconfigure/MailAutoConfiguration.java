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
package com.gitee.fubluesky.kernel.email.autoconfigure;

import com.gitee.fubluesky.kernel.email.api.MailSenderApi;
import com.gitee.fubluesky.kernel.email.api.pojo.MailProperties;
import com.gitee.fubluesky.kernel.email.java.JavaMailSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-30 9:56
 */
@Configuration
@ConditionalOnProperty(prefix = "kernel.mail", name = "enabled", matchIfMissing = true)
public class MailAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "kernel.mail")
    @ConditionalOnMissingBean(MailProperties.class)
    public MailProperties mailProperties() {
        return new MailProperties();
    }

    @Bean
    @ConditionalOnMissingBean(MailSenderApi.class)
    public MailSenderApi mailSenderApi(MailProperties mailProperties) {
        return new JavaMailSender(mailProperties);
    }

}
