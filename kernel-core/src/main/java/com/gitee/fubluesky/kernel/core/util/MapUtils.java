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
import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * map工具类
 *
 * @author yanghq
 * @version 1.0
 * @since 2023/3/28 15:20
 */
@UtilityClass
public class MapUtils {

    /**
     * 将集合转换为以提供的键提取器函数生成键的Map。
     *
     * @param source    要转换的源集合
     * @param keyMapper 用于从源对象中提取键的函数
     * @return 转换后的Map
     */
    public <T, K> Map<K, T> toMap(Collection<T> source, Function<T, K> keyMapper) {
        if (CollUtil.isEmpty(source)) {
            return Maps.newHashMap();
        }
        // 使用toMap方法将源集合转换为以键为主键的Map
        return toMap(source, keyMapper, Function.identity());
    }

    /**
     * 将集合转换为包含指定键和值的Map。
     *
     * @param source      要转换的源集合
     * @param keyMapper   用于从源对象中提取键的函数
     * @param valueMapper 用于从源对象中提取值的函数
     * @return 转换后的Map
     */
    public <T, K, V> Map<K, V> toMap(Collection<T> source, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        if (CollUtil.isEmpty(source)) {
            return Maps.newHashMap();
        }
        // 使用toMap方法将源集合转换为以键为主键、值为副键的Map
        return toMap(source, keyMapper, valueMapper, (v1, v2) -> v1);
    }

    /**
     * 将集合转换为包含指定键和值的Map，并在重复键时使用提供的合并函数进行合并。
     *
     * @param source        要转换的源集合
     * @param keyMapper     用于从源对象中提取键的函数
     * @param valueMapper   用于从源对象中提取值的函数
     * @param mergeFunction 在重复键时使用的合并函数
     * @return 转换后的Map
     */
    public <T, K, V> Map<K, V> toMap(Collection<T> source, Function<T, K> keyMapper, Function<T, V> valueMapper,
                                     BinaryOperator<V> mergeFunction) {
        if (CollUtil.isEmpty(source)) {
            return Maps.newHashMap();
        }
        // 使用toMap方法将源集合转换为以键为主键、值为副键的Map，并在重复键时使用提供的合并函数进行合并
        return toMap(source, keyMapper, valueMapper, mergeFunction, HashMap::new);
    }

    /**
     * 将集合转换为Map。
     *
     * @param source        要转换的源集合
     * @param keyMapper     用于从源对象中提取键的函数
     * @param valueMapper   用于从源对象中提取值的函数
     * @param mergeFunction 在重复键时使用的合并函数
     * @param mapFactory    提供新Map实例的函数
     * @return 转换后的Map
     */
    public <T, K, V> Map<K, V> toMap(Collection<T> source, Function<T, K> keyMapper, Function<T, V> valueMapper,
                                     BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> mapFactory) {
        if (CollUtil.isEmpty(source)) {
            return Maps.newHashMap();
        }
        // 使用Stream API将源集合转换为Map
        return source.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapFactory));
    }

    /**
     * 将集合转换为以提供的键提取器函数生成键的Map。
     *
     * @param source    要转换的源集合
     * @param keyMapper 用于从源对象中提取键的函数
     * @param <T>       源对象类型
     * @param <K>       键类型
     * @return 转换后的Map
     */
    public static <T, K> Map<K, List<T>> toMultiMap(Collection<T> source, Function<T, K> keyMapper) {
        if (CollUtil.isEmpty(source)) {
            return Maps.newHashMap();
        }
        return toMultiMap(source, keyMapper, t -> t);
    }

    /**
     * 将集合转换为以提供的键提取器函数生成键的Map。
     *
     * @param source      要转换的源集合
     * @param keyMapper   用于从源对象中提取键的函数
     * @param valueMapper 用于从源对象中提取值的函数
     * @param <T>         源对象类型
     * @param <K>         键类型
     * @param <V>         值类型
     * @return 转换后的Map
     */
    public static <T, K, V> Map<K, List<V>> toMultiMap(Collection<T> source, Function<T, K> keyMapper,
                                                       Function<T, V> valueMapper) {
        if (CollUtil.isEmpty(source)) {
            return Maps.newHashMap();
        }
        return source.stream()
                .collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

}
