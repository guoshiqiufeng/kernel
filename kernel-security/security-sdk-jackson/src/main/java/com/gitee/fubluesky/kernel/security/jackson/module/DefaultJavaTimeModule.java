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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-09 16:03
 */
public class DefaultJavaTimeModule extends SimpleModule {

	public DefaultJavaTimeModule() {
		super(PackageVersion.VERSION);
		// LocalDateTime
		this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(PlatformJacksonConstant.DATETIME_FORMAT));
		this.addDeserializer(LocalDateTime.class,
				new LocalDateTimeDeserializer(PlatformJacksonConstant.DATETIME_FORMAT));
		// LocalDate
		this.addSerializer(LocalDate.class, new LocalDateSerializer(PlatformJacksonConstant.DATE_FORMAT));
		this.addDeserializer(LocalDate.class, new LocalDateDeserializer(PlatformJacksonConstant.DATE_FORMAT));
		// LocalTime
		this.addSerializer(LocalTime.class, new LocalTimeSerializer(PlatformJacksonConstant.TIME_FORMAT));
		this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(PlatformJacksonConstant.TIME_FORMAT));
	}

}
