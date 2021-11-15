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

package com.gitee.fubluesky.kernel.cache.api;

import java.util.Collection;
import java.util.Map;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-05 14:00
 */
public interface CacheOperatorApi<T> {

	/**
	 * 添加
	 * @param key 键
	 * @param value 值
	 */
	void add(String key, T value);

	/**
	 * 添加
	 * @param key 键
	 * @param value 值
	 * @param expire 过期时间，单位秒
	 */
	void add(String key, T value, Long expire);

	/**
	 * 获取
	 * @param key 键
	 * @return 值
	 */
	T get(String key);

	/**
	 * 删除
	 * @param keys 键
	 */
	void delete(String... keys);

	/**
	 * 设置过期时间
	 * @param key 键
	 * @param expire 过期时间，单位秒
	 */
	void expire(String key, Long expire);

	/**
	 * 判断是否存在
	 * @param key 键
	 * @return true 存在, false 不存在
	 */
	boolean contains(String key);

	/**
	 * 获取所有key
	 * @return keys
	 */
	Collection<String> getAllKeys();

	/**
	 * 获取prefix开头的所有key
	 * @param prefix 前缀
	 * @return keys
	 */
	Collection<String> getAllKeys(String prefix);

	/**
	 * 获得缓存的所有值
	 * @return 值
	 */
	Collection<T> getAllValues();

	/**
	 * 获取所有的key，value
	 * @return 集合
	 */
	Map<String, T> getAllKeyValues();

	/**
	 * 获取缓存的前缀
	 * @return 缓存前缀
	 */
	String getKeyPrefix();

}
