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

package com.gitee.fubluesky.kernel.security.captcha;

import cn.hutool.core.util.StrUtil;
import com.gitee.fubluesky.kernel.cache.api.CacheOperatorApi;
import com.gitee.fubluesky.kernel.core.util.IdUtils;
import com.gitee.fubluesky.kernel.security.api.CaptchaApi;
import com.gitee.fubluesky.kernel.security.api.pojo.Captcha;
import com.wf.captcha.SpecCaptcha;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-10 10:23
 */
public class CaptchaService implements CaptchaApi {

	private final CacheOperatorApi<String> cacheOperatorApi;

	public CaptchaService(CacheOperatorApi<String> cacheOperatorApi) {
		this.cacheOperatorApi = cacheOperatorApi;
	}

	/**
	 * 生成图形验证码
	 * @return 验证码对象
	 */
	@Override
	public Captcha captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String code = specCaptcha.text().toLowerCase();
		String key = IdUtils.generateUuid();
		cacheOperatorApi.add(key, code);
		return new Captcha().setImage(specCaptcha.toBase64()).setKey(key);
	}

	/**
	 * 校验图形验证码
	 * @param key 缓存Key
	 * @param code 验证码
	 * @return 验证码是否正确
	 */
	@Override
	public boolean validateCaptcha(String key, String code) {
		if (StrUtil.isAllEmpty(key, code)) {
			return false;
		}

		if (!code.trim().toLowerCase().equals(cacheOperatorApi.get(key))) {
			return false;
		}

		// 删除缓存中验证码
		cacheOperatorApi.delete(key);

		return true;
	}

	/**
	 * 根据key值获取验证码
	 * @param key 缓存Key
	 * @return 验证码
	 */
	@Override
	public String getCode(String key) {
		return cacheOperatorApi.get(key);
	}

}
