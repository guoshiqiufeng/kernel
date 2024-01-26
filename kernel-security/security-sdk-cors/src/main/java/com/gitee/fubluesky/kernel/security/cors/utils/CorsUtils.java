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
package com.gitee.fubluesky.kernel.security.cors.utils;

import com.gitee.fubluesky.kernel.core.util.ListUtils;
import com.gitee.fubluesky.kernel.security.cors.pojo.CorsProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-26 11:29
 */
public class CorsUtils {

    public final String ALLOW_ALL = "*";

    private final CorsProperties corsProperties;

    public CorsUtils(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    public CorsFilter getCorsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(corsProperties.getAllowCredentials());
        config.addAllowedOriginPattern(corsProperties.getAllowedOrigin());
        if (ListUtils.isEmpty(corsProperties.getAllowHeader())) {
            config.addAllowedHeader(ALLOW_ALL);
        } else {
            for (String s : corsProperties.getAllowHeader()) {
                config.addAllowedHeader(s);
            }
        }
        if (ListUtils.isEmpty(corsProperties.getAllowMethod())) {
            config.addAllowedMethod(ALLOW_ALL);
        } else {
            for (String s : corsProperties.getAllowMethod()) {
                config.addAllowedMethod(s);
            }
        }
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProperties.getPath(), config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return new CorsFilter(source);
    }

}
