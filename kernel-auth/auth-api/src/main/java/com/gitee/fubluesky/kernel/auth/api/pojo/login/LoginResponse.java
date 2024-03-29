/*
 * Copyright (c) 2021-2024, fubluesky (fubluesky@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitee.fubluesky.kernel.auth.api.pojo.login;

import lombok.Data;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 13:40
 */
@Data
public class LoginResponse {

    /**
     * 登录人的信息
     */
    private LoginUser loginUser;

    /**
     * 登录人的token
     */
    private String token;

    /**
     * 到期时间
     */
    private Long expireAt;

    public LoginResponse(LoginUser loginUser, String token, Long expireAt) {
        this.loginUser = loginUser;
        this.token = token;
        this.expireAt = expireAt;
    }

}
