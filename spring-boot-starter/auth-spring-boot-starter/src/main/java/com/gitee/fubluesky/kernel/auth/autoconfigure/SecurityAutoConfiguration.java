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
package com.gitee.fubluesky.kernel.auth.autoconfigure;

import cn.hutool.core.collection.CollectionUtil;
import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.auth.security.JwtAuthorizationFilter;
import com.gitee.fubluesky.kernel.auth.security.SecurityAuthenticationEntryPoint;
import io.micrometer.common.util.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsUtils;

import java.util.Arrays;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-12-21 13:41
 */
@Configuration
@EnableWebSecurity
@AutoConfigureAfter(value = {AuthAutoConfiguration.class})
@ConditionalOnProperty(prefix = "kernel.auth.security", name = "enabled", havingValue = "true")
@EnableMethodSecurity
public class SecurityAutoConfiguration {


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(AuthenticationManager authenticationManager, AuthProperties authProperties, HttpSecurity http) throws Exception {
        // authorizeRequests所有security全注解配置实现的开端，表示开始说明需要的权限。
        // 需要的权限分两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限。
        // antMatchers表示拦截什么路径，permitAll任何权限都可以访问，直接放行所有。
        // anyRequest()任何的请求，authenticated认证后才能访问
        // .and().csrf().disable();固定写法，表示使csrf拦截失效。
        http.headers(h -> h.contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable).disable());

        // 允许网页被同一域名iframe
        http.headers(h -> h.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));

        if (CollectionUtil.isNotEmpty(authProperties.getSecurity().getAllowPatterns())) {
            String[] allowPatterns = authProperties.getSecurity().getAllowPatterns().toArray(new String[1]);
            allowPatterns = Arrays.stream(allowPatterns).filter(StringUtils::isNotEmpty).toArray(String[]::new);
            String[] finalAllowPatterns = allowPatterns;
            http.authorizeHttpRequests(h -> h.requestMatchers(finalAllowPatterns).permitAll());
        }
        http.authorizeHttpRequests(h -> h.requestMatchers(CorsUtils::isPreFlightRequest).permitAll().anyRequest()
                .authenticated());
        // 禁用logout
        http.logout(AbstractHttpConfigurer::disable);
        // jwt 过滤器
        http.addFilter(new JwtAuthorizationFilter(authenticationManager));
        // 不需要session
        http.sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 使csrf拦截失效。
        http.csrf(AbstractHttpConfigurer::disable);
        // 配置 匿名用户访问无权限资源时的 异常
        http.exceptionHandling(h -> h.authenticationEntryPoint(new SecurityAuthenticationEntryPoint()));
        return http.build();
    }

}

