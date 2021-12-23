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

package com.gitee.fubluesky.kernel.security.jackson.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.gitee.fubluesky.kernel.security.jackson.constant.PlatformJacksonConstant;
import com.gitee.fubluesky.kernel.security.jackson.pojo.PlatformJacksonProperties;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-09 16:03
 */
public class DefaultJavaTimeModule extends SimpleModule {

	public DefaultJavaTimeModule(PlatformJacksonProperties platformJacksonProperties) {
		super(PackageVersion.VERSION);
		// LocalDateTime
		String dateTimePattern = platformJacksonProperties.getDateTimePattern();
		if (StringUtils.isBlank(dateTimePattern)) {
			dateTimePattern = PlatformJacksonConstant.PATTERN_DATETIME;
		}
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(dateTimePattern);
		this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormat));
		this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormat));
		// LocalDate
		String datePattern = platformJacksonProperties.getDatePattern();
		if (StringUtils.isBlank(datePattern)) {
			datePattern = PlatformJacksonConstant.PATTERN_DATE;
		}
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(datePattern);
		this.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormat));
		this.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormat));
		// LocalTime
		String timePattern = platformJacksonProperties.getTimePattern();
		if (StringUtils.isBlank(timePattern)) {
			timePattern = PlatformJacksonConstant.PATTERN_TIME;
		}
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(timePattern);
		this.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormat));
		this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormat));
	}

}
