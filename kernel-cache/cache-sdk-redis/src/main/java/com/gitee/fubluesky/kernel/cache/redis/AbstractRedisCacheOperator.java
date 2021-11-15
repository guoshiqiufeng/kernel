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

package com.gitee.fubluesky.kernel.cache.redis;

import com.gitee.fubluesky.kernel.cache.api.CacheOperatorApi;
import com.gitee.fubluesky.kernel.core.util.StrUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 缓存封装
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-05 14:43
 */
public abstract class AbstractRedisCacheOperator<T> implements CacheOperatorApi<T> {

	private final RedisTemplate<String, T> redisTemplate;

	public AbstractRedisCacheOperator(RedisTemplate<String, T> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 添加
	 * @param key 键
	 * @param value 值
	 */
	@Override
	public void add(String key, T value) {
		redisTemplate.boundValueOps(getKeyPrefix() + getKey(key)).set(value);
	}

	/**
	 * 添加
	 * @param key 键
	 * @param value 值
	 * @param expire 过期时间，单位秒
	 */
	@Override
	public void add(String key, T value, Long expire) {
		redisTemplate.boundValueOps(getKeyPrefix() + getKey(key)).set(value, expire, TimeUnit.SECONDS);
	}

	/**
	 * 获取
	 * @param key 键
	 * @return 值
	 */
	@Override
	public T get(String key) {
		return redisTemplate.boundValueOps(getKeyPrefix() + getKey(key)).get();
	}

	/**
	 * 删除
	 * @param keys 键
	 */
	@Override
	public void delete(String... keys) {
		List<String> keyList = Arrays.asList(keys);
		List<String> withPrefixKeys = keyList.stream().map(i -> getKeyPrefix() + getKey(i))
				.collect(Collectors.toList());
		redisTemplate.delete(withPrefixKeys);
	}

	/**
	 * 设置过期时间
	 * @param key 键
	 * @param expire 过期时间，单位秒
	 */
	@Override
	public void expire(String key, Long expire) {
		redisTemplate.boundValueOps(getKeyPrefix() + getKey(key)).expire(expire, TimeUnit.SECONDS);
	}

	/**
	 * 判断是否存在
	 * @param key 键
	 * @return true 存在, false 不存在
	 */
	@Override
	public boolean contains(String key) {
		T value = redisTemplate.boundValueOps(getKeyPrefix() + getKey(key)).get();
		return value != null;
	}

	/**
	 * 获取所有key
	 * @return keys
	 */
	@Override
	public Collection<String> getAllKeys() {
		return getAllKeys("");
	}

	/**
	 * 获取prefix开头的所有key
	 * @param prefix 前缀
	 * @return keys
	 */
	@Override
	public Collection<String> getAllKeys(String prefix) {
		Set<String> keys = redisTemplate.keys(getKeyPrefix() + prefix + "*");
		if (keys != null) {
			// 去掉缓存key的prefix前缀
			return keys.stream().map(key -> StrUtils.removePrefix(getKey(key), getKeyPrefix()))
					.collect(Collectors.toSet());
		}
		else {
			return Sets.newHashSet();
		}
	}

	/**
	 * 获得缓存的所有值
	 * @return 值
	 */
	@Override
	public Collection<T> getAllValues() {
		Set<String> keys = redisTemplate.keys(getKeyPrefix() + "*");
		if (keys != null) {
			return redisTemplate.opsForValue().multiGet(keys);
		}
		else {
			return Lists.newArrayList();
		}
	}

	/**
	 * 获取所有的key，value
	 * @return 集合
	 */
	@Override
	public Map<String, T> getAllKeyValues() {
		Collection<String> allKeys = this.getAllKeys();
		HashMap<String, T> results = Maps.newHashMap();
		for (String key : allKeys) {
			key = getKey(key);
			results.put(key, this.get(key));
		}
		return results;
	}

	@Override
	public String getKey(String key) {
		return key;
	}

}
