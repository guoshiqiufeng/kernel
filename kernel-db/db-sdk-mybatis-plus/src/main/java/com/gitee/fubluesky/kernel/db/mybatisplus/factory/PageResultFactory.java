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
package com.gitee.fubluesky.kernel.db.mybatisplus.factory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 16:46
 */
@UtilityClass
public class PageResultFactory {

    /**
     * PageResult 封装
     *
     * @param page mybatis plus 分页对象
     * @return PageResult
     */
    public PageResult createPageResult(IPage<?> page) {
        return new PageResult(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    /**
     * PageResult 封装
     *
     * @param list       列表数据
     * @param totalCount 总条数
     * @param pageSize   每页条数
     * @param currPage   当前页
     * @return PageResult
     */
    public PageResult createPageResult(List<?> list, int totalCount, int pageSize, int currPage) {
        return new PageResult(list, totalCount, pageSize, currPage);
    }

}
