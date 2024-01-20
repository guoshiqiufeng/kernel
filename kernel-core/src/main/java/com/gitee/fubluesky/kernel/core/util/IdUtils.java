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
package com.gitee.fubluesky.kernel.core.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 17:13
 */
@UtilityClass
public class IdUtils {

    public String generateUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
