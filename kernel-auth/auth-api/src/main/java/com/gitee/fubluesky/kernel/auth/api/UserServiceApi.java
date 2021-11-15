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
import com.gitee.fubluesky.kernel.auth.api.pojo.login.UserLoginInfoDTO;

import java.util.Set;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-12 16:31
 */
public interface UserServiceApi {

	/**
	 * 获取用户信息
	 * @param account 账号
	 * @return 用户信息
	 */
	UserLoginInfoDTO getUserLoginInfo(String account);

	/**
	 * 校验密码 自定义实现
	 * @param password 密码
	 * @param userLoginInfoDTO 用户信息
	 */
	default void validatePassword(String password, UserLoginInfoDTO userLoginInfoDTO) {
	}

	/**
	 * 获取 用户
	 * @param userId 用户id
	 * @return 用户
	 */
	LoginUser getLoginUser(Long userId);

	/**
	 * 获取用户头像
	 * @param userId 用户id
	 * @return 用户头像
	 */
	String getUserIcon(Long userId);

	/**
	 * 设置用户头像
	 * @param userId 用户id
	 * @param userIcon 用户头像
	 */
	void setUserIcon(Long userId, String userIcon);

	/**
	 * 获取用户所有权限
	 * @param userId 用户id
	 * @return 权限集合
	 */
	Set<String> getUserPermissions(Long userId);

	/**
	 * 删除用户权限缓存
	 * @param userId 用户id
	 */
	void deleteUserPermissionsCache(Long userId);

}
