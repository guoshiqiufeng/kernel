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
import com.gitee.fubluesky.kernel.jwt.api.pojo.JwtProperties;
import com.gitee.fubluesky.kernel.jwt.api.pojo.DefaultJwtPayload;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 17:18
 */
@Slf4j
public class JwtTokenOperator implements JwtApi {

	private final JwtProperties jwtProperties;

	public JwtTokenOperator(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	/**
	 * 生成token
	 * @param payload 载体信息
	 * @return token
	 */
	@Override
	public String generateToken(Map<String, Object> payload) {
		JwtBuilder builder = Jwts.builder().setClaims(payload).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret());
		Date expiration = getExpiration((String) payload.get("appId"));
		if (expiration != null) {
			builder.setExpiration(expiration);
		}
		return builder.compact();
	}

	private Date getExpiration(String appId) {
		if (!jwtProperties.getEnableMultiExpire()) {
			// 未开启多租户
			if (jwtProperties.getExpire() > 0) {
				return new Date(System.currentTimeMillis() + jwtProperties.getExpire() * 1000);
			}
		}
		else {
			if (StringUtils.isBlank(appId)) {
				log.error("generateToken fail. The appId is blank!");
				throw new JwtException(JwtExceptionEnum.JWT_CREATE_ERROR);
			}
			// 获取
			Map<String, Long> map = jwtProperties.getExpireMap();
			if (map == null || map.isEmpty()) {
				log.error("generateToken fail. The expireMap is empty!");
				throw new JwtException(JwtExceptionEnum.JWT_CREATE_ERROR);
			}
			Long expire = map.get(appId);
			if (expire == null) {
				log.error("generateToken fail. The appId's expire in expireMap is empty!");
				throw new JwtException(JwtExceptionEnum.JWT_PARSE_ERROR);
			}
			if (expire > 0) {
				return new Date(System.currentTimeMillis() + expire * 1000);
			}
		}
		return null;
	}

	/**
	 * 生成token
	 * @param defaultJwtPayload 载体信息
	 * @return token
	 */
	@Override
	public String generateToken(DefaultJwtPayload defaultJwtPayload) {
		Date expiration = getExpiration(defaultJwtPayload.getAppId());
		if (expiration != null) {
			defaultJwtPayload.setExpirationDate(expiration.getTime());
		}
		JwtBuilder builder = Jwts.builder().setClaims(BeanMapTool.beanToMap(defaultJwtPayload))
				.setSubject(defaultJwtPayload.getUserId().toString()).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret());
		if (expiration != null) {
			builder.setExpiration(expiration);
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
		return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
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
