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

package com.gitee.fubluesky.kernel.core.exception.enums.http;

import com.gitee.fubluesky.kernel.core.enums.ErrorType;
import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import lombok.Getter;

import static com.gitee.fubluesky.kernel.core.constants.CoreConstants.EXCEPTION_STEP_CODE;

/**
 * servlet异常
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 15:51
 */
@Getter
public enum ServletExceptionEnum implements AbstractExceptionEnum {

	/**
	 * 获取不到http context异常
	 */
	HTTP_CONTEXT_ERROR(ErrorType.BUSINESS_ERROR.getCode(), EXCEPTION_STEP_CODE + "01", "获取不到http context"),

	/**
	 * 404
	 */
	NOT_FOUND_ERROR(ErrorType.EMPTY.getCode(), "404", "接口地址无效");

	/**
	 * 异常分类  {@link ErrorType}
	 * 用户端异常: 1
	 * 业务异常: 2
	 * 第三方异常: 3
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

	ServletExceptionEnum(String typeCode, String code, String message) {
		this.typeCode = typeCode;
		this.code = code;
		this.message = message;
	}

	/**
	 * 获取异常的状态码
	 * @return 状态码
	 */
	@Override
	public String getCode() {
		return typeCode + code;
	}

	/**
	 * 获取异常的消息
	 * @return 异常消息
	 */
	@Override
	public String getMessage() {
		return message;
	}

}
