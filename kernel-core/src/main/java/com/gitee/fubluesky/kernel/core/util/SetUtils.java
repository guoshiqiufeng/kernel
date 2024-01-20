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
package com.gitee.fubluesky.kernel.core.util;

import cn.hutool.core.collection.CollUtil;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * set工具类
 *
 * @author yanghq
 * @version 1.0
 * @since 2023/3/29 16:48
 */
@UtilityClass
public class SetUtils {

    /**
     * 将集合转换为另一种类型的集合
     *
     * @param source 源集合
     * @param mapper 转换函数
     * @param <T>    源集合元素类型
     * @param <U>    目标集合元素类型
     * @return 目标集合
     */
    public static <T, U> Set<U> toSet(Collection<T> source, Function<T, U> mapper) {
        if (CollUtil.isEmpty(source)) {
            return new HashSet<>();
        }
        return source.stream().map(mapper).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 将集合转换为另一种类型的集合，并过滤
     *
     * @param source    源集合
     * @param mapper    转换函数
     * @param predicate 过滤函数
     * @param <T>       源集合元素类型
     * @param <U>       目标集合元素类型
     * @return 目标集合
     */
    public static <T, U> Set<U> toSet(Collection<T> source, Function<T, U> mapper, Predicate<U> predicate) {
        if (CollUtil.isEmpty(source)) {
            return new HashSet<>();
        }
        return source.stream().map(mapper).filter(Objects::nonNull).filter(predicate).collect(Collectors.toSet());
    }

}
