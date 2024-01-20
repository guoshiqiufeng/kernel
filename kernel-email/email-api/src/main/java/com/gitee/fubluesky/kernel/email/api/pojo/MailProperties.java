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
package com.gitee.fubluesky.kernel.email.api.pojo;

import lombok.Data;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-29 17:09
 */
@Data
public class MailProperties {

    /**
     * 是否启用
     */
    private Boolean enabled = false;

    /**
     * 服务器主机名
     */
    private String host;

    /**
     * 服务器端口
     */
    private Integer port = 465;

    /**
     * 是否需要用户名密码验证
     */
    private Boolean auth = false;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String pass;

    /**
     * 发送方
     */
    private String from;

    /**
     * 是否开启调试
     */
    private Boolean debug = false;

}
