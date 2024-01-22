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
package com.gitee.fubluesky.kernel.security.cors.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-26 14:39
 */
@Data
public class CorsProperties implements Serializable {

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    /**
     * 路径
     */
    private String path = "/**";

    /**
     * 用户是否可以发送、处理 cookie
     */
    private Boolean allowCredentials = true;

    /**
     * 用户允许的网站域名，默认 全允许 为 *
     */
    private String allowedOrigin = "*";

    private List<String> allowHeader;

    private List<String> allowMethod;

}
