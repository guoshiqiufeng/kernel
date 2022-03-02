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

package com.gitee.fubluesky.kernel.core.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/**
 * LocalDateTime 工具类
 * @author yanghq
 * @version 1.0
 * @since 2022-03-02 13:11
 */
@UtilityClass
public class LocalDateTimeUtils {

    /**
     * 获取年开始时间
     * @param date 时间
     * @return 年开始时间
     */
    public LocalDateTime beginOfYear(LocalDateTime date) {
        return LocalDateTime.of(LocalDate.from(date.with(TemporalAdjusters.firstDayOfYear())), LocalTime.MIN);
    }


    /**
     * 获取年结束时间
     * @param date 时间
     * @return 年结束时间
     */
    public LocalDateTime endOfYear(LocalDateTime date) {
        return LocalDateTime.of(LocalDate.from(date.with(TemporalAdjusters.lastDayOfYear())), LocalTime.MAX);
    }

    /**
     * 获取月开始时间
     * @param date 时间
     * @return 月开始时间
     */
    public static LocalDateTime beginOfMonth(LocalDateTime date) {
        return LocalDateTime.of(LocalDate.from(date.with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    /**
     * 获取月结束时间
     * @param date 时间
     * @return 月结束时间
     */
    public static LocalDateTime endOfMonth(LocalDateTime date) {
        return LocalDateTime.of(LocalDate.from(date.with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }

    /**
     * 获取日开始时间
     * @param date 时间
     * @return 日开始时间
     */
    public static LocalDateTime beginOfDay(LocalDateTime date) {
        return date.with(LocalTime.MIN);
    }

    /**
     * 获取日结束时间
     * @param date 时间
     * @return 日结束时间
     */
    public static LocalDateTime endOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }
}
