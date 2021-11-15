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

package com.gitee.fubluesky.kernel.core.enums;

import lombok.Getter;

/**
 * 性别
 * @author yanghq
 * @version 1.0
 * @since 2021-07-21 13:48
 */
@Getter
public enum SexEnum {

	/**
	 * 男
	 */
	MALE("1", "男"),

	/**
	 * 女
	 */
	FEMALE("2", "女");

	private final String code;

	private final String describe;

	SexEnum(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}

	public static SexEnum getEnum(String code) {
		if (null != code) {
			for (SexEnum sexEnum : SexEnum.values()) {
				if (sexEnum.getCode().equals(code)) {
					return sexEnum;
				}
			}
		}
		return null;
	}

	public static String codeToDescribe(String code) {
		if (null != code) {
			for (SexEnum sexEnum : SexEnum.values()) {
				if (sexEnum.getCode().equals(code)) {
					return sexEnum.getDescribe();
				}
			}
		}
		return "未知";
	}

}
