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

package com.gitee.fubluesky.kernel.security.crypto;

import com.gitee.fubluesky.kernel.core.util.CharsetUtil;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-10 17:06
 */
public interface Crypto {

	/**
	 * 加密
	 * @param src 原字符串
	 * @param charset 字符编码
	 * @return 加密后的
	 */
	String encrypt(String src, String charset);

	/**
	 * 加密
	 * @param src 原字符串
	 * @return 加密后的
	 */
	default String encrypt(String src) {
		return encrypt(src, CharsetUtil.CHARSET_UTF_8.name());
	}

	/**
	 * 解密
	 * @param src 原字符串
	 * @param charset 返回的字符串编码
	 * @return 解密后的
	 */
	String decrypt(String src, String charset);

	/**
	 * 解密
	 * @param src 原字符串
	 * @return 解密后的
	 */
	default String decrypt(String src) {
		return decrypt(src, CharsetUtil.UTF_8);
	}

}
