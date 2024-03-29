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
package com.gitee.fubluesky.kernel.security.jackson.constant;

import java.time.format.DateTimeFormatter;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-08 15:52
 */
public interface PlatformJacksonConstant {

    /**
     * 禁用
     */
    String DISABLE = "false";

    /**
     * 日期时间 格式
     */
    String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期 格式
     */
    String PATTERN_DATE = "yyyy-MM-dd";

    /**
     * 时间格式
     */
    String PATTERN_TIME = "HH:mm";

    String PATTERN_TIMES = "HH:mm:ss";

    int TIMES_LENGTH = 8;

    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(PlatformJacksonConstant.PATTERN_DATE);

    DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern(PlatformJacksonConstant.PATTERN_TIME);

}
