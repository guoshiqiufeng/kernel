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

import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;

/**
 * 用户信息
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-12 13:47
 */
public interface LoginApi {

	/**
	 * 获取 token
	 * @return token
	 */
	String getToken();

	/**
	 * 获取当前用户缓存
	 * @return 用户
	 */
	LoginUser getLoginUser();

	/**
	 * 获取用户缓存
	 * @param userId 用户id
	 * @return 用户
	 */
	LoginUser getLoginUserCache(Long userId);

	/**
	 * 更新用户缓存
	 * @param userId 用户id
	 * @param loginUser 用户信息
	 */
	void updateLoginUserCache(Long userId, LoginUser loginUser);

	/**
	 * 删除用户缓存
	 * @param userId 用户id
	 */
	void deleteLoginUserCache(Long userId);

}
