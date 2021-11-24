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

import com.gitee.fubluesky.kernel.core.exception.ServiceException;
import com.gitee.fubluesky.kernel.core.exception.enums.http.ServletExceptionEnum;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServlet工具类
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 15:48
 */
@UtilityClass
public class HttpServletUtil {

	/**
	 * 获取request对象
	 */
	public HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes == null) {
			throw new ServiceException(ServletExceptionEnum.HTTP_CONTEXT_ERROR);
		}
		else {
			return requestAttributes.getRequest();
		}
	}

	/**
	 * 获取response对象
	 */
	public HttpServletResponse getResponse() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes == null) {
			throw new ServiceException(ServletExceptionEnum.HTTP_CONTEXT_ERROR);
		}
		else {
			return requestAttributes.getResponse();
		}
	}

	public String getOrigin() {
		HttpServletRequest request = getRequest();
		return request.getHeader("Origin");
	}

}
