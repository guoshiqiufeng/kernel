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

import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.fubluesky.kernel.db.api.pojo.domain.BaseDomain;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;

/**
 * 通用业务逻辑接口
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-05 13:38
 */
public interface IBaseService<T extends BaseDomain> extends IService<T> {

    /**
     * 新增
     *
     * @param domain 领域模型
     * @return {@code boolean}
     */
    @Override
    boolean save(T domain);

    /**
     * 删除
     *
     * @param id {@code Long} ID
     * @return {@code boolean}
     */
    boolean remove(Long id);

    /**
     * 更新
     *
     * @param domain 领域模型
     * @return {@code boolean}
     */
    boolean update(T domain);

    /**
     * 获取
     *
     * @param id {@code Long} ID
     * @return 领域模型
     */
    T get(Long id);

    /**
     * 分页查询
     *
     * @param domain 领域模型
     * @return 分页数据
     */
    PageResult findPage(T domain);

    /**
     * 批量删除
     *
     * @param ids id数组
     * @return {@code boolean}
     */
    boolean delete(Long[] ids);

}
