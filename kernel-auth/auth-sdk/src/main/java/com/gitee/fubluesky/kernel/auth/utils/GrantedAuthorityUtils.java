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
package com.gitee.fubluesky.kernel.auth.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-25 9:18
 */
@UtilityClass
public class GrantedAuthorityUtils implements Serializable {

    @Serial
    private static final long serialVersionUID = 8612802882156008935L;

    /**
     * roles 转换为 GrantedAuthority 列表
     *
     * @param roles 角色
     * @return GrantedAuthority
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Set<String> roles) {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
    }

}
