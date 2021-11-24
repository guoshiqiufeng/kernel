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

package com.gitee.fubluesky.kernel.core.exception;

import com.gitee.fubluesky.kernel.core.enums.ErrorType;
import lombok.Getter;

/**
 * 请求基础常量
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 16:10
 */
@Getter
public enum ResponseEnum implements AbstractExceptionEnum {

	/**
	 * 成功
	 */
	SUCCESS(ErrorType.EMPTY.getCode(), "20000", "请求成功"),

	/**
	 * 失败
	 */
	FAILURE(ErrorType.EMPTY.getCode(), "-1", "失败");

	/**
	 * 异常分类 {@link ErrorType} 用户端异常: 1 业务异常: 2 第三方异常: 3
	 */
	private final String typeCode;

	/**
	 * 错误编码
	 */
	private final String code;

	/**
	 * 错误信息
	 */
	private final String message;

	ResponseEnum(String typeCode, String code, String message) {
		this.typeCode = typeCode;
		this.code = code;
		this.message = message;
	}

}
