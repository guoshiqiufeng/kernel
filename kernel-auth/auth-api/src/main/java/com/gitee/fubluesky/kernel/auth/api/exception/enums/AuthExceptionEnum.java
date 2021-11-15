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
package com.gitee.fubluesky.kernel.auth.api.exception.enums;

import com.gitee.fubluesky.kernel.auth.api.constants.AuthConstants;
import com.gitee.fubluesky.kernel.core.constants.CoreConstants;
import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 9:53
 */
public enum AuthExceptionEnum implements AbstractExceptionEnum {

	/**
	 * 登录失效
	 */
	AUTH_EXPIRED_ERROR(CoreConstants.BUSINESS_ERROR_TYPE_CODE, AuthConstants.AUTH_EXCEPTION_STEP_CODE + "01",
			"当前登录失效，请重新登录"),

	/**
	 * 刷新token
	 */
	AUTH_TOKEN_ERROR(CoreConstants.BUSINESS_ERROR_TYPE_CODE, AuthConstants.AUTH_EXCEPTION_STEP_CODE + "02",
			"token有误，请刷新token"),

	/**
	 * 账号或密码为空
	 */
	USERNAME_PASSWORD_EMPTY(CoreConstants.USER_OPERATION_ERROR_TYPE_CODE, AuthConstants.AUTH_EXCEPTION_STEP_CODE + "03",
			"账号或密码为空"),

	/**
	 * 账号或密码错误
	 */
	USERNAME_PASSWORD_ERROR(CoreConstants.USER_OPERATION_ERROR_TYPE_CODE, AuthConstants.AUTH_EXCEPTION_STEP_CODE + "04",
			"账号或密码错误"),

	/**
	 * 验证码为空
	 */
	CAPTCHA_EMPTY(CoreConstants.USER_OPERATION_ERROR_TYPE_CODE, AuthConstants.AUTH_EXCEPTION_STEP_CODE + "05",
			"验证码不能为空"),

	/**
	 * 验证码有误
	 */
	CAPTCHA_ERROR(CoreConstants.USER_OPERATION_ERROR_TYPE_CODE, AuthConstants.AUTH_EXCEPTION_STEP_CODE + "06",
			"验证码有误"),;

	/**
	 * 异常分类 用户端异常: A 业务异常: B 第三方异常: C
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

	AuthExceptionEnum(String typeCode, String code, String message) {
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
