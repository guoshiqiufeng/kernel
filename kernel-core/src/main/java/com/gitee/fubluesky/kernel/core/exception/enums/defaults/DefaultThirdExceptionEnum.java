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

package com.gitee.fubluesky.kernel.core.exception.enums.defaults;

import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import lombok.Getter;

import static com.gitee.fubluesky.kernel.core.constants.CoreConstants.FIRST_LEVEL_WIDE_CODE;
import static com.gitee.fubluesky.kernel.core.constants.CoreConstants.THIRD_ERROR_TYPE_CODE;

/**
 * 第三方服务错误
 * @author yanghq
 * @version 1.0
 * @since 2021-07-21 14:51
 */
@Getter
public enum DefaultThirdExceptionEnum implements AbstractExceptionEnum {

	/**
	 * 调用第三方服务出错（一级宏观错误码）
	 */
	THIRD_PARTY_ERROR(THIRD_ERROR_TYPE_CODE, FIRST_LEVEL_WIDE_CODE, "第三方调用出现错误");

	/**
	 * 异常分类 用户端异常: 1 业务异常: 2 第三方异常: 3
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

	DefaultThirdExceptionEnum(String typeCode, String code, String message) {
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
