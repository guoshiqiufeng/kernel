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

package com.gitee.fubluesky.kernel.jwt.api.pojo.config;

import lombok.Data;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 15:56
 */
@Data
public class JwtConfig {

	/**
	 * 是否启用
	 */
	private Boolean enabled = false;

	/**
	 * 密钥
	 */
	private String secret = "secret";

	/**
	 * token有效时长，单位秒
	 */
	private long expire = 1800;

	/**
	 * 缓存刷新时间（单位天）
	 */
	private int refresh = 1;

}
