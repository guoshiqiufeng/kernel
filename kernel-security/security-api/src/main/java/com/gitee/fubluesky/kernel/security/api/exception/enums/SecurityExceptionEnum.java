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

package com.gitee.fubluesky.kernel.security.api.exception.enums;

import com.gitee.fubluesky.kernel.core.enums.ErrorType;
import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.kernel.security.api.constants.SecurityConstants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-09 15:00
 */
public enum SecurityExceptionEnum implements AbstractExceptionEnum {

	/**
	 * 无访问权限
	 */
	PERMISSION_NO_ACCESS(ErrorType.BUSINESS_ERROR.getCode(), SecurityConstants.EXCEPTION_STEP_CODE + "01", "无访问权限"),

	/**
	 * 签名有误
	 */
	SIGN_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SecurityConstants.EXCEPTION_STEP_CODE + "50", "签名有误"),

	/**
	 * appId有误
	 */
	SIGN_APP_ID_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SecurityConstants.EXCEPTION_STEP_CODE + "51", "appId有误"),

	;

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

	SecurityExceptionEnum(String typeCode, String code, String message) {
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
