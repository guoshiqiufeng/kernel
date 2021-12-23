/*
 * Copyright 2020-2021 fubluesky.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitee.fubluesky.kernel.security.jackson.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.gitee.fubluesky.kernel.core.util.HttpServletUtil;
import com.gitee.fubluesky.kernel.security.jackson.constant.PlatformJacksonConstant;
import com.gitee.fubluesky.kernel.security.jackson.pojo.PlatformJacksonProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
			if (StringUtils.isNotBlank(timestamps) && PlatformJacksonConstant.DISABLE.equals(timestamps)) {
				log.debug("timestamps: {}", timestamps);
				// if dateFormat is blank, set yyyy-MM-dd
				if (StringUtils.isBlank(dateFormat)) {
					dateFormat = PlatformJacksonConstant.PATTERN_DATE;
				}
				log.debug("dateFormat: {}", dateFormat);
				DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
				// localDate parse
				String localDateStr = jsonParser.getValueAsString();
				return LocalDate.parse(localDateStr, format);
			}
			else {
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
			String dateFormat = request.getHeader(platformJacksonProperties.getDateFormatHeaderName());
			if (StringUtils.isNotBlank(timestamps) && PlatformJacksonConstant.DISABLE.equals(timestamps)) {
				log.debug("timestamps: {}", timestamps);
				// if dateFormat is blank, set yyyy-MM-dd HH:mm:ss
				if (StringUtils.isBlank(dateFormat)) {
					dateFormat = PlatformJacksonConstant.PATTERN_DATETIME;
				}
				log.debug("dateFormat: {}", dateFormat);
				DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
				// localDateTime parse
				String localDateTimeStr = jsonParser.getValueAsString();
				return LocalDateTime.parse(localDateTimeStr, format);
			}
			else {
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
			// get request header dateFormat
			String dateFormat = request.getHeader(platformJacksonProperties.getDateFormatHeaderName());
			if (StringUtils.isNotBlank(timestamps) && PlatformJacksonConstant.DISABLE.equals(timestamps)) {
				log.debug("timestamps: {}", timestamps);
				// if dateFormat is blank, set HH:mm
				if (StringUtils.isBlank(dateFormat)) {
					dateFormat = PlatformJacksonConstant.PATTERN_TIME;
				}
				log.debug("dateFormat: {}", dateFormat);
				DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
				// localDateTime parse
				String localDateTimeStr = jsonParser.getValueAsString();
				return LocalTime.parse(localDateTimeStr, format);
			}
			else {
				long timestamp = jsonParser.getValueAsLong();
				Instant instant = Instant.ofEpochMilli(timestamp);
				return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
			}
		}

	}

}
