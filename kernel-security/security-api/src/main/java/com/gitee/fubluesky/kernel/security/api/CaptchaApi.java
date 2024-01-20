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

package com.gitee.fubluesky.kernel.security.api;

import com.gitee.fubluesky.kernel.security.api.pojo.Captcha;

/**
 * 验证码接口
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-10 9:42
 */
public interface CaptchaApi {

	/**
	 * 生成图形验证码
	 * @return 验证码对象
	 */
	Captcha captcha();

	/**
	 * 校验图形验证码
	 * @param key 缓存Key
	 * @param code 验证码
	 * @return 验证码是否正确
	 */
	boolean validateCaptcha(String key, String code);

	/**
	 * 根据key值获取验证码
	 * @param key 缓存Key
	 * @return 验证码
	 */
	String getCode(String key);

}
