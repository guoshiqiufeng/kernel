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

package com.gitee.fubluesky.kernel.excel.api.exception.enums;

import com.gitee.fubluesky.kernel.core.enums.ErrorType;
import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.kernel.excel.api.constants.ExcelConstants;
import lombok.Getter;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-18 15:36
 */
@Getter
public enum ExcelExceptionEnum implements AbstractExceptionEnum {

	/**
	 * Excel异常
	 */
	EXCEL_ERROR(ErrorType.USER_OPERATION_ERROR.getCode(), ExcelConstants.EXCEPTION_STEP_CODE + "01", "Excel异常"),

	/**
	 * Excel导出异常
	 */
	EXCEL_EXPORT_ERROR(ErrorType.USER_OPERATION_ERROR.getCode(), ExcelConstants.EXCEPTION_STEP_CODE + "02", "Excel导出异常"),

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

	ExcelExceptionEnum(String typeCode, String code, String message) {
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
