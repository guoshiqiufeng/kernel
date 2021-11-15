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
package com.gitee.fubluesky.kernel.auth.api.constants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 9:47
 */
public interface AuthConstants {

	/**
	 * auth模块的名称
	 */
	String AUTH_MODULE_NAME = "kernel-auth";

	/**
	 * 异常枚举的步进值
	 */
	String AUTH_EXCEPTION_STEP_CODE = "11";

	/**
	 * 缓存前缀
	 */
	String CACHE_KEY_PREFIX = "AUTH_CACHE";

	String LOGIN_USER_PREFIX = "LOGIN_USER";

	String LOGIN_TOKEN_PREFIX = "LOGIN_TOKEN";

}
