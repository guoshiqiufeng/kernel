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

package com.gitee.fubluesky.kernel.db.autoconfigure;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.gitee.fubluesky.kernel.db.mybatisplus.handler.MyMetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-13 10:29
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ MybatisPlusProperties.class, CustomMybatisPlusProperties.class })
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
public class MyBatisPlusAutoConfiguration {

	/**
	 * 分页插件
	 */
	@Bean
	public MybatisPlusInterceptor paginationInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return interceptor;
	}

	/**
	 * mybatis plus 自动填充 配置
	 */
	@Bean
	public MyMetaObjectHandler myMetaObjectHandler() {
		return new MyMetaObjectHandler();
	}

	/**
	 * 修改mybatis-plus 默认参数
	 */
	MyBatisPlusAutoConfiguration(MybatisPlusProperties mybatisPlusProperties,
			CustomMybatisPlusProperties customMybatisPlusProperties) {
		log.debug("MyBatisPlusAutoConfiguration start");
		mybatisPlusProperties.setGlobalConfig(customMybatisPlusProperties.getGlobalConfig())
				.setConfiguration(customMybatisPlusProperties.getConfiguration());
		log.debug("mybatisPlusProperties: {}", mybatisPlusProperties);
		log.debug("MyBatisPlusAutoConfiguration end");
	}

}
