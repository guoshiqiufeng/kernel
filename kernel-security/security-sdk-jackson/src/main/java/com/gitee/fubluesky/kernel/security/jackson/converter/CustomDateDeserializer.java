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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.gitee.fubluesky.kernel.core.util.HttpServletUtil;
import com.gitee.fubluesky.kernel.security.jackson.constant.PlatformJacksonConstant;
import com.gitee.fubluesky.kernel.security.jackson.pojo.PlatformJacksonProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间反序列化
 *
 * @author yanghq
 * @version 1.0
 * @since 2020-12-08 15:40
 */
@Slf4j
public class CustomDateDeserializer {

    /**
     * 反序列化LocalDate
     */
    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        PlatformJacksonProperties platformJacksonProperties;

        public LocalDateDeserializer(PlatformJacksonProperties platformJacksonProperties) {
            this.platformJacksonProperties = platformJacksonProperties;
        }

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
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
                // localDate parse
                String localDateStr = jsonParser.getValueAsString();
                return LocalDate.parse(localDateStr, format);
            } else {
                long timestamp = jsonParser.getValueAsLong();
                Instant instant = Instant.ofEpochMilli(timestamp);
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            }
        }

    }

    /**
     * 反序列化LocalDateTime
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        PlatformJacksonProperties platformJacksonProperties;

        public LocalDateTimeDeserializer(PlatformJacksonProperties platformJacksonProperties) {
            this.platformJacksonProperties = platformJacksonProperties;
        }

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
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
                // localDateTime parse
                String localDateTimeStr = jsonParser.getValueAsString();
                return LocalDateTime.parse(localDateTimeStr, format);
            } else {
                long timestamp = jsonParser.getValueAsLong();
                Instant instant = Instant.ofEpochMilli(timestamp);
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            }
        }

    }

    /**
     * 反序列化LocalTime
     */
    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

        PlatformJacksonProperties platformJacksonProperties;

        public LocalTimeDeserializer(PlatformJacksonProperties platformJacksonProperties) {
            this.platformJacksonProperties = platformJacksonProperties;
        }

        @Override
        public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            // get http servlet request
            HttpServletRequest request = HttpServletUtil.getRequest();
            // get request header timestamps
            String timestamps = request.getHeader(platformJacksonProperties.getTimestampsEnabledHeaderName());
            // get request header timeFormat
            String timeFormat = request.getHeader(platformJacksonProperties.getTimeFormatHeaderName());
            if (StrUtil.isNotBlank(timestamps) && PlatformJacksonConstant.DISABLE.equals(timestamps)) {
                log.debug("timestamps: {}", timestamps);
                if (StrUtil.isBlank(timeFormat)) {
                    timeFormat = platformJacksonProperties.getTimePattern();
                }
                String localDateTimeStr = jsonParser.getValueAsString();

                // if timeFormat is blank, set HH:mm
                if (StrUtil.isBlank(timeFormat)) {
                    timeFormat = getTimeFormat(localDateTimeStr);
                } else if (PlatformJacksonConstant.PATTERN_TIME.equals(timeFormat)
                        || PlatformJacksonConstant.PATTERN_TIMES.equals(timeFormat)) {
                    timeFormat = getTimeFormat(localDateTimeStr);
                }
                log.debug("dateFormat: {}", timeFormat);
                DateTimeFormatter format = DateTimeFormatter.ofPattern(timeFormat);
                // localTime parse
                return LocalTime.parse(localDateTimeStr, format);
            } else {
                long timestamp = jsonParser.getValueAsLong();
                Instant instant = Instant.ofEpochMilli(timestamp);
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            }
        }

    }

    private static String getTimeFormat(String localDateTimeStr) {
        String timeFormat;
        timeFormat = PlatformJacksonConstant.PATTERN_TIME;
        if (localDateTimeStr.length() == PlatformJacksonConstant.TIMES_LENGTH) {
            timeFormat = PlatformJacksonConstant.PATTERN_TIMES;
        }
        return timeFormat;
    }

}
