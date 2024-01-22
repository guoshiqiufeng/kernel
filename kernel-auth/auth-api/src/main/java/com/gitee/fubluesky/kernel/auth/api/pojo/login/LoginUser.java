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
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 10:39
 */
@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -8213601764304142784L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 状态 0：禁用 1：正常 2 : 未激活
     */
    private Integer status;

    /**
     * 用户类别 ：1 管理员 2普通用户
     */
    private Integer userType;

    /**
     * token
     */
    private String token;

    /**
     * 拓展
     */
    private String expand;

    /**
     * 角色
     */
    private Set<String> roles;

}
