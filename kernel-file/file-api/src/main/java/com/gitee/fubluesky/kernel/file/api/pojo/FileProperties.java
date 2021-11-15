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

package com.gitee.fubluesky.kernel.file.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-30 14:54
 */
@Data
public class FileProperties implements Serializable {

	private static final long serialVersionUID = 5894163653727901822L;

	/**
	 * 域名
	 */
	private String domain;

	/**
	 * 路径前缀
	 */
	private String prefix;

	/**
	 * 是否使用时间规则+uuid命名文件
	 */
	private Boolean datePathEnabled = true;

}
