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
package com.gitee.fubluesky.kernel.jwt.api.pojo;

import com.gitee.fubluesky.kernel.core.util.IdUtils;
import lombok.Data;

import java.util.Map;

/**
 * 载体信息
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 16:57
 */
@Data
public class DefaultJwtPayload {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 唯一表示id, 用于缓存登录用户的唯一凭证
     */
    private String uuid;

    /**
     * 是否记住我
     */
    private Boolean rememberMe;

    /**
     * 过期时间戳
     */
    private Long expirationDate;

    /**
     * 其他载体信息
     */
    private Map<String, Object> others;

    public DefaultJwtPayload() {
    }

    public DefaultJwtPayload(Long userId, String account, String appId, boolean rememberMe) {
        this.userId = userId;
        this.account = account;
        this.appId = appId;
        this.uuid = IdUtils.generateUuid();
        this.rememberMe = rememberMe;
    }

}
