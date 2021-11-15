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

import com.gitee.fubluesky.kernel.auth.api.AuthApi;
import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.auth.cache.LoginUserTokenRedisCache;
import com.gitee.fubluesky.kernel.auth.utils.GrantedAuthorityUtils;
import com.gitee.fubluesky.kernel.auth.utils.SpringContextUtils;
import com.gitee.fubluesky.kernel.jwt.api.JwtApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt security 验证 鉴权
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-25 9:23
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LoginApi loginApi = SpringContextUtils.getBean(LoginApi.class);
		String token = loginApi.getToken();
		if (StringUtils.isBlank(token)) {
			chain.doFilter(request, response);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(getAuthentication(token, loginApi, response));
		super.doFilterInternal(request, response, chain);
	}

	/**
	 * 从token中获取用户信息
	 * @param token token
	 * @param loginApi 登录接口
	 * @param response response
	 * @return UsernamePasswordAuthenticationToken
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(String token, LoginApi loginApi,
			HttpServletResponse response) {
		AuthProperties properties = SpringContextUtils.getBean(AuthProperties.class);
		token = token.replace(properties.getTokenPrefix(), "");
		JwtApi jwtApi = SpringContextUtils.getBean(JwtApi.class);
		if (!jwtApi.validateToken(token)) {
			LoginUserTokenRedisCache tokenRedisCache = SpringContextUtils.getBean(LoginUserTokenRedisCache.class);
			LoginUser loginUser = tokenRedisCache.get(token);
			if (loginUser != null) {
				// 重新签发token
				AuthApi authApi = SpringContextUtils.getBean(AuthApi.class);
				token = authApi.createToken(loginUser, true);
				response.setHeader(properties.getTokenHeaderName(), token);
			}
			return null;
		}
		LoginUser loginUser = loginApi.getLoginUser();

		if (loginUser == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(loginUser, null,
				GrantedAuthorityUtils.getAuthorities(loginUser.getRoles()));
	}

}
