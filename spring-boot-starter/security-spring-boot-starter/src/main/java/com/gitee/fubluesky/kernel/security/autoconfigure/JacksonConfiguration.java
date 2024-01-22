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
package com.gitee.fubluesky.kernel.security.autoconfigure;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.gitee.fubluesky.kernel.security.jackson.constant.PlatformJacksonConstant;
import com.gitee.fubluesky.kernel.security.jackson.converter.CustomDateDeserializer;
import com.gitee.fubluesky.kernel.security.jackson.converter.CustomDateSerializer;
import com.gitee.fubluesky.kernel.security.jackson.pojo.PlatformJacksonProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
@ConditionalOnProperty(prefix = "kernel.jackson", name = "enabled", matchIfMissing = true)
public class JacksonConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "kernel.jackson")
    @ConditionalOnMissingBean(PlatformJacksonProperties.class)
    public PlatformJacksonProperties platformJacksonProperties() {
        return new PlatformJacksonProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public Jackson2ObjectMapperBuilderCustomizer customizer(PlatformJacksonProperties platformJacksonProperties) {
        return builder -> {
            builder.simpleDateFormat(PlatformJacksonConstant.PATTERN_DATETIME);

            if (!platformJacksonProperties.getTimestampsEnabled()) {
                // 去掉默认的时间戳格式
                builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            }
            // 设置为中国上海时区
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 序列化时，日期的统一格式
            builder.dateFormat(new SimpleDateFormat(PlatformJacksonConstant.PATTERN_DATETIME, Locale.CHINA));
            // 序列化处理
            builder.featuresToEnable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
            builder.featuresToEnable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature());

            // 失败处理
            builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // 单引号处理
            builder.featuresToEnable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            // 序列化、反序列化设置
            if (!platformJacksonProperties.getTimestampsEnabled()) {
                // LocalDateTime
                String dateTimePattern = platformJacksonProperties.getDateTimePattern();
                if (StrUtil.isBlank(dateTimePattern)) {
                    dateTimePattern = PlatformJacksonConstant.PATTERN_DATETIME;
                }
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(dateTimePattern);
                builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormat));
                builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormat));
                // LocalDate
                String datePattern = platformJacksonProperties.getDatePattern();
                if (StrUtil.isBlank(datePattern)) {
                    datePattern = PlatformJacksonConstant.PATTERN_DATE;
                }
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(datePattern);
                builder.serializerByType(LocalDate.class, new LocalDateSerializer(dateFormat));
                builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(dateFormat));
                // LocalTime
                String timePattern = platformJacksonProperties.getTimePattern();
                if (StrUtil.isBlank(timePattern)) {
                    timePattern = PlatformJacksonConstant.PATTERN_TIME;
                }
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(timePattern);
                builder.serializerByType(LocalTime.class, new LocalTimeSerializer(timeFormat));
                builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(timeFormat));
            } else {
                builder.serializerByType(LocalDateTime.class,
                        new CustomDateSerializer.LocalDateTimeSerializer(platformJacksonProperties));
                builder.deserializerByType(LocalDateTime.class,
                        new CustomDateDeserializer.LocalDateTimeDeserializer(platformJacksonProperties));
                // LocalDate
                builder.serializerByType(LocalDate.class, new CustomDateSerializer.LocalDateSerializer(platformJacksonProperties));
                builder.deserializerByType(LocalDate.class,
                        new CustomDateDeserializer.LocalDateDeserializer(platformJacksonProperties));
                // LocalTime
                builder.serializerByType(LocalTime.class, new CustomDateSerializer.LocalTimeSerializer(platformJacksonProperties));
                builder.deserializerByType(LocalTime.class,
                        new CustomDateDeserializer.LocalTimeDeserializer(platformJacksonProperties));
            }

        };
    }

}
