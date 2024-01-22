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
package com.gitee.fubluesky.kernel.db.mybatisplus.pojo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-23 17:28
 */
@Data
public class DbProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = -8733821714991760707L;

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    /**
     * 是否启用分页插件
     */
    private Boolean interceptorEnabled = true;

    /**
     * 是否启用数据填充
     */
    private Boolean metaEnabled = true;

    /**
     * 通用字段：创建时间
     */
    private String createDate = "createDate";

    /**
     * 通用字段：更新时间
     */
    private String modifyDate = "modifyDate";

    /**
     * 通用字段：已删除
     */
    private String isDeleted = "isDeleted";

}
