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

package com.gitee.fubluesky.kernel.security.sign.utils;

import com.gitee.fubluesky.kernel.security.api.exception.SecurityException;
import com.gitee.fubluesky.kernel.security.api.exception.enums.SecurityExceptionEnum;
import com.gitee.fubluesky.kernel.security.sign.annotation.Sign;
import com.gitee.fubluesky.kernel.security.sign.pojo.SignProperties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-12 10:23
 */
@Slf4j
@UtilityClass
public class SignUtils {

	/**
	 * 判断是否需要签名验证 类上带有 @Sign(false) 注解 则不需要验证 方法上带有 @Sign(false) 注解 则不需要验证
	 * @param methodParameter 方法参数
	 * @return true 需要签名验证 false不需要
	 */
	public boolean needValidateSign(MethodParameter methodParameter) {
		boolean encrypt = true;
		boolean classPresentAnnotate = methodParameter.getContainingClass().isAnnotationPresent(Sign.class);
		Method method = methodParameter.getMethod();

		if (classPresentAnnotate) {
			// 类上标注的是否需要加密
			encrypt = methodParameter.getContainingClass().getAnnotation(Sign.class).value();
			// 类不加密，所有都不加密
			if (!encrypt) {
				return false;
			}
		}
		if (method != null) {
			boolean methodPresentAnnotate = method.isAnnotationPresent(Sign.class);
			if (methodPresentAnnotate) {
				// 方法上标注的是否需要加密
				encrypt = method.getAnnotation(Sign.class).value();
			}
		}
		return encrypt;
	}

	/**
	 * 获取签名主体
	 * @param content 内容
	 * @param signProperties 配置文件
	 * @return 签名主体
	 */
	public String getSignBody(String content, String appId, SignProperties signProperties) {
		if (!signProperties.getUsedMultiSalt()) {
			return content + signProperties.getSalt();
		}
		Map<String, String> saltMap = signProperties.getSaltMap();
		if (saltMap == null || saltMap.isEmpty()) {
			throw new SecurityException(SecurityExceptionEnum.SIGN_APP_ID_ERROR);
		}
		String salt = saltMap.get(appId);
		if (StringUtils.isBlank(salt)) {
			log.error("appId:{}, salt is not found in saltMap", appId);
			throw new SecurityException(SecurityExceptionEnum.SIGN_ERROR);
		}
		return content + salt;
	}

}
