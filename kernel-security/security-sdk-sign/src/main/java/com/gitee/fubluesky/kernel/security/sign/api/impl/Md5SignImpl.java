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

package com.gitee.fubluesky.kernel.security.sign.api.impl;

import cn.hutool.core.util.StrUtil;
import com.gitee.fubluesky.kernel.security.api.exception.SecurityException;
import com.gitee.fubluesky.kernel.security.api.exception.enums.SecurityExceptionEnum;
import com.gitee.fubluesky.kernel.security.sign.api.SignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-11 15:13
 */
@Slf4j
public class Md5SignImpl implements SignApi {

	@Override
	public void validateSign(String sign, String body) {
		if (StrUtil.isEmpty(sign) || StrUtil.isEmpty(body)) {
			throw new SecurityException(SecurityExceptionEnum.SIGN_ERROR);
		}
		String digest = DigestUtils.md5DigestAsHex(body.getBytes());
		log.debug("md5: {}", digest);
		if (!sign.equals(digest)) {
			throw new SecurityException(SecurityExceptionEnum.SIGN_ERROR);
		}
	}

}
