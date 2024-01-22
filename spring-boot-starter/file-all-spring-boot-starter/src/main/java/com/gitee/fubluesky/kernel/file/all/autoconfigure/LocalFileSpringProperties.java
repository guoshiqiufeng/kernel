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
package com.gitee.fubluesky.kernel.file.all.autoconfigure;

import com.gitee.fubluesky.kernel.file.local.pojo.LocalFileProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author yanghq
 * @version 1.0
 * @since 2022-03-10 11:44
 */
@ConfigurationProperties(prefix = "kernel.file.local")
public class LocalFileSpringProperties extends LocalFileProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = -3702694512869108639L;

}
