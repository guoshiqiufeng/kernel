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

import java.io.IOException;
import java.time.*;
import java.util.Date;

/**
 * 时间序列化
 *
 * @author yanghq
 * @version 1.0
 * @since 2020-12-08 15:40
 */
public class CustomDateSerializer {

	public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

		@Override
		public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException {
			if (localDate != null) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = localDate.atStartOfDay(zone).toInstant();
				jsonGenerator.writeNumber(instant.toEpochMilli());
			}
		}

	}

	public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

		@Override
		public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
				SerializerProvider serializerProvider) throws IOException {
			if (localDateTime != null) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = localDateTime.atZone(zone).toInstant();
				jsonGenerator.writeNumber(instant.toEpochMilli());
			}
		}

	}

	public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {

		@Override
		public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException {
			if (localTime != null) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = localTime.atDate(LocalDate.now()).atZone(zone).toInstant();
				jsonGenerator.writeNumber(instant.toEpochMilli());
			}
		}

	}

	public static class DateSerializer extends JsonSerializer<Date> {

		@Override
		public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializers)
				throws IOException {
			if (value != null) {
				jsonGenerator.writeNumber(value.getTime());
			}
		}

	}

}
