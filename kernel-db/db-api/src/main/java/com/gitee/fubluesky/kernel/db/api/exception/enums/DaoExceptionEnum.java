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

package com.gitee.fubluesky.kernel.db.api.exception.enums;

import com.gitee.fubluesky.kernel.core.constants.CoreConstants;
import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.kernel.db.api.constants.DbConstants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 10:30
 */
public enum DaoExceptionEnum implements AbstractExceptionEnum {

	/**
	 * 包含非法字符
	 */
	ILLEGAL_CHARACTERS_ERROR(CoreConstants.BUSINESS_ERROR_TYPE_CODE, DbConstants.DB_EXCEPTION_STEP_CODE + "01",
			"包含非法字符"),

	/**
	 * 数据不存在
	 */
	DATA_NONE_ERROR(CoreConstants.BUSINESS_ERROR_TYPE_CODE, DbConstants.DB_EXCEPTION_STEP_CODE + "02", "数据不存在");

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

	DaoExceptionEnum(String typeCode, String code, String message) {
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