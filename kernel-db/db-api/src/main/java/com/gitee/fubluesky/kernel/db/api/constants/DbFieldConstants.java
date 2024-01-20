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
package com.gitee.fubluesky.kernel.db.api.constants;

/**
 * 数据库常用字段
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 9:50
 */
public interface DbFieldConstants {

    /**
     * 主键id的字段名
     */
    String ID = "id";

    /**
     * 创建用户的字段名
     */
    String CREATE_USER = "createUser";

    /**
     * 创建时间的字段名
     */
    String CREATE_DATE = "createDate";

    /**
     * 更新用户的字段名
     */
    String MODIFY_USER = "modifyUser";

    /**
     * 更新时间的字段名
     */
    String MODIFY_DATE = "modifyDate";

    /**
     * 删除标记的字段名
     */
    String IS_DELETED = "isDeleted";

}
