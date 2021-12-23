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

package com.gitee.fubluesky.kernel.security.jackson.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-08 15:40
 */
@Data
public class PlatformJacksonProperties implements Serializable {

	/**
	 * 是否启用
	 */
	private Boolean enabled = true;

	/**
	 * 是否启用时间戳序列化
	 */
	private Boolean timestampsEnabled = true;

	/**
	 * 是否启用时间戳序列化 请求头 字段名 只有在启用 timestampsEnabled 才会 有用
	 */
	private String timestampsEnabledHeaderName = "timestamps";

	/**
	 * 时间序列化 反序列化 格式化 规则 请求头 字段名 只有在 timestampsEnabledHeaderName 对应的 值 为 false 时才会启用
	 */
	private String dateFormatHeaderName = "dateFormat";

}
