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
package com.gitee.fubluesky.kernel.security.jackson.converter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gitee.fubluesky.kernel.core.util.HttpServletUtil;
import com.gitee.fubluesky.kernel.security.jackson.constant.PlatformJacksonConstant;
import com.gitee.fubluesky.kernel.security.jackson.pojo.PlatformJacksonProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间序列化
 *
 * @author yanghq
 * @version 1.0
 * @since 2020-12-08 15:40
 */
@Slf4j
public class CustomDateSerializer {

    /**
     * LocalDate 序列化 处理
     */
    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        PlatformJacksonProperties platformJacksonProperties;

        public LocalDateSerializer(PlatformJacksonProperties platformJacksonProperties) {
            this.platformJacksonProperties = platformJacksonProperties;
        }

        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            if (localDate == null) {
                return;
            }
            // get http servlet request
            HttpServletRequest request = HttpServletUtil.getRequest();
            // get request header timestamps
            String timestamps = request.getHeader(platformJacksonProperties.getTimestampsEnabledHeaderName());
            // get request header dateFormat
            String dateFormat = request.getHeader(platformJacksonProperties.getDateFormatHeaderName());
            if (StrUtil.isNotBlank(timestamps) && PlatformJacksonConstant.DISABLE.equals(timestamps)) {
                log.debug("timestamps: {}", timestamps);
                if (StrUtil.isBlank(dateFormat)) {
                    dateFormat = platformJacksonProperties.getDatePattern();
                }
                // if dateFormat is blank, set yyyy-MM-dd
                if (StrUtil.isBlank(dateFormat)) {
                    dateFormat = PlatformJacksonConstant.PATTERN_DATE;
                }
                log.debug("dateFormat: {}", dateFormat);
                DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
                // localDate format
                String date = format.format(localDate);
                jsonGenerator.writeString(date);
            } else {
                ZoneId zone = ZoneId.systemDefault();
                Instant instant = localDate.atStartOfDay(zone).toInstant();
                jsonGenerator.writeNumber(instant.toEpochMilli());
            }
        }

    }

    /**
     * LocalDateTime 序列化 处理
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        PlatformJacksonProperties platformJacksonProperties;

        public LocalDateTimeSerializer(PlatformJacksonProperties platformJacksonProperties) {
            this.platformJacksonProperties = platformJacksonProperties;
        }

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (localDateTime == null) {
                return;
            }
            // get http servlet request
            HttpServletRequest request = HttpServletUtil.getRequest();
            // get request header timestamps
            String timestamps = request.getHeader(platformJacksonProperties.getTimestampsEnabledHeaderName());
            // get request header dateFormat
            String dateFormat = request.getHeader(platformJacksonProperties.getDateTimeFormatHeaderName());
            if (StrUtil.isNotBlank(timestamps) && PlatformJacksonConstant.DISABLE.equals(timestamps)) {
                log.debug("timestamps: {}", timestamps);
                if (StrUtil.isBlank(dateFormat)) {
                    dateFormat = platformJacksonProperties.getDateTimePattern();
                }
                // if dateFormat is blank, set yyyy-MM-dd HH:mm:ss
                if (StrUtil.isBlank(dateFormat)) {
                    dateFormat = PlatformJacksonConstant.PATTERN_DATETIME;
                }
                log.debug("dateFormat: {}", dateFormat);
                DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
                // localDateTime format
                String date = format.format(localDateTime);
                jsonGenerator.writeString(date);
            } else {
                ZoneId zone = ZoneId.systemDefault();
                Instant instant = localDateTime.atZone(zone).toInstant();
                jsonGenerator.writeNumber(instant.toEpochMilli());
            }
        }

    }

    /**
     * LocalTime 序列化 处理
     */
    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {

        PlatformJacksonProperties platformJacksonProperties;

        public LocalTimeSerializer(PlatformJacksonProperties platformJacksonProperties) {
            this.platformJacksonProperties = platformJacksonProperties;
        }

        @Override
        public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            if (localTime == null) {
                return;
            }
            // get http servlet request
            HttpServletRequest request = HttpServletUtil.getRequest();
            // get request header timestamps
            String timestamps = request.getHeader(platformJacksonProperties.getTimestampsEnabledHeaderName());
            // get request header dateFormat
            String timeFormat = request.getHeader(platformJacksonProperties.getTimeFormatHeaderName());
            if (StrUtil.isNotBlank(timestamps) && PlatformJacksonConstant.DISABLE.equals(timestamps)) {
                log.debug("timestamps: {}", timestamps);
                if (StrUtil.isBlank(timeFormat)) {
                    timeFormat = platformJacksonProperties.getTimePattern();
                }
                // if dateFormat is blank, set HH:mm
                if (StrUtil.isBlank(timeFormat)) {
                    timeFormat = PlatformJacksonConstant.PATTERN_TIME;
                }
                log.debug("dateFormat: {}", timeFormat);
                DateTimeFormatter format = DateTimeFormatter.ofPattern(timeFormat);
                // localTime format
                String date = format.format(localTime);
                jsonGenerator.writeString(date);
            } else {
                ZoneId zone = ZoneId.systemDefault();
                Instant instant = localTime.atDate(LocalDate.now()).atZone(zone).toInstant();
                jsonGenerator.writeNumber(instant.toEpochMilli());
            }
        }

    }

}
