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
package com.gitee.fubluesky.kernel.auth.api.pojo;

import lombok.Data;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-12 14:37
 */
@Data
public class AuthProperties {

	/**
	 * token 前缀
	 */
	private String tokenPrefix = "Bearer ";

	/**
	 * token param
	 */
	private String tokenParamName = "token";

	/**
	 * token header
	 */
	private String tokenHeaderName = "Authorization";

	/**
	 * token cookie
	 */
	private String tokenCookieName = "Authorization";

}