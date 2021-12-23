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

package com.gitee.fubluesky.kernel.security.autoconfigure;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gitee.fubluesky.kernel.security.jackson.constant.PlatformJacksonConstant;
import com.gitee.fubluesky.kernel.security.jackson.module.DefaultJavaTimeModule;
import com.gitee.fubluesky.kernel.security.jackson.module.TimestampsJavaTimeModule;
import com.gitee.fubluesky.kernel.security.jackson.pojo.PlatformJacksonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-08 16:38
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "kernel.jackson")
	@ConditionalOnMissingBean(PlatformJacksonProperties.class)
	public PlatformJacksonProperties platformJacksonProperties() {
		return new PlatformJacksonProperties();
	}

	@Autowired
	private PlatformJacksonProperties platformJacksonProperties;

	@Autowired
	private Jackson2ObjectMapperBuilder builder;

	@Bean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		builder.simpleDateFormat(PlatformJacksonConstant.PATTERN_DATETIME);
		// 创建ObjectMapper
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		// 设置地点为中国
		objectMapper.setLocale(Locale.CHINA);
		if (!platformJacksonProperties.getTimestampsEnabled()) {
			// 去掉默认的时间戳格式
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		}
		// 设置为中国上海时区
		objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
		// 序列化时，日期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat(PlatformJacksonConstant.PATTERN_DATETIME, Locale.CHINA));
		// 序列化处理
		objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
		objectMapper.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
		objectMapper.findAndRegisterModules();
		// 失败处理
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 单引号处理
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 反序列化时，属性不存在的兼容处理
		objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		if (!platformJacksonProperties.getTimestampsEnabled()) {
			objectMapper.registerModule(new DefaultJavaTimeModule(platformJacksonProperties));
		}
		else {
			objectMapper.registerModule(new TimestampsJavaTimeModule(platformJacksonProperties));
		}
		objectMapper.findAndRegisterModules();
		return objectMapper;
	}

}
