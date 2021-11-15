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

package com.gitee.fubluesky.kernel.auth;

import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.UserServiceApi;
import com.gitee.fubluesky.kernel.auth.api.exception.AuthException;
import com.gitee.fubluesky.kernel.auth.api.exception.enums.AuthExceptionEnum;
import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.auth.utils.SpringContextUtils;
import com.gitee.fubluesky.kernel.cache.api.CacheOperatorApi;
import com.gitee.fubluesky.kernel.core.util.HttpServletUtil;
import com.gitee.fubluesky.kernel.jwt.api.JwtApi;
import com.gitee.fubluesky.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-12 14:19
 */
public class LoginImpl implements LoginApi {

	private final AuthProperties authProperties;

	private final CacheOperatorApi<LoginUser> loginUserCache;

	public LoginImpl(AuthProperties authProperties, CacheOperatorApi<LoginUser> loginUserCache) {
		this.authProperties = authProperties;
		this.loginUserCache = loginUserCache;
	}

	/**
	 * 获取 token
	 * @return token
	 */
	@Override
	public String getToken() {
		// 获取当前http请求
		HttpServletRequest request = HttpServletUtil.getRequest();

		// 从param参数中获取token
		String token = request.getParameter(authProperties.getTokenParamName());
		if (StringUtils.isNotBlank(token)) {
			token = token.replace(authProperties.getTokenPrefix(), "");
			return token;
		}

		// 从header中获取token
		token = request.getHeader(authProperties.getTokenHeaderName());
		if (StringUtils.isNotBlank(token)) {
			token = token.replace(authProperties.getTokenPrefix(), "");
			return token;
		}

		// 从cookie中获取token
		String cookieName = authProperties.getTokenCookieName();
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				// 如果cookie有对应的值，并且不为空
				if (cookieName.equals(cookie.getName()) && StringUtils.isNotBlank(cookie.getValue())) {
					token = cookie.getValue();
					token = token.replace(authProperties.getTokenPrefix(), "");
					return token;
				}
			}
		}
		return null;
	}

	/**
	 * 获取用户
	 * @return 用户
	 */
	@Override
	public LoginUser getLoginUser() {
		// 获取token
		String token = getToken();
		JwtApi jwtApi = SpringContextUtils.getBean(JwtApi.class);
		DefaultJwtPayload userJwt = jwtApi.getDefaultPayload(token);
		if (userJwt == null) {
			throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
		}
		UserServiceApi userServiceApi = SpringContextUtils.getBean(UserServiceApi.class);
		LoginUser user = userServiceApi.getLoginUser(userJwt.getUserId());
		if (user == null) {
			throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
		}
		// user cache expire 1h
		loginUserCache.add(user.getUserId() + "", user, 60 * 60L);
		return user;
	}

	/**
	 * 获取用户缓存
	 * @param userId 用户id
	 * @return 用户
	 */
	@Override
	public LoginUser getLoginUserCache(Long userId) {
		return loginUserCache.get(userId + "");
	}

	/**
	 * 更新用户缓存
	 * @param userId 用户id
	 * @param loginUser 用户信息
	 */
	@Override
	public void updateLoginUserCache(Long userId, LoginUser loginUser) {
		loginUserCache.add(userId + "", loginUser);
	}

	/**
	 * 删除用户缓存
	 * @param userId 用户id
	 */
	@Override
	public void deleteLoginUserCache(Long userId) {
		loginUserCache.delete(userId + "");
	}

}
