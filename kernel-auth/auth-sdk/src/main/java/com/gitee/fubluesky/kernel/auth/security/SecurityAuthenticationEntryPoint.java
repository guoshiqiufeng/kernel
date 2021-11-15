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

package com.gitee.fubluesky.kernel.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.fubluesky.kernel.auth.api.exception.enums.AuthExceptionEnum;
import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.auth.utils.SpringContextUtils;
import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.core.util.HttpServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 匿名用户访问无权限资源时的 异常
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-25 15:01
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException authException) throws IOException {
		ResponseResult responseResult = ResponseResult.failure(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
		AuthProperties properties = SpringContextUtils.getBean(AuthProperties.class);
		if (StringUtils.isNotBlank(httpServletResponse.getHeader(properties.getTokenHeaderName()))) {
			responseResult = ResponseResult.failure(AuthExceptionEnum.AUTH_TOKEN_ERROR);
		}
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		httpServletResponse.setCharacterEncoding("utf-8");
		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setHeader("Access-Control-Expose-Headers", properties.getTokenHeaderName());
		httpServletResponse.setHeader("Access-Control-Allow-Origin", HttpServletUtil.getOrigin());
		ObjectMapper objectMapper = new ObjectMapper();
		String resBody = objectMapper.writeValueAsString(responseResult);
		PrintWriter printWriter = httpServletResponse.getWriter();
		printWriter.print(resBody);
		printWriter.flush();
		printWriter.close();
	}

}
