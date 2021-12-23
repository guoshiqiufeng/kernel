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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
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
				// localDate format
				String date = format.format(localDate);
				jsonGenerator.writeString(date);
			}
			else if (localDate != null) {
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
				// localDateTime format
				String date = format.format(localDateTime);
				jsonGenerator.writeString(date);
			}
			else if (localDateTime != null) {
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
				// localTime format
				String date = format.format(localTime);
				jsonGenerator.writeString(date);
			}
			else if (localTime != null) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = localTime.atDate(LocalDate.now()).atZone(zone).toInstant();
				jsonGenerator.writeNumber(instant.toEpochMilli());
			}
		}

	}

}
