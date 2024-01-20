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
package com.gitee.fubluesky.kernel.file.ftp.pojo;

import com.gitee.fubluesky.kernel.file.api.pojo.FileProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-31 13:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FtpFileProperties extends FileProperties {

    /**
     * 是否启用
     */
    private Boolean enabled = false;

    /**
     * ftp Ip
     */
    private String host;

    /**
     * ftp port
     */
    private Integer port;

    /**
     * 是否需要用户名密码验证
     */
    private Boolean auth = false;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否私有模式
     */
    private Boolean isPassive = false;

    /**
     * 临时目录
     */
    private String tempDir;

}
