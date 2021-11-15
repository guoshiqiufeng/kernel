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

package com.gitee.fubluesky.kernel.security.sign.advice;

import com.gitee.fubluesky.kernel.security.sign.api.SignApi;
import com.gitee.fubluesky.kernel.security.sign.pojo.SignProperties;
import com.gitee.fubluesky.kernel.security.sign.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-11 17:23
 */
@Slf4j
@ControllerAdvice
public class SignRequestBodyAdvice extends RequestBodyAdviceAdapter {

	@Autowired
	private SignProperties signProperties;

	@Autowired
	private SignApi signApi;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		// 配置未开启签名
		if (!signProperties.getEnabled()) {
			log.debug("signProperties is disabled");
			return inputMessage;
		}
		// 配置未开启签名
		if (!signProperties.getRequestBodyEnable()) {
			log.debug("signProperties requestBodyEnable is disabled");
			return inputMessage;
		}
		if (SignUtils.needValidateSign(parameter)) {
			String businessInfo = new BufferedReader(new InputStreamReader(inputMessage.getBody())).lines()
					.collect(Collectors.joining(System.lineSeparator()));
			log.debug("businessInfo: {}", businessInfo);
			String sign = httpServletRequest.getHeader(signProperties.getSignHeaderName());
			log.debug("sign: {}", sign);
			String appId = httpServletRequest.getHeader(signProperties.getAppIdHeaderName());
			log.debug("appId: {}", appId);
			String uri = httpServletRequest.getRequestURI();
			if (StringUtils.isNotBlank(uri)) {
				uri = uri.replace("//", "/");
			}
			String body = SignUtils.getSignBody(uri + businessInfo, appId, signProperties);
			log.debug("body: {}", body);
			signApi.validateSign(sign, body);
			return new MappingJacksonInputMessage(new ByteArrayInputStream(businessInfo.getBytes()),
					inputMessage.getHeaders());
		}
		return inputMessage;
	}

}
