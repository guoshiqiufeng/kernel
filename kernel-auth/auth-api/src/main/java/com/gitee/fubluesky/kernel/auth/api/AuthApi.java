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
package com.gitee.fubluesky.kernel.auth.api;

import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginRequest;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginResponse;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-12 13:51
 */
public interface AuthApi {

	/**
	 * 登录 逻辑处理 不链接数据库 进行校验 调用此方法前 自行进行数据库数据校验等操作
	 * @param loginRequest 登录信息
	 * @return LoginResponse
	 */
	LoginResponse login(LoginRequest loginRequest);

	/**
	 * 登录 逻辑处理 不链接数据库 进行校验 调用此方法前 自行进行数据库数据校验等操作
	 * @param loginRequest 登录信息
	 * @param validatePassword 是否校验密码
	 * @return LoginResponse
	 */
	LoginResponse login(LoginRequest loginRequest, boolean validatePassword);

	/**
	 * 退出登录
	 */
	void logout();

	/**
	 * 创建用户token 设置缓存
	 * @param loginUser 用户信息
	 * @param rememberMe 是否记住
	 * @return token
	 */
	String createToken(LoginUser loginUser, Boolean rememberMe);

}
