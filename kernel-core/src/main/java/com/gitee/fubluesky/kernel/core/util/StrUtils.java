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

package com.gitee.fubluesky.kernel.core.util;

import lombok.experimental.UtilityClass;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-06 9:43
 */
@UtilityClass
public class StrUtils {

	/**
	 * 字符串是否 为null、为""
	 * @param str 被检测的字符串
	 * @return 是否为空
	 */
	public boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 去掉前缀
	 * @param str 字符串
	 * @param prefix 前缀
	 * @return 切掉后的字符串，若前缀不是 prefix， 返回原字符串
	 */
	public String removePrefix(CharSequence str, CharSequence prefix) {
		if (isEmpty(str) || isEmpty(prefix)) {
			return str(str);
		}

		final String str2 = str.toString();
		if (str2.startsWith(prefix.toString())) {
			// 截取后半段
			return subSuf(str2, prefix.length());
		}
		return str2;
	}

	/**
	 * 切割指定位置之后部分的字符串
	 * @param string 字符串
	 * @param fromIndex 切割开始的位置（包括）
	 * @return 切割后后剩余的后半部分字符串
	 */
	public String subSuf(CharSequence string, int fromIndex) {
		if (isEmpty(string)) {
			return null;
		}
		return string.subSequence(fromIndex, string.length()).toString();
	}

	/**
	 * {@link CharSequence} 转为字符串，null安全
	 * @param cs {@link CharSequence}
	 * @return 字符串
	 */
	public String str(CharSequence cs) {
		return null == cs ? null : cs.toString();
	}

}
