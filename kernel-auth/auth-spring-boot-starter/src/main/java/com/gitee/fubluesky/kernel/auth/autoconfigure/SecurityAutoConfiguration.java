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

import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.auth.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-12-21 13:41
 */
@Configuration
@EnableWebSecurity
@AutoConfigureAfter(value = { AuthAutoConfiguration.class })
@ConditionalOnProperty(prefix = "kernel.auth", name = "securityConfigEnabled", matchIfMissing = true)
public class SecurityAutoConfiguration {

    @Autowired
    private AuthProperties authProperties;

    @Bean
    public SecurityConfig securityConfig() {
        return new SecurityConfig(authProperties);
    }
}
