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

package com.gitee.fubluesky.kernel.core.constants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-21 10:46
 */
public interface CoreConstants {

	/**
	 * 用户端操作异常的错误码分类编号
	 */
	String USER_OPERATION_ERROR_TYPE_CODE = "1";

	/**
	 * 业务执行异常的错误码分类编号
	 */
	String BUSINESS_ERROR_TYPE_CODE = "2";

	/**
	 * 第三方调用异常的错误码分类编号
	 */
	String THIRD_ERROR_TYPE_CODE = "3";

	/**
	 * 一级宏观码标识，宏观码标识代表一类错误码的统称
	 */
	String FIRST_LEVEL_WIDE_CODE = "0001";

	/**
	 * 异常枚举的步进值
	 */
	String CORE_EXCEPTION_STEP_CODE = "01";

	/**
	 * 规则模块的名称
	 */
	String CORE_MODULE_NAME = "kernel-core";

	String FLAG_TRUE = "true";

	String FLAG_FALSE = "false";

	/**
	 * 冒号
	 */
	String COLON = ":";

}