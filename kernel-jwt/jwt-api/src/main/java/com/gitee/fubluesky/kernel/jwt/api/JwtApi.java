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

package com.gitee.fubluesky.kernel.jwt.api;

import com.gitee.fubluesky.kernel.jwt.api.exception.JwtException;
import com.gitee.fubluesky.kernel.jwt.api.pojo.payload.DefaultJwtPayload;

import java.util.Map;

/**
 * jwt 操作
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 15:56
 */
public interface JwtApi {

	/**
	 * 生成token
	 * @param payload 载体信息
	 * @return token
	 */
	String generateToken(Map<String, Object> payload);

	/**
	 * 生成token
	 * @param defaultJwtPayload 载体信息
	 * @return token
	 */
	String generateToken(DefaultJwtPayload defaultJwtPayload);

	/**
	 * 获取jwt的payload（通用的）
	 * @param token token
	 * @return 载体信息
	 */
	Map<String, Object> getPayloadClaims(String token);

	/**
	 * 获取jwt的payload
	 * @param token token
	 * @return 载体信息
	 */
	DefaultJwtPayload getDefaultPayload(String token);

	/**
	 * 校验jwt token是否正确
	 * @param token token
	 * @return true token正确，false token错误或失效
	 */
	boolean validateToken(String token);

	/**
	 * 校验jwt token是否正确，如果参数token是错误的会抛出对应异常
	 * @param token token
	 * @throws JwtException token过期了 错误
	 */
	void validateTokenWithException(String token) throws JwtException;

	/**
	 * 校验jwt token是否失效了
	 * @param token token
	 * @return true token失效，false token没失效
	 */
	boolean validateTokenIsExpired(String token);

}
