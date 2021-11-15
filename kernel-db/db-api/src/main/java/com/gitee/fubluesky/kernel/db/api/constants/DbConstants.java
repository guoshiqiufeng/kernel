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

package com.gitee.fubluesky.kernel.db.api.constants;

/**
 * 数据库模块常量
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 9:02
 */
public interface DbConstants {

	/**
	 * db模块的名称
	 */
	String DB_MODULE_NAME = "kernel-db";

	/**
	 * 异常枚举的步进值
	 */
	String DB_EXCEPTION_STEP_CODE = "02";

	/**
	 * 要搜索类型别名的包
	 */
	String TYPE_ALIASES_PACKAGE = "com.gitee.fubluesky.hull.*.*.domain";

	/**
	 * 逻辑删除字段
	 */
	String LOGIC_DELETE_FIELD = "isDeleted";

}
