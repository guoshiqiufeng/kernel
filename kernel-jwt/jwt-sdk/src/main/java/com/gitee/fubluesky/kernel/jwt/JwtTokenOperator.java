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

package com.gitee.fubluesky.kernel.jwt;

import com.gitee.fubluesky.kernel.core.util.BeanMapTool;
import com.gitee.fubluesky.kernel.jwt.api.JwtApi;
import com.gitee.fubluesky.kernel.jwt.api.exception.JwtException;
import com.gitee.fubluesky.kernel.jwt.api.exception.enums.JwtExceptionEnum;
import com.gitee.fubluesky.kernel.jwt.api.pojo.config.JwtConfig;
import com.gitee.fubluesky.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 17:18
 */
public class JwtTokenOperator implements JwtApi {

	private final JwtConfig jwtConfig;

	public JwtTokenOperator(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}

	/**
	 * 生成token
	 * @param payload 载体信息
	 * @return token
	 */
	@Override
	public String generateToken(Map<String, Object> payload) {
		JwtBuilder builder = Jwts.builder().setClaims(payload).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret());
		if (jwtConfig.getExpire() > 0) {
			Date expireDate = new Date(System.currentTimeMillis() + jwtConfig.getExpire() * 1000);
			builder.setExpiration(expireDate);
		}
		return builder.compact();
	}

	/**
	 * 生成token
	 * @param defaultJwtPayload 载体信息
	 * @return token
	 */
	@Override
	public String generateToken(DefaultJwtPayload defaultJwtPayload) {
		Date expireDate = null;
		if (jwtConfig.getExpire() > 0) {
			expireDate = new Date(System.currentTimeMillis() + jwtConfig.getExpire() * 1000);
			defaultJwtPayload.setExpirationDate(expireDate.getTime());
		}
		JwtBuilder builder = Jwts.builder().setClaims(BeanMapTool.beanToMap(defaultJwtPayload))
				.setSubject(defaultJwtPayload.getUserId().toString()).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret());
		if (expireDate != null) {
			builder.setExpiration(expireDate);
		}
		return builder.compact();
	}

	/**
	 * 获取jwt的payload（通用的）
	 * @param token token
	 * @return 载体信息
	 */
	@Override
	public Map<String, Object> getPayloadClaims(String token) {
		return Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
	}

	/**
	 * 获取jwt的payload
	 * @param token token
	 * @return 载体信息
	 */
	@Override
	public DefaultJwtPayload getDefaultPayload(String token) {
		Map<String, Object> jwtPayload = getPayloadClaims(token);
		return BeanMapTool.mapToBean(jwtPayload, DefaultJwtPayload.class);
	}

	/**
	 * 校验jwt token是否正确
	 * @param token token
	 * @return true token正确，false token错误或失效
	 */
	@Override
	public boolean validateToken(String token) {
		try {
			getPayloadClaims(token);
			return true;
		}
		catch (io.jsonwebtoken.JwtException jwtException) {
			return false;
		}
	}

	/**
	 * 校验jwt token是否正确，如果参数token是错误的会抛出对应异常
	 * @param token token
	 * @throws JwtException token过期了 错误
	 */
	@Override
	public void validateTokenWithException(String token) throws JwtException {
		boolean tokenIsExpired = this.validateTokenIsExpired(token);
		if (tokenIsExpired) {
			throw new JwtException(JwtExceptionEnum.JWT_EXPIRED_ERROR);
		}

		// 判断jwt是否错误
		try {
			getPayloadClaims(token);
		}
		catch (io.jsonwebtoken.JwtException jwtException) {
			throw new JwtException(JwtExceptionEnum.JWT_PARSE_ERROR);
		}
	}

	/**
	 * 校验jwt token是否失效了
	 * @param token token
	 * @return true token失效，false token没失效
	 */
	@Override
	public boolean validateTokenIsExpired(String token) {
		try {
			Claims claims = (Claims) getPayloadClaims(token);
			final Date expiration = claims.getExpiration();
			return expiration.before(new Date());
		}
		catch (ExpiredJwtException expiredJwtException) {
			return true;
		}
	}

}
