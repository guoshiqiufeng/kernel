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

package com.gitee.fubluesky.kernel.cache.api.constants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-26 17:33
 */
public interface CacheConstants {

	/**
	 * 缓存模块的名称
	 */
	String MODULE_NAME = "kernel-cache";

	/**
	 * 缓存模块的异常步进值
	 */
	String EXCEPTION_STEP_CODE = "10";

	/**
	 * 默认object对象缓存的缓存前缀
	 */
	String OBJECT_CACHE_PREFIX = "object:";

	/**
	 * 默认String对象缓存的缓存前缀
	 */
	String STRING_CACHE_PREFIX = "string:";

}
