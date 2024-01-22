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
package com.gitee.fubluesky.kernel.auth.component;

import cn.hutool.core.util.StrUtil;
import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.auth.utils.GrantedAuthorityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.util.Collection;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-24 9:54 使用方式：
 * 在Controller方法上添加 @PreAuthorize("@pms.hasPermission('a:delete')") 会判断是否有a:delete的权限
 */
@Slf4j
@Component("pms")
public class PermissionService {

    @Autowired
    private LoginApi loginApi;

    public boolean hasPermission(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }
        LoginUser loginUser = loginApi.getLoginUser();
        if (loginUser == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = GrantedAuthorityUtils.getAuthorities(loginUser.getRoles());
        return authorities.stream().map(GrantedAuthority::getAuthority).filter(StrUtil::isNotBlank)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
    }

}
