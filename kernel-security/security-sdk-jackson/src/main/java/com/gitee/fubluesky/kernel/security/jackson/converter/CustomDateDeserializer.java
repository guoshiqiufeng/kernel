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

import java.io.IOException;
import java.time.*;
import java.util.Date;

/**
 * 时间反序列化
 *
 * @author yanghq
 * @version 1.0
 * @since 2020-12-08 15:40
 */
public class CustomDateDeserializer {

	/**
	 * 反序列化LocalDate
	 */
	public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

		@Override
		public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException {
			long timestamp = jsonParser.getValueAsLong();
			Instant instant = Instant.ofEpochMilli(timestamp);
			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		}

	}

	/**
	 * 反序列化LocalDateTime
	 */
	public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

		@Override
		public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException {
			long timestamp = jsonParser.getValueAsLong();
			Instant instant = Instant.ofEpochMilli(timestamp);
			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		}

	}

	/**
	 * 反序列化LocalTime
	 */
	public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

		@Override
		public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException {
			long timestamp = jsonParser.getValueAsLong();
			Instant instant = Instant.ofEpochMilli(timestamp);
			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
		}

	}

	/**
	 * 反序列化Date
	 */
	public static class DateDeserializer extends JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt)
				throws IOException {
			long timestamp = jsonParser.getValueAsLong();
			return new Date(timestamp);
		}

	}

}
