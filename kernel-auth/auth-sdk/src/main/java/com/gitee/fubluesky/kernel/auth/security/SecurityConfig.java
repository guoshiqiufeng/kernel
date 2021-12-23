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

package com.gitee.fubluesky.kernel.auth.security;

import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.core.util.ListUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsUtils;

/**
 * spring security 配置
 * @author yanghq
 * @version 1.0
 * @since 2021-12-21 13:40
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthProperties authProperties;

    public SecurityConfig(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Override
    public void configure(WebSecurity web) {
        // 新防火墙强制覆盖原来的
        web.httpFirewall(defaultHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorizeRequests所有security全注解配置实现的开端，表示开始说明需要的权限。
        // 需要的权限分两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限。
        // antMatchers表示拦截什么路径，permitAll任何权限都可以访问，直接放行所有。
        // anyRequest()任何的请求，authenticated认证后才能访问
        // .and().csrf().disable();固定写法，表示使csrf拦截失效。
        http.headers().contentTypeOptions().disable();
        // 允许网页被同一域名iframe
        http.headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));

        if(ListUtils.isNotEmpty(authProperties.getSecurityConfigAllowPatterns())){
            http.authorizeRequests()
                    .antMatchers(authProperties.getSecurityConfigAllowPatterns().toArray(new String[1]))
                    .permitAll();
        }
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll().anyRequest().authenticated();
        // jwt 过滤器
        http.addFilter(new JwtAuthorizationFilter(authenticationManager()));
        // 不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 使csrf拦截失效。
        http.csrf().disable();
        // 配置 匿名用户访问无权限资源时的 异常
        http.exceptionHandling().authenticationEntryPoint(new SecurityAuthenticationEntryPoint());
    }

}
