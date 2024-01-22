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
package com.gitee.fubluesky.kernel.security.jackson.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.gitee.fubluesky.kernel.security.jackson.converter.CustomDateDeserializer;
import com.gitee.fubluesky.kernel.security.jackson.converter.CustomDateSerializer;
import com.gitee.fubluesky.kernel.security.jackson.pojo.PlatformJacksonProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-09 16:03
 */
public class TimestampsJavaTimeModule extends SimpleModule {

    public TimestampsJavaTimeModule(PlatformJacksonProperties platformJacksonProperties) {
        super(PackageVersion.VERSION);
        // LocalDateTime
        this.addSerializer(LocalDateTime.class,
                new CustomDateSerializer.LocalDateTimeSerializer(platformJacksonProperties));
        this.addDeserializer(LocalDateTime.class,
                new CustomDateDeserializer.LocalDateTimeDeserializer(platformJacksonProperties));
        // LocalDate
        this.addSerializer(LocalDate.class, new CustomDateSerializer.LocalDateSerializer(platformJacksonProperties));
        this.addDeserializer(LocalDate.class,
                new CustomDateDeserializer.LocalDateDeserializer(platformJacksonProperties));
        // LocalTime
        this.addSerializer(LocalTime.class, new CustomDateSerializer.LocalTimeSerializer(platformJacksonProperties));
        this.addDeserializer(LocalTime.class,
                new CustomDateDeserializer.LocalTimeDeserializer(platformJacksonProperties));
    }

}
