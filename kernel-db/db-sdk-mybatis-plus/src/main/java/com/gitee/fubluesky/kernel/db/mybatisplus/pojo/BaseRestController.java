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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gitee.fubluesky.kernel.core.pojo.ResponseResult;
import com.gitee.fubluesky.kernel.db.api.pojo.domain.BaseDomain;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通用控制层 rest 实现
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-05-10 16:51
 */
@SuppressWarnings("all")
public abstract class BaseRestController<T extends BaseDomain, S extends IBaseService<T>> {

    @Autowired
    protected S service;

    /**
     * 新增
     *
     * @param domain 领域模型
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult save(@Valid @RequestBody T domain) {
        // 业务逻辑
        boolean created = service.save(domain);
        if (created) {
            return ResponseResult.success("创建成功");
        }

        return ResponseResult.failure();
    }

    /**
     * 删除
     *
     * @param id {@code Long}
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable Long id) {
        // 业务逻辑
        boolean deleted = service.remove(id);
        if (deleted) {
            return ResponseResult.success("删除成功");
        }

        return ResponseResult.failure();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return {@link ResponseResult}
     */
    @DeleteMapping
    public ResponseResult delete(@RequestBody Long[] ids) {
        // 业务逻辑
        boolean deleted = service.delete(ids);
        if (deleted) {
            return ResponseResult.success("删除成功");
        }

        return ResponseResult.failure();
    }

    /**
     * 修改
     *
     * @param domain 领域模型
     * @return {@link ResponseResult}
     */
    @PutMapping
    public ResponseResult update(@Valid @RequestBody T domain) {
        // 业务逻辑
        boolean updated = service.update(domain);
        if (updated) {
            return ResponseResult.success("编辑成功");
        }

        return ResponseResult.failure();
    }

    /**
     * 获取
     *
     * @param id {@code Long} id
     * @return {@link ResponseResult}
     */
    @GetMapping(value = {"/{id}", "/info/{id}"})
    public ResponseResult get(@PathVariable Long id) {
        T domain = service.get(id);
        return ResponseResult.success(domain);
    }

    /**
     * 分页
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/page")
    public ResponseResult page(@ModelAttribute T domain) {
        PageResult pageResult = service.findPage(domain);
        return ResponseResult.success(pageResult);
    }

    /**
     * 列表查询
     *
     * @param domain 领域模型
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult list(@ModelAttribute T domain) {
        List<T> list = service.list(new QueryWrapper<>(domain));
        return ResponseResult.success(list);
    }

}
