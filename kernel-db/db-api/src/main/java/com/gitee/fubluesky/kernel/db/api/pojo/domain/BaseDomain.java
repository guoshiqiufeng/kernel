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
package com.gitee.fubluesky.kernel.db.api.pojo.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用领域模型
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 10:06
 */
@Data
public class BaseDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = -9106347079313841483L;

}
