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
package com.gitee.fubluesky.kernel.core.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础分页请求参数
 *
 * @author yanghq
 */
@Data
public class BasePageRequest implements Serializable {

    private static final long serialVersionUID = -1618391697513897845L;

    /**
     * 当前页
     */
    private int currPage = 1;

    /**
     * 每页条数
     */
    private int pageSize = 10;

}
